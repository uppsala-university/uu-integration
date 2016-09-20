package se.uu.its.integration.ladok2groups.dto;


public class MembershipEvent extends PotentialMembershipEvent {
	
	Long pmeId;
	
	public Long getPmeId() {
		return pmeId;
	}
	public void setPmeId(Long pmeId) {
		this.pmeId = pmeId;
	}
	@Override
	public String toString() {
		return "MembershipEvent [pmeId=" + pmeId + ", id=" + id + ", date="
				+ date + ", pnr=" + pnr + ", meType=" + meType
				+ ", courseCode=" + courseCode + ", reportCode=" + reportCode
				+ ", startSemester=" + startSemester + ", semester=" + semester
				+ ", origin=" + origin + ", origin2=" + origin2 + "]";
	}
	
}
