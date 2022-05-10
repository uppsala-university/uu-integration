package se.uu.its.integration.esb.rest.services.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.impl.IdentityImpl;

import static org.junit.Assert.assertNotNull;

public class IdentityTest {

	private Log log = LogFactory.getLog(this.getClass());
	
//	@Test
	public void testRegisterAkkaAccountChange() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerAkkaAccountChange("akka-internal-ref-01", "akkaid", "19700101xxxx");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);
	}
	
	//@Test
	public void testRegisterNewAkkaAccount() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerNewAkkaAccount("akka-internal-ref-02", "akkaid", "19700101xxxx");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);
		
	}
	
//	@Test
	public void testRegisterDeletedAkkaAccount() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerDeletedAkkaAccount("akka-internal-ref-03", "akkaid", "19700101xxxx");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);
		
	}
	
//	@Test
	public void testRegisterAkkaChangedPersonNumber() throws Exception {

		Identity identity = new IdentityImpl();
		String id = identity.registerAkkaChangedPersonNumber("akka-internal-ref-04", "akkaid", "19700101xxxx", "19700101yyyy");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);		
		
	}
	
//	@Test
	public void testRegisterAkkaAccountRoleDeleted() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerAkkaAccountRoleDeleted("akka-internal-ref-05", "Role", "akkaid", "X11");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);		
		
	}
	
//	@Test
	public void testRegisterAkkaAccountRoleCreated() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerAkkaAccountRoleCreated("akka-internal-ref-06", "Role", "akkaid", "X11");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);		
		
	}
	
//	@Test
	public void testRegisterOrganizationDepartmentMappingCreated() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerOrganizationDepartmentMappingCreated("akka-internal-ref-07", "X11", "CodeX");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);		
		
	}

//	@Test
	public void testRegisterOrganizationDepartmentMappingDeleted() throws Exception {
		
		Identity identity = new IdentityImpl();
		String id = identity.registerOrganizationDepartmentMappingDeleted("akka-internal-ref-08", "X11", "CodeX");
		
		log.info("Event id: " + id);
		
		assertNotNull(id);		
		
	}	
	
}
