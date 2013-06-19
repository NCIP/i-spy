/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.struts.action;

 
import gov.nih.nci.ispy.util.ispyConstants;
import gov.columbia.c2b2.ispy.web.struts.form.WebUserListForm;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
import org.apache.log4j.Logger;
 
 
import gov.columbia.c2b2.ispy.user.*;
import gov.columbia.c2b2.ispy.util.*;
 
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
 
import gov.nih.nci.ispy.web.struts.form.BaseForm;
import gov.nih.nci.ispy.util.SecurityHelper;
import gov.nih.nci.ispy.util.ispyConstants;

import gov.columbia.c2b2.ispy.util.*;

public final class WebUserListAction extends Action
{
     
   
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    
    public static final String PARAM_USER_ID = "userID";
    private CsmSecurityManager secManager =  CsmSecurityManager.getInstance("ispy") ;
    
    private SecurityHelper securityHelper = (SecurityHelper)SpringContext.getBean("securityHelper");
    
    private String forwardStr = null;
   
    private static SMTPMailer mailer = new SMTPMailer();
    
    public String getFrdStr()
    {
    	return this.forwardStr;
    }
    private Long getUserID(HttpServletRequest request, ParameterMap params) throws Exception {
        String userIDString = params.getParameter(PARAM_USER_ID);
        if ((userIDString == null)  ) {
            return null;
        } else {
            return new Long(userIDString);
        }
    }
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		    WebUserListForm f = (WebUserListForm) form;
		
