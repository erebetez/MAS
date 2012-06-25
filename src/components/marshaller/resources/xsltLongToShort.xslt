<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="no" encoding="utf-8"
		omit-xml-declaration="yes" />
	<xsl:strip-space elements="*" />
	<xsl:template match="/marshallenvelop">
		<E>
			<xsl:attribute name="v"><xsl:value-of
				select="concat(format-number(@major,'#'),'.',format-number(@minor,'#'))" /></xsl:attribute>
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</E>
	</xsl:template>
	<xsl:template match="//*[name()!= 'marshallenvelop']">
		<xsl:variable name="element-name">
			<xsl:choose>
				<xsl:when test="name() = 'item'">I</xsl:when>
				<xsl:when test="name() = 'member'">M</xsl:when>
				<xsl:when test="name() = 'dictionary'">D</xsl:when>
				<xsl:when test="name() = 'list'">L</xsl:when>
				<xsl:when test="name() = 'atom'">
					<xsl:choose>
						<xsl:when test="@*[local-name()='type'] = 'string'">s</xsl:when>
						<xsl:when test="@*[local-name()='type'] = 'dateTime'">z</xsl:when>
						<xsl:when test="@*[local-name()='type'] = 'decimal'">d</xsl:when>
						<xsl:when test="@*[local-name()='type'] = 'integer'">i</xsl:when>
						<xsl:when test="@*[local-name()='type'] = 'boolean'">b</xsl:when>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:element name="{$element-name}">
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="text()">
		<xsl:copy-of select="." />
	</xsl:template>
</xsl:stylesheet>