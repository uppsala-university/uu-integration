package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "Affilitation", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Affiliation extends UUIntegrationObject {

	private static final long serialVersionUID = -4494236157832316698L;

	@XmlAttribute(name = "identifier")
	private String identifier;

	@XmlElementRef
	private Person person;
	
	/**
	 * Needed for JAXB.
	 */	
	protected Affiliation() {
	}
	
	public Affiliation(String identifier) {
		this.identifier = identifier;
	}
	
	public Affiliation(String identifier, Person person) {
		this.identifier = identifier;
		this.person = person;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
}
