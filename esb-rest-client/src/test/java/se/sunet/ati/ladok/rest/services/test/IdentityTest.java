package se.sunet.ati.ladok.rest.services.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.impl.IdentityImpl;

public class IdentityTest {

	@Test
	public void test() throws Exception {
		
		Identity identity = new IdentityImpl();
		
		System.out.println(identity);
		
		assertNotNull(new Object());
	}
	
}
