package se.uu.its.integration.model.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.events.AffiliationChangedEvent;

@XmlTransient
public abstract class UUEvent implements Serializable {
	
	private static final long serialVersionUID = 6670201491567203786L;

	@XmlAttribute(name = "type")
	String type;
	
	@XmlAttribute(name = "id")
	String id;
	
	@XmlElement(name = "IssuedTime")
	protected Calendar time;	

	@XmlElement(name = "Producer")
	public String producer;
	
	protected UUEvent() {
	}
	
	public String toString() {

		String result = "Error!";
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			OutputStream identityEventXmlStream = new ByteArrayOutputStream();

			jaxbMarshaller.marshal(this, identityEventXmlStream);
			result = identityEventXmlStream.toString();

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
		}

		return result;

	}
	
	protected UUEvent(String producer) {
		this.time = GregorianCalendar.getInstance();
		this.producer = producer;
		this.type = this.getClass().getSimpleName();
		
	}
	
	public Calendar getIssuedTime() {
		return time;
	}
	
	public String getType() {
		return type;
	}
	
	public String getProducer() {
		return producer;
	}
}
