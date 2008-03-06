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
	<title>I-SPY Categorical Plots</title>
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
	<script type="text/javascript">Help.insertHelp("Explanatory_data_analysis_plot", " align='right'", "padding:2px;");</script>
	<a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help');" onmouseout="return nd();"/> </a> 
	
</span>
<div style="background-color: #ffffff"><img src="images/hiccc_header_04.png" /></div>


<%
String colorBy = request.getParameter("colorBy")!=null ? (String) request.getParameter("colorBy") : "ClinicalResponse"; 
String key = request.getParameter("key")!=null ? (String) request.getParameter("key") : "taskId";
%>


<table>
	<tr>
		<td>
		<graphing:CatCorrPlot taskId="<%=key%>" colorBy="<%=colorBy%>" />

		</td>
		
	</tr>
</table>
</div>

</body>
</html>

