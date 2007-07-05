<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div class="crumb">
<span style="float:left">
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/I-SPY_Users_Guide_1.0_6.21.07.pdf">user guide</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/Integrated_Clin_MR_Labtrak_05JULY07.xls">integrated data file</a>
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
