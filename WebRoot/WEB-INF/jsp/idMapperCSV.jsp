<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %><%@ page buffer="none" %><%@ page import="gov.nih.nci.ispy.web.ajax.*"%><%

String lookups = request.getParameter("i")!=null ? request.getParameter("i") : null;
long randomness = System.currentTimeMillis();
if(lookups!=null)	{
	String csv = IdLookup.getCSV(lookups);

		response.setContentType("application/csv");
		response.setHeader("Content-Disposition", "attachment; filename=idLookup"+randomness+".csv");
		out.write(csv);	
}
else	{
	out.write("no ids passed?");
}

%>