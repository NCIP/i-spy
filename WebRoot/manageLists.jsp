<%@ page
	import="gov.nih.nci.caintegrator.application.lists.ListType,
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
<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<script>

//this function is called by upload.jsp sitting in an iframe
//it is given an associative array (much like a paramMap) from
// the validated list. The groupString variable(below) is inserted into the page
// at the appropriate place.

function handleResponse(msg) { 
   var groupString = "<div id='" + msg["name"] + "'><b>" + msg["name"] + "</b> created on: " + msg["date"] + "[<a href='#' onclick='deleteList(\"" + msg["name"]
                            + "\");return false;'>delete</a>]"
                            + "[<a href='#' onclick='getDetails(\""
                            + msg["name"]
                            + "\");return false;'>details</a>]</div><br /><div id='"+msg["name"]+"details'></div></div>" ;  
   //check to see if the list exists on the page. 
   // if it does, remove it and add the new list of the
   // same name. Then clear the form fields.
   if(document.getElementById(msg["name"])!=null){
      Element.remove(msg["name"]);
      Element.remove(msg["name"]+ "details");
	      if(document.getElementById(msg["name"]+"detailsDiv")!=null){
	        Element.remove(msg["name"]+ "detailsDiv");
	      }
      new Insertion.Top(msg["type"]+"ListMarker", groupString);
      document.forms[0].listName.value="";
      document.forms[0].upload.value="";
      listNameArray.push(msg["name"]);
   }
   else{
   		new Insertion.Top(msg["type"]+"ListMarker", groupString);
   		document.forms[0].listName.value="";
     	document.forms[0].upload.value="";
     	listNameArray.push(msg["name"]);
   }
  }
  
  // this functions as a toggle for the details link next to a 
  // list. If details are currently viewable, it removes the element
  // from the DOM. If the "list name + detailsDiv" element doesn't
  // exist, the AJAX call is made to retrieve the most current details
  // from the userListBean sitting in the session. -KR
  
 function getDetails(name){        
	   if(document.getElementById(name+ "detailsDiv")==null){	    
	    UserListHelper.getDetailsFromList(name,putDetails);	    
	   }
	   else	{
	     Element.remove(name+"detailsDiv");	     
	    }
	}

 //function to make an AJAX call to the userListBean in the session
 // and delete the list under the name it passes as a param. Then finds
 // the div with an id matching this name and removes it from the DOM. -KR
 
 function deleteList(name){
 	UserListHelper.removeList(name);
 	Element.remove(name);
 	Element.remove(name+"detailsDiv"); 	
 }
 
 //function to make an AJAX call to the userListBean in the session
 // and delete the list item under the list name it passes as params. Then finds
 // the div with an id matching this item name and removes it from the DOM. -KR
 
 function deleteItem(name, itemId){
 	UserListHelper.removeItemFromList(name,itemId);
 	Element.remove(name + itemId + "_div");	 	
 }
	
 //this is the callback function from the getDetails AJAX function (above). This
 //function recieves a Document object back from the getDetails call and parses
 // the Document object. As it parses the Document (which is a UserList), it 
 // dynamically creates DOM elements for each item in a particular list and adds
 // them to the page. The dynamically created nodes are created/deleted depending
 // on when the user toggles the "details" link next to a link. -KR
 
 function putDetails(userList){
 	try	{
	 			var list = userList.getElementsByTagName("list");
	 			listName = list[0].getAttribute("name");
	 			listType = list[0].getAttribute("type");
	 			
	 			var items = userList.getElementsByTagName("item");		 		
		 		if(items.length < 1)	{
		 			//no details
		 			throw("No details found.");
		 		}
		 		
		 		var itemId;
		 		var value;
		 		var dDIV = document.createElement("div");
		 			dDIV.setAttribute("id",listName + "detailsDiv");
		 			
		 		for(var i=0; i<items.length; i++)	{
		 		
		 			itemId = items[i].firstChild.data;	
		 			
		 			var tDIV = document.createElement("div");
					tDIV.setAttribute("id", listName + itemId + "_div");
					tDIV.setAttribute("class","detailsList");
					tDIV.appendChild(document.createTextNode(i+1 + ") " + listType + ": " + itemId + "  "));
					
					var eAnchor = document.createElement("a");					
					eAnchor.setAttribute("href","#");					
					eAnchor["onclick"]=new Function("deleteItem('"+listName+ "','" + itemId + "');return false;");			
					eAnchor.appendChild(document.createTextNode("[delete]"));
					
					tDIV.appendChild(eAnchor);					
					dDIV.appendChild(tDIV);
			        
			     }
			     
			     document.getElementById(listName + "details").appendChild(dDIV);
	 		}
	 		catch(err)	{
	 			$("details").innerHTML = err;
	 			$("details").style.display = "";
	 		}
	 		
	}
</script>


	
<iframe id="RSIFrame" name="RSIFrame" style="width:0px; height:0px; border: 0px" src="blank.jsp"></iframe>

<span id="info">&nbsp;</span>

