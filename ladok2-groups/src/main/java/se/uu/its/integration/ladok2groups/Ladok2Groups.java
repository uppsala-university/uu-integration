package se.uu.its.integration.ladok2groups;

import static se.uu.its.integration.ladok2groups.JdbcUtil.query;
import static se.uu.its.integration.ladok2groups.JdbcUtil.queryByObj;
import static se.uu.its.integration.ladok2groups.JdbcUtil.update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.Reg;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;
import se.uu.its.integration.ladok2groups.sql.Ladok2GroupSql;

public class Ladok2Groups {
	
	static Log log = LogFactory.getLog(Ladok2Groups.class);

	DataSource esbDs;
	DataSource ladok2ReadDs;

	EsbGroupSql esbSql = new EsbGroupSql();
	Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	
	NamedParameterJdbcTemplate esbJdbc;
	NamedParameterJdbcTemplate l2Jdbc;

	public void setEsbDs(DataSource esbDs) {
		this.esbDs = esbDs;
	}

	public void setLadok2ReadDs(DataSource ladok2ReadDs) {
		this.ladok2ReadDs = ladok2ReadDs;
	}
	
	public String updateGroupEvents() throws Exception {
		init(); // delayed init to let the ds resources have time to be injected
		// Not interested in events older than this:
		Date defaultFrom = MembershipEventUtil.DATE_FORMAT.parse("2015-11-01 000000"); // TODO: 2007-01-01
		MembershipEvent pme = getMostRecentPotentialMembershipEvent();
		// Skip forward 1 second from most recent event to avoid duplicate events:
		Date from = (pme == null) ? defaultFrom : new Date(pme.getDate().getTime() + 1000);
		Date now = new Date();
		// Skip most recent events to make sure all events for that time have arrived at Ladok:
		Date to = new Date(now.getTime() - 15000); 
		return updateGroupEventsFromDate(from, to);
	}
	
	public String updateGroupEventsFromDate(Date from, Date to) {
		init(); // delayed init to let the ds resources have time to be injected
		List<MembershipEvent> mes = updatePotentialMembershipEvents(from, to);
		mes = updateMembershipEvents(mes);
		updateMemberships(mes);
		return "Number of new Ladok membership events: " + mes.size();
	}
	
	void init() {
		if (esbJdbc == null) { esbJdbc = new NamedParameterJdbcTemplate(esbDs); }
		if (l2Jdbc == null) { l2Jdbc = new NamedParameterJdbcTemplate(ladok2ReadDs); }
	}
	
	List<MembershipEvent> updatePotentialMembershipEvents(Date from, Date to) {
		List<MembershipEvent> mes =  getNewLadokMembershipEvents(from, to); // TODO: max num
		if (mes.size() > 10) {
			mes =  mes.subList(0, 10);
		}
		saveNewPotentialMembershipEvents(mes);
		return mes;
	}
	
	List<MembershipEvent> updateMembershipEvents(List<MembershipEvent> potentialMembershipEvents) {
		List<MembershipEvent> mes = new ArrayList<MembershipEvent>();
		for (MembershipEvent me : potentialMembershipEvents) {
			if (me.getType() == MembershipEvent.Type.ADD) {
				mes.add(me);
			} else {
				List<Membership> ms = findMemberships(me);
				// TODO: check unique origin instead of only result set size:
				if (ms.size() == 1) {
					me.setReportCode(ms.get(0).getReportCode());
					mes.add(me);
				} else {
					log.info("Skipping potential membership event due to ambiguity: " + me);
				}
			}
		}
		saveNewMembershipEvents(mes);
		return mes;
	}
	
	void updateMemberships(List<MembershipEvent> mes) {
		update(esbJdbc, esbSql.getSaveNewMembershipsSql(), MembershipEventUtil.toMemberships(mes));
	}
	
	List<MembershipEvent> getNewLadokMembershipEvents(Date from, Date to) {
		String fd = MembershipEventUtil.DATE_FORMAT.format(from);
		String[] dateAndTime = fd.split(" ");
		String date = dateAndTime[0];
		String time = dateAndTime[1];
		List<Reg> reg = query(l2Jdbc, Reg.class, l2Sql.getRegSql(), "datum", date, "tid", time);
		List<BortReg> bortreg = query(l2Jdbc, BortReg.class, l2Sql.getBortRegSql(), "datum", date, "tid", time);
		List<InReg> inreg = query(l2Jdbc, InReg.class, l2Sql.getInRegSql(), "datum", date, "tid", time);
		List<Avliden> avliden = query(l2Jdbc, Avliden.class, l2Sql.getAvlidenSql(), "datum", date);
		List<MembershipEvent> mes = new ArrayList<MembershipEvent>();
        mes.addAll(MembershipEventUtil.toMembershipEvents(reg));
        mes.addAll(MembershipEventUtil.toMembershipEvents(bortreg));
        mes.addAll(MembershipEventUtil.toMembershipEvents(inreg));
        mes.addAll(MembershipEventUtil.toMembershipEvents(avliden));
		MembershipEventUtil.sort(mes);
		mes = MembershipEventUtil.filter(mes, from, to);
		return mes;
	}
	
	MembershipEvent getMostRecentPotentialMembershipEvent() {
		List<MembershipEvent> mes = query(esbJdbc, MembershipEvent.class,
				esbSql.getMostRecentPotentialMembershipEventSql());
		return mes.isEmpty() ? null : mes.get(0);
	}

	MembershipEvent getMostRecentMembershipEvent() {
		List<MembershipEvent> mes = query(esbJdbc, MembershipEvent.class,
				esbSql.getMostRecentMembershipEventSql());
		return mes.isEmpty() ? null : mes.get(0);
	}

	List<Membership> findMemberships(MembershipEvent potentialMembershipEvent) {
		return queryByObj(esbJdbc, Membership.class, esbSql.getFindMembershipsSql(), potentialMembershipEvent);
	}

	int[] saveNewPotentialMembershipEvents(List<MembershipEvent> membershipEvents) {
		return update(esbJdbc, esbSql.getSaveNewPotentialMembershipEventsSql(), membershipEvents);
	}
	
	int[] saveNewMembershipEvents(List<MembershipEvent> membershipEvents) {
		return update(esbJdbc, esbSql.getSaveNewMembershipEventsSql(), membershipEvents);
	}
	
	int[] saveNewMemberships(List<Membership> memberships) {
		return update(esbJdbc, esbSql.getSaveNewMembershipsSql(), memberships);
	}
	
}
