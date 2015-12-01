<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group"
	xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events"
	xmlns:ui="http://schemas.ladok.se/utbildningsinformation"
	xmlns:events="http://schemas.ladok.se/events"
	xmlns:base="http://schemas.ladok.se"
	exclude-result-prefixes="uuig uuie base events ui">

	<xsl:output method="xml" omit-xml-declaration="no" indent="yes" standalone="yes"/>

	<xsl:template match="/">
<!--  
		<uuie:GroupEvent xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group" xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events" xmlns:uuii="http://www.uu.se/schemas/integration/2015/Identity" type="GroupCreateRequestEvent">
-->
		<uuie:GroupEvent xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group" xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events" xmlns:uuii="http://www.uu.se/schemas/integration/2015/Identity">
		<xsl:attribute name="type">GroupCreateRequestEvent</xsl:attribute>
		<uuie:IssuedTime><xsl:value-of select="ui:KurstillfalleTillStatusHandelse/events:Handelsetid"/></uuie:IssuedTime>
		<uuie:Producer>Ladok</uuie:Producer>
		<uuie:ProducerReferenceId><xsl:value-of select="ui:KurstillfalleTillStatusHandelse/events:SelfRef/base:Uid"/></uuie:ProducerReferenceId>
<!--  
		<uuig:Group type="StudentGroup">
-->
		<uuig:Group>
			<xsl:attribute name="type">StudentGroup</xsl:attribute>
			<uuig:Name>hkslab:<xsl:value-of select="ui:KurstillfalleTillStatusHandelse/ui:Utbildningstillfalleskod"/></uuig:Name>
			<uuig:Description>Det här är en automatiskt genererad deltagarlista för ett kurstillfälle.</uuig:Description>
			<uuig:DisplayName><xsl:value-of select="ui:KurstillfalleTillStatusHandelse/ui:Utbildningstillfalleskod"/></uuig:DisplayName>
			</uuig:Group>
		</uuie:GroupEvent>

	</xsl:template>

</xsl:stylesheet>