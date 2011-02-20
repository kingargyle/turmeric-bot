<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="issue"> 1261</xsl:param>
    <xsl:variable name="resturl">
        <xsl:text>https://www.ebayopensource.org/jira/si/jira.issueviews:issue-xml/TURMERIC-</xsl:text>
        <xsl:value-of select="substring-after($issue, ' ')"/>
        <xsl:text>/TURMERIC-</xsl:text>
        <xsl:value-of select="substring-after($issue, ' ')"/>
        <xsl:text>.xml</xsl:text>
    </xsl:variable>
    <xsl:variable name="issuexml" select="document($resturl)" />
        
	<xsl:output method="text"/>
	
	<xsl:template match="/">
	    <xsl:apply-templates select="$issuexml/rss/channel/item"/>
	</xsl:template>
	
	<xsl:template match="item">
		<xsl:text>Status: </xsl:text>
		<xsl:value-of select="status"/>
		<xsl:text> - </xsl:text>
		<xsl:value-of select="title"/>
		<xsl:text> - </xsl:text>
		<xsl:value-of select="link"/>
	</xsl:template>	
</xsl:stylesheet>