<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<fieldset class="gray">
<legend class="red">
Select Timepoint 
<b class="req">*</b></legend>

<html:errors property="timepoints"/><br />
<table border="0">
	<tr><td valign="top">Timepoint:</td>
	    
			<td>&nbsp;&nbsp;<html:select multiple="true" property="timepoints">
								<html:optionsCollection property="timepointCollection" /> 
							</html:select>
			&nbsp;&nbsp;</td>
	
	</tr>	
</table>
			
			
</fieldset>
