package se.uu.its.integration.ladok2groups.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.util.StringUtils;

public class PnrUtil {
	
	public static Calendar cal = new GregorianCalendar();
	
	public static String normalize(String pnr) {
		if (StringUtils.isEmpty(pnr)) return null;
		String nPnr = pnr.replaceAll("[ -]", "");
		if (nPnr.length() == 10) {
			int cent = cal.get(Calendar.YEAR) / 100;
			int yr = cal.get(Calendar.YEAR) % 100;
			int pnrYr = Integer.parseInt(nPnr.substring(0, 2));
			// Interpret ages < 13 year old as 100 year older
			int pnrCent = (yr-pnrYr < 13) ? cent-1 : cent;
			nPnr = pnrCent + nPnr;
		}
		return nPnr;
	}
}
