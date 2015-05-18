package se.uu.its.integration.model.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.RoleEvent;

//Entity
//Table(name="uuevents")
@XmlSeeAlso({AffiliationEvent.class, PersonEvent.class, RoleEvent.class, OrganizationDepartmentMappingEvent.class})
@XmlTransient
public  class UUEvent implements Serializable {
	
	private static final long serialVersionUID = 6670201491567203786L;
	
	//XmlAttribute(name = "type")
	private String type;
	
	//Id
	//XmlAttribute(name = "identifier")
	private String identifier = null;
	
//	@XmlElement(name = "ProcessedTime")
//	protected Calendar processedTime;		
	
	//Column
	//XmlElement(name = "IssuedTime")
	private Calendar issuedTime;	

	//Column
	//XmlElement(name = "Producer")
	private String producer;

	//Column
	//XmlElement(name = "ProducerReferenceId")
	private String producerReferenceId;
	
	public UUEvent() {
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
		this.issuedTime = GregorianCalendar.getInstance();
		this.type = this.getClass().getSimpleName();
		this.producer = producer;
		this.producerReferenceId = producerReferenceId;
	}
	
	@XmlElement(name = "IssuedTime")
	public Calendar getIssuedTime() {
		return issuedTime;
	}
	
	@XmlAttribute(name = "type")
	public String getType() {
		return type;
	}
	
	@XmlAttribute(name = "producer")
	public String getProducer() {
		return producer;
	}
	
	@XmlAttribute(name = "identifier")
	public String getIdentifier() {
		return identifier;
	}
	
	@XmlAttribute(name = "ProducerReferenceId")
	public String getProducerReferenceId() {
		return producerReferenceId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setIssuedTime(Calendar issuedTime) {
		this.issuedTime = issuedTime;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setProducerReferenceId(String producerReferenceId) {
		this.producerReferenceId = producerReferenceId;
	}
}
