package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlElementRef;

import se.uu.its.integration.model.common.UUEvent;

public class PersonEvent extends UUEvent {

	@XmlElementRef(type = Person.class)
	private Person person;	
	
	@XmlElementRef(type = PersonEventData.class)
	private PersonEventData personEventData;

	protected PersonEvent() {
	}
	
	protected PersonEvent(String producer, Person person) {
		super(producer);
		this.person = person;
	}
	
	protected PersonEvent(String producer, Person person, PersonEventData personEventData) {
		super(producer);
		this.person = person;
		this.personEventData = personEventData;
	}	
	
}
