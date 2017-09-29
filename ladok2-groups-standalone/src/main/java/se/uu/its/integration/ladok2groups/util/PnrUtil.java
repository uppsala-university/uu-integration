package se.uu.its.integration.ladok2groups.util;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

public class PnrUtil {

	public static String normalize(String pnr) {
		LocalDate today = LocalDate.now();
		if (StringUtils.isEmpty(pnr)) return null;
		String nPnr = pnr.replaceAll("[ -]", "");
		if (nPnr.length() == 10) {
			int cent = today.getYear() / 100;
			int yr = today.getYear() % 100;
			int pnrYr = Integer.parseInt(nPnr.substring(0, 2));
			// Interpret ages < 13 year old as 100 year older
			int pnrCent = (yr-pnrYr < 13) ? cent-1 : cent;
			nPnr = pnrCent + nPnr;
		}
		return nPnr;
	}
}
