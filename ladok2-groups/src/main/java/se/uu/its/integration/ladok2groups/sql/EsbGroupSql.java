package se.uu.its.integration.ladok2groups.sql;

import java.util.Map;

public class EsbGroupSql {
	
	Map<String, String> sql;

	public EsbGroupSql() {
		sql = YamlUtil.loadSql(this.getClass().getClassLoader(), "esb-groups-sql.yml");
	}
	
	public String getMostRecentPotentialMembershipEventSql() {
		return sql.get("esb.sql.mostRecentPotentialMembershipEvent");
	}

	public String getMostRecentMembershipEventSql() {
		return sql.get("esb.sql.mostRecentMembershipEvent");
	}

	public String getSaveNewPotentialMembershipEventsSql() {
		return sql.get("esb.sql.saveNewPotentialMembershipEvents");
	}

	public String getSaveNewMembershipEventsSql() {
		return sql.get("esb.sql.saveNewMembershipEvents");
	}

}
