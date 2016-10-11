package se.uu.its.integration.ladok2groups.dto;

public class MembershipEvent extends PotentialMembershipEvent {

	@Override
	public String toString() {
		return "MembershipEvent [id=" + id + ", date=" + date + ", pnr=" + pnr
				+ ", meType=" + meType + ", courseCode=" + courseCode
				+ ", reportCode=" + reportCode + ", startSemester="
				+ startSemester + ", semester=" + semester + ", origin="
				+ origin + ", origin2=" + origin2 + "]";
	}
}
