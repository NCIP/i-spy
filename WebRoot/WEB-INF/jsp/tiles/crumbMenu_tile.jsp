<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div class="crumb">
<span style="float:left; margin-top:0; padding-top:0;">
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/I-Spy_1.5_Users_Guide.pdf">user guide</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/IntegratedClinicData_Feb_2010.xls">integrated data file</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/DataDictionaryIntegratedDataJan2010.doc">clinical, pathology data dictionary</a>
</span>
<br>
<span style="float:left; padding-top:0.1em;">
<a style="font-size:.8em" href="docs/ACRIN6657_Mammo_08-01-15.xls">mammo data</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/I-SPY.BAC.CGH.Baseline.N59.xls">array CGH (baseline)</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/ISPY_BAC_CBS_N59.xls">array CGH (CBS)</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:Help.popHelp('Cite_data');">cite data</a>&nbsp;&nbsp;&nbsp;
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
