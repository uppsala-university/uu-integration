package se.uu.its.integration.ladok2groups.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
		if (Integer.class.equals(c) || Long.class.equals(c) || String.class.equals(c)
				|| Date.class.equals(c))  {
			return t.queryForList(sql, bpsps, c);
		} else {
			return t.query(sql, bpsps, BeanPropertyRowMapper.newInstance(c));
		}
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
	
	public static void updateN(DataSource ds, Log log, SqlAndValueObjs... savs) {
		try {
			List<PreparedStatementCreator> pscs = new ArrayList<PreparedStatementCreator>(savs.length);
			Connection con = ds.getConnection();
			for (SqlAndValueObjs sav : savs) {
				for (Object value : sav.getValues()) {
					PreparedStatementCreator psc = getPreparedStatementCreator(sav.getSql(), value);
					pscs.add(psc);
				}
			}
			updateN(con, pscs, log);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static PreparedStatement getPreparedStatement(Connection con, String sql, Object value) throws SQLException {
		PreparedStatementCreator psc = getPreparedStatementCreator(sql, value);
		return psc.createPreparedStatement(con);
	}

	static PreparedStatementCreator getPreparedStatementCreator(String sql, Object value) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(value);
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
		int[] paramTypes = NamedParameterUtils.buildSqlTypeArray(parsedSql, paramSource);
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, paramTypes);
		pscf.setReturnGeneratedKeys(true);
		return pscf.newPreparedStatementCreator(params);
	}

	static void updateN(Connection con, List<PreparedStatementCreator> pscs, Log log) throws SQLException {
		List<PreparedStatement> pss = new ArrayList<PreparedStatement>(pscs.size());
		try {
			con.setAutoCommit(false);
			for (PreparedStatementCreator psc : pscs) {
				PreparedStatement ps = psc.createPreparedStatement(con);
				pss.add(ps);
				ps.executeUpdate();
			}
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
			for (PreparedStatement ps : pss) {
				if (ps != null) { ps.close(); }
			}
			if (con != null) { con.setAutoCommit(true); con.close(); }
		}
	}


}
