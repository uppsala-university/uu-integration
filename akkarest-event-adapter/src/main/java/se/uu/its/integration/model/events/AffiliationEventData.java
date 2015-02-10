package se.uu.its.integration.model.events;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUEventData;
import se.uu.its.integration.model.common.UUEventDataProperty;

@XmlRootElement(name = "AffiliationEventData", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class AffiliationEventData extends UUEventData {

	private static final long serialVersionUID = -2618604061922069506L;
	
	@XmlElementWrapper(name = "AffiliationEventDataProperties", namespace = "http://www.uu.se/schemas/integration/2015/Events")	
	@XmlElement(name = "EventDataProperty", namespace = "http://www.uu.se/schemas/integration/2015/Events")
	List<UUEventDataProperty> eventDataProperties  = new ArrayList<UUEventDataProperty>();
	
	public AffiliationEventData() {
	}
	
	public void addEventPropertyData(UUEventDataProperty eventDataProperty) {
		eventDataProperties.add(eventDataProperty);
	}
}
