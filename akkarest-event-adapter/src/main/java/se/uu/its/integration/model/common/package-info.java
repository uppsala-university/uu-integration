@XmlSchema(
	xmlns = {
			@javax.xml.bind.annotation.XmlNs(prefix = "uuie", namespaceURI="http://www.uu.se/schemas/integration/2015/Events"),
			@javax.xml.bind.annotation.XmlNs(prefix = "uuii", namespaceURI="http://www.uu.se/schemas/integration/2015/Identity")
		   },
		   
    namespace = "http://www.uu.se/schemas/integration/2015/Events", 
    elementFormDefault = XmlNsForm.QUALIFIED) 
package se.uu.its.integration.model.common;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;