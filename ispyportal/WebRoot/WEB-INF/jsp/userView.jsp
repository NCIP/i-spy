<%@ page import="java.util.List" %>
 <%@ page import="gov.columbia.c2b2.ispy.user.WebUser"%>
<%@ page import="gov.columbia.c2b2.ispy.util.Constants"%>
 

<html><head><title>I-SPY Manage Users</title>
	
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	
	<link href="css/bigStyle.css" rel="stylesheet" type="text/css">
	<link href="js/user/users.css" rel="stylesheet" type="text/css">
	<link href="js/user/style.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" language="javascript" src="js/user/jquery-1.2.1.pack.js"></script>
	<script type="text/javascript" language="javascript" src="js/user/tablesorter.js"></script>
	<script type="text/javascript" language="javascript">
	 
	 jQuery.noConflict();   
	 
	function confirmDelete() {
        return window.confirm("Deactivate this user?");
    }
    function confirmReActive() {
        return window.confirm("Reactivate this user?");
    }
	
	
	
	
	</script>

	 
	
	 </head><body> 
    

    <%
    WebUser u = (WebUser) request.getAttribute(Constants.ATTRIBUTE_USER);

   %>

 
	 
 

		 
				 
						 
							 

							 
							
							
							<div class="content_container">								
								
								
								<h3>View Account</h3>
								
								<p class="textright"> <a href="analysisHomeWithAdmin.do">Back to User List</a> </p>
								
								<br/>
								
								<p class="status">Status: <span><%=Constants.getStatus(u.getStatus())%></span></p>

								<table class="user_account">
									<tbody>
										<tr>
											<th>Username</th>
											<td><%= u.getUsername()%></td>
										</tr>
										<tr>
											<th>First Name</th>
											<td><%= u.getFirstName()%></td>
										</tr>
										<tr>
											<th>Last Name</th>
											<td><%= u.getLastName()%></td>
										</tr>
								
										<tr>
											<th>Email Address</th>
											<td><%= u.getEmail()%></td>
										</tr>
										<tr>
											<th>Institution</th>
											<td><%= u.getInstitution()%></td>
										</tr>
										<tr>
											<th>Department</th>
											<td><%= u.getDepartment()%></td>
										</tr>
										<tr>
											<th>Address 1</th>
											<td><%= u.getAddress1()%></td>
										</tr>
										<tr>
											<th>Address 2</th>
											<td><%= u.getAddress2()%></td>
										</tr>
										<tr>
											<th>City</th>
											<td><%= u.getCity()%></td>
										</tr>
										<tr>
											<th>State/Province</th>
											<td><%= u.getState()%></td>
										</tr>
										<tr>
											<th>Country</th>
											<td><%= u.getCountry()%></td>
										</tr>
										<tr>
											<th>Phone</th>
											<td><%= u.getPhone()%></td>
										</tr>
										 
										
										
										<tr>
                                           <th>Intended Data Use:</th>
         
                                           <td><%=u.getIntendedUse()%></td>

                                        </tr>
										 
									</tbody>
								</table>
								
								<div class="form_controls">
								
								<form action="manageUser.do" method="post" name="WebUserListForm">
    <input type="hidden" name="action" value="<%=Constants.ACTION_EDIT_USER%>"/>
    <input type="hidden" name="<%=Constants.PARAM_USER_ID%>" value="<%=u.getId()%>"/>
    <!--   <input type="submit" name="editSubmit" value="Edit"
        onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_EDIT_USER%>';"/>  -->
    
    <%   if (u.getStatus().getStatus() == Constants.STATUS_ACTIVE || u.getStatus().getStatus() == Constants.STATUS_CONFIRM) {
    %>
    <input type="submit" name="deleteSubmit" value="Deactivate"
           onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_DELETE_USER%>'; return confirmDelete()"/>
    <% } else if (u.getStatus().getStatus() == Constants.STATUS_PENDING) { %>
      <input type="hidden" name="<%=Constants.ATTRIBUTE_USER_LIST%><%=u.getId()%>"/>
    <input type="submit" name="approveSubmit" value="Approve"
           onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_APPROVE_USER%>';"/>
    <input type="submit" name="denySubmit" value="Deny"
           onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_BULK_DECLINE_USER%>';"/>
    <% } else { %>
    <input type="submit" name="deleteSubmit" value="Reactivate"
           onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_REACTIVATE_USER%>'; return confirmReActive()"/>
     
      
     <% } %>
    
    
    
    
</form>
								
								</div>
								
				
								<br>
							 
								 
							 							
							</div>
							 
							  
 
									 
								 
							 
					 

				 
		
 
</div>
 
	</div></body></html>