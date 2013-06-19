<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Invasive Range
<a href="javascript: Help.popHelp('IHC_Loss_query_invasiveSum_tooltip');">[?]</a></legend>

	<div class="mains">
	      <div>&nbsp;&nbsp;	
	      			invasive sum	      			        			
					<html:select property="invasiveRangeOperator">						
						<html:optionsCollection property="operators" />
					</html:select>
					<html:text property="invasiveRange" size="3" />				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Benign Range
<a href="javascript: Help.popHelp('IHC_Loss_query_benignSum_tooltip');">[?]</a></legend>

	<div class="mains">
	      <div>&nbsp;&nbsp;	
	      			benign sum	      			        			
					<html:select property="benignRangeOperator">						
						<html:optionsCollection property="operators" />
					</html:select>
					<html:text property="benignRange" size="3" />				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Result Code
<a href="javascript: Help.popHelp('IHC_Loss_query_invasiveSum_tooltip');">[?]</a></legend>

	<div class="mains">
	      <div>	 &nbsp;&nbsp;       			
					<html:select property="lossResult" multiple="true" size="3">						
						<html:optionsCollection property="lossCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>


