<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:uuid="java://java.util.UUID">

	<xsl:variable name="uid" select="uuid:randomUUID()"/>

	<!-- Identity transform. -->
	<xsl:template match="@* | node()">
	<xsl:param name="appendage"/>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<!-- Add the id attribute to the root element. -->
	<xsl:template match="/*">
		<xsl:element name="uuie:{local-name(.)}">
		    <xsl:copy-of select="@*"/>
            <xsl:attribute name="identifier"><xsl:value-of select="$uid"/></xsl:attribute>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>