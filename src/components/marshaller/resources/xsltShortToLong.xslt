<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" encoding="utf-8" />
	<xsl:template match="/E">
		<marshallenvelop>
			<xsl:attribute name="major"><xsl:value-of
				select="format-number(substring-before(@v, '.'),'00')" /></xsl:attribute>
			<xsl:attribute name="minor"><xsl:value-of
				select="format-number(substring-after(@v, '.'),'00')" /></xsl:attribute>
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</marshallenvelop>
	</xsl:template>
	<xsl:template match="//L">
		<list>
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</list>
	</xsl:template>
	<xsl:template match="//D">
		<dictionary>
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</dictionary>
	</xsl:template>

	<xsl:template match="//I">
		<item>
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</item>
	</xsl:template>
	<xsl:template match="//M">
		<member>
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</member>
	</xsl:template>
	<xsl:template match="//s">
		<atom type="string">
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</atom>
	</xsl:template>
	<xsl:template match="//z">
		<atom type="dateTime">
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</atom>
	</xsl:template>

	<xsl:template match="//d">
		<atom type="decimal">
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</atom>
	</xsl:template>
	<xsl:template match="//i">
		<atom type="integer">
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</atom>
	</xsl:template>
	<xsl:template match="//b">
		<atom type="boolean">
			<xsl:apply-templates select="./*|text()|processing-instruction()" />
		</atom>
	</xsl:template>
	<xsl:template match="text()">
		<xsl:copy-of select="." />
	</xsl:template>
</xsl:stylesheet>
