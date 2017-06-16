package se.uu.its.integration.ladok2groups.sql;

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

	public String getNamnSql() {
		return sql.get("ladok.sql.namn");
	}
	
	public String getAntagenSql() {
		return sql.get("ladok.sql.antagen");
	}

	public String getTerminSql() {
		return sql.get("ladok.sql.termin");
	}

	public String getKurskodForKurstillfalleSql() {
		return sql.get("ladok.sql.kurskodForKurstillfalle");
	}
}
