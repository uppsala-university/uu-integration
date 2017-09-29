package se.uu.its.integration.ladok2groups.util;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(value = Parameterized.class)
public class PnrUtilTest {

	private String pnr;
	private String expected;

	public PnrUtilTest(String pnr, String expected) {
		this.pnr = pnr;
		this.expected = expected;
	}

	@Parameterized.Parameters(name = "{index}: normalizePnr({0}) = {1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"7304051234", "197304051234"},
				{"0704051234", "190704051234"},
				{"0504051234", "190504051234"},
				{"0404051234", "200404051234"}
		});
	}

	@Test
	public void normalizePnr() {
		assertThat(PnrUtil.normalize(pnr), is(expected));
	}


}