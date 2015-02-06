package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PersonEvent")
public class PersonChangedEvent extends PersonEvent {

	private PersonChangedEvent() {
	}
	
	public PersonChangedEvent(String producer, Person person, PersonEventData personEventData) {
		super(producer, person, personEventData);
	}
	
}
