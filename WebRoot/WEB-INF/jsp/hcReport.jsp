<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%
String key = "taskId";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");
%>
<html>
	<head>
		<title>ISPY Report</title>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script>
		<script language="JavaScript" type="text/javascript" src="XSL/a_js.js"></script>
		<script language="javascript" src="js/Help.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/prototype_1.5pre.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/scriptaculous/scriptaculous.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/event-selectors.js"></script>

		<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
		<script type='text/javascript' src='dwr/engine.js'></script>

	 	<LINK href="xsl/css.css" rel="stylesheet" type="text/css" />
	 	<script language="javascript">
	 	
	 	var reportType = "";
	 	
	 		
	 		
	 	var tmpElements = Array();
	 	
	 	//http://johankanngard.net/2005/11/14/remove-an-element-in-a-javascript-array/
		Array.prototype.remove = function(s){
			for(i=0;i<this.length;i++){
				if(s==this[i]) this.splice(i, 1);
			}
		}
	
	 		var Rules = {
	 		//'.gene, .patient': function(element) {
			    //element.setStyle({backgroundColor: '#ccc'});
			//    if(element.innerHTML != "")	{
			//	    element.innerHTML += "<input name='checkable' class='saveElement' type='checkbox' value='"+element.id+"'/>";
			//	}
			//  },
			 
			  '.saveElement:click' : function(element)	{
					if(element.selected || element.checked)
						tmpElements.push(element.value);
					else
						tmpElements.remove(element.value);
			  }
			 
	 		
	 		};
	 		
	 		var CheckMgr = {
	 		
		 		'checkAll' : function(field)	{
					if(field.length > 1)	{
						for (i = 0; i < field.length; i++)	{
							field[i].checked = true ;
							tmpElements.push(field[i].value);
						}
					}
					else	{
						field.checked = true;
						tmpElements.push(field.value);
					}
				},
				'uncheckAll' : function(field)	{
					if(field)	{
						if(field.length > 1)	{
							for (i = 0; i < field.length; i++)	{
								field[i].checked = false ;	
							}
						}
						else	{
							field.checked = false;
						}
							
						tmpElements = new Array();
						
						if($('checkAll'))		
							$("checkAll").checked = false;
					}
							
				},
				'manageCheckAll' : function(box)	{
						if(box.checked)	{
							CheckMgr.checkAll(document.getElementsByName('checkable'));
						}
						else	{
							CheckMgr.uncheckAll(document.getElementsByName('checkable'));
						}
				}
			};
			
			var SaveElements = {
			
				'save' : function()	{
					//get the name
					$('statusImg').style.display = "";
					$('statusSpan').innerHTML = "Saving...";
					
					var name = $("listName").value;
					if(name != "")	{
						//convert the overlib list to a comma seperated list
						if(tmpElements.length > 0)	{
							
							//var commaSepList = tmpElements.join(",");
							
							if(reportType == "GENE")
								DynamicListHelper.createGeneList(tmpElements, name, SaveElements.save_cb);
							else
								DynamicListHelper.createPatientList(tmpElements, name, SaveElements.save_cb);
								
						}
						else	{
							alert("Please select some items to save");
						}
					}
					else	{
						alert("Please enter a name for your group");
					}
				},
				'save_cb' : function(txt)	{
					var results = txt == "pass" ? "List Saved" : "There was a problem saving your list";
					//alert(results); //pass | fail
					
					setTimeout(function()	{ $('statusImg').style.display = "none"; }, 1000);
					
					
					if(txt != "fail")	{
						setTimeout(function()	{$('statusSpan').innerHTML = "<b>List saved.</b>";},1000);
						//erase the name
						$("listName").value = "";
						//clear the sample list
						tmpElements = new Array();
						
					}
					else	{
						$('statusSpan').innerHTML = "<b>List did not save.  Please try again.</b>";
					}
					
					setTimeout(function()	{ $('statusSpan').innerHTML = ""; }, 2000);
					
					//uncheck them all
					CheckMgr.uncheckAll(document.getElementsByName('checkable'));
					
					
					try	{
						if(!window.opener.closed)	{
							if(reportType == "GENE")
								window.opener.SidebarHelper.loadSidebar();
							else
								window.opener.SidebarHelper.loadSidebar();
						}
						
					}
					catch(err)	{
						alert("cant update sidebar: " + err);
					}
					
				}
			};
			
	 		window.onload = function()	{
		 		if(document.getElementsByName("gene").length > 0)
			 		reportType = "GENE";
			 	else
			 		reportType = "PATIENT";
			 	
	 			EventSelectors.start(Rules);
	 			
	 		}
	 	
	 	</script>
	</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img align="right" src="images/close.png" border="0"></a>
	<script type="text/javascript">Help.insertHelp("HCA_report", " align='right'", "padding:2px;");</script>
	<a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/> </a> 	
</span>

<div style="background-color: #ffffff"><img src="images/ispyPortalHeader.gif" /></div>



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
  ceilingSize = 30.0;
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


<div>
Save Selected: <input type="text" id="listName"/>
<input type="button" value="save checked" onclick="SaveElements.save();"/>
<input type="checkbox" id="checkAll" onclick="CheckMgr.manageCheckAll(this)"/>All?
<!--  <a href="#" onclick="return false;" onmouseover="return overlib(tmpElements);" onmouseout="return nd()">[selected]</a> -->
<img src="images/indicator.gif" style="display:none" id="statusImg"/><span id="statusSpan" style="margin-left:15px;"></span>
</div>
<div>
<graphing:HCPlotReport taskId="<%=key%>" />
</div>



</body>
</html>

