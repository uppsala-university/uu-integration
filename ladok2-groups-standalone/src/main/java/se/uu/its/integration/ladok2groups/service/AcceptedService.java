package se.uu.its.integration.ladok2groups.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AcceptedService {
	
	static Log log = LogFactory.getLog(AcceptedService.class);
	
	@Scheduled(fixedDelay=30000)
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
