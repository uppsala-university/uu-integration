package se.uu.its.integration.ladok2groups.util;

import static se.uu.its.integration.ladok2groups.util.JdbcUtil.executeStatementsInSameTx;
import static se.uu.its.integration.ladok2groups.util.JdbcUtil.queryByObj;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.dto.Membership;
import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;
import se.uu.its.integration.ladok2groups.sql.EsbGroupSql;

public class MembershipEventJdbcUtil {
	
	static EsbGroupSql esbSql = new EsbGroupSql();

	public static void saveMembershipAddEvent(
			NamedParameterJdbcTemplate jt, PlatformTransactionManager tm,
			PotentialMembershipEvent pme) {
		saveMembershipAddEvent(jt, tm, pme, null);
	}

	public static void saveMembershipAddEvent(
			NamedParameterJdbcTemplate jt, 
			PlatformTransactionManager tm,
			PotentialMembershipEvent pme, Membership obsoleteMembership) {
		List<SqlAndValueObjs> stms = new ArrayList<>();
		List<Integer> numRegs = queryByObj(jt, Integer.class,
				esbSql.getNumberOfMembershipsForCourseInstanceSql(), pme);
		if (numRegs.get(0) == 0) {
			// First membership for this group -> create an implicit new group event
			stms.add(sqlAndVals(esbSql.getSaveNewMembershipEventSql(), new GroupEvent(pme)));
		}
		// Save the new membership event and corresponding membership
		stms.add(sqlAndVals(esbSql.getSaveNewMembershipEventSql(), new MembershipEvent(pme)));
		stms.add(sqlAndVals(esbSql.getSaveNewMembershipSql(), new Membership(pme)));
		if (obsoleteMembership != null) {
			// Remove any obsolete memberships
			stms.add(sqlAndVals(esbSql.getDeleteMembershipByIdSql(), obsoleteMembership));
		}
		// updateN(ds, log, stms);
		executeStatementsInSameTx(jt, tm, stms);
	}
	
}
