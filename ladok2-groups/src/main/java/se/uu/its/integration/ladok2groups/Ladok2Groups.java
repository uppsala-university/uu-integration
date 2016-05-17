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
	
	public void updateGroupEvents() throws Exception {
		init(); // delayed init to let the ds resources have time to be injected
		List<MembershipEvent> pmes = query(esbJdbc, MembershipEvent.class,
				esbSql.getMostRecentPotentialMembershipEventSql());
		// Skip forward 1 second from most recent event to avoid duplicate events:
		Date start = pmes.isEmpty() ? getLadok2GroupEventStartTime() : new Date(
				pmes.get(0).getDate().getTime() + 1000);
		// Skip most recent events to make sure all events for this interval have arrived at Ladok:
		Date end = new Date(new Date().getTime() - 15000);
		
		// If interval is too large, batch updates to each day:
		Calendar from = new GregorianCalendar();
		from.setTimeInMillis(start.getTime());
		from.set(Calendar.MILLISECOND, 0);
		Calendar to = new GregorianCalendar();
		to.setTimeInMillis(end.getTime());
		to.set(Calendar.MILLISECOND, 0);
		int endYear = to.get(Calendar.YEAR);
		int endDay = to.get(Calendar.DAY_OF_YEAR);
		to.setTime(from.getTime());
		to.set(Calendar.HOUR_OF_DAY, 23);
		to.set(Calendar.MINUTE, 59);
		to.set(Calendar.SECOND, 59);

		// Update events for all days up to the last day
		while (to.after(from) && (to.get(Calendar.YEAR) < endYear || to.get(Calendar.DAY_OF_YEAR) < endDay)) {
			updatePotentialMembershipEvents(from.getTime(), to.getTime());
			updateMembershipEvents();
			// Increment interval one day:
			from.setTime(to.getTime());
			to.add(Calendar.DAY_OF_YEAR, 1);
		}
		// Update events for the last day
		updatePotentialMembershipEvents(from.getTime(), end);
		updateMembershipEvents();
	}
		
	int updatePotentialMembershipEvents(Date from, Date to) {
		List<MembershipEvent> mes =  getNewLadokMembershipEvents(from, to);
		update(esbJdbc, esbSql.getSaveNewPotentialMembershipEventSql(), mes);
		log.info("Updated potential membership events in interval [" 
				+ DATE_FORMAT.format(from) + ", " + DATE_FORMAT.format(to) + "): " + mes.size());
		return mes.size();
	}
	
	int updateMembershipEvents() {
		List<MembershipEvent> potentialMembershipEvents = getUnprocessedPotentialMembershipEvents();
		log.info("Number of unprocessed potential membership events: " + potentialMembershipEvents.size());
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
		String d_to = MembershipEventUtil.DATE_FORMAT.format(to);
		String[] dt_to = d_to.split(" ");
		String date_to = dt_to[0];
		String d_from = MembershipEventUtil.DATE_FORMAT.format(from);
		String[] dt_from = d_from.split(" ");
		String date_from = dt_from[0];
		String time_from = date_from.equals(date_to) ? dt_from[1] : "000000";
		List<Reg> reg = query(l2Jdbc, Reg.class, l2Sql.getRegSql(), 
				"datum_from", date_from, "datum_to", date_to, "tid", time_from);
		List<BortReg> bortreg = query(l2Jdbc, BortReg.class, l2Sql.getBortRegSql(), 
				"datum_from", date_from, "datum_to", date_to, "tid", time_from);
		List<InReg> inreg = query(l2Jdbc, InReg.class, l2Sql.getInRegSql(), 
				"datum_from", date_from, "datum_to", date_to, "tid", time_from);
		List<Avliden> avliden = query(l2Jdbc, Avliden.class, l2Sql.getAvlidenSql(), 
				"datum_from", date_from, "datum_to", date_to);
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
