package se.uu.its.integration.ladok2groups.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import se.uu.its.integration.ladok2groups.conf.DbConf;
import se.uu.its.integration.ladok2groups.conf.EventProps;
import se.uu.its.integration.ladok2groups.conf.FlywayProps;
import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.dto.PotentialMembershipEvent;

import java.time.LocalDateTime;
import java.util.List;

import static se.uu.its.integration.ladok2groups.util.MembershipEventJdbcUtil.esbSql;
import static se.uu.its.integration.ladok2groups.util.SqlAndValueObjs.sqlAndVals;

@RunWith(SpringRunner.class)
@Import({DbConf.class, FlywayProps.class})
@EnableAutoConfiguration
@TestPropertySource(locations="classpath:test.application.properties")
public class JdbcUtilIntegrationTest {

	private LocalDateTime QUARTER_PAST_TEN = LocalDateTime.of(2018, 3, 4, 10, 15, 0);

	@Autowired
	@Qualifier("esb")
	NamedParameterJdbcTemplate esbJdbc;

	@Autowired
	@Qualifier("esb")
	PlatformTransactionManager esbTm;

	@Test
	public void addOneElementToMembershipsTable() {
		List<PotentialMembershipEvent> mesBefore = JdbcUtil.queryByParams(esbJdbc, PotentialMembershipEvent.class, esbSql.getMostRecentMembershipEventSql());

		GroupEvent event = new GroupEvent(QUARTER_PAST_TEN, "code1", "20181", "code2", "origin");
		JdbcUtil.executeStatementsInSameTx(esbJdbc, esbTm, sqlAndVals(esbSql.getSaveNewMembershipEventSql(), event));

		List<PotentialMembershipEvent> mesAfter = JdbcUtil.queryByParams(esbJdbc, PotentialMembershipEvent.class, esbSql.getMostRecentMembershipEventSql());

		Assert.assertEquals(mesBefore.size() + 1, mesAfter.size());
		Assert.assertTrue(mesAfter.stream().filter(s -> s.getDate().equals(QUARTER_PAST_TEN)).findFirst().isPresent());
	}

}