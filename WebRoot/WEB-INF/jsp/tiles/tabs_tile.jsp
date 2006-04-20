<script language="javascript">

	function setStatus(txt)	{
		document.getElementById("statusMsg").innerHTML = txt;
		setTimeout("clearStatus()", 2000);
	}
	function clearStatus()	{
		document.getElementById("statusMsg").innerHTML = "<br/>";
	}
</script>
<script type='text/javascript' src='dwr/interface/Inbox.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>


<span id="statusMsg"><br/></span>
<% 
	//default settings for tabs
	String simple = "";
	String adv = "";
	String viewResults = "";
	String analysis = "";
	String secondary = "";
	String advSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"menu.do\">Advanced Search Home</a></li>\n" +
							"<li><a href=\"#\">Gene Expression</a></li>\n" +
							"<li><a href=\"#\">CGH</a></li>\n" +
							"<li><a href=\"#\">Clinical</a></li>\n" +
							"<li><a href=\"#\">IHC</a></li>\n" +
							"<li><a href=\"#\">p53</a></li>\n" +
							"<li><a href=\"#\">FISH</a></li>\n" +
							"</ul>\n";
	String resultsSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"viewResults.do\">View Findings</a></li>\n" +
							"<li><a href=\"#\">Managed Saved Lists</a></li>\n" +
							"</ul>\n";				
	String simpleSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"simpleSearch.do\">Simple Search Home</a></li>\n" +
							"</ul>\n";
	String analysisSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"analysisHome.do\">Analysis Home</a></li>\n" +
							"</ul>\n";
							
	String s = (String) request.getParameter("s");
	if(s != null)	{
		int sect = Integer.parseInt(s);	
		switch(sect)	{
			case 1:
				//1 is simple search
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"menu.do\" onclick=\"return false\">Advanced Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
			case 2:
				//2 is adv
				simple = "<a href=\"simpleSearch.do\">Simple Search</a>";
				adv = "<span>Advanced Search</span>\n" + advSecondary;
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
			case 3:
			    //3 is high order analysis
				simple = "<a href=\"simpleSearch.do\">Simple Search</a>";
				adv = "<a href=\"#\">Advanced Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<span>High Order Analysis</span>\n" + analysisSecondary;
				break;
			case 4:
			    //4 is view results
				simple = "<a href=\"simpleSearch.do\">Simple Search</a>";
				adv = "<a href=\"#\">Advanced Search</a>";
				viewResults = "<span>View Results&nbsp;&nbsp;</span>\n";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
			default:
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"#\">Advanced Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
		
		}
	}
%>
<div id="header">
	<ul id="primary">
		<li><%= simple %></li>
		<li><%= adv %></li>
		<li><%= analysis %></li>
		<li><%= viewResults %></li>
	</ul>
</div>
