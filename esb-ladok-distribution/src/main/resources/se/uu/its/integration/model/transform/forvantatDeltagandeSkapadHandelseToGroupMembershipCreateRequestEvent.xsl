<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group"
	xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events"
	xmlns:uuii="http://www.uu.se/schemas/integration/2015/Identity"	
	xmlns:sd="http://schemas.ladok.se/studiedeltagande"
	xmlns:events="http://schemas.ladok.se/events"
	xmlns:base="http://schemas.ladok.se"
	exclude-result-prefixes="uuig uuie base events uuii">

	<xsl:output method="xml" omit-xml-declaration="no" indent="yes" standalone="yes"/>

	<xsl:param name="studentId" />
	<xsl:param name="kurstillfalleKod" />
	<xsl:param name="personNumber" />

	<xsl:template match="/">

		<uuie:GroupEvent xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group" xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events" xmlns:uuii="http://www.uu.se/schemas/integration/2015/Identity">
			<xsl:attribute name="type">GroupMembershipCreateRequestEvent</xsl:attribute>
		    <uuie:IssuedTime><xsl:value-of select="sd:ForvantatDeltagandeSkapadHandelse/events:Handelsetid"/></uuie:IssuedTime>
		    <uuie:Producer>Ladok</uuie:Producer>
		    <uuie:ProducerReferenceId><xsl:value-of select="sd:ForvantatDeltagandeSkapadHandelse/events:HandelseUID"/></uuie:ProducerReferenceId>
		    <uuig:Group>
		    	<xsl:attribute name="type">StudentGroup</xsl:attribute>
		        <uuig:Name>hkslab:<xsl:value-of select="$kurstillfalleKod" /></uuig:Name>
		        <uuig:Description>This is the first Group</uuig:Description>
		    </uuig:Group>
		    <uuie:GroupEventData>
		        <uuie:GroupEventDataProperties/>
		        <uuii:NewMember>
					<xsl:attribute name="identifier">
						<xsl:value-of select="$studentId" />
					</xsl:attribute>
		            <uuii:PersonNumber><xsl:value-of select="$personNumber" /></uuii:PersonNumber>
		        </uuii:NewMember>
		    </uuie:GroupEventData>
		</uuie:GroupEvent>

	</xsl:template>

</xsl:stylesheet>