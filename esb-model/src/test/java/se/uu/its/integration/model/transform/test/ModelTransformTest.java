package se.uu.its.integration.model.transform.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
import se.uu.its.integration.model.events.GroupEvent;
import se.uu.its.integration.model.events.GroupEventData;
import se.uu.its.integration.model.events.GroupMembershipCreateRequestEvent;
import se.uu.its.integration.model.group.StudentGroup;
import se.uu.its.integration.model.identity.Affiliation;
import se.uu.its.integration.model.identity.Person;

public class ModelTransformTest {

	private Log log = LogFactory.getLog(this.getClass());
	static final String STUDENT_LOCAL_ID = "stud1234";
	static final String STUDENT_PERSON_NUMBER = "197001011234";
	static final String KURSTILFALLESKOD = "KT001";
	static final String MESSAGE_PRODUCER_LADOK = "Ladok";
	static final String LADOK_MESSAGE_ID = "9bf85736-975b-11e5-b128-543fc12c43e6";
	static final String GROUPER_GROUP_IDENTIFIER = "hkslab:g1"; 
	
	@Test
	public void testForvantatDeltagandeSkapadHandelseToGroupMembershipCreateRequest() throws Exception {

		String ladokForvantatDeltagandeSkapadHandelseXml = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			    "<sd:ForvantatDeltagandeSkapadHandelse xmlns:sd=\"http://schemas.ladok.se/studiedeltagande\" xmlns:dap=\"http://schemas.ladok.se/dap\" xmlns:service=\"http://schemas.ladok.se/service\" xmlns:events=\"http://schemas.ladok.se/events\" xmlns:base=\"http://schemas.ladok.se\">" +
			    		"<events:EventContext>" + 
			    			"<events:DoldForExternaSystem>false</events:DoldForExternaSystem>" +
			    			"<events:LarosateID>43</events:LarosateID>" +
			    		"</events:EventContext>" +
			    		"<events:Handelsetid>2015-11-30T13:12:21.923</events:Handelsetid>" +
			    		"<events:HandelseUID>" + LADOK_MESSAGE_ID + "</events:HandelseUID>" +
			    		"<sd:Senaredelmarkering>false</sd:Senaredelmarkering>" +
			    		"<sd:StudentUID>bdcdda52-12e5-4f73-b21a-9b5a49ca987a</sd:StudentUID>" +
			    		"<sd:Studieavgiftsbetalning>studiedeltagande.domain.studieavgiftsbetalningsvarde.ej_aktuell</sd:Studieavgiftsbetalning>" +
			    		"<sd:TillfallesantagningUID>9bf6a985-975b-11e5-b128-543fc12c43e6</sd:TillfallesantagningUID>" +
			    		"<sd:UtbildningstillfalleUID>2cdf0716-975b-11e5-bc73-c14909054451</sd:UtbildningstillfalleUID>" +
			    "</sd:ForvantatDeltagandeSkapadHandelse>";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("studentId", STUDENT_LOCAL_ID);
		parameters.put("kurstillfalleKod", KURSTILFALLESKOD);
		parameters.put("personNumber", STUDENT_PERSON_NUMBER);
	
		ModelUtils utily = new ModelUtils();
		String transformedXml = utily.xsltTransform(ladokForvantatDeltagandeSkapadHandelseXml, "/se/uu/its/integration/model/transform/forvantatDeltagandeSkapadHandelseToGroupMembershipCreateRequestEvent.xsl", parameters);
		GroupEvent transformedEvent = (GroupEvent) ModelUtils.getUnmarchalledObject(GroupEvent.class, transformedXml);
		
		GroupMembershipCreateRequestEvent groupMembershipCreateRequestEvent = new GroupMembershipCreateRequestEvent(
				"Ladok", 
				LADOK_MESSAGE_ID, 
				new StudentGroup(
						"hkslab:g1",
						"This is the first Group",
						"Group 1"),
				new GroupEventData(
						new Person(STUDENT_LOCAL_ID, STUDENT_PERSON_NUMBER))
				);
		String groupMembershipCreateRequestEventXml = groupMembershipCreateRequestEvent.toString();

		log.info("Transformed XML: " + transformedXml);
		log.info("Expekted XML: " + groupMembershipCreateRequestEventXml);
		
		assertTrue(transformedEvent.getProducerReferenceId().equalsIgnoreCase(groupMembershipCreateRequestEvent.getProducerReferenceId()));
	
	}

