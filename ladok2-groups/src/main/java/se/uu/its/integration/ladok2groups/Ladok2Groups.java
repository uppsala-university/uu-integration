package se.uu.its.integration.ladok2groups;

import static se.uu.its.integration.ladok2groups.JdbcUtil.query;
import static se.uu.its.integration.ladok2groups.JdbcUtil.queryByObj;
import static se.uu.its.integration.ladok2groups.JdbcUtil.update;
import static se.uu.its.integration.ladok2groups.JdbcUtil.update2;
import static se.uu.its.integration.ladok2groups.MembershipEventUtil.DATE_FORMAT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	
	Date ladok2GroupEventStart;

	DataSource esbDs;
	DataSource ladok2ReadDs;

	EsbGroupSql esbSql = new EsbGroupSql();
	Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	
	NamedParameterJdbcTemplate esbJdbc;
	NamedParameterJdbcTemplate l2Jdbc;
	
	public Date getLadok2GroupEventStartTime() {
		return ladok2GroupEventStart;
	}
	
	public static void main(String[] args) throws ParseException {
		Date d = new Date();
		d = DATE_FORMAT.parse("2016-01-01 080105");
		Calendar c = new GregorianCalendar();
		c.clear();
		c.setTimeInMillis(d.getTime() + 86400000L);
		c.set(Calendar.MILLISECOND, 0);
		int ms = c.get(Calendar.MILLISECOND);
		int ss = c.get(Calendar.SECOND);
		System.out.println(DATE_FORMAT.format(d) + ", ms=" + ms + ", ss=" + ss
				+ ", t=" + d.getTime() + ", c=" + c.getTimeInMillis() + ", cf=" + DATE_FORMAT.format(c.getTime()));
	}
	
	public String getLadok2GroupEventStart() {
		return DATE_FORMAT.format(ladok2GroupEventStart);
	}
	
	public void setLadok2GroupEventStart(String time) {
		try {
			ladok2GroupEventStart = MembershipEventUtil.DATE_FORMAT.parse(time);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public void setEsbDs(DataSource esbDs) {
		this.esbDs = esbDs;
	}

	public void setLadok2ReadDs(DataSource ladok2ReadDs) {
		this.ladok2ReadDs = ladok2ReadDs;
	}
	
	void init() {
		if (esbJdbc == null) { esbJdbc = new NamedParameterJdbcTemplate(esbDs); }
		if (l2Jdbc == null) { l2Jdbc = new NamedParameterJdbcTemplate(ladok2ReadDs); }
	}
	
	public String updateGroupEvents() throws Exception {
		init(); // delayed init to let the ds resources have time to be injected
		List<MembershipEvent> pmes = query(esbJdbc, MembershipEvent.class,
				esbSql.getMostRecentPotentialMembershipEventSql());
		// Skip forward 1 second from most recent event to avoid duplicate events:
		Date from = pmes.isEmpty() ? getLadok2GroupEventStartTime() : new Date(
				pmes.get(0).getDate().getTime() + 1000);
		// Skip most recent events to make sure all events for this interval have arrived at Ladok:
		Date to = new Date(new Date().getTime() - 15000);
		int numMEs = updatePotentialMembershipEvents(from, to);
		updateMembershipEvents();
		return "Number of new Ladok membership events: " + numMEs;
	}
	
	int updatePotentialMembershipEvents(Date from, Date to) {
		// TODO: Iterate over days between from/to?
		long time = from.getTime(), end = to.getTime(), dayMS = 86400000L;
		Calendar c = new GregorianCalendar();
		c.clear();
		c.setTimeInMillis(time);
		c.set(Calendar.MILLISECOND, 0);
		time = c.getTimeInMillis();
		// while (time < to) {
		    // TODO
		//}
		
		List<MembershipEvent> mes =  getNewLadokMembershipEvents(from, to); // TODO: max num
		if (mes.size() > 10) {
			mes =  mes.subList(0, 10);
		}
		update(esbJdbc, esbSql.getSaveNewPotentialMembershipEventSql(), mes);
		return mes.size();
	}
	
	int updateMembershipEvents() {
		List<MembershipEvent> potentialMembershipEvents = getUnprocessedPotentialMembershipEvents();
		//List<MembershipEvent> mes = new ArrayList<MembershipEvent>();
		log.info("Unprocessed mes: " + potentialMembershipEvents);
		for (MembershipEvent me : potentialMembershipEvents) {
			if (me.getMeType() == MembershipEvent.Type.ADD) {
				// TODO: fortsattningsreg -> update membership ?
				update2(esbDs, 
						esbSql.getSaveNewMembershipEventSql(), me,
						esbSql.getSaveNewMembershipSql(), MembershipEventUtil.toMembership(me), 
						log);
				log.info("Adding new membership add event: " + me);
			} else {
				List<Membership> ms = queryByObj(esbJdbc, Membership.class, esbSql.getFindMembershipsSql(), me);
				if (ms.size() == 1) {
					me.setReportCode(ms.get(0).getReportCode());
					update(esbJdbc, esbSql.getSaveNewMembershipEventSql(), me);
					// TODO: Delete memberships in the same transaction
					log.info("Adding new membership remove event: " + me);
				} else if (ms.size() == 0) {
					log.info("Skipping potential membership remove event due to no matching membership: " + me);
				} else {
					// TODO: check matching memberships and possibly make multiple events
					log.info("Skipping potential membership remove event due to ambiguity: " + me);
				}
			}
		}
		return potentialMembershipEvents.size();
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
	
	List<MembershipEvent> getUnprocessedPotentialMembershipEvents() {
		List<MembershipEvent> unprocessed;
		List<MembershipEvent> mes = query(esbJdbc, MembershipEvent.class,
				esbSql.getMostRecentMembershipEventSql());
		if (mes.isEmpty()) {
			unprocessed = query(esbJdbc, MembershipEvent.class, 
					esbSql.getAllPotentialMembershipEventsSql());
		} else {
			unprocessed = query(esbJdbc, MembershipEvent.class,
					esbSql.getPotentialMembershipEventsNewerThanSql(), 
					"id", mes.get(0).getId());
		}
		return unprocessed;
	}
	
}
