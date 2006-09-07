<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script type='text/javascript' src='dwr/interface/ReporterLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	function getReporterList(element, gene, platform, elementToUpdate){
		
    	ReporterLookup.lookup(gene, platform, elementToUpdate, createReporterList);
	}
	function createReporterList(txt){  
	 	
    	try	{
				var res = eval('(' + txt + ')');
				
				var result = res[0].results;
				var elementToUpdate = res[0].elementId;
				var reporters = res[0].reporters;
				
				if(result == "found"){				
					DWRUtil.removeAllOptions(elementToUpdate);    			
    				DWRUtil.addOptions(elementToUpdate, reporters);
    			}
    			else{
    				DWRUtil.removeAllOptions(elementToUpdate);    			
    				DWRUtil.addOptions(elementToUpdate, ['none']);
    				alert("No reporters found for: " + res[0].gene);
    			}
			}
		catch(err){
				alert(err);
			}    	
	}
</script>

<div class="report" style="padding:3px">   

<fieldset class="gray" style="text-align:left">
<legend class="red">
	X-Axis
</legend>
<span id="confirm"></span>

<br/>

 
	<html:select styleId="xaxis" property="xaxis" onchange="ediv(this,'advX',this[1].value);">
		<html:option value="none">select a category...</html:option>
		<html:optionsCollection property="continuousCollection" />		
	</html:select>
	&nbsp;


	<div id="advX" style="display:none; margin-left:20px;">
	<br />
	       <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<input type="text" id="geneSymbolX" size="10" /><br /><br />
		   Array Platform:
				<html:select property="platformX" styleId="platformX">				    
				    <html:optionsCollection property="arrayPlatformCollection" />	
				</html:select>
			
			<input type="button" onclick="getReporterList(this, $('geneSymbolX').value , $('platformX').options[$('platformX').selectedIndex].value , $('reporterX').name);" id="lookupButtonX" value="lookup reporters">
			<span id="reporterStatus">&nbsp;</span>
			
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <html:select property="reporterX" styleId="reporterX">
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

 
	<html:select property="yaxis" styleId="yaxis" onchange="ediv(this,'advY',this[1].value);">
		<html:option value="none">select a category...</html:option>
		<html:optionsCollection property="continuousCollection" />		
	</html:select>
	&nbsp;


	<div id="advY" style="display:none; margin-left:20px;">
	<br />
	        <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<input type="text" id="geneSymbolY" size="10" /><br /><br />
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
	<html:select property="correlationMethod">		
		<html:optionsCollection property="correlationMethodCollection" />		
	</html:select>

<br />
<br />
</fieldset>






	</div>
  