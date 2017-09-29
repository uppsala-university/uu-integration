package se.uu.its.integration.ladok2groups.service;

import org.springframework.stereotype.Component;

/**
 * Class prevents schedule jobs to run simultaneously. If a concurrent job is already running the caller will wait
 * for the running scheduled job to finnish before it continues. This method works for "fixedDelay" and "cron" job
 * that does not overlap in time. Which is the case for this application.
 */
@Component
public class ScheduledJobSynchronizer {

	public void executeExclusively(Runnable runnable) {
		synchronized (this) {
			runnable.run();
		}
	}

}
