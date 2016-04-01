package se.uu.its.integration.ladok2groups;

import java.util.Map;

public class EsbGroupSql {
	
	Map<String, String> sql;

	public EsbGroupSql() {
		sql = YamlUtil.loadSql(this.getClass().getClassLoader(), "esb-groups-sql.yml");
	}
	
	public String getTest() {
		return sql.get("esb.sql.test");
	}
}
