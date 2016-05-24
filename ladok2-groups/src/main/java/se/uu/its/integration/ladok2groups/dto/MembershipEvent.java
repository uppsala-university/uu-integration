package se.uu.its.integration.ladok2groups.dto;


public class MembershipEvent extends PotentialMembershipEvent {
	
	Long pmeid;
	
	public Long getPmeid() {
		return pmeid;
	}
	public void setPmeid(Long pmeid) {
		this.pmeid = pmeid;
	}
	
	@Override
	public String toString() {
		return "MembershipEvent [id=" + id + ", pmeid=" + pmeid + ", date="
				+ date + ", pnr=" + pnr + ", meType=" + meType
				+ ", courseCode=" + courseCode + ", reportCode=" + reportCode
				+ ", semester=" + semester + ", origin=" + origin
				+ ", origin2=" + origin2 + "]";
	}
	
}
