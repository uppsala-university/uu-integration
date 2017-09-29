package se.uu.its.integration.ladok2groups.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import se.uu.its.integration.ladok2groups.dto.AccMembership;
import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent.Type;
import se.uu.its.integration.ladok2groups.l2dto.Antagen;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;
import se.uu.its.integration.ladok2groups.sql.Ladok2GroupSql;
import se.uu.its.integration.ladok2groups.util.MembershipEventUtil;
import se.uu.its.integration.ladok2groups.util.SemesterUtil;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.executeStatementsInSameTx;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByParams;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sql;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

@Service
public class AcceptedEventService {

	private static final Logger logger = LoggerFactory.getLogger(AcceptedEventService.class);

	@Autowired
	@Qualifier("ladok2read")
	private NamedParameterJdbcTemplate l2Jdbc;

	@Autowired
	@Qualifier("esb")
	private NamedParameterJdbcTemplate esbJdbc;

	@Autowired
	@Qualifier("esb")
	private PlatformTransactionManager esbTm;

	private Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	private EsbGroupSql esbSql = new EsbGroupSql();

	@Autowired
	private ScheduledJobSynchronizer scheduledJobSynchronizer;

	@Scheduled(cron = "${events.accUpdateCron}")
	public void updateAccepted() {
		scheduledJobSynchronizer.executeExclusively(() -> saveAccMembershipAndMembershipEvents());
	}

	private void saveAccMembershipAndMembershipEvents() {
		logger.info("Update accepted job started");
		long start = System.currentTimeMillis();
		String semester = queryByParams(l2Jdbc, String.class, l2Sql.getTerminSql()).get(0);
		String nextSemester = SemesterUtil.getNextSemester(semester);
		List<AccMembership> storedAccs = queryByParams(esbJdbc, AccMembership.class, esbSql.getAccMembershipsSql(),"semester1", semester, "semester2", nextSemester);
		long storedRead = System.currentTimeMillis();
		List<Antagen> l2Ant = queryByParams(l2Jdbc, Antagen.class,
				l2Sql.getAntagenSql(), "termin1", semester, "termin2", nextSemester);
		long l2Read = System.currentTimeMillis();
		LocalDateTime eventDate = LocalDateTime.now();
		List<AccMembership> l2Accs = MembershipEventUtil.toAccMemberships(
				l2Ant, eventDate);
		long save = System.currentTimeMillis();
		// Add membership events:
		List<AccMembership> addedAccs = new ArrayList<>(l2Accs);
		addedAccs.removeAll(storedAccs);
		List<MembershipEvent> membershipAddEvents = MembershipEventUtil.toMembershipAddEvents(addedAccs);
		// Remove membership events:
		List<AccMembership> removedAccs = new ArrayList<>(storedAccs);
		removedAccs.removeAll(l2Accs);
		List<MembershipEvent> membershipRemoveEvents = MembershipEventUtil.toMembershipRemoveEvents(removedAccs);
		// All membership events:
		List<MembershipEvent> allMembershipEvents = new ArrayList<>(membershipAddEvents);
		allMembershipEvents.addAll(membershipRemoveEvents);
		// saveAccMemberEvents(l2Accs, membershipAddEvents);
		saveAccMemberEvents(l2Accs, allMembershipEvents);
		logger.info("Accepted membership events update: Stored: {}, Ladok: {}, New: {} in {} ms (storedRead: {}, l2read: {} , save: {})",
				storedAccs.size(), l2Accs.size(), addedAccs.size(), System.currentTimeMillis() - start, storedRead - start, l2Read - storedRead, save - l2Read);
	}

	void saveAccMemberEvents(List<AccMembership> memberships, List<MembershipEvent> membEvents) {
		List<GroupEvent> groupEvents = new ArrayList<>();
		Set<String> cis = getUniqueCourseInstances(membEvents);
		for (String ci : cis) {
			String[] split = ci.split(":");
			String courseCode = split[0];
			String startSemester = split[1];
			String reportCode = split[2];
			List<Integer> numRegs = queryByParams(esbJdbc, Integer.class,
					esbSql.getNumberOfMembershipsForCourseInstanceSql(),
					"startSemester", startSemester,
					"reportCode", reportCode);
			if (Integer.valueOf(0).equals(numRegs.get(0))) {
				MembershipEvent me = membEvents.get(0);
				groupEvents.add(new GroupEvent(me.getDate(), courseCode,
						startSemester, reportCode, me.getOrigin()));
			}
		}
		executeStatementsInSameTx(esbJdbc, esbTm,
				sqlAndVals(esbSql.getSaveNewMembershipEventSql(), groupEvents),
				sqlAndVals(esbSql.getSaveNewMembershipEventSql(), membEvents),
				sql(esbSql.getDeleteAccMembershipsSql()),
				sqlAndVals(esbSql.getSaveNewAccMembershipSql(), memberships));
	}

	Set<String> getUniqueCourseInstances(List<MembershipEvent> mes) {
		Set<String> cis = new HashSet<String>();
		for (MembershipEvent me : mes) {
			if (me.getMeType() != Type.ForvantatDeltagandeSkapadEvent) {
				continue;
			}
			cis.add(me.getCourseCode() + ":" + me.getStartSemester() + ":"
					+ me.getReportCode());
		}
		return cis;
	}

}
