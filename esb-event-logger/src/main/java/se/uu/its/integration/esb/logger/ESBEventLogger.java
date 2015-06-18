package se.uu.its.integration.esb.logger;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.camel.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;

import se.uu.its.integration.model.common.ModelUtils;
import se.uu.its.integration.model.events.UUEvent;

public class ESBEventLogger {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public ESBEventLogger() {
	}
	
	EntityManagerFactory emf;

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		log.info("Setting entity manager factory " + emf);
		this.emf = emf;
	}
	 
	public boolean isNewEvent(Exchange e) {
		boolean isNewEvent = true;
		String producer = (String) e.getIn().getHeader("eventProducer");
		String producerReferenceId = (String) e.getIn().getHeader("eventProducerReferenceId");
		if (producer == null || producerReferenceId == null) {
			log.error("No producer or producerReferenceId headers. Can't check if event is new.");
			throw new RuntimeException("Invalid producer or ref id: " + producer + "/" + producerReferenceId);
		}
		EntityManager entityManager = this.emf.createEntityManager();
		//entityManager.getTransaction().begin();
		List<UUEvent> ev = entityManager
				.createQuery("select e from UUEvent e where "
						+ " e._producer = :producer "
						+ " and e._producerReferenceId = :producerReferenceId", 
						UUEvent.class)
				.setParameter("producer", producer)
				.setParameter("producerReferenceId", producerReferenceId)
				.getResultList();
		//entityManager.getTransaction().commit();
		//entityManager.close();
		isNewEvent = ev.isEmpty();
		log.debug("Event from producer " + producer + " with reference id " + producerReferenceId + " is new?: " + isNewEvent);
		return isNewEvent;
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
	
}
