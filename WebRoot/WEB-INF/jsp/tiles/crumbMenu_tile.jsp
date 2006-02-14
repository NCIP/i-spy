<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div class="crumb">
  <span style="text-align:right;font-size:.85em;">
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logoutPage.do">
      Logout
    </a>
  </span>
</div>
