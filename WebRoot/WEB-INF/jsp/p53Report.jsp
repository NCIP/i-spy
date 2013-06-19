<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.*" %>
<%@ page import="java.util.ArrayList" %>

<html>
	<head>
		<title>ISPY Report</title>
		<script language="JavaScript" src="js/box/browserSniff.js"></script>
			<script language="javascript" src="js/Help.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="xsl/js.js"></script>
		<script language="JavaScript" type="text/javascript" src="xsl/a_js.js"></script> 
		<script language="JavaScript" type="text/javascript" src="xsl/a_saveSamples.js"></script>
		 <LINK href="xsl/css.css" rel="stylesheet" type="text/css" />
		 
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
		
	</head>
<body>

<script type='text/javascript' src='dwr/interface/DynamicReport.js'></script>
<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<%


//get the main params from the request
//these will actually be given to the XSL for transformation via AJAX

String key = "test";
if(request.getParameter("taskId")!=null)
	key = (String) request.getParameter("taskId");
	

String newReport="";
if(request.getParameter("newReport")!=null){
	newReport = (String) request.getParameter("newReport");	
		if(session.getAttribute("clinical_tmpSampleList")!=null){
			ArrayList al = new ArrayList();
			al = (ArrayList) session.getAttribute("clinical_tmpSampleList");
			al.clear();
		}
	}


String xhtml = "nada";
if(session.getAttribute(key+"_xhtml")!=null)	{
	//display the report, and run some init JS functions on the page
	xhtml = (String) session.getAttribute(key+"_xhtml");
	out.println(xhtml);
	out.println("<script language='javascript'>A_initSaveSample(); checkStep(); initSortArrows();</script>");
	session.removeAttribute(key+"_xhtml");
}
else	{

	String p_highlight = "";
	if(request.getParameter("p_highlight")!=null)
		p_highlight = (String) request.getParameter("p_highlight");
	String p_highlight_op = "gt";
	if(request.getParameter("p_highlight_op")!=null)
		p_highlight_op = (String) request.getParameter("p_highlight_op");
	
	//some times these are off, as they are dynamically set after this at times
	String p_page = "0";
	if(request.getParameter("p_page")!=null)
		p_page = (String) request.getParameter("p_page");
	String p_step = "25";
	if(request.getParameter("p_step")!=null)
		p_step = (String) request.getParameter("p_step");
	
	
	
	//for sorting
	String p_sort_method = request.getParameter("p_sort_method")!=null ? (String) request.getParameter("p_sort_method") : "ascending";
	String p_sort_element = request.getParameter("p_sort_element")!=null ? (String) request.getParameter("p_sort_element") : "";
	
	%>
	<div id="imgContainer" style="display:block; position:absolute;">
		<br/>
		<center><img src="images/circleStatusGray200.gif" /></center>
	</div>
	<script language="javascript">
		
		var reportC = document.getElementById("reportContainer");
		var imgC = document.getElementById("imgContainer");
		//center the img
		if(!saf)	{
			imgC.style.top = document.body.clientHeight/2-100;
			imgC.style.left = document.body.clientWidth/2-100;
		}
		
		if(saf)	{
			imgC.style.width = "100%";
		}
		
		function A_getReport(key)	{
			var a = new Object();
			a["key"] = "<%=key%>";
			a["p_highlight"] = "<%=p_highlight%>";
			a["p_highlight_op"] = "<%=p_highlight_op%>";
			
			a["p_page"] = "<%=p_page%>";
			a["p_step"] = "<%=p_step%>";			
			
			a["p_sort_element"] = "<%=p_sort_element%>";
			a["p_sort_method"] = "<%=p_sort_method%>";
			
			//a["two"] = "atwo";
			//var a = { key1:"value1", key2:"value2" };
			DynamicReport.generateDynamicReport(key, a, "p53_report.xsl" , A_getReport_cb);
		}
		
		function A_getReport_cb(html)	{
			//html is nothing now
			imgC.style.display = "none";
			location.replace(window.location);
			//location.replace("/ispy/loeReport.do?taskId=<%=key%>");
		}
		
		setTimeout("A_getReport('<%=key%>')", 0100);
		
	</script>

<% 
} 
%>
</body>
</html>

