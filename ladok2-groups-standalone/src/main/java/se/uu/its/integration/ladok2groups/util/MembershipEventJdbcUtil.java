package se.uu.its.integration.ladok2groups.util;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.getPreparedStatement;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByObj;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.updateN;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;

public class MembershipEventJdbcUtil {
	
	static EsbGroupSql esbSql = new EsbGroupSql();

	public static MembershipEvent saveMembershipAddEvent(DataSource ds,
			NamedParameterJdbcTemplate jt, Log log, PotentialMembershipEvent pme) {
		return saveMembershipAddEvent(ds, jt, log, pme, null);
	}

	public static MembershipEvent saveMembershipAddEvent(DataSource ds,
			NamedParameterJdbcTemplate jt, Log log,
			PotentialMembershipEvent pme, Membership obsoleteMembership) {
		List<SqlAndValueObjs> savs = new ArrayList<>();
		List<Integer> numRegs = queryByObj(jt, Integer.class,
				esbSql.getNumberOfMembershipsForCourseInstanceSql(), pme);
		if (numRegs.get(0) == 0) {
			// First membership for this group -> create an implicit new group event
			savs.add(sqlAndVals(esbSql.getSaveNewMembershipEventSql(), new GroupEvent(pme)));
		}
		// Save the new membership event and corresponding membership
		savs.add(sqlAndVals(esbSql.getSaveNewMembershipEventSql(), new MembershipEvent(pme)));
		savs.add(sqlAndVals(esbSql.getSaveNewMembershipSql(), new Membership(pme)));
		if (obsoleteMembership != null) {
			// Remove any obsolete memberships
			savs.add(sqlAndVals(esbSql.getDeleteMembershipByIdSql(), obsoleteMembership));
		}
		updateN(ds, log, savs);
		return null;
	}
	
	public static MembershipEvent saveMembershipAddEvent_DEPRECATED(DataSource ds,
			NamedParameterJdbcTemplate jt, Log log,
			PotentialMembershipEvent pme, Membership obsoleteMembership) {
		MembershipEvent me = null;
		List<PreparedStatement> pss = new ArrayList<PreparedStatement>();
		Connection con = null;
		try {
			List<Integer> numRegs = queryByObj(jt, Integer.class,
					esbSql.getNumberOfMembershipsForCourseInstanceSql(), pme);
			int nr = numRegs.get(0);
			con = ds.getConnection();
			con.setAutoCommit(false);
			if (nr == 0) {
				// First membership for this group -> create an implicit new group event
				GroupEvent ge = new GroupEvent(pme);
				PreparedStatement psge = getPreparedStatement(con, esbSql.getSaveNewMembershipEventSql(), ge);
				pss.add(psge);
				psge.execute();
			}
			// Save the new membership event
			me = new MembershipEvent(pme);
			PreparedStatement psme = getPreparedStatement(con, esbSql.getSaveNewMembershipEventSql(), me);
			pss.add(psme);
			psme.execute();
			// Save the corresponding membership (with the generated event id as foreign key)
			ResultSet genKeys = psme.getGeneratedKeys();
			genKeys.next();
			long genId = genKeys.getLong(1);
			me.setId(genId);
			Membership m = new Membership(me);
			PreparedStatement psm = getPreparedStatement(con, esbSql.getSaveNewMembershipSql(), m);
			pss.add(psm);
			psm.execute();
			if (obsoleteMembership != null) {
				// Remove any obsolete memberships
				PreparedStatement psd = getPreparedStatement(con,
						esbSql.getDeleteMembershipByIdSql(), obsoleteMembership);
				pss.add(psd);
				psd.execute();
			}
			con.commit();
			return me;
		} catch (Exception e ) {
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
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
			if (con != null) { 
				try {
					con.setAutoCommit(true);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				} 
				try {
					con.close(); 
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				} 
			}
		}
		return me;
	}
}
