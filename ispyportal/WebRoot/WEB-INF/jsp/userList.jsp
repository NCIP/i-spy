<html><head> 
	
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
    <%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
    <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
    <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
    <%@ page import="java.util.*"%>   
	<%@ page import="gov.columbia.c2b2.ispy.util.Constants"%>
	<%@ page import="gov.columbia.c2b2.ispy.user.WebUser"%>
	<%@ page import="gov.columbia.c2b2.ispy.util.Util"%>

	
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	
	<link href="css/bigStyle.css" rel="stylesheet" type="text/css">
	<link href="js/user/style.css" rel="stylesheet" type="text/css">
	<link href="js/user/users.css" rel="stylesheet" type="text/css"> 
	 
	<script type="text/javascript" language="javascript" src="js/user/jquery-1.2.1.pack.js"></script> 	
	<script type="text/javascript" language="javascript" src="js/user/tablesorter.js"></script>
	
	<%@ page language="java" %><%@ page buffer="none" %><%@ page import="gov.columbia.c2b2.ispy.web.ajax.*"%><%
      
     List users = (List) request.getAttribute(Constants.ATTRIBUTE_USER_LIST);
     if (users == null)
        users = (List) WebUserHelper.getAllUsers();
     String currentPage = (String) request.getAttribute(Constants.ATTERIBUTE_LIST_PROPERTY);
     if (currentPage == null || currentPage.trim().equals(""))
        currentPage = Constants.ATTRIBUTE_ALL;
     List deactivatedUsers = (List)  WebUserHelper.getDeactivatedUsers();
     String lastAccessDateStr = "";
     request.setAttribute("action", "test");
     
    %>
	
	<script type="text/javascript" language="javascript">
	  
	  
	jQuery.noConflict(); 
        
	
	
	jQuery.fn.extend({
   		check: function() {
		 return this.each(function() { this.checked = true; });
	   },
	   uncheck: function() {
		 return this.each(function() { this.checked = false; });
	   }
	 });
 
	jQuery(document).ready( function(){

		jQuery('#unique_id').tablesorter({
			headers: {
				0: { sorter: false }
			}
		});
		
	});
	 
	
	 
	 function confirmDelete(count) {
       
        if ( count > 1)
        return window.confirm("Deactivate the specified users?");
        else
         return window.confirm("Deactivate the specified user?");
    }
    
     function confirmReActive(count) {
        if ( count > 1)
        return window.confirm("Reactivate the specified users?");
        else
         return window.confirm("Reactivate the specified user?");
    }
    
    function doSelection(turnOn) {
    <%
             for (int i = 0; i < users.size(); i++) {
                WebUser oneUser = (WebUser) users.get(i);
     %>
        document.forms[0].<%=Constants.ATTRIBUTE_USER_LIST%><%=oneUser.getId()%>.checked = turnOn;
    <%
            }
    %>
    }

    function checkdeletedscript() {
        var checkFound = false;
        var count = 0;
    <%
               if(deactivatedUsers!=null){
                for (int i = 0; i < deactivatedUsers.size(); i++) {
               WebUser oneUser = (WebUser) deactivatedUsers.get(i);
    %>

        if (document.forms[0].<%=Constants.ATTRIBUTE_USER_LIST%><%=oneUser.getId()%>.checked) {
            checkFound = true;
            count++;
        }
    <%
            }
            }
    %>
        if (checkFound != true) {
            alert("Please check at least one user.");
            return false;
        }
        return confirmReActive(count);
    }

    function checksendmailscript() {
        var checkFound = false;
       
        
    <%  if(users!=null){
          for (int i = 0; i < users.size(); i++) {
               WebUser oneUser = (WebUser) users.get(i);
    %>

               if (document.forms[0].<%=Constants.ATTRIBUTE_USER_LIST%><%=oneUser.getId()%>.checked) {
                    checkFound = true;
            

               }
       <%
         }  }
       %>
    
      
    
        if (checkFound != true) {
            <%
               if(deactivatedUsers!=null){
                 for (int i = 0; i < deactivatedUsers.size(); i++) {
                    WebUser oneUser = (WebUser) deactivatedUsers.get(i);
             %>

                     if (document.forms[0].<%=Constants.ATTRIBUTE_USER_LIST%><%=oneUser.getId()%>.checked) {
                         checkFound = true;
            
                      }
       <%
            }
            }
       %>
          if (checkFound != true) {
            alert("Please check at least one user.");
            return false;
          }
       }
        
           return true;
    }
    
     function checkscript(needConfirm) {
        var checkFound = false;
        var count = 0;
        
    <%          if(users!=null){
                for (int i = 0; i < users.size(); i++) {
               WebUser oneUser = (WebUser) users.get(i);
    %>

        if (document.forms[0].<%=Constants.ATTRIBUTE_USER_LIST%><%=oneUser.getId()%>.checked) {
            checkFound = true;
            count++;

        }
    <%
            }  }
    %>
        if (checkFound != true) {
            alert("Please check at least one user.");
            return false;
        }
         if ( needConfirm == true )
           return confirmDelete(count); 
         else
           return true;
    }
	
	
	
	 
	</script>
	 
	
	 </head>
	 
	 
	 <body> 
 
		  
												
								<h3>Manage Users</h3>								
								
								<html:form action="manageUser.do" method="POST">
								<html:hidden property="action" value="approveUser"/>
								
								<% if (currentPage.equalsIgnoreCase(Constants.ATTRIBUTE_ALL)) {%>
                                 <p class="textright"><a href="manageUser.do?action=<%=Constants.ACTION_VIEW_PENDING_USERS%>"> Show
                                       Pending accounts</a>
                                 <%}%>
                                 <% if (currentPage.equalsIgnoreCase(Constants.ATTRIBUTE_PENDING)) {%>

                                <p class="textright"><a href="manageUser.do?action=<%=Constants.ACTION_LIST_USERS%>"> Show All
                                 Accounts</a>
                                   <%}%>


                                <p class="textright"><a href="#" onclick="doSelection(true); return false">Select all</a> | <a href="#"
                                                                                               onclick="doSelection(false); return false">Clear
                                     selection</a>
                                </p>
								
								<table id="unique_id" size="80%" class="tablesorter">
									<thead>
										<tr>
											<th height="51">&nbsp;</th>
										   <th class="sortable">First<br>Name</th>
										   <th class="sortable">Last<br>Name</th>
										   <th class="sortable">Institution</th>
								
											<th class="sortable">Create<br>Date</th>
											<th class="sortable">Last<br>Access<br>Date</th>
											<th class="sortable">Status</th>
										</tr>
									</thead>
								
									<tbody>
		<%
            for (int i = 0; i < users.size(); i++) {
                WebUser u = (WebUser) users.get(i);
                lastAccessDateStr = "";

        %>
        <div id="divname"<%=u.getId()%> >
            <tr <%=(i % 2 == 1) ? "class=\"odd\"" : ""%>>                 
                <td><input type="checkbox" name="<%=Constants.ATTRIBUTE_USER_LIST%><%=u.getId()%>"/></td>
                <td>
                    <a href="manageUser.do?action=<%=Constants.ACTION_VIEW_USER%>&<%=Constants.PARAM_USER_ID%>=<%=u.getId()%>"><%=u.getFirstName()%></a>
                </td>
                <td>
                    <a href="manageUser.do?action=<%=Constants.ACTION_VIEW_USER%>&<%=Constants.PARAM_USER_ID%>=<%=u.getId()%>"><%=u.getLastName()%></a>
                </td>
                <td><%=u.getInstitution()%></td>
                <td><%=Util.formatDateUS(u.getRegisteredDate())%></td>
                
                <% if (currentPage.equalsIgnoreCase(Constants.ATTRIBUTE_ALL))  
                   { 
                     Date lastAccessDate = WebUserHelper.findUserLastAccess(u);
                     lastAccessDateStr = Util.formatDateUS(lastAccessDate);
                                     
                  %>                  
                     
                  <%}%>
                  <td><%=lastAccessDateStr%></td>
                  <td><%=Constants.getStatus(u.getStatus())%></td>
            </tr>
        </div>
        <%
           }
        %>
        <%
            if (currentPage.equalsIgnoreCase(Constants.ATTRIBUTE_ALL)) {
                for (int i = 0; i < deactivatedUsers.size(); i++) {
                    WebUser u = (WebUser) deactivatedUsers.get(i);
        %>
        <div id="divname"<%=u.getId()%> >
            <tr <%=(i % 2 == 1) ? "class=\"odd\"" : ""%>>
                 
                <td><input type="checkbox" name="<%=Constants.ATTRIBUTE_USER_LIST%><%=u.getId()%>"/></td>
                 
                <td>
                    <a href="manageUser.do?action=<%=Constants.ACTION_VIEW_USER%>&<%=Constants.PARAM_USER_ID%>=<%=u.getId()%>"><%=u.getFirstName()%></a>
                </td>
                <td>
                    <a href="manageUser.do?action=<%=Constants.ACTION_VIEW_USER%>&<%=Constants.PARAM_USER_ID%>=<%=u.getId()%>"><%=u.getLastName()%></a>
                </td>
                <td><p class=deactivated><%=u.getInstitution()%></td>
                <td><p class=deactivated><%=Util.formatDateUS(u.getRegisteredDate())%></td>
                
                <% if (currentPage.equalsIgnoreCase(Constants.ATTRIBUTE_ALL)) {
                    Date lastAccessDate = WebUserHelper.findUserLastAccess(u);
                    lastAccessDateStr = Util.formatDateUS(lastAccessDate);
                %>                
                <td><p class=deactivated><%=lastAccessDateStr%></td>
                <td><p class=deactivated><%=Constants.getStatus(u.getStatus())%></td>
                
                <%}%>
            </tr>
        </div>

        <%
              }   
            }
        %>
								
										
						</tbody>
		</table>
								
								<div class="form_controls">
									
									<input type="submit" name="Approve" value="Approve"
       onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_BULK_APPROVE_USER%>'; return checkscript()"/>
<input type="submit" name="DenySubmit" value="Deny"
       onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_BULK_DECLINE_USER%>'; return checkscript()"/>

<% if (!currentPage.equalsIgnoreCase(Constants.ATTRIBUTE_PENDING)) {%>
<input type="submit" name="reSubmit" value="Deactivate"
       onclick="document.forms[0].action.value='<%=Constants.ACTION_BULK_DELETE_USER%>';  return checkscript(true)"/>

<input type="submit" name="deleteSubmit" value="Reactivate"
       onclick="document.forms[0].elements[0].value='<%=Constants.ACTION_BULK_REACTIVATE_USER%>';  return checkdeletedscript()"/>

 
<%}%>
								
								
								</div>
								
								</html:form>
								
											 
								 
							 							
					 

</body></html>