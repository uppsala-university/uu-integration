package se.uu.its.integration.ladok2groups;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent.Type;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.PnrEvent;
import se.uu.its.integration.ladok2groups.l2dto.Reg;

public class MembershipEventUtil {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HHmmss");
	
	public static final Comparator<MembershipEvent> MEMBERSHIPEVENT_COMPARATOR = new Comparator<MembershipEvent>() {
		@Override
		public int compare(MembershipEvent me1, MembershipEvent me2) {
			return me1.getDate().compareTo(me2.getDate());
		}
	};
	
	public static void sort(List<MembershipEvent> mes) {
		Collections.sort(mes, MEMBERSHIPEVENT_COMPARATOR);
	}
	
	public static List<MembershipEvent> filter(List<MembershipEvent> mes, Date from, Date to) {
		List<MembershipEvent> fmes = new ArrayList<MembershipEvent>();
		for (MembershipEvent me : mes) {
			if (!from.after(me.getDate()) && me.getDate().before(to)) { // from: incl, to: excl
				fmes.add(me);
			}
		}
		return fmes;
	}

	public static List<MembershipEvent> toMembershipEvents(List<?> os) {
		List<MembershipEvent> mes = new ArrayList<MembershipEvent>(os.size());
		for (Object o : os) {
			mes.add(toMembershipEvent(o));
		}
		return mes;
	}

	public static MembershipEvent toMembershipEvent(Reg r) {
		MembershipEvent ge = newMembershipEvent(r);
		ge.setMeType(Type.ADD);
		ge.setCourseCode(r.getKurskod());
		ge.setReportCode(r.getAnmkod());
		ge.setSemester(r.getStartter());
		ge.setOrigin(r.getOrigin());
		return ge;
	}

	public static MembershipEvent toMembershipEvent(BortReg r) {
		MembershipEvent ge = newMembershipEvent(r);
		ge.setMeType(Type.REMOVE);
		Map<String, String> urPost = parseUrPost(r.getUrpost());
		ge.setSemester(urPost.get("TERMIN"));
		ge.setCourseCode(r.getKurskod());
		ge.setOrigin("BORTREGK");
		ge.setOrigin2(r.getUrtabell());
		return ge;
	}

	public static MembershipEvent toMembershipEvent(InReg r) {
		MembershipEvent ge = newMembershipEvent(r);
		ge.setMeType(Type.REMOVE);
		ge.setOrigin(r.getOrigin());
		ge.setCourseCode(r.getKurskod());
		ge.setSemester(r.getTermin());
		return ge;
	}

	public static MembershipEvent toMembershipEvent(Avliden a) {
		MembershipEvent ge = newMembershipEvent(a);
		ge.setMeType(Type.REMOVE);
		ge.setOrigin("avliden");
		return ge;
	}
	
	public static List<Membership> toMemberships(List<MembershipEvent> mes) {
		List<Membership> ms = new ArrayList<Membership>(mes.size());
		for (MembershipEvent me : mes) {
			ms.add(toMembership(me));
		}
		return ms;
	}
	
	public static Membership toMembership(MembershipEvent me) {
		Membership m = new Membership();
		m.setDate(me.getDate());
		m.setPnr(me.getPnr());
		m.setCourseCode(me.getCourseCode());
		m.setReportCode(me.getReportCode());
		m.setSemester(me.getSemester());
		m.setOrigin(me.getOrigin());
		m.setOrigin2(me.getOrigin2());
		return m;
	}
	
	public static Date getDate(String formattedDate) {
		try {
			return DATE_FORMAT.parse(formattedDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private static MembershipEvent toMembershipEvent(Object o) {
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

	private static MembershipEvent newMembershipEvent(PnrEvent pe) {
		MembershipEvent me = new MembershipEvent();
		me.setPnr(pe.getPnr());
		me.setDate(getDate(pe.getDatum(), pe.getTid()));
		return me;
	}
	
	private static Date getDate(String date, String time) {
		if (time == null) {
			time = "000000";
		}
		try {
			return DATE_FORMAT.parse(date + " " + time);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
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