	@Test 
	public void testGroupMembershipCreateRequestToGrouperXmlPayload() throws Exception {
		
		String grouperCreateXmlPayload =
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<WsRestAddMemberRequest>" +
						"<wsGroupLookup>" +
							"<groupName>" + GROUPER_GROUP_IDENTIFIER + "</groupName>" +
						"</wsGroupLookup>" +
						"<subjectLookups>" +
							"<WsSubjectLookup>" +
								"<subjectId>" + STUDENT_LOCAL_ID + "</subjectId>" +
							"</WsSubjectLookup>" +
						"</subjectLookups>" +
				"</WsRestAddMemberRequest>";				
		
		String groupMembershipCreateRequestEventXml = new GroupMembershipCreateRequestEvent(
				"Ladok", 
				LADOK_MESSAGE_ID, 
				new StudentGroup(
						"hkslab:g1",
						"This is the first Group",
						"Group 1"),
				new GroupEventData(
						new Person(STUDENT_LOCAL_ID, STUDENT_PERSON_NUMBER))
				).toString();
		
		ModelUtils utily = new ModelUtils();
		String transformedXml = utily.xsltTransform(groupMembershipCreateRequestEventXml, "/se/uu/its/integration/model/transform/groupMembershipCreateRequestEventToGouperCreateXmlPayload.xml");		
		
		log.info("Source XML: " + groupMembershipCreateRequestEventXml);
		
		log.info("Transformed XML: " + transformedXml);
		
		assertTrue(transformedXml.equalsIgnoreCase(grouperCreateXmlPayload));
	}
	
	@Test
	public void testGroupCreateRequestEventToGouperCreateXmlPayload() throws Exception {
		
		String grouperCreateXmlPayload =
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
		
		log.info(grouperCreateXmlPayload);
		
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
		
		assertFalse(!transformedXml.equalsIgnoreCase(grouperCreateXmlPayload));
		
	}	
	
