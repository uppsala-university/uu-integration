package se.uu.its.integration.ladok2groups;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.query;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByObj;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.saveMembershipAddEvent;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.update;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.updateN;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.filter;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.format;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.parse;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.sort;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.toMembershipEvents;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

import java.util.ArrayList;
import java.util.Arrays;
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
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.Reg;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;
import se.uu.its.integration.ladok2groups.sql.Ladok2GroupSql;
import se.uu.its.integration.ladok2groups.util.JdbcUtil;

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
		return format(ladok2GroupEventStart);
	}
	
	public void setLadok2GroupEventStart(String time) {
		ladok2GroupEventStart = parse(time);
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
		List<PotentialMembershipEvent> pmes = query(esbJdbc, PotentialMembershipEvent.class,
				esbSql.getMostRecentPotentialMembershipEventSql());
		// Skip forward 1 second from most recent event to avoid duplicate events:
		Date start = pmes.isEmpty() ? getLadok2GroupEventStartTime() : new Date(
				pmes.get(0).getDate().getTime() + 1000);
		// Skip most recent events to make sure all events for this interval have arrived at Ladok:
		Date end = new Date(new Date().getTime() - 15000);
		
		// Convert events interval to calendar days:
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

		// If interval is too large, batch updates to each day.
		// Update events for all days up to the last day:
		while (to.after(from) && (to.get(Calendar.YEAR) < endYear || to.get(Calendar.DAY_OF_YEAR) < endDay)) {
			updatePotentialMembershipEvents(from.getTime(), to.getTime());
			updateMembershipEvents();
			// Increment interval one day:
			from.setTime(to.getTime());
			to.add(Calendar.DAY_OF_YEAR, 1);
		}
		// Update events for the last day:
		updatePotentialMembershipEvents(from.getTime(), end);
		updateMembershipEvents();
	}
		
	int updatePotentialMembershipEvents(Date from, Date to) {
		List<PotentialMembershipEvent> mes =  getNewLadokMembershipEvents(from, to);
		update(esbJdbc, esbSql.getSaveNewPotentialMembershipEventSql(), mes);
		log.info("Updated potential membership events in interval [" 
				+ format(from) + ", " + format(to) + "): " + mes.size());
		return mes.size();
	}
	
	int updateMembershipEvents() {
		List<PotentialMembershipEvent> potentialMembershipEvents = getUnprocessedPotentialMembershipEvents();
		log.info("Number of unprocessed potential membership events: " + potentialMembershipEvents.size());
		for (PotentialMembershipEvent pme : potentialMembershipEvents) {
			String orig = pme.getOrigin();
			if (pme.getMeType() == PotentialMembershipEvent.Type.ADD) {
				if ("FFGKURS".equals(orig) || "OMKURS".equals(orig) || "UBINDRG".equals(orig)) {
					MembershipEvent me = saveMembershipAddEvent(esbDs, log, pme);
					log.info("New membership add event: " + pme + " generated event: " + me);
				} else if ("FORTKURS".equals(orig)) {
					List<Membership> ms = queryByObj(esbJdbc, Membership.class, 
							esbSql.getMembershipsByReportCodeStartSemesterSql(), pme);
					if (!ms.isEmpty()) {
						MembershipEvent me = saveMembershipAddEvent(esbDs, log, pme, ms.get(0));
						log.info("Updating existing membership event with FORTKURS event: "
								+ pme + ", generated event: " + me);
					} else {
						MembershipEvent me = JdbcUtil.saveMembershipAddEvent(esbDs, log, pme);
						log.info("New membership add event: " + pme + ", generated event: " + me);
					}
				} else {
					log.error("Unknown membership add event: " + pme);
				}
			} else {
				if (orig.startsWith("INREG")) {
					List<Membership> ms = queryByObj(esbJdbc, Membership.class, 
							esbSql.getMembershipsByCourseCodeSql(), pme);
					List<MembershipEvent> mes = toMembershipEvents(pme, ms);
					updateN(esbDs, log,
							sqlAndVals(esbSql.getSaveNewMembershipEventSql(), mes),
							sqlAndVals(esbSql.getDeleteMembershipByCourseCodeSql(), pme));
					log.info("New INREG* membership event: " + pme + ", generated events: " + mes);
				} else if ("BORTREGK".equals(orig)) {
					List<String> validOrig2s = Arrays.asList(new String[] {"OMKURS", "FORTKURS", "FFGKURS", "UBINDRG"});
					if (validOrig2s.contains(pme.getOrigin2())) {
						List<Membership> ms = queryByObj(esbJdbc, Membership.class, 
								esbSql.getMembershipsByCourseCodeSemesterOrigin2Sql(), pme);
						List<MembershipEvent> mes = toMembershipEvents(pme, ms);
						updateN(esbDs, log,
								sqlAndVals(esbSql.getSaveNewMembershipEventSql(), mes),
								sqlAndVals(esbSql.getDeleteMembershipsByCourseCodeSemesterOrigin2Sql(), pme));
						log.info("New BORTREGK membership event: " + pme + ", generated events: " + mes);
					} else if (pme.getOrigin2().startsWith("INREG")) {
						log.info("BORTREGK event with URTABELL = INREG*. Don't do anything: " + pme);
					} else {
						log.error("Unknown BORTREGK membership event: " + pme);
					}
				} else if ("AVLIDEN".equals(orig)) {
					List<Membership> ms = queryByObj(esbJdbc, Membership.class, 
							esbSql.getMembershipsSql(), pme);
					List<MembershipEvent> mes = toMembershipEvents(pme, ms);
					updateN(esbDs, log,
							sqlAndVals(esbSql.getSaveNewMembershipEventSql(), mes),
							sqlAndVals(esbSql.getDeleteMembershipsSql(), pme));
					log.info("New AVLIDEN membership event: " + pme + ", generated events: " + mes);
				} else {
					log.error("Unknown membership remove event: " + pme);
				}
			}
		}
		return potentialMembershipEvents.size();
	}
	
	List<PotentialMembershipEvent> getNewLadokMembershipEvents(Date from, Date to) {
		String d_to = format(to);
		String[] dt_to = d_to.split(" ");
		String date_to = dt_to[0];
		String d_from = format(from);
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
		List<PotentialMembershipEvent> mes = new ArrayList<PotentialMembershipEvent>();
        mes.addAll(toMembershipEvents(reg));
        mes.addAll(toMembershipEvents(bortreg));
        mes.addAll(toMembershipEvents(inreg));
        mes.addAll(toMembershipEvents(avliden));
		sort(mes);
		mes = filter(mes, from, to);
		return mes;
	}
	
	List<PotentialMembershipEvent> getUnprocessedPotentialMembershipEvents() {
		List<PotentialMembershipEvent> unprocessed;
		List<PotentialMembershipEvent> mes = query(esbJdbc, PotentialMembershipEvent.class,
				esbSql.getMostRecentMembershipEventSql());
		if (mes.isEmpty()) {
			unprocessed = query(esbJdbc, PotentialMembershipEvent.class, 
					esbSql.getAllPotentialMembershipEventsSql());
		} else {
			unprocessed = query(esbJdbc, PotentialMembershipEvent.class,
					esbSql.getPotentialMembershipEventsNewerThanSql(), 
					"id", mes.get(0).getId());
		}
		return unprocessed;
	}
	
}
