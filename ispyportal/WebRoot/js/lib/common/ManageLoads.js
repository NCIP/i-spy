/**
*	//this object is dependent on the DynamicListHelper, included in the sidebar tile
*	// src='dwr/interface/DynamicListHelper.js'
*	// also dependent on browserSniff.js, SidebarHelper.js, and prototype.js 1.5.x
*/
	var ManageLoads = {

		'getAllLoadInfo' : function()	{
        	WebGroupDisplay.getAllUploadLogs(ManageLoads.getLoadFilesInfo_sb);

		},
        'pikcInputFile' : function(ext)    {
        
//   alert(ext);     
 			WebGroupDisplay.getInputFile(ext, ManageLoads.getFileForInput);
          },
          
          'getFileForInput' : function(txt)	{
        
  			try	{
				var fileArray = eval('(' + txt + ')');
				var tst = "";
				var Type = "LOG";
				tst += "<select name='validation' id='file_select' size='5'>";
				
				if(fileArray.length>0){
				for(var i=0; i<fileArray.length; i++)	{
					var file = fileArray[i];
					var name = file.FileName;
 					tst += "<option onClick=\"_selectedValue='"+name+"'\">"+name+"</option>";
 				}
				tst += "</select>";				
				
				if($('ValidScript'))
					$('ValidScript').innerHTML = tst;
			  } else{
			  
			  	if($('ValidScript')){
							$('ValidScript').innerHTML = "<b>No Files uploaded Available</b><br/><br/>";
					}
			  
			  }	
			}
			catch(err)	{
				alert("ERR: " + err);
			}

		},
		
		
		 'processFile' : function(inTag, validTag, uploadTag)    {
		 try{
 

		 
		 
		     var input = document.getElementById( inTag ).value;
		     var valid = document.getElementById( validTag ).value;
			var upload = document.getElementById( uploadTag ).value;
		     
		     
		     if(input.length < 1){
                   alert("Please select some input file");
                   throw("no input files selected");
                }
                
                 if(valid.length < 1){
                   alert("Please select some validation script");
                   throw("no validation scripts selected");
                }
                
                if(upload.length < 1){
                   alert("Please select some upload script file");
                   throw("no upload script selected");
                }
 			WebGroupDisplay.processFile(input, valid, upload, ManageLoads.confirmUpload);
 			
 			
 			 } catch(err) {
                  alert("ERR: " + err);
             }
          },
          
          
         'uploadFinal' : function()    {
		 try{
//alert("DEBUG");
		 WebGroupDisplay.processConfUpload(ManageLoads.dispUploadMes);
		 

 			 } catch(err) {
                  alert("ERR: " + err);
             }
          },
          
          'dispUploadMes' : function(txt)	{

			try	{
//alert(txt);
				var confMessage = eval('(' + txt + ')');

				var message = confMessage.messageF;
		//		var content = confMessage.content;
				var win1 = window.open('dispUploadMes.html', 'dispUploadMes', 'height=800,width=1000');
 
				win1.onload = function(){
            		win1.document.getElementById('messageF').innerHTML = message;
 
				}

   			}
			catch(err)	{
				alert("ERR: " + err);
			}
			 
		},
		
		'confirmUpload' : function(txt)	{

			try	{
				var confMessage = eval('(' + txt + ')');
				var message = confMessage.message;
		//		var content = confMessage.content;
				var win1 = window.open('confirmUpload.html', 'confirm_upload', 'height=800,width=1000');
 
				win1.onload = function(){
            		win1.document.getElementById('message').innerHTML = message;
            //		win1.document.getElementById('content').innerHTML = content;
				}

   			}
			catch(err)	{
				alert("ERR: " + err);
			}
			 
		},       

        'deleteSelectedRecs' : function(recTag)    {
//  alert("DEBUG in delete");
            try{
                var sMembers = Array();
				var confirmMsg = "Delete the following records?\n";
                var ls = $(recTag) ? $(recTag).getElementsByTagName('input') : Array();

                for(var i=0; i<ls.length; i++){
                   if(ls[i].type=='checkbox' && (ls[i].selected || ls[i].checked)){
                        sMembers.push(ls[i].name);
                        confirmMsg += ls[i].name + "\n";
                    }
                }
                if(sMembers.length < 1){
                   alert("Please select some records to Delete");
                   throw("no records selected");
                }
    //    alert("debug -|" + sMembers + "|");
					 if(confirm(confirmMsg)){
                     	WebGroupDisplay.deleteSelectedRecs(sMembers, ManageLoads.generic_cb);
                      }

                 } catch(err) {
                    alert("ERR: " + err);
                   }
          },
				
		'getLoadFilesInfo_sb' : function(txt)	{

			try	{
				var logArray = eval('(' + txt + ')');
				var tst = "";
				var Type = "LOG";
				
				       tst = "<table id='unique_id' class='sortable' size='80%'>"
                    	+"<thead>"
                    	+"<tr>"
                    	+"<th class='' height='51'/>"
                    	+"<th class='sortable header headerSortUp'>Date</th>"
                    	+"<th class='sortable header'>File</th>"
                    	+"<th class='sortable header'>Validation Script</th>"
                    	+"<th class='sortable header'>Upload Script</th>"
                    	+"<th class='sortable header'>Output</th>"
                    	+"</tr>"
                    	+"</thead>"
                    	+"<tbody>";
				
				if(logArray.length>0){
				for(var i=0; i<logArray.length; i++)	{
					var logCont = logArray[i];
                    var recID = logCont.recID;
                    var inputFileName = logCont.inputFileName;
                    var validationScript = logCont.validationScript;
                    var uploadScript = logCont.uploadScript;
                    var outFileName = logCont.outFileName;
                    var numOfRecs = logCont.numOfRecords;
                    var date = logCont.logDate;
        
					tst += "<tr class='odd'>"
						+"<td>"
						+"<input type='checkbox' name='"+recID+"'/>"
						+"</td>"
						+"<td>"+date+"</td>"
						+"<td>"
						+"<a onclick=\"alert(Open '+(this).text()+'')\" href='#'>"+inputFileName+"</a>"
						+"</dt>"
						+"<td>"
						+"<a onclick=\"alert(Open '+(this).text()+'')\" href='#'>"+validationScript+"</a>"
						+"</dt>"
						+"<td>"
						+"<a onclick=\"alert(Open '+(this).text()+'')\" href='#'>"+uploadScript+"</a>"
						+"</dt>"
						+"<td>"
						+"<a onclick=\"alert(Open '+(this).text()+'')\" href='#'>"+outFileName+"</a>"
						+"</dt>"
						+"</tr>";
		            
				}
				tst += "</tbody></table>";
			 
// alert ("Variables list for debug tst|" + tst + "|" );				
			if($(Type+'LogDiv'))
				$(Type+'LogDiv').innerHTML = tst;
				} else{
				
					if($(Type+'LogDiv')){
							$(Type+'LogDiv').innerHTML = "<b>No Records Available</b><br/><br/>";
					}
				}

			}
			catch(err)	{
				alert("ERR: " + err);
			}

			 
		},


				
		'generic_cb' : function(name)	{
	/*	
					try	{
				SidebarHelper.loadSidebar();
			}
			catch(err)	{}
		*/
			ManageLoads.getAllLoadInfo()
		}

	};
