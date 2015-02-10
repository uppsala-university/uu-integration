package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Role;

@XmlRootElement(name = "RoleEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class RoleCreatedEvent extends RoleEvent {

	private static final long serialVersionUID = -6422519057794636309L;

	/**
	 * Needed for JAXB.
	 */	
	@SuppressWarnings("unused")
	private RoleCreatedEvent() {
	}
	
	public RoleCreatedEvent(String producer, String producerReferenceId, Role role) {
		super(producer, producerReferenceId, role);
	}

}
