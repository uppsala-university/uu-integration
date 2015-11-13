package se.uu.its.integration.model.transform.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import se.uu.its.integration.model.common.ModelUtils;
import se.uu.its.integration.model.events.AffiliationCreatedEvent;
import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.GroupCreateRequestEvent;
import se.uu.its.integration.model.group.StudentGroup;
import se.uu.its.integration.model.identity.Affiliation;

public class ModelTransformTest {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Test
	public void testGroupCreateRequestEventToGouperCreateXmlPayload() throws Exception {
		
		log.info("TEST");
		
		String grouperCrateXmlPayload =
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<WsRestGroupSaveRequest>" +
				  "<wsGroupToSaves>" +
				    "<WsGroupToSave>" +
				      "<wsGroupLookup>" +
				        "<groupName>hkslab:g1</groupName>" +
				      "</wsGroupLookup>" +
				      "<wsGroup>" +
				        "<displayExtension>Group 1</displayExtension>" + 
				        "<description>This is the first Group</description>" +
				        "<name>hkslab:g1</name>" + 
				      "</wsGroup>" +
				    "</WsGroupToSave>" +
				  "</wsGroupToSaves>" +
				"</WsRestGroupSaveRequest>";
		
		log.info(grouperCrateXmlPayload);
		
		String groupCreateRequestEventXml = new GroupCreateRequestEvent(
				"test", 
				"testid", 
				new StudentGroup(
						"hkslab:g1",
						"This is the first Group",
						"Group 1")
				).toString();
		
		log.info(groupCreateRequestEventXml);
		
		ModelUtils utily = new ModelUtils();
		String transformedXml = utily.xsltTransform(groupCreateRequestEventXml, "/se/uu/its/integration/model/transform/groupCreateRequestEventToGouperCreateXmlPayload.xml");

		log.info(transformedXml);
		
		assertFalse(!transformedXml.equalsIgnoreCase(grouperCrateXmlPayload));
		
	}	
	
	@Test
	public void testKurstillfalleStatusHandelseToGroupCreateRequestEvent() throws Exception {
		
		String kurstillfalleStatusHandelseXml = "";
		
		String answerXml = new GroupCreateRequestEvent(
				"test", 
				"testid", 
				new StudentGroup(
						"Studentgrupp",
						"Studentgrupp beskrivning")
				).toString();
		
		ModelUtils utily = new ModelUtils();
		String transformedXml = utily.xsltTransform(kurstillfalleStatusHandelseXml, "/se/uu/its/integration/model/transform/kurstillfalleStatusHandelseToGroupCreateRequestEvent.xsl");

		assertFalse(!transformedXml.equalsIgnoreCase(answerXml));
		
	}	
	
	@Test
	public void testAddIntegrationEventIdToEventByMethod() throws Exception {
		
		String xml = new AffiliationCreatedEvent(
				"Testsystem",
				"Ev902",
				new Affiliation("emplempl")).toString();
		
		ModelUtils utily = new ModelUtils();
		String transformedXml = utily.addIntegrationEventIdToEvent(xml);

		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, transformedXml);	

		log.info("Generated id: " + objectFromXml.getIdentifier());		
		
		assertNotNull(transformedXml);
		
	}
	
	
	@Test
	public void testAddIntegrationEventIdToEvent() throws TransformerException, UnsupportedEncodingException, JAXBException {
		
//		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
		
		String xml = new AffiliationCreatedEvent(
				"Testsystem",
				"Ev902",
				new Affiliation("emplempl")).toString();
		
		// get the stylesheet as an inputstream
		InputStream stylesheet = ClassLoader.class.getResourceAsStream("/se/uu/its/integration/model/transform/addIntegrationEventIdToEvent.xsl");
		
		// get the transformer
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));		
		transformer.setParameter("uid", UUID.randomUUID());
		
		// the source and the target
		StringReader reader = new StringReader(xml);
		ByteArrayOutputStream targetDocument = new ByteArrayOutputStream();
		
		// do the tranformation
		transformer.transform(new StreamSource(reader), new StreamResult(targetDocument));
		
		String transformedXml = targetDocument.toString("UTF-8");

		// print
		log.debug(transformedXml);		
				
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, transformedXml);	

		log.info("Generated id: " + objectFromXml.getIdentifier());
		
		assertTrue("Unmarchalled object does not av a proper identifier.", (objectFromXml.getIdentifier() != null));
	}

}
