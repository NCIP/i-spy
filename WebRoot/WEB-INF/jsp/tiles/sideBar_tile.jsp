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
		if(txt != "")	{
			$('sidebarPatientUL').innerHTML = "<ul>" + txt + "</ul>";
			SidebarHelper.createOnClicks('sidebarPatientUL');
		}
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
				var url = "listExport.jsp?list="+this.innerHTML;
				location.href=url;
				//this = the li we want
				//alert(this.innerHTML); 
			};
			lis[i].onmouseover = function() { 
				tmpp[this.id] = this.title;
				this.title = "";
				return overlib(tmpp[this.id].split(",").join("<br/>"), CAPTION, this.id + " Elements:");
			};
			lis[i].onmouseout = function() { 
				this.title = tmpp[this.id];
				return nd();
			};
			
			lis[i].style.cursor = "pointer";
		}
	
	}

};

//load the initial values
SidebarHelper.loadPatientUL();
SidebarHelper.loadGeneUL();

</script>
     