<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
<%@ page import="gov.nih.nci.ispy.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.*" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.*" %>
<%@ page import="gov.nih.nci.caintegrator.exceptions.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="gov.nih.nci.ispy.service.findings.*" %>

<script language="javascript">
	if(location.href.indexOf("viewResults") == -1)	{
		location.replace("viewResults.do");
	}
</script>
<%
String helpLink = "<a href=\"javascript: spawn('help.jsp";
String helpLinkClose = "', 350, 500);\">"+
				"<img align=\"right\" src=\"images/help.png\" border=\"0\" "+
				"onmouseover=\"return overlib('Click here for additional information about the view results page.', CAPTION, 'Help', OFFSETX, -50);\" onmouseout=\"return nd();\">"+
				"</a><br clear=\"all\">";
%> 
<%=helpLink%>?sect=viewResults<%=helpLinkClose%>

      
           
     <fieldset>
     	<legend>Report Results</legend>
       	<div id="loadingMsg" style="color:red;font-weight:bold;">&nbsp;</div>
       	<ul>
     <%
     	//get the finding related HOA's
     	//will use scriptlet, as we arent accessing a Struts related form
     	//PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
		BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();
		Collection sessionFindings = btc.getAllSessionFindings(session.getId());
		if(sessionFindings!=null && !sessionFindings.isEmpty())	{
		
			//looks like we have some findings, generate the JS to check the status of them
			%>
			<script language="javascript" src="js/a_functions.js"></script>
			<script language="javascript">	
				//testMap("testingtesting");
				var customError = function(message)	{};
				DWREngine.setWarningHandler(customError);
				DWREngine.setErrorHandler(customError);
				
				setTimeout("A_checkAllFindingStatus('<%=session.getId()%>')", 0200);
				var vr_checker = setInterval("A_checkAllFindingStatus('<%=session.getId()%>')", 5000);

			</script>
			<%
			
			//why arent these in the backingbean?  - we need to check these directly from cache
			// because they are dynamic - we can not look at a copy placed in the backing bean
			//for(Object o : sessionFindings)	{ //no 1.5 stuff allowed
			for(Iterator i = sessionFindings.iterator();i.hasNext();)	{
	
				// Finding f = (Finding) o;
				Finding f = (Finding) i.next();
				String qname = "N/A";
				//q&d
				try	{
					qname =  f.getQueryDTO().getQueryName();
				}
				catch(Exception e)	{
					qname = f.getTaskId();
				}
				
				String comments = "";
				
				String currentStatus = "running";
				if(f.getStatus() == FindingStatus.Completed)
					currentStatus = "<b id=\"" +f.getTaskId() + "_status\">completed</b>  <img src='images/check.png' alt='complete' id=\"" + f.getTaskId() + "_image\"/>";
				else if(f.getStatus() == FindingStatus.Running)
					currentStatus = "<b id=\"" + f.getTaskId() + "_status\" >running</b> <img src='images/circle.gif' alt='running' id=\"" + f.getTaskId() + "_image\" />";
				else if(f.getStatus() == FindingStatus.Error)	{
				
					AnalysisServerException ase = (AnalysisServerException) btc.getObjectFromSessionCache(session.getId(), f.getTaskId()+"_analysisServerException");
				
					comments = ase != null && ase.getMessage() != null ? ase.getMessage() : "Unspecified Error";
				
					//this relates to a static obj and therefore is causing a caching effect...not good, should revisit the middle tier implementation for this
					//comments = f.getStatus().getComment() != null ? f.getStatus().getComment() : "Unspecified Error";
					
					//currentStatus = "<b id=\"" + f.getTaskId() + "_status\" ><a href=\"#\" onmouseover=\"return overlibWrapper('"+comments+"');return false;\" onmouseout=\"return nd();\" ><strong>error</strong></a></b> <img src='images/error.png' alt='error' id=\"" + f.getTaskId() + "_image\" />";
					comments = StringEscapeUtils.escapeJavaScript(comments);
					currentStatus = "<b id=\"" + f.getTaskId() + "_status\" ><script language=\"javascript\">document.write(showErrorHelp('"+comments+"','error'));</script></b> <img src='images/error.png' alt='error' id=\"" + f.getTaskId() + "_image\" />";
				}
				//System.out.println(f.getTaskId() + ": " + comments);
				
				out.println("<span style='color:red; float:right'>" + currentStatus + "</span> ");
				
				String onclick="";	
				if(f.getStatus()!= FindingStatus.Completed)	{
					onclick = "javascript:alert('Analysis Not yet complete');return false;";
				}
				
				//check the type of finding and create the appropriate link
				if(f instanceof ISPYClinicalFinding){
					out.println("<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('quickClinical.do?taskId=" + f.getTaskId() + "', 750, 500,'clinical_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Clinical)</i> ");
				}
				if(f instanceof ClassComparisonFinding){
					out.println("<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('testReport.do?key=" + f.getTaskId() + "', 750, 500,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(CC)</i> ");
				}
				if(f instanceof HCAFinding){
					out.println("<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('hcReport.do?key=" + f.getTaskId() + "', 750, 500,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + " </a> <i>(HC)</i> ");
				}
				if(f instanceof PrincipalComponentAnalysisFinding){
					out.println("<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('pcaReport.do?key=" + f.getTaskId() + "', 900, 600,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(PCA)</i> ");
				}
				
				out.println("<span style=\"font-size:10px\">(elapsed time: <span id=\"" + f.getTaskId() + "_time\" >" + f.getElapsedTime() + "</span>ms) </span>");
				out.println("</li>");
				out.println("<br clear=\"all\" />");
				out.println("<br clear=\"all\" />");
			}
		}
		else	{
			//no findings yet
			out.println("<strong>No Report Results at this time.</strong><br/><br/>");
		}
     
     
     %>
     </ul>
     <br/><br/>
     <div style="font-size:9px;text-align:center;">
     (CC) Class Comparison | 
     (HC) Hierarchical Clustering | 
     (PCA) Principal Component Analysis
     </div>
     
</fieldset>
<br /><br />
     