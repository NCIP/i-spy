<%@ page import="gov.nih.nci.caintegrator.application.lists.ListType,gov.nih.nci.caintegrator.application.lists.UserList,gov.nih.nci.caintegrator.application.lists.UserListBean, gov.nih.nci.caintegrator.application.lists.ListManager, gov.nih.nci.caintegrator.application.lists.UserListBeanHelper,org.apache.struts.upload.FormFile,java.io.File,java.util.Map,java.util.HashMap,java.util.List,org.dom4j.Document"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<script type='text/javascript' src='js/scriptaculous/effects.js'></script>

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>


<script>

//this function is called by upload.jsp sitting in an iframe
//it is given an associative array (much like a paramMap) from
// the validated list. The groupString variable(below) is inserted into the page
// at the appropriate place.-KR

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
		//hide the indicator
		setTimeout(function(){$('uploadStatus').style.display = "none";}, 500);

	}
  
  // this functions as a toggle for the details link next to a 
  // list. If details are currently viewable, it removes the element
  // from the DOM. If the "list name + detailsDiv" element doesn't
  // exist, the AJAX call is made to retrieve the most current details
  // from the userListBean sitting in the session. -KR
  
	function getDetails(name){   
		  
		if(document.getElementById(name+ "detailsDiv")==null){	    
			if($(name+"status"))
				$(name+"status").style.display = "";
	    	UserListHelper.getDetailsFromList(name,putDetailsIHTML);	    
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
		try	{
			SidebarHelper.loadSidebar();
		}
		catch(err){} 	
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
		listType = ""; //clear this for now
		
		var items = userList.getElementsByTagName("item");		
		var invalidItems = userList.getElementsByTagName("invalidItem");	 
		
		var itemId;
 		var value;
 		var dDIV = document.createElement("div");
 		dDIV.setAttribute("id",listName + "detailsDiv");
 		dDIV.setAttribute("class", "listItemsDiv");
 		
 		if(items.length > 0)	{
		 		for(var i=0; i<items.length; i++)	{
		 		
		 			itemId = items[i].firstChild.data;	
		 			
		 			var tDIV = document.createElement("div");
					tDIV.setAttribute("id", listName + itemId + "_div");
					tDIV.setAttribute("class","detailsList");
					tDIV.appendChild(document.createTextNode(i+1 + ") " + listType + " " + itemId + "  "));
					
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
				
				var exDIV = document.createElement("div");
				var oc = function() { 
					var url = "listExport.jsp?list="+this.parentNode.id.substring(0, this.parentNode.id.indexOf("detailsDiv"));
					location.href=url;
				};
				exDIV.innerHTML = "[ export list? ]";
				exDIV.style.marginTop = "10px";
				exDIV.style.cursor = "pointer";
				exDIV.style.width="90px";
				exDIV.style.height="20px";
				//exDIV.style.border="1px solid red";
				exDIV.onclick = oc;
				dDIV.appendChild(exDIV);
				}
		     else{
		        document.getElementById(listName + "details").appendChild(dDIV);
		      	$(listName + "detailsDiv").innerHTML = "<span>No details found</span>";
		     	}
		       
	 		}
	 		catch(err)	{
	 			$("details").innerHTML = err;
	 			$("details").style.display = "";
	 		}
	 		
	 		if($(listName+"status"))	{
				setTimeout(function()	{$(listName+"status").style.display = "none";}, 500);
			}
	}
	
	//same as above, but w/innerHTML
	function putDetailsIHTML(userList){
 	try	{
		var list = userList.getElementsByTagName("list");
		listName = list[0].getAttribute("name");
		listType = list[0].getAttribute("type");
		listType = ""; //clear this for now
		
		var items = userList.getElementsByTagName("item");		
		var invalidItems = userList.getElementsByTagName("invalidItem");	 
		
		var itemId;
 		var value;
 		var dDIV = document.createElement("div");
 		dDIV.setAttribute("id",listName + "detailsDiv");
 		dDIV.setAttribute("class", "listItemsDiv");
 		
 		//rcl - create and append our container
 		 document.getElementById(listName + "details").appendChild(dDIV);
 		
 		//setup a handle to the working container
 		var wDiv = $(listName + "detailsDiv");
 		
 		if(items.length > 0)	{
 		
 			var tmp = "";
		 		for(var i=0; i<items.length; i++)	{
		 		
			 		
		 			itemId = items[i].firstChild.data;	
		 			
		 			tmp += "<li id='"+listName + itemId + "_div"+"' class='detailsList'>"+(i+1) +") " +listType + " " + itemId;
		 			
					
					var oc = new Function("deleteItem('"+listName+ "','" + itemId + "');return false;");
					tmp += "<a href=\"#\" onclick=\"deleteItem('"+listName+ "','" + itemId + "');return false;\">[delete]</a></li>";
					  
			     }
			     
			     wDiv.innerHTML += tmp;
			     
				if(!invalidItems.length < 1){

					wDiv.innerHTML += "<span id='invalid_span' style='color:gray; padding:3px'>Invalid items: ";
					
					
					for(var i=0; i<invalidItems.length; i++){
						invalidItemId = invalidItems[i].firstChild.data;
						if((i+1) == invalidItems.length){
							wDiv.innerHTML += invalidItemId;
						}
						else{
							wDiv.innerHTML += invalidItemId + ",";
						}
					}
					
					wDiv.innerHTML += "<br/></span>";
					
				}
				wDiv.innerHTML += "<div onclick=\"location.href='listExport.jsp?list="+listName+"';\" style='margin-top:10px;cursor:pointer; width:90px;height:20px'><img src='images/downArrow20.png'/><u>export list</u></div>";



		}
	     else{
	        document.getElementById(listName + "details").appendChild(dDIV);
	      	$(listName + "detailsDiv").innerHTML = "<span>No details found</span>";
	     }
	       
	}
	catch(err)	{
		$("details").innerHTML = err;
		$("details").style.display = "";
	}
	 		
	if($(listName+"status"))	{
		setTimeout(function()	{$(listName+"status").style.display = "none";}, 500);
	}
	
	}
	
	
	//this invokes and processes the Ajax call to generate the initial listing of lists
	var ManageListHelper = {
		'getPatientLists' : function()	{
			//this function is dependent on the DynamicListHelper, included in the sidebar tile
			// src='dwr/interface/DynamicListHelper.js'
			DynamicListHelper.getAllLists("PatientDID", ManageListHelper.getGenericLists_cb);
			
		},
		'getDefaultPatientLists' : function()	{
			//this function is dependent on the DynamicListHelper, included in the sidebar tile
			// src='dwr/interface/DynamicListHelper.js'
			DynamicListHelper.getAllLists("DefaultPatientDID", ManageListHelper.getGenericLists_cb);
			
		},
		'getGeneLists' : function()	{
			DynamicListHelper.getAllLists("GeneSymbol", ManageListHelper.getGenericLists_cb);			
		},
		'getAllLists' : function()	{
			ManageListHelper.getGeneLists();
			ManageListHelper.getPatientLists();
			ManageListHelper.getDefaultPatientLists();
		},
		'getGenericLists_cb' : function(listsDOM)	{
			
			
			var tmp = listsDOM.getElementsByTagName("lists");
			var listType = tmp[0] ? tmp[0].getAttribute("type") : "none";
			if(listType == "none") return;
			
			var lists = listsDOM.getElementsByTagName("list");
			if(lists.length == 0)	{
					//because we have default lists, do report that patient lists are empty
					if(listType == "patient"){
					  $(listType+'ListDiv').innerHTML = "<b>No custom "+ listType + " lists currently saved</b>";
					}
					else{
					  $(listType+'ListDiv').innerHTML = "<b>No "+ listType + " lists currently saved</b>";
				    }
				return;
			}
			//alert(listType + " : " + lists.length);
			
			$(listType+'ListDiv').innerHTML = "";  //clear it, and repopulate
			
			for(var t=0; t<lists.length; t++)	{
			
				var status = "<span id=\""+lists[t].getAttribute("name")+"status\" style=\"display:none\"><img src=\"images/indicator.gif\"/></span>";
				var shortName = lists[t].getAttribute("name").length>25 ? lists[t].getAttribute("name").substring(0,23) + "..." : lists[t].getAttribute("name");
				// += or =
				$(listType+'ListDiv').innerHTML += "<div id='"
                	+ lists[t].getAttribute("name")
                    + "' class='listListing'>" 
                    + "<input type='checkbox' style='border:0px;' id='' name='" + listType + "' value='" +lists[t].getAttribute("name")+ "'/>"
                    + "<b style='color:#000000;' title='"+lists[t].getAttribute("name")+"'>"
                    + shortName + "</b>"
                   // + " created:" + lists[t].getAttribute("date") 
                  //  + " (" + lists[t].getAttribute("invalid") + " invalid) "
                    + "<div style='cursor:pointer;margin-left:20px;;width:200px;display:inline;' onclick='getDetails(\""
                    + lists[t].getAttribute("name")
                    + "\");return false;'>"
                    + "<img src='images/arrowPane20.png' border='0' style='vertical-align:text-bottom'/>show/hide details" + status + "</div>"
                    + "<div style='cursor:pointer;margin-left:20px;;width:200px;display:inline;'  onclick='deleteList(\""
                    + lists[t].getAttribute("name")
                    + "\");return false;'>"
                    + "<img src='images/deleteCross20.png' border='0' style='vertical-align:text-bottom;'/>delete</div>"
                    + "</div><br /><div id='"
                    + lists[t].getAttribute("name")
                    + "details'></div>";
			}
		},
		'groupSelectedLists' : function(listGroup, groupName, action)	{
			var sLists = Array();
			try	{
				if(groupName == "")	{
					alert("Please Enter a name for the new group");
					throw("no name error");
				}
				var ls = $(listGroup).getElementsByTagName('input');
				for(var i=0; i<ls.length; i++)	{
					if(ls[i].type=='checkbox' && (ls[i].selected || ls[i].checked))
						sLists.push(ls[i].value);
				}
				
				if(sLists.length < 1)	{
					alert("Please select some lists to " + action);
					throw("no lists selected");
				}
				
				var groupType = "patient";
				
				if(listGroup.indexOf('patient')!= -1)	{
					groupType = "patient";
				}
				else	{
					groupType = "gene";
				}
				
				//ajax call
				//sLists, groupType, groupName
				DynamicListHelper.uniteLists(sLists, groupName, groupType, action, ManageListHelper.groupSelectedLists_cb );
				//alert(groupType + " : " + groupName + "( " + action + " ):" + sLists);
			}
			catch(err) {} 
		},
		'groupSelectedLists_cb' : function(txt)	{
			var res = txt.split(",");
			//alert(res[0]);
			try	{
			
				if(res[0].indexOf("pass")!=-1)	{
					//alert("all good");
					$('geneGroupName').value = "";
					$('patientGroupName').value = "";
					var st = "patientGroupStatus";
					if(res[1] == "gene")	{
						st = "geneGroupStatus";
					}
					$(st).innerHTML = "Group Saved";
					setTimeout(function()	{ $(st).innerHTML = ""; }, 2000);
				}
				else {
					if(res[2] == "join"){
						alert("List did not save, please try again.");
					}
					else{
						alert("The selected lists do not have any common entries.");
					}
				}
					
				ManageListHelper.getAllLists();
			   	SidebarHelper.loadSidebar();
			}
			catch(err)	{alert(err);}
		}
	};
	
	var FormChanger = {
	//$('typeSelector').value , $('uploadRow') , $('listName') , $('uploadButton')
		'upload2type' : function()	{

			//hide the upload field and show a text area
			$('uploadRow').style.display = "none";
			$('textRow').style.display = "";
			
			$('uploadButton').onclick = TextFormList.processTextForm;
			//alert($('uploadButton').onclick);
		},
		'type2upload' : function()	{
			//hide the textarea field and show a upload
			$('uploadRow').style.display = "";
			$('textRow').style.display = "none";
			
			$('uploadButton').onclick = validateForm;
			//alert($('uploadButton').onclick);
		}
	};
</script>
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
</style>
<iframe id="RSIFrame" name="RSIFrame" style="width:0px; height:0px; border: 0px" src="blank.jsp"></iframe>

<span id="info">&nbsp;</span>

<div style="text-align:center">
	<a href="#geneLists">Gene Lists</a> | 
	<a href="#patientLists">Patient Lists</a> | 
	<a href="#addList">Add List</a>
</div>
<a name="patientLists"></a>
<fieldset class="groupList" id="patientListsFS">
	<legend onclick="new Effect.toggle('patContainer')">
		Patient Lists
	</legend>
	<div id="patContainer">
		<br/>
		<div id="patientListDiv"></div>	
		<script>ManageListHelper.getDefaultPatientLists();</script>
		<div id="defaultPatientListDiv"></div>	
		<script>ManageListHelper.getPatientLists();</script>
		
		
		<div id="listDiv" />
			New List Name:<input type="text" id="patientGroupName"/>
			<b><input type="button" onclick="ManageListHelper.groupSelectedLists('patientListsFS', $('patientGroupName').value,'join')" value="Join Selected"/></b>	
			<b><input type="button" onclick="ManageListHelper.groupSelectedLists('patientListsFS', $('patientGroupName').value,'intersect')" value="Intersect Selected"/></b>	
			<span class="status" id="patientGroupStatus"></span>
		</div>
		<div id="PatientDIDListMarker">
			&nbsp;
		</div>
	</div>
</fieldset>

<a name="geneLists"></a>
<fieldset class="groupList" id="geneListsFS">
	<legend onclick="new Effect.toggle('gContainer')">
		Gene Symbol Lists
	</legend>
	<div id="gContainer">
	<br />
	
	<div id="geneListDiv"></div>
	<script>ManageListHelper.getGeneLists();</script>
	
	<div id="listDiv" />
		New List Name:<input type="text" id="geneGroupName"/>
		<b><input type="button" onclick="ManageListHelper.groupSelectedLists('geneListsFS',$('geneGroupName').value, 'join')" value="Join Selected"/></b>	
		<b><input type="button" onclick="ManageListHelper.groupSelectedLists('geneListsFS',$('geneGroupName').value, 'intersect')" value="Intersect Selected"/></b>	
		<span class="status" id="geneGroupStatus"></span>
	</div>
	
	<div id="GeneSymbolListMarker">
		&nbsp;
	</div>
	</div>
</fieldset>

<a name="addList"></a>
<fieldset class="listForm" id="listForm">
	<legend class="listLegend">
		<a onclick="FormChanger.type2upload();return false;" href="#">Upload List</a> -or- <a href="#" onclick="FormChanger.upload2type();return false;">Manually type List</a>
	</legend>
	<div id="uploadListDiv">
	<form id="uploadForm" method="post" action="upload.jsp" enctype="multipart/form-data" target="RSIFrame" onSubmit="return false;">
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
					<app:help help="There must only be one entry (id) per line." />
					
				</td>
			</tr>
			<tr id="uploadRow">
				<td>
					Upload file:
				</td>
				<td colspan="2">
					<input type="file" id="upload" name="upload" size="25">
				</td>
			</tr>
			<tr id="textRow" style="display:none">
				<td>
					Type Ids:<br/> (comma separated)
				</td>
				<td colspan="2">
					<textarea id="typeListIds" style="width:300px"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					Name list:
				</td>
				<td colspan="2">
					<input type="text" id="listName" name="listName" size="30">
					<input type="button" value="add list" onclick="validateForm()" id="uploadButton">
					<span id="uploadStatus" style="display:none"><img src="images/indicator.gif"/>&nbsp; saving...</span>
				</td>
			</tr>

		</table>
	</form>
	</div>
	
</fieldset>

<script>
     //all form validation is done client side. The name and file fields are checked for null
     // and list name collisions are checked. If there is a collision the user can either
     //overwrite the stored list with the current list or cancel the action and rename it. -KR
     
     
     //never gets called if you click <enter> on the keyboard, hence the onSubmit = return false; - RL
     function validateForm(){
     
     $('uploadStatus').style.display = "";
     
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
     	    $('uploadStatus').style.display = "none";
     	    return false;
     	 }
     	 else {
	     	$('uploadForm').submit();
     	 	//document.forms[0].submit();
     	 }
     	 
     }
     
    var TextFormList =	{
    	'processTextForm' : function ()	{
		     if($('typeListIds').value != "" && $('listName').value != "")	{
		     
		     	$('uploadStatus').style.display = "";
		     	//construct array
		     	var ids = Array();
		     	ids = $('typeListIds').value.split(",");
		     	//clean on the sside
		     	//ajax call
		     	try	{
			     	if($('typeSelector').value == "patient")	{
				    	DynamicListHelper.createPatientList(ids, $('listName').value, TextFormList.processTextForm_cb);
				    }
				    else	{
				    	DynamicListHelper.createGeneList(ids, $('listName').value,TextFormList.processTextForm_cb);
				    }
			    }
			    catch(err)	{
				    $('uploadStatus').style.display = "";
			    }
		     	
		     }
		     else	{
		     	alert("Please fill in all fields");
		     } 
	    },
	    'processTextForm_cb' : function(res)	{
	    	//clear form, refresh lists
	    	var r = res.split(",");
	    	if(r[0] != "pass")
	    		alert("List did not save correctly, please try again.");
	    		
	    	$('typeListIds').value = "";
	    	$('listName').value = "";
	    	
	    	$('uploadStatus').style.display = "none";
	    	generic_cb("none"); //reload all sidebars
	    }
	};
 
</script>
<div style="text-align:right; margin:10px;">
<a href="#" onclick="javascript:scroll(0,0);return false;">[top]</a>
</div>
