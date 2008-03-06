<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">  
<xsl:variable name="name" select="list/@name" />

    <table border="1">
    <tr bgcolor="#9acd32">
      <th align="left">Patient</th>      
    </tr>
    <xsl:for-each select="list/item">
    <tr>
      
      <xsl:variable name="id" select="value" />
      <td id="{$id}of{$name}"><xsl:value-of select="value"/></td>
            
    </tr>
    </xsl:for-each>
    </table>
  
</xsl:template>

</xsl:stylesheet>