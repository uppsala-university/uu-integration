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
	
	public AffiliationChangedEvent(String producer, String affiliationIdentifier) {
		super(producer, affiliationIdentifier);
	}
	
	public AffiliationChangedEvent(String producer, Affiliation affiliation) {
		super(producer, affiliation);
	}

	public AffiliationChangedEvent(String producer, Affiliation affiliation, AffiliationEventData affiliationEventData) {
		super(producer, affiliation, affiliationEventData);
	}	
}

