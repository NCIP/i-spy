<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*,gov.nih.nci.ispy.util.ispyConstants;" %> 


<fieldset class="gray">
<legend class="red">CNA Status
<app:help help="Select cna status to further filter the query" />
</legend>
	<div class="mains">
	      <div>
	      <logic:present name="fishQueryForm">
	        <html:checkbox property="cnaStatus" value="<%=ispyConstants.CNA_STATUS[0]%>" /><%=ispyConstants.CNA_STATUS[0]%>
	      	<html:checkbox property="cnaStatus" value="<%=ispyConstants.CNA_STATUS[1]%>" /><%=ispyConstants.CNA_STATUS[1]%>
	      	<html:checkbox property="cnaStatus" value="<%=ispyConstants.CNA_STATUS[2]%>" /><%=ispyConstants.CNA_STATUS[2]%>
	      </logic:present>
		  </div>
			
	</div>
</fieldset>
