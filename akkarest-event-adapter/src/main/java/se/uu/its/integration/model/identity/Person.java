package se.uu.its.integration.model.identity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "Person", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Person extends UUIntegrationObject {

	private static final long serialVersionUID = -2352347322813468432L;

	@XmlAttribute(name = "identifier")
	private String identifier;
	
	@XmlElement(name = "PersonNumber", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private String personNumber;
	
	@XmlElement(name = "Forname", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private String forname;
	
	@XmlElement(name = "Lastname", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private String lastname;

	@XmlElementWrapper(name = "Affiliations", namespace = "http://www.uu.se/schemas/integration/2015/Identity")	
	@XmlElements({
	    @XmlElement(name = "Student", type = Student.class, namespace = "http://www.uu.se/schemas/integration/2015/Identity"),
	    @XmlElement(name = "Employee", type = Employee.class, namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	})
	List<Affiliation> affiliations  = null;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private Person() {
	}
	
	public Person(String personNumber, String forname, String lastname) {
		this.identifier = personNumber;
		this.personNumber = personNumber;
		this.forname = forname;
		this.lastname = lastname;
	}
	
	public void AddAffiliation(Affiliation affiliation) {
		if (affiliations == null) 
			affiliations = new ArrayList<Affiliation>();	
		this.affiliations.add(affiliation);
	}
	
}
