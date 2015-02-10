package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.OrganizationDepartmentMapping;

@XmlRootElement(name = "OrganizationDepartmentMappingEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class OrganizationDepartmentMappingChangedEvent extends
		OrganizationDepartmentMappingEvent {

	private static final long serialVersionUID = 5817790543832271652L;
	
	@SuppressWarnings("unused")
	private OrganizationDepartmentMappingChangedEvent() {
	}

	public OrganizationDepartmentMappingChangedEvent(String producer, String producerReferenceId, OrganizationDepartmentMapping orgDepMapping) {
		super(producer, producerReferenceId, orgDepMapping);
	}
}
