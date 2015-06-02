package se.uu.its.integration.esb.logger;

import java.awt.Event;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import se.uu.its.integration.model.common.ModelUtils;
import se.uu.its.integration.model.events.UUEvent;
import se.uu.its.integration.esb.logger.ESBLoggerUnexpectedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;

public class ESBEventLogger {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public ESBEventLogger() {
	}

	/**
	 * Checks if is already processed e.g. exists in event database.
	 * 
	 * @param xml The event described as XML.
	 * @return Returns true if event logged as processed for the first time, false
	 * if already logged. 
	 * @throws Exception If event not is recognized.
	 */
	public boolean isEventProcessed(String xml) throws Exception {

		log.debug(xml);
		
		boolean success = true;
		
		try {
			UUEvent event = (UUEvent) ModelUtils.getUnmarchalledObject(UUEvent.class, xml);
			
			log.info("Check duplicate event: " + event.getProducer() + ":" + event.getProducerReferenceId() + " (" + event.getIdentifier() + ")");

			// If event is already processed...
			if (! true)
				success = false;
			
		} catch (JAXBException e) {
			
			log.error("Error processing event: " + e.getMessage());
			throw new ESBLoggerUnexpectedException(e.getMessage());
			
		}
		
		return success;
	}
	
	/**
	 * Logs the event to the event database.
	 * 
	 * @param xml The event described as XML.
	 * @return Proper HTTP response.
	 * @throws JAXBException 
	 */
	public Response logEvent(String xml) throws JAXBException {
		
		UUEvent event = (UUEvent) ModelUtils.getUnmarchalledObject(UUEvent.class, xml);
		
		log.info("Loggin event: " + event.getProducer() + ":" + event.getProducerReferenceId() + " (" + event.getIdentifier() + ")");
		
		ResponseBuilder builder = new ResponseBuilderImpl();
		builder.status(Status.ACCEPTED);
		builder.type(MediaType.APPLICATION_XML);
		builder.entity(xml);
		
		return builder.build();
	}
	
	public Object debuggo(Object o) {
		
		log.info("DEBUUUUUGOOO: " + o.getClass().getName());
		
		return o;
	}
	
	public Object kaboom(Object o) {
		throw new RuntimeException("Kaboom! " + o.toString());
	}
}
