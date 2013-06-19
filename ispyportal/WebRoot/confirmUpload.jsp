<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

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

<script type="text/javascript" language="javascript" src="files/script.js"></script>
<script type="text/javascript" language="javascript">
	_selectedValue = null;
</script>
</head>
<body>

<!--header NCI logo-->
<div style="background-color: #ffffff"><img src="images/hiccc_header_04.png" /></div>
<!--end all headers-->



<div id="container">

<h2>Confirm Your upload</h2>

<br/>

<form method="post" action="upload.do">
<table>
<tr>
<td><a>Message From process Action:</a></td>
<td></td>
<td><pre id='message'></pre></td><td><span id='statusProc' style='display:'><img src='images/indicator.gif'></span></td>
</tr>
<tr><br></br></tr>

</table>




	
	<br/><br/>
	
    <table><tr>
    <td><input type="submit" value="Process Upload" id="uploadButton"/></td>

    <td></td>
    <td><input type="button" value="Cancel Upload" onclick="window.close()" id="uploadButton"/></td>
</form>

</div>
	<script type="text/javascript">

	</script>
</body>
</html>