package se.uu.its.integration.service.group.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.camel.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.uu.its.integration.model.events.UUEvent;

public class IdentityPersistence {
	
	static Log log = LogFactory.getLog(IdentityPersistence.class);
	
	EntityManagerFactory emf;

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		log.info("Setting entity manager factory " + emf);
		this.emf = emf;
	}
	 
	public boolean isNewMessage(Exchange e) {
		boolean isNewMessage = true;
		String producer = (String) e.getIn().getHeader("uuieProducer");
		String producerReferenceId = (String) e.getIn().getHeader("uuieProducerReferenceId");
		if (producer == null || producerReferenceId == null) {
			log.error("No producer or producerReferenceId headers. Can't check if message is new.");
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
		isNewMessage = ev.isEmpty();
		log.debug("Message from producer " + producer + " with reference id "
				+ producerReferenceId + " is new?: " + isNewMessage);
		return isNewMessage;
	}
}

