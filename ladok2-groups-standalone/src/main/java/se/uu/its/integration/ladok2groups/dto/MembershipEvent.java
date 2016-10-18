package se.uu.its.integration.ladok2groups.dto;

public class MembershipEvent extends PotentialMembershipEvent {

	String condition1;
	String condition2;
	String condition3;

	public MembershipEvent() {
	}
	
	public String getCondition1() {
		return condition1;
	}
	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}
	public String getCondition2() {
		return condition2;
	}
	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}
	public String getCondition3() {
		return condition3;
	}
	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}

	@Override
	public String toString() {
		return "MembershipEvent [condition1=" + condition1 + ", condition2="
				+ condition2 + ", condition3=" + condition3 + ", id=" + id
				+ ", date=" + date + ", pnr=" + pnr + ", meType=" + meType
				+ ", courseCode=" + courseCode + ", reportCode=" + reportCode
				+ ", startSemester=" + startSemester + ", semester=" + semester
				+ ", program=" + program + ", programOrientation="
				+ programOrientation + ", origin=" + origin + ", origin2="
				+ origin2 + "]";
	}
	
}
