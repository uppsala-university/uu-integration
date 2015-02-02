package se.uu.its.integration.akka.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="IdentityDeletedEvent")
public class IdentityDeletedEvent extends IdentityEvent {

	/**
	 * Needed for JAXB.
	 */
	private IdentityDeletedEvent() {
	}		
	
	public IdentityDeletedEvent(String identity) {
		super(identity);
	}

}
