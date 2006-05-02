<a href="manageLists.do">manage lists</a>

<div style="text-align:left; margin-top:10px;">
	Patient Lists:
	<div id="sidebarPatientUL">
		<img src="images/indicator.gif"/>
    </div>
</div>
     
<div style="text-align:left; margin-top:20px;">
	Gene Lists:
	<div id="sidebarGeneUL">
		<img src="images/indicator.gif"/>
	</div>	
</div>

<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

<script language="javascript">


//segregated so we can call them individually w/ease
var SidebarHelper = {
	'delay' : 500,
	'loadingImg' : "<img src=\"images/indicator.gif\"/>",
	'loadPatientUL' : function()	{
		$('sidebarPatientUL').innerHTML = this.loadingImg;
		setTimeout( function()	{
			DynamicListHelper.getPatientListAsList(SidebarHelper.loadPatientUL_cb);
			},this.delay);
	},
	'loadPatientUL_cb' : function(txt)	{
		if(txt != "")
			$('sidebarPatientUL').innerHTML = "<ul>" + txt + "</ul>";
		else	
			$('sidebarPatientUL').innerHTML = "No Lists Available";
	},
	'loadGeneUL' : function()	{
		$('sidebarGeneUL').innerHTML = this.loadingImg;
		setTimeout( function()	{
			DynamicListHelper.getGeneListAsList(SidebarHelper.loadGeneUL_cb);
		},this.delay);
	},
	'loadGeneUL_cb' : function(txt)	{
		if(txt != "")
			$('sidebarGeneUL').innerHTML = "<ul>" + txt + "</ul>";
		else	
			$('sidebarGeneUL').innerHTML = "No Lists Available";
	}

};

//load the initial values
SidebarHelper.loadPatientUL();
SidebarHelper.loadGeneUL();

</script>
     