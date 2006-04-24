<script language="javascript" src="js/prototype_1.5pre.js"></script>
<script language="javascript" src="js/event-selectors.js"></script>
<script language="javascript" src="js/stripeScript.js"></script>

<link href="js/activewidgets/runtime/styles/xp/grid.css" rel="stylesheet" type="text/css" ></link>
<script src="js/activewidgets/runtime/lib/grid.js"></script>

	<!-- grid format -->
	<style>
		.active-controls-grid {height: 100%; font: menu;}
/*
		.active-column-0 {width:  80px;}
		.active-column-1 {width: 200px;}
		.active-column-2 {text-align: right;}
		.active-column-3 {text-align: right;}
		.active-column-4 {text-align: right;}
*/
iframe	{
	margin-bottom:20px;
}
		.active-grid-column {border-right: 1px solid threedlightshadow;}
		.active-grid-row {border-bottom: 1px solid threedlightshadow;}
	</style>

<script type='text/javascript' src='dwr/interface/IdLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

<style>
	.redBottom td	{
		border-top: 3px solid red;
	}
</style>
<script language="javascript">
	
	var StatusSpan = {
		'start' : function()	{
			//Effect.BlindUp("lookupResults");
			
			$('statusSpan').innerHTML = "<img src=\"images/indicator.gif\"/>";
			$('lookupButton').disabled = true;
			$('lookupButton').value = "searching...";
			//setTimeout("StatusSpan.stop()", 2000);
			//$('lookupString').value
			
			A_IdLookup.lookup($('lookupString').value);
		},
		'stop' : function(pats)	{
			$('statusSpan').innerHTML = pats + " registrant(s) returned";
			$('lookupButton').value = "search";
			$('lookupButton').disabled = false;
			var sect = "lookupResults"
			$('lookupString').value = '';
			
			//Effect.BlindDown(sect);
			//Stripe.stripe("dataTable");	
		},
		'removeIframes' : function()	{
			var obj = $('ifcontainer');
			while(obj.firstChild) obj.removeChild(obj.firstChild);
		}
	
	};
	 var Rules = {
		'#lookupButton:click': function(element)	{
			$('lookupResults').style.display = "none";
			$('lookupResults').innerHTML = "";
			StatusSpan.removeIframes();
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
	 
	 var myGrids = Array();
	 
	 var A_IdLookup	=	{
	 	'lookup': function(commaIds)	{
	 		if($('gridFrame'))
		 		$('gridFrame').style.display = 'none'; //hide it
	 		IdLookup.lookup(commaIds, A_IdLookup.lookup_cb);
	 	},
	 	'lookup_cb' : function(txt)	{
	 		
	 		var numpatients = 0;
	 		d = document;
	 		
	 		//hold the arr data for grid
	 		var _myData = new Array();
	 		var _myColumns = [
				"RegID", "LabTrack ID", "Timepoint", "Core Type", "Section Info"
			];
				
	 		try	{
	 			registrants = txt.getElementsByTagName("registrant");
		 		//games = txt.getElementsByTagName("sample");
		 		
		 		if(registrants.length < 1)	{
		 			//no records
		 			throw("No records found. Please try again.");
		 		}
		 		
		 		numpatients = registrants.length;
		 		var frameid;
		 		for(var r=0; r<registrants.length; r++)	{
		 			
		 			games = registrants[r].getElementsByTagName("sample");
		 			
		 			//generate the iframe
		 			frameid = registrants[r].getAttribute("regId");
		 			_myData = new Array();
		 			
			 		//var ifrm = "<iframe id=\"if_"+frameid+"_if\" name=\"if_"+frameid+"_if\" src=\"awWrapper.do\" frameborder=\"3\" style=\"width:100%;\"></iframe>";
					//var ins = new Insertion.After("lookupResults", ifrm);
					
					var eDIV = document.createElement("iframe");
					eDIV.setAttribute("id","if_"+frameid+"_if");
					eDIV.setAttribute("name","if_"+frameid+"_if");
					eDIV.setAttribute("src","awWrapper.do?reg="+frameid);
					eDIV.setAttribute("style","width:100%");
					eDIV.setAttribute("frameborder","0");
					// append your newly created DIV element to an already existing element.
					document.getElementById("ifcontainer").appendChild(eDIV);
					
				/*
					for(i=0;i<games.length;i++) {
					
						_myData[i] = new Array();
					
						data = games[i].childNodes;
						
						for(j=0;j<data.length;j++) {
							if(data[j].nodeType == 1) {
								_myData[i][j] = data[j].childNodes[0].nodeValue;
							} 
						}
					}
					//make a grid in each frame
					//the new iframe is not in the dom yet!
					myGrids[r] = new Object();
					myGrids[r].frameid = frameid;
					myGrids[r].myData = _myData;
					myGrids[r].myColumns = _myColumns;
				*/	

				}
				
	 		}
	 		catch(err)	{
	 			$('lookupResults').innerHTML = err;
	 			$('lookupResults').style.display = "";
	 		}
	 		finally	{
		 		setTimeout(function()	{ StatusSpan.stop(numpatients)}, 1000);
		 		/*
		 		myGrids.each	(function(item, index)	{
		 		
					var tmpp = "if_"+item.frameid+"_if";
					if(window.frames[tmpp])	{
						
					
				//		(10000).times(function(n) {
				//		 var t= "";  
				//		});
						
												
						setTimeout(function() { window.frames[tmpp].Grid.makeGrid(item.myData, item.myColumns)}, 100);
						Effect.Appear(tmpp);
					}
					}
				);
				*/
				
		 	}
	 	}
	 	
	 }
	 

		 
		window.onload = function()	{
			//RoundedTop("div.tops", "#e0e0e0", "gray");
			//RoundedBottom("div.mains", "#e0e0e0", "#fff");
			EventSelectors.start(Rules);			
		};
		
		
	</script>

<fieldset id="lookupDiv">
	<legend>Patient or Sample Id(s)</legend>
	<form id="theForm" action="#" method="get" onsubmit="return false;">
	<input type="text" name="lookup" id="lookupString"/>
	<input type="button" id="lookupButton" value="search"/>
	<span id="statusSpan"></span>
	</form>
	
	<div id="lookupResults" style="display:none"></div>
	<div id="ifcontainer">
	
	</div>
</fieldset>
<br/><br/>