package se.uu.its.integration.model.events;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUEventData;
import se.uu.its.integration.model.common.UUEventDataProperty;
import se.uu.its.integration.model.identity.Person;

@XmlRootElement(name = "GroupEventData", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class GroupEventData extends UUEventData{

	private static final long serialVersionUID = 1788902282607510420L;

	@XmlElementWrapper(name = "GroupEventDataProperties", namespace = "http://www.uu.se/schemas/integration/2015/Events")	
	@XmlElement(name = "EventDataProperty", namespace = "http://www.uu.se/schemas/integration/2015/Events")
	List<UUEventDataProperty> eventDataProperties  = new ArrayList<UUEventDataProperty>();

	@XmlElement(name = "NewMember", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	Person newMembership = null;
	
	public GroupEventData() {
	}
	
	public GroupEventData(Person member) {
		this.newMembership = member;
	}
	
	public void addEventPropertyData(UUEventDataProperty eventDataProperty) {
		eventDataProperties.add(eventDataProperty);
	}	
	
	public void addNewMember(Person member) {
		this.newMembership = member;
	}
}
