package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.group.Group;

@XmlRootElement(name = "GroupEvent")
public class GroupMembershipCreateRequestEvent extends GroupEvent {

	private  static final long serialVersionUID = -2489746152057220353L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private GroupMembershipCreateRequestEvent() {
	}	

	public GroupMembershipCreateRequestEvent(String producer, 
			String producerReferenceId, 
			Group group, 
			GroupEventData groupEventData) {
		super(producer, producerReferenceId, group, groupEventData);
	}	
	
}
