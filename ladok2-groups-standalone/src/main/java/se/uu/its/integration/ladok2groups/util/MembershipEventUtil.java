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

import se.uu.its.integration.ladok2groups.dto.AccMembership;
import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent.Type;
import se.uu.its.integration.ladok2groups.l2dto.Antagen;
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
		long from_t = from.getTime(), to_t = to.getTime();
		for (PotentialMembershipEvent me : mes) {
			long me_t = me.getDate().getTime();
			if ((from_t < me_t) && (me_t <= to_t)) {
				fmes.add(me);
			}
		}
		return fmes;
	}

	public static List<PotentialMembershipEvent> toMembershipEvents(List<? extends PnrEvent> es) {
		List<PotentialMembershipEvent> mes = new ArrayList<PotentialMembershipEvent>(es.size());
		for (PnrEvent e : es) {
			PotentialMembershipEvent pme = null;
			if (e instanceof Reg) {
				pme = new PotentialMembershipEvent((Reg) e);
			} else if (e instanceof BortReg) {
				pme = new PotentialMembershipEvent((BortReg) e);
			} else if (e instanceof InReg) {
				pme = new PotentialMembershipEvent((InReg) e);
			} else if (e instanceof Avliden) {
				pme = new PotentialMembershipEvent((Avliden) e);
			}
			if (pme != null) {
				mes.add(pme);
			} else {
				throw new RuntimeException("Unknown PnrEvent: " + e);
			}
		}
		return mes;
	}
	
	public static List<MembershipEvent> toMembershipEvents(PotentialMembershipEvent pme, List<Membership> ms) {
		List<MembershipEvent> mes = new ArrayList<MembershipEvent>(ms.size());
		for (Membership m : ms) {
			mes.add(new MembershipEvent(pme, m));
		}
		return mes;
	}
	
	public static List<AccMembership> toAccMemberships(List<Antagen> as, Date date) {
		List<AccMembership> accs = new ArrayList<>();
		for (Antagen antagen : as) {
			accs.add(new AccMembership(antagen, date));
		}
		return accs;
	}

	public static List<MembershipEvent> toMembershipAddEvents(List<AccMembership> ms) {
		List<MembershipEvent> es = new ArrayList<>();
		for (AccMembership m : ms) {
			es.add(new MembershipEvent(m, Type.ForvantatDeltagandeSkapadEvent)); // Type.ADD
		}
		return es;
	}
	
	public static List<MembershipEvent> toMembershipRemoveEvents(List<AccMembership> ms) {
		List<MembershipEvent> es = new ArrayList<>();
		for (AccMembership m : ms) {
			es.add(new MembershipEvent(m, Type.AterbudEvent)); // Type.REMOVE
		}
		return es;
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
	
	public static Map<String, String> parseUrPost(String urPost) {
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
