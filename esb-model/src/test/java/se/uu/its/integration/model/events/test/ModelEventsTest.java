package se.uu.its.integration.model.events.test;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;
import org.junit.Test;

import se.uu.its.integration.model.common.ModelUtils;
import se.uu.its.integration.model.common.UUEventDataProperty;
import se.uu.its.integration.model.events.AffiliationChangedEvent;
import se.uu.its.integration.model.events.AffiliationCreatedEvent;
import se.uu.its.integration.model.events.AffiliationDeletedEvent;
import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.AffiliationEventData;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingCreatedEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingDeletedEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonChangedEvent;
import se.uu.its.integration.model.events.PersonDeletedEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.PersonEventData;
import se.uu.its.integration.model.events.RoleCreatedEvent;
import se.uu.its.integration.model.events.RoleDeletedEvent;
import se.uu.its.integration.model.events.RoleEvent;
import se.uu.its.integration.model.identity.Affiliation;
import se.uu.its.integration.model.identity.Employee;
import se.uu.its.integration.model.identity.Organization;
import se.uu.its.integration.model.identity.OrganizationDepartmentMapping;
import se.uu.its.integration.model.identity.Person;
import se.uu.its.integration.model.identity.Role;
import se.uu.its.integration.model.identity.Student;


public class ModelEventsTest {

	private static final String SYSTEM_MESSAGE_PRODUCER = "AKKA";
	
	@Test 
	public void testToString() {

		Person person = new Person("198001010000", "Test", "Testsson");
		person.AddAffiliation(new Student("studstud"));
		person.AddAffiliation(new Employee("emplempl"));
		UUEventDataProperty property = new UUEventDataProperty("Personnumber", "197001010000", "198001010000");
		PersonEventData personEventData = new PersonEventData();
		personEventData.addEventPropertyData(property);	
		
		PersonChangedEvent event = new PersonChangedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				"Ev104",
				person, 
				personEventData);
		
		String xml = event.toString();
		System.out.println(xml);
		
		assertTrue("String representation is not valid xml.", !xml.equalsIgnoreCase(""));
	}
	
	@Test 
	public void testPersonChangedEventFullXml() throws JAXBException {
		
		Person person = new Person("198001010000", "Test", "Testsson");
		person.AddAffiliation(new Student("studstud"));
		person.AddAffiliation(new Employee("emplempl"));
		UUEventDataProperty property = new UUEventDataProperty("Personnumber", "197001010000", "198001010000");
		PersonEventData personEventData = new PersonEventData();
		personEventData.addEventPropertyData(property);	
		
		PersonChangedEvent event = new PersonChangedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				"Ev104",
				person, 
				personEventData);

		String xml = ModelUtils.getMarchalledObjectXml(PersonChangedEvent.class, event);
		
		System.out.println(xml);
		System.out.println();
		
		PersonEvent objectFromXml = (PersonEvent) ModelUtils.getUnmarchalledObject(PersonEvent.class, xml);	

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}

	@Test 
	public void testPersonDeletedEvent() throws JAXBException {
		
		Person person = new Person("199001011771");
		person.AddAffiliation(new Student("studstud"));
		person.AddAffiliation(new Employee("emplempl"));
		
		PersonDeletedEvent event = new PersonDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				"Ev104",
				person);

		String xml = ModelUtils.getMarchalledObjectXml(PersonDeletedEvent.class, event);
		
		System.out.println(xml);
		System.out.println();
		
		PersonEvent objectFromXml = (PersonEvent) ModelUtils.getUnmarchalledObject(PersonEvent.class, xml);	

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}	
	
	@Test
	public void testAffiliationCreatedEvent() throws JAXBException {
		
		AffiliationCreatedEvent event = new AffiliationCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				"Ev902",
				new Affiliation("emplempl"));		

		String xml = ModelUtils.getMarchalledObjectXml(AffiliationChangedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, xml);		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}	
	
	@Test
	public void testAffiliationDeletedEvent() throws JAXBException {
		
		AffiliationDeletedEvent event = new AffiliationDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				"Ev893",
				new Affiliation("testtest"));		

		String xml = ModelUtils.getMarchalledObjectXml(AffiliationDeletedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, xml);		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}	
	
	@Test
	public void testAffiliationChangedEventMinimalXml() throws JAXBException {
		
		AffiliationChangedEvent event = new AffiliationChangedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				"Ev765",
				new Affiliation("studstud"));		

		String xml = ModelUtils.getMarchalledObjectXml(AffiliationChangedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, xml);		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}

	@Test
	public void testAffiliationChangedEventIdentifier() throws JAXBException {
		
		UUEventDataProperty property = new UUEventDataProperty("Identifier", "studstud", "ztudzstud");
		AffiliationEventData affilationEventData = new AffiliationEventData();
		affilationEventData.addEventPropertyData(property);			
		
		AffiliationChangedEvent event = new AffiliationChangedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				"Ev765",
				new Affiliation("studstud"),
				affilationEventData);		

		String xml = ModelUtils.getMarchalledObjectXml(AffiliationChangedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, xml);		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}	
	
	@Test
	public void testRoleCreatedEventMinimalXml() throws JAXBException {

		RoleCreatedEvent event = new RoleCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				"Ev108",
				new Role(
						"Betygsättande lärare",
						new Employee("emplempl"),
						new Organization("X11")
						));
		
		String xml = ModelUtils.getMarchalledObjectXml(RoleCreatedEvent.class, event);
		System.out.println(xml);
		System.out.println();
		
		RoleEvent objectFromXml = (RoleEvent) ModelUtils.getUnmarchalledObject(RoleEvent.class, xml); 

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	
	}

	@Test
	public void testRoleDeletedEventMinimalXml() throws JAXBException {

		RoleDeletedEvent event = new RoleDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				"Ev305",
				new Role(
						"Betygsättande lärare",
						new Student("emplempl"),
						new Organization("X11")
						));
		
		String xml = ModelUtils.getMarchalledObjectXml(RoleDeletedEvent.class, event);
		System.out.println(xml);
		System.out.println();
		
		RoleEvent objectFromXml = (RoleEvent) ModelUtils.getUnmarchalledObject(RoleEvent.class, xml); 

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	
	}	
	
