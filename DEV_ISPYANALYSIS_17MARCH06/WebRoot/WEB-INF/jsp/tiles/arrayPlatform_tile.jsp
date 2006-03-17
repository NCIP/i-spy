<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.ArrayPlatformType"%>

<fieldset class="gray">
<legend class="red">
	Select Array Platform
</legend>

	
<br/>	
&nbsp;&nbsp;<html:select property="arrayPlatform">
					<html:option value="<%=ArrayPlatformType.AGILENT.name()%>"><%=ArrayPlatformType.AGILENT.toString()%></html:option>
					</html:select>
			<html:errors property="arrayPlatform"/>
</fieldset>