	@Test
	public void testKurstillfalleStatusHandelseToGroupCreateRequestEvent() throws Exception {
		
		String kurstillfalleStatusHandelseXml = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<ui:KurstillfalleTillStatusHandelse xmlns:ui=\"http://schemas.ladok.se/utbildningsinformation\" xmlns:dap=\"http://schemas.ladok.se/dap\" xmlns:service=\"http://schemas.ladok.se/service\" xmlns:events=\"http://schemas.ladok.se/events\" xmlns:base=\"http://schemas.ladok.se\">" +
						"<events:EventContext>" +
							"<events:DoldForExternaSystem>false</events:DoldForExternaSystem>" +
							"<events:LarosateID>43</events:LarosateID>" +
						"</events:EventContext>" +
						"<events:Handelsetid>2015-11-17T13:08:20.846</events:Handelsetid>" +
						"<events:SelfRef>" +
							"<base:link rel=\"self\" mediaType=\"application/vnd.ladok+xml,application/vnd.ladok-utbildningsinformation+xml,application/vnd.ladok-utbildningsinformation+json\" method=\"GET\" uri=\"https://api.mit.ladok.se:443/utbildningsinformation/utbildningstillfalle/4f410159-8d23-11e5-ab9a-fa1faa7b41b5\" />" +
							"<base:Uid>4f410159-8d23-11e5-ab9a-fa1faa7b41b5</base:Uid>" +
						"</events:SelfRef>" +
						"<events:ResursUID>4f410159-8d23-11e5-ab9a-fa1faa7b41b5</events:ResursUID>" +
						"<events:HandelseUID>e4e828f0-8d23-11e5-ab9a-fa1faa7b41b5</events:HandelseUID>" +
						"<ui:OrganisationUID>00000000-3300-0000-0043-000000000000</ui:OrganisationUID>" +
						"<ui:Status>3</ui:Status>" +
						"<ui:UtbildningUID>c6dfd3ac-8d22-11e5-ab9a-fa1faa7b41b5</ui:UtbildningUID>" +
						"<ui:UtbildningsinstansUID>c6dfac9b-8d22-11e5-ab9a-fa1faa7b41b5</ui:UtbildningsinstansUID>" +
						"<ui:UtbildningstypID>52</ui:UtbildningstypID>" +
						"<ui:Avgiftsbelagt>false</ui:Avgiftsbelagt>" +
						"<ui:StartperiodID>43146</ui:StartperiodID>" +
						"<ui:StudielokaliseringID>43000</ui:StudielokaliseringID>" +
						"<ui:StudietaktID>1</ui:StudietaktID>" +
						"<ui:UndervisningsformID>3</ui:UndervisningsformID>" +
						"<ui:UndervisningstidID>1</ui:UndervisningstidID>" +
						"<ui:UtbildningUtbildningstypID>22</ui:UtbildningUtbildningstypID>" +
						"<ui:UtbildningstillfalleUID>4f410159-8d23-11e5-ab9a-fa1faa7b41b5</ui:UtbildningstillfalleUID>" +
						"<ui:Utbildningstillfalleskod>KT401</ui:Utbildningstillfalleskod>" +
						"<ui:Kurstillfallesattribut>" +
							"<ui:Finansieringsform>1</ui:Finansieringsform>" +
						"</ui:Kurstillfallesattribut>" +
						"<ui:Kurstillfallesperioder>" +
							"<base:Uid>4f41286c-8d23-11e5-ab9a-fa1faa7b41b5</base:Uid>" +
							"<ui:ForstaRegistreringsdatum>2015-01-01</ui:ForstaRegistreringsdatum>" +
							"<ui:ForstaUndervisningsdatum>2015-01-01</ui:ForstaUndervisningsdatum>" +
							"<ui:Omfattningsvarde>30.0</ui:Omfattningsvarde>" +
							"<ui:SistaRegistreringsdatum>2015-01-31</ui:SistaRegistreringsdatum>" +
							"<ui:SistaUndervisningsdatum>2015-12-31</ui:SistaUndervisningsdatum>" +
							"<ui:UtbildningsomradePerOrganisation>" +
								"<base:Uid>97b80800-8d23-11e5-ab9a-fa1faa7b41b5</base:Uid>" +
								"<ui:OrganisationUID>00000000-3300-0000-0043-000000000000</ui:OrganisationUID>" +
								"<ui:Procent>100</ui:Procent>" +
								"<ui:UtbildningsomradeID>2</ui:UtbildningsomradeID>" +
							"</ui:UtbildningsomradePerOrganisation>" +
						"</ui:Kurstillfallesperioder>" +
					"</ui:KurstillfalleTillStatusHandelse>";
		
		GroupCreateRequestEvent answer = new GroupCreateRequestEvent(
				"Ladok", 
				"4f410159-8d23-11e5-ab9a-fa1faa7b41b5", 
				new StudentGroup(
						"hkslab:KT401",
						"Det här är en automatiskt genererad deltagarlista för ett kurstillfälle.")
				);		
		
		ModelUtils utily = new ModelUtils();
		String transformedXml = utily.xsltTransform(kurstillfalleStatusHandelseXml, "/se/uu/its/integration/model/transform/kurstillfalleStatusHandelseToGroupCreateRequestEvent.xsl");

		log.info("Transformed XML: " + transformedXml);
		
		GroupEvent transformedEvent = (GroupEvent) ModelUtils.getUnmarchalledObject(GroupEvent.class, transformedXml);
		
		assertFalse(transformedEvent.getProducerReferenceId() == answer.getProducerReferenceId());
		
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
