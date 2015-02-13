package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Role;

@XmlRootElement(name = "RoleEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class RoleDeletedEvent extends RoleEvent {

	private static final long serialVersionUID = 8991269975037976860L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private RoleDeletedEvent() {
	}
	
	public RoleDeletedEvent(String producer, String producerReferenceId, Role role) {
		super(producer, producerReferenceId, role);
	}

}
