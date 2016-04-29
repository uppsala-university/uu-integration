package se.uu.its.integration.ladok2groups.sql;

import java.util.Map;

public class EsbGroupSql {
	
	Map<String, String> sql;

	public EsbGroupSql() {
		sql = YamlUtil.loadSql(this.getClass().getClassLoader(), "esb-groups-sql.yml");
	}

    public String getAllPotentialMembershipEventsSql() {
		return sql.get("esb.sql.allPotentialMembershipEvents");
	}
	
	public String getPotentialMembershipEventsNewerThanSql() {
		return sql.get("esb.sql.potentialMembershipEventsNewerThan");
	}

	public String getMostRecentPotentialMembershipEventSql() {
		return sql.get("esb.sql.mostRecentPotentialMembershipEvent");
	}

	public String getMostRecentMembershipEventSql() {
		return sql.get("esb.sql.mostRecentMembershipEvent");
	}

	public String getSaveNewPotentialMembershipEventSql() {
		return sql.get("esb.sql.saveNewPotentialMembershipEvent");
	}

	public String getSaveNewMembershipEventSql() {
		return sql.get("esb.sql.saveNewMembershipEvent");
	}
	
	public String getUpdateMembershipSql() {
		return sql.get("esb.sql.updateMembership");
	}

	public String getDeleteMembershipSql() {
		return sql.get("esb.sql.deleteMembership");
	}

	public String getSaveNewMembershipSql() {
		return sql.get("esb.sql.saveNewMembership");
	}

	public String getFindMembershipsSql() {
		return sql.get("esb.sql.findMemberships");
	}
}
