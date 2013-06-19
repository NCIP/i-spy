<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<%@ page import="java.io.FileInputStream" %>
<% 	/*
		This page receives the section identifier in the query string (s)
		Read properties file based on this ID, then assembles the
		tiles (only as includes) to build the form
 		[reads from props file and generate content with a loop ]
		props file example: dataType.properties
		1=dataElement
		2=dateElementTwo
		corresponding JSP tiles are called: dataElement_tile.jsp
	*/
%>
<%
//below, we need to set the form action to the name cooresponding bean/action -
//get if from the request, and make lower to match the struts-config
/* the props will be determined by the s parameter set to
 * lowercase.
 */
String action = "";
String param = request.getParameter("s");
action = param.toLowerCase();
System.out.println("action:"+action);
%>
<br/>

<div class="setQuery">
<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
</div>

<html:form action="<%=action%>" enctype="multipart/form-data">
  <%   
  	Properties props = new Properties();  	
  	FileInputStream fsi = null;  	
  	try {
      	fsi = new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+param+".properties");
      	props.load(fsi);
      	fsi.close();
      }
  	  catch (IOException e) {
      	out.println("cant read props");
      	if(fsi != null)
	      	fsi.close();
  	  }
  	  finally	{
  	  	if(fsi != null)
	      	fsi.close();
	 }

  	    	  
  	String strIncFile = "";
  	%>
    <script type="text/javascript">Help.convertHelp("<%=action%>", " align='right'", "float:right;padding:2px;");</script>
  	<%
  	for (int t=1; t<props.size()+1; t++)	{
  		strIncFile = "/WEB-INF/jsp/tiles/"+props.getProperty(String.valueOf(t))+"_tile.jsp";
 	%>
  	<div class="report" style="padding:3px">
      <tiles:insert page="<%= strIncFile %>" flush="false" />
	</div>
  <%
  }
  %>
</html:form>
