package se.uu.its.integration.ladok2groups.service;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.executeStatementsInSameTx;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByParams;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import se.uu.its.integration.ladok2groups.dto.AccMembership;
import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.l2dto.Antagen;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;
import se.uu.its.integration.ladok2groups.sql.Ladok2GroupSql;
import se.uu.its.integration.ladok2groups.util.MembershipEventUtil;

@Service
public class AcceptedEventService {
	
	static Log log = LogFactory.getLog(AcceptedEventService.class);
	
	/*
	@Autowired @Qualifier("ladok2read")
	DataSource ladok2ReadDs;

	@Autowired @Qualifier("esb")
	DataSource esbDs;
	*/
	
	@Autowired @Qualifier("ladok2read")
	NamedParameterJdbcTemplate l2Jdbc;
	
	@Autowired @Qualifier("esb")
	NamedParameterJdbcTemplate esbJdbc;

	@Autowired @Qualifier("esb")
	PlatformTransactionManager esbTm;

	Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	EsbGroupSql esbSql = new EsbGroupSql();
	
	//@Scheduled(fixedDelayString = "${app.AcceptedEventService.updateAcceptedMembers.delay}")
	public void updateAcceptedMembers() throws Exception {
		System.out.println("Helu schedule: " + System.currentTimeMillis());
	}
	
	//@Scheduled(cron="0 0 4 * * *")
	public void everyNightAtFour() {
		System.out.println("It's four in the morning!");
	}

	//@Scheduled(cron="*/10 * * * * *")
	public void everyTenSeconds() {
		System.out.println("It's been 10 seconds!");
	}
	
	public String updateAccepted() {
		long start = System.currentTimeMillis();
		// TODO: Include prev and/or next semester too?:
		String semester = queryByParams(l2Jdbc, String.class, l2Sql.getTerminSql()).get(0);
		Date accMemberFetchTime = new Date();
		List<Antagen> l2Ant = queryByParams(l2Jdbc, Antagen.class,
				l2Sql.getAntagenSql(), "termin", semester);
		List<AccMembership> l2Accs = MembershipEventUtil.toAccMemberships(
				l2Ant, accMemberFetchTime);
		List<AccMembership> storedAccs = queryByParams(esbJdbc, AccMembership.class,
				esbSql.getAccMembershipsSql(), "semester", semester);
		List<AccMembership> addedAccs = new ArrayList<AccMembership>(l2Accs);
		addedAccs.removeAll(storedAccs);
		List<MembershipEvent> membershipAddEvents = MembershipEventUtil.toMembershipAddEvents(addedAccs);
		/*
		List<AccMembership> removedAccs = new ArrayList<AccMembership>(storedAccs);
		removedAccs.removeAll(l2Accs);
		List<MembershipEvent> membershipRemoveEvents = MembershipEventUtil.toMembershipRemoveEvents(removedAccs);
		*/
		saveAccMemberEvents(l2Accs, membershipAddEvents);
		return "Time: " + (System.currentTimeMillis() - start) + ", Size: " + l2Accs.size() + " "
				+ (l2Accs.size() > 0 ? l2Accs.get(0) : "");
	}
	
	void saveAccMemberEvents(List<AccMembership> memberships, List<MembershipEvent> membAddEvents) {
		List<GroupEvent> groupEvents = new ArrayList<>();
		Set<String> cis = getUniqueCourseInstances(membAddEvents);
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
				MembershipEvent me = membAddEvents.get(0);
				groupEvents.add(new GroupEvent(me.getDate(), courseCode,
						startSemester, reportCode, me.getOrigin()));
			}
		}
		/*updateN(esbDs, log,*/
		executeStatementsInSameTx(esbJdbc, esbTm,
				sqlAndVals(esbSql.getSaveNewMembershipEventSql(), groupEvents),
				sqlAndVals(esbSql.getSaveNewMembershipEventSql(), membAddEvents),
				sqlAndVals(esbSql.getSaveNewAccMembershipSql(), memberships));
	}
	
	Set<String> getUniqueCourseInstances(List<MembershipEvent> mes) {
		Set<String> cis = new HashSet<String>();
		for (MembershipEvent me : mes) {
			cis.add(me.getCourseCode() + ":" + me.getStartSemester() + ":"
					+ me.getReportCode());
		}
		return cis;
	}
	
}
