/**
*	//this object is dependent on the DynamicListHelper, included in the sidebar tile
*	// src='dwr/interface/DynamicListHelper.js'
*	// also dependent on browserSniff.js, SidebarHelper.js, and prototype.js 1.5.x
*/
	var ManageGroupHelper = {

		'getAllLists' : function()	{
        	WebGroupDisplay.getAllGroups(ManageGroupHelper.getGenericGroups_cb);
        	WebGroupDisplay.getUsersList(ManageGroupHelper.getAllUsers);
		},
		
				// ************* upload related **************** //
		//this function is called by upload.jsp sitting in an iframe
		//it is given an associative array (much like a paramMap) from
		// the validated list. The groupString variable(below) is inserted into the page
		// at the appropriate place.-KR...dont need the main argument anymore
		
		'handleResponse' : function(msg) { 
			//optional arguments: 1-upload form id, 2-upload status id
			try	{
				ManageGroupHelper.getAllLists();
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
		

		'addGRSelectedMembers' : function(groupTag, grName, action){
//		alert("debug -|" + groupTag + "|" + grName + "|" + action + "|");
			try{
				if(grName.length < 2){
					alert("Please provide a name");
                   	throw("no name  provided");
				}
				var groupId = 0;
                var sMembers = Array();
				var confirmMsg = "Do You want to create the group \"" + grName + "\" with the following members? \"\n";

                var ls = $(groupTag) ? $(groupTag).getElementsByTagName('option') : Array();
                
                for(var i=0; i<ls.length; i++){
                   if(ls[i].selected || ls[i].checked){
                        sMembers.push(ls[i].id);
                        confirmMsg += ls[i].value + "\n";
 //          alert("debug -|" + ls[i].id + "|" + ls[i].value + "|");
                    }
                }
//        alert("debug -|" + sMembers + "|");
					 if(confirm(confirmMsg))
                     	WebGroupDisplay.addSelectedMembers(groupId, grName, sMembers, ManageGroupHelper.generic_cb);
						
			} catch(err) {
               alert("ERR: " + err);
            }
		},


		
		'addSelectedMembers' : function(groupTag, groupId, grName, action){
//		alert("debug -|" + groupTag + "|" + groupId + "|" + grName + "|" + action + "|");
			try{
                var sMembers = Array();
				var confirmMsg = "Do You want to Add the following members to the group \"" + grName + "\" ?\n";

                var ls = $(groupTag) ? $(groupTag).getElementsByTagName('option') : Array();
                
                for(var i=0; i<ls.length; i++){
                   if(ls[i].selected || ls[i].checked){
                        sMembers.push(ls[i].id);
                        confirmMsg += ls[i].value + "\n";
  //         alert("debug -|" + ls[i].id + "|" + ls[i].value + "|");
                    }
                }
                if(sMembers.length < 1){
                   alert("Please select some members to " + action);
                   throw("no members selected");
                }
    //    alert("debug -|" + sMembers + "|");
					 if(confirm(confirmMsg))
                     	WebGroupDisplay.addSelectedMembers(groupId, grName, sMembers, ManageGroupHelper.generic_cb);
						
			} catch(err) {
               alert("ERR: " + err);
            }
		},


		
        'deleteSelectedMembers' : function(groupTag, groupId, action)    {
            try{
                var sMembers = Array();
				var confirmMsg = "Delete the following members?\n";
                var ls = $(groupTag) ? $(groupTag).getElementsByTagName('input') : Array();

                for(var i=0; i<ls.length; i++){
                   if(ls[i].type=='checkbox' && (ls[i].selected || ls[i].checked)){
                        sMembers.push(ls[i].value);
                        confirmMsg += ls[i].id + "\n";
                    }
                }
                if(sMembers.length < 1){
                   alert("Please select some members to " + action);
                   throw("no members selected");
                }
    //    alert("debug -|" + sMembers + "|");
					 if(confirm(confirmMsg))
                     	WebGroupDisplay.deleteSelectedMembers(groupId, sMembers, ManageGroupHelper.generic_cb);

                 } catch(err) {
                    alert("ERR: " + err);
                   }
          },


		
		'getAllUsers' : function(txt)	{

			try	{
				var userContainerArray = eval('(' + txt + ')');
				
				var tst = "";
				var userLabel = "USERS";
				
				tst = "<select type='text' name='newMembersN' size='10' style='width:250px' multiple='multiple'>";
				
			    for(var i=0; i<userContainerArray.length; i++){
		            	var userContainer = userContainerArray[i];
		            	var logNameU = userContainer.LoginName;
		            	var lastNameU = userContainer.LastName;
		            	var firstNameU = userContainer.FirstName;
		            	var userIDU = userContainer.UserID;
		            	var userIni = lastNameU + ", " + firstNameU;
		            	tst += "<option id='" + userIDU + "' value='" + userIni + "'>"
		            	 + lastNameU + ", " + firstNameU + "</option>";
		        }
                tst += "</select>";
 				
			if($(userLabel+'ListDiv'))
				$(userLabel+'ListDiv').innerHTML = tst;
			}
			catch(err)	{
				alert("ERR: " + err);
			}
			 
		},

				
		'getGenericGroups_cb' : function(txt)	{
			//accepts a JSON object	<- now accepts a json array
			// String listType : patient | gene | defaultPatient		
			// Array<Object> listItems : { listName, listDate, itemCount, invalidItems }
			//Note:  $('..'+"ListDiv") needs be be defined in the HTML src
			// ^ now, should be auto generated
			 
			try	{
				var groupContainerArray = eval('(' + txt + ')');
				var tst = "";
				var groupType = "GROUP";
//debugger;				
				if(groupContainerArray.length>0){
				for(var i=0; i<groupContainerArray.length; i++)	{
					var groupContainer = groupContainerArray[i];
                    var groupName = groupContainer.groupName;
                    var displayName = "";
 /*           
                    if(groupName.indexOf('\\') != -1){
                    	displayName = groupName.replace(/\\/g, "\\\\");
                    	groupName = groupName.replace(/\\/g, "&#92;");
                    } else
           			if(groupName.indexOf('\"') != -1){
                    	groupName = groupName.replace(/\"/g, "&#34;");
                    	displayName = groupName;
         			} else
           			if(groupName.indexOf('\'') != -1){
           			    displayName = groupName.replace(/\'/g, "\\\'");
                    	groupName = groupName.replace(/\'/g, "&#39;");
         			} else
          			if(groupName.indexOf(';') != -1){
           			    displayName = groupName.replace(/;/g, "\;");
                    	groupName = groupName.replace(/;/g, "&#59;");
         			}  
 */        			
         			var displayName = groupName;

						if(groupName.indexOf('\'') != -1 || groupName.indexOf('\"') != -1 || groupName.indexOf('\\') != -1){
						if(displayName.lastIndexOf('\\') != -1)
								displayName = displayName+" ";
						if(groupName.indexOf('\\') != -1){
                    		displayName = displayName.replace(/\\/g, "\\\\");
                    		groupName = groupName.replace(/\\/g, "&#92;");
                    	}
						if(groupName.indexOf('\'') != -1){
                    		displayName = displayName.replace(/\'/g, "\\\'");
                    		groupName = groupName.replace(/\'/g, "&#39;");
                   		
                    	}
						if(groupName.indexOf('\"') != -1){
                   			groupName = groupName.replace(/\"/g, "&#34;");
							displayName = displayName.replace(/\"/g, "&#34;");
                    	}

                    	} else{
                    		displayName = groupName;
                    	}		
         			
         			
         			
         			       			
					var members = groupContainer.members;
					var memberCount = members.length;
					var groupId = groupContainer.groupID;
					var users = groupContainer.users;
					
					tst += "<div id='" + groupId
		                    + "' class='listListing'> <p class='group-title'>"
		                    + groupName + "</p>"
		                    + "<div style='margin-left: 10px;'>"
		                    + "<span id='" + groupId + "_count'>" + memberCount + "</span> member(s)"
		                    
		                    + "<div style='cursor: pointer; margin-left: 20px; display: inline;' "
		                    + "onClick=\"Effect.toggle('" + groupId + "_details','blind'); return false\">"
		                    + "<img src='files/arrowPane20.png' style='vertical-align: text-bottom;' border='0'>show/hide member(s)</div>"
		                    
		                    + "<div style='cursor: pointer; margin-left: 20px; display: inline;' "
		                    + "onClick=\"ManageGroupHelper.deleteGoup('" + groupId + "', '" + displayName + "'); return false\">"
		                    + "<img src='files/deleteCross20.png' style='vertical-align: text-bottom;' border='0'>delete group</div>"
		                    + "</div><br>"
		                    + "<div id='" + groupId + "_details' style='display:none;'>"
		                    + "<form class='member_form'>"
		                    + "<div id='" + groupId + "_RemoveUser'><ul>";
		            
		            for(var t=0; t<members.length; t++){
		            	var memberContainer = members[t];
		            	var memberID = memberContainer.memberID;
		            	var logName = memberContainer.LoginName;
		            	var lastName = memberContainer.LastName;
		            	var firstName = memberContainer.FirstName;
		            	var userID = memberContainer.UserID;
		            	var userIni = lastName + ", " + firstName;
		            	
		            	tst += "<li><input type='checkbox' id='" + userIni + "' name='" + groupId + "' "
                                    + "value='" +memberID+ "' title='" + userID + "'>"
                                    +"\&nbsp;" + lastName + ", " + firstName + "</li>";
		        
		            }
                        tst += "</ul><p class='btns'><input value='add user(s)' type='submit' "
                            + "onClick=\""
                            + "Effect.toggle('" + groupId + "_RemoveUser', 'blind'); "
                            + "Effect.toggle('" + groupId + "_AddUser','blind'); "
                            + "return false\">\&nbsp;\&nbsp;"
                            + "<input value='remove selected user(s)' name='remove_user' type='submit' "
                            + "onClick=\""
                            + "Effect.toggle('" + groupId + "_details','blind'); "

                            + "ManageGroupHelper.deleteSelectedMembers('" + groupId + "_RemoveUser', '" + groupId + "', 'delete'); "

                            + "return false\"></p></div>"
                            + "<div id='" + groupId + "_AddUser' style='display:none'>"
                            + "<select type='text' name='newMembers' size='10' style='width: 250px;' multiple='multiple'>";


		            for(var z=0; z<users.length; z++){
		            	var userContainer = users[z];
		            	var logNameU = userContainer.LoginName;
		            	var lastNameU = userContainer.LastName;
		            	var firstNameU = userContainer.FirstName;
		            	var userIDU = userContainer.UserID;
		            	var userIni = lastNameU + ", " + firstNameU;
		            	
		            	tst += "<option id='" + userIDU + "' value='" + userIni + "'>"
		            	 + lastNameU + ", " + firstNameU + "</option>";
		            }
		           
		            tst += "</select><p><input value='add selected users' name='add_user' type='submit' "
		                + "onClick=\"Effect.toggle('" + groupId + "_AddUser','blind'); "
		                + "Effect.toggle('" + groupId + "_details','blind'); "
		                + "Effect.toggle('" + groupId + "_RemoveUser','blind'); "
		                + "ManageGroupHelper.addSelectedMembers('"+groupId+"_AddUser', '"+groupId+"', "
		                + "'"+displayName+"', 'Add Members'); "
		                + "return false\">\&nbsp;\&nbsp;"
		                + "<input value='cancel' type='submit' "
		                + "onClick=\"Effect.toggle('" + groupId + "_AddUser','blind'); "
		                + "Effect.toggle('" + groupId + "_RemoveUser','blind'); return false\"></p></div>"
		                + "</form></div></div>";     		               
				}
			 
// alert ("Variables list for debug tst|" + tst + "|" );				
			if($(groupType+'ListDiv'))
				$(groupType+'ListDiv').innerHTML = tst;
				} else{
				
					if($(groupType+'ListDiv')){
							$(groupType+'ListDiv').innerHTML = "<b>No Groups currently saved</b><br/><br/>";
					}
				}
			}
			catch(err)	{
				alert("ERR: " + err);
			}

			 
		},

		 //function to make an AJAX call to the userListBean in the session
		 // and delete the list under the name it passes as a param. Then finds
		 // the div with an id matching this name and removes it from the DOM. -KR
		'deleteGoup' : function(name, grName){
//debugger;
			if(confirm("Delete this group? - \"" + grName + "\""))
				WebGroupDisplay.removeGroupFromAjax(name, ManageGroupHelper.generic_cb);
//				WebGroupDisplay.removeGroupFromAjax(name);
/*
		    try{
alert("DEBUG before function");
		    	ManageGroupHelper.generic_cb;
alert("DEBUG after function");
		    } catch(err)	{
				alert("ERR: " + err);
			}
*/
		},
				
		'generic_cb' : function(name)	{
		
					try	{
				SidebarHelper.loadSidebar();
			}
			catch(err)	{}
		
			ManageGroupHelper.getAllLists()
		}

	};
