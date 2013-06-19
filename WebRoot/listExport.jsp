<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %><%@ page buffer="none" %><%@ page import="gov.nih.nci.ispy.web.ajax.*"%><%
String key = request.getParameter("list")!=null ? (String) request.getParameter("list") : null;
if(key!=null)	{
	long randomness = System.currentTimeMillis();
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename=list_"+randomness+".txt");
	try	{
		out.write(DynamicListHelper.exportListasTxt(key, session));
	}
	catch(Exception e)	{
		out.write("error writing list");
	}
}
%>