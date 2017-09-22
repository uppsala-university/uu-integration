package se.uu.its.integration.ladok2groups.dto;


import org.junit.Assert;
import org.junit.Test;
import se.uu.its.integration.ladok2groups.util.MembershipEventUtil;

import java.time.LocalDateTime;

public class PotentialMembershipEventTest {

	LocalDateTime EXPECTED_TIME = LocalDateTime.parse("2010-08-01 030405", MembershipEventUtil.DATE_FORMAT);
	LocalDateTime END_OF_DAY = LocalDateTime.parse("2010-08-01 235959", MembershipEventUtil.DATE_FORMAT);

	@Test
	public void setDateWithCorrectInput() {
		PotentialMembershipEvent event = new PotentialMembershipEvent();

		event.setDate("2010-08-01", "030405");

		LocalDateTime date = event.getDate();

		Assert.assertEquals(EXPECTED_TIME, date);
	}

	@Test
	public void setDateWithNullItid() {
		PotentialMembershipEvent event = new PotentialMembershipEvent();

		event.setDate("2010-08-01", null);

		LocalDateTime date = event.getDate();

		Assert.assertEquals(END_OF_DAY, date);
	}

}