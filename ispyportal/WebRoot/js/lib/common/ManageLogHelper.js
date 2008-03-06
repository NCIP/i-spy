	var ManageLogHelper = {


		'sendNotification' : function(){
//		alert("Debug notification as alert GROUP");
			WebGroupDisplay.getAllLogs(ManageLogHelper.displayLogInfo);
		
		},
		
		'displayLogInfo' : function(txt){
//		alert("Debug notification as alert GROUP");
			try	{
					var listmsg = "";
					var groupAddMsg = "";
					var groupDelMsg = "";
					var listContainerArray = eval('(' + txt + ')');
//	alert("Debug notification as alert GROUP");
		
					if(listContainerArray.length>0){
						listmsg = "You can view new lists shared to You:\n\n";
						groupAddMsg = "You have been added to the following groups:\n\n";
						groupDelMsg = "You have been removed from the following groups:\n\n";
		
						for(var i=0; i<listContainerArray.length; i++)	{
							var listContainer = listContainerArray[i];
				
							if(listContainer.ACTION == "GRADD"){
								groupAddMsg += "\t\""+listContainer.groupName+"\" The Group owned by "+listContainer.userName+"\n";
//								alert("Debug notification as alert GROUP");
							} else if(listContainer.ACTION == "SHADD"){
								listmsg += "\""+listContainer.listName+"\" - The List owned by "+listContainer.userName+"\n";
								//alert("Debug notification as alert  LIST");
							} else 	if(listContainer.ACTION == "GRDEL"){
								groupDelMsg += "\t\""+listContainer.groupName+"\" The Group owned by "+listContainer.userName+"\n";
//								alert("Debug notification as alert GROUP");
							}
						}
					
					}
		
					if(listmsg.length>41){
						alert(listmsg);
					}
					if (groupAddMsg.length>48){
						alert(groupAddMsg);
					}
					if (groupDelMsg.length>52){
						alert(groupDelMsg);
					}
					
					
					
					
				}
				catch(err)	{
					alert("ERR: " + err);
				}
		
			
		}
		
		};