<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>ISPY Post Analysis Portal</title>
		
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">

		<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="js/caIntScript.js"></script>
		<script language="javascript" src="js/box/browserSniff.js"></script>
		<script language="javascript" src="js/prototype.js"></script>
		<script language="javascript" src="js/scriptaculous/scriptaculous.js"></script>

		<script language="javascript" src="js/overlib.js"></script>
		<script language="javascript" src="js/overlib_hideform.js"></script>

		<style type="text/css" media="screen">@import "/css/tabs.css"</style>

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
											<fieldset style="border: 1px solid #000066;width:700px">
											<legend style="text-align:center;background-color:#ffffff">ISPY Message</legend>
												<p style="text-align:left">
  											<strong>Our apologies, this page cannot be found.</strong>
											</p><br /><br /><br /><a href="index.jsp">go back to home page</a><br/>
											</fieldset>
												<br /><br />
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
							</td>
						</tr>
					</table>
					<!-- End Footer table -->
				</div>
			</div>
	</body>
</html>
