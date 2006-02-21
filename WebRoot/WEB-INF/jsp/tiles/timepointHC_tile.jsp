<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
Select Timepoint
</legend>
	
<br>
Timepoint:
&nbsp;&nbsp;<html:select property="timepoint">
					<html:optionsCollection property="timepointCollection" /> 
			</html:select>
			&nbsp;&nbsp;

			
			
</fieldset>
