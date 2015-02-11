package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Person;

@XmlRootElement(name = "PersonEvent")
public class PersonDeletedEvent extends PersonEvent {

	private static final long serialVersionUID = -3351842758598749156L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private PersonDeletedEvent() {
	}
	
	public PersonDeletedEvent(String producer, 
			String producerReferenceId, 
			Person person, 
			PersonEventData personEventData) {
		super(producer, producerReferenceId, person, personEventData);
	}

	public PersonDeletedEvent(String producer, 
			String producerReferenceId, 
			Person person) {
		super(producer, producerReferenceId, person);
	}	
	
}
