package se.uu.its.integration.model.transform.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

import se.uu.its.integration.model.common.ModelUtils;
import se.uu.its.integration.model.events.AffiliationCreatedEvent;
import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.identity.Affiliation;

public class ModelTransformTest {

	@Test
	public void testAddIntegrationEventIdToEvent() throws TransformerException, UnsupportedEncodingException, JAXBException {
		
		String xml = new AffiliationCreatedEvent(
				"Testsystem",
				"Ev902",
				new Affiliation("emplempl")).toString();
		
		// get the stylesheet as an inputstream
		InputStream stylesheet = ClassLoader.class.getResourceAsStream("/se/uu/its/integration/model/transform/addIntegrationEventIdToEvent.xsl");
		
		// get the transformer
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));		

		// the source and the target
		StringReader reader = new StringReader(xml);
		ByteArrayOutputStream targetDocument = new ByteArrayOutputStream();
		
		// do the tranformation
		transformer.transform(new StreamSource(reader), new StreamResult(targetDocument));
		
		String transformedXml = targetDocument.toString("UTF-8");

		// print
		System.out.println(transformedXml);		
				
		AffiliationEvent objectFromXml = (AffiliationEvent) ModelUtils.getUnmarchalledObject(AffiliationEvent.class, transformedXml);	

		System.out.println("Generated id: " + objectFromXml.getIdentifier());
		
		assertTrue("Unmarchalled object does not av a proper identifier.", (objectFromXml.getIdentifier() != null));
	}

}
