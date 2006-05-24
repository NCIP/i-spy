<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<script language="javascript" src="js/prototype_1.5pre.js"></script>
<script language="javascript" src="js/event-selectors.js"></script>
<script language="javascript" src="js/stripeScript.js"></script>

<script type='text/javascript' src='dwr/interface/IdLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

<script language="javascript">
	
	//http://johankanngard.net/2005/11/14/remove-an-element-in-a-javascript-array/
	Array.prototype.remove = function(s){
		for(i=0;i<this.length;i++){
			if(s==this[i]) this.splice(i, 1);
		}
	}
	
	var TmpRegistrants = Array();
	var SaveRegs = {
		'preSave' : function()	{
			if(TmpRegistrants.length > 0 && $('regName').value.length > 0)	{
				SaveRegs.save(TmpRegistrants, $('regName').value);
			}
			else	{
				//alert("cant save : " + TmpRegistrants.length + " : " + $('regName').value.length);
				//alert("Please select some registrants and enter a name for your group");
				$('saveStatus').innerHTML = "<br/><br/><b>Error: Please select some registrants and enter a name for your group</b>";
				setTimeout(function() { $('saveStatus').innerHTML = ""; }, 2000);
			}
		},
		'save' : function(regArray, name)	{
			//alert(regArray);
			//clean the array
			//make the ajax call
			$('saveStatus').innerHTML = "<img src=\"images/indicator.gif\"/>";
			setTimeout( function()	{
				IdLookup.createPatientList(regArray, name, SaveRegs.save_cb);
				}, 500);
		},
		'save_cb' : function(txt)	{
			if(txt == "pass")	{
				//alert("List saved successfully");
				$('saveStatus').innerHTML = "<b>List Saved Successfully</b>";
				setTimeout(function() { $('saveStatus').innerHTML = ""; }, 2000);
				
				//uncheck the boxes
				for(var i=0; i<TmpRegistrants.length; i++)	{
					document.getElementById(TmpRegistrants[i]+"_cb").checked = false;
					document.getElementById(TmpRegistrants[i]+"_cb").selected = false;	
					document.getElementById(TmpRegistrants[i]+"_cb").parentNode.style.color = "";				
				}
				$('checkAllBox').checked = false;
				$('checkAllBox').selected = false;
				
				//clear the tmp storage
				TmpRegistrants = new Array();
				$('regName').value = "";
				
				//reload our lists if we can
				if(SidebarHelper)	{
					SidebarHelper.loadPatientUL();
				}
			}
			else	{
				alert("Error saving List.");
			}
		},
		'checkAll' : function(box)	{
			var chks = $('ifcontainer').getElementsByTagName("input");
			
			TmpRegistrants = new Array();  //clear it
			
			for(var v = 0; v<chks.length; v++)	{
				if(chks[v]["type"] == "checkbox")	{
					if(box.checked || box.selected)	{
						chks[v].selected = true;
						chks[v].checked = true;
						TmpRegistrants.push(chks[v].value);
					}
					else	{
						chks[v].selected = false;
						chks[v].checked = false;
					}
				}
			}
		}
	}

	var StatusSpan = {
		'start' : function()	{
			
			$('lookupResults').style.display = "none";
			$('ifcontainer').style.display = "none";
			//$('lookupResults').innerHTML = "";
			StatusSpan.removeIframes();
			
			$('statusSpan').innerHTML = "<img src=\"images/indicator.gif\"/>";
			$('lookupButton').disabled = true;
			$('lookupButton').value = "searching...";
			
			A_IdLookup.lookup($('lookupString').value);
		},
		'stop' : function(pats)	{
			$('statusSpan').innerHTML = pats + " registrant(s) returned";
			$('lookupButton').value = "search";
			$('lookupButton').disabled = false;
			if(pats != 0)	{
				$("lookupResults").style.display = "";
				$("ifcontainer").style.display = "";
			}
			$('lookupString').value = '';
			
		},
		'removeIframes' : function()	{
			var obj = $('ifcontainer');
			while(obj.firstChild) obj.removeChild(obj.firstChild);
		}
	
	};
	
	
	 var Rules = {
		'#lookupButton:click': function(element)	{
			//$('lookupResults').style.display = "none";
			//$('ifcontainer').style.display = "none";
			//$('lookupResults').innerHTML = "";
			//StatusSpan.removeIframes();
			StatusSpan.start();
		},
		'.resultsTable' : function(element)	{
			Stripe.stripe(element.id);
		},
		'.closeme:click' : function(element)	{
			//alert(element.parentNode.id);
			Effect.BlindUp(element.parentNode.id);
			$('statusSpan').innerHTML = "";
			return false;
		},
		'.printme:click' : function(element)	{
			window.print();
		}
	 }
	 	 
	 var A_IdLookup	=	{
	 	'lookup': function(commaIds)	{
	 		IdLookup.lookup(commaIds, A_IdLookup.lookup_cb);
	 	},
	 	'lookup_cb' : function(txt)	{
	 		
	 		var numpatients = 0;
	 		d = document;
	 						
	 		try	{
	 			registrants = txt.getElementsByTagName("registrant");
		 		
		 		var searchedFor = txt.getElementsByTagName("table")[0].getAttribute("name");
		 		//alert("searched for: " + searchedFor);
		 		
		 		if(registrants.length < 1)	{
		 			//no records
		 			throw("No records found. Please try again.");
		 		}
		 		
		 		numpatients = registrants.length;
		 		
		 		var linkDIV = document.createElement("div");
		 		linkDIV.style.marginTop = "20px";
		 		
		 		var csvLink = document.createElement("a");
				csvLink.setAttribute("href","#");
				csvLink.style.marginLeft = "10px";
				csvLink.setAttribute("name", frameid);
				csvLink.onclick = function()  {
					//alert(searchedFor);
					var u = "idDownload.do?i="+searchedFor;
					location.replace(u);
					return false;
				};
				csvLink.appendChild(document.createTextNode("[download all "+numpatients+" patient(s) listed?]"));
		 		linkDIV.appendChild(csvLink);
		 		
		 		if(numpatients > 1)	{
			 		//join table link
			 		var joinLink = document.createElement("a");
					joinLink.setAttribute("href","#");
					joinLink.style.marginLeft = "10px";
					joinLink.style.marginTop = "100px";
					joinLink.setAttribute("name", frameid);
					joinLink.onclick = function()  {
						//alert(searchedFor);
						var u = "awWrapper.do?reg="+searchedFor+"&sf="+searchedFor;
						spawn(u, 600,400);
						return false;
					};
					joinLink.appendChild(document.createTextNode("[show all in 1 table]"));
			 		linkDIV.appendChild(joinLink);
		 		}
		 		document.getElementById("ifcontainer").appendChild(linkDIV);
		 		//
		 		
		 		
		 		var frameid;
		 		for(var r=0; r<registrants.length; r++)	{
		 			
		 			games = registrants[r].getElementsByTagName("sample");
		 			
		 			//generate the iframe
		 			frameid = registrants[r].getAttribute("regId");
		 			
		 			var tDIV = document.createElement("div");
					tDIV.setAttribute("id","div_"+frameid+"_div");
					tDIV.setAttribute("name","div_"+frameid+"_div");
					tDIV.setAttribute("style","width:100%");
					tDIV.setAttribute("class", "titleDiv");
					
					var cBox = document.createElement("input");
					cBox.setAttribute("id",frameid+"_cb");
					cBox.setAttribute("name",frameid+"_cb");
					cBox.setAttribute("type","checkbox");
					cBox.setAttribute("value", frameid);
					cBox.style.border = "0px"; //ie 
					
					cBox.onclick = function()	{
						if(this.checked)	{
							TmpRegistrants.push(this.value);
							this.parentNode.style.color = "red";
							
						}
						else	{
							this.parentNode.style.color = "";
							TmpRegistrants.remove(this.value);
						}
						
						//alert(TmpRegistrants);
					};
					
					tDIV.appendChild(cBox);
					
					tDIV.appendChild(document.createTextNode(r+1 + ") Registrant: " + frameid + " | "));
					
					var eAnchor = document.createElement("a");
					eAnchor.setAttribute("href","#");
					eAnchor.onclick = function()  {
					
						var dsp = this.parentNode.nextSibling.style.display;
						if(dsp == 'none')
							this.parentNode.nextSibling.style.display = "";
						else
							this.parentNode.nextSibling.style.display = "none";
					
					//	Effect.toggle(this.parentNode.nextSibling);		
						return false;
						
					};
					eAnchor.appendChild(document.createTextNode("show/hide table data"));
					tDIV.appendChild(eAnchor);
					
					//tDIV.innerHTML += "&nbsp;<a href='idMapperCSV.do?ids='>[export patient]</a>";
					///
					
					eAnchor = document.createElement("a");
					eAnchor.setAttribute("href","#");
					eAnchor.style.marginLeft = "10px";
					eAnchor.setAttribute("name", frameid);
					eAnchor.onclick = function()  {
					
						var u = "idDownload.do?i="+this.name;
						location.replace(u);
						//alert(this.name);
						return false;
						
					};
					eAnchor.appendChild(document.createTextNode("download"));
					tDIV.appendChild(eAnchor);
					
					////
					
					document.getElementById("ifcontainer").appendChild(tDIV);
					
					var eDIV = document.createElement("iframe");
					eDIV.setAttribute("id","if_"+frameid+"_if");
					eDIV.setAttribute("name","if_"+frameid+"_if");
					eDIV.setAttribute("src","awWrapper.do?reg="+frameid+"&sf="+searchedFor);
					eDIV.setAttribute("style","width:100%");
					eDIV.style.width = "100%";
					eDIV.setAttribute("frameborder","0");
					
					if(numpatients > 1)
						eDIV.style.display = "none";
						
					document.getElementById("ifcontainer").appendChild(eDIV);
					
				}
				
	 		}
	 		catch(err)	{
	 			//$('lookupResults').innerHTML = err;
	 			//$('lookupResults').style.display = "";
	 		}
	 		finally	{
		 		setTimeout(function()	{ StatusSpan.stop(numpatients)}, 1000);				
		 	}
	 	}
	 	
	 }

		window.onload = function()	{
			EventSelectors.start(Rules);			
		};
		
		//Event.observe(document, 'keypress', function(event){ if(event.keyCode == Event.KEY_RETURN) StatusSpan.start();});
		
	</script>
	<style>
		iframe { margin-bottom:20px; }	
		.titleDiv	{ margin-top:10px;}
	</style>
	
<br/>
<fieldset id="lookupDiv">
	<legend>Patient or Sample Id(s)</legend><br/>
	<form id="theForm" action="#" method="get" onsubmit="return false;">
	Please enter ID(s) in a comma seperated format (ex. 123,456,789)
	<app:help help="You may enter Patient Ids or Sample Ids.  Use a comma to seperate multiple Ids.  The results will display id information about the corresponding patients." />
	
	<br/>
	<br/>
	<input type="text" name="lookup" id="lookupString" style="width:200px"/>
	<input type="button" id="lookupButton" value="search"/>
	<span id="statusSpan"></span>
	</form>
	
	
	<div id="ifcontainer" style="display:none">
	
	</div>
	
	<div id="lookupResults" style="display:none; margin-left:20px;margin-top:20px;">
		Save Checked as: <input type="text" id="regName" name="regName"/>
		&nbsp;<input type="button" id="regButton" onclick="SaveRegs.preSave();" value="save" />
		<input style="border:0px" type="checkbox" id="checkAllBox" onclick="SaveRegs.checkAll(this);"/> all?
		<span id="saveStatus"></span>
	</div>
</fieldset>
<br/><br/>