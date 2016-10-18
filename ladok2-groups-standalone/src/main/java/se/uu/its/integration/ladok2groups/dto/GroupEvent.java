package se.uu.its.integration.ladok2groups.dto;


public class GroupEvent extends MembershipEvent {
	
	public GroupEvent() {
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
