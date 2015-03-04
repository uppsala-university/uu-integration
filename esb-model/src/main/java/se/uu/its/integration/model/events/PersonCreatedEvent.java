package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Person;

@XmlRootElement(name = "PersonEvent")
public class PersonCreatedEvent extends PersonEvent {

	private static final long serialVersionUID = 2325281187450370804L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private PersonCreatedEvent() {
	}
	
	public PersonCreatedEvent(String producer, 
			String producerReferenceId, 
			Person person, 
			PersonEventData personEventData) {
		super(producer, producerReferenceId, person, personEventData);
	}

	public PersonCreatedEvent(String producer, 
			String producerReferenceId, 
			Person person) {
		super(producer, producerReferenceId, person);
	}	
	
}
