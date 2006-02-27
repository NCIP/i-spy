<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<fieldset class="gray">
<legend class="red">
Select Timepoint 
<b class="req">*</b></legend>

<html:errors property="timepoints"/><br />
<table border="0">
	<tr><td valign="top">Timepoint:</td>
	    <logic:present name="hierarchicalClusteringForm"> 
			<td>&nbsp;&nbsp;<html:select multiple="true" property="timepoints">
								<html:optionsCollection property="timepointCollection" /> 
							</html:select>
			&nbsp;&nbsp;</td>
		</logic:present>
		
		<logic:present name="principalComponentForm"> 
			<td>&nbsp;&nbsp;<html:select property="timepoints">
								<html:optionsCollection property="timepointCollection" /> 
							</html:select>
			&nbsp;&nbsp;</td>
		</logic:present>
	</tr>	
</table>
			
			
</fieldset>
