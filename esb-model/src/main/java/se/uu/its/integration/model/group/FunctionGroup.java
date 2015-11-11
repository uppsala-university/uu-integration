package se.uu.its.integration.model.group;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FunctionGroup", namespace = "http://www.uu.se/schemas/integration/2015/Group")
public class FunctionGroup extends Group {

	private static final long serialVersionUID = -264911496152943199L;

	@SuppressWarnings("unused")
	private FunctionGroup() {
	}
	
	public FunctionGroup(String name, String description) {
		super(name, description);
	}	
	
}
