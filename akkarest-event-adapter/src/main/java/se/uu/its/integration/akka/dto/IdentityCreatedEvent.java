package se.uu.its.integration.akka.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="IdentityCreatedEvent")
public class IdentityCreatedEvent extends IdentityEvent {

	/**
	 * Needed for JAXB.
	 */	
	private IdentityCreatedEvent() {
	}	
	
	public IdentityCreatedEvent(String identity) {
		super(identity);
	}

}
