<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>


       
<%
if(session.getAttribute("logged")!=null &&  session.getAttribute("logged").equals("yes"))
{
//youre already logged in, why are you here?
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "simpleSearch.do";
response.setHeader("Location",newLocn);
}
String update = System.getProperty("gov.nih.nci.ispyportal.data_updates");

String[] updates = update.split(",");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>ISPY Post Analysis Portal-Login</title>
		
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
		
		<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="js/caIntScript.js"></script>
		<script language="javascript" src="js/box/browserSniff.js"></script>
		<script language="javascript" src="js/prototype.js"></script>
		<script language="javascript" src="js/Help.js"></script>
		<script language="javascript" src="js/scriptaculous/scriptaculous.js"></script>

		<script language="javascript" src="js/overlib.js"></script>
		<script language="javascript" src="js/overlib_hideform.js"></script>

		
		<style type="text/css" media="screen">@import "css/tabs.css";</style>

	</head>

	<body>

 
		<!--header NCI logo-->
		<table width="765" align="center" border="0" cellspacing="0" cellpadding="0" bgcolor="#A90101">
			<tr bgcolor="#A90101">
				<td width="283" height="37" align="left">
					<a href="http://www.cancer.gov"><img src="images/logotype.gif" width="283" height="37" border="0"></a>
				</td>
				<td>
					&nbsp;
				</td>
				<td width="295" height="37" align="right">
					<a href="http://www.cancer.gov"><img src="images/tagline.gif" width="295" height="37" border="0"></a>
				</td>
			</tr>
		</table>

		<div align="center" style="padding:0px;">


			<!--header ispy image map-->
			<div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
				<map name="ispyPortalMap">
					<area alt="I-SPY Application Logo" coords="5,3,236,61" href="#">
				</map>
				<img src="images/ispyPortalHeader.gif" width="765" height="65" border="0" usemap="#ispyPortalMap">
			</div>
			<!--end all headers-->

			<div class="content">

				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tr>
						<td width="765">
							<Table cellpadding="4" cellspacing="2" border="0" width="100%">
								<tr>
									<td>
										<div>
											<div style="position:relative;padding:5px;float:left;left:0px;top:10px;width:46%">
												<p class="mainText">
													<span style="font-size:16px">P</span>rogress in finding better therapies for breast cancer treatment is hampered by the lack of opportunity to integrate and rapidly test novel therapeutics in the clinical setting. In order to catalyze the
													transition from uniform to tailored care, clinical trials to identify markers and mechanisms of resistance to therapy are critical. A collaboration of physicians, researcher and cooperative groups are conducting one such study, the I-SPY
													Trial, a multi-center clinical trial of women undergoing neoadjuvant chemotherapy from breast cancer. In order to assist in the conduct of the trial and the analysis of results, a great deal of attention has been paid to facilitating
													collaboration, providing infrastructure for data management, analysis, and communication, and developing a commitment to sharing information and developing data standards.
												</p>
												<p class="mainText">
													The NCI Center for Bioinformatics (NCICB) is designing a web-based system to support correlative data analysis and centralized reporting of results.
												</p>
												
												<br /><span style="font-weight:bold;font-style:italic">version 1.5.2</span><br/><br/>
													<span style="text-decoration:underline">Latest data updates</span>
														<ul>
														<%
														for(int i=0;i<updates.length;i++){
															out.print("<li>" + updates[i] + "</li>");
														}
														%>															
														</ul>
											
														<span style="text-decoration:underline">Additional Information:</span>
															<ul> <li><a style="font-size:.9em;" href="docs/ReleaseNote_1.5.2.doc">Release Notes</a></li>
															<li><a style="font-size:.9em" href="javascript:Help.popHelp('Cite_data');">How to Cite I-spy Data</a></li></ul>
													
											</div>

											<div style="position:relative;float:right;left:10px;top:10px;width:50%">
												<div style="position:relative;left:65px">
													<img src="images/ribbon.gif" alt="cancer robbon with dna strand overlay" />

													<!--login form/table begins-->
													<html:form action="login.do">
														<div style="width:200px">
															<br /> <br /><br /><span style="font-size:16px;color:gray;text-align:left">login</span>
															<html:errors property="invalidLogin" />
														</div>
														<table border="0">
															<tr>
																<Td>
																	username:
																</td>
																<td>
																	<!--<html:text property="userName" />-->
																	<input type="text" name="userName"/>
																</td>
																<td rowspan="3">
																	  <script type="text/javascript">Help.insertHelp("Logging_in", " align='right'", "padding:2px;");</script>												
																</td>
															</tr>
															<tr>
																<Td>
																	password:
																</td>
																<td>
																	<!--<html:password property="password" />-->
																	<input type="password" name="password"/>
																</td>
															</tr>
															<tr>
																<td colspan="2" align="right">
																	<html:submit />
																	&nbsp;&nbsp;
																	<html:reset />
																</td>																	      
															</tr>
													
														</table>
													</html:form>
													<!--end login form-->
													<div style="width:80%;margin-left:-20px;">
												      <ul>
												       <li><a style="font-size:.9em;" href="mailto:ncicb@pop.nci.nih.gov?subject=ISPY: Request username/password">request username/password</a></li>
													  </ul>													
													</div>													 
												</div>
											</div>

										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<%-- include footer --%>
				<!-- Start Footer Table -->
				<div class="content" style="border-top:1px solid silver;border-bottom:1px solid silver;width:100%">
					<div style="padding:5px" align="left">
						<img src="images/caIntegratorLogoPower.png" alt="powered by caIntegrator" />
					</div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="top">
								<div align="center">
									<a href="http://www.cancer.gov/"><img src="images/footer_nci.gif" width="63" height="31" alt="National Cancer Institute" border="0"></a> <a href="http://www.dhhs.gov/"><img src="images/footer_hhs.gif" width="39" height="31"
											alt="Department of Health and Human Services" border="0"></a> <a href="http://www.nih.gov/"><img src="images/footer_nih.gif" width="46" height="31" alt="National Institutes of Health" border="0"></a> <a href="http://www.firstgov.gov/"><img
											src="images/footer_firstgov.gif" width="91" height="31" alt="FirstGov.gov" border="0"></a>
								</div>
							</td>
						</tr>
					</table>
					<!-- End Footer table -->
				</div>
			</div>
		</div>
	</body>
</html>
