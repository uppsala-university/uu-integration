package se.uu.its.integration.model.group;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import se.uu.its.integration.model.common.UUIntegrationObject;
import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.RoleEvent;

@XmlSeeAlso({AffiliationEvent.class, PersonEvent.class, RoleEvent.class, OrganizationDepartmentMappingEvent.class})
@XmlRootElement(name = "Group", namespace = "http://www.uu.se/schemas/integration/2015/Group")
public abstract class Group extends UUIntegrationObject {

	private static final long serialVersionUID = -6510744307597252176L;

	@XmlElement(name = "Name", namespace = "http://www.uu.se/schemas/integration/2015/Group")
	protected String _name;
	
	@XmlElement(name = "Description", namespace = "http://www.uu.se/schemas/integration/2015/Group")
	protected String _description;
	
	@XmlElement(name = "DisplayName", namespace = "http://www.uu.se/schemas/integration/2015/Group")
	protected String _displayName;	

	@XmlAttribute(name = "type")
	String _type;	
	
	/**
	 * Needed for JAXB.
	 */		
	@SuppressWarnings("unused")
	protected Group() {
	}
	
	protected Group(String name, String description) {
		this._name = name;
		this._description = description;
		this._type = this.getClass().getSimpleName();
	}
	
	protected Group(String name, String description, String displayName) {
		this(name, description);
		this._displayName = displayName;
	}
	
}
