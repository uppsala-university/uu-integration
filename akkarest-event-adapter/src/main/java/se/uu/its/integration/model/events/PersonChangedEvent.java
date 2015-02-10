package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Person;

@XmlRootElement(name = "PersonEvent")
public class PersonChangedEvent extends PersonEvent {

	private static final long serialVersionUID = 2325281187450370804L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private PersonChangedEvent() {
	}
	
	public PersonChangedEvent(String producer, Person person, PersonEventData personEventData) {
		super(producer, person, personEventData);
	}
	
}
