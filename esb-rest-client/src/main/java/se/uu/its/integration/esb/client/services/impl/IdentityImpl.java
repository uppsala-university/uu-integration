package se.uu.its.integration.esb.client.services.impl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.ServiceBase;
import se.uu.its.integration.model.common.UUEventDataProperty;
import se.uu.its.integration.model.events.PersonChangedEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.PersonEventData;
import se.uu.its.integration.model.identity.Employee;
import se.uu.its.integration.model.identity.Person;
import se.uu.its.integration.model.identity.Student;

public class IdentityImpl extends ServiceBase implements Identity {

	private String identityUrl = "/identity";
	private String personEventPath = "/event/person/";
	private static final String RESPONSE_TYPE_KATALOGINFORMATION = "application/vnd.ladok-kataloginformation";

	private static final String SYSTEM_MESSAGE_PRODUCER = "AKKA";
	
	
    public IdentityImpl() throws Exception {
		
    	super();

    	log.info("Initializing Identity service client.");
		String targetUrl = restBase + identityUrl;
		log.info("Using target URL: " + targetUrl);
		
		if (cb != null)
			webtarget = cb.build().target(targetUrl);
		else
			throw new Exception("Could not initialize service.");
	}

	
	@Override
	public String registerAkkaAccountChange(String akkaEventRefId, String akkaId, String personnummer) throws Exception {

		Person person = new Person(personnummer);
		
		PersonChangedEvent event = new PersonChangedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				"Ev104",
				person);

		log.info("Posting to path: " + personEventPath);
		PersonEvent eventResponse =
				webtarget.path(personEventPath).request().post(Entity.entity(event, "application/xml"), 
						PersonEvent.class);

		return eventResponse.getIdentifier();
		
    }

}
