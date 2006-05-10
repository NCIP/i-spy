<%@ page
	import="gov.nih.nci.caintegrator.application.lists.ListType,gov.nih.nci.caintegrator.application.lists.UserList,gov.nih.nci.caintegrator.application.lists.UserListBean,gov.nih.nci.ispy.util.ISPYListManager,gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper,org.apache.struts.upload.FormFile,java.io.File,java.util.Map,java.util.HashMap,java.util.List,org.dom4j.Document"%>
<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>


<script>

//this function is called by upload.jsp sitting in an iframe
//it is given an associative array (much like a paramMap) from
// the validated list. The groupString variable(below) is inserted into the page
// at the appropriate place.

	var listNameArray = new Array(); //RCL

	function handleResponse(msg) { 

	    //lets refresh the sidebar...these functions are declared in sideBar_tile.jsp .. -RL
	    ManageListHelper.getAllLists();
		
		try	{
		   	SidebarHelper.loadSidebar();
		}
		catch(err)	{}
	   
		//reset the form
		Form.reset($('uploadForm'));

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
		if(confirm("Delete this List?"))
			UserListHelper.removeListFromAjax(name, generic_cb);
	}
 
	function generic_cb(name)	{
		//lets refresh the sidebar...these functions are declared in sideBar_tile.jsp .. -RL
		try	{
				SidebarHelper.loadSidebar();
		}
		catch(err)	{
			//alert(err);
			//cant find those funcs most likely, so catch this
		}
		
		ManageListHelper.getAllLists()
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
		var invalidItems = userList.getElementsByTagName("invalidItem");	 		
		
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
			     
				if(!invalidItems.length < 1){

					var iSPAN = document.createElement("span");
					iSPAN.setAttribute("id", "invalid_span");
					iSPAN.style.color = "gray";
					iSPAN.style.padding = "3px";
					iSPAN.appendChild(document.createTextNode("Invalid items: "));
					for(var i=0; i<invalidItems.length; i++){
						invalidItemId = invalidItems[i].firstChild.data;
						if((i+1) == invalidItems.length){
							iSPAN.appendChild(document.createTextNode(invalidItemId));
						}
						else{
							iSPAN.appendChild(document.createTextNode(invalidItemId  + ","));
						}
					}
					var b = document.createElement("br");                               
					dDIV.appendChild(iSPAN);
					dDIV.appendChild(b);
				}
		     
	 		}
	 		catch(err)	{
	 			$("details").innerHTML = err;
	 			$("details").style.display = "";
	 		}
	 		
	}
	
	//this invokes and processes the Ajax call to generate the initial listing of lists
	var ManageListHelper = {
		'getPatientLists' : function()	{
			//this function is dependent on the DynamicListHelper, included in the sidebar tile
			// src='dwr/interface/DynamicListHelper.js'
			DynamicListHelper.getAllLists("PatientDID", ManageListHelper.getGenericLists_cb);
			
		},
		'getGeneLists' : function()	{
			DynamicListHelper.getAllLists("GeneSymbol", ManageListHelper.getGenericLists_cb);			
		},
		'getAllLists' : function()	{
			ManageListHelper.getGeneLists();
			ManageListHelper.getPatientLists();
		},
		'getGenericLists_cb' : function(listsDOM)	{
			
			var tmp = listsDOM.getElementsByTagName("lists");
			var listType = tmp[0] ? tmp[0].getAttribute("type") : "none";
			if(listType == "none") return;
			
			var lists = listsDOM.getElementsByTagName("list");
			if(lists.length == 0)	{
				$(listType+'ListDiv').innerHTML = "<b>No "+ listType + " lists currently saved</b>";
				return;
			}
			//alert(listType + " : " + lists.length);
			
			$(listType+'ListDiv').innerHTML = "";  //clear it, and repopulate
			
			for(var t=0; t<lists.length; t++)	{
			// += or =
				$(listType+'ListDiv').innerHTML += "<div id='"
                	+ lists[t].getAttribute("name")
                    + "'><b>"
                    + lists[t].getAttribute("name") + "</b>"
                    + " created on:" + lists[t].getAttribute("date") 
                    + " (" + lists[t].getAttribute("invalid") + " invalid) "
                    + "[<a href='#' onclick='deleteList(\""
                    + lists[t].getAttribute("name")
                    + "\");return false;'>delete</a>]"
                    + "[<a href='#' onclick='getDetails(\""
                    + lists[t].getAttribute("name")
                    + "\");return false;'>details</a>]</div><br /><div id='"
                    + lists[t].getAttribute("name")
                    + "details'></div>";
			}
		}
	};
</script>

<iframe id="RSIFrame" name="RSIFrame" style="width:0px; height:0px; border: 0px" src="blank.jsp"></iframe>

<span id="info">&nbsp;</span>

<fieldset class="groupList">
	<legend>
		Patient Lists
	</legend>
	<br />
	<div id="patientListDiv"></div>
	<script>ManageListHelper.getPatientLists();</script>
	
	<div id="listDiv" />

	</div>
	<div id="PatientDIDListMarker">
		&nbsp;
	</div>
</fieldset>

<fieldset class="groupList">
	<legend>
		Gene Symbol Lists
	</legend>
	<br />
	
	<div id="geneListDiv"></div>
	<script>ManageListHelper.getGeneLists();</script>
	
	<div id="listDiv" />

	</div>
	
	<div id="GeneSymbolListMarker">
		&nbsp;
	</div>
</fieldset>


<fieldset class="listForm" id="listForm">
	<legend class="listLegend">
		upload list
	</legend>
	<form id="uploadForm" method="post" action="upload.jsp" enctype="multipart/form-data" target="RSIFrame">
		<table border="0" cellspacing="2" cellpadding="2">
			<tr>
				<td>
					Choose the list type:
				</td>
				<td colspan="2">
					<select id="typeSelector" name="type">
						<option>
							patient
						</option>
						<option>
							gene symbol
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					Upload file:
				</td>
				<td colspan="2">
					<input type="file" id="upload" name="upload" size="25">
				</td>
			</tr>
			<tr>
				<td>
					Name list:
				</td>
				<td>
					<input type="text" id="listName" name="listName" size="30">
				</td>
				<td style="text-align:right">
					<input type="button" value="add list" onclick="validateForm()">
				</td>
			</tr>

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
     	 else {document.forms[0].submit();}
     	 
     }
 
</script>

