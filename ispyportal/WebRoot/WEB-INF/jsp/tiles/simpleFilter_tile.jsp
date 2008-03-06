<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<fieldset class="gray" style="text-align:left">
<legend class="red">
	Filter Genes/Reporters
</legend>
<span id="confirm"></span>

<br/>
<logic:present name="gpIntegrationForm"> 
	&nbsp;&nbsp;Select a gene or reporter list: &nbsp;&nbsp;
		<html:select property="geneSetName" styleId="geneList" disabled="false" onfocus="javascript:getGeneList()">
			<option>none</option>
			<html:optionsCollection property="geneSetNameList" />
		</html:select>
</logic:present>

<script language="javascript">

	function getGeneList(){
    	UserListHelper.getGeneSymbolListNames(createGeneList);
	}
	function createGeneList(data){    	
    	DWRUtil.removeAllOptions("geneList", data);
    	DWRUtil.addOptions("geneList", ['none']) 
    	DWRUtil.addOptions("geneList", data);
    	
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


</fieldset>	