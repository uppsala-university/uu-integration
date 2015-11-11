package se.uu.its.integration.model.group;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrganizationalGroup", namespace = "http://www.uu.se/schemas/integration/2015/Group")
public class OrganizationalGroup extends Group {

	private static final long serialVersionUID = -4564369078462622972L;

	@SuppressWarnings("unused")
	private OrganizationalGroup() {
	}
	
	public OrganizationalGroup(String name, String description) {
		super(name, description);
	}
	
}
