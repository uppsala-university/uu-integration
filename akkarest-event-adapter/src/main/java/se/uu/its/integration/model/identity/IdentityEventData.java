package se.uu.its.integration.model.identity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUEventData;
import se.uu.its.integration.model.common.UUEventDataProperty;

@XmlRootElement(name = "IdentityEventData")
public class IdentityEventData extends UUEventData {

	@XmlElementWrapper(name = "IdentityEventDataProperties")	
	@XmlElement(name = "IdentityEventDataProperty")
	List<UUEventDataProperty> identityEventDataProperties  = new ArrayList<UUEventDataProperty>();
	
	public IdentityEventData() {
	}
	
	public void addEventPropertyData(UUEventDataProperty eventDataProperty) {
		identityEventDataProperties.add(eventDataProperty);
	}
}
