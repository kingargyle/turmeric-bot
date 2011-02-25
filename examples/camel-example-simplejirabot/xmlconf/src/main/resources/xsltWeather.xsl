<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="airport"> CMH</xsl:param>
	<xsl:variable name="resturl">
		<xsl:text>http://api.wunderground.com/auto/wui/geo/WXCurrentObXML/index.xml?query=</xsl:text>
		<xsl:choose>
			<xsl:when test="string-length(substring-after($airport, ' ')) = 0 ">
				<xsl:text>TNM</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="substring-after($airport, ' ')"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="weather"
		select="document($resturl)" />

	<xsl:output method="text" />
	<xsl:template match="/">
		<xsl:text> Current Weather Conditions at </xsl:text>
		<xsl:apply-templates select="$weather/current_observation" />
	</xsl:template>

	<xsl:template match="current_observation">
		<xsl:apply-templates select="observation_location" />
		<xsl:value-of select="weather" />
		<xsl:text>, </xsl:text>
		<xsl:value-of select="temperature_string" />
		<xsl:text> humidity </xsl:text>
		<xsl:value-of select="relative_humidity" />
		<xsl:text> wind </xsl:text>
		<xsl:value-of select="wind_string" />
		<xsl:text> making it feels like  </xsl:text>
		<xsl:value-of select="windchill_string" />
		<xsl:text>.</xsl:text>
	</xsl:template>

	<xsl:template match="observation_location">
		<xsl:value-of select="full" />
		<xsl:text>. Current Conditions are  </xsl:text>
	</xsl:template>

</xsl:stylesheet>