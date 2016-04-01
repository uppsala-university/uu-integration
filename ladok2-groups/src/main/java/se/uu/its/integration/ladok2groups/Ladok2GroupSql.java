package se.uu.its.integration.ladok2groups;

import java.util.Map;

public class Ladok2GroupSql {

	Map<String, String> sql;

	public Ladok2GroupSql() {
		sql = YamlUtil.loadSql(this.getClass().getClassLoader(), "ladok-groups-sql.yml");
	}
	
	public String getRegSql() {
		return sql.get("ladok.sql.reg");
	}
	
	public String getBortRegSql() {
		return sql.get("ladok.sql.bortreg");
	}
	
	public String getInRegSql() {
		return sql.get("ladok.sql.inreg");
	}

	public String getAvlidenSql() {
		return sql.get("ladok.sql.avliden");
	}
	
}
