package se.uu.its.integration.model.identity.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import se.uu.its.integration.model.common.UUEventDataProperty;
import se.uu.its.integration.model.identity.Identity;
import se.uu.its.integration.model.identity.IdentityChangedEvent;
import se.uu.its.integration.model.identity.IdentityEvent;
import se.uu.its.integration.model.identity.IdentityEventData;
import se.uu.its.integration.model.identity.Person;
import se.uu.its.integration.model.identity.PersonChangedEvent;
import se.uu.its.integration.model.identity.PersonEvent;
import se.uu.its.integration.model.identity.PersonEventData;


public class IdentityEventModelTest {

	private static final String SYSTEM_MESSAGE_PRODUCER = "AKKA";
	
	@Test 
	public void testPersonChangedEventFullXml() throws JAXBException {
		Person personWithRef = new Person("marja992", "Markus", "Jardemalm");
		JAXBContext jaxbContext = JAXBContext.newInstance(PersonChangedEvent.class);

		UUEventDataProperty property = new UUEventDataProperty("Firstname", "Marcus", "Markus");
		PersonEventData personEventData = new PersonEventData();
		personEventData.addEventPropertyData(property);	
		
		PersonChangedEvent personEvent = new PersonChangedEvent(SYSTEM_MESSAGE_PRODUCER, personWithRef, personEventData);

		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(personEvent, System.out);
		
		OutputStream personEventXmlStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(personEvent, personEventXmlStream);
		String identityEventXml = personEventXmlStream.toString();
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		PersonEvent personEventFromXml = (PersonEvent) jaxbUnmarshaller.unmarshal(new StringReader(identityEventXml));		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", personEventFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}
	
	@Test
	public void testIdentityChangedEventMinimalXml() throws JAXBException {
		Identity identity = new Identity("marja992");
		IdentityChangedEvent identityEvent = new IdentityChangedEvent(SYSTEM_MESSAGE_PRODUCER,
				identity);		

		JAXBContext jaxbContext = JAXBContext.newInstance(IdentityChangedEvent.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(identityEvent, System.out);	
		
		OutputStream identityEventXmlStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(identityEvent, identityEventXmlStream);
		String identityEventXml = identityEventXmlStream.toString();
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		IdentityEvent identityEventFromXml = (IdentityEvent) jaxbUnmarshaller.unmarshal(new StringReader(identityEventXml));		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", identityEventFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
	}
	
	@Test
	public void testIdentityChangedEventFullXml() throws JAXBException {
		
		Person person = new Person("Markus", "Jardemalm");

		Identity identity = new Identity("marja992", person);
		
		IdentityEventData identityEventData = new IdentityEventData();
		UUEventDataProperty property = new UUEventDataProperty("Firstname", "Marcus", "Markus");
		identityEventData.addEventPropertyData(property);
		
		IdentityChangedEvent identityEvent = new IdentityChangedEvent(SYSTEM_MESSAGE_PRODUCER, 
				identity, 
				identityEventData);

		JAXBContext jaxbContext = JAXBContext.newInstance(IdentityChangedEvent.class);
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(identityEvent, System.out);
		
		OutputStream identityEventXmlStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(identityEvent, identityEventXmlStream);
		String identityEventXml = identityEventXmlStream.toString();
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		IdentityEvent identityEventFromXml = (IdentityEvent) jaxbUnmarshaller.unmarshal(new StringReader(identityEventXml));
		
		System.out.println("Parsed info:");
		System.out.println(" IdentityEvent:Type: " + identityEventFromXml.getType());
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    fmt.setCalendar(identityEventFromXml.getIssuedTime());
	    String issuedTime = fmt.format(identityEventFromXml.getIssuedTime().getTime());
		
		System.out.println(" IdentityEvent:IssuedTime: " + issuedTime);
		System.out.println(" Identity:identifier: " + identityEventFromXml.getIdentity().getIdentifier());
		
//		jaxbMarshaller.marshal(personEvent, System.out);		

		assertTrue("Unmarchalled value of producer is not the same as marchalled object property value.", identityEventFromXml.getProducer().equalsIgnoreCase(SYSTEM_MESSAGE_PRODUCER));
		
		
		// curl -X POST -H "Content-Type: application/xml" -d @identity-event.xml http://localhost:8989/esb/rest/identity/event/
	}

}
