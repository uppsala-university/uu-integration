package se.uu.its.integration.ladok2groups;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.Reg;

public class Ladok2Groups {
	
	DataSource esbDs;
	DataSource ladok2ReadDs;

	EsbGroupSql esbSql = new EsbGroupSql();
	Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	
	NamedParameterJdbcTemplate esbJdbc;
	NamedParameterJdbcTemplate l2Jdbc;

	public String updateGroupEvents() {
		try { // TODO: Catch errors or propagate upward? (Schedulers wait for a timeout)
			Date from = getLastUpdate();
			Date now = new Date();
			Date to = new Date(now.getTime() - 1000);
			return updateGroupEventsFromDate(from, to);
		} catch (Exception e) {
			return e.getCause() + " " + e.getMessage();
		}
	}
	
	public String updateGroupEventsFromDate(Date from, Date to) {
		List<MembershipEvent> mes =  getLadokMembershipEvents(from, to);
		updateMembershipEvents(mes);
		String rs = "- - - - - - - - - -" + "\n - " + mes.size();
		if (mes.size() > 0) {
			rs += ", " + mes; // mes.get(0);
		}
		rs += "\n - - - - - - - - - - ";
		return rs;
	}

	public DataSource getEsbDs() {
		return esbDs;
	}

	public void setEsbDs(DataSource esbDs) {
		this.esbDs = esbDs;
	}

	public DataSource getLadok2ReadDs() {
		return ladok2ReadDs;
	}

	public void setLadok2ReadDs(DataSource ladok2ReadDs) {
		this.ladok2ReadDs = ladok2ReadDs;
	}
	
	Date getLastUpdate() {
		try { // TODO
			return MembershipEventUtil.DATE_FORMAT.parse("2015-11-01 000000");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	List<MembershipEvent> getLadokMembershipEvents(Date from, Date to) {
		String fd = MembershipEventUtil.DATE_FORMAT.format(from);
		String[] dateAndTime = fd.split(" ");
		String date = dateAndTime[0];
		String time = dateAndTime[1];
		List<Reg> reg = query(l2Jdbc(), Reg.class, l2Sql.getRegSql(), "datum", date, "tid", time);
		List<BortReg> bortreg = query(l2Jdbc(), BortReg.class, l2Sql.getBortRegSql(), "datum", date, "tid", time);
		List<InReg> inreg = query(l2Jdbc(), InReg.class, l2Sql.getInRegSql(), "datum", date, "tid", time);
		List<Avliden> avliden = query(l2Jdbc(), Avliden.class, l2Sql.getAvlidenSql(), "datum", date);
		List<MembershipEvent> mes = new ArrayList<MembershipEvent>();
        mes.addAll(MembershipEventUtil.toMembershipEvents(reg));
        mes.addAll(MembershipEventUtil.toMembershipEvents(bortreg));
        mes.addAll(MembershipEventUtil.toMembershipEvents(inreg));
        mes.addAll(MembershipEventUtil.toMembershipEvents(avliden));
		MembershipEventUtil.sort(mes);
		mes = MembershipEventUtil.filter(mes, from, to);
		return mes;
	}
	
	int updateMembershipEvents(List<MembershipEvent> membershipEvents) {
		int numUpdated = 0;
		// TODO
		return numUpdated;
	}
	
	NamedParameterJdbcTemplate esbJdbc() {
		if (esbJdbc == null) {
			esbJdbc = new NamedParameterJdbcTemplate(getEsbDs());
		}
		return esbJdbc;
	}
	
	NamedParameterJdbcTemplate l2Jdbc() {
		if (l2Jdbc == null) {
			l2Jdbc = new NamedParameterJdbcTemplate(getLadok2ReadDs());
		}
		return l2Jdbc;
	}

	<T> List<T> query(NamedParameterJdbcTemplate t, Class<T> c, 
			String sql, Object... params) {
		Map<String, Object> m = new HashMap<String, Object>();
		for (int i = 0; i < params.length; i+=2) {
			m.put(params[i].toString(), params[i+1]);
		}
		return t.query(sql, m, BeanPropertyRowMapper.newInstance(c));
	}
		
	int update(NamedParameterJdbcTemplate t, String sql, Object value) {
	    return t.update(sql, new BeanPropertySqlParameterSource(value));
	}
		
	/*
	public List<AvReg> select() {
		return jdbc.query(ladokSql.getBortreg(), params(
				"idatum", "2015-11-01",
				"itid", "000000"),
				AVREG_RM);
	}*/

	/*
	@Autowired
	LadokSql ladokSql;
	
	@Autowired
	@Qualifier("uppdokOpen")
	NamedParameterJdbcTemplate jdbc;
	
	public void insert(Map<String, String> row) {
		// TODO
		// jdbc.update(ladokSql.getBortreg(), row);
	}

	public List<AvReg> select() {
		return jdbc.query(ladokSql.getBortreg(), params(
				"idatum", "2015-11-01",
				"itid", "000000"),
				AVREG_RM);
	}

    */
	 
	
}
