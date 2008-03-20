<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>I-SPY Register</title>
	
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	
	<link href="css/bigStyle.css" rel="stylesheet" type="text/css">
		<link href="js/user/register.css" rel="stylesheet" type="text/css">
	<link href="js/user/style.css" rel="stylesheet" type="text/css"> 
	
	<script type="text/javascript" language="javascript" src="js/user/jquery-1.2.1.pack.js"></script>
	<script type="text/javascript" language="javascript" src="js/user/array.extend.js"></script>
	<script type="text/javascript" language="javascript" src="js/user/validate.js"></script>	
	<script type="text/javascript" language="javascript" src="js/user/tablesorter.js"></script>
	<script type="text/javascript" language="javascript">
	 
	
	// validation
	
		_validate['firstName']	= 'empty';
		_validate['lastName']	= 'empty';
		_validate['email']		= 'email';
		
		_validate['institution']	 = 'empty';
		_validate['address1']		 = 'empty';
		_validate['city']			 = 'empty';
		_validate['state']			 = 'empty';
		_validate['country']		 = 'empty';
		_validate['password']		 = 'match:confirmPassword';
		_validate['confirmPassword'] = 'empty';
	
	
	// extend jquery for checkboxes
	
		jQuery.fn.extend({
			check: function() {
			 return this.each(function() { this.checked = true; });
		   },
		   uncheck: function() {
			 return this.each(function() { this.checked = false; });
		   }
		 });
		 
 
 	// on document ready
 
		$(document).ready( function(){
	
			// sort the table
	
			$('#unique_id').tablesorter({
				headers: {
					0: { sorter: false }
				}
			});
			
		});
	
	//-->
	</script>	 
	<style type="text/css" media="screen">@import "css/tabs.css";</style>
	
	<style>
		#sidebar div b {
			border-bottom: 1px solid #A90101;
		}
	</style>

</head>

<body> 

<div id="overDiv" style="position: absolute; visibility: hidden; z-index: 1000;"></div>
    
    <!--header NCI logo-->
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
		<area alt="HICCC Analysis Portal Logo" coords="5,3,166,61" href="/ispyportal">
	</map>

	<img src="images/hiccc_header_04.png" width="765" height="67" border="0" usemap="#hicccMap">
</div>
<br></br>
<!--end all headers-->

	<div class="content register">
	
		<h3>Registration Form</h3>
				
		<p>Please fill out the fields below. Fields marked with a <font color="red">*</font> are required. As soon as your account is created you will receive an e-mail with instructions how to access the system.</p>
		
		<p>At present accounts are issued only to academic users. You must provide a .edu e-mail address for your registration request to be processed.</p>
		
		<br/>
		
		
		<html:form action="register.do" onsubmit="return validate_form( this )" method="POST">
					
			<html:hidden property="action" value="submitRegistration"/>
			<!--<html:hidden property="oldEmail" value="null"/>-->
				   
			<table border="0" width="647" height="555">
				
				<tr>
					<th><b>First Name:</b></th>
					<td><html:text property="firstName" size="40" value=""/><font color="red">*</font></td>
				</tr>
				<tr>
					<th><b>Last Name:</b></th>
					<td><html:text property="lastName" size="40" value=""/><font color="red">*</font></td>
				</tr>
				<tr>
					<th><b>Email Address:</b></th>
					<td><html:text property="email" size="40" value=""/><font color="red">*</font></td>
				</tr>
				<tr>
					<th><b>Institution:</b></th>
					<td><html:text property="institution" size="40" value=""/><font color="red">*</font></td>
				</tr>
				<tr>
					<th><b>Department:</b></th>
					<td><html:text property="department" size="40" value=""/></td>
				</tr>
				<tr>
					<th><b>Address 1:</b></th>
					<td><html:text property="address1" size="40" value=""/><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th><b>Address 2:</b></th>
					<td><html:text property="address2" size="40" value=""/></td>
				</tr>
				<tr>
					<th><b>City:</b></th>
					<td><html:text property="city" size="40" value=""/><font color="red">*</font></td>
				</tr>
		
				<tr>
					<th><b>State/Province:</b></th>
					<td><html:text property="state" size="40" value=""/><font color="red">*</font></td>
				</tr>
				<tr>
					<th><b>Country:</b></th>
					<td><html:text property="country"  size="40" value=""/><font color="red">*</font></td>
				</tr>
				<tr>
					<th><b>Phone:</b></th>
					<td><html:text property="phone" size="40" value=""/></td>
				</tr>
				<tr>
					<th><b>Intended Use of Data:</b></th>
					<td><html:textarea property="intendedUse"  cols="37" rows="5"></html:textarea></td>
				</tr>
				<tr>
					<th><b>Password:</b></th>
					<td><html:password property="password" size="30"/><font color="red">*</font></td>
		
				</tr>
				<tr>
					<th><b>Confirm Password:</b></th>
					<td><html:password property="confirmPassword"  size="30"/><font color="red">*</font></td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td class="submit"><br/><html:submit value="Register" /></td>
				</tr>
			</table>
		</html:form>

	
	</div>
	
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
	
</div>

</body>
</html>