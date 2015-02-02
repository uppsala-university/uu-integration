package se.uu.its.integration.akka.dto;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class AKKAEvent {

	@XmlAttribute(name = "time")
	protected  Calendar eventTime;
	
	protected AKKAEvent() {
		this.eventTime = GregorianCalendar.getInstance();
	}
	
}
