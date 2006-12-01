
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>

<app:checkLogin name="logged" page="/index.jsp" />

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


<fieldset style="border: 1px solid #000066;width:765px">
<legend style="text-align:center;background-color:#ffffff">LEGAL RULES OF THE ROAD</legend>
<p style="text-align:left">The ISPY database is provided as a public service by the National
 Cancer Institute (NCI) to foster the rapid dissemination of information to the scientific
  community and the public. This data may
   already be published or otherwise in the public domain. In addition, confidential information
    may be posted which has not yet been published or is the subject of a patent application to be
     filed. All data may be subject to copyright provisions. 
       By submitting data to the Server, you are certifying that you are the author of this
        data, and are authorized to release the data to approved investigators. You also certify that you will post
         only data generated and/or produced by you or your laboratory, and that you will
          consult with your institutions technology development office before posting or 
          disclosing confidential information which may be patentable. You may browse, download,
           and use the data for non-commercial, scientific and educational purposes. However,
            you may encounter documents or portions of documents contributed by private
             institutions or organizations. Other parties may retain all rights to publish
              or produce these documents. Commercial use of the documents on this site may be
               protected under United States and foreign copyright laws. You are encouraged to review important 
               Use Guidelines by clicking on the link below.
</p>
		
		<p style="color:#002185;font-weight:bold;text-align:left">I HAVE READ AND UNDERSTOOD THE ABOVE PROVISIONS,
		AND SIGNIFY MY AGREEMENT BY <a href="analysisHome.do">CLICKING HERE</a> </p>

</fieldset>


<!--begin NCI footer-->
<div>
<table width="765" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px">
<tr>
<td valign="top"><div align="center">
	<a href="http://www.cancer.gov/"><img src="images/footer_nci.gif" width="63" height="31" alt="National Cancer Institute" border="0"></a>
	<a href="http://www.dhhs.gov/"><img src="images/footer_hhs.gif" width="39" height="31" alt="Department of Health and Human Services" border="0"></a>

	<a href="http://www.nih.gov/"><img src="images/footer_nih.gif" width="46" height="31" alt="National Institutes of Health" border="0"></a>
	<a href="http://www.firstgov.gov/"><img src="images/footer_firstgov.gif" width="91" height="31" alt="FirstGov.gov" border="0"></a></div>
</td>
</tr>
</table>
<!--end NCI footer-->
</div>

</body>
</html>
