<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app"%>
<%@ page import="gov.columbia.c2b2.ispy.user.WebUser"%>
<%@ page import="gov.columbia.c2b2.ispy.util.Constants"%>
 
<html><head> 
	
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	<link href="js/user/users.css" rel="stylesheet" type="text/css">
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
	
	    jQuery.noConflict();   
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
	
	 
	</script>
 
<%
    
    String firstName = "";
    String lastName = "";
    String email = "";
    String institution = "";
    String department = "";
    String address1 = "";
    String address2 = "";
    String city = "";
    String state = "";
    String country = "";
    String phone = "";
    String indendedUse = "";
    String oldEmail = "";
    WebUser u = (WebUser) request.getAttribute(Constants.ATTRIBUTE_USER);
    if (u != null) {
        firstName = u.getFirstName();
        lastName = u.getLastName();
        email = u.getEmail();
        institution = u.getInstitution();
        department = u.getDepartment();
        address1 = u.getAddress1();
        address2 = u.getAddress2();
        city = u.getCity();
        state = u.getState();
        country = u.getCountry();
        phone = u.getPhone();
        indendedUse = u.getIntendedUse();
    }
%>
						
								
								<h3>View Account</h3>
								<p>Please fill out the fields below. Fields marked with a <font color="red">*</font> are required. <br></p><br/>
								
								<html:form action="manageUser.do" method="POST">
								<table class="user_account">
									<html:hidden property="action" value="saveUser"/>
			 
			                        <html:hidden property="oldEmail" value="<%=email%>"/>
									
									<tbody>
										<tr>
											<th>Username</th>
											<td><input name="username" type="text" value="<%=email%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>First Name</th>
											<td><input name="firstName" type="text" value="<%=firstName%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>Last Name</th>
											<td><input name="lastName" type="text" value="<%=lastName%>" /><font color="red">*</font></td>
										</tr>
								
										<tr>
											<th>Email Address</th>
											<td><input name="email" type="text" value="<%=email%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>Insitution</th>
											<td><input name="institution" type="text" value="<%=institution%>"/><font color="red">*</font></td>
										</tr>
										<tr>
											<th>Department</th>
											<td><input name="department" type="text" value="<%=department%>" /></td>
										</tr>
										<tr>
											<th>Address 1</th>
											<td><input name="address1" type="text" value="<%=address1%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>Address 2</th>
											<td><input name="address2" type="text" value="<%=address2%>" /></td>
										</tr>
										<tr>
											<th>City</th>
											<td><input name="city" type="text" value="<%=city%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>State/Province</th>
											<td><input name="state" type="text" value="<%=state%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>Country</th>
											<td><input name="country" type="text" value="<%=country%>" /><font color="red">*</font></td>
										</tr>
										<tr>
											<th>Phone</th>
											<td><input name="phone" type="text" value="<%=phone%>" /></td>
										</tr>
									</tbody>
								</table>
								
								<br>
			<div class="form_controls">
			<html:submit value="Save" onclick="document.forms[0].elements[0].value='saveUser'; return validate_form( this )" />  
		    <html:submit value="Cancel" onclick="document.forms[0].elements[0].value='saveUserCancel';" />
			
			
			</div>
								
				
				</html:form>				 
							 
								 				
							</div>
							 
							 
 
									 
								 
							 
	 
	 
</body></html>