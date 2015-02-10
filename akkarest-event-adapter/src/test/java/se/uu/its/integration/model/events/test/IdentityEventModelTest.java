package se.uu.its.integration.model.events.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import se.uu.its.integration.model.common.UUEventDataProperty;
import se.uu.its.integration.model.events.AffiliationChangedEvent;
import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.AffiliationEventData;
import se.uu.its.integration.model.events.PersonChangedEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.PersonEventData;
import se.uu.its.integration.model.events.RoleCreatedEvent;
import se.uu.its.integration.model.events.RoleEvent;
import se.uu.its.integration.model.identity.Affiliation;
import se.uu.its.integration.model.identity.Employee;
import se.uu.its.integration.model.identity.Organization;
import se.uu.its.integration.model.identity.Person;
import se.uu.its.integration.model.identity.Role;
import se.uu.its.integration.model.identity.Student;


public class IdentityEventModelTest {

	private static final String SYSTEM_MESSAGE_PRODUCER = "AKKA";
	
	@Test 
	public void testPersonChangedEventFullXml() throws JAXBException {
		
		Person person = new Person("0000000000", "Test", "Testsson");
		person.AddAffiliation(new Student("0000000000"));
		person.AddAffiliation(new Employee("1111111111"));
		UUEventDataProperty property = new UUEventDataProperty("Firstname", "Tezt", "Test");
		PersonEventData personEventData = new PersonEventData();
		personEventData.addEventPropertyData(property);	
		
		PersonChangedEvent event = new PersonChangedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				person, 
				personEventData);

		String xml = getMarchalledObjectXml(PersonChangedEvent.class, event);
		
		System.out.println(xml);
		System.out.println();
		
		PersonEvent objectFromXml = (PersonEvent) getUnmarchalledObject(PersonEvent.class, xml);	

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}
	
	@Test
	public void testAffiliationChangedEventMinimalXml() throws JAXBException {
		
		AffiliationChangedEvent event = new AffiliationChangedEvent(
				SYSTEM_MESSAGE_PRODUCER,
				new Affiliation("testtest"));		

		String xml = getMarchalledObjectXml(AffiliationChangedEvent.class, event);
		System.out.println(xml);
		System.out.println();		
		
		AffiliationEvent objectFromXml = (AffiliationEvent) getUnmarchalledObject(AffiliationEvent.class, xml);		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}
	
	@Test
	public void testRoleCreatedEventMinimalXml() throws JAXBException {

		RoleCreatedEvent event = new RoleCreatedEvent(
				SYSTEM_MESSAGE_PRODUCER, 
				new Role(
						new Student("000000000000"),
						new Organization("X11")
						));
		
		String xml = getMarchalledObjectXml(RoleCreatedEvent.class, event);
		System.out.println(xml);
		System.out.println();
		
		RoleEvent objectFromXml = (RoleEvent) getUnmarchalledObject(RoleEvent.class, xml); 

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", objectFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	
	}
	
	private Object getUnmarchalledObject(
			@SuppressWarnings("rawtypes") Class c, String xml) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(new StringReader(xml));			
	}

	private String getMarchalledObjectXml(Class c, Object o) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		OutputStream objectXmlStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(o, objectXmlStream);
		return objectXmlStream.toString();
		
	}

	@Test
	public void testAffiliationChangedEventFullXml() throws JAXBException {
		
		Person person = new Person("0000000000", "Test", "Testsson");
		Affiliation affiliation = new Affiliation("testtest", person);
		AffiliationEventData affiliationEventData = new AffiliationEventData();
		UUEventDataProperty property = new UUEventDataProperty("Identifier", "test1234", "testtest");
		affiliationEventData.addEventPropertyData(property);
		AffiliationChangedEvent event = new AffiliationChangedEvent(SYSTEM_MESSAGE_PRODUCER, 
				affiliation, 
				affiliationEventData);

		String xml = getMarchalledObjectXml(AffiliationChangedEvent.class, event);
		System.out.println(xml);
		System.out.println();
		
		AffiliationEvent identityEventFromXml = (AffiliationEvent) getUnmarchalledObject(AffiliationEvent.class, xml);
		
		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", identityEventFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));

		// curl -X POST -H "Content-Type: application/xml" -d @identity-event.xml http://localhost:8989/esb/rest/person/event/

		// GET:  https://localhost:8989/esb/rest/person/{id}
		
		// POST: http://localhost:8989/esb/rest/person/event/
		// POST: http://localhost:8989/esb/rest/affiliation/event/		
		// POST: http://localhost:8989/esb/rest/orgdepmapping/event/	
		// POST: http://localhost:8989/esb/rest/role/event/	
		
		
	}

}
