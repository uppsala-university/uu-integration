package se.uu.its.integration.ladok2groups.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class JdbcUtil {
	
	public static <T> List<T> queryByParams(NamedParameterJdbcTemplate t, Class<T> c, 
			String sql, Object... params) {
		Map<String, Object> m = new HashMap<String, Object>();
		for (int i = 0; i < params.length; i+=2) {
			m.put(params[i].toString(), params[i+1]);
		}
		if (Integer.class.equals(c) || Long.class.equals(c) || String.class.equals(c)
				|| Date.class.equals(c))  {
			return t.queryForList(sql, m, c);
		} else {
			return t.query(sql, m, BeanPropertyRowMapper.newInstance(c));
		}
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
	
	public static void executeStatementsInSameTx(NamedParameterJdbcTemplate jt, 
			PlatformTransactionManager tm, SqlAndValueObjs... stms) {
		executeStatementsInSameTx(jt, tm, Arrays.asList(stms));
	}
	
	public static void executeStatementsInSameTx(NamedParameterJdbcTemplate jt, 
			PlatformTransactionManager tm, List<SqlAndValueObjs> stms) {
		TransactionStatus txStat = tm.getTransaction(new DefaultTransactionDefinition());
		try {
			for (SqlAndValueObjs st : stms) {
				String sql = st.getSql();
				List<BeanPropertySqlParameterSource> bpsps = new ArrayList<>();
				for (Object v : st.getValues()) {
					bpsps.add(new BeanPropertySqlParameterSource(v));
				}
				jt.batchUpdate(sql,(BeanPropertySqlParameterSource[]) bpsps
						.toArray(new BeanPropertySqlParameterSource[bpsps.size()]));
			}
			tm.commit(txStat);
		} catch (Exception e) {
			tm.rollback(txStat);
			throw e;
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

	/*
	public static void updateN(DataSource ds, Log log, SqlAndValueObjs... stms) {
		updateN(ds, log, Arrays.asList(stms));
	}

	public static void updateN(DataSource ds, Log log, List<SqlAndValueObjs> stms) {
		try {
			List<PreparedStatementCreator> pscs = new ArrayList<PreparedStatementCreator>(stms.size());
			Connection con = ds.getConnection();
			for (SqlAndValueObjs sav : stms) {
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
	*/

}
