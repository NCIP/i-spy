<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%
/*
 *		this is the main tiles template for the form based pages
*/
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
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
				<td width="765"> 
					<Table cellpadding="4" cellspacing="2" border="0" width="100%"> 
						<tr><td>
						<div>
							<%-- include the main form --%>
							<tiles:insert attribute="mainForm"/> 
						</div>
						</td></tr>	
					</table>
				</td>
			</tr>
		</table>
	<%-- include footer --%>	
	<tiles:insert attribute="footer"/> 
	</div>
</body>
</html>