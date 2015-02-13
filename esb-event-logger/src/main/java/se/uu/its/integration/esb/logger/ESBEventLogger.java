package se.uu.its.integration.esb.logger;

import javax.xml.bind.JAXBException;

import se.uu.its.integration.model.common.ModelUtils;
import se.uu.its.integration.model.common.UUEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ESBEventLogger {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public ESBEventLogger() {
	}

	/**
	 * Register event to the event database if event is not already
	 * processed e.g. exists in event database.
	 * 
	 * @param xml The event described as XML.
	 * @return Returns true if event logged as processed for the first time, false
	 * if already logged. 
	 * @throws Exception 
	 */
	public boolean registerOnce(String xml) throws Exception {

		log.debug(xml);
		
		boolean success = true;
		
		try {
			UUEvent event = (UUEvent) ModelUtils.getUnmarchalledObject(UUEvent.class, xml);
			
			log.info("Logging event: " + event.getIdentifier());

			// If event is already processed...
			if (! true)
				success = false;
			
		} catch (JAXBException e) {
			
			log.error("Error processing event: " + e.getMessage());
			throw new ESBLoggerUnexpectedException(e.getMessage());
			
		}
		
		return success;
	}
	
}
