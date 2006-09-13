<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script type='text/javascript' src='dwr/interface/ReporterLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	
</script>

<div class="report" style="padding:3px">   

<fieldset class="gray" style="text-align:left">
<legend class="red">
	X-Axis
</legend>
<span id="confirm"></span>

<br/>

 
	<html:select style="margin-left:20px" styleId="xaxis" property="xaxis" onchange="ediv(this,'advX',this[1].value);">
		<html:option value="none">select a category...</html:option>
		<html:optionsCollection property="continuousCollection" />		
	</html:select>
	&nbsp;


	<div id="advX" style="display:none; margin-left:20px;">
	<br />
	       <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<html:text property="geneX" styleId="geneSymbolX" size="10" /><br /><br />
		   Array Platform:
				<html:select property="platformX" styleId="platformX">				    
				    <html:optionsCollection property="arrayPlatformCollection" />	
				</html:select>
			
			<input type="button" onclick="getReporterList(this, $('geneSymbolX').value , $('platformX').options[$('platformX').selectedIndex].value , $('reporterX').name);" id="lookupButtonX" value="lookup reporters">
			<span id="reporterStatus">&nbsp;</span>
			
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <html:select  property="reporterX" styleId="reporterX">
				 	<html:option value="availableReporters1">none</html:option>
				</html:select>
			
			</div>
			
			
	<br /><br />
	</div>	

</fieldset>	
	</div>
	
	
	<fieldset class="gray" style="text-align:left">
<legend class="red">
	Y-Axis
</legend>
<span id="confirm"></span>

<br/>

 
	<html:select style="margin-left:20px" property="yaxis" styleId="yaxis" onchange="ediv(this,'advY',this[1].value);">
		<html:option value="none">select a category...</html:option>
		<html:optionsCollection property="continuousCollection" />		
	</html:select>
	&nbsp;


	<div id="advY" style="display:none; margin-left:20px;">
	<br />
	        <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<html:text property="geneY" styleId="geneSymbolY" size="10" /><br /><br />
		   Array Platform:
				<html:select property="platformY" styleId="platformY">
				    <html:optionsCollection property="arrayPlatformCollection" />			 	
				</html:select>
			
			<input type="button" onclick="getReporterList(this, $('geneSymbolY').value , $('platformY').options[$('platformY').selectedIndex].value , $('reporterY').name);" id="lookupButtonY" value="lookup reporters" />
			<span id="reporterStatus">&nbsp;</span>
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <html:select property="reporterY" styleId="reporterY">
			        <html:option value="none">none</html:option>
				 	<html:option value="availableReporters1">none</html:option>
				</html:select>
			</div>
			
			
			
			
	<br /><br />
	</div>	

</fieldset>	

<div class="report" style="padding:3px">
 
<fieldset class="gray">
<legend class="red">		
		Correlation Method
</legend>
<br>
	<html:select style="margin-left:20px" property="correlationMethod">		
		<html:optionsCollection property="correlationMethodCollection" />		
	</html:select>

<br />
<br />
</fieldset>






	</div>
  