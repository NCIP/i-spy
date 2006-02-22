<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Select Group<b class="req">*</b>

<logic:present name="principalComponentForm">
</legend>
<html:radio property="groupsOption" styleClass="radio" value="allSamples" onclick="onRadio(this,'allSamples');" />Show all samples<br /><br />

<html:radio property="groupsOption" styleId="variousSamplesRadio" styleClass="radio" value="variousSamples" onclick="onRadio(this,'variousSamples');" />Select samples<br />
<table align="center" border="0">
    <tr style="vertical-align:top">
      <td>Existing Groups
        <br/>
        <html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
             <html:optionsCollection property="existingGroupsList"/>
		</html:select>
      </td>
      <td style="vertical-align:middle">
        <input id="button1" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button" disabled="true" /><br />
        <input id="button2" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button" disabled="true" />
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
	<em>choose 2 groups</em>
	<html:errors property="selectedGroups"/>
	<table align="center" border="0">
    <tr style="vertical-align:top">
      <td>Existing Groups
        <br/>
        <html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
           <html:optionsCollection property="existingGroupsList"/>
		</html:select>
      </td>
      <td style="vertical-align:middle">
        <input onclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));initBaseline();" value="&lt;&lt;" type="button"/><br />
        <input onclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'));initBaseline();" value=">>" type="button"/>
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
		Baseline
		: <span id="baseline">none</span>
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
		
		var baseline = lst[lst.length-1];
		
		//remove the tag from the other ones
		for(var i=0; i<lst.length-1; i++)	{
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
		
		
		//add the tag to the new baseline
		if(baseline != null && baseline.text.indexOf(bltag) == -1)	{
			baseline.text += bltag;
			baseline.style.color="red";
			baseline.style.border="1px solid";
			document.getElementById("baseline").innerHTML = baseline.value.length > 10 ? baseline.value.substring(0, 10)+"..." : baseline.value ;
			document.getElementById("baselineGroup").value = baseline.value;
		}
		
	
	}
	
	//initBaseline();
</script>
</logic:present>

</fieldset>
