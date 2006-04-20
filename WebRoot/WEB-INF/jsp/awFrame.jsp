<html>
<head>

	<style> body, html {margin:0px; padding: 0px; overflow: hidden;} </style>


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
		function testTT() {
			alert("test");
		}
		
		var Grid = {
			'makeGrid' : function(myData, myColumns)	{
				//	create ActiveWidgets Grid javascript object
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
	</script>
</head>
<body>
	<div id="tbl">
		<img src="images/indicator.gif" style="align:center"/>	
	</div>

</body>
</html>