<html>
<head>

	<style> 
		body, html {
			margin:0px; padding: 0px; 
			/* overflow: hidden;	*/
		} 
		div	{ font-family: arial; font-size:11px;}
		
		#safTable tr td	{
			font-size:10px;
			border:1px solid #000;
			padding: 5px;
		}
	</style>


	<script type='text/javascript' src='dwr/interface/IdLookup.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>
	<script type='text/javascript' src='js/box/browserSniff.js'></script>
	<link href="js/activewidgets/runtime/styles/xp/grid.css" rel="stylesheet" type="text/css" ></link>
	<script src="js/activewidgets/runtime/lib/grid.js"></script>

	<style>
		.active-controls-grid {height: 100%; font: menu;}

		.active-column-0,.active-column-1,.active-column-2,.active-column-3,.active-column-4 {
			width:  80px;
		}
		.active-column-4 {width:150px;}

		.active-grid-column {border-right: 1px solid threedlightshadow;}
		.active-grid-row {border-bottom: 1px solid threedlightshadow;}
	</style>
	<script type="text/javascript">

		var Grid = {
			'makeGrid' : function(myData, myColumns)	{
			
				if(saf)	{
					//alert("saf");
					var obj = "<table id='safTable'>\n"; //some HTML for our table
					obj+="<tr>\n";
					for(var i=0; i<myColumns.length; i++)	{
						obj+= "<td>"+myColumns[i]+"</td>\n";
					}
					obj+= "</tr>\n";
					for(var j=0; j<myData.length; j++)	{
						obj+= "<tr>\n";
						for(var ii=0; ii<myData[j].length; ii++)	{
							obj+="<td>"+myData[j][ii]+"</td>\n";
						}				
						obj+="</tr>\n";
					}
					obj+= "</table>";
				}
				else	{
					//	create ActiveWidgets Grid javascript object
					//note: this does not work with prototype
					var obj = new Active.Controls.Grid;
				
					//	set number of rows/columns
					obj.setRowProperty("count", myData.length);
					obj.setColumnProperty("count", myColumns.length);
				
					//	provide cells and headers text
					obj.setDataProperty("text", function(i, j){return myData[i][j]});
					obj.setColumnProperty("text", function(i){return myColumns[i]});
				
					//	set headers width/height
					obj.setRowHeaderWidth(20);
					obj.setColumnHeaderHeight("20px");
		
					//obj.setRowText(function(i){return myData[i][0]});
					//obj.setRowHeaderWidth("100px");
				}
				
				document.getElementById("tbl").innerHTML = obj;
			}
			
		};
	
	
<%
	String registrant = request.getParameter("reg")!=null ? request.getParameter("reg") : null;
	String sf = request.getParameter("sf")!=null ? request.getParameter("sf") : "";
	if(registrant!=null)	{
	//registrant = registrant.replace("|",",");
	%>
		window.onload = function()	{
			document.getElementById('tbl').innerHTML += " displaying data for registrant: <%=registrant%>";
			setTimeout(function()	{ IdLookup.lookup('<%=registrant%>', A_IdLookup.lookup_cb) }, 800);
		
		}
		
	<%	
	}
%>

	var sf = Array();
	sf = "<%=sf%>".split(",");
	
	var A_IdLookup = {
		'lookup_cb' : function(txt)	{

			var _myColumns = [
					"ISPY ID", "LabTrak ID", "Timepoint", "Core Type", "Section Info"
				];
				
			var _myData = Array();
				
			try	{
			
	 			//var registrants = txt.getElementsByTagName("registrant");
	 			
		 		var registrants = eval(txt);
		 		
		 		if(registrants.length < 2)	{
		 			//no records
		 			throw("No records found. Please try again.");
		 		}
	
	
		 		var numpatients = registrants.length-1;
		 		var frameid;
		 				
		 		for(var r=1; r<registrants.length; r++)	{
		 			
		 			//var samples = registrants[r].getElementsByTagName("sample");
		 			var samples = registrants[r].length > 0 ? registrants[r] : Array();
		 			
					for(i=0;i<samples.length;i++) {
					
						//_myData[i] = new Array();
					
						var tmp2d = new Array();
						
						data = samples[i].childNodes;
						/*
						for(j=0;j<data.length;j++) {
							if(data[j].nodeType == 1) {
								//_myData[i][j] = data[j].childNodes[0].nodeValue;
								tmp2d[j] = data[j].childNodes[0].nodeValue;
							} 
						}
						*/
						tmp2d.push(samples[i].regId);
						tmp2d.push(samples[i].labtrackId);
						tmp2d.push(samples[i].timePoint);
						tmp2d.push(samples[i].coreType);
						tmp2d.push(samples[i].sectionInfo);

						_myData.push(tmp2d);
					}
				}
	 		}
	 		catch(err)	{
	 			document.getElementById('tbl').innerHTML = err;
	 			alert("ERR: " + err);
	 		}
	 		finally	{
		 		Grid.makeGrid(_myData, _myColumns)
			}
			
			//hackified way of climbing the DOM
				var divs = document.getElementsByTagName("div");
				for(var j=0; j<divs.length; j++)	{
					if(divs[j].innerHTML && divs[j].innerHTML.indexOf("<")==-1)	{
						for(var z=0; z<sf.length; z++)	{
							if(divs[j].innerHTML.indexOf(sf[z])!=-1)	{
								divs[j].style.backgroundColor = "yellow";
							}
						}
					}
				}
			
			
			
		}
	};
</script>
</head>
<body>	
	<div id="tbl">
		<img src="images/indicator.gif" style="align:center"/>	
	</div>
</body>
</html>