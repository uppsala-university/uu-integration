@XmlSchema(
	xmlns = {
			@javax.xml.bind.annotation.XmlNs(prefix = "uuie", namespaceURI="http://www.uu.se/schemas/integration/2015/Events"),
			@javax.xml.bind.annotation.XmlNs(prefix = "uuii", namespaceURI="http://www.uu.se/schemas/integration/2015/Identity"),
			@javax.xml.bind.annotation.XmlNs(prefix = "uuig", namespaceURI="http://www.uu.se/schemas/integration/2015/Group")
		   },
		   
    namespace = "http://www.uu.se/schemas/integration/2015/Events", 
    elementFormDefault = XmlNsForm.QUALIFIED) 

package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
