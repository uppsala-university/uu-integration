package se.uu.its.integration.esb.rest.services.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.impl.IdentityImpl;

public class IdentityTest {

	@Test
	public void testRegisterAkkaAccountChange() throws Exception {
		
		Identity identity = new IdentityImpl();
		
		String id = identity.registerAkkaAccountChange("akka-internal-ref-01", "marja992", "19720704xxxx");
		
		System.out.println("Event id: " + id);
		
		assertNotNull(id);
	}
	
}
