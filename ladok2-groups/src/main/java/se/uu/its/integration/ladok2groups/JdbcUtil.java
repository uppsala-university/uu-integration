package se.uu.its.integration.ladok2groups;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
	
}
