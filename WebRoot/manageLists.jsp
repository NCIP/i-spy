<%@ page import ="gov.nih.nci.caintegrator.application.lists.ListType,
gov.nih.nci.caintegrator.application.lists.UserList,
gov.nih.nci.caintegrator.application.lists.UserListBean,
gov.nih.nci.ispy.util.ISPYUploadManager,
gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper,
gov.nih.nci.ispy.web.helper.ISPYUserListGenerator,
org.apache.struts.upload.FormFile,
java.io.File,
java.util.Map,
java.util.HashMap,
java.util.List,
org.dom4j.Document"%>
<script type='text/javascript' src='/ispyportal/dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='/ispyportal/dwr/engine.js'></script>
<script type='text/javascript' src='/ispyportal/dwr/util.js'></script>
<script>
function handleResponse(msg) { 
   if(msg["type"]=="PatientDID"){  
   		var groupString = "<b>" + msg["name"] + "</b>(" + msg["count"] + " patients) created on: " + msg["date"] + "<br />" ;
   	}
   else if(msg["type"]=="GeneSymbol"){  
   		var groupString = "<b>" + msg["name"] + "</b>(" + msg["count"] + " genes) created on: " + msg["date"] + "<br />" ;
   	}
   new Insertion.Before("listMarker", groupString);
   
  }
  
 function getDetails(name){ 		
	}
	
 function putDetails(data){	    
	    
	}
</script>

<iframe id="RSIFrame" name="RSIFrame" style="width:0px; height:0px; border: 0px" src="blank.jsp"></iframe>

<fieldset>
	<legend>
		Patient Groups
	</legend>
	<br />
	<div id="listMarker" />
	<%
	  
	  ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(request.getSession());
	  List patientLists = helper.getLists(ListType.PatientDID);
		  if(!patientLists.isEmpty()){
		  	for(int i=0; i<patientLists.size(); i++){
		  	  UserList list = (UserList)patientLists.get(i);
		  	  out.write("<span id='" + list.getName() + "'><b>" + list.getName() + "</b>(" + list.getItemCount() + " patients)" + " created on: " + list.getDateCreated() + "[<a href='#' onclick='alert(\"" + list.getName() + "\");'>delete</a>]" + "[<a href='#' onclick='getDetails(\"" + list.getName() + "\");'>details</a>]</span><br />");
		  	  Document listDetails = helper.getDetailsFromList(list.getName());
		  	  if(listDetails!=null){
		  	       response.flushBuffer();	
		  	       System.out.println("list has details");
		  	       helper.renderListDetails(request,listDetails,"detailsList.xsl",out);
		  	  }
		  	}
		  }
		  else{
		  	out.write("No patient groups have been created");
		  }
		  
    %>
	<form method="post" action="upload.jsp" enctype="multipart/form-data" target="RSIFrame">
		<input type="file" id="upload" name="upload" size="25">
		<input type="submit" value="submit">
	</form>
	</div>
	
</fieldset>