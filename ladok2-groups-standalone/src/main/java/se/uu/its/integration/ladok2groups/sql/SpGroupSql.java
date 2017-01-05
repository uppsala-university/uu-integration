package se.uu.its.integration.ladok2groups.sql;

import java.util.Map;

public class SpGroupSql {
	
	Map<String, String> sql;

	public SpGroupSql() {
		sql = YamlUtil.loadSql(this.getClass().getClassLoader(), "sp-groups-sql.yml");
	}

	
	// Registration membership events

    public String getRegEventsInIntervalSql() {
		return sql.get("sp.sql.regEventsInInterval");
	}
	
    public String getRegEventsFromIdToDateSql() {
		return sql.get("sp.sql.regEventsFromIdToDate");
	}
	
}
