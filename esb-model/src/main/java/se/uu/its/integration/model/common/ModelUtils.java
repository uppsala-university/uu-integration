package se.uu.its.integration.model.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ModelUtils {
	
	public static String TO_STRING_ERROR_MSG = "Error!";
	
	public ModelUtils() {
	}
	
	public String getNewEventId() {
		return UUID.randomUUID().toString();
	}
	
	public String addIntegrationEventIdToEvent(String xml) throws UnsupportedEncodingException, TransformerException {
		return xsltTransform(xml, "/se/uu/its/integration/model/transform/addIntegrationEventIdToEvent.xsl");
	}
	
	public String xsltTransform(String xml, String xsltResourcePath) throws TransformerException, UnsupportedEncodingException {
		
		// get the stylesheet as an inputstream
		InputStream stylesheet = ClassLoader.class.getResourceAsStream(xsltResourcePath);
		
		// get the transformer
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));		
		transformer.setParameter("uid", UUID.randomUUID().toString());
		
		// the source and the target
		StringReader reader = new StringReader(xml);
		ByteArrayOutputStream targetDocument = new ByteArrayOutputStream();
		
		// do the tranformation
		transformer.transform(new StreamSource(reader), new StreamResult(targetDocument));
		
		String output = targetDocument.toString("UTF-8");
		System.out.println(output);
		
		return output;
		
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
