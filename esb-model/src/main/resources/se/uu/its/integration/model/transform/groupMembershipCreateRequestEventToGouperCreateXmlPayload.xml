<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group"
	xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events"
	xmlns:uuii="http://www.uu.se/schemas/integration/2015/Identity"
	exclude-result-prefixes="uuig uuie uuii">

	<xsl:output method="xml" omit-xml-declaration="no" indent="no" />

	<xsl:template match="/">

		<WsRestAddMemberRequest>
  			<wsGroupLookup>
    			<groupName><xsl:value-of select="uuie:GroupEvent/uuig:Group/uuig:Name"/></groupName>
  			</wsGroupLookup>
  			<subjectLookups>
    			<WsSubjectLookup>
      				<subjectId><xsl:value-of select="uuie:GroupEvent/uuie:GroupEventData/uuii:NewMember/@identifier"/></subjectId>
    			</WsSubjectLookup>
			</subjectLookups>
		</WsRestAddMemberRequest>

	</xsl:template>

</xsl:stylesheet>