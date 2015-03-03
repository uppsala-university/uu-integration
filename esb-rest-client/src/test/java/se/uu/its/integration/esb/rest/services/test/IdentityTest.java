package se.uu.its.integration.esb.rest.services.test;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.impl.IdentityImpl;

public class IdentityTest {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Test
	public void testRegisterAkkaAccountChange() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerAkkaAccountChange("akka-internal-ref-01", "marja992", "19720704xxxx");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);
	}
	
}
