
<%@ page import="gov.columbia.c2b2.ispy.web.ajax.WebGroupDisplay,
				org.apache.struts.upload.FormFile,
				java.io.File,
				java.util.Map,
				java.util.HashMap,
				java.util.List,
				org.dom4j.Document,
				gov.nih.nci.ispy.util.ISPYListFilter"%>

<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>
<script type='text/javascript' src='dwr/interface/WebGroupDisplay.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<script type='text/javascript' src='js/lib/common/ManageGroupHelper.js'></script>


<%-- <script type='text/javascript' src='js/lib/common/ManageListHelper.js'></script> --%>
<script type='text/javascript' src='js/lib/common/TextFormList.js'></script>
<script type='text/javascript' src='js/lib/common/FormChanger.js'></script>
<script type='text/javascript' src='js/lib/common/StatusMessage.js'></script>	
	

	<style>	
		.status {
			color:#A90101;
			font-weight:bold;
		}
		.groupList li	{
			margin-left:20px;
			list-type:none;
			list-style-type: none;
		}
		
		li.detailsList	{
			padding:3px;
		}
	</style>
	
	<style>
		#sidebar div b {
			border-bottom: 1px solid #A90101;
		}
	</style>
	
	</head>
	<body>
    
<div id="overDiv" style="position: absolute; visibility: hidden; z-index: 1000;"></div>

 <div id="main" style="min-height: 370px;">		

<iframe id="RSIFrame" name="RSIFrame" style="border: 0px none ; width: 0px; height: 0px;" src="blank.jsp"></iframe>

<div style="text-align: center;">

	<img align="right" onClick="Help.popHelp('Manage_lists_overview');" name="helpIcon" id="helpIcon" alt="help" src="files/help.png" style="border: 0px none ; padding: 2px; cursor: pointer;"/>
	
	<br clear="left">

	<a href="#UserGroups">User Groups</a> | 

	<a href="#AddUserGroup">Add a New User Group</a>
	
	<br/><br/><br/><br/>
	
</div>



<%

        String label = "GROUP";
        String labelU = "USERS";
%>
     <a name="<%=label%>Lists"></a>
      	<fieldset class="groupList" id="<%=label%>ListsFS">
		<legend onclick="new Effect.toggle('<%=label%>Container')">
			User Groups
		</legend>
			<br/>
          <div id="<%=label%>Container">	
			<div id="<%=label%>ListDiv"></div>
		  </div>
	
		</div>
      </fieldset>
	<div style="text-align:right; margin:10px;">
		<a href="#" onclick="javascript:scroll(0,0);return false;">[top]</a>
	</div>

	
	<a name="AddUserGroup"></a>
	<fieldset class="listForm" id="listForm">
		<legend class="listLegend" onClick="$('#uploadListDiv').toggle();">
			Add a New User Group
		</legend>
		<br>
		<div id="uploadListDiv">
			
			<table cellpadding="4" cellspacing="0" border="0" >
	
			<tr>

			<th>&nbsp;&nbsp;Group Name:</th>
			<td><input type="text" name="newGroupName" size="50" id="<%=labelU%>GroupName"/></td>
			</tr>
			
			<tr>
			<th>
				&nbsp;&nbsp;Group Members:
				<br/><br/><span style="font-size:x-small; font-weight:normal;">(Hold Ctrl to Select Multiple)</span>
			</th>

			<td>
			<div id="<%=labelU%>ListDiv"></div>

			</td>
			</tr>
			
			<tr>

			<th></th>
			<td><input type="submit" value="Add This Group" onClick="ManageGroupHelper.addGRSelectedMembers('<%=labelU %>ListDiv', $('<%=labelU%>GroupName').value, 'add group'); return false"/></td>
			</tr>
			
			</table>
			
			<br/>
			
		</div>
		
	</fieldset>
	
	
	
	<div style="margin: 10px; text-align: right;">
		<a href="#" onClick="javascript:scroll(0,0);return false;">[top]</a>


 		
<script type="text/javascript">
		if(!saf){
			addLoadEvent(ManageGroupHelper.generic_cb); 
		}
		else	{
			ManageGroupHelper.generic_cb("init");
		}		
		//StatusMessage.showStatus("asdf");
</script>


