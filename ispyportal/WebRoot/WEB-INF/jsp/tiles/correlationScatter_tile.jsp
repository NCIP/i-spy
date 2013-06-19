<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script type='text/javascript' src='dwr/interface/ReporterLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<div class="report" style="padding:3px">   
<html:errors property="nogene" />

<fieldset class="gray" style="text-align:left">
<legend class="red">
	X-Axis
</legend>


<br/>
		
		
	
 	<html:errors property="xaxis" />
	<html:select style="margin-left:20px" styleId="xaxis" property="xaxis" onchange="ediv(this,'advX',this[1].value);">
		<html:option value="none">select a category...</html:option>
		<html:optionsCollection property="continuousCollection" />		
	</html:select>
	&nbsp;
	
	
	<div id="advX" style="display:none; margin-left:20px;">
	<br />
	       <span id="reporterStatusX">Search for a reporter below</span>
	       <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<html:text property="geneX" styleId="geneSymbolX" size="10" /><br /><br />
		   Array Platform:
				<html:select property="platformX" styleId="platformX">				    
				    <html:optionsCollection property="arrayPlatformCollection" />	
				</html:select>
			
			<input type="button" onclick="getReporterList(this, $('geneSymbolX').value , $('platformX').options[$('platformX').selectedIndex].value , $('reporterX').name);" id="lookupButtonX" value="lookup reporters">
			
			<html:errors property="reporterSelectionX" />
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <html:select  property="reporterX" styleId="reporterX">
				 	<html:option value="<bean:write name='correlationScatterForm' property='reporterX'/>"><bean:write name='correlationScatterForm' property='reporterX'/></html:option>
				</html:select>
			
			</div>
			<script type="text/javascript">
				ediv($('xaxis'),'advX',$('xaxis')[1].value);				
			</script>
			
	<br /><br />
	</div>	

</fieldset>	
	</div>
	
	
	<fieldset class="gray" style="text-align:left">
<legend class="red">
	Y-Axis
</legend>


<br/>
	
 	<html:errors property="yaxis" />
	<html:select style="margin-left:20px" property="yaxis" styleId="yaxis" onchange="ediv(this,'advY',this[1].value);">
		<html:option value="none">select a category...</html:option>
		<html:optionsCollection property="continuousCollection" />		
	</html:select>
	&nbsp;


	<div id="advY" style="display:none; margin-left:20px;">
	<br />
			<span id="reporterStatusY">Search for a reporter below</span>
	        <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<html:text property="geneY" styleId="geneSymbolY" size="10" /><br /><br />
		   Array Platform:
				<html:select property="platformY" styleId="platformY">
				    <html:optionsCollection property="arrayPlatformCollection" />			 	
				</html:select>
			
			<input type="button" onclick="getReporterList(this, $('geneSymbolY').value , $('platformY').options[$('platformY').selectedIndex].value , $('reporterY').name);" id="lookupButtonY" value="lookup reporters" />
			<html:errors property="reporterSelectionY" />
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <html:select property="reporterY" styleId="reporterY">
			        <html:option value="<bean:write name='correlationScatterForm' property='reporterY'/>"><bean:write name='correlationScatterForm' property='reporterY'/></html:option>
				</html:select>
			</div>
			
			<script type="text/javascript">
				ediv($('yaxis'),'advY',$('yaxis')[1].value);				
			</script>
			
			
			
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
  