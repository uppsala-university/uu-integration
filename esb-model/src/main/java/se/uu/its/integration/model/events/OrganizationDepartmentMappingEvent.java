package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.OrganizationDepartmentMapping;

@XmlRootElement(name = "OrganizationDepartmentMappingEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class OrganizationDepartmentMappingEvent extends UUEvent {

	@XmlElementRef
	private OrganizationDepartmentMapping orgDepMapping;
	
	protected OrganizationDepartmentMappingEvent() {
	}
	
	protected OrganizationDepartmentMappingEvent(String producer, 
			String producerReferenceId,
			OrganizationDepartmentMapping orgDepMapping) {
		super(producer, producerReferenceId);
		this.orgDepMapping = orgDepMapping;
	}
	
	private static final long serialVersionUID = 5545577433036254509L;

}
