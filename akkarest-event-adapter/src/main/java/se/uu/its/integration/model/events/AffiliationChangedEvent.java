package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Affiliation;

@XmlRootElement(name = "AffiliationEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class AffiliationChangedEvent extends AffiliationEvent {

	private static final long serialVersionUID = -4284046648676116674L;

	/**
	 * Needed for JAXB.
	 */	
	@SuppressWarnings("unused")
	private AffiliationChangedEvent() {
	}
	
	public AffiliationChangedEvent(String producer, String producerReferenceId, String affiliationIdentifier) {
		super(producer, producerReferenceId, affiliationIdentifier);
	}
	
	public AffiliationChangedEvent(String producer, String producerReferenceId, Affiliation affiliation) {
		super(producer, producerReferenceId, affiliation);
	}

	public AffiliationChangedEvent(String producer, String producerReferenceId, Affiliation affiliation, AffiliationEventData affiliationEventData) {
		super(producer, producerReferenceId, affiliation, affiliationEventData);
	}	
}

