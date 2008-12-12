/*****************************
code for saving a tmp gene list
to the session, across pagination
******************************/

var SaveGenes = {
	'savedHeader' : "Selected Genes:\n<br/>",
	'currentTmpReporters' : "",
	'currentTmpReportersCount' : 0,
	'A_saveTmpGene' : function(g)	{
			var gene = g.value;
			
			if(g.checked == true)	{ // && currentTmpReporters.indexOf(reporter.value)==-1
				DynamicReport.saveTmpGene(gene, SaveGenes.A_saveTmpGene_cb);
				//alert("Adding " + rep);
			}
			else	{
				DynamicReport.removeTmpGene(gene, SaveGenes.A_saveTmpGene_cb);
				//alert("removing " + rep);
			}
		},
	'A_saveTmpGene_cb' : function (txt)	{
			//look9ing for txt["count"] and txt["elements"]..txt["elements"] is a <br/> delim string
			//reporter has been added to the list,
			//show how many we've saved,
			if(txt["count"] > -1) {
				if($("reporterCount"))
					$("reporterCount").innerHTML = txt["count"] + " genes selected";
				SaveGenes.currentTmpReportersCount = txt["count"];
				
				//update the running tab for overlib if this is not an init call
				SaveGenes.currentTmpReporters = txt["elements"];
				
				//highlight box in red with the tempReporterName if we have some waiting to be saved
				if($("tmp_prb_queryName") && txt["count"] > 0)
					$("tmp_prb_queryName").style.border = "1px solid red";
			}
			else	{
				SaveGenes.A_clearTmpGenes_cb("");
			}
		},
	'A_clearTmpGenes' : function ()	{
			DynamicReport.clearTmpGenes(SaveGenes.A_clearTmpGenes_cb);
		},
	'A_clearTmpGenes_cb' : function (txt)	{
			if($("reporterCount"))
				$("reporterCount").innerHTML = "";
			SaveGenes.currentTmpReportersCount = 0;
			SaveGenes.currentTmpReporters = "";
			if($("tmp_prb_queryName"))
				$("tmp_prb_queryName").style.border = "1px solid";
	
			SaveGenes.A_uncheckAll(document.getElementsByName('tmpReporter'));
		},
	'A_checkAll' : function(field)	{
			//checks all on the Current page only
			if(field.length > 1)	{
				for (i = 0; i < field.length; i++)	{
					field[i].checked = true ;
					SaveGenes.A_saveTmpGene(field[i]);
				}
			}
			else
				field.checked = true;
		},
	'A_uncheckAll' : function(field)	{
			if(field.length > 1)	{
				for (i = 0; i < field.length; i++)	{
					field[i].checked = false ;
					SaveGenes.A_saveTmpGene(field[i]);
				}
			}
			else
				field.checked = false;
				
			$("checkAll").checked = false;
				
		},
	'manageCheckAll' : function(box)	{
			if(box.checked)	{
				SaveGenes.A_checkAll(document.getElementsByName('tmpReporter'));
			}
			else	{
				SaveGenes.A_uncheckAll(document.getElementsByName('tmpReporter'));
			}
		},
	'A_checkAllOnAll' : function(box)	{
		//clear the tmp ones weve already checked
		//get all the genes on all pages and savethem
		
		//update the UI to show which ones are checked and precheck all the boxes
		if(box.checked && allGenes.length && allGenes.length > 1)	{
		//	SaveGenes.A_checkAll(allGenes);
			if(allGenes.length > 1)	{
				/*
				for (i = 0; i < allGenes.length; i++)	{
					DynamicReport.saveTmpGene(allGenes[i], SaveGenes.A_saveTmpGene_cb);
				}
				*/
				
				DynamicReport.saveTmpGeneFromArray(allGenes, SaveGenes.A_saveTmpGene_cb);
				
				setTimeout("SaveGenes.preCheckGenes()", 500);
			}
		}
		else if(!box.checked)	{
		
			SaveGenes.A_clearTmpGenes();
		}
	},
	'A_saveGenes' : function()	{
			//get the name
			var name = $("tmp_prb_queryName").value;
			if(name != "")	{
				//convert the overlib list to a comma seperated list
				if(SaveGenes.currentTmpReporters != "")	{
					var replaceme = "<br/>";
					var commaSepList = SaveGenes.currentTmpReporters.replace(/<br\/>/g, ",");
					if(commaSepList.charAt(commaSepList.length-1) == ",")
						commaSepList = commaSepList.substring(0, commaSepList.length-1);
					//alert("="+commaSepList+"=");
					
					//will actually call the new stuff to save a gene list - RL
					//DynamicReport.saveGenes(commaSepList, name, SaveGenes.A_saveGenes_cb);
					var geneArray = new Array();
					geneArray = commaSepList.split(",");
					DynamicListHelper.createGeneList(geneArray, name, SaveGenes.A_saveGenes_cb);
				}
				else	{
					alert("Please select some genes to save");
				}
			}
			else	{
				alert("Please enter a name for your gene group");
			}
		},
	'A_saveGenes_cb' : function(txt)	{
			var results = txt == "pass" ? "Gene List Saved" : "There was a problem saving your gene list";
			alert(results); //pass | fail
			if(txt != "fail")	{
				//erase the name
				$("tmp_prb_queryName").value = "";
				//clear the sample list
				SaveGenes.A_clearTmpGenes();
			}
			//attempt to refresh the parents sidebar
			if(!window.opener.closed)	{
				try	{
					window.opener.SidebarHelper.loadSidebar();
				}
				catch(err)	{
					alert("cant update sidebar: " + err);
				}
			}
		},
	'A_initSaveGene' : function()	{
			DynamicReport.saveTmpGene("", SaveGenes.A_initTmpGene_cb);
		},
	'A_initTmpGene_cb' : function(txt)	{
			SaveGenes.A_saveTmpGene_cb(txt);
			//now, check the ones if theyve been previously selected
			var field = document.getElementsByName('tmpReporter');
			
			if(field.length >= 1 && SaveGenes.currentTmpReporters != "")	{				
				for (i = 0; i < field.length; i++)	{
					if(SaveGenes.currentTmpReporters.indexOf(field[i].value) != -1 )
						field[i].checked = true ;
				}
			}
			else	{
				if(SaveGenes.currentTmpReporters.indexOf(field.value) != -1 )
					field.checked = true;
			}
		},
	'preCheckGenes' : function()	{
		var field = document.getElementsByName('tmpReporter');
		
		//alert(SaveGenes.currentTmpReporters);
		
			if(field.length > 1 && SaveGenes.currentTmpReporters != "")	{
				for (i = 0; i < field.length; i++)	{
					if(SaveGenes.currentTmpReporters.indexOf(field[i].value) != -1 )
						field[i].checked = true ;
				}
			}
			else	{
				if(SaveGenes.currentTmpReporters.indexOf(field.value) != -1 )
					field.checked = true;
			}
	
	}
};