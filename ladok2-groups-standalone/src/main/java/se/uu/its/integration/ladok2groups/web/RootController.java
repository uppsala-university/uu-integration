package se.uu.its.integration.ladok2groups.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.uu.its.integration.ladok2groups.service.AcceptedEventService;
import se.uu.its.integration.ladok2groups.service.RegistrationEventService;

@RestController
public class RootController {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@Autowired
	RegistrationEventService regEvents;
	
	@Autowired
	AcceptedEventService accEvents;
    
    @RequestMapping("")
	public String index() {
		try {
			regEvents.updateEvents();
			return "Testing group update done";
		} catch (RuntimeException e) {
			logger.error("index failed", e);
			throw e;
		}

	}
}
