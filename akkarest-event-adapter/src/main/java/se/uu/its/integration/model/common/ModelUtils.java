package se.uu.its.integration.model.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ModelUtils {

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
