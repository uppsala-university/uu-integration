package se.uu.its.integration.ladok2groups.util;

import java.util.Arrays;
import java.util.List;

/**
 * SQL and list of value objects intended to be used each as parameters in
 * separate executions of the SQL.
 */
public class SqlAndValueObjs {
	
	String sql;
	List<? extends Object> values;
	
	public static SqlAndValueObjs sqlAndVals(String sql, Object... values) {
		return new SqlAndValueObjs(sql, Arrays.asList(values));
	}
	
	public static SqlAndValueObjs sqlAndVals(String sql, List<? extends Object> values) {
		return new SqlAndValueObjs(sql, values);
	}
	
	public SqlAndValueObjs(String sql, List<? extends Object> values) {
		this.sql = sql;
		this.values = values;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<? extends Object> getValues() {
		return values;
	}

	public void setValues(List<? extends Object> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "SqlAndValues [sql=" + sql + ", values=" + values + "]";
	}
	
}
