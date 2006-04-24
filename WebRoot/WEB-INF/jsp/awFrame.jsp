<html>
<head>

	<style> 
		body, html {
			margin:0px; padding: 0px; overflow: hidden;	
		} 
		div	{ font-family: arial; font-size:11px;}
	</style>


	<script type='text/javascript' src='dwr/interface/IdLookup.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>

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

				document.getElementById("tbl").innerHTML = obj;
			}
		};
	
	
<%
	String registrant = request.getParameter("reg")!=null ? request.getParameter("reg") : null;
	if(registrant!=null)	{
	%>
		window.onload = function()	{
			document.getElementById('tbl').innerHTML += " displaying data for registrant:" + <%=registrant%>+"";
			setTimeout(function()	{ IdLookup.lookup(<%=registrant%>, A_IdLookup.lookup_cb) }, 800);
		
		}
		
	<%	
	}
%>
	var A_IdLookup = {
		'lookup_cb' : function(txt)	{
			var _myColumns = [
					"RegID", "LabTrack ID", "Timepoint", "Core Type", "Section Info"
				];
				
			var _myData = Array();
				
			try	{
			
	 			var registrants = txt.getElementsByTagName("registrant");
		 		
		 		if(registrants.length < 1)	{
		 			//no records
		 			throw("No records found. Please try again.");
		 		}
	
		 		var numpatients = registrants.length;
		 		var frameid;
		 		for(var r=0; r<registrants.length; r++)	{
		 			
		 			var samples = registrants[r].getElementsByTagName("sample");
		 			
					for(i=0;i<samples.length;i++) {
					
						_myData[i] = new Array();
					
						data = samples[i].childNodes;
						
						for(j=0;j<data.length;j++) {
							if(data[j].nodeType == 1) {
								_myData[i][j] = data[j].childNodes[0].nodeValue;
							} 
						}
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