package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Employee", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class Employee extends Affiliation {

	private static final long serialVersionUID = -1199776753461842540L;
	
	private Employee() {
	}
	
	public Employee(String identifier) {
		super(identifier);
	}

}
