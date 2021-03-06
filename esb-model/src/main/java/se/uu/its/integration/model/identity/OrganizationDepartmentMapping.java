package se.uu.its.integration.model.identity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.common.UUIntegrationObject;

@XmlRootElement(name = "OrganizationDepartmentMapping", namespace = "http://www.uu.se/schemas/integration/2015/Identity")
public class OrganizationDepartmentMapping extends UUIntegrationObject {

	private static final long serialVersionUID = 6244901645709167722L;

	@XmlElementRef
	private Organization organization;
	
	@XmlElement
	private String ladokDepartmentCode;
	
	public OrganizationDepartmentMapping() {
	}
	
	public OrganizationDepartmentMapping(Organization organization, String ladokDepartmentCode) {
		this.organization = organization;
		this.ladokDepartmentCode = ladokDepartmentCode;
	}
	
	public OrganizationDepartmentMapping(String organizationId, String ladokDepartmentCode) {
		this.organization = new Organization(organizationId);
		this.ladokDepartmentCode = ladokDepartmentCode;
	}
}
