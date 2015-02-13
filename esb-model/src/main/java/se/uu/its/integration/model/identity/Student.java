package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Student", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Student extends Affiliation {

	private static final long serialVersionUID = 7671206260738297260L;

	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	private Student() {
	}
	
	public Student(String identifier) {
		super(identifier);
	}

}
