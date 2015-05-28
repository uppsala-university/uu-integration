package se.uu.its.integration.model.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Person;

//@Entity
//@DiscriminatorValue("PersonEvent")
@XmlRootElement(name = "PersonEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class PersonEvent extends UUEvent {

	private static final long serialVersionUID = -5522728677632277563L;

	@Transient
	@XmlElementRef(type = Person.class)
	private Person person;	
	
	@Transient
	@XmlElementRef(type = PersonEventData.class)
	private PersonEventData personEventData;

	protected PersonEvent() {
	}
	
	protected PersonEvent(String producer, String producerReferenceId, Person person) {
		super(producer, producerReferenceId);
		this.person = person;
	}
	
	protected PersonEvent(String producer, String producerReferenceId, Person person, PersonEventData personEventData) {
		super(producer, producerReferenceId);
		this.person = person;
		this.personEventData = personEventData;
	}	
	
}
