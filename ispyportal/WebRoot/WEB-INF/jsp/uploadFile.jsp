<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" language="javascript" src="files/script.js"></script>
<link rel='stylesheet' href='css/style.css'></link>
<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>
<script type='text/javascript' src='dwr/interface/WebGroupDisplay.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<script type='text/javascript' src='js/lib/common/ManageLoads.js'></script>
<script type="text/javascript" language="javascript" src="files/validate.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%--
			<div id="main" style="min-height: 370px;">

							<br clear="all"><br/>
	--%>
	
		<style>
#sidebar div b {
	border-bottom: 1px solid #A90101;
}
		
span.error {
 color:#AB0303;
 font-weight:bold;
 font-size:8pt;
}
	</style>
							<div class="content_container">
								
								
								<h3>Upload New File</h3>								
								
								<br/>
								
								
								<form method="post" enctype="multipart/form-data" onsubmit="ManageLoads.processFile('file_input', 'validation_script','upload_script'); return false" >
								<input type="hidden" name="message" id="message" value="" />
								<input type="hidden" name="content" id="content" value="" />
									<table cellpadding="0" cellspacing="12" border="0">

								
										<tr>
										<th align="right">Upload File:</th>
										<td><input type="text" name="file_input" id="file_input" size="40" value="" /> <input type="button" value="Choose" title="file_input" class="file_browser" onClick="file_browser('file_input.html', 'file_input'); return false"/><br/></td>
										<tr>
										<th align="right">Validation Script:</th>
										<td><input type="text" name="validation_script" id="validation_script" size="40" value="" /> <input type="button" value="Choose" title="validation_script" onClick="file_browser('validation_script.html'); return false" /><br/></td>
										</tr>

										<tr>
										<th align="right">Upload Script:</th>
										<td><input type="text" name="upload_script" id="upload_script" size="40" value="" /> <input type="button" value="Choose" title="upload_script" onClick="file_browser('upload_script.html'); return false" /><br/></td>
										</tr>
										
										<tr>
										<th></th>
										<td><input type="submit" value="Upload"/></td>
										</tr>

									
									</table>
								
								</form>							 
<script type="text/javascript" language="javascript">
<!--
/*
	function openwin( url, name, h, w ){	
	
		window.open( url, name, "width="+w+",height="+h );
		
		return false;
	
	}
*/
//-->
</script>							 	
								
							</div>
							 <br><br>
 
							
							
							
							
							
							
							
							
							