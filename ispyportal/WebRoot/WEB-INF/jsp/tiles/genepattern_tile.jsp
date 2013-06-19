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
<legend class="red">Select Group and Time Points<b class="req">*</b></legend>
<app:help help="If you have chosen to compare groups with ONE fixed timepoint, only move two groups into the selected groups box. Your baseline group will be determined by the second group in the box. If you have chosen to compare groups ACROSS timepoints, you can move one group into the selected groups box. Your baseline group is now determined by the first timepoint you have chosen." />

<html:errors property="selectedGroups1"/>
<table align="center" border="0">
    <tr>
    	<td style="vertical-align:middle">Select 2 or More Groups
			<br/>
			<html:select styleId="nonselectedGroups"  style="width:200px" property="existingGroups">
			     <html:optionsCollection property="existingGroupsList"/>
			</html:select>
			<br/><br/>
			Select 1 or More Time Points
			<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:select multiple="true" property="timepoint" styleId="timePoint">
				<html:optionsCollection property="timepointCollection" /> 
			</html:select>
		</td>
			<td style="vertical-align:middle">
			<input onclick="removeSelected(document.getElementById('selectedGroups'))" value="<<" type="button"/><br/>
			<input onclick="moveGroupAndTime(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'), document.getElementById('timePoint'))" value=">>" type="button"/>
		</td>
		<td colspan="2" align="center" style="vertical-align:middle">Selected Groups
			<br/>
			<logic:notEmpty name="gpIntegrationForm" property="selectedGroups">
			<html:select styleId="selectedGroups" size="5" multiple="true" style="width:270px; overflow:none;" property="selectedGroups">
				<html:options property="selectedGroups"/>
			</html:select>
			</logic:notEmpty>
			<logic:empty name="gpIntegrationForm" property="selectedGroups">
			<html:select styleId="selectedGroups" size="5" multiple="true" style="width:270px; overflow:none;" property="selectedGroups">
			</html:select>
			</logic:empty>
			<br/>	
		</td>
	</tr>
</table>
</fieldset>

<script language="javascript">
function removeSelected(fbox) {
	var arrLookup = new Array();

	var fLength = 0;
	for(i = 0; i < fbox.options.length; i++) {
		if (fbox.options[i].selected && fbox.options[i].value != "") {
			continue;
		}
		else {
			var no = new Option();
			no.value = fbox.options[i].value;
			no.text = fbox.options[i].text;
			arrLookup[fLength] = no;
			fLength++;
		}
	}
	fbox.length = 0;
	for (i = 0; i < arrLookup.length; i++){
		fbox[i] = arrLookup[i];
	}
}
  
function moveGroupAndTime(fbox, tbox, timebox) {
	var groupOption = new Option();
	var timeOptionValue = "";
	var timeOptionText = "";
	//var totalTimePoints = timebox.options.length;
	for(i = 0; i < fbox.options.length; i++) {
		if (fbox.options[i].selected && fbox.options[i].value != "") {
			groupOption.value = fbox.options[i].value;
			groupOption.text = fbox.options[i].text;
		}
	}
	for(i = 0; i < timebox.options.length; i++) {
		if (timebox.options[i].selected && timebox.options[i].value != "") {
			if (timeOptionValue.length == 0){
				timeOptionValue = timebox.options[i].value;
				timeOptionText = timebox.options[i].text;
			}else {
				timeOptionValue = timeOptionValue + "-" + timebox.options[i].value;
				timeOptionText = timeOptionText + "-" +timebox.options[i].text;
			}
		}
	}

	
	var arrTboxValue = new Array();
	var arrTboxText = new Array();
	var tlength = 0;
	for (i = 0; i < tbox.options.length; i++) {
		if (tbox.options[i].value != ""){
			arrTboxValue[tlength] = tbox.options[i].value;
			arrTboxText[tlength] = tbox.options[i].text;
			tlength++;
		}
	}
	if (timeOptionValue.length == 0){
		//timeOptionText = "All";
		//timeOptionValue = "All";
		return;
	}
	tbox.length = 0;
	if (arrTboxValue.length == 0){
		var newOption = new Option();
		newOption.value = groupOption.value + "---" + timeOptionValue;
		newOption.text = groupOption.text + " --- " + timeOptionText;
		tbox[0] = newOption;
	}
	else {
		for (i = 0; i < arrTboxValue.length; i++){
			var op = new Option();

			op.value = arrTboxValue[i];
			op.text = arrTboxText[i];
			tbox[i] = op;
		}
		var op = new Option();
		op.value = groupOption.value + "---" + timeOptionValue;
		op.text = groupOption.text + " --- " + timeOptionText;
		for (i = 0; i < tbox.length; i++){
			if (tbox.options[i].value == op.value){
				op.value = "";
				break;
			}
		}
		if (op.value != "")
			tbox[tbox.length] = op;
	}
}
</script>
