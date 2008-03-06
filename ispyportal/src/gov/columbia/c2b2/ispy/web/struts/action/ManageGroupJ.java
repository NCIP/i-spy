package gov.columbia.c2b2.ispy.web.struts.action;

import org.apache.struts.actions.*;
import org.apache.struts.action.*;

import javax.servlet.http.*;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import javax.naming.*;
//import javax.naming.InitialContext;
//import javax.naming.Context;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

//import org.patient.glu.pcSQLException;
//import org.patient.glu.query_db;
//import org.util.My_calendar;
import gov.columbia.c2b2.ispy.jdbc.GrDbConn;
import gov.columbia.c2b2.ispy.jdbc.GrDbIns;

public class ManageGroupJ extends DispatchAction {
	
    private String MessIID;
    private ArrayList headers = null;
    private ArrayList userList = null;
	
	/**********************************************************************/
    public ActionForward execute(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
        throws IOException {
         String message = "";
         String addr = request.getRemoteAddr();
         String query="";
 
         HttpSession session = request.getSession(false);
         Object userID = session.getAttribute("patcisUser");
         userID = 67;
//         query = "SELECT SUBSTR(A.MessageDate,0) AS Dates, A.MessageFromUserID AS FromUID, A.MessageSubject AS Subject, B.MessageInstanceID AS messIID, B.MessageReadFlag AS ReadFlag, C.UserLastName + ',' + SPACE(1) + C.UserFirstName AS nameU FROM MessagesComposed A, MessageReceived B, Users C WHERE A.MessageID = B.MessageID AND B.MessageToUserID = '" + userID + "' AND A.MessageFromUserID = C.Account_Name AND B.MessageDeletedFlag IS NULL ORDER BY A.MessageDate DESC";
           query = "SELECT own_id as userID, GR_ID as groupID, gr_name as groupName," +
           "gr_status as groupStatus, share_status as shareStatus, update_time FROM " +
           "csm_groupm WHERE own_id = " + userID + " and gr_status = 'A'";

System.out.println("query is |" + query + "|");
	 message = "|myIdeatel|" + userID + "|" + addr + "|" + "view^messages^inbox";
	 servlet.log(message);
         headers =  GrDbConn.getGroups(query,"head");

         request.setAttribute("headers", headers);
	 return mapping.findForward("success");
    }//end execute method
/**********************************************************************/

}
