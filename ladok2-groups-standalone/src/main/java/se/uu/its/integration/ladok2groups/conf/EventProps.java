package se.uu.its.integration.ladok2groups.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="events")
public class EventProps {
	
	String regUpdateStart = "2007-01-01 000000";

	public String getRegUpdateStart() {
		return regUpdateStart;
	}

	public void setRegUpdateStart(String regUpdateStart) {
		this.regUpdateStart = regUpdateStart;
	}
	
}
