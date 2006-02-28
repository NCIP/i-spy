<%@ page language="java" %>
<%@ page import="java.util.*, java.lang.*, java.net.URLEncoder " %>
<%@ page import="java.util.ArrayList, gov.nih.nci.ispy.web.reports.quick.*" %>
<%
	String[] sampleArray = request.getParameterValues("sampleList");
	StringBuffer sb = new StringBuffer();
	
	if(sampleArray != null && sampleArray.length > 0)	{
	/*
		for(int i=0; i<sampleArray.length; i++)	{
			out.println(sampleArray[i] + "<br/>");
		}
	*/	
		List samples = Arrays.asList(sampleArray);
		
		sb = QuickClinicalReport.quickSampleReport(samples);
	}

%>
<html>
<head>
	<LINK href="xsl/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div>
	<a href="javascript:history.back()">&lt;&lt;&lt;back</a><br/>
</div>
	<%= sb.toString() %>
</body>
</html>