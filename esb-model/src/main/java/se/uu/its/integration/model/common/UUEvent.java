package se.uu.its.integration.model.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.RoleEvent;

@Entity
@Table(name="uuevents")
@XmlSeeAlso({AffiliationEvent.class, PersonEvent.class, RoleEvent.class, OrganizationDepartmentMappingEvent.class})
@XmlTransient
public  class UUEvent implements Serializable {
	
	private static final long serialVersionUID = 6670201491567203786L;
	
	@Transient
	@XmlAttribute(name = "type")
	private String _type;
	
	@Id
	@GeneratedValue
	@Column(name = "identifier")	
	@XmlAttribute(name = "identifier")
	private String _identifier = null;

	@Transient
	@XmlElement(name = "ProcessedTime")
	protected Calendar _processedTime;		
	
	@Column(name = "issuedTime")
	@XmlElement(name = "IssuedTime")
	private Calendar _issuedTime;	
	
	@Column(name = "producer")
	@XmlElement(name = "Producer")
	private String _producer;
	
	@Column(name = "producerReferenceId")
	@XmlElement(name = "ProducerReferenceId")
	private String _producerReferenceId;
	
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
		this._issuedTime = GregorianCalendar.getInstance();
		this._type = this.getClass().getSimpleName();
		this._producer = producer;
		this._producerReferenceId = producerReferenceId;
	}
	
	public Calendar getIssuedTime() {
		return _issuedTime;
	}

	public void setIssuedTime(Calendar issuedTime) {
		this._issuedTime = issuedTime;
	}	

	public String getType() {
		return _type;
	}
	
	public void setType(String type) {
		this._type = type;
	}

	public String getProducer() {
		return _producer;
	}

	public void setProducer(String producer) {
		this._producer = producer;
	}	
	
	public String getIdentifier() {
		return _identifier;
	}
	
	public void setIdentifier(String identifier) {
		this._identifier = identifier;
	}
	
	public String getProducerReferenceId() {
		return _producerReferenceId;
	}
	
	public void setProducerReferenceId(String producerReferenceId) {
		this._producerReferenceId = producerReferenceId;
	}	
	
}
