<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    
    <xsl:param name="jobname"> turmeric-ci-runtime</xsl:param>
    <xsl:variable name="joburl">
      <xsl:text>https://www.ebayopensource.org/hudson/job/</xsl:text>
      <xsl:value-of select="substring-after($jobname, ' ')"/>
      <xsl:text>/api/xml</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="job" select="document($joburl)"/>
    
	<xsl:template match="/">
		<xsl:apply-templates select="$job/*/build[1]" ></xsl:apply-templates>
	</xsl:template>
   
   <xsl:template match="build">
      <xsl:variable name="url">
         <xsl:value-of select="url"/>
         <xsl:text>api/xml</xsl:text>
      </xsl:variable>
      <xsl:apply-templates select="document($url)/freeStyleBuild" mode="build"/>
   
   </xsl:template>
   
   <xsl:template match="freeStyleBuild" mode="build">
      <xsl:text>Hudson job </xsl:text>
      <xsl:apply-templates select="fullDisplayName"/>
      <xsl:text>. </xsl:text>
      <xsl:apply-templates select="building"/>
      <xsl:apply-templates select="result"/>
      <xsl:text>. Culprit: </xsl:text>
      <xsl:value-of select="culprit/fullName"/>
      <xsl:text>.</xsl:text>
      <xsl:apply-templates select="url"/>
   </xsl:template>
  
   <xsl:template match="result">
      <xsl:text>Result: </xsl:text>
      <xsl:value-of select="."/>
   </xsl:template>
   
   <xsl:template match="url">
      <xsl:text>, </xsl:text>
      <xsl:value-of select="."/>
   </xsl:template>
   
   <xsl:template match="fullDisplName">
      <xsl:value-of select="."/>
      <xsl:text>. </xsl:text>
   </xsl:template>
   
   <xsl:template match="building">
      <xsl:text>Currently Building: </xsl:text>
      <xsl:value-of select="."/>
      <xsl:text>.</xsl:text>
   </xsl:template>
   
</xsl:stylesheet>