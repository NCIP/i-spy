/**
*	//this object is dependent on the DynamicListHelper, included in the sidebar tile
*	// src='dwr/interface/DynamicListHelper.js'
*	// also dependent on browserSniff.js, SidebarHelper.js, and prototype.js 1.5.x
*/
    var win1;
    var win2;
    var winFile;
	var ManageLoads = {
	'delay' : 250,


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
                    tst += "<option value='"+name+"'>"+name+"</option>";
 				}
				tst += "</select>";				
				$('statusProc').style.display = "none";
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
//		 try{

		     var input = document.getElementById( inTag ).value;
		     var valid = document.getElementById( validTag ).value;
			var upload = document.getElementById( uploadTag ).value;
		     
//debugger;		     
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
			win1 = window.open('confirmUpload.jsp', 'confirm_upload', 'height=800,width=1000');
			WebGroupDisplay.processFile(input, valid, upload, ManageLoads.confirmUpload);

          },
          
          'confirmUpload' : function(txt)	{
			try	{
//debugger;
				var confMessage = eval('(' + txt + ')');
				var message = confMessage.message;
					win1.document.getElementById('statusProc').style.display = "none";
            		win1.document.getElementById('message').innerHTML = message;
   			}
			catch(err)	{
				alert("ERR: " + err);
			}			 
		},       
                   
          'displayFileContent' : function(fileID)    {
 			winFile = window.open("dispFileContent.jsp?id='"+fileID+"'", "display_content", "location=1,status=1,scrollbars=1,height=800,width=1000");
          },
          
          
          'startContent' : function(id){
//  alert("id |"+id+"|");        
          		WebGroupDisplay.getFile(id, ManageLoads.contentLoad);
          },
			
          'contentLoad' : function(txt) {
			try	{
				var file = eval('(' + txt + ')');
				var fileName = file.fileName;
				var fileContent = file.fileContent;

				$('statusProc').style.display = "none";		
      			$('fileName').innerHTML = fileName;
      			if(fileContent.indexOf('<') != -1){
                   	fileContent = fileContent.replace(/</g, "&lt;");		
           		}
            	if(fileContent.indexOf('>') != -1){
                  fileContent = fileContent.replace(/>/g, "&gt;");
             	}
 /*            	
             	if(fileContent.indexOf('\\') != -1){
                 	  		fileContent = fileContent.replace(/\\/g, "\\\\\\");
                 }
   */
                 if(fileContent.indexOf('\'') != -1){
                    		fileContent = fileContent.replace(/\'/g, "\\\'");                 		
                 }
				if(fileContent.indexOf('\"') != -1){
                   			fileContent = fileContent.replace(/\"/g, "&#34;");
                }
                    	
				if(fileContent.indexOf('\n') != -1){
				var	app=navigator.appName.substring(0,1);
//	alert(app);
					if(app == 'M'){
 //                  			fileContent = fileContent.replace(/\n/g, "&ltbr&gt");
 						fileContent = fileContent.replace(/\n/g, "<br>");
                   	}
                 }
      			$('contentF').innerHTML = fileContent;
   				}
			catch(err)	{
				alert("ERR: " + err);
			} 
          },
          
          
         'uploadFinal' : function()    {
   
		 try{

		 WebGroupDisplay.processConfUpload(ManageLoads.dispUploadMes);

 			 } catch(err) {
                  alert("ERR: " + err);
             }
          },
          
          'dispUploadMes' : function(txt)	{
			try	{

				var confMessage = eval('(' + txt + ')');

				var message = confMessage.messageF;
				$('statusProc').style.display = "none";
				if($('messageF'))
					$('messageF').innerHTML = message;

   			}
			catch(err)	{
				alert("ERR: " + err);
			}
			 
		},

        'deleteSelectedRecs' : function(recTag)    {
//  alert("DEBUG in delete");
            try{
                var sMembers = Array();
				var confirmMsg = "Delete the checked records?\n";
                var ls = $(recTag) ? $(recTag).getElementsByTagName('input') : Array();

                for(var i=0; i<ls.length; i++){
                   if(ls[i].type=='checkbox' && (ls[i].selected || ls[i].checked)){
                        sMembers.push(ls[i].name);
             //           confirmMsg += ls[i].name + "\n";
                    }
                }
                if(sMembers.length < 1){
                   alert("Please select some records to Delete");
                   throw("no records selected");
                }
 
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
                    
                    
                    
                    var ShortinputFileName = logCont.inputFileName.length>21 ? logCont.inputFileNme.substring(0,20) + "..." : logCont.inputFileName;
                    var ShortvalidationScript = logCont.validationScript.length>21 ? logCont.validationScript.substring(0,20) + "..." : logCont.validationScript;
                    var ShortuploadScript = logCont.uploadScript.length>21 ? logCont.uploadScript.substring(0,20) + "..." : logCont.uploadScript;
                    var ShortoutFileName = logCont.outFileName.length>21 ? logCont.outFileName.substring(0,20) + "..." : logCont.outFileName;
                    
                    var numOfRecs = logCont.numOfRecords;
                    var date = logCont.logDate;
                    var files = logCont.files;
                    var vsContent = "";
                    var usContent = "";
                    var outContent = "";
                    var vsId = "";
                    var usId = "";
                    var outId = "";
// debugger;
                    for(var j=0; j<files.length; j++){
                    var fCont = files[j];
                    	if(fCont.logName == logCont.validationScript){
                    		vsContent = fCont.fileContent;
                    		vsId = fCont.recID;
                    		
                    	}else
                    	if(fCont.logName == logCont.uploadScript){
                    		usContent = fCont.fileContent;
                    		usId = fCont.recID;
                    	}else
                    	if(fCont.logName == logCont.outFileName){
                    		outContent = fCont.fileContent;
                    		outId = fCont.recID;
                    	} 
                    }
 
						if(vsContent.indexOf('\\') != -1 || usContent.indexOf('\\') != -1 || outContent.indexOf('\\') != -1){
                 	  		vsContent = vsContent.replace(/\\/g, "\\\\\\");
                 	  		usContent = usContent.replace(/\\/g, "\\\\\\");
                 	  		outContent = outContent.replace(/\\/g, "\\\\\\");
                    	}
                    	if(vsContent.indexOf('\'') != -1 || usContent.indexOf('\'') != -1 || outContent.indexOf('\'') != -1){
                    		vsContent = vsContent.replace(/\'/g, "\\\'"); 
                    		usContent = usContent.replace(/\'/g, "\\\'");
                    		outContent = outContent.replace(/\'/g, "\\\'");                  		
                    	}
						if(vsContent.indexOf('\"') != -1 || usContent.indexOf('\"') != -1 || outContent.indexOf('\"') != -1){
                   			vsContent = vsContent.replace(/\"/g, "&#34;");
                   			usContent = usContent.replace(/\"/g, "&#34;");
                   			outContent = outContent.replace(/\"/g, "&#34;");
                    	}
                    	
						if(vsContent.indexOf('\n') != -1 || usContent.indexOf('\n') != -1 || outContent.indexOf('\n') != -1){
                   			vsContent = vsContent.replace(/\n/g, "\\n");
                   			usContent = usContent.replace(/\n/g, "\\n");
                   			outContent = outContent.replace(/\n/g, "\\n");
                    	}
                    	
                  
						if(vsContent.indexOf('<') != -1 || usContent.indexOf('<') != -1 || outContent.indexOf('<') != -1){
                   			vsContent = vsContent.replace(/</g, "&lt;");
                    	}
                    	if(vsContent.indexOf('>') != -1 || usContent.indexOf('>') != -1 || outContent.indexOf('>') != -1){
                   			vsContent = vsContent.replace(/>/g, "&gt;");
                    	}



					tst += "<tr class='odd'>"
						+"<td>"
						+"<input type='checkbox' name='"+recID+"'/>"
						+"</td>"
						+"<td>"+date+"</td>"
						+"<td>"
//						+"<a>"+inputFileName+"</a>"
						+ inputFileName
						+"</dt>"
						+"<td>"
						+"<a onclick=\"ManageLoads.displayFileContent('"+vsId+"')\" href='#'>"+ShortvalidationScript+"</a>"
						+"</dt>"
						+"<td>";
						if(uploadScript == "N/A"){
							tst += uploadScript;
						} else{
							tst +="<a onclick=\"ManageLoads.displayFileContent('"+usId+"')\" href='#'>"+ShortuploadScript+"</a>"
					//		tst +="<a onclick=\"alert(Open '+(this).text()+'')\" href='#'>"+uploadScript+"</a>";
						}
						tst +="</dt>"
						+"<td>";
						if(outFileName == "N/A"){
							tst += outFileName;
						} else{
							tst +="<a onclick=\"ManageLoads.displayFileContent('"+outId+"')\" href='#'>"+ShortoutFileName+"</a>"
						//	tst +="<a onclick=\"alert(Open '+(this).text()+'')\" href='#'>"+outFileName+"</a>";
						}						
						tst +="</dt>"
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