<fieldset>
	<legend>
		Patient Lists
	</legend>
	<br />
	 <div id="listDiv" />
		<%
		// the user list bean in the session is accessed and the patient lists
		// are displayed.**May want to create a tag for this**  -KR
		
		ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(request
                    .getSession());
                    
            String allListNames = "";        
                    
            List patientLists = helper.getLists(ListType.PatientDID);
            if (!patientLists.isEmpty()) {
                for (int i = 0; i < patientLists.size(); i++) {
                    UserList list = (UserList) patientLists.get(i);
                    ISPYUploadManager uploadManager = (ISPYUploadManager) ISPYUploadManager.getInstance();
                    Map paramMap = uploadManager.getParams(list);
                    out.write("<div id='" + paramMap.get("listName") + "'><b>"
                            + paramMap.get("listName") + "</b> created on: "
                            + paramMap.get("date") 
                            + "[<a href='#' onclick='deleteList(\"" + paramMap.get("listName")
                            + "\");return false;'>delete</a>]"
                            + "[<a href='#' onclick='getDetails(\""
                            + paramMap.get("listName")
                            + "\");return false;'>details</a>]</div><br /><div id='"+paramMap.get("listName")+"details'></div>");                   
                
	                //now add to the tally of current list names
	                String currentName = (String)paramMap.get("listName");
	                if (allListNames.length() > 0){
						  	 allListNames += ",";
					}
					if (allListNames != null && currentName.trim().length() > 0){
							 allListNames += '"'+currentName+'"';
					}	
                }
            } else {
                out.write("");
            }

        %>
     </div>
     <div id="PatientDIDListMarker">&nbsp;</div>
     </fieldset>
     
     <fieldset>
	<legend>
		Gene Symbol Lists
	</legend>
	<br />
	 <div id="listDiv" />
		<%
            // the user list bean in the session is accessed and the gene symbol
            // lists are displayed. **May want to create a tag for this**  -KR
            List geneLists = helper.getLists(ListType.GeneSymbol);
            if (!geneLists.isEmpty()) {
                for (int i = 0; i < geneLists.size(); i++) {
                    UserList list = (UserList) geneLists.get(i);
                    ISPYUploadManager uploadManager = (ISPYUploadManager) ISPYUploadManager.getInstance();
                    Map paramMap = uploadManager.getParams(list);
                    out.write("<div id='" + paramMap.get("listName") + "'><b>"
                            + paramMap.get("listName") + "</b> created on: "
                            + paramMap.get("date") 
                            + "[<a href='#' onclick='deleteList(\"" + paramMap.get("listName")
                            + "\");return false;'>delete</a>]"
                            + "[<a href='#' onclick='getDetails(\""
                            + paramMap.get("listName")
                            + "\");return false;'>details</a>]</div><br /><div id='"+paramMap.get("listName")+"details'></div>");                   
                
                	//now add to the tally of current list names
	                String currentName = (String)paramMap.get("listName");
	                if (allListNames.length() > 0){
						  	 allListNames += ",";
					}
					if (allListNames != null && currentName.trim().length() > 0){
							 allListNames += '"'+currentName+'"';
					}	
                }
            } else {
                out.write("");
            }

        %>
     </div>
     <div id="GeneSymbolListMarker">&nbsp;</div>
     </fieldset>
     
        <script>
        var listNameArray = new Array(<%=allListNames%>);
        </script>
     
		<fieldset id="listForm" class="listForm">
		<legend class="listLegend">upload list</legend>
			<form method="post" action="upload.jsp" enctype="multipart/form-data" target="RSIFrame">
				<table border="0" cellspacing="2" cellpadding="2">
				                  <tr><td>Choose the list type</td><td colspan="2"><select id="typeSelector" name="type"><option>patient</option><option>gene symbol</option></select></td></tr>
				                  <tr><td>Upload file: </td><td colspan="2"><input type="file" id="upload" name="upload" size="25"></td></tr>
								  <tr><td>Name list: </td><td><input type="text" id="listName" name="listName" size="30"></td>
								       <td style="text-align:right"><input type="button" value="add list" onclick="validateForm()"></td></tr>
								  
			   </table>
			</form>
		</fieldset>

 <script>
     //all form validation is done client side. The name and file fields are checked for null
     // and list name collisions are checked. If there is a collision the user can either
     //overwrite the stored list with the current list or cancel the action and rename it. -KR
     
     function validateForm(){
     
      var thisListName = document.forms[0].listName.value;
			
	  
						
     
        var errors = "";
     	if(document.forms[0].listName.value==""){ 
     		errors += "please enter a name for this list. \n";     	    
     	 }
     	if(document.forms[0].upload.value==""){
     	    errors += "please enter a file for this list.";
     	 }
     	  
     	if(errors != ""){
     	    alert(errors);
     	    return false;
     	 }
     	 else{
	     	 var found = false;
				if (!(thisListName == null || thisListName == "")) {
					for(var t=0;t<listNameArray.length; t++)	{
					  if (thisListName == listNameArray[t]) found = true;
					}
					if (found) {
						  if (confirm("List Name exists in system. This action will overwrite existing list.")) {
					  		document.forms[0].submit();
						  }
						  else return false;
				 	}else {document.forms[0].submit();}
				 }
			
       }
     }
 
</script>

