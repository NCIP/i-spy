<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Select Group
<a href="javascript: Help.popHelp('IHC_Loss_query_select_group_tooltip');">[?]</a></legend>


	<html:errors property="patients" />
      <html:select style="margin-left:20px" property="patientGroup">			
			<html:option value="none">none</html:option>
			<html:optionsCollection property="patientGroupCollection" />
	  </html:select>
</fieldset>
