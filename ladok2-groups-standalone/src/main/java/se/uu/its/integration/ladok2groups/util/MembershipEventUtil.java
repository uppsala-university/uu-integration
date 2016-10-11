package se.uu.its.integration.ladok2groups.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent.Type;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.PnrEvent;
import se.uu.its.integration.ladok2groups.l2dto.Reg;

public class MembershipEventUtil {
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HHmmss");
	public static final SimpleDateFormat DATE_FORMAT_HHMM = new SimpleDateFormat("yyyy-MM-dd HHmm");
	
	public static final Comparator<PotentialMembershipEvent> MEMBERSHIPEVENT_COMPARATOR = new Comparator<PotentialMembershipEvent>() {
		@Override
		public int compare(PotentialMembershipEvent me1, PotentialMembershipEvent me2) {
			return me1.getDate().compareTo(me2.getDate());
		}
	};
	
	public static void sort(List<? extends PotentialMembershipEvent> mes) {
		Collections.sort(mes, MEMBERSHIPEVENT_COMPARATOR);
	}
	
	public static List<PotentialMembershipEvent> filter(List<PotentialMembershipEvent> mes, Date from, Date to) {
		List<PotentialMembershipEvent> fmes = new ArrayList<PotentialMembershipEvent>();
		for (PotentialMembershipEvent me : mes) {
			if (!from.after(me.getDate()) && me.getDate().before(to)) { // from: incl, to: excl
				fmes.add(me);
			}
		}
		return fmes;
	}

	public static List<PotentialMembershipEvent> toMembershipEvents(List<? extends PnrEvent> es) {
		List<PotentialMembershipEvent> mes = new ArrayList<PotentialMembershipEvent>(es.size());
		for (PnrEvent e : es) {
			mes.add(toMembershipEvent(e));
		}
		return mes;
	}

	public static PotentialMembershipEvent toMembershipEvent(Reg r) {
		PotentialMembershipEvent ge = newMembershipEvent(r);
		ge.setMeType(Type.ADD);
		ge.setCourseCode(r.getKurskod());
		ge.setReportCode(r.getAnmkod());
		ge.setStartSemester(r.getStartter());
		ge.setSemester(r.getTermin());
		String[] o1o2 = r.getOrigin().split(":");
		ge.setOrigin(o1o2[0]);
		ge.setOrigin2(o1o2[1]);
		return ge;
	}

	public static PotentialMembershipEvent toMembershipEvent(BortReg r) {
		PotentialMembershipEvent ge = newMembershipEvent(r);
		ge.setMeType(Type.REMOVE);
		Map<String, String> urPost = parseUrPost(r.getUrpost());
		ge.setSemester(urPost.get("TERMIN"));
		ge.setCourseCode(r.getKurskod());
		ge.setOrigin("BORTREGK");
		ge.setOrigin2(r.getUrtabell());
		return ge;
	}

	public static PotentialMembershipEvent toMembershipEvent(InReg r) {
		PotentialMembershipEvent ge = newMembershipEvent(r);
		ge.setMeType(Type.REMOVE);
		ge.setOrigin(r.getOrigin());
		ge.setCourseCode(r.getKurskod());
		ge.setSemester(r.getTermin());
		return ge;
	}

	public static MembershipEvent toMembershipEvent(PotentialMembershipEvent pme) {
		MembershipEvent me = new MembershipEvent();
		me.setMeType(pme.getMeType());
		me.setDate(pme.getDate());
		me.setPnr(pme.getPnr());
		me.setCourseCode(pme.getCourseCode());
		me.setReportCode(pme.getReportCode());
		me.setStartSemester(pme.getStartSemester());
		me.setSemester(pme.getSemester());
		me.setOrigin(pme.getOrigin());
		me.setOrigin2(pme.getOrigin2());
		return me;
	}
	
	public static List<MembershipEvent> toMembershipEvents(PotentialMembershipEvent pme, List<Membership> ms) {
		List<MembershipEvent> mes = new ArrayList<MembershipEvent>(ms.size());
		for (Membership m : ms) {
			MembershipEvent me = toMembershipEvent(pme);
			me.setCourseCode(m.getCourseCode());
			me.setReportCode(m.getReportCode());
			me.setSemester(m.getSemester());
			me.setStartSemester(m.getStartSemester());
			mes.add(me);
		}
		return mes;
	}
	
	public static PotentialMembershipEvent toMembershipEvent(Avliden a) {
		PotentialMembershipEvent ge = newMembershipEvent(a);
		ge.setMeType(Type.REMOVE);
		ge.setOrigin("AVLIDEN");
		return ge;
	}
	
	public static List<Membership> toMemberships(List<PotentialMembershipEvent> mes) {
		List<Membership> ms = new ArrayList<Membership>(mes.size());
		for (PotentialMembershipEvent me : mes) {
			ms.add(toMembership(me));
		}
		return ms;
	}
	
	public static Membership toMembership(PotentialMembershipEvent me) {
		Membership m = new Membership();
		m.setDate(me.getDate());
		m.setPnr(me.getPnr());
		m.setCourseCode(me.getCourseCode());
		m.setReportCode(me.getReportCode());
		m.setStartSemester(me.getStartSemester());
		m.setSemester(me.getSemester());
		m.setOrigin(me.getOrigin());
		m.setOrigin2(me.getOrigin2());
		return m;
	}
	
	public static GroupEvent toGroupEvent(PotentialMembershipEvent pme) {
		GroupEvent me = new GroupEvent();
		me.setMeType(Type.ADDGROUP); // Only addgroup events so far
		me.setDate(pme.getDate());
		me.setPnr(null);
		me.setCourseCode(pme.getCourseCode());
		me.setReportCode(pme.getReportCode());
		me.setStartSemester(pme.getStartSemester());
		me.setSemester(null);
		me.setOrigin(pme.getOrigin());
		me.setOrigin2(pme.getOrigin2());
		return me;
	}
	
	public static Date parse(String formattedDate) {
		try {
			return DATE_FORMAT.parse(formattedDate);
		} catch (ParseException e) {
			try {
				return DATE_FORMAT_HHMM.parse(formattedDate);
			} catch (ParseException e1) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String format(Date date) {
		return DATE_FORMAT.format(date);
	}

	private static PotentialMembershipEvent toMembershipEvent(Object o) {
		if (o instanceof Reg) {
			return toMembershipEvent((Reg) o);
		} else if (o instanceof BortReg) {
			return toMembershipEvent((BortReg) o);
		} else if (o instanceof InReg) {
			return toMembershipEvent((InReg) o);
		} else if (o instanceof Avliden) {
			return toMembershipEvent((Avliden) o);
		} else {
			throw new RuntimeException("Can't convert " + o.getClass() + " to MembershipEvent");
		}
	}

	private static PotentialMembershipEvent newMembershipEvent(PnrEvent pe) {
		PotentialMembershipEvent me = new PotentialMembershipEvent();
		me.setPnr(pe.getPnr());
		// Absence of time -> set as late as possible to not miss any event
		String time = pe.getTid();
		if (time == null || time.equals("000000")) {
			time = "235959";
		}
		Date date = parse(pe.getDatum() + " " + time);
		me.setDate(date);
		return me;
	}
	
	private static Map<String, String> parseUrPost(String urPost) {
		 Map<String, String> m = new HashMap<String, String>();
		 String[] attrs = urPost.split(";");
		 for (String attr : attrs) {
			String[] nameAndVal = attr.split("=");
			if (nameAndVal.length == 2) {
				m.put(nameAndVal[0].trim().toUpperCase(), nameAndVal[1].trim());
			}
		 }
		 return m;
	}

}
