package se.uu.its.integration.model.events;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.common.ModelUtils;

@Entity
//@Inheritance
//@DiscriminatorColumn (name = "type")
@Table(name="uuevents")
@XmlSeeAlso({AffiliationEvent.class, PersonEvent.class, RoleEvent.class, OrganizationDepartmentMappingEvent.class})
//@XmlTransient
@XmlRootElement(name = "UUEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public  class UUEvent implements Serializable {
	
	private static final long serialVersionUID = 6670201491567203786L;
	
	@Transient
	@XmlAttribute(name = "type")
	String _type;
	
	@Id
	@Column(name = "identifier")
	@XmlAttribute(name = "identifier")
	String _myNewId;

	@Transient
	@XmlElement(name = "ProcessedTime")
	Calendar _processedTime;		
	
	@Column(name = "issuedTime")
	@XmlElement(name = "IssuedTime")
	Calendar _issuedTime;	
	
	@Column(name = "producer")
	@XmlElement(name = "Producer")
	String _producer;
	
	@Column(name = "producerReferenceId")
	@XmlElement(name = "ProducerReferenceId")
	String _producerReferenceId;
	
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
	
	public UUEvent exportUUEvent() {
		UUEvent uuEvent = new UUEvent();
		uuEvent._myNewId = UUID.randomUUID().toString();
		uuEvent._issuedTime = this._issuedTime;
		uuEvent._processedTime = this._processedTime;
		uuEvent._producer = this._producer;
		uuEvent._producerReferenceId = this._producerReferenceId;
		uuEvent._type = this._type;
		return uuEvent;
	}
	
	protected UUEvent(String producer, String producerReferenceId) {
		this._issuedTime = GregorianCalendar.getInstance();
		this._type = this.getClass().getSimpleName();
		this._producer = producer;
		this._producerReferenceId = producerReferenceId;
	}
	
//	public Calendar getIssuedTime() {
//		return _issuedTime;
//	}
//
//	public void setIssuedTime(Calendar issuedTime) {
//		this._issuedTime = issuedTime;
//	}	
//
//	public String getType() {
//		return _type;
//	}
//	
//	public void setType(String type) {
//		this._type = type;
//	}
//
	public String getProducer() {
		return _producer;
	}
//
//	public void setProducer(String producer) {
//		this._producer = producer;
//	}	
//	
	public String getIdentifier() {
		return _myNewId;
	}
//	
//	public void setIdentifier(String identifier) {
//		this._identifier = identifier;
//	}
//	
	public String getProducerReferenceId() {
		return _producerReferenceId;
	}
//	
//	public void setProducerReferenceId(String producerReferenceId) {
//		this._producerReferenceId = producerReferenceId;
//	}	
	
}
