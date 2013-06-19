<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

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
	<html:option value="<%=ArrayPlatformType.CDNA_ARRAY_PLATFORM.name()%>"><%=ArrayPlatformType.CDNA_ARRAY_PLATFORM.toString()%></html:option>
</html:select>
			<html:errors property="arrayPlatform"/>
</fieldset>
