<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

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
	String list = "";
	
	String advSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"advancedHome.do\">Search Home</a></li>\n" +
							"<li><a href=\"clinicalQueryInit.do?method=setup\">Clinical</a></li>\n" +
							"<li><a href=\"ihcLevelQueryInit.do?method=setup\">IHC Level</a></li>\n" +
							"<li><a href=\"ihcLossQueryInit.do?method=setup\">IHC Loss</a></li>\n" +
							"<li><a href=\"p53QueryInit.do?method=setup\">P53</a></li>\n" +										
							//"<li><a href=\"fishQueryInit.do?method=setup\">FISH</a></li>\n" +
							//"<li><a href=\"#\">p53</a></li>\n" +
							"</ul>\n";
	String resultsSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"viewResults.do\">View Findings</a></li>\n" +
							"<li><a href=\"#\">Managed Saved Lists</a></li>\n" +
							"</ul>\n";				
	String simpleSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"simpleSearch.do\">ID Lookup Home</a></li>\n" +
							"</ul>\n";
	String analysisSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"analysisHome.do\">Analysis Home</a></li>\n" +
							"<li><a href=\"classcomparisonInit.do?method=setup\">Class Comparison</a></li>\n" +
							"<li><a href=\"principalcomponentInit.do?method=setup\">PCA</a></li>\n" +
							"<li><a href=\"hierarchicalclusteringInit.do?method=setup\">HC</a></li>\n" +										
							"<li><a href=\"correlationScatterInit.do?method=setup\">Correlation Scatter</a></li>\n" +
							"<li><a href=\"categoricalCorrelationInit.do?method=setup\">Categorical Plot</a></li>\n" +
							"<li><a href=\"gpintegrationInit.do?method=setup\">GP Analysis</a></li>\n" +
							"</ul>\n";
							
   String resultSecondary = "<ul id=\"secondary\">\n" +
							//"<li><a href=\"graph.do?method=setup\">Search</a></li>\n" +
							"<li><a href=\"viewResults.do\">Report Results</a></li>\n" +
							"<li><a href=\"gpProcess.do?method=setup\">GenePattern Job Results</a></li>\n" +							
							"</ul>\n";							
	String s = (String) request.getParameter("s");
	if(s != null)	{
		int sect = Integer.parseInt(s);	
		switch(sect)	{
			case 1:
				//1 is simple search
				simple = "<span>ID Lookup</span>\n" + simpleSecondary;
				adv = "<a href=\"advancedHome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 2:
				//2 is adv
				simple = "<a href=\"simpleSearch.do\">ID Lookup</a>";
				adv = "<span>Search</span>\n" + advSecondary;
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 3:
			    //3 is high order analysis
				simple = "<a href=\"simpleSearch.do\">ID Lookup</a>";
				adv = "<a href=\"advancedHome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<span>High Order Analysis</span>\n" + analysisSecondary;
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 4:
			    //4 is view results
				simple = "<a href=\"simpleSearch.do\">ID Lookup</a>";
				adv = "<a href=\"advancedHome.do\"> Search</a>";
				//viewResults = "<span>View Results&nbsp;&nbsp;</span>\n";
				viewResults = "<span id=\"inboxStatus\">View Results&nbsp;&nbsp;</span>\n" + resultSecondary;
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 5:
			    //4 is view results
				simple = "<a href=\"simpleSearch.do\">ID Lookup</a>";
				adv = "<a href=\"advancedHome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<span>Manage Lists</span>\n";
				break;
			case 6:
			    //6 is to vew gene pattern job view
				simple = "<a href=\"simpleSearch.do\">ID Lookup</a>";
				adv = "<a href=\"advancedHome.do\">Search</a>";
				//viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				//viewResults = "<span>View Results&nbsp;&nbsp;</span>\n";
				viewResults = "<span id=\"inboxStatus\">View Results&nbsp;&nbsp;</span>\n" + resultSecondary;
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			default:
				//simple = "<span>Simple Search</span>\n" + simpleSecondary;
				simple = "<a href=\"simpleSearch.do\">ID Lookup</a>";
				adv = "<a href=\"advancedHome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
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
		<li><%= list %></li>			
	</ul>
</div>
