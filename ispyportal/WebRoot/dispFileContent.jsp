<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>
<%@ page import="gov.columbia.c2b2.ispy.util.Constants"%>
<app:checkLogin name="logged" page="/index.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"">
<html>




<head>
<title>ISPY Post Analysis Portal-Login</title>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
<style type="text/css" media="screen">@import "css/tabs.css";</style>





<script type='text/javascript' src='files/prototype_1.js'></script>
<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>
<script type='text/javascript' src='js/lib/common/ManageLoads.js'></script>
<script type='text/javascript' src='dwr/interface/WebGroupDisplay.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>



<script type='text/javascript' src='js/lib/common/TextFormList.js'></script>
<script type='text/javascript' src='js/lib/common/FormChanger.js'></script>
<script type='text/javascript' src='js/lib/common/StatusMessage.js'></script>

 <script type="text/javascript" language="javascript" src="files/querystring.js"></script> 
<%-- <script defer src="files/ie_onload.js" type="text/javascript"></script>  --%>
</head>
<body>

<!--header NCI logo-->
<div style="background-color: #ffffff"><img src="images/hiccc_header_04.png" /></div>
<!--end all headers-->



<div id="container">

<h2>Uploaded files content</h2><span id='statusProc' style='display:'><img src='images/indicator.gif'></span>

<br/>

<table border="1" cellpadding="6">
<tr>
<td valign="top"><a>Content Of the File</a><pre id='fileName'><pre></td>
<td></td>
<td><pre id='contentF'></pre></td>
</tr>
<tr><br></br></tr>
</table>
	<br/><br/>
	
    <table><tr>

    <td></td>
    <td><input type="button" value="Close" onclick="window.close()"/></td>

</div>
<script type="text/javascript">

var qs = new Querystring();
var id = qs.get("id");
//alert("ID |"+id+"|");
ManageLoads.startContent(id); 

</script>	
</body>
</html>
