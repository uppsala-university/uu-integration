package se.uu.its.integration.model.group;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StudentGroup", namespace = "http://www.uu.se/schemas/integration/2015/Group")
public class StudentGroup extends Group {

	private static final long serialVersionUID = -4180900375458970379L;

	@SuppressWarnings("unused")
	private StudentGroup() {
	}
	
	public StudentGroup(String name, String description) {
		super(name, description);
	}	

}
