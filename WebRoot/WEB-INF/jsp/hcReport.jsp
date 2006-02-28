<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
	<head>
		<title>Rembrandt Report</title>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script>
		<script language="JavaScript" type="text/javascript" src="XSL/a_js.js"></script>

		<script language="JavaScript" type="text/javascript" src="js/prototype.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/scriptaculous/scriptaculous.js"></script>

	 	<LINK href="xsl/css.css" rel="stylesheet" type="text/css" />
	</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img align="right" src="images/close.png" border="0"></a>
	<a href="javascript: spawn('help.jsp?sect=hcPlot', 350, 500);"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();" /></a>
	<a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/> </a> 	
</span>

<div style="background-color: #ffffff"><img src="images/ispyPortalHeader.gif" /></div>


<%
String key = "taskId";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");
%>
Image Control: 
<!-- 
<a href="#" onclick="fullsize()">fullsize</a> |
<a href="#" onclick="shrink()">small</a> 
-->
<!-- 
<a href="#" onmouseover="grow()" onmouseout="stop()">grow</a> |
<a href="#" onmouseover="small()" onmouseout="stop()">shrink</a> |
<a href="#" onclick="stop()">stop</a> 
-->

<div id="track1" style="background-image: url('images/scaler_slider_track.gif');margin: 4px 0pt 0pt 10px; width: 200px; background-repeat: repeat-x; background-position: left center; height: 18px;">
	<div class="selected" id="handle1" style="width: 18px; height: 18px; position: relative; left: 196px;"><img src="images/scaler_slider.gif"></div>
</div>
<script language="javascript">
function scaleIt(v) {
  var scalePhotos = document.getElementsByClassName('scale-image');

  floorSize = .26;
  ceilingSize = 10.0;
  v = floorSize + (v * (ceilingSize - floorSize));

  for (i=0; i < scalePhotos.length; i++) {
    scalePhotos[i].style.width = (v*190)+'px';
  }
} 

var demoSlider = new Control.Slider('handle1','track1',
{axis:'horizontal', alignX: 2, sliderValue:.5, increment: 50, maximum: 1000});

demoSlider.options.onSlide = function(value){
  scaleIt(value);
}

demoSlider.options.onChange = function(value){
  scaleIt(value);
} 

demoSlider.setValue('.5');
demoSlider.setValue('.1');
</script> 
<br clear="all"/>
<div class="scale-image" style="width:400px">
<graphing:HCPlot taskId="<%=key%>" />
</div>
<script language="javascript">
var rbt_image = document.getElementById("rbt_image");


function rbt_image_init()	{
	rbt_image.style.width = "100%";
}

function shrink()	{
	//rbt_image.setAttribute("height", "200");
	//rbt_image.setAttribute("width", "500");
	rbt_image_init();
}
function fullsize()	{
	rbt_image.removeAttribute("width");
	rbt_image.removeAttribute("height");
	rbt_image.style.width = "";
}

//init
rbt_image_init();

</script>


<div style="height:300px; overflow:auto;">
<graphing:HCPlotReport taskId="<%=key%>" />
</div>
</body>
</html>

