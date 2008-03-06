<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div class="crumb">
<span style="float:left">
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/I-SPY_User's_Guide_1.10.08.pdf">user guide</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/Integrated_Clin_Path_MR_Labtrak_fish_12_03_2007.xls">integrated data file</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/ACRIN6657_Mammo_08-01-15.xls">mammo data</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/CondensedDataDictionary907.doc">Clinical Data Dictionary</a>
</span>
<br>
<span style="float:left">
<a style="font-size:.8em" href="docs/Path Variables 10_18_7.doc">Pathology Data Descriptions</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/I-SPY.BAC.CGH.Baseline.N59.xls">Array CGH (baseline)</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/ISPY_BAC_CBS_N59.xls">Array CGH (CBS)</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:Help.popHelp('Cite_data');">Cite Data</a>&nbsp;&nbsp;&nbsp;

</span>





<% if(session.getAttribute("logged").equals("yes")){ %>
  <span style="text-align:right;font-size:.85em;">
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logout.do">
      Logout
    </a>
  </span>
<%}%>	
</div>
