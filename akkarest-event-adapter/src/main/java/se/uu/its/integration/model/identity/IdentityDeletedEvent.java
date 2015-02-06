package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "IdentityDeletedEvent")
@XmlRootElement(name = "IdentityEvent")
public class IdentityDeletedEvent extends IdentityEvent {

	/**
	 * Needed for JAXB.
	 */
	private IdentityDeletedEvent() {
	}		
	
//	public IdentityDeletedEvent(String identity) {
//		super(identity);
//	}
	
	public IdentityDeletedEvent(String producer, Identity identity) {
		super(producer, identity);
	}

}
