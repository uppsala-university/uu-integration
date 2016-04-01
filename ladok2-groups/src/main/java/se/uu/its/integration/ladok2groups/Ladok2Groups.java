package se.uu.its.integration.ladok2groups;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import se.uu.its.integration.ladok2groups.dto.Avliden;
import se.uu.its.integration.ladok2groups.dto.BortReg;
import se.uu.its.integration.ladok2groups.dto.InReg;
import se.uu.its.integration.ladok2groups.dto.Reg;

public class Ladok2Groups {
	
	DataSource esbDs;
	DataSource ladok2ReadDs;

	EsbGroupSql esbSql = new EsbGroupSql();
	Ladok2GroupSql l2Sql = new Ladok2GroupSql();
	
	NamedParameterJdbcTemplate esbJdbc;
	NamedParameterJdbcTemplate l2Jdbc;
	
	public String updateGroupEvents() {
		return updateGroupEvents("2015-11-01", "000000");
	}

	String updateGroupEvents(String date, String time) {
		String rs = "";
		try {
			List<Reg> regs = query(l2Jdbc(), Reg.class, l2Sql.getRegSql(), "datum", date, "tid", time);
			List<BortReg> bortregs = query(l2Jdbc(), BortReg.class, l2Sql.getBortRegSql(), "datum", date, "tid", time);
			List<InReg> inregs = query(l2Jdbc(), InReg.class, l2Sql.getInRegSql(), "datum", date, "tid", time);
			List<Avliden> avlid = query(l2Jdbc(), Avliden.class, l2Sql.getAvlidenSql(), "datum", date);
			// List<Tst> es = query(getEsbJdbc(), Tst.class, esbSql.getTest());
			rs =  "Ladok 2 groups update : " 
			        + "\n - - - - - - - - - - "
			        + "\n ==> " + regs.size() + ", " + regs.get(0)
			        + "\n ==> " + inregs.size() + ", " + inregs.get(0)
			        + "\n ==> " + bortregs.size() + ", " + bortregs.get(0)
			        + "\n ==> " + avlid.size() + ", " + avlid.get(0)
			        + "\n - - - - - - - - - - ";
		} catch (Exception e) {
			rs = "Ladok 2 groups update : " +  e.getClass()  + " "  + e.getMessage();
		}
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
