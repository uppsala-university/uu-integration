package se.uu.its.integration.ladok2groups.dto;


public class GroupEvent extends MembershipEvent {
	
	@Override
	public String toString() {
		return "GroupEvent [id=" + id + ", date="
				+ date + ", pnr=" + pnr + ", meType=" + meType
				+ ", courseCode=" + courseCode + ", reportCode=" + reportCode
				+ ", startSemester=" + startSemester + ", semester=" + semester
				+ ", origin=" + origin + ", origin2=" + origin2 + "]";
	}
	
}
