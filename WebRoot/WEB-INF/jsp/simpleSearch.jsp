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
			Effect.BlindUp("lookupResults");
			
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
			Stripe.stripe("dataTable");
			
		}
	
	};
	 var Rules = {
		'#lookupButton:click': function(element)	{
		$('lookupResults').style.display = "none";
		$('lookupResults').innerHTML = "";
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
	 		//alert(commaIds);
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
		 		//$('lookupResults').innerHTML = txt;
		 		/*
		 		var tables = txt.getElementsByTagName("table");
		 		tables.each(function(t)	{
			 			document.write(t.getElementsByTagName("tr").length);
					}	 		
		 		);
		 		*/
		 		games = txt.getElementsByTagName("sample");
		 		
		 		if(games.length < 1)	{
		 			//no records
		 			throw("No records found. Please try again.");
		 		}
		 		
				table = d.createElement("table");
				table.setAttribute("cellspacing","0");
				table.setAttribute("id","dataTable");
				table.setAttribute("border","1");
				table.setAttribute("cellpadding","4");
			
				tr = table.insertRow(0);
				tr.style.backgroundColor = "silver";
				tr.style.color = "#000";
				
				headings = new Array("RegID","LabTrack ID","Timepoint","Core Type","Section Info");
				
				for(k=0;k<headings.length;k++) {
					td = d.createElement("td");
					td.appendChild(d.createTextNode(headings[k]));
					tr.appendChild(td);
				}
				
				//init the counter
				if(games.length > 0)
					numpatients = 1;
				
				for(i=0;i<games.length;i++) {
				
					_myData[i] = new Array();
				
					data = games[i].childNodes;
					tr = table.insertRow(i+1);
					
					if( i > 0 && (games[i].childNodes[0].childNodes[0].nodeValue != games[i-1].childNodes[0].childNodes[0].nodeValue))	{
						tr.className = "redBottom";
						numpatients++;
					}
					//tr.className = i%2?"on":"off";
					
					for(j=0;j<data.length;j++) {
						if(data[j].nodeType == 1) {
							_myData[i][j] = data[j].childNodes[0].nodeValue;
						
							td = d.createElement("td");
							td.appendChild(d.createTextNode(data[j].childNodes[0].nodeValue));
							tr.appendChild(td);
						} 
					}
				}	
				
				$("lookupResults").appendChild(table);	
	 		}
	 		catch(err)	{
	 			$('lookupResults').innerHTML = err;
	 		}
	 		finally	{
		 		setTimeout(function()	{ StatusSpan.stop(numpatients)}, 1000);
		 		
		 		AWwrapper.makeGrid(_myData, _myColumns);

		 	}
	 	}
	 	
	 }
	 
	 
	 var AWwrapper = {
	 	'obj' : new Active.Controls.Grid,
	 	'makeGrid' : function(myData, myColumns)	{
			//	create ActiveWidgets Grid javascript object
			
			//these must be correct!!
			//	set number of rows/columns
			this.obj.setRowProperty("count", myData.length);
			this.obj.setColumnProperty("count", myColumns.length);
			
			//	provide cells and headers text
			this.obj.setDataProperty("text", function(i, j){return myData[i][j]});
			this.obj.setColumnProperty("text", function(i){return myColumns[i]});
			
			//	set headers width/height
			this.obj.setRowHeaderWidth("28px");
			this.obj.setColumnHeaderHeight("20px");
			
			//	set click action handler
			this.obj.setAction("click", function(src){window.status = src.getItemProperty("text")});
			this.obj.setStyle("height", "100%");
			this.obj.setStyle("width", "100%"); 
			//	write grid html to the page

		this.obj.getTemplate("layout").action("adjustSize");
			$('res2').innerHTML = this.obj;
			$('res2').style.height = "1000px";
			alert(this.obj.height);
			//document.write(this.obj);
	 	
	 	},
	 	'writeGrid' : function()	{
	 		document.write(this.obj);
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
	
	<div id="lookupResults" style="display:none">
		hey, here are the results
	</div>
	<div id="res2">
	</div>
	<iframe src="awWrapper.do" frameborder="0" style="width:100%;height:auto; overflow:auto">
	</iframe>

</fieldset>
<br/><br/>