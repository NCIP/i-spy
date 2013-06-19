<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*,gov.nih.nci.ispy.util.ispyConstants;" %> 


<fieldset class="gray">
<legend class="red">Select Biomarkers
<a href="javascript: Help.popHelp('IHC_LOE_query_biomarkers_tooltip');">[?]</a></legend>

	<div class="mains">
	      <div>
	      <logic:present name="fishQueryForm">
	        <html:checkbox property="biomarkers" value="<%=ispyConstants.FISH_BIOMARKERS[0]%>" /><%=ispyConstants.FISH_BIOMARKERS[0]%>
	      	<html:checkbox property="biomarkers" value="<%=ispyConstants.FISH_BIOMARKERS[1]%>" /><%=ispyConstants.FISH_BIOMARKERS[1]%>
	      </logic:present>
	      
	       
		 <logic:present name="ihcLevelQueryForm">
		 	&nbsp;&nbsp;<html:select property="biomarkers" multiple="true">						
						<html:optionsCollection property="biomarkersCollection" />
			</html:select>		
		 </logic:present>
		 
		  <logic:present name="ihcLossQueryForm">
		 	&nbsp;&nbsp;<html:select property="biomarkers">						
						<html:optionsCollection property="biomarkersCollection" />
			</html:select>		
		 </logic:present>	  	      
		  </div>		
	</div>
</fieldset>
