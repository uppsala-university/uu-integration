package se.uu.its.integration.model.events;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUEventData;
import se.uu.its.integration.model.common.UUEventDataProperty;

@XmlRootElement(name = "PersonEventData", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class PersonEventData extends UUEventData {

	private static final long serialVersionUID = -4519431852455973732L;

	@XmlElementWrapper(name = "PersonEventDataProperties", namespace = "http://www.uu.se/schemas/integration/2015/Events")	
	@XmlElement(name = "PersonEventDataProperty", namespace = "http://www.uu.se/schemas/integration/2015/Events")
	List<UUEventDataProperty> personEventDataProperties  = new ArrayList<UUEventDataProperty>();
	
	public PersonEventData() {
	}
	
	public void addEventPropertyData(UUEventDataProperty eventDataProperty) {
		personEventDataProperties.add(eventDataProperty);
	}	
	
}
