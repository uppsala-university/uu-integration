package se.uu.its.integration.model.identity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUEventData;
import se.uu.its.integration.model.common.UUEventDataProperty;

@XmlRootElement(name = "PersonEventData")
public class PersonEventData extends UUEventData {

	@XmlElementWrapper(name = "PersonEventDataProperties")	
	@XmlElement(name = "PersonEventDataProperty")
	List<UUEventDataProperty> personEventDataProperties  = new ArrayList<UUEventDataProperty>();
	
	public PersonEventData() {
	}
	
	public void addEventPropertyData(UUEventDataProperty eventDataProperty) {
		personEventDataProperties.add(eventDataProperty);
	}	
	
}
