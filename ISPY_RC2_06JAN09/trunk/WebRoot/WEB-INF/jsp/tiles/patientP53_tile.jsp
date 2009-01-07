<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Select Group
<a href="javascript: Help.popHelp('select_group_tooltip_P53');">[?]</a></legend>
</legend>

	<html:errors property="patients" />
      <html:select style="margin-left:20px" property="patientGroup">			
			<html:option value="none">none</html:option>
			<html:optionsCollection property="patientGroupCollection" />
	  </html:select>
</fieldset>
