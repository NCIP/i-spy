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
		var bigflag = document.getElementsByName("filterType")[0].checked;
		
		if(!bigflag)	{
			if(win)	{ Effect.SlideDown('advFilter'); }
			else	{ $('advFilter').style.display = "block"; }
		}
		else	{
			if(win)	{ Effect.SlideUp('advFilter'); }
			else	{ $("advFilter").style.display = "none"; } 
		}
			
	}
</script>
<!-- 
<a href="#" onclick="Effect.SlideUp('advFilter');;return false;">Default</a>
<a href="#" onclick="Effect.SlideDown('advFilter');;return false;">Advanced</a>
-->
	<div id="advFilter" style="display:none; margin-left:20px;">
	<br /><br />
			<logic:present name="principalComponentForm"> 
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="99"/>&nbsp;&nbsp;%
			</logic:present>
			
			<logic:present name="hierarchicalClusteringForm"> 
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="95"/>&nbsp;&nbsp;%
			</logic:present>
	<br /><br />
	</div>	

</fieldset>	