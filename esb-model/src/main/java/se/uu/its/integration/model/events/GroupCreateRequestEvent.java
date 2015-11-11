package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.group.Group;
import se.uu.its.integration.model.identity.Person;

@XmlRootElement(name = "GroupEvent")
public class GroupCreateRequestEvent extends GroupEvent {

	private static final long serialVersionUID = 2325281187450370804L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private GroupCreateRequestEvent() {
	}
	
	public GroupCreateRequestEvent(String producer, 
			String producerReferenceId, 
			Group group, 
			GroupEventData personEventData) {
		super(producer, producerReferenceId, group, personEventData);
	}

	public GroupCreateRequestEvent(String producer, 
			String producerReferenceId, 
			Group group) {
		super(producer, producerReferenceId, group);
	}	
	
}