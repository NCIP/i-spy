<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page import="gov.columbia.c2b2.ispy.util.Config" %>
<%@ page import="gov.columbia.c2b2.ispy.util.Constants"%>
<%@ page import="java.util.ArrayList" %>
 
 
<%
    boolean editing = true;
    ArrayList userListIDs = (ArrayList) (request.getAttribute(Constants.ATTRIBUTE_USERID_LIST));

%>

<h4>Please fill the reason to reject the account(s).</h2>

<%    
    String error = (String) request.getAttribute(Constants.ATTRIBUTE_ERROR);
    if (error != null) {
%><p><font color="red"><%= error %></font></p><%
    }

%>

<p>
    The total number of affected accounts: <%=userListIDs==null?0:userListIDs.size()%>
  
    <form action="manageUser.do" method="post" name="WebUserListForm">
    <input type="hidden" name="action" value="<%=Constants.ACTION_SUBMIT_BULK_DECLINE_USER%>"/>
     
      <% if (userListIDs != null && userListIDs.size() > 0) {
        for (int i=0; i< userListIDs.size(); i++ ) {
           Integer id = (Integer)userListIDs.get(i);
           String oneuser = id.toString();
        %>

    <input type="hidden" name="<%=Constants.ATTRIBUTE_USER_LIST%><%=oneuser%>"
           value="<%=Constants.ATTRIBUTE_USER_LIST%><%=oneuser%>"/>

    <%
            }
        } else {
        }
    %>

    
    <table>
        <tr>
            <td><b>Reason:</b></td>
        </tr>
        <tr>
            <td><TEXTAREA NAME="<%=Constants.ATTRIBUTE_DENY_REASON%>" COLS=50
                          ROWS=8><%=Config.getConfig().getTemplate()%></TEXTAREA></td>

        </tr>
        <tr>
            <td><input type="submit" name="SUBMIT" value="<%= editing ? "Deny" : "Deactivate"%>"/></td>
        </tr>
    </table>
</form>
 
