package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name="IdentityCreatedEvent")
@XmlRootElement(name="IdentityEvent")
public class IdentityCreatedEvent extends IdentityEvent {

	/**
	 * Needed for JAXB.
	 */	
	private IdentityCreatedEvent() {
	}	
	
	public IdentityCreatedEvent(String producer, String identity) {
		super(producer, identity);
	}

}
