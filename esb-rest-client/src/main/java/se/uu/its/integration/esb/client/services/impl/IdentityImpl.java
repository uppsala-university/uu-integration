package se.uu.its.integration.esb.client.services.impl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.ServiceBase;
import se.uu.its.integration.model.common.UUEventDataProperty;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingCreatedEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingDeletedEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonChangedEvent;
import se.uu.its.integration.model.events.PersonCreatedEvent;
import se.uu.its.integration.model.events.PersonDeletedEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.PersonEventData;
import se.uu.its.integration.model.events.RoleCreatedEvent;
import se.uu.its.integration.model.events.RoleDeletedEvent;
import se.uu.its.integration.model.events.RoleEvent;
import se.uu.its.integration.model.events.UUEvent;
import se.uu.its.integration.model.identity.Employee;
import se.uu.its.integration.model.identity.Organization;
import se.uu.its.integration.model.identity.OrganizationDepartmentMapping;
import se.uu.its.integration.model.identity.Person;
import se.uu.its.integration.model.identity.Role;

public class IdentityImpl extends ServiceBase implements Identity {

	private static final String URL_IDENTITY = "/identity";
	private static final String PATH_PERSON_EVENT = "/event/person/";
	private static final String PATH_ROLE_EVENT = "/event/role/";
	private static final String PATH_ORGDEPMAP_EVENT = "/event/orgdepmap/";

	private static final String SYSTEM_MESSAGE_PRODUCER = "AKKA";
	
	
    public IdentityImpl() throws Exception {
		
    	super();

    	log.info("Initializing Identity service client.");
		String targetUrl = restBase + URL_IDENTITY;
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
				akkaEventRefId,
				person);

		log.info("Posting to path: " + PATH_PERSON_EVENT);

		Response response = webtarget.path(PATH_PERSON_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		UUEvent eventResponse = response.readEntity(PersonEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;    }


	@Override
	public String registerNewAkkaAccount(String akkaEventRefId, String akkaId, String personnummer) {

		Person person = new Person(personnummer);
		
		PersonCreatedEvent event = new PersonCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				akkaEventRefId,
				person);

		log.info("Posting to path: " + PATH_PERSON_EVENT);

		Response response = webtarget.path(PATH_PERSON_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		UUEvent eventResponse = response.readEntity(PersonEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;	}


	@Override
	public String registerDeletedAkkaAccount(String akkaEventRefId, String akkaId, String personnummer) {

		Person person = new Person(personnummer);
		
		PersonDeletedEvent event = new PersonDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				akkaEventRefId,
				person);

		log.info("Posting to path: " + PATH_PERSON_EVENT);

		Response response = webtarget.path(PATH_PERSON_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		UUEvent eventResponse = response.readEntity(PersonEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;
	}


	@Override
	public String registerAkkaChangedPersonNumber(String akkaEventRefId,
			String akkaKId, String personnummer, String previousPersonnumber) {

		Person person = new Person(personnummer);
		UUEventDataProperty property = new UUEventDataProperty("Personnumber", previousPersonnumber, personnummer);
		PersonEventData personEventData = new PersonEventData();
		personEventData.addEventPropertyData(property);	
		
		PersonChangedEvent event = new PersonChangedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				akkaEventRefId,
				person,
				personEventData);
				

		log.info("Posting to path: " + PATH_PERSON_EVENT);
		
		Response response = webtarget.path(PATH_PERSON_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		UUEvent eventResponse = response.readEntity(PersonEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;
	}


	@Override
	public String registerAkkaAccountRoleDeleted(String akkaEventRefId,
			String role, String accountId, String organizationId) {

		RoleCreatedEvent event = new RoleCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				akkaEventRefId,
				new Role(
						"Betygsättande lärare",
						new Employee("emplempl"),
						new Organization("X11")
						));

		log.info("Posting to path: " + PATH_ROLE_EVENT);
		
		Response response = webtarget.path(PATH_ROLE_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		RoleEvent eventResponse = response.readEntity(RoleEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;	
	}


	@Override
	public String registerAkkaAccountRoleCreated(String akkaEventRefId,
			String role, String accountId, String organizationId) {

		RoleDeletedEvent event = new RoleDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				akkaEventRefId,
				new Role(
						"Betygsättande lärare",
						new Employee("emplempl"),
						new Organization("X11")
						));

		log.info("Posting to path: " + PATH_ROLE_EVENT);

		Response response = webtarget.path(PATH_ROLE_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		RoleEvent eventResponse = response.readEntity(RoleEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;
	}


	@Override
	public String registerOrganizationDepartmentMappingCreated(String akkaEventRefId,
			String organizationId, String mappedId) {

		OrganizationDepartmentMappingCreatedEvent event = new OrganizationDepartmentMappingCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				akkaEventRefId,
				new OrganizationDepartmentMapping("X11", "CodeX"));	

		log.info("Posting to path: " + PATH_ORGDEPMAP_EVENT);

		Response response = webtarget.path(PATH_ORGDEPMAP_EVENT).request().post(Entity.entity(event, "application/xml"));
		log.info("Response status code: " + response.getStatus());
		
		OrganizationDepartmentMappingEvent eventResponse = response.readEntity(OrganizationDepartmentMappingEvent.class);

		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;
	}


	@Override
	public String registerOrganizationDepartmentMappingDeleted(String akkaEventRefId,
			String organizationId, String mappedId) {

		OrganizationDepartmentMappingDeletedEvent event = new OrganizationDepartmentMappingDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				akkaEventRefId,
				new OrganizationDepartmentMapping("X11", "CodeX"));	

		log.info("Posting to path: " + PATH_ORGDEPMAP_EVENT);

		Response response = webtarget.path(PATH_ORGDEPMAP_EVENT).request().post(Entity.entity(event, "application/xml")); 
		log.info("Response status code: " + response.getStatus());
		
		OrganizationDepartmentMappingEvent eventResponse = response.readEntity(OrganizationDepartmentMappingEvent.class);
		
		return response.getStatus() == 202 ? eventResponse.getIdentifier() : null;
	}

}
