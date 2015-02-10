package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "Organization", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Organization extends UUIntegrationObject {

	private static final long serialVersionUID = -9167789779962890441L;

	@XmlAttribute(name = "identifier")
	private String identifier;
	
	public Organization(String identifier) {
		this.identifier = identifier;
	}
	
	private Organization() {
	}
	
}
