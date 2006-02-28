<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<fieldset class="gray">
<legend class="red">
Select Timepoint
</legend>
	
<html:errors property="timepoints"/><br />
<table border="0">
	<tr><td valign="top">
			<html:radio styleClass="radio" property="timepointRange" value="fixed" />
			Fixed Timepoint:
		</td>
	   	<td>&nbsp;&nbsp;<html:select property="timepointBaseFixed">
								<html:optionsCollection property="timepointCollection" /> 
						</html:select>
			&nbsp;&nbsp;
		</td>
		<td valign="top">
			<html:radio styleClass="radio" property="timepointRange" value="across" />
			Across Timepoints:
		</td>
	   	<td>&nbsp;&nbsp;<html:select property="timepointBaseAcross">
								<html:optionsCollection property="timepointCollection" /> 
						</html:select>
			
		</td>
		<td>vs.</td>
		<td>&nbsp;&nbsp;<html:select property="timepointComparison">
								<html:optionsCollection property="timepointCollection" /> 
						</html:select>
			
		</td>
				
</table>
			
			
</fieldset>
