<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %>


<% 
	String bottomPage = "rbtFramesBottom.jsp";
	if(request.getParameter("p") != null)
	       bottomPage = request.getParameter("p");
%>

<HTML>
<HEAD>
<TITLE>Rembrandt Report Annotation</TITLE>
</HEAD>

  <FRAMESET rows="70, *" border="0">
      <FRAME src="rbtFramesTop.jsp" scrolling="no">
      <FRAME src="<%=bottomPage%>">
  </FRAMESET>

 
</HTML>