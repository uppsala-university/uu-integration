package se.uu.its.integration.model.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.RoleEvent;

@XmlSeeAlso({AffiliationEvent.class, PersonEvent.class, RoleEvent.class, OrganizationDepartmentMappingEvent.class})
@XmlTransient
public abstract class UUEvent implements Serializable {
	
	private static final long serialVersionUID = 6670201491567203786L;

	@XmlAttribute(name = "type")
	String type;
	
	@XmlAttribute(name = "identifier")
	String identifier = null;
	
	@XmlElement(name = "IssuedTime")
	protected Calendar time;	

	@XmlElement(name = "Producer")
	public String producer;

	@XmlElement(name = "ProducerReferenceId")
	String producerReferenceId;
	
	protected UUEvent() {
	}
	
	public String toString() {

		String result = ModelUtils.TO_STRING_ERROR_MSG;
		
		try {
			result = ModelUtils.getMarchalledObjectXml(this.getClass(), this);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return result;
		
	}
	
	protected UUEvent(String producer, String producerReferenceId) {
		this.time = GregorianCalendar.getInstance();
		this.type = this.getClass().getSimpleName();
		this.producer = producer;
		this.producerReferenceId = producerReferenceId;
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
	
	public String getIdentifier() {
		return identifier;
	}
}
