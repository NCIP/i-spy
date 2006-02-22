<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>

<fieldset class="gray">
<legend class="red">
	Filter Genes/Reporters
</legend>
<span id="confirm"></span>

<html:radio styleClass="radio" property="filterType" value="default" />Default<br /><br />

<html:radio styleClass="radio" property="filterType" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advFilter','pm');return false;">&nbsp;+&nbsp;</a>

	<div id="advFilter" class="divHide">
		<br>
			<logic:present name="principalComponentForm"> 
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="99"/>&nbsp;&nbsp;%
			</logic:present>
			
			<logic:present name="hierarchicalClusteringForm"> 
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="95"/>&nbsp;&nbsp;%
			</logic:present>
			
	
		</div>	  
	  
	