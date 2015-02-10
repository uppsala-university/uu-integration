package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "Role", namespace ="http://www.uu.se/schemas/integration/2015/Identity")
public class Role extends UUIntegrationObject {
	
	private static final long serialVersionUID = -2598200142511032271L;

	@XmlElements({
	    @XmlElement(name = "Student", type = Student.class, namespace = "http://www.uu.se/schemas/integration/2015/Identity"),
	    @XmlElement(name = "Employee", type = Employee.class, namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	    
	})	
	private Affiliation affiliation;
	
	@XmlElementRef
	private Organization organization;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private Role() {
	}

	public Role(Affiliation affiliation, Organization organization) {
		this.affiliation = affiliation;
		this.organization = organization;
	}
	
}
