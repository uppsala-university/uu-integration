package se.uu.its.integration.model.events;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.group.Group;
import se.uu.its.integration.model.identity.Affiliation;

//@Entity
//@DiscriminatorValue("PersonEvent")
@XmlRootElement(name = "GroupEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class GroupEvent extends UUEvent {

	private static final long serialVersionUID = -5522728677632277563L;

	@Transient
	@XmlElementRef(type = Group.class, namespace = "http://www.uu.se/schemas/integration/2015/Group")
	protected Group group;	
	
	@Transient
	@XmlElementRef(type = GroupEventData.class, namespace = "http://www.uu.se/schemas/integration/2015/Events")
	protected GroupEventData groupEventData;

	protected GroupEvent() {
	}
	
	protected GroupEvent(String producer, String producerReferenceId, Group group, GroupEventData groupEventData) {
		super(producer, producerReferenceId);
		this.group = group;
		this.groupEventData = groupEventData;
	}

	protected GroupEvent(String producer, String producerReferenceId, Group group) {
		super(producer, producerReferenceId);
		this.group = group;
	}	
	
}