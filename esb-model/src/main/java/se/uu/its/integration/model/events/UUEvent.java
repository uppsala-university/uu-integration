package se.uu.its.integration.model.events;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import se.uu.its.integration.model.common.ModelUtils;

@Entity
@Table(name="uuevents")
@NamedQuery(name = "selectUUEventById", query = "select e from UUEvent e where e.id = :id")
@XmlSeeAlso({AffiliationEvent.class, PersonEvent.class, RoleEvent.class, OrganizationDepartmentMappingEvent.class})
@XmlRootElement(name = "UUEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public  class UUEvent implements Serializable {
	
	private static final long serialVersionUID = 6670201491567203786L;
	
	@Id
	@Column(name = "identifier")
	@XmlAttribute(name = "identifier")
	String _identifier;

	@Column(name = "event_type")
	@XmlAttribute(name = "type")
	String _type;	
	
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
		uuEvent._identifier = this._identifier;
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
	
	public String getProducer() {
		return _producer;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public String getProducerReferenceId() {
		return _producerReferenceId;
	}
	
}
