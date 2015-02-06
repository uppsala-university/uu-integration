package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "Person", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Person extends UUIntegrationObject {

	@XmlAttribute(name = "identity", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private String identityRef;
	
	@XmlElement(name = "Forname", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private String forname;
	
	@XmlElement(name = "Lastname", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private String lastname;
	
	private Person() {
	}
	
	public Person(String forname, String lastname) {
		this.forname = forname;
		this.lastname = lastname;
	}
	
	public Person(String identityRef, String forname, String lastname) {;
		this.identityRef = identityRef;
		this.forname = forname;
		this.lastname = lastname;		
	}
}
