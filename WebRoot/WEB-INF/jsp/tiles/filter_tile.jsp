<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

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
<logic:present name="principalComponentForm"> 
	<html:radio styleClass="radio" property="filterType" value="default" onclick="tdiv(this,'advFilter','pca');" />Default
	&nbsp;
</logic:present>
<logic:present name="hierarchicalClusteringForm"> 
	<html:radio styleClass="radio" property="filterType" value="default" onclick="tdiv(this,'advFilter','hc');" />Default
	&nbsp;
</logic:present>
<html:radio styleClass="radio" property="filterType" value="advanced" onclick="tdiv(this,'advFilter');"/>Advanced

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


	<div id="advFilter" style="display:none; margin-left:20px;">
	<br />
	        <span id="percentileError">&nbsp;</span>
	        
			<logic:present name="principalComponentForm"> 
			Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<html:text styleId="variancePercentile" property="variancePercentile" onblur="checkPercentile()" size="4"/>&nbsp;&nbsp;%
			</logic:present>			
			
			<logic:present name="hierarchicalClusteringForm"> 
			Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<html:text styleId="variancePercentile" property="variancePercentile" onblur="checkPercentile()" size="4"/>&nbsp;&nbsp;%
			</logic:present>
			
			&nbsp;&nbsp;Constrain by GeneList: &nbsp;&nbsp;
			    <html:select property="geneSetName" styleId="geneList" disabled="false" onfocus="javascript:getGeneList()">
				 	<option>none</option>
				 	<html:optionsCollection property="geneSetNameList" />
				</html:select>
			
			
			
	<br /><br />
	</div>	

</fieldset>	