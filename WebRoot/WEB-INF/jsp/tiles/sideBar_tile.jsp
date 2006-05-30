<div id="manageListLinkDiv" style="text-align:center; margin-top:20px;">
<fieldset style="text-align:center">
<a href="manageLists.do">Manage Lists</a>
</fieldset>
</div>

<style>
#sidebar div b {
	border-bottom: 1px solid #A90101;
}
</style>
<div id="sidebar">
<div style="text-align:left; margin-top:10px;">
	<b>Patient Lists:</b><br/>
	<br/><i>-Custom Lists</i>
	<div id="sidebarPatientUL">
		<img src="images/indicator.gif"/>
    </div>
    <br/><i>-Pre-defined Lists</i>
    <div id="sidebarDPatientUL">
		<img src="images/indicator.gif"/>
    </div>       
</div>
     
<div style="text-align:left; margin-top:20px;">
	<b>Gene Lists:</b>
	<div id="sidebarGeneUL">
		<img src="images/indicator.gif"/>
	</div>	
</div>
</div>


<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

<script language="javascript">


//segregated so we can call them individually w/ease
var SidebarHelper = {
	'delay' : 250,
	'loadingImg' : "<img src=\"images/indicator.gif\"/>",
	'loadSidebar' : function()	{
		SidebarHelper.loadPatientUL();
		SidebarHelper.loadDefaultPatientUL();
		SidebarHelper.loadGeneUL();
	},
	'loadPatientUL' : function()	{
		$('sidebarPatientUL').innerHTML = this.loadingImg;
		setTimeout( function()	{
			DynamicListHelper.getPatientListAsList(SidebarHelper.loadPatientUL_cb);
			},this.delay);
	},
	'loadDefaultPatientUL' : function()	{
		$('sidebarDPatientUL').innerHTML = this.loadingImg;
		setTimeout( function()	{
			DynamicListHelper.getDefaultPatientListAsList(SidebarHelper.loadDefaultPatientUL_cb);
			},this.delay);
	},
	'loadPatientUL_cb' : function(txt)	{
		if(txt != "")	{
			$('sidebarPatientUL').innerHTML = "<ul>" + txt + "</ul>";
			SidebarHelper.createOnClicks('sidebarPatientUL');
		}
		else	
			$('sidebarPatientUL').innerHTML = "<ul><li>No Lists Available</li></ul>";
	},
	'loadDefaultPatientUL_cb' : function(txt)	{
		if(txt != "")	{
			$('sidebarDPatientUL').innerHTML = "<ul>" + txt + "</ul>";
			SidebarHelper.createOnClicks('sidebarDPatientUL');
		}
		else	
			$('sidebarDPatientUL').innerHTML = "No Lists Available";
	},
	'loadGeneUL' : function()	{
		$('sidebarGeneUL').innerHTML = this.loadingImg;
		setTimeout( function()	{
			DynamicListHelper.getGeneListAsList(SidebarHelper.loadGeneUL_cb);
		},this.delay);
	},
	'loadGeneUL_cb' : function(txt)	{
		if(txt != "")	{
			$('sidebarGeneUL').innerHTML = "<ul>" + txt + "</ul>";
			SidebarHelper.createOnClicks('sidebarGeneUL');
		}
		else	
			$('sidebarGeneUL').innerHTML = "No Lists Available";
	},
	'createOnClicks' : function(theId)	{
		var lis = $(theId).getElementsByTagName("li");

		var tmpp = new Array();

		for(var i=0; i<lis.length; i++)	{
			lis[i].ondblclick = function() { 
				var url = "listExport.jsp?list="+this.id;
				location.href=url;
				//this = the li we want
				//alert(this.innerHTML); 
			};
			lis[i].onmouseover = function() { 
				tmpp[this.id] = this.title;
				this.title = "";
				return overlib(tmpp[this.id].split(",").join("<br/>"), CAPTION, this.id.substring(0,10) + " Elements:");
			};
			lis[i].onmouseout = function() { 
				this.title = tmpp[this.id];
				return nd();
			};
			
			lis[i].id = lis[i].innerHTML;
			
			lis[i].style.cursor = "pointer";
			//trim the HTML length
			if(lis[i].innerHTML.length > 15)
				lis[i].innerHTML = lis[i].innerHTML.substring(0,14) + "...";
		}
	
	}

};

//load the initial values
SidebarHelper.loadPatientUL();
SidebarHelper.loadDefaultPatientUL();
SidebarHelper.loadGeneUL();


</script>
     