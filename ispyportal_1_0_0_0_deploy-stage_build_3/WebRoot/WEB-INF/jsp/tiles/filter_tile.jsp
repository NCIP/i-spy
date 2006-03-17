<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>

<fieldset class="gray" style="text-align:left">
<legend class="red">
	Filter Genes/Reporters
</legend>
<span id="confirm"></span>

<br/>
<html:radio styleClass="radio" property="filterType" value="default" onclick="tdiv(this);" />Default
&nbsp;
<html:radio styleClass="radio" property="filterType" value="advanced" onclick="tdiv(this);"/>Advanced

<script language="javascript">

	function tdiv(el)	{

		//look at the 1st radio...this wont work with more than 2 options
		var bigflag = document.getElementsByName("filterType")[0] ? document.getElementsByName("filterType")[0].checked : true;
		
		if(!bigflag)	{
			if(win)	{ Effect.BlindDown('advFilter'); }
			else	{ $('advFilter').style.display = "block"; }
		}
		else	{
			if(win)	{ Effect.BlindUp('advFilter'); }
			else	{ $("advFilter").style.display = "none"; } 
		}
			
	}
	
	
	function checkPercentile(){
	   //look at percentile make sure it is between 0 and 100
	   var percentile = document.getElementById("variancePercentile").value;
	   
	   if(percentile >= 0 && percentile < 100 && percentile != ""){
	   	return true;
	   }
	   else{
	   	document.getElementById("variancePercentile").value = 0;
	   	document.getElementById("percentileError").innerHTML="<p align='center'><div class='err'><img src='images/err_y.png' align='left'>Percentile can only be a number from 0 to 100</div></p><br />";
	   	return false;
	   }
	}
	
</script>


	<div id="advFilter" style="display:none; margin-left:20px;">
	<br /><br />
	        <span id="percentileError">&nbsp;</span>
	        
			<logic:present name="principalComponentForm"> 
			Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<html:text styleId="variancePercentile" property="variancePercentile" onblur="checkPercentile()" size="4"/>&nbsp;&nbsp;%
			</logic:present>
			
			<logic:present name="hierarchicalClusteringForm"> 
			Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<html:text styleId="variancePercentile" property="variancePercentile" onblur="checkPercentile()" size="4"/>&nbsp;&nbsp;%
			</logic:present>
	<br /><br />
	</div>	

</fieldset>	