/*
*	//this object is dependent on the DynamicListHelper, included in the sidebar tile
*	// src='dwr/interface/DynamicListHelper.js'
*	// also dependent on browserSniff.js, SidebarHelper.js, and prototype.js 1.5.x
*/
	var ManageListHelper = {
	/*
		'getGenericLists' : function(listType)	{
			DynamicListHelper.getGenericLists(listType, ManageListHelper.getGenericLists_cb);
		},
*/
		'getAllLists' : function()	{
			//assumes browserSniff.js has already been included and declared the browser specific vars

			DynamicListHelper.getAllLists(ManageListHelper.getGenericLists_cb);

		},
		
		'getAllListsShr' : function()	{
			//assumes browserSniff.js has already been included and declared the browser specific vars

			DynamicListHelper.getAllSharedLists(ManageListHelper.getGenericLists_shared);
		},

		'getGenericLists_shared' : function(txt)	{
			//accepts a JSON object	<- now accepts a json array
			// String listType : patient | gene | defaultPatient		
			// Array<Object> listItems : { listName, listDate, itemCount, invalidItems }
			try	{
				var listContainerArray = eval('(' + txt + ')');
				for(var i=0; i<listContainerArray.length; i++)	{
					var listContainer = listContainerArray[i];
					var listType = listContainer.listType ? listContainer.listType : "none";
					if(listType == "none") {
						continue;
					}
//debugger;					
					var lists = listContainer.listItems;

					if(lists.length == 0)	{
						if($(listType+'ListDivS')){
							$(listType+'ListDivS').innerHTML = "<b>No "+ listType + " lists currently saved</b><br/><br/>";
					    }
					    if($(listType+'UniteDivS')){
					    	$(listType+'UniteDivS').style.display = "none";
					    }
						continue;
					}
	
					if($(listType+'ListDivS'))
						$(listType+'ListDivS').innerHTML = "";  //clear it, and repopulate
					
					var tst = "";
					for(var t=0; t<lists.length; t++)	{
					
						var status = "<span id=\""+lists[t].listID+"statusS\" style=\"display:none\"><img src=\"images/indicator.gif\"/></span>";
						var shortName = lists[t].listName.length>25 ? lists[t].listName.substring(0,23) + "..." : lists[t].listName;
						var theName = lists[t].listName;
						
						var displayName = theName;

						if(theName.indexOf('\'') != -1 || theName.indexOf('\"') != -1 || theName.indexOf('\\') != -1){
						if(displayName.lastIndexOf('\\') != -1)
								displayName = displayName+" ";
						if(theName.indexOf('\\') != -1){
                    		displayName = displayName.replace(/\\/g, "\\\\");
                    		theName = theName.replace(/\\/g, "&#92;");
                    	}
						if(theName.indexOf('\'') != -1){
                    		displayName = displayName.replace(/\'/g, "\\\'");
                    		theName = theName.replace(/\'/g, "&#39;");
                   		
                    	}
						if(theName.indexOf('\"') != -1){
                   			theName = theName.replace(/\"/g, "&#34;");
							displayName = displayName.replace(/\"/g, "&#34;");
                    	}

                    	} else{
                    		displayName = theName;
                    	}						
						
						var itemCount = lists[t].itemCount;
						var author = lists[t].author;
						var date = lists[t].listDate;
						var notes = lists[t].notes;
						var lstyle = lists[t].highlightType; 
						var lib = "Author: " + lists[t].author + "<br />" +
								  "Date Created: " + lists[t].listDate 	+ "<br />" +
								  "Notes: <br />"  + lists[t].notes;
						
						var listSubType = lists[t].listSubType;
						var listID = lists[t].listID;		

						tst +=  "<div id='"
		                	+ theName
		                    + "' class='SlistListing'>" 
//		                    + "<input type='checkbox' style='border:0px;' id='' name='" + listType + "' value='" +theName+ "'/>"
		                    + "<b style='"+lstyle+"' onmouseover=\"overlib('" + lib +"', CAPTION, '"+ displayName+ "');\" onmouseout='return nd();'>"
		                    + shortName + "</b>\&nbsp\&nbsp<span class='owner'>( " +author+" )</span><br/><div style='margin-left:10px'>"
		                    + " Type: Default - " + listSubType 
		                    + "<span id='"+listID+"_count'>" + itemCount + "</span> item(s)" 
		                    + "<div style='cursor:pointer;margin-left:20px;width:200px;display:inline;' onclick='ManageListHelper.getDetailsS(\""
		                    + listID
		                    + "\", \""+listID+"\");return false;'>"
		                    + "<img src='images/arrowPane20.png' border='0' style='vertical-align:text-bottom'/>details" + status + "</div>"
		                    + "<br /><div id='S"
		                    + listID
		                    + "detailsS'></div></div><br/><br/>";    
					}
					if($(listType+'ListDivS'))
						$(listType+'ListDivS').innerHTML = tst;
				}
			}
			catch(err)	{
				//alert("ERR: " + err);
			}
			 
		},
	
		'shareToSelectedGroups' : function(groupTag, listId, listName){
			try{
                var sGroups = Array();
				var confirmMsg = "Do You want to share the following list \"" + listName + "\" with?\n";
                var ls = $(groupTag) ? $(groupTag).getElementsByTagName('option') : Array();
                
                for(var i=0; i<ls.length; i++){
                   if(ls[i].selected || ls[i].checked){
                        sGroups.push(ls[i].id);
                        confirmMsg += ls[i].value + "\n";
                    }
                }
                if(sGroups.length < 1){
                   alert("Please select some groups to share the list with");
                   throw("no members selected");
                }
					 if(confirm(confirmMsg))
                     	UserListHelper.shareTheList(listId, sGroups, ManageListHelper.listShared);
              //       	ManageListHelper.generic_cb();
						
			} catch(err) {
               alert("ERR: " + err);
            }
		},
		
		'listShared' : function (txt){
//debugger;
		try{
			if(txt == "Y")
				ManageListHelper.generic_cb();	
			}
			catch(err){}		

		},				
		'getGenericLists_cb' : function(txt)	{
			//accepts a JSON object	<- now accepts a json array
			// String listType : patient | gene | defaultPatient		
			// Array<Object> listItems : { listName, listDate, itemCount, invalidItems }
			try	{
				var listContainerArray = eval('(' + txt + ')');
//System.out.println("DEBUGL|"+txt+"|");
//alert(txt);
				for(var i=0; i<listContainerArray.length; i++)	{
					var listContainer = listContainerArray[i];
		
					var listType = listContainer.listType ? listContainer.listType : "none";
					
					if(listType == "none") {
						continue;
					}
					
					var lists = listContainer.listItems;
					
					//Note:  $('..'+"ListDiv") needs be be defined in the HTML src
					// ^ now, should be auto generated 
					
					if(lists.length == 0)	{
						if($(listType+'ListDiv')){
							$(listType+'ListDiv').innerHTML = "<b>No "+ listType + " lists currently saved</b><br/><br/>";
					    }
					    if($(listType+'UniteDiv')){
					    	$(listType+'UniteDiv').style.display = "none";
					    }
						continue;
					}
	
					if($(listType+'ListDiv'))
						$(listType+'ListDiv').innerHTML = "";  //clear it, and repopulate
					
					var tst = "";

					for(var t=0; t<lists.length; t++)	{
					
						var status = "<span id=\""+lists[t].listID+"status\" style=\"display:none\"><img src=\"images/indicator.gif\"/></span>";
						var shortName = lists[t].listName.length>25 ? lists[t].listName.substring(0,23) + "..." : lists[t].listName;
						var theName = lists[t].listName;
						var displayName = theName;
//						var tempName = theName;
//debugger;
						if(theName.indexOf('\'') != -1 || theName.indexOf('\"') != -1 || theName.indexOf('\\') != -1){
						if(displayName.lastIndexOf('\\') != -1)
								displayName = displayName+" ";
						if(theName.indexOf('\\') != -1){
                    		displayName = displayName.replace(/\\/g, "\\\\");
                    		theName = theName.replace(/\\/g, "&#92;");
                    	}
						if(theName.indexOf('\'') != -1){
                    		displayName = displayName.replace(/\'/g, "\\\'");
                    		theName = theName.replace(/\'/g, "&#39;");
                   		
                    	}
						if(theName.indexOf('\"') != -1){
                   			theName = theName.replace(/\"/g, "&#34;");
							displayName = displayName.replace(/\"/g, "&#34;");
                    	}

                    	} else{
                    		displayName = theName;
                    	}
                    	
                    	
						var itemCount = lists[t].itemCount;
						var author = lists[t].author;
						var date = lists[t].listDate;
						var notes = lists[t].notes;
						var lstyle = lists[t].highlightType; 
						var lib = "Author: " + lists[t].author + "<br />" +
								  "Date Created: " + lists[t].listDate 	+ "<br />" +
								  "Notes: <br />"  + lists[t].notes;
						
						var listSubType = lists[t].listSubType;	
						var listID = lists[t].listID;
						var groupsNS = lists[t].groupNotSharedWith;
					    var groupsS = lists[t].groupSharedWith;
					    var membersS = lists[t].members;
					    var listID = lists[t].listID;
																					
						// += or =
						tst +=  "<div id='"
		                	+ theName
		                    + "' class='dlistListing'>" 
		                    + "<input type='checkbox' style='border:0px;' id='' name='" + listType + "' value='" +theName+ "'/>"
		                    + "<b style='"+lstyle+"' onmouseover=\"overlib('" + lib +"', CAPTION, '"+ displayName+ "');\" onmouseout='return nd();'>"
		                    + shortName + "</b><br/><div style='margin-left:10px'>" + listSubType 
		                    + "<span id='"+listID+"_count'>" + itemCount + "</span> item(s)" 	                    
		                    + "<div style='cursor:pointer;margin-left:20px;width:200px;display:inline;' onclick='ManageListHelper.getDetails(\""
		                    + listID
		                    + "\",\""+listID+"\");return false;'>"
		                    + "<img src='images/arrowPane20.png' border='0' style='vertical-align:text-bottom'/>details" + status + "</div>"
		                    + "<div style='cursor:pointer;margin-left:20px;width:200px;display:inline;'  onclick='ManageListHelper.deleteList(\""
		                    + listID
		                    + "\");return false;'>"
		                    + "<img src='images/deleteCross20.png' border='0' style='vertical-align:text-bottom;'/>delete</div>"
		                    + "<div style='cursor:pointer;margin-left:20px;width:200px;display:inline;'"
		                    + "  onclick=\"Effect.toggle('"+listID+"detailsS', 'blind'); return false\">"
		                    + "<img src='files/share.gif' border='0' style='vertical-align:text-bottom;'/>sharing</div>"
		                    + "</div>"
		                    + "<br/>"
		             //       + "<div id='"+theName +"details'></div>"		             
		                    + "<div id='"+listID+"detailsS' style='display:none;'>"
		                    + "<p><a href='javascript:void(0);' name='share_add' title='Click to Expand' onClick=\"Effect.toggle('"+listID+"groupsNS', 'blind'); return false\">"
		                    + "Select Group(s) to Share List with [+]</a></p>"
		                    + "<div id='"+listID+"groupsNS' class='share_add' style='display:none;'>"
		                    + "<select type='text' name='group' multiple='multiple' size='6' class='share_groups' style='width: 250px;'>";

		                    if(groupsNS.length>0){
		                    	for(var g=0; g<groupsNS.length; g++)	{
		                    		var groupContainer = groupsNS[g];
		                   // 		      alert("listContainer|"+groupContainer.groupName+"|");	
		                    		var groupID = groupContainer.groupID;
		                    		var groupName = groupContainer.groupName;
		                    		tst += "<option id='"+groupID+"' value='"+groupName+"'>"
		                    	    + groupName 
		                    	    + "</option>";
								}
							}						
											
						    tst += "</select>"
						    + "<p class='btns'><input type='submit' value='Share from Selected Group(s)' onClick=\""
				//		    + "alert( 'This Will Share This List With the Groups Selected Above' ); "
						    + "ManageListHelper.shareToSelectedGroups('"+listID+"groupsNS', '"+listID+"', '"+displayName+"'); return false "
						    + "\" /></p>"
						    + "</div>"
						    + "<p><a href='javascript:void(0);' name='share_groups' title='Click to Expand' onClick=\"Effect.toggle('"+listID+"groupsS', 'blind'); return false\">"
						    + "Group(s) You Have Shared This List With [+]</a></p>"
						    + "<div id='"+listID+"groupsS' class='share_groups' style='display:none;'>"
						    + "<ul>";
							tst += "<table cellpadding=\"8\" cellspacing=\"0\" border=\"0\">"
							    + "<tr><th></th><th>Group</th><th>Date</th></tr>";
                            if(groupsS.length>0){
		                    	for(var h=0; h<groupsS.length; h++)	{
		                    		var groupContainer = groupsS[h];
		                    		var groupID = groupContainer.groupID;
		                    		var groupName = groupContainer.groupName;
		                    		var groupShare = groupContainer.shareDate;
		                    		tst += "<tr><td><li id='"+listID+"' value='"+groupID+"'/></td>"
		                    		+ "<td>"
		                    	    + groupName 
		                    	    + "</td><td>"
		                    	    + groupShare
		                    	    + "</td></tr>";
		                    	//    + "</li>";
								}
							}						    
						   tst += "</table>"; 
						   tst += "</ul>"
						    + "</div>"
						    + "<p><a href='javascript:void(0);' name='share_members' title='Click to Expand' onClick=\"Effect.toggle('"+listID+"membersS', 'blind'); return false\">"
						    + "Member(s) You Have Shared This List With [+]</a></p>"
						    + "<div id='"+listID+"membersS' class='share_members' style='display:none;'>"
						    + "<ul>";
						    
						    tst += "<table cellpadding=\"8\" cellspacing=\"0\" border=\"0\">"
							    + "<tr><th></th><th>User</th><th>Date</th></tr>";
							    
							if(membersS.length>0){
			                    for(var k=0; k<membersS.length; k++){			                
		            			var user = membersS[k];
		            			var logNameU = user.LoginName;
		            			var lastNameU = user.LastName;
		            			var firstNameU = user.FirstName;
		            			var userIDU = user.UserID;
		            			var shareDate = user.shareDate;
		            			var userIni = lastNameU + ", " + firstNameU;
		            			tst += "<tr><td><li id='" + userIDU + "' value='" + userIni + "'/></td>"
		            			    + "<td>"+ lastNameU + ", " + firstNameU + "</td><td>"+shareDate+"</td></tr>";
		            	 //			+ "</li>";
		        				}	
							}	
						   tst += "</table>"; 	
						   tst += "</ul>"
						    + "</div>"
				            + "</div><br/>"
                            + "<div id='" + listID +"details' class='details'></div>"
		                    + "\n</div>\n";  		                   
					}	
					if($(listType+'ListDiv'))
						$(listType+'ListDiv').innerHTML = tst;
				}
			}
			catch(err)	{
				//alert("ERR: " + err);
			}			 
		},
			
		'groupSelectedLists' : function(groupType, listGroup, groupName, action)	{
			//params:  String listType, DOM where the selected boxes are, new list name, action to perform
//debugger;			 
			var sLists = Array();
			try	{
				if(groupName == "")	{
					alert("Please Enter a name for the new group");
					throw("no name error");
				}
				var ls = $(listGroup) ? $(listGroup).getElementsByTagName('input') : Array();
				for(var i=0; i<ls.length; i++)	{
					if(ls[i].type=='checkbox' && (ls[i].selected || ls[i].checked))
						sLists.push(ls[i].value);
				}
				
				if(sLists.length < 1)	{
					alert("Please select some lists to " + action);
					throw("no lists selected");
				}
				if(sLists.length!=2 && action == "difference")	{
					alert("Please select only 2 groups");
					throw("incorrect amount of lists");
				}
								
				DynamicListHelper.uniteLists(sLists, groupName, groupType, action, ManageListHelper.groupSelectedLists_cb );
			}
			catch(err) {} 
		},

		'groupSelectedLists_cb' : function(txt)	{
			//var res = txt.split(",");
			//accepts a comma sep string, should/could be json!
			//String results : pass | fail
			//String action
			//String groupType
			try	{
				var res = eval('(' + txt + ')');
				
				if(res.results.indexOf("pass")!=-1)	{
					$(res.groupType+'GroupName').value = "";
					
					var st = res.groupType+"GroupStatus";
					$(st).innerHTML = "Group Saved";
					setTimeout(function()	{ $(st).innerHTML = ""; }, 2000);
				}
				else {
					if(res.action == "join"){
						alert("List did not save, please try again.");
					}
					else{
						alert("The selected lists do not have any common entries.");
					}
				}
					
				ManageListHelper.getAllLists();
			   	SidebarHelper.loadSidebar();
			}
			catch(err)	{
				//alert(err);
			}
		},

		// ************* upload related **************** //
		//this function is called by upload.jsp sitting in an iframe
		//it is given an associative array (much like a paramMap) from
		// the validated list. The groupString variable(below) is inserted into the page
		// at the appropriate place.-KR...dont need the main argument anymore
		
		'handleResponse' : function(msg) { 
			//optional arguments: 1-upload form id, 2-upload status id
			try	{
				ManageListHelper.getAllLists();
			   	SidebarHelper.loadSidebar();
			}
			catch(err)	{}
		   
		   var uform = arguments.length > 1 && arguments[1]!= "" ? arguments[0] : "uploadForm";
		   var ustat = arguments.length > 2 ? arguments[2] : "uploadStatus";
			//reset the form
			Form.reset($(uform));
			//hide the indicator
			setTimeout(function(){$(ustat).style.display = "none";}, 500);

		},
		
		// this functions as a toggle for the details link next to a 
		// list. If details are currently viewable, it removes the element
		// from the DOM. If the "list name + detailsDiv" element doesn't
		// exist, the AJAX call is made to retrieve the most current details
		// from the userListBean sitting in the session. -KR
		// requires name+"detailsDiv" and name+"status" elements(ids) to be in the HTML
		'getDetails' : function(name,id){   
//debugger;
			if(document.getElementById(name+ "detailsDiv")==null){	    
				if($(name+"status"))
					$(name+"status").style.display = "";
		    	UserListHelper.getDetailsFromList(id,ManageListHelper.putDetailsIHTML);	    
		   	}
		   	else	{
		    	Element.remove(name+"detailsDiv");	     
		    }
		},
		
		// this functions as a toggle for the details link next to a 
		// list. If details are currently viewable, it removes the element
		// from the DOM. If the "list name + detailsDiv" element doesn't
		// exist, the AJAX call is made to retrieve the most current details
		// from the userListBean sitting in the session. -KR
		// requires name+"detailsDiv" and name+"status" elements(ids) to be in the HTML
		'getDetailsS' : function(name, id){   
//alert("Debug in getDetailsS");
//debugger;
			if(document.getElementById(name+ "detailsDivS")==null){	    
				if($(name+"statusS"))
					$(name+"statusS").style.display = "";
		    	UserListHelper.getDetailsFromList(id,ManageListHelper.putDetailsIHTML_S);	    
		   	}
		   	else	{
		    	Element.remove(name+"detailsDivS");	     
		    }
		},
		 //function to make an AJAX call to the userListBean in the session
		 // and delete the list under the name it passes as a param. Then finds
		 // the div with an id matching this name and removes it from the DOM. -KR
		'deleteList' : function(id){
			if(confirm("Delete this List?"))
				UserListHelper.removeListFromAjax(id, ManageListHelper.generic_cb);
		},
		'generic_cb' : function(name)	{
	
			try	{
				SidebarHelper.loadSidebar();
			}
			catch(err)	{}
				
			ManageListHelper.getAllLists()
		},
		
		'generic_shr' : function(name)	{
	
			try	{
				SidebarHelper.loadSidebar();
			}
			catch(err)	{}
				
			ManageListHelper.getAllListsShr()
		},
		//function to make an AJAX call to the userListBean in the session
		// and delete the list item under the list name it passes as params. Then finds
		// the div with an id matching this item name and removes it from the DOM. -KR
		'deleteItem' : function (listId, itemId, itemID){

			try	{
//			alert("REMOVE process done");
				UserListHelper.removeItemFromList(listId,itemId, itemID, ManageListHelper.tempF);
				
				Element.remove(listId + itemId + "_div");

				//update the count
				if($(listId+"_count"))	{
					var tmp = $(listId+"_count").innerHTML;
//debugger;				
					tmp--;
					$(listId+"_count").innerHTML = parseInt(tmp);
				}
			}
			catch(err){} 	
		},
		
		
		'tempF' : function (txt){
//debugger;
		try{
			if(txt == "Y")
				SidebarHelper.loadSidebar();

		
			}
			catch(err){}		

		},
		//this is the callback function from the getDetails AJAX function (above). This
		//function recieves a Document object back from the getDetails call and parses
		// the Document object. As it parses the Document (which is a UserList), it 
		// dynamically creates DOM elements for each item in a particular list and adds
		// them to the page. The dynamically created nodes are created/deleted depending
		// on when the user toggles the "details" link next to a link. -KR
		
		// modded to use IHTML and JSON- rcl
		'putDetailsIHTML' : function(userList){
			//JSON: String listName
			// String listType
			// Array validItems
			// Array invalidItems
		 	try	{
//debugger;
				var list = eval('(' + userList + ')');
				listName = list.listName;
				if(listName.indexOf('\\') != -1){
                    listName = listName.replace(/\\/g, "\\\\");
                }
				listType = list.listType;
				listType = ""; //clear this for now
				var lId = list.listID;
//	alert(lId);			
				var items = list.validItems ? list.validItems : Array();
				var invalidItems = list.invalidItems ? list.invalidItems : Array();
				
								
				var itemId;
				var itemRank = "";
				var itemNotes = "";
		 		var value;
		 		var dDIV = document.createElement("div");
		 		dDIV.setAttribute("id",lId + "detailsDiv");
		 		dDIV.setAttribute("class", "listItemsDiv");
		 		
		 		//rcl - create and append our container
		 		 document.getElementById(lId + "details").appendChild(dDIV);
		 		//setup a handle to the working container
		 		var wDiv = $(lId + "detailsDiv");
		 		wDiv.style.borderLeft = "1px dashed red";
		 		wDiv.style.marginLeft = "20px";
				wDiv.style.width="300px";
		 		if(items.length > 0)	{
		 			var tmp = "";		 				 			
					for(var i=0; i<items.length; i++)	{
						itemId = items[i].name;
						iId = items[i].itemID;
	//		alert(iId);
						itemRank = "";
						itemNotes = "";
							if(items[i].rank!=null){
								itemRank = " rank: " + items[i].rank;
							}
							if(items[i].notes!=null){
								itemNotes = " notes: " + items[i].notes;
							}						
						tmp += "<li id='"+lId + itemId + "_div"+"' class='detailsList'>"+(i+1) +") " +listType + " " + itemId + itemRank + itemNotes;						
						var oc = new Function("deleteItem('"+lId+ "','" + itemId + "', '"+iId+"');return false;");
						
						tmp += "<a href=\"#\" onclick=\"ManageListHelper.deleteItem('"+lId+ "','" + itemId + "', '"+iId+"');return false;\">[delete]</a></li>";

					}  
					wDiv.innerHTML += tmp;
					     
					var eid = encodeURIComponent(listName);
					wDiv.innerHTML += "<div onclick=\"location.href='listExport.jsp?list="+lId+"';\" style='margin:20px;cursor:pointer; width:90px;height:20px'><img src='images/downArrow20.png'/><u>export list</u></div>";
				}
				else{
			    	document.getElementById(lId + "details").appendChild(dDIV);
			    	wDiv.innerHTML = "<span>No valid items found</span><br />";
				}
			     
			    if(!invalidItems.length < 1)	{
					var intmp = "<div style='margin-left:20px;'><span id='invalid_span' style='color:gray; padding:3px;'><br/>Invalid or does not exist in the database:<br/> ";
					//wDiv.innerHTML += "<span id='invalid_span' style='color:gray; padding:3px'><br/>Invalid or does not exist in the database:<br/> ";
					for(var i=0; i<invalidItems.length; i++){
						invalidItemId = invalidItems[i];
						if((i+1) == invalidItems.length){
							//wDiv.innerHTML += invalidItemId;
							intmp += invalidItemId;
						}
						else{
							intmp += invalidItemId + ", ";
							//wDiv.innerHTML += invalidItemId + ", ";							
						}
					}
					wDiv.innerHTML +=intmp;		
					wDiv.innerHTML += "<br/></span>";
							
				}    
			}
			catch(err)	{
				if($('details'))	{
					$("details").innerHTML = err;
					$("details").style.display = "";
				}
			}
		 		
			if($(lId+"status"))	{
				setTimeout(function()	{$(lId+"status").style.display = "none";}, 500);
			}		
		},
		
		//this is the callback function from the getDetails AJAX function (above). This
		//function recieves a Document object back from the getDetails call and parses
		// the Document object. As it parses the Document (which is a UserList), it 
		// dynamically creates DOM elements for each item in a particular list and adds
		// them to the page. The dynamically created nodes are created/deleted depending
		// on when the user toggles the "details" link next to a link. -KR
		
		// modded to use IHTML and JSON- rcl
		'putDetailsIHTML_S' : function(userList){
//alert("Debug in putDetailsIHTML_S");
			//JSON: String listName
			// String listType
			// Array validItems
			// Array invalidItems
		 	try	{
				var list = eval('(' + userList + ')');
				listName = list.listName;
				if(listName.indexOf('\\') != -1){
                    listName = listName.replace(/\\/g, "\\\\");
                }
				listType = list.listType;
				listType = ""; //clear this for now
				var lId = list.listID;
//	alert(lId);	
//debugger;		
				var items = list.validItems ? list.validItems : Array();
				var invalidItems = list.invalidItems ? list.invalidItems : Array();
				
								
				var itemId;
				var itemRank = "";
				var itemNotes = "";
		 		var value;
		 		var dDIV = document.createElement("divS");
		 		dDIV.setAttribute("id",lId + "detailsDivS");
		 		dDIV.setAttribute("class", "listItemsDivS");
		 		
		 		//rcl - create and append our container
		 		 document.getElementById("S"+lId + "detailsS").appendChild(dDIV);
		 		//setup a handle to the working container
		 		var wDiv = $(lId + "detailsDivS");
		 		wDiv.style.borderLeft = "1px dashed red";
		 		wDiv.style.marginLeft = "20px";
				wDiv.style.width="300px";
		 		if(items.length > 0)	{
		 			var tmp = "";		 				 			
					for(var i=0; i<items.length; i++)	{
						itemId = items[i].name;
						iId = items[i].itemID;
	//		alert(iId);
						itemRank = "";
						itemNotes = "";
							if(items[i].rank!=null){
								itemRank = " rank: " + items[i].rank;
							}
							if(items[i].notes!=null){
								itemNotes = " notes: " + items[i].notes;
							}						
						tmp += "<li id='"+lId + itemId + "_divS"+"' class='detailsListS'>"+(i+1) +") " +listType + " " + itemId + itemRank + itemNotes;	
											
				//		var oc = new Function("deleteItem('"+listName+ "','" + itemId + "', '"+iId+"');return false;");
/*
						tmp += "<a href=\"#\" onclick=\"ManageListHelper.deleteItem('"+listName+ "','" + itemId + "', '"+iId+"');return false;\">[delete]</a></li>";
*/
					}  
					wDiv.innerHTML += tmp;
					     
					var eid = encodeURIComponent(listName);
					wDiv.innerHTML += "<div onclick=\"location.href='listExport.jsp?list="+lId+"';\" style='margin:20px;cursor:pointer; width:90px;height:20px'><img src='images/downArrow20.png'/><u>export list</u></div>";
				}
				else{
			    	document.getElementById("S"+lId + "detailsS").appendChild(dDIV);
			    	wDiv.innerHTML = "<span>No valid items found</span><br />";
				}
			     
			    if(!invalidItems.length < 1)	{
					var intmp = "<div style='margin-left:20px;'><span id='invalid_span' style='color:gray; padding:3px;'><br/>Invalid or does not exist in the database:<br/> ";
					//wDiv.innerHTML += "<span id='invalid_span' style='color:gray; padding:3px'><br/>Invalid or does not exist in the database:<br/> ";
					for(var i=0; i<invalidItems.length; i++){
						invalidItemId = invalidItems[i];
						if((i+1) == invalidItems.length){
							//wDiv.innerHTML += invalidItemId;
							intmp += invalidItemId;
						}
						else{
							intmp += invalidItemId + ", ";
							//wDiv.innerHTML += invalidItemId + ", ";							
						}
					}
					wDiv.innerHTML +=intmp;		
					wDiv.innerHTML += "<br/></span>";
							
				}    
			}
			catch(err)	{
				if($('detailsS'))	{
					$("detailsS").innerHTML = err;
					$("detailsS").style.display = "";
				}
			}
		 		
			if($(lId+"statusS"))	{
				setTimeout(function()	{$(lId+"statusS").style.display = "none";}, 500);
			}		
		},
		//all form validation is done client side. The name and file fields are checked for null
		// and list name collisions are checked. If there is a collision the user can either
		//overwrite the stored list with the current list or cancel the action and rename it. -KR

		//never gets called if you click <enter> on the keyboard, hence the onSubmit = return false; - RL
		//moved here from inline - RL
		
     	'validateForm' : function()	{
			//optional args: listName, upload, uploadStatus
			//loosely checking for presence
			var listName = arguments.length>0 ? arguments[0] : 'listName';
			var upload = arguments.length>1 ? arguments[1] : 'upload';
			var uploadStatus = arguments.length> 2 ? arguments[2] : 'uploadStatus';
			var uploadForm = arguments.length>3 ? arguments[3] : 'uploadForm';
			
			$(uploadStatus).style.display = "";
	     	var thisListName = $(listName).value;
			
			var errors = "";
	     	if(thisListName == ""){ 
	     		errors += "please enter a name for this list. \n";     	    
	     	}
	     	if($(upload).value==""){
	     		errors += "please enter a file for this list.";
			}  
	     	if(errors != ""){
	     	    alert(errors);
	     	    $(uploadStatus).style.display = "none";
	     	    return false;
	     	}
	     	else {
		    	$(uploadForm).submit();
			} 
	     }
	     
	};
	