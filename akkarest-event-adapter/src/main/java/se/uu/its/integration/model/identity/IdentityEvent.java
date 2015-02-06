package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.common.UUEvent;

@XmlRootElement(name = "IdentityEvent")
public class IdentityEvent extends UUEvent {

	@XmlElementRef(type = Identity.class)
	private Identity identity;
	
	@XmlElementRef(type = IdentityEventData.class)
	private IdentityEventData identityEventData;
	
	protected IdentityEvent() {
	}
	
	protected IdentityEvent(String producer, String identity) {
		super(producer);
		this.identity = new Identity(identity);
	}
	
	protected IdentityEvent(String producer, Identity identity) {
		super(producer);
		this.identity = identity;
	}
	
	protected IdentityEvent(String producer, Identity identity, IdentityEventData identityEventData) {
		super(producer);
		this.identity = identity;
		this.identityEventData = identityEventData;
	}
	
	public Identity getIdentity(){
		return identity;
	}
		
}
