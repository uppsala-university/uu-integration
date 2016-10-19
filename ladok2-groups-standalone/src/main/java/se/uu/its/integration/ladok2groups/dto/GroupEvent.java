package se.uu.its.integration.ladok2groups.dto;


public class GroupEvent extends MembershipEvent {
	
	public GroupEvent() {
	}

	public GroupEvent(PotentialMembershipEvent pme) {
		setMeType(Type.ADDGROUP); // Only addgroup events so far
		setDate(pme.getDate());
		setPnr(null);
		setCourseCode(pme.getCourseCode());
		setReportCode(pme.getReportCode());
		setStartSemester(pme.getStartSemester());
		setSemester(null);
		setProgram(pme.getProgram());
		setProgramOrientation(pme.getProgramOrientation());
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
