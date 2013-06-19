
function checkReporterSelection(elementX, elementY, reporterX, reporterY, targetValue, name){
	elementX = elementX.value;
	elementY = elementY.value;
	reporterX = reporterX.value;
	reporterY = reporterY.value;
	//alert("targertval: " + targetValue);
	
	if(elementX != "none"){
		if (elementX == targetValue && reporterX == "none"){	
			$('reporterStatusX').innerHTML = "<div class='err'><img src='images/err_y.png' align='left'>Please select a reporter</div>";
			return false;
		}
	}
	if(elementY != "none"){
		if (elementY == targetValue && reporterY == "none"){	
			$('reporterStatusY').innerHTML = "<div class='err'><img src='images/err_y.png' align='left'>Please select a reporter</div>";
			return false;
		}
	}	
	
}

function getReporterList(element, gene, platform, elementToUpdate){    	
    	if(gene!=""){
    		ReporterLookup.lookup(gene, platform, elementToUpdate, createReporterList);
    	}
	}
	
function createReporterList(txt){  	 	
    	
    	
    	try	{
				var res = eval('(' + txt + ')');
				
				var result = res[0].results;
				var elementToUpdate = res[0].elementId;
				var reporters = res[0].reporters;
				
				if(result == "found"){				
					DWRUtil.removeAllOptions(elementToUpdate);    			
    				DWRUtil.addOptions(elementToUpdate, reporters);
    			}
    			else{
    				DWRUtil.removeAllOptions(elementToUpdate);    			
    				DWRUtil.addOptions(elementToUpdate, ['none']);
    				alert("No reporters found for: " + res[0].gene);
    			}
			}
		catch(err){
				alert(err);
			}    	
	}
	
function ediv(el,div,changeParam)	{							
						var elId = el.id;	
						//alert(elId);		   
	    				var el = el.value; //the element value to check	
	    				//alert(el);
	    				//alert(changeParam);	    				
	    				var hideable = div;  //the element to show or hide
						var bigflag = false;	
		
						if(el == changeParam) {
							bigflag = true;
						}
						else{						
							bigflag = false;
						}		
						if(bigflag)	{
							
							if(win)	{ Effect.BlindDown(hideable); }
							else	{ $(hideable).style.display = "block"; }			
						}
						else	{							
							//its default settings							
							if(win)	{
									setBackToDefault(elId);									
									Effect.BlindUp(hideable); 							
									}
							else	{ 
									setBackToDefault(elId);									
									$(hideable).style.display = "none"; 									
									} 			
						}
			
					}

function setBackToDefault(formType){
		//alert(formType);
		if(formType == "pca"){
			document.getElementById("variancePercentile").value = "70.0";
		}
		else if(formType == "hc"){
		document.getElementById("variancePercentile").value = "95.0";
		document.getElementById("geneList").value = "none";
		}
		else if(formType == "cc"){
		document.getElementById("statisticalMethod").value = "TTest";
		document.getElementById("comparisonAdjustment").value = "NONE";
		document.getElementById("foldChange").value = "list";
		document.getElementById("statisticalSignificance").value = .05;
		document.getElementById("foldChangeManual").value = "";
		document.getElementById("foldChangeAuto").value = "2";
		}
		else if(formType == "xaxis"){
		document.getElementById("geneSymbolX").value = "";
		document.getElementById("reporterX").options[0].value = "none";	
		document.getElementById("reporterX").options[0].text = "none";	
		document.getElementById("reporterX").options.length = 1;
						
		}
		else if(formType == "yaxis"){
		document.getElementById("geneSymbolY").value = "";
		document.getElementById("reporterY").options[0].value = "none";
		document.getElementById("reporterY").options[0].text = "none";
		document.getElementById("reporterY").options.length = 1;						
		}
		
			
	}
	

function checkNull(text){
	
	if(text.value == "")	{
		
		scroll(0,0);
		text.focus();
		text.style.border = "2px solid red";
		alert("Please Fill in a Unique Query Name");
		return false;
	}
	else	{
	
		return checkQueryName();
	}
}

