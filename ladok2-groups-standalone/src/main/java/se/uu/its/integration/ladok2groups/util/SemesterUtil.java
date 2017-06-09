package se.uu.its.integration.ladok2groups.util;

public class SemesterUtil {
	
	public static void main(String[] args) {
		System.out.println("" + getPreviousSemester("20170"));
	}

    /**
     * Returns the next semester after the specified semester. The semester
     * format is 'YYYYN', where YYYY is the year and N indicates if the semester
     * is a spring semester (= 1) or an autumn semester (= 2).
     *
     * Example: 20001 is the spring semester of the year 2000.
     */
    public static String getNextSemester(String semester) {
        int year = Integer.parseInt(semester.substring(0, 4));
        int term = Integer.parseInt(semester.substring(4, 5));
        if ((term < 1) || (term > 2)) {
            throw new RuntimeException("Incorrect current semester:" + semester);
        }
        if (term == 1) {
            term = 2;
        } else {
            year = year + 1;
            term = 1;
        }
        String nextSemester = (new Integer(year)).toString() + term;
        return nextSemester;
    }

    /**
     * Returns the previous semester before the specified semester. The semester
     * format is 'YYYYN', where YYYY is the year and N indicates if the semester
     * is a spring semester (= 1) or an autumn semester (= 2).
     *
     * Example: 20001 is the spring semester of the year 2000.
     */
    public static String getPreviousSemester(String semester) {
        int year = Integer.parseInt(semester.substring(0, 4));
        int term = Integer.parseInt(semester.substring(4, 5));
        if ((term < 1) || (term > 2)) {
            throw new RuntimeException("Incorrect current semester:" + semester);
        }
        if (term == 2) {
            term = 1;
        } else {
            year = year - 1;
            term = 2;
        }
        String previousSemester = (new Integer(year)).toString() + term;
        return previousSemester;
    }
    
}
