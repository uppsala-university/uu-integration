package se.uu.its.integration.ladok2groups.sql;

import java.util.Map;

public class EsbGroupSql {
	
	Map<String, String> sql;

	public EsbGroupSql() {
		sql = YamlUtil.loadSql(this.getClass().getClassLoader(), "esb-groups-sql.yml");
	}
	
	
	// PotentialMembershipEvent

    public String getAllPotentialMembershipEventsSql() {
		return sql.get("esb.sql.allPotentialMembershipEvents");
	}
	
	public String getPotentialMembershipEventsNewerThanSql() {
		return sql.get("esb.sql.potentialMembershipEventsNewerThan");
	}

	public String getMostRecentPotentialMembershipEventSql() {
		return sql.get("esb.sql.mostRecentPotentialMembershipEvent");
	}

	public String getSaveNewPotentialMembershipEventSql() {
		return sql.get("esb.sql.saveNewPotentialMembershipEvent");
	}
	
	
	// MembershipEvent

	public String getMostRecentMembershipEventSql() {
		return sql.get("esb.sql.mostRecentMembershipEvent");
	}

	public String getSaveNewMembershipEventSql() {
		return sql.get("esb.sql.saveNewMembershipEvent");
	}

	
	// Membership
	
	public String getNumberOfMembershipsForCourseInstanceSql() {
		return sql.get("esb.sql.numberOfMembersForCourseInstance");
	}
	
	public String getMembershipsSql() {
		return sql.get("esb.sql.memberships");
	}
	
	public String getMembershipsByCourseCodeSql() {
		return sql.get("esb.sql.membershipsByCourseCode");
	}
	
	public String getMembershipsByReportCodeStartSemesterSql() {
		return sql.get("esb.sql.membershipsByReportCodeStartSemester");
	}
	
	public String getMembershipsByCourseCodeSemesterOrigin2Sql() {
		return sql.get("esb.sql.membershipsByCourseCodeSemesterOrigin2");
	}
	
	public String getSaveNewMembershipSql() {
		return sql.get("esb.sql.saveNewMembership");
	}

	@Deprecated
	public String getUpdateMembershipSql() {
		return sql.get("esb.sql.updateMembership");
	}

	public String getDeleteMembershipByIdSql() {
		return sql.get("esb.sql.deleteMembershipById");
	}

	public String getDeleteMembershipByCourseCodeSql() {
		return sql.get("esb.sql.deleteMembershipByCourseCode");
	}

	public String getDeleteMembershipsByCourseCodeSemesterOrigin2Sql() {
		return sql.get("esb.sql.deleteMembershipsByCourseCodeSemesterOrigin2");
	}
	
	public String getDeleteMembershipsSql() {
		return sql.get("esb.sql.deleteMemberships");
	}
	
	
	// AccMembership
	
	public String getAccMembershipsSql() {
		return sql.get("esb.sql.accMemberships");
	}
	
	public String getSaveNewAccMembershipSql() {
		return sql.get("esb.sql.saveNewAccMembership");
	}
	
	public String getDeleteAccMembershipsSql() {
		return sql.get("esb.sql.deleteAccMemberships");
	}
	
}