function spawnx(url,winw,winh, name) {

  var w = window.open(url, name,
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
	
	//check for pop-up blocker
	if (w==null || typeof(w)=="undefined") {
		alert("You have pop-ups blocked.  Please click the highlighted link at the top of this page to view the report.  You may disable your pop-up blocker for this site to avoid doing this in the future.");
		/*
		if(document.all) {
			  document.all.popup.visible = "true"; 
			  document.all.popup.className = "pop";
		      document.all.popup.innerText = "You have pop-ups blocked.  Click <a href=\"javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');\">here</a> to view the report."; 
			  
		    } else { 
			  document.getElementById('popup').visible = "true";
			  document.getElementById('popup').className= "pop";
		      document.getElementById('popup').innerHTML = "You have pop-ups blocked.  Click <a href=\"javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');\">here</a> to view the report."; 	  
		} 
		*/
		document.write("<Br><Br><span class=\"pop\">You have pop-ups blocked.  Click <a href=\"javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');\">here</a> to view the report.</span>");
		//scroll(0, 8000);
	} else {
		w.focus();

	}
} 

function spawn(url,winw,winh) {
  var w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
} 



function deleteRow(queryNum){
//get elements body and table
 var mybody=document.getElementsByTagName("body").item(0);
 var mytable=mybody.getElementsByTagName("table").item(0);
 
 //show warning if user has deleted all but one row
 if (mytable.rows.length == 2){
 alert('Deleting any more rows will create an invalid query');
 }
 //delete rows by finding the row id and the row's index
 else {
  var thisTR = document.getElementById(queryNum);
  var thisTRIndex = thisTR.rowIndex;
  mytable.deleteRow(thisTRIndex);
   }
 }

  
 
 function onRadio(formElement,i){
     
   //selected index of the selected
	var element = formElement.name;
	
	  if(element == "groupsOption"){
	  	if (i == "allSamples"){
	  		document.getElementById("button1").disabled = true;
	  		document.getElementById("button2").disabled = true; 		
	  	}
	  	else if(i == "variousSamples"){
	  		document.getElementById("button1").disabled = false;
	  		document.getElementById("button2").disabled = false; 	
	  	}
	  }
	  
	  if(element == "tumorType" || formElement == "tumorType"){
	    document.forms[0].tumorGrade.options.length = 1;
	        myOption1 = new Option("I","one");
		    myOption2 = new Option("II","two");
		    myOption3 = new Option("III","three");
		    myOption4 = new Option("IV","four");
		    
		 
		if(i == "ALL"){
		 document.forms[0].tumorGrade.options[1] = myOption1;
	     document.forms[0].tumorGrade.options[2] = myOption2;
	     document.forms[0].tumorGrade.options[3] = myOption3;
	     document.forms[0].tumorGrade.options[4] = myOption4;
	     }    
	    if(i == "ASTROCYTOMA"){
	     document.forms[0].tumorGrade.options[1] = myOption2;
	     document.forms[0].tumorGrade.options[2] = myOption3;
	     }
	    if(i == "GBM"){
	     document.forms[0].tumorGrade.options[1] = myOption4;
	     }
	    if(i == "OLIG"){
	     document.forms[0].tumorGrade.options[1] = myOption2;
	     document.forms[0].tumorGrade.options[2] = myOption3;
	     }
	    if(i == "MIXED"){
	     document.forms[0].tumorGrade.options[1] = myOption1;
	     document.forms[0].tumorGrade.options[2] = myOption2;
	     document.forms[0].tumorGrade.options[3] = myOption3;
	     }
	  }
	
	  if(element == "isAllGenesQuery"){
	   if(i == 0){
	    document.forms[0].allGeneQuery.disabled = true;
	    checkToggle(formElement, "qrows");
	    }	   
	   if(i == 1){
	    document.forms[0].allGeneQuery.disabled = false;
	    checkToggle(formElement, "qrows");
	    }
	   }	
	  
	  if(element == "sampleGroup"){
	    if(i == 0){
	    document.forms[0].sampleFile.disabled = true;
	    document.forms[0].sampleList.disabled = false;
	    }
	    if(i == 1){
	    document.forms[0].sampleList.value = "";
	    document.forms[0].sampleList.disabled = true;
	    document.forms[0].sampleFile.disabled = false;
	    }
	  }
		
	  if(element == "plot"){
	  document.forms[0].quickSearchType.options.length = 1;
	  
	     if (i == 2){
	      myOption = new Option();
	      myOption2 = new Option();
	      myOption.text = "Gene Keyword";
		  myOption2.text = "SNP Probe set ID";
	      document.forms[0].quickSearchType.options[0] = myOption;
	      document.forms[0].quickSearchType.options[1] = myOption2;
		  }	
	     else if(i != 2){
	      myOption = new Option();
		  myOption.text = "Gene Keyword";
	      document.forms[0].quickSearchType.options[0] = myOption;
	     }
	   }	
		
	  if(element == "geneGroup"){	
	      if (i == 0){
	      
		  document.forms[0].geneList.disabled = false;
		  document.forms[0].geneType.disabled = false;
		  document.forms[0].geneFile.disabled = true;
		  document.forms[0].geneList.focus();
		  }
	      if (i == 1) {
	      
	      document.forms[0].geneList.value = "";
	      document.forms[0].geneList.disabled = true;
		  document.forms[0].geneFile.disabled = false;
		  document.forms[0].geneFile.focus();
	      }
	   }
	   
	   if(element == "cloneId"){	
	      if (i == 0){
	      document.forms[0].cloneListSpecify.disabled = false;
		  document.forms[0].cloneList.disabled = false;	
		  document.forms[0].cloneListFile.disabled = true;
 		  document.forms[0].cloneListSpecify.focus();
		
		  }
	      if (i == 1) {
	      
	      document.forms[0].cloneListSpecify.value = "";
		  document.forms[0].cloneListSpecify.disabled = true;	    
		  document.forms[0].cloneListFile.disabled = false;
	      document.forms[0].cloneListFile.focus();
	      }
	   }
	   if(element == "snpId"){
	      if (i == 0){		  
	        document.forms[0].snpListSpecify.disabled = false;			
	        document.forms[0].snpList.disabled = false;
			document.forms[0].snpListSpecify.focus();
			document.forms[0].snpListFile.value = "";	
	        document.forms[0].snpListFile.disabled = true;
	      }
	      if (i == 1){
	        document.forms[0].snpListSpecify.value = "";			
	        document.forms[0].snpListSpecify.disabled = true;	        
	        document.forms[0].snpListFile.disabled = false;
			document.forms[0].snpListFile.focus();
	      }
	   }
	   
  }

 function alertUser(){
  
if(confirm("This will eliminate all data currently entered in the form and will not add a query"))
{
location.href='menu.do';
}

  
 }

function hideLoadingMessage(){
	if(document.getElementById('spnLoading') != null)
			document.getElementById('spnLoading').style.display = "none" ;
}
		

function checkToggle(box, id)	{
	if(box.value == 'false')
		toggleDiv(id, false);
	else
		toggleDiv(id, true);
}

function toggleDiv(id, state)	{
		if(state)
		    document.getElementById(id).style.display = "none";
		else
			document.getElementById(id).style.display = "block";	
}
 

function preMove(test, fbox, tbox)	{

	if(test)	{
		move(fbox, tbox);
	}
	else	{
		//alert("Invalid Selection");	
	}
}

function move(fbox, tbox) {

	var arrFbox = new Array();
	var arrTbox = new Array();
	var arrLookup = new Array();
	var i;
	for(i = 0; i < tbox.options.length; i++) {
		arrLookup[tbox.options[i].text] = tbox.options[i].value;
		arrTbox[i] = tbox.options[i].text;
	}
	var fLength = 0;
	var tLength = arrTbox.length;
	for(i = 0; i < fbox.options.length; i++) {
		arrLookup[fbox.options[i].text] = fbox.options[i].value;
		if (fbox.options[i].selected && fbox.options[i].value != "") {
			arrTbox[tLength] = fbox.options[i].text;
			tLength++;
		}
		else {
			arrFbox[fLength] = fbox.options[i].text;
			fLength++;
		}
	}
	//arrFbox.sort();
	//arrTbox.sort();
	fbox.length = 0;
	tbox.length = 0;
	var c;
	for(c = 0; c < arrFbox.length; c++) {
		var no = new Option();
		no.value = arrLookup[arrFbox[c]];
		no.text = arrFbox[c];
		fbox[c] = no;
	}
	for(c = 0; c < arrTbox.length; c++) {
		var no = new Option();
		no.value = arrLookup[arrTbox[c]];
		no.text = arrTbox[c];
		tbox[c] = no;
	}
}

function saveMe(tbox,fbox) {

var strValues = "";

if(tbox == null || fbox == null || !tbox.length)
	return;
	
var boxLength = 0;
if(tbox.length)	{ boxLength = tbox.length; }


var fboxLength = 0;
if(fbox.length) { fboxLength = fbox.length;}

var count = 0;
if (boxLength != 0) {

	for (i = 0; i < boxLength; i++) {
		if (count == 0) {
		strValues = tbox.options[i].value;
		}
		else {
		strValues = strValues + "," + tbox.options[i].value;
		}
	count++;
   }
   
}
if (strValues.length == 0) {
//alert("You have not made any selections");
}
else {

//alert("Here are your values you've selected:\r\n" + strValues);
for(i=0; i < boxLength; i++)
  {
    tbox.options[i].selected = true;
    
  }

}

}
function tdiv(el,div,formType)	{
	
	   
	    var elName = el.name; //the element to check
	    var hideable = div;  //the element to show or hide
	  
		//look at the 1st radio...this wont work with more than 2 options
		var bigflag = document.getElementsByName(elName)[0] ? document.getElementsByName(elName)[0].checked : true;
		
		if(!bigflag)	{
			if(win)	{ Effect.BlindDown(hideable); }
			else	{ $(hideable).style.display = "block"; }			
		}
		else	{
			//its default settings
			if(win)	{ Effect.BlindUp(hideable); }
			else	{ $(hideable).style.display = "none"; } 
			javascript:setBackToDefault(formType);
		}
			
	}
 
function toggleSDiv(divId,aId){

			var divId = divId;
			var aId1 = aId;
			
			var div = document.getElementById(divId);
			var a = document.getElementById(aId);
			
			
			if(div.className == 'divHide'){
				div.className = 'divShow';
				a.innerHTML = '&nbsp;-&nbsp;';
				}
			else {
				div.className = 'divHide';
				a.innerHTML = '&nbsp;+&nbsp;';
				}
			}
   
   //http://johankanngard.net/2005/11/14/remove-an-element-in-a-javascript-array/
	Array.prototype.remove = function(s){
		for(i=0;i<this.length;i++){
			if(s==this[i]) this.splice(i, 1);
		}
	}

//http://simon.incutio.com/archive/2004/05/26/addLoadEvent
function addLoadEvent(func) {
	var oldonload = window.onload;
  	if (typeof window.onload != 'function') {
    	window.onload = func;
  	} 
  	else {
	    window.onload = function() {
	    	if (oldonload) {
	        	oldonload();
	      	}
			func();
	    }
 	}
}