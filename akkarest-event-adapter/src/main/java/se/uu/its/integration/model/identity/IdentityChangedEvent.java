package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IdentityEvent")
public class IdentityChangedEvent extends IdentityEvent {

	/**
	 * Needed for JAXB.
	 */	
	private IdentityChangedEvent() {
	}
	
	public IdentityChangedEvent(String producer, String identity) {
		super(producer, identity);
	}
	
	public IdentityChangedEvent(String producer, Identity identity) {
		super(producer, identity);
	}

	public IdentityChangedEvent(String producer, Identity identity, IdentityEventData identityEventData) {
		super(producer, identity, identityEventData);
	}	
}