		    String forwardStr = null;
		    String userName  = null;
	    	ParameterMap params = new ParameterMap();
	    	        
	    	
	    	try
	    	{
	    	  HttpSession session = request.getSession();
	    	  String action = f.getAction();
	    	  
	    	  Enumeration paramNames = request.getParameterNames();
		      while (paramNames.hasMoreElements()) {
		            String name = (String) paramNames.nextElement();
		            params.put(name, request.getParameterValues(name));
		      }    	  
	    	  
	    	  userName = (String)session.getAttribute("name");
	    	  
	    	  WebUser theUser = null;
	          Long userID = getUserID(request, params);
	          if ( userID != null )
	             theUser = (WebUser)securityHelper.findUserByUserID(userID.intValue());
	          else
	        	  theUser = (WebUser)securityHelper.findByUsername(userName,true);
	        	  
	          
	          List<Integer> userListIDs = new ArrayList<Integer>();
	           
	          Set<String> paramNameSet = params.keySet();
	          for (String p : paramNameSet) {
	              if (p.startsWith(Constants.ATTRIBUTE_USER_LIST)) {
	                  String idString = p.substring(Constants.ATTRIBUTE_USER_LIST.length());
	                  int id = Integer.parseInt(idString);
	                  userListIDs.add(id);
	              }
	          }
	          
	          if ( theUser != null )
	          { 
	        	  if (theUser.getIntendedUse() == null || theUser.getIntendedUse().equals("null"))
	          	  theUser.setIntendedUse("");
	          }
	          
	          
	          request.setAttribute(Constants.ATTRIBUTE_USER, theUser);
	          request.setAttribute(Constants.ATTRIBUTE_USERID_LIST, userListIDs);
	          request.setAttribute(Constants.ATTERIBUTE_LIST_PROPERTY, Constants.ATTRIBUTE_ALL);
	    	  
	          
	          if (Constants.ACTION_LIST_USERS.equals(action)) {
	              List<WebUser> users = securityHelper.getAllUsers();
	            	              
	              request.setAttribute(Constants.ATTRIBUTE_USER_LIST, users);
	              List<WebUser> deactivatedUsers = securityHelper.getDeactivatedUsers();
	              
	              request.setAttribute(Constants.ATTRIBUTE_USER_LIST, users);
	              request.setAttribute(Constants.ATTRIBUTE_DEACTIVATVED_USER_LIST, deactivatedUsers);
	             
	              forwardStr = Constants.JSP_USER_LIST;
	          } else if (Constants.ACTION_VIEW_PENDING_USERS.equals(action)) {
	              List<WebUser> users = securityHelper.getAllUsers();
	              List<WebUser> pending_users = new ArrayList<WebUser>();
	              for (WebUser oneUser : users) {
	                  if (oneUser.getStatus().getStatus() == Constants.STATUS_PENDING) {
	                      pending_users.add(oneUser);
	                  }
	              }
	              request.setAttribute(Constants.ATTRIBUTE_USER_LIST, pending_users);
	              request.setAttribute(Constants.ATTERIBUTE_LIST_PROPERTY, Constants.ATTRIBUTE_PENDING);
	              forwardStr = Constants.JSP_USER_LIST;
	          } else if (Constants.ACTION_BULK_APPROVE_USER.equals(action)) {

	              if (userListIDs != null && userListIDs.size() > 0) {
	                  for (int id : userListIDs) {
	                      WebUser oneUser = (WebUser) securityHelper.findUserByUserID(id);
	                      oneUser.setStatus(new Status(Constants.STATUS_CONFIRM));
	                      oneUser.setAuditInfo(new AuditInfo(null,null, userName ,Util.getNow()));
	                      securityHelper.update(oneUser);
	                      CsmSecurityManager.getInstance("ispy").createUser(oneUser);
	                      setInfoString(request, "The user account has been approved.");
	                      sendUserEmail(oneUser, true, "");
	                  }

	              }
	              forwardStr = Constants.JSP_USER_MODIFIED;
	              
	          } else if (Constants.ACTION_BULK_DECLINE_USER.equals(action)) {
	              if (userListIDs.size() > 0) {
	            	  forwardStr = Constants.JSP_USER_MODIFIEDEXP;
	              }

	          } else if (Constants.ACTION_SUBMIT_BULK_DECLINE_USER.equals(action)) {

	              String reason = params.getParameter(Constants.ATTRIBUTE_DENY_REASON);
	              if (userListIDs.size() > 0) {
	                  for (int id : userListIDs) {
	                      WebUser oneUser = (WebUser) securityHelper.findUserByUserID(id);
	                      oneUser.setStatus(new Status(Constants.STATUS_DEACTIVATED));
	                      oneUser.setAuditInfo(new AuditInfo(null,null,userName,Util.getNow()));
	                      securityHelper.update(oneUser);
	                      CsmSecurityManager.getInstance("ispy").deleteUser(oneUser.getEmail());
	                      sendUserEmail(oneUser, false, reason);
	                  }
	                  if (userListIDs.size() == 1) {
	                      setInfoString(request, "The user account has been denied.");
	                  } else {
	                      setInfoString(request, "The user accounts have been denied.");
	                  }
	                  forwardStr = Constants.JSP_USER_MODIFIED;
	              }
	          } else if (Constants.ACTION_BULK_DELETE_USER.equals(action)) {

	              if (userListIDs.size() > 0) {
	                  for (int id : userListIDs) {
	                      WebUser oneUser = (WebUser) securityHelper.findUserByUserID(id);
	                      oneUser.setStatus(new Status(Constants.STATUS_DEACTIVATED));
	                      oneUser.setAuditInfo(new AuditInfo(null,null,userName,Util.getNow()));
	                      securityHelper.update(oneUser);
	                      CsmSecurityManager.getInstance("ispy").deleteUser(oneUser.getEmail());
	                      setInfoString(request, "The user account has been deactivated.");
	                  }
	                  forwardStr = Constants.JSP_USER_MODIFIED;
	              }
	          } else if (Constants.ACTION_BULK_REACTIVATE_USER.equals(action)) {

	              if (userListIDs.size() > 0) {
	                  for (int id : userListIDs) {
	                      WebUser oneUser = (WebUser) securityHelper.findUserByUserID(id);
	                      if (oneUser.getAcceptDate() != null)
	                          oneUser.setStatus(new Status(Constants.STATUS_ACTIVE));
	                      else
	                    	  oneUser.setStatus(new Status(Constants.STATUS_CONFIRM));
	                      oneUser.setAuditInfo(new AuditInfo(null,null,userName,Util.getNow()));
	                      securityHelper.update(oneUser);
	                      CsmSecurityManager.getInstance("ispy").createUser(oneUser);
	                      setInfoString(request, "The user account has been Reactivated.");
	                  }
	                  forwardStr = Constants.JSP_USER_MODIFIED;
	              }	           
	          } 
	          else if (Constants.ACTION_VIEW_USER.equals(action)) {
	        	              
	        	  forwardStr = Constants.JSP_USER_VIEW;
	          
	          }
	          else if (Constants.ACTION_EDIT_USER.equals(action)) {
	        	  forwardStr = Constants.JSP_USER_EDIT;
	           
	    	   
	    	 } else if (Constants.ACTION_DELETE_USER.equals(action)) {
	             theUser.setStatus(new Status(Constants.STATUS_DEACTIVATED));
	             theUser.setAuditInfo(new AuditInfo(null,null,userName,Util.getNow()));
                 securityHelper.update(theUser);
                 CsmSecurityManager.getInstance("ispy").deleteUser(theUser.getEmail());
                 setInfoString(request, "The user was deactivated.");
	             forwardStr = Constants.JSP_USER_MODIFIED;
	         } else if (Constants.ACTION_REACTIVATE_USER.equals(action)) {
	        	 if (theUser.getAcceptDate() != null)
                     theUser.setStatus(new Status(Constants.STATUS_ACTIVE));
                 else
               	     theUser.setStatus(new Status(Constants.STATUS_CONFIRM));
	             theUser.setAuditInfo(new AuditInfo(null,null,userName,Util.getNow()));
                 securityHelper.update(theUser);
                 CsmSecurityManager.getInstance("ispy").createUser(theUser);
                 
                 setInfoString(request, "The user was reactivated.");
	             forwardStr = Constants.JSP_USER_MODIFIED;
	         } else if (Constants.ACTION_SAVE_USER.equals(action)) {
	                 
	                 theUser = (WebUser) securityHelper.findByUsername(f.getOldEmail(),true);
	        	     f.loadUser(theUser);
	                 theUser.setUsername(theUser.getEmail());
	                 securityHelper.update(theUser);
	                 CsmSecurityManager.getInstance("ispy").createUser(theUser);
	                 setInfoString(request, "The user info was saved.");
	                 forwardStr =  Constants.JSP_USER_MODIFIED;              

	         } 
	         else if (Constants.ACTION_SAVE_USER_CANCEL.equals(action)) {
                                   
                 theUser = securityHelper.findByUsername(f.getOldEmail(), true);                  
                 request.setAttribute(Constants.ATTRIBUTE_USER, theUser);
                 forwardStr =  Constants.JSP_USER_VIEW;              

            } 
	         else if (Constants.ACTION_APPROVE_USER.equals(action)) {
	             theUser.setStatus(new Status(Constants.STATUS_CONFIRM));
	             theUser.setAuditInfo(new AuditInfo(null,null,userName,Util.getNow()));
                 securityHelper.update(theUser);
                 CsmSecurityManager.getInstance("ispy").createUser(theUser);
                 setInfoString(request, "The user account has been approved.");
	             sendUserEmail(theUser, true, "");
	             forwardStr = Constants.JSP_USER_MODIFIED;        
	         }
	          else
	          {
	        	  forwardStr = Constants.JSP_USER_LIST;
	          }
	    	  }	  //end try
	          catch (Exception e)
	          {
	        	  logger.error(e.getMessage());
	          }
		    	    	
	          return (mapping.findForward(forwardStr));
		     
	  		
	}
	
	public static void sendUserEmail(WebUser user, boolean approved, String reason) throws Exception {
        String body = "";
        String title = "ISPY User Account";
        if (approved == true) {
            title += " has been approved.";
            body += "Your ISPY user account has been approved. ";
            //body += "Proceed to the following address to login:\n\n";
            //body += Config.getConfig().getBaseUrl() + ConfirmUserServlet.SERVLET_URI + "?" + ConfirmUserServlet.PARAM_USER_ID + "=" + user.getId() + "&" + ConfirmUserServlet.PARAM_KEY + "=" + user.getRegistrationKey();
            body += reason;
        } else {
            title += " has been rejected.";
            body += "Your ISPY user account has been denied. The reason is: \n\n";
            body += reason;
            body += "\n\nIf any question, please contact " + Config.getConfig().getContactUsEmail();
        }
        String fromAddress = Config.getConfig().getEmailFromSystem();
        mailer.sendEmail(title, body, null, fromAddress, user.getEmail());
    }
    
     
    public static void setInfoString(HttpServletRequest request, String info) {
        request.setAttribute(Constants.ATTRIBUTE_INFO, info);
    }
    
    
	
}
