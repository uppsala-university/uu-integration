package se.uu.its.integration.ladok2groups.dto;

import java.util.Date;


public class GroupEvent extends MembershipEvent {
	
	public GroupEvent() {
	}

	public GroupEvent(Date date, String courseCode, 
			String startSemester, String reportCode, String origin) {
		setMeType(Type.KurstillfalleTillStatusEvent); // Type.ADDGROUP
		setDate(date);
		setCourseCode(courseCode);
		setStartSemester(startSemester);
		setReportCode(reportCode);
		setOrigin(origin);
	}
	
	public GroupEvent(PotentialMembershipEvent pme) {
		setMeType(Type.KurstillfalleTillStatusEvent); // Type.ADDGROUP
		setDate(pme.getDate());
		setCourseCode(pme.getCourseCode());
		setReportCode(pme.getReportCode());
		setStartSemester(pme.getStartSemester());
		setOrigin(pme.getOrigin());
		setOrigin2(pme.getOrigin2());
	}
	
	@Override
	public String toString() {
		return "GroupEvent [id=" + id + ", date=" + date + ", pnr=" + pnr
				+ ", meType=" + meType + ", courseCode=" + courseCode
				+ ", reportCode=" + reportCode + ", startSemester="
				+ startSemester + ", semester=" + semester + ", program="
				+ program + ", programOrientation=" + programOrientation + ", origin="
				+ origin + ", origin2=" + origin2 + "]";
	}
	
}
