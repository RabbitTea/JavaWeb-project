<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="UTF-8" doctype-public="-//W3C//DTD XHTML 1.0 Transi
tional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/> 
<xsl:template match="/">
	<html xmlns="http://www.w3.org/1999/xhtml">
	  <head>
	      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
	      <title><xsl:value-of select="view/header/title"></xsl:value-of></title>
	  </head>
	  <body>
	      <form>
	          <xsl:attribute name="name">
	             <xsl:value-of select="view/body/form/name"></xsl:value-of>
	          </xsl:attribute>
	          <xsl:attribute name="action">
	             <xsl:value-of select="view/body/form/action"></xsl:value-of>
	          </xsl:attribute>
	          <xsl:attribute name="method">
	             <xsl:value-of select="view/body/form/method"></xsl:value-of>
	          </xsl:attribute>
	          <br></br>
	          
	          <xsl:for-each select="view/body/form/textView">
	          
	             <xsl:value-of select="name"></xsl:value-of>
	             <input type="text" text-align="center">
	                <xsl:attribute name="value">
	                   <xsl:value-of select="value"></xsl:value-of>
	                </xsl:attribute>  
	             </input>
	        
	             <br></br>
	          </xsl:for-each>
	           <br></br>
	          
	          <input type="submit" onclick="window.location.href = 'WebContent/login.jsp'">
	             <xsl:attribute name="value">
	                <xsl:value-of select="view/body/form/buttonView/name"></xsl:value-of>
	             </xsl:attribute>
	          </input>
	          <br></br>
	      </form>
	  </body>
	</html>
		
</xsl:template>
</xsl:stylesheet>