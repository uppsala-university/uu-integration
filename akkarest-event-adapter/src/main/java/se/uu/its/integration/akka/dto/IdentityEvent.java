package se.uu.its.integration.akka.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class IdentityEvent extends AKKAEvent {

	@XmlElement(name="Identity")
	String identity;
	
	protected IdentityEvent() {
	}
	
	protected IdentityEvent(String identity) {
		super();
		this.identity = identity;
	}
	
}
