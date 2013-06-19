<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
	<title>I-SPY PCA Plots</title>
	<LINK href="css/tabs.css" rel="stylesheet" type="text/css" />
	
	<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
	
	<script language="JavaScript" src="js/box/browserSniff.js"></script>

<%		
//	<script type='text/javascript' src='dwr/interface/DynamicReport.js'></script>
//	<script type='text/javascript' src='dwr/engine.js'></script>
//	<script type='text/javascript' src='dwr/util.js'></script>
 
//	<script language="JavaScript" src="js/a_saveSamples.js"></script>
%> 
	<script language="JavaScript" src="js/box/x_core.js"></script>
	<script language="JavaScript" src="js/box/x_event.js"></script>
	<script language="JavaScript" src="js/box/x_dom.js"></script>
	<script language="JavaScript" src="js/box/x_drag.js"></script>
	<script language="JavaScript" src="js/box/wz_jsgraphics.js"></script>
	<script language="javascript" src="js/Help.js"></script>
	<script language="JavaScript" src="js/box/dbox.js"></script>
	<script type="text/javascript" src="js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
		
</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img align="right" src="images/close.png" border="0"></a>
	<script type="text/javascript">Help.insertHelp("PCA_report", " align='right'", "padding:2px;");</script>
	<a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help');" onmouseout="return nd();"/> </a> 
	
</span>
<div style="background-color: #ffffff"><img src="images/ispyPortalHeader.gif" /></div>


<%
String colorBy = request.getParameter("colorBy")!=null ? (String) request.getParameter("colorBy") : "ClinicalResponse"; 
String key = request.getParameter("key")!=null ? (String) request.getParameter("key") : "taskId";
String pcaView = request.getParameter("pcaView")!=null ? (String) request.getParameter("pcaView") : "PC1vsPC2";
%>


<div id="header">
	<ul id="primary">
		<li> 
		<%
		if(pcaView.equals("PC1vsPC2"))
			out.write("<span>PC1vsPC2</span>");
		else
			out.write("<a href=\"pcaReport.do?key="+key+"&pcaView=PC1vsPC2\">PC1vsPC2</a>");		
		%>
		</li>
		<li>
		<%
		if(pcaView.equals("PC1vsPC3"))
			out.write("<span>PC1vsPC3</span>");
		else
			out.write("<a href=\"pcaReport.do?key="+key+"&pcaView=PC1vsPC3\">PC1vsPC3</a>");		
		%>
		<li>
		<%
		if(pcaView.equals("PC2vsPC3"))
			out.write("<span>PC2vsPC3</span>");
		else
			out.write("<a href=\"pcaReport.do?key="+key+"&pcaView=PC2vsPC3\">PC2vsPC3</a>");		
		%>
	</ul>
</div>
<div id="main" style="font-family:arial; font-size:12px">
<div style="margin-left:10px">
<b>Color By: </b>
<%
if(colorBy.equals("ClinicalResponse"))
	out.write("Clinical Response");	
else
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=ClinicalResponse\">Clinical Response</a>");	
	
out.write("&nbsp; | &nbsp;");

if(colorBy.equals("DiseaseStage"))
	out.write("Disease Stage");
else	
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=DiseaseStage\">Disease Stage</a>");	
	
out.write("&nbsp; | &nbsp;");

if(colorBy.equals("Timepoint"))
	out.write("Timepoint");	
else	
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=Timepoint\">Timepoint</a>");	

%>
<br/>
</div>
<table>
	<tr>
		<td>
<% if(pcaView.equals("PC1vsPC2"))	{ %>
<graphing:PCAPlot taskId="<%=key%>" components="PC1vsPC2" colorBy="<%=colorBy%>" />
<% } %>
<% if(pcaView.equals("PC1vsPC3"))	{ %>
<p><graphing:PCAPlot taskId="<%=key%>" components="PC1vsPC3" colorBy="<%=colorBy%>" /></p>
<% } %>
<% if(pcaView.equals("PC2vsPC3"))	{ %>
<p><graphing:PCAPlot taskId="<%=key%>" components="PC2vsPC3" colorBy="<%=colorBy%>" /></p>
<% } %>
		</td>
		<td style="vertical-align:top">
		<div style="border:1px dashed silver;height:300px;width:100px; margin-left:10px; margin-top:30px; overflow:auto;" id="sample_list">
			<div style="background-color: #ffffff; width:100px; font-weight: bold; text-align:center;">Samples:</div><br/>
			<div style="font-size:9px; text-align:center;" id="sampleCount"></div><br/>
			<span id="pending_samples" style="font-size:11px"></span>
		</div>
		<br/>
		<div style="margin-left:10px; text-align:center">
		<%
			//<input type="text" id="sampleGroupName" name="sampleGroupName" style="width:95px"/><br/>
			//<input type="button" style="width:95px" value="save samples" onclick="javascript:A_saveSamples();" /><br/>			
		%>
			<a href="#" onclick="processQuickClinical(); return false;">view clinical data</a><br/><br/>		
		</div>
		<div style="margin-left:10px; font-size:11px; text-decoration:none; text-align:center;">
			<a href="#" onclick="javascript: if(confirm('clear samples?')) { clearPending();return false; } ">[clear samples]</a><br/>
		</div>
		</td>
	</tr>
</table>
</div>

<script language="javascript" src="js/box/lassoHelper.js"></script>


<!--  translate samples to clinical report -->
<form id="quickClinicalWrapper"></form>

<script language="javascript">

	function processQuickClinical()	{
		var f = document.getElementById("quickClinicalWrapper");
		
		//quickly clear the node, so we dont get duplicate elements when the back button is used
		while(f.firstChild) f.removeChild(f.firstChild);
		
		if(!f)	{ return; }
		//set up the form
		f.setAttribute("method", "post");
		f.setAttribute("action", "quickClinical.do?taskId=<%=key%>");
		f.setAttribute("name", "quickClinicalWrapper");
		
		
		for(var i=0; i<pendingSamples.length; i++)	{
			var hid = document.createElement("input");
			hid.setAttribute("type", "hidden");
			hid.setAttribute("name", "sampleList");
			hid.setAttribute("value", pendingSamples[i]);
			f.appendChild(hid);
		}
		var otherHid = document.createElement("input");
		otherHid.setAttribute("type", "hidden");
		otherHid.setAttribute("name", "fromGraph");
		otherHid.setAttribute("value", "true");
		f.appendChild(otherHid);
		f.submit();
	}
</script>


</body>
</html>

