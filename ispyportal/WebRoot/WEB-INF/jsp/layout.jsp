<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %><%
/*
 *		this is the main tiles template for the form based pages
*/
%>
<app:checkLogin name="logged" page="/index.jsp" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><tiles:getAsString name="title"/></title>
	<tiles:insert attribute="htmlHead"/> <%-- include html head --%>
</head>

<body>

	 <%-- include div for overlib --%>
    <tiles:insert attribute="overlib"/>
    <%-- include header --%>
    <tiles:insert attribute="header"/>
	<div class="content">
		<%-- include crumb menu --%>
		<tiles:insert attribute="crumbMenu"/> 
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td width="575"> 
					<table cellpadding="4" cellspacing="2" border="0" width="100%"> 
					<tr class="report"><td><b><tiles:getAsString name="title"/></b></td></tr>
						<tr><td>
						<tiles:insert attribute="tabs"/>
						<div id="main">
							<%-- include the main form --%>
							<tiles:insert attribute="mainForm"/> 
						</div>
						</td></tr>	
						<tr><td align="left" width="100%">
							<%-- include required message note --%>
							<tiles:insert attribute="reqdFieldsMsg"/><br/>
						</td></tr>
					</table>
				</td>
				<td valign="top" class="sideBar">
					<%-- include sidebar --%>
				    <tiles:insert attribute="sideBar"/> 
				</td>
			</tr>
		</table>
	<%-- include footer --%>	
	<tiles:insert attribute="footer"/> 
	</div>
</body>
</html>