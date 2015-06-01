package se.uu.its.integration.model.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.uu.its.integration.model.events.UUEvent;

public class ModelUtils {
	
	public static String TO_STRING_ERROR_MSG = "Error!";
	protected Log log = LogFactory.getLog(this.getClass());
	
	public ModelUtils() {
	}
	
	public Object convertToUUEvent(Object message) {
		
		log.info("+++++++++++++++++ HEJ!");
		
		if (message instanceof UUEvent) {
			UUEvent e = ((UUEvent) message).exportUUEvent();
//			e.setIdentifier("debugid_xyz");
			return e;
		} else {
			return message;
		}
	}
	
	public String getNewEventId() {
		return UUID.randomUUID().toString();
	}
	
	public String addIntegrationEventIdToEvent(String xml) throws Exception {
		
		log.debug("Adding new integration event id.");
		
		return xsltTransform(xml, "/se/uu/its/integration/model/transform/addIntegrationEventIdToEvent.xsl");
	}
	
	public String xsltTransform(String xml, String xsltResourcePath) throws Exception {
		
		// get the stylesheet as an inputstream
		InputStream stylesheet = this.getClass().getResourceAsStream(xsltResourcePath);

		if (stylesheet == null) {
			throw new Exception("ELENDESSKIT");
		}		
		
		// get the transformer
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));		

		// TODO: Should be moved to method signature as parameter.
		String uid = UUID.randomUUID().toString();
		log.info("Setting parameter uid: " + uid);
		transformer.setParameter("uid", uid);
		
		// the source and the target
		StringReader reader = new StringReader(xml);
		ByteArrayOutputStream targetDocument = new ByteArrayOutputStream();
		
		// do the tranformation
		transformer.transform(new StreamSource(reader), new StreamResult(targetDocument));
		
		String transformedXml = targetDocument.toString("UTF-8");
		log.debug(transformedXml);
		
		return transformedXml;
		
	}

	public static Object getUnmarchalledObject(
			@SuppressWarnings("rawtypes") Class c, String xml) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(new StringReader(xml));			
	}

	public static String getMarchalledObjectXml(Class c, Object o) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		OutputStream objectXmlStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(o, objectXmlStream);
		return objectXmlStream.toString();
		
	}	
	
}
