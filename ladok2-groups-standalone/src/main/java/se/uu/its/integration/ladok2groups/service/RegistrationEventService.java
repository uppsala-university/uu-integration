package se.uu.its.integration.ladok2groups.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import se.uu.its.integration.ladok2groups.conf.EventProps;
import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.Kurstillfalle;
import se.uu.its.integration.ladok2groups.l2dto.Namn;
import se.uu.its.integration.ladok2groups.l2dto.Reg;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;
import se.uu.its.integration.ladok2groups.sql.Ladok2GroupSql;
import se.uu.its.integration.ladok2groups.sql.SpGroupSql;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.executeStatementsInSameTx;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByObj;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByParams;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.update;
import static se.uu.its.integration.ladok2groups.util.MembershipEventJdbcUtil.saveMembershipAddEvent;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.filter;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.format;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.parse;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.sort;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.toMembershipEvents;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

@Service
public class RegistrationEventService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationEventService.class);

	private Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	private SpGroupSql spSql = new SpGroupSql();
	private EsbGroupSql esbSql = new EsbGroupSql();

	@Autowired
	@Qualifier("ladok2read")
	private NamedParameterJdbcTemplate l2Jdbc;

	@Autowired
	@Qualifier("sp")
	private NamedParameterJdbcTemplate spJdbc;

	@Autowired
	@Qualifier("esb")
	private NamedParameterJdbcTemplate esbJdbc;

	@Autowired
	@Qualifier("esb")
	private PlatformTransactionManager esbTm;

	@Autowired
	private EventProps eventProps;

	@Autowired
	private ScheduledJobSynchronizer scheduledJobSynchronizer;

	@Scheduled(fixedDelayString = "${events.regUpdateDelay}")
	public void updateEvents() {
		scheduledJobSynchronizer.executeExclusively(() -> {
			logger.info("Update event job started");
			long start = System.currentTimeMillis();
			batchUpdatesForEachDay();
			logger.info("Update event job finished, duration: {} ms", System.currentTimeMillis() - start);
		});
	}

	public void batchUpdatesForEachDay() {
		// Get start date:
		List<PotentialMembershipEvent> pmes = queryByParams(esbJdbc, PotentialMembershipEvent.class,
				esbSql.getMostRecentPotentialMembershipEventFromLadokSql());
		LocalDateTime start;
		if (pmes.isEmpty()) {
			// Start one second before because we exclude the start time
			start = parse(eventProps.getRegUpdateStart()).minusSeconds(1);
		} else {
			start = pmes.get(0).getDate();
		}

		// Skip most recent events to make sure all events for this interval have arrived at Ladok:
		LocalDateTime end = LocalDateTime.now().minusSeconds(15).truncatedTo(ChronoUnit.SECONDS);
		int endYear = end.getYear();
		int endDay = end.getDayOfYear();

		// Convert current event interval to calendar days:
		LocalDateTime from = start;
		LocalDateTime to = from.toLocalDate().atTime(23, 59, 59);

		// Update events for all days up to (but not including) the last day:
		while (to.getDayOfYear() < endDay || to.getYear() < endYear) {
			updatePotentialMembershipEvents(from, to);
			updateMembershipEvents();
			// Increment interval one day:
			from = to;
			to = from.plusDays(1);
		}

		// Update events for the last day:
		updatePotentialMembershipEvents(from, end);
		updateMembershipEvents();
	}

	int updatePotentialMembershipEvents(LocalDateTime from, LocalDateTime to) {
		long start = System.currentTimeMillis();
		List<PotentialMembershipEvent> mes = new ArrayList<>();
		mes.addAll(getNewSpMembershipEvents(from, to));
		long spRead = System.currentTimeMillis();
		mes.addAll(getNewLadokMembershipEvents(from, to));
		long l2Read = System.currentTimeMillis();
		sort(mes);
		update(esbJdbc, esbSql.getSaveNewPotentialMembershipEventSql(), mes);
		logger.info("Updated potential membership events in interval [{}, {}]: in {} ms (SP read: {} ms, L2 read: {} ms, save: {} ms)",
				format(from), format(to), mes.size(), System.currentTimeMillis() - start, spRead - start, System.currentTimeMillis() - l2Read);

		return mes.size();
	}

	int updateMembershipEvents() {
		long start = System.currentTimeMillis();
		List<PotentialMembershipEvent> potentialMembershipEvents = getUnprocessedPotentialMembershipEvents();
		long unprocessedRead = System.currentTimeMillis();
		logger.info("Number of unprocessed potential membership events: {} (read: {} ms)", potentialMembershipEvents.size(), unprocessedRead - start);
		for (PotentialMembershipEvent pme : potentialMembershipEvents) {
			String orig = pme.getOrigin();
			long s = System.currentTimeMillis();
			//if (pme.getMeType() == PotentialMembershipEvent.Type.ADD) {
			if (PotentialMembershipEvent.MEMBERSHIP_ADD_TYPES.contains(pme.getMeType())) {
				if ("FFGKURS".equals(orig) || "OMKURS".equals(orig) || "UBINDRG".equals(orig)
						|| "SP".equals(orig)) {
					saveMembershipAddEvent(esbJdbc, esbTm, pme);
					logger.info("New membership add event: {} (save: {} ms)", pme, System.currentTimeMillis() - s);
				} else if ("FORTKURS".equals(orig)) {
					List<Membership> ms = queryByObj(esbJdbc, Membership.class,
							esbSql.getMembershipsByReportCodeStartSemesterSql(), pme);
					long r = System.currentTimeMillis();
					if (!ms.isEmpty()) {
						saveMembershipAddEvent(esbJdbc, esbTm, pme, ms.get(0));
						logger.info("Updating existing membership event with FORTKURS event: {} (read: {} ms, save: {} ms)", pme, r - s, System.currentTimeMillis() - r);
					} else {
						saveMembershipAddEvent(esbJdbc, esbTm, pme);
						logger.info("New membership add event: {} (read: {} ms, save: {} ms)", pme, r - s, System.currentTimeMillis() - r);
					}
				} else {
					logger.error("Unknown membership add event: {}", pme);
				}
			} else {
				if (orig.startsWith("INREG")) {
					List<Membership> ms = queryByObj(esbJdbc, Membership.class,
							esbSql.getMembershipsByCourseCodeSql(), pme);
					long r = System.currentTimeMillis();
					List<MembershipEvent> mes = toMembershipEvents(pme, ms);
					executeStatementsInSameTx(esbJdbc, esbTm,
							sqlAndVals(esbSql.getSaveNewMembershipEventSql(), mes),
							sqlAndVals(esbSql.getDeleteMembershipByCourseCodeSql(), pme));
					logger.info("New INREG* membership event: {}, generated events: {} (read: {} ms, save: {} ms)", pme, mes, r - s, System.currentTimeMillis() - r);

				} else if ("BORTREGK".equals(orig)) {
					List<String> validOrig2s = Arrays.asList(new String[]{"OMKURS", "FORTKURS", "FFGKURS", "UBINDRG"});
					if (validOrig2s.contains(pme.getOrigin2())) {
						List<Membership> ms = queryByObj(esbJdbc, Membership.class,
								esbSql.getMembershipsByCourseCodeSemesterOrigin2Sql(), pme);
						long r = System.currentTimeMillis();
						List<MembershipEvent> mes = toMembershipEvents(pme, ms);
						executeStatementsInSameTx(esbJdbc, esbTm,
								sqlAndVals(esbSql.getSaveNewMembershipEventSql(), mes),
								sqlAndVals(esbSql.getDeleteMembershipsByCourseCodeSemesterOrigin2Sql(), pme));
						logger.info("New BORTREGK membership event: {}, generated events: {} (read: {} ms, save: {} ms)", pme, mes, r - s, System.currentTimeMillis() - r);
					} else if (pme.getOrigin2().startsWith("INREG")) {
						logger.info("BORTREGK event with URTABELL = INREG*. Don't do anything: {}", pme);
					} else {
						logger.error("Unknown BORTREGK membership event: {}", pme);
					}
				} else if ("NAMN".equals(orig)) {
					// NAMN events can be either a StudentAvlidenmarkeringEvent
					// or a KontaktuppgifterEvent
					/* DEPRECATED: Generate remove events for all current memberships.
					List<Membership> ms = queryByObj(esbJdbc, Membership.class, 
							esbSql.getMembershipsSql(), pme);
					long r = System.currentTimeMillis();
					List<MembershipEvent> mes = toMembershipEvents(pme, ms);
					executeStatementsInSameTx(esbJdbc, esbTm,
							sqlAndVals(esbSql.getSaveNewMembershipEventSql(), mes),
							sqlAndVals(esbSql.getDeleteMembershipsSql(), pme));
					log.info("New AVLIDEN membership event: " + pme + ", generated events: " + mes
							 + " (read: " + (r - s) + " ms, "
							 + "save: " + (System.currentTimeMillis() - r) + " ms)");
							*/
					MembershipEvent me = new MembershipEvent(pme);
					update(esbJdbc, esbSql.getSaveNewMembershipEventSql(), me);
					logger.info("New NAMN membership event: {}, generated event: {} save: {} ms)", pme, me, System.currentTimeMillis() - s);
				} else {
					logger.error("Unknown membership remove event: {}", pme);
				}
			}
		}
		logger.info("Membership events updated: {} (duration: {} ms)", potentialMembershipEvents.size(), System.currentTimeMillis() - start);
		return potentialMembershipEvents.size();
	}

	List<PotentialMembershipEvent> getNewSpMembershipEvents(LocalDateTime from, LocalDateTime to) {
		List<PotentialMembershipEvent> recentSpMes = queryByParams(esbJdbc, PotentialMembershipEvent.class,
				esbSql.getMostRecentPotentialMembershipEventFromSpSql());
		LocalDateTime spFrom = recentSpMes.size() > 0 ? recentSpMes.get(0).getDate() : from;
		List<PotentialMembershipEvent> pmes = queryByParams(spJdbc,
				PotentialMembershipEvent.class,
				spSql.getRegEventsInIntervalSql(), "from", Timestamp.valueOf(spFrom), "to", Timestamp.valueOf(to));
		complementCourseCodesIfNecessary(pmes);
		return pmes;
	}

	void complementCourseCodesIfNecessary(List<PotentialMembershipEvent> pmes) {
		// TODO: Batch all lookups
		for (PotentialMembershipEvent pme : pmes) {
			if (pme.getCourseCode() == null || "".equals(pme.getCourseCode())) {
				List<Kurstillfalle> ktf = queryByParams(l2Jdbc, Kurstillfalle.class,
						l2Sql.getKurskodForKurstillfalleSql(),
						"starttermin", pme.getStartSemester(),
						"anmkod", pme.getReportCode());
				if (ktf.size() == 1) {
					pme.setCourseCode(ktf.get(0).getKurskod());
					logger.debug("Complementing course code from Uppdok for SP event: {}", pme);
				} else {
					logger.error("Can't find course code in Uppdok for SP event: {}", pme);
				}
			}
		}
	}

	List<PotentialMembershipEvent> getNewLadokMembershipEvents(LocalDateTime from, LocalDateTime to) {
		String d_to = format(to);
		String[] dt_to = d_to.split(" ");
		String date_to = dt_to[0];
		String d_from = format(from);
		String[] dt_from = d_from.split(" ");
		String date_from = dt_from[0];
		String time_from = date_from.equals(date_to) ? dt_from[1] : "000000";
		List<Reg> reg = queryByParams(l2Jdbc, Reg.class, l2Sql.getRegSql(),
				"datum_from", date_from, "datum_to", date_to, "tid", time_from);
		List<BortReg> bortreg = queryByParams(l2Jdbc, BortReg.class, l2Sql.getBortRegSql(),
				"datum_from", date_from, "datum_to", date_to, "tid", time_from);
		List<InReg> inreg = queryByParams(l2Jdbc, InReg.class, l2Sql.getInRegSql(),
				"datum_from", date_from, "datum_to", date_to, "tid", time_from);
		List<Namn> namn = queryByParams(l2Jdbc, Namn.class, l2Sql.getNamnSql(),
				"datum_from", date_from, "datum_to", date_to);
		List<PotentialMembershipEvent> mes = new ArrayList<PotentialMembershipEvent>();
		mes.addAll(toMembershipEvents(reg));
		mes.addAll(toMembershipEvents(bortreg));
		mes.addAll(toMembershipEvents(inreg));
		mes.addAll(toMembershipEvents(namn));
		sort(mes);
		mes = filter(mes, from, to);
		return mes;
	}

	List<PotentialMembershipEvent> getUnprocessedPotentialMembershipEvents() {
		List<PotentialMembershipEvent> unprocessed;
		List<PotentialMembershipEvent> mes = queryByParams(esbJdbc, PotentialMembershipEvent.class,
				esbSql.getMostRecentMembershipEventSql());
		if (mes.isEmpty()) {
			unprocessed = queryByParams(esbJdbc, PotentialMembershipEvent.class,
					esbSql.getAllPotentialMembershipEventsSql());
		} else {
			unprocessed = queryByParams(esbJdbc, PotentialMembershipEvent.class,
					esbSql.getPotentialMembershipEventsNewerThanSql(),
					"date", mes.get(0).getDate());
			logger.info("Finding unprocessed potential membership events with id newer than {}", mes.get(0).getId());
		}
		return unprocessed;
	}

}
