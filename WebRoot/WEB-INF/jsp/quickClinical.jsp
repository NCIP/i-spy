<%@ page language="java" %>
<%@ page import="java.util.*, java.lang.*, java.net.URLEncoder " %>
<%@ page import="java.util.ArrayList, gov.nih.nci.ispy.web.reports.quick.*" %>
<%
	String[] sampleArray = request.getParameterValues("sampleList");
	StringBuffer sb = new StringBuffer();
	
	if(sampleArray != null && sampleArray.length > 0)	{
		List samples = Arrays.asList(sampleArray);		
		sb = QuickClinicalReport.quickSampleReport(samples);
	}
	else	{
		sb.append("<br/><br/><b>No Samples Selected.  Please click back, and then select some samples.</b><br/>");
	}

%>
<html>
	<head>
		<link href="xsl/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/stripeScript.js"></script>
		<script type="text/javascript">
		
			window.onload = function()	{
				Stripe.stripe('reportTable');
			};
		
		</script>
	</head>
	<body>
		<div>
			<a href="javascript:history.back()">&lt;&lt;&lt;back</a><br/>
		</div>
		<%= sb.toString() %>
	</body>
</html>