package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "Identity", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Identity extends UUIntegrationObject {

	@XmlAttribute(name = "identifier")
	private String identifier;

//	@XmlElement(name = "Firstname", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
//	private String firstname;
//	
//	@XmlElement(name = "Lastname", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
//	private String lastname;
	
	@XmlElementRef
	private Person person;
	
	/**
	 * Needed for JAXB.
	 */	
	private Identity() {
	}
	
	public Identity(String identifier) {
		this.identifier = identifier;
	}
	
	public Identity(String identifier, Person person) {
		this.identifier = identifier;
		this.person = person;
	}
	
//	public void setFirstname(String name) {
//		this.firstname = name;
//	}
//	
//	public void setLastname(String lastname) {
//		this.lastname = lastname;
//	}
	
	public String getIdentifier() {
		return identifier;
	}
	
}
