<script language="javascript" src="js/prototype_1.5pre.js"></script>
<script language="javascript" src="js/event-selectors.js"></script>

<script type='text/javascript' src='dwr/interface/IdLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

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
		'stop' : function()	{
			$('statusSpan').innerHTML = "1 record returned";
			$('lookupButton').value = "search";
			$('lookupButton').disabled = false;
			var sect = "lookupResults"
			$('lookupString').value = '';
			Effect.BlindDown(sect);
			
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
	 		
	 		d = document;
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
				table = d.createElement("table");
				table.setAttribute("cellspacing","0");
				table.setAttribute("id","dataTable");
				table.setAttribute("border","1");
				table.setAttribute("cellpadding","4");
			
				tr = table.insertRow(0);
				tr.style.backgroundColor = "silver";
				tr.style.color = "#000";
				headings = new Array("RegID","LabTrack ID","Timepoint");
				
				for(k=0;k<headings.length;k++) {
					td = d.createElement("td");
					td.appendChild(d.createTextNode(headings[k]));
					tr.appendChild(td);
				}
				
				for(i=0;i<games.length;i++) {
					data = games[i].childNodes;
					tr = table.insertRow(i+1);
					
					//tr.className = i%2?"on":"off";
					
					for(j=0;j<data.length;j++) {
						if(data[j].nodeType == 1) {
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
		 		setTimeout("StatusSpan.stop()", 1000);
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
	
	<div id="lookupResults" style="display:none">
		hey, here are the results
	</div>
</fieldset>
<br/><br/>