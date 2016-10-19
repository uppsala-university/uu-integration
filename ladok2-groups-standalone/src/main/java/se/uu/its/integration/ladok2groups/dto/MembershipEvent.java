package se.uu.its.integration.ladok2groups.dto;

public class MembershipEvent extends PotentialMembershipEvent {

	String condition1;
	String condition2;
	String condition3;

	public MembershipEvent() {
	}
	
	public MembershipEvent(PotentialMembershipEvent pme) {
		super(pme);
	}

	public MembershipEvent(PotentialMembershipEvent pme, Membership m) {
		this(pme);
		setCourseCode(m.getCourseCode());
		setReportCode(m.getReportCode());
		setSemester(m.getSemester());
		setStartSemester(m.getStartSemester());
	}
	
	public MembershipEvent(AccMembership m, Type t) {
		setMeType(t);
		setDate(m.getDate());
		setPnr(m.getPnr());
		setReportCode(m.getReportCode());
		setStartSemester(m.getSemester());
		setSemester(m.getSemester());
		setCourseCode(m.getCourseCode());
		setProgram(m.getProgram());
		setProgramOrientation(m.getProgramOrientation());
		setCondition1(m.getCondition1());
		setCondition2(m.getCondition2());
		setCondition3(m.getCondition3());
		setOrigin("LANTKURS");
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
