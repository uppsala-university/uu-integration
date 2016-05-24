package se.uu.its.integration.ladok2groups.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JdbcUtil {
	
	public static <T> List<T> query(NamedParameterJdbcTemplate t, Class<T> c, 
			String sql, Object... params) {
		Map<String, Object> m = new HashMap<String, Object>();
		for (int i = 0; i < params.length; i+=2) {
			m.put(params[i].toString(), params[i+1]);
		}
		return t.query(sql, m, BeanPropertyRowMapper.newInstance(c));
	}
		
	public static <T> List<T> queryByObj(NamedParameterJdbcTemplate t, Class<T> c, 
			String sql, Object queryObj) {
		BeanPropertySqlParameterSource bpsps = new BeanPropertySqlParameterSource(queryObj);
		return t.query(sql, bpsps, BeanPropertyRowMapper.newInstance(c));
	}
		
	public static int[] update(NamedParameterJdbcTemplate t, String sql, List<?> values) {
		BeanPropertySqlParameterSource[] bpsps = new BeanPropertySqlParameterSource[values.size()];
		int idx = 0;
		for (Object v : values) {
			bpsps[idx++] = new BeanPropertySqlParameterSource(v);
		}
	    return t.batchUpdate(sql, bpsps);
	}
	
	public static int update(NamedParameterJdbcTemplate t, String sql, Object value) {
		BeanPropertySqlParameterSource bpsps = new BeanPropertySqlParameterSource(value);
	    return t.update(sql, bpsps);
	}
	
	@Deprecated
	static String substituteParamsFromObj(String sql, Object value) {
		BeanPropertySqlParameterSource bpsps = new BeanPropertySqlParameterSource(value);
		return  NamedParameterUtils.substituteNamedParameters(sql, bpsps);
	}

	public static PreparedStatementCreator getPreparedStatementCreator(String sql, Object value) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(value);
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
		int[] paramTypes = NamedParameterUtils.buildSqlTypeArray(parsedSql, paramSource);
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, paramTypes);
		return pscf.newPreparedStatementCreator(params);
	}

	public static void update2(DataSource ds, String sql1, Object value1,
			String sql2, Object value2, Log log) {
		try {
			Connection con = ds.getConnection();
			PreparedStatementCreator psc1 = getPreparedStatementCreator(sql1, value1);
			PreparedStatementCreator psc2 = getPreparedStatementCreator(sql2, value2);
			update2(con, psc1, psc2, log);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static void update2(Connection con, PreparedStatementCreator psc1, PreparedStatementCreator psc2, Log log) throws SQLException {
		PreparedStatement s1 = null;
		PreparedStatement s2 = null;
		try {
			con.setAutoCommit(false);
			s1 = psc1.createPreparedStatement(con);
			s2 = psc2.createPreparedStatement(con);
			s1.executeUpdate();
			s2.executeUpdate();
			con.commit();
		} catch (SQLException e ) {
			log.error(e);
			if (con != null) {
				try {
					log.error("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					log.error(excep);
				}
			}
		} finally {
			if (s1 != null) { s1.close(); }
			if (s2 != null) { s2.close(); }
			if (con != null) { con.setAutoCommit(true); con.close(); }
		}
	}
	
}
