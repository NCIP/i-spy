<script language="javascript" src="js/prototype_1.5pre.js"></script>
<script language="javascript" src="js/event-selectors.js"></script>
<script language="javascript" src="js/stripeScript.js"></script>

<script type='text/javascript' src='dwr/interface/IdLookup.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

<script language="javascript">
	
	var StatusSpan = {
		'start' : function()	{
			
			$('statusSpan').innerHTML = "<img src=\"images/indicator.gif\"/>";
			$('lookupButton').disabled = true;
			$('lookupButton').value = "searching...";
			
			A_IdLookup.lookup($('lookupString').value);
		},
		'stop' : function(pats)	{
			$('statusSpan').innerHTML = pats + " registrant(s) returned";
			$('lookupButton').value = "search";
			$('lookupButton').disabled = false;
			var sect = "lookupResults"
			$('lookupString').value = '';
			
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
	 		IdLookup.lookup(commaIds, A_IdLookup.lookup_cb);
	 	},
	 	'lookup_cb' : function(txt)	{
	 		
	 		var numpatients = 0;
	 		d = document;
	 						
	 		try	{
	 			registrants = txt.getElementsByTagName("registrant");
		 		
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
		 			
		 			var tDIV = document.createElement("div");
					tDIV.setAttribute("id","div_"+frameid+"_div");
					tDIV.setAttribute("name","div_"+frameid+"_div");
					tDIV.setAttribute("style","width:100%");
					tDIV.setAttribute("class", "titleDiv");
					tDIV.appendChild(document.createTextNode("Registrant: " + frameid + " | "));
					
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
					document.getElementById("ifcontainer").appendChild(tDIV);
					
					var eDIV = document.createElement("iframe");
					eDIV.setAttribute("id","if_"+frameid+"_if");
					eDIV.setAttribute("name","if_"+frameid+"_if");
					eDIV.setAttribute("src","awWrapper.do?reg="+frameid);
					eDIV.setAttribute("style","width:100%");
					eDIV.setAttribute("frameborder","0");
					document.getElementById("ifcontainer").appendChild(eDIV);
					
				}
				
	 		}
	 		catch(err)	{
	 			$('lookupResults').innerHTML = err;
	 			$('lookupResults').style.display = "";
	 		}
	 		finally	{
		 		setTimeout(function()	{ StatusSpan.stop(numpatients)}, 1000);				
		 	}
	 	}
	 	
	 }

		window.onload = function()	{
			EventSelectors.start(Rules);			
		};
		
	</script>
	<style>
		iframe { margin-bottom:20px; }	
		titleDiv	{ margin-top:10px;}
	</style>
	
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