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
<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>
<script type='text/javascript' src='dwr/interface/WebGroupDisplay.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='files/tablekit.js'></script>
<link rel='stylesheet' href='css/style.css'></link>


<script type='text/javascript' src='js/lib/common/ManageLoads.js'></script>
	<script type="text/javascript" language="javascript" src="js/user/jquery-1.2.1.pack.js"></script> 	

	<script type="text/javascript" language="javascript" src="js/user/tablesorter.js"></script>

<%-- <script type='text/javascript' src='js/lib/common/ManageListHelper.js'></script> --%>
<script type='text/javascript' src='js/lib/common/TextFormList.js'></script>
<script type='text/javascript' src='js/lib/common/FormChanger.js'></script>
<script type='text/javascript' src='js/lib/common/StatusMessage.js'></script>
<script type='text/javascript' src='js/prototype.js'></script>	



<div id="main" style="min-height: 370px;">
<br clear="all"/>
<br/>
<div class="content_container">
<h3>Upload File</h3>
<p class="textright">
<a href="uploadFile.do">Upload a new file</a>
</p>
<form name="theForm" method="post">
<input type="hidden" value="approveUser" name="action"/>
<div id="divname" 79=""/>
<div id="divname" 80=""/>
<div id="divname" 17=""/>



<div id="LOGLogDiv"></div>

<br></br><br></br>
<script type="text/javascript">
		if(!saf){
			addLoadEvent(ManageLoads.generic_cb); 
		}
		else	{
			ManageLoads.generic_cb("init");
		}		
		
</script>


<div class="form_controls">
<input type="submit" value="Delete File(s)" name="deleteFile" onClick="ManageLoads.deleteSelectedRecs('LOGLogDiv'); return false"/>
</div>
</form>
</div>
<br/>
<br/>
<div>
<br/>
</div>