package se.uu.its.integration.ladok2groups.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AcceptedEventService {
	
	static Log log = LogFactory.getLog(AcceptedEventService.class);
	
	@Scheduled(fixedDelayString = "${app.AcceptedEventService.updateAcceptedMembers.delay}")
	public void updateAcceptedMembers() throws Exception {
		System.out.println("Helu schedule: " + System.currentTimeMillis());
	}
	
	@Scheduled(cron="0 0 4 * * *")
	public void everyNightAtFour() {
		System.out.println("It's four in the morning!");
	}

	@Scheduled(cron="*/10 * * * * *")
	public void everyTenSeconds() {
		System.out.println("It's been 10 seconds!");
	}
}
