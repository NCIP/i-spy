<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
long randomness = System.currentTimeMillis(); //prevent image caching
%>
<script language="javascript">
document.forms[0].target = "_self";
</script>

<div align="center">
<html:reset styleId="clearButton" styleClass="xbutton" value="clear" />&nbsp;&nbsp;
<html:submit styleId="submittalButton" styleClass="subButton" property="method" value="submit" onclick="javascript:saveMe(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));return checkNull(document.forms[0].analysisResultName)"> 
</html:submit>