//	private Object getUnmarchalledObject(
//			@SuppressWarnings("rawtypes") Class c, String xml) throws JAXBException {
//
//		JAXBContext jaxbContext = JAXBContext.newInstance(c);
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		return jaxbUnmarshaller.unmarshal(new StringReader(xml));			
//	}
//
//	private String getMarchalledObjectXml(Class c, Object o) throws JAXBException {
//		JAXBContext jaxbContext = JAXBContext.newInstance(c);
//		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		
//		OutputStream objectXmlStream = new ByteArrayOutputStream();
//		jaxbMarshaller.marshal(o, objectXmlStream);
//		return objectXmlStream.toString();
//		
//	}

	@Test
	public void testAffiliationChangedEventFullXml() throws JAXBException {
		
		Person person = new Person("0000000000", "Test", "Testsson");
		Affiliation affiliation = new Affiliation("testtest", person);
		AffiliationEventData affiliationEventData = new AffiliationEventData();
		UUEventDataProperty property = new UUEventDataProperty("Identifier", "test1234", "testtest");
		affiliationEventData.addEventPropertyData(property);
		AffiliationChangedEvent event = new AffiliationChangedEvent(SYSTEM_MESSAGE_PRODUCER,
				"Ev425",
				affiliation, 
				affiliationEventData);

		String xml = ModelUtils.getMarchalledObjectXml(AffiliationChangedEvent.class, event);
		System.out.println(xml);
		System.out.println();
		
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, xml);

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));

		// curl -X POST -H "Content-Type: application/xml" -d @identity-event.xml http://localhost:8989/esb/rest/person/event/

		// GET:  https://localhost:8989/esb/rest/person/{id}
		
		// POST: http://localhost:8989/esb/rest/person/event/
		// POST: http://localhost:8989/esb/rest/affiliation/event/		
		// POST: http://localhost:8989/esb/rest/orgdepmapping/event/	
		// POST: http://localhost:8989/esb/rest/role/event/	

		
		// /esb/rest/{domain}/{object}/{type}
		// POST: http://localhost:8989/esb/rest/identity/event/affiliation
		// POST: http://localhost:8989/esb/rest/identity/event/person		
		// GET:  http://localhost:8989/esb/rest/identity/affiliation/{id}	
		// GET:  http://localhost:8989/esb/rest/identity/person/{id}		

		// POST: http://localhost:8989/esb/rest/studyadm/event/course/	
		// POST: http://localhost:8989/esb/rest/studyadm/event/program/		
		// GET:  http://localhost:8989/esb/rest/studyadm/course/{id}/			
		// GET:  http://localhost:8989/esb/rest/studyadm/program/{id}/courses/			
		
		// Tested:
		// curl -X POST -H "Content-Type: application/xml" -d @affiliation-created-event.xml http://localhost:8989/esb/rest/identity/event/affiliation/
		// curl -X POST -H "Content-Type: application/xml" -d @role-created-event.xml http://localhost:8989/esb/rest/identity/event/role/
		// curl -X POST -H "Content-Type: application/xml" -d @orgdepmap-created-event.xml http://localhost:8989/esb/rest/identity/event/orgdepmap/
		// curl -X POST -H "Content-Type: application/xml" -d @person-changed-event.xml http://localhost:8989/esb/rest/identity/event/person/
	}
	
	@Test
	public void testOrganizationDepartmentMappingCreatedEvent() throws JAXBException {
		
		OrganizationDepartmentMappingCreatedEvent event = new OrganizationDepartmentMappingCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				"Ev198",
				new OrganizationDepartmentMapping("X11", "CodeX"));
		
		String xml = ModelUtils.getMarchalledObjectXml(OrganizationDepartmentMappingCreatedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		OrganizationDepartmentMappingEvent objectFromXml = (OrganizationDepartmentMappingEvent) ModelUtils.getUnmarchalledObject(OrganizationDepartmentMappingCreatedEvent.class, xml); 

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
		
	}

	@Test
	public void testOrganizationDepartmentMappingDeletedEvent() throws JAXBException {
		
		OrganizationDepartmentMappingDeletedEvent event = new OrganizationDepartmentMappingDeletedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				"Ev198",
				new OrganizationDepartmentMapping("X11", "CodeX"));
		
		String xml = ModelUtils.getMarchalledObjectXml(OrganizationDepartmentMappingDeletedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		OrganizationDepartmentMappingEvent objectFromXml = (OrganizationDepartmentMappingEvent) ModelUtils.getUnmarchalledObject(OrganizationDepartmentMappingCreatedEvent.class, xml); 

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
		
	}	
	
}
