<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div class="crumb">
<span style="float:left">
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/I-SPY_Users_Guide_1 0_12 6 06_ljj.pdf">user guide</a>&nbsp;&nbsp;&nbsp;
</span>

<% if(session.getAttribute("logged") == "yes"){ %>
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
