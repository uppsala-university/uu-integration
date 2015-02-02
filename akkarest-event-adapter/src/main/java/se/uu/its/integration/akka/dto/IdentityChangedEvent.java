package se.uu.its.integration.akka.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="IdentityChangedEvent")
public class IdentityChangedEvent extends IdentityEvent {

	/**
	 * Needed for JAXB.
	 */	
	private IdentityChangedEvent() {
	}
	
	public IdentityChangedEvent(String identity) {
		super(identity);
	}

}

