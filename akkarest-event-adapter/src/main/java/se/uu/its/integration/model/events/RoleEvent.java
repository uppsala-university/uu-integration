package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUEvent;
import se.uu.its.integration.model.identity.Role;

@XmlRootElement(name = "RoleEvent", namespace ="http://www.uu.se/schemas/integration/2015/Events")
public class RoleEvent extends UUEvent {

	private static final long serialVersionUID = -182661851842778841L;
	
	@XmlElementRef
	private Role role;
	
	protected RoleEvent() {
	}
	
	protected RoleEvent(String producer, String producerReferenceId, Role role) {
		super(producer, producerReferenceId);
		this.role = role;
	}
}
