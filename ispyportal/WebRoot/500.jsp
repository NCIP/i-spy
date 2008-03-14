
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>ISPY Post Analysis Portal-Login</title>
		<tiles:insert attribute="htmlHead" />
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">

		<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="js/caIntScript.js"></script>
		<script language="javascript" src="js/box/browserSniff.js"></script>
		<script language="javascript" src="js/prototype.js"></script>
		<script language="javascript" src="js/scriptaculous/scriptaculous.js"></script>

		<script language="javascript" src="js/overlib.js"></script>
		<script language="javascript" src="js/overlib_hideform.js"></script>

		<style type="text/css" media="screen">@import "css/tabs.css";</style>

	</head>

	<body>


<!--header NCI logo-->
<table width="765" align="center" border="0" cellspacing="0" cellpadding="0" bgcolor="#A90101">
	<tr>
		<td width="283" height="37" align="left" bgcolor="#A90101">
			<a href="http://www.cancer.gov" target="_blank"><img src="images/hiccc_header_01.png" width="283" height="37" border="0"></a>
		</td>
		<td width="283" height="37" align="middle" bgcolor="#5A799C">

			<a href="http://hiccc.columbia.edu" target="_blank"><img src="images/hiccc_header_02.png" width="187" height="37" border="0"></a>
		</td>
		<td width="295" height="37" align="right" bgcolor="#5A799C">
			<a href="http://hiccc.columbia.edu" target="_blank"><img src="images/hiccc_header_03.png" width="295" height="37" border="0"></a>
		</td>
	</tr>
</table>

<div align="center" style="padding:0px;">

<!--header ispy image map-->

<div style="width:765px; margin:0px;">
	<map name="hicccMap">
		<area alt="HICCC Analysis Portal Logo" coords="5,3,166,61" href="analysisHome.do">
	</map>
	
	<img src="images/hiccc_header_04.png" width="765" height="67" border="0" usemap="#hicccMap">
</div>
<br></br>
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
  											<strong>Our apologies, there has been an internal server error.</strong>
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
