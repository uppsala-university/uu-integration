package se.uu.its.integration.ladok2groups.service;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.query;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.updateN;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import se.uu.its.integration.ladok2groups.dto.AccMembership;
import se.uu.its.integration.ladok2groups.l2dto.Antagen;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;
import se.uu.its.integration.ladok2groups.sql.Ladok2GroupSql;
import se.uu.its.integration.ladok2groups.util.MembershipEventUtil;

@Service
public class AcceptedEventService {
	
	static Log log = LogFactory.getLog(AcceptedEventService.class);
	
	@Autowired @Qualifier("ladok2read")
	DataSource ladok2ReadDs;

	@Autowired @Qualifier("esb")
	DataSource esbDs;

	@Autowired @Qualifier("ladok2read")
	NamedParameterJdbcTemplate l2Jdbc;
	
	@Autowired @Qualifier("esb")
	NamedParameterJdbcTemplate esbJdbc;

	Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	EsbGroupSql esbSql = new EsbGroupSql();
	
	@Scheduled(fixedDelayString = "${app.AcceptedEventService.updateAcceptedMembers.delay}")
	public void updateAcceptedMembers() throws Exception {
		System.out.println("Helu schedule: " + System.currentTimeMillis());
	}
	
	@Scheduled(cron="0 0 4 * * *")
	public void everyNightAtFour() {
		System.out.println("It's four in the morning!");
	}

	@Scheduled(cron="*/10 * * * * *")
	public void everyTenSeconds() {
		System.out.println("It's been 10 seconds!");
	}
	
	public String updateAccepted() {
		String semester = "20162";
		Date now = new Date();
		List<Antagen> l2Ant = query(l2Jdbc, Antagen.class,
				l2Sql.getAntagenSql(), "termin", semester);
		List<AccMembership> l2Accs = MembershipEventUtil.toAccMemberships(
				l2Ant, now);
		List<AccMembership> storedAccs = query(esbJdbc, AccMembership.class,
				esbSql.getAccMembershipsSql(), "semester", semester);
		long start = System.currentTimeMillis();
		updateN(esbDs, log,
				sqlAndVals(esbSql.getSaveNewAccMembershipSql(), l2Accs));
		long tot = System.currentTimeMillis() - start;
		return "Time: " + tot + ", " + l2Accs.size() + " "
				+ (l2Accs.size() > 0 ? l2Accs.get(0) : "");
	}
}
