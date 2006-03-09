<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.*" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.ispy.web.factory.ApplicationFactory" %>


<fieldset class="gray">
<legend class="red">		
		Name Analysis Result
<b class="req">*</b>
</legend>
<br>
	<html:errors property="analysisResultName"/>
<html:text styleId="analysisResultName" property="analysisResultName" size="50"  /> (should be unique)
<br />
<html:errors property="queryName"/><br />
</fieldset>

<%		
		BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();
		Collection sessionFindings = btc.getAllSessionFindings(session.getId());
		String queryNames = "";
		
		if(sessionFindings!=null && !sessionFindings.isEmpty())	{	
			for(Iterator i = sessionFindings.iterator();i.hasNext();)	{		
					// Finding f = (Finding) o;
					Finding f = (Finding) i.next();
					String queryName = "N/A";
					//q&d
					try	{
					queryName =  f.getQueryDTO().getQueryName();
					}
					catch(Exception e)	{
					queryName = f.getTaskId();
					}
				if (queryNames.length() > 0){
					  	 queryNames += ",";
				}
				if (queryName != null && queryName.trim().length() > 0){
						 queryNames += '"'+queryName+'"';
				}	
			}
		}
%>

<script language="javascript">
	
	function checkQueryName()	{		
			//alert("yog");
			var thisQueryName = document.forms[0].analysisResultName.value;
			
			<%
					out.println("\t\t\tvar queryNameArray = new Array("+queryNames+");");
			%>
			var found = false;
			if (!(thisQueryName == null || thisQueryName == "")) {
				for(var t=0;t<queryNameArray.length; t++)	{
				  if (thisQueryName == queryNameArray[t]) found = true;
				}
				if (found) {
					  if (confirm("Query Name exists in system. This action will overwrite existing query")) {
				  		return true;
					  }
			 	}else {return true;}
			 }
			 	
			 	return false;
		 
	}
</script>





