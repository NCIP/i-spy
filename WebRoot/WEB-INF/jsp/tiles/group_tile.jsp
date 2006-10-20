<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<logic:notPresent name="categoricalCorrelationForm">
<legend class="red">Select Group<b class="req">*</b></legend>
<app:help help="If you have chosen to compare groups with ONE fixed timepoint, only move two groups into the selected groups box. Your baseline group will be determined by the second group in the box. If you have chosen to compare groups ACROSS timepoints, you can move one group into the selected groups box. Your baseline group is now determined by the first timepoint you have chosen." />
</logic:notPresent>

<logic:present name="categoricalCorrelationForm">
<legend class="red"><b>X-Axis</b></legend>
</logic:present>

<logic:present name="categoricalCorrelationForm">
<html:errors property="selectedGroups3"/>
<table align="center" border="0">
    <tr style="vertical-align:top">
    	<td>Existing Groups
			<br/>
			<html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
			     <html:optionsCollection property="existingGroupsList"/>
			</html:select>
		</td>
		<td style="vertical-align:middle">
			<input onclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button"/><br />
			<input onclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button"/>
		</td>
		<td>Selected Groups
			<br/>
			<html:select styleId="selectedGroups" size="5" multiple="true" style="width:200px" property="selectedGroups">
			</html:select>
		</td>
	</tr>
</table>
</logic:present>


<logic:present name="classComparisonForm">
	<span id="instruction"><b>&nbsp;</b></span>
	<html:errors property="selectedGroups1"/>
	<html:errors property="selectedGroups2"/>
	<html:errors property="selectedGroups3"/>
	<table align="center" border="0">
    	<tr style="vertical-align:top">
		<td>Existing Groups
	        <br/>
	        <html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
	           <html:optionsCollection property="existingGroupsList"/>
			</html:select>
		</td>
		<td style="vertical-align:middle">
			<input onclick="move($('selectedGroups'),$('nonselectedGroups'));initBaseline();" value="&lt;&lt;" type="button"/><br />
			<input onclick="move($('nonselectedGroups'),$('selectedGroups'));initBaseline();" value=">>" type="button"/>
		</td>
		<td>Selected Groups
			<br/>
			<html:select styleId="selectedGroups" size="5" multiple="true" style="width:200px; overflow:none;" property="selectedGroups">
			</html:select>
			<br/>	
			
			<div style="padding-top:10px; padding-left:5px;">
				<span style="cursor:pointer; border:1px solid; padding-right:3px; padding-left:3px;" onclick="javascript:moveUpList(document.getElementById('selectedGroups'));initBaseline();">&uarr;</span>
				<span style="cursor:pointer; border:1px solid; padding-right:3px; padding-left:3px;" onclick="javascript:moveDownList(document.getElementById('selectedGroups'));initBaseline();">&darr;</span>
				<span style="font-size:10px; font-family:arial; padding:10px;">
					Baseline : <span id="baseline">none</span>
					<input type="hidden" name="baselineGroup" id="baselineGroup"/>
				</span>
			</div>
		</td>
		</tr>
	</table>
  
  <script language="javascript">
	var lst = document.getElementById('selectedGroups');
	var nonlst = document.getElementById('nonselectedGroups');
		
	var bltag = " (baseline)";

	function initBaseline()	{
		
		//assumes two choices only for now
		var _timepoint = "";
		if(document.getElementsByName('timepointRange') && document.getElementsByName('timepointRange').length > 0)	{
			_timepoint = document.getElementsByName('timepointRange')[0].checked ? "fixed" : "across";
		}
		
		//changes instruction text
		var instruction = (_timepoint == "across") ? "<em><b>choose one group</b></em>" : "<em><b>choose 2 groups</b></em>";
		document.getElementById("instruction").innerHTML = instruction;
					
		var baseline = lst[lst.length-1];
		
		var lengthToDo = (_timepoint == "across") ? lst.length : lst.length-1;
		
		//remove the tag from the other ones
		for(var i=0; i<lengthToDo; i++)	{
			var currentBaseline = lst[i];
			if(currentBaseline.text.indexOf(bltag) != -1)	{
				currentBaseline.text = currentBaseline.text.substring(0, currentBaseline.text.indexOf(bltag));
				currentBaseline.style.color = '';
				currentBaseline.style.border = '';
			}
		}
				
		for(var i=0; i<nonlst.length; i++)	{
			var currentBaseline = nonlst[i];
			if(currentBaseline.text.indexOf(bltag) != -1)	{
				currentBaseline.text = currentBaseline.text.substring(0, currentBaseline.text.indexOf(bltag));
				currentBaseline.style.color = '';
				currentBaseline.style.border = '';
			}
		}		
		
		//dont add baseline for across
		if(_timepoint == "across")	{
			$("baseline").innerHTML = $F('timepointBaseline');
			$F("baselineGroup") = $F('timepointBaseline');
			return;
		}
		
		//add the tag to the new baseline
		if(baseline != null && baseline.text.indexOf(bltag) == -1)	{
			baseline.text += bltag;
			baseline.style.color="red";
			baseline.style.border="1px solid";
			var cleanbaseline = baseline.text.substring(0, baseline.text.indexOf(bltag));
			
			document.getElementById("baseline").innerHTML = cleanbaseline.length > 10 ? cleanbaseline.substring(0, 10)+"..." : cleanbaseline ;
			document.getElementById("baselineGroup").value = baseline.value;
		}
		
	
	}
	
	//initBaseline();
</script>
</logic:present>

</fieldset>
