<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<div class="report" style="padding:3px">   

<fieldset class="gray" style="text-align:left">
<legend class="red">
	X-Axis
</legend>
<span id="confirm"></span>

<br/>

 
	<select name="x" onchange="ediv(this,'advX',this[1].value);">
		<option value="none">select a category...</option>
		<option value="gene">gene</option>
		<option value="ld1">% LD T1_T2</option>
		<option value="ld2">% LD T1_T3</option>
		<option value="ld3">% LD T1_T4</option>
	</select>
	&nbsp;


	<div id="advX" style="display:none; margin-left:20px;">
	<br />
	       <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<input type="text" name="geneSymbol" size="10" ><br /><br />
		   Array Platform:
				<select name="platform">
				    <option value="oligo">oligo</option>
				 	<option value="agilent">agilent</option>
				</select>
			
			<br /><br />
			<input type="button" id="lookupButton" value="lookup reporters">
			<span id="reporterStatus">&nbsp;</span>
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <select name="availableReporters" id="availableReporters"><option>none</option>
				 	<option value="availableReporters1">none</option></select>
			
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

 
	<select name="x" onchange="ediv(this,'advY',this[1].value);">
		<option value="none">select a category...</option>
		<option value="gene">gene</option>
		<option value="ld1">% LD T1_T2</option>
		<option value="ld2">% LD T1_T3</option>
		<option value="ld3">% LD T1_T4</option>
	</select>
	&nbsp;


	<div id="advY" style="display:none; margin-left:20px;">
	<br />
	        <div style="margin-left:20px;padding:5px;border: 1px dotted red"> 
	       Gene Symbol:&nbsp;&nbsp;
				<input type="text" name="geneSymbol" size="10" ><br /><br />
		   Array Platform:
				<select name="platform">
				    <option value="oligo">oligo</option>
				 	<option value="agilent">agilent</option>
				</select>
			
			<br /><br />
			<input type="button" id="lookupButton2" value="lookup reporters">
			<span id="reporterStatus">&nbsp;</span>
			<br /><br />
			
			Available Reporters: &nbsp;&nbsp;
			    <select name="availableReporters" id="availableReporters"><option>none</option>
				 	<option value="availableReporters1">none</option></select>
			
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
	<select name="cm"">		
		<option value="pearson">pearson</option>
		<option value="ld1">spearman</option>		
	</select>

<br />
<br />
</fieldset>






	</div>
  