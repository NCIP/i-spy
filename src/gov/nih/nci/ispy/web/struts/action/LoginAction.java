/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.CacheConstants;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.ispy.util.ISPYListLoader;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;
import gov.nih.nci.ispy.web.struts.form.LoginForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class LoginAction extends Action
{
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    private static String SEPERATOR = File.separator;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
        LoginForm f = (LoginForm) form;
//		String userName=f.getUserName();
//		String password = f.getPassword();
//		
//		boolean valid = false;		
//		HttpSession session = request.getSession();
//        FileInputStream fsi = null;
//
//		Properties props = new Properties();
//		ServletContext context = session.getServletContext();
//        
//	    try {				   
//        
//			fsi = new FileInputStream(context.getRealPath("WEB-INF")+ SEPERATOR + "users.properties"); 
//			logger.debug("loading props...");
//			props.load(fsi);
//			logger.debug("props file length: " + props.size());	
//			fsi.close();
//			String authType = props.getProperty("authentication") ;
//			
//			if(authType != null && authType.equals("file"))
//			{
//				String u = "";
//				String p = "";			
//			
//				u = props.getProperty("username");
//				p = props.getProperty("password");
//									
//				if(userName.equals(u) && password.equals(p))
//				{
//					valid = true ;
//				}
//			}
//			else if(authType != null && authType.equals("ldap"))
//			{
//			    UserCredentials user = null;	    			
//				SecurityManager secManager =  SecurityManager.getInstance("ispy") ;
//				user = secManager.authenticate(userName,password);
//				if(user != null)
//				{
//					valid = true ;
//				}						
//			}
//				    	
//						
//			
//			if(valid)
//			{
//				session.setAttribute("logged", "yes");
//                session.setAttribute("name", userName);
//                                   
//                UserListBean userListBean = new UserListBean();
//
//                ISPYListValidator listValidator = new ISPYListValidator();
//                
//                String filePath = System.getProperty("gov.nih.nci.ispyportal.data_directory");
//                
//                userListBean = ISPYListLoader.loadLists(userListBean,ispyConstants.ALL_USER_LISTS, filePath, listValidator);  
//                // load IspySpecific clinical status lists
//                userListBean = ISPYListLoader.loadStatusGroups(userListBean);
//                //add userListBean to session
//                session.setAttribute(CacheConstants.USER_LISTS,userListBean);
//                
//				return (mapping.findForward("success"));	
//			}
//			
//		} catch (Exception e) {
//		    logger.error("Can't read user props");
//		    logger.error(e);
//            if(fsi != null)
//                fsi.close();
//          }
//          finally   {
//            if(fsi != null)
//                fsi.close();
//         }
//		
		if(f.isUserLoggedIn())
			return (mapping.findForward("success"));
		else
			return (mapping.findForward("failure"));
			
		
	}
}
