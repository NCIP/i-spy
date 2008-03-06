<%@ page import="gov.columbia.c2b2.ispy.list.ListType,gov.nih.nci.ispy.util.ISPYListFilter"%>
<!--<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='js/lib/common/SidebarHelper.js'/></script>-->
<div id="manageListLinkDiv" style="text-align:center; margin-top:20px;">
	<fieldset style="text-align:center">
		<a href="manageLists.do">Manage Lists</a>
	</fieldset>
</div>
<style>
	#sidebar div b {
		border-bottom: 1px solid #A90101;
	}
</style>

<div id="sidebar">
<%
	ListType[] lts = ISPYListFilter.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<div style="text-align:left; margin-top:20px;">
		<b><%=label%> Lists:</b>   
		<div id="sidebar<%=label%>UL">
			<img src="images/indicator.gif"/>
		</div>	
	</div>
<%
	}
%>
<br/><br/>
<%-- 
<b style="color:#A90101; font-size:10px;">Items in Red are "custom" lists</b>
--%>
</div>
<script language="javascript">
	SidebarHelper.loadSidebar();
</script>

<style>
	#sidebarS div b {
		border-bottom: 1px solid #A90101;
	}
</style>

<div id="sidebarS">
<%
	lts = ISPYListFilter.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<div style="text-align:left; margin-top:20px;">
		<b>Shared <%=label%> Lists:</b>
		<div id="sidebar<%=label%>ULS">
			<img src="images/indicator.gif"/>
		</div>	
	</div>
<%
	}
%>
<br/><br/>
<b style="color:#A90101; font-size:10px;">Items in Red are "custom" lists</b>
</div>
<script language="javascript">
	SidebarHelper.loadSidebarS();
</script>