<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>


<fieldset class="gray">
<legend class="red">
Select Timepoint<b class="req">*</b>
<app:help help="If you choose to compare groups with ONE fixed timepoint, your baseline group will be determined by the second group you choose below. If you choose to compare groups ACROSS timepoints, your baseline group is determined by the first timepoint you choose (to the left of vs.)" />
</legend>
	
<html:errors property="timepoints"/><br />
<table border="0">
	<tr>
		<td valign="top">
			<html:radio styleClass="radio" property="timepointRange" value="fixed" onclick="initBaseline();" />
			Fixed Timepoint:
		</td>
	   	<td>&nbsp;&nbsp;
	   		<html:select property="timepointBaseFixed">
				<html:optionsCollection property="timepointCollection" /> 
			</html:select>
			&nbsp;&nbsp;
		</td>
		<td valign="top">
			<html:radio styleClass="radio" property="timepointRange" value="across" onclick="initBaseline();" />
			Across Timepoints:
		</td>
	   	<td>&nbsp;&nbsp;
	   		<html:select property="timepointBaseAcross" styleId="timepointBaseline" onchange="initBaseline();" >
				<html:optionsCollection property="timepointCollection" /> 
			</html:select>
		</td>
		<td>vs.</td>
		<td>&nbsp;&nbsp;
			<html:select property="timepointComparison">
				<html:optionsCollection property="timepointCollection" /> 
			</html:select>
		</td>
	</tr>
</table>
			
			
</fieldset>
