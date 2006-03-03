<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
if(session.getAttribute("logged") == "yes")
{
//youre already logged in, why are you here?
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "index.jsp";
response.setHeader("Location",newLocn);}
%>

	      <div style="position:relative;padding:5px;float:left;left:0px;top:10px;width:46%">      
		       <p class="mainText"><span style="font-size:16px">P</span>rogress in finding better therapies for breast cancer treatment
				  is hampered by the lack of opportunity to integrate and rapidly
				  test novel therapeutics in the clinical setting.  In order to
				  catalyze the transition from uniform to tailored care, clinical
				  trials to identify markers and mechanisms of resistance to
				  therapy are critical. A collaboration of physicians, researcher
				  and cooperative groups are conducting one such study, the I-SPY
				  Trial, a multi-center clinical trial of women undergoing neoadjuvant
				  chemotherapy from breast cancer.  In order to assist in the conduct
				  of the trial and the analysis of results, a great deal of attention
				  has been paid to facilitating collaboration, providing infrastructure
				  for data management, analysis, and communication, and developing
				  a commitment to sharing information and developing data standards.</p>
				  <p class="mainText"> The NCI Center for Bioinformatics (NCICB) is designing a web-based
				  system to support correlative data analysis and centralized reporting
				  of results.  </p> 
		</div>
	 
		 <div style="position:relative;float:right;left:10px;top:10px;width:50%">
			    <div style="position:relative;left:65px">
			    	<img src="images/ribbon.gif" alt="cancer robbon with dna strand overlay"/>
			    
			    <!--login form/table begins-->			     
			       <html:form action="login.do"> 
			       <div style="width:200px"><html:errors property="invalidLogin" /></div>
				   <table border="0">
			            <tr><Td>username:</td><td><html:text property="userName" /></td></tr>
			            <tr><Td>password:</td><td><html:password property="password" /></td></tr>
			       		<tr><td colspan="2" align="right"><html:submit/>&nbsp;&nbsp;<html:reset/></td></tr>
			       </table>
			          
			      
			       </html:form>
			       <!--end login form-->
			     </div>
		 </div>
