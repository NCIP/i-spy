package gov.nih.nci.ispy.web.struts.form;
 
import gov.columbia.c2b2.ispy.util.CsmSecurityManager;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.dao.SearchCriteria;
import gov.nih.nci.security.dao.UserSearchCriteria;

import gov.nih.nci.ispy.util.ISPYListLoader;
import gov.nih.nci.ispy.util.SecurityHelper;
import gov.nih.nci.ispy.util.UILookupMapLoader;
import gov.nih.nci.ispy.util.UserGroupBean;
import gov.nih.nci.ispy.util.SecurityHelper;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.util.UserListLoaderDB;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;
import gov.columbia.c2b2.ispy.util.Constants;
import gov.columbia.c2b2.ispy.user.*;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.SecurityServiceProvider;
//import gov.nih.nci.security.UserSearchCriteria;
//import gov.nih.nci.caintegrator.security.SearchCriteria;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import uk.ltd.getahead.dwr.ExecutionContext;

public final class LoginForm extends BaseForm
{
/**
     * 
     */
private static final long serialVersionUID = 1L;
private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
private static String SEPERATOR = File.separator;
private boolean userLoggedIn = false;
private boolean isAdminRole = false;
private boolean acceptAgreement = false;

private String userName;
private String password;
private static UserProvisioningManager userProvisioningManager;
public void setUserName(String argUserName)
{
	userName = argUserName;
}
public String getUserName()
{
	return userName;
}
public void setPassword(String argPassword)
{
	password = argPassword;
}
public String getPassword()
{
	return password;
}

public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
{
	WebUser webUser =null;
	SecurityManager secManager =  SecurityManager.getInstance("ispy") ;
	SecurityHelper securityHelper = (SecurityHelper)SpringContext.getBean("securityHelper");
	ActionErrors errors=new ActionErrors();
	if(userName == null || password.equals(""))
	{
		errors.add("User Name:",new ActionMessage("error.userName"));
		errors.add("Password:",new ActionMessage("error.password"));
        errors.add("invalidLogin", new ActionMessage(
        "gov.nih.nci.nautilus.ui.struts.form.invalidLogin.error"));

	}    
   
    HttpSession session = request.getSession();
    FileInputStream fsi = null;

    Properties props = new Properties();
    ServletContext context = session.getServletContext();
    
    try {                  
    
        fsi = new FileInputStream(context.getRealPath("WEB-INF")+ SEPERATOR + "users.properties"); 
        logger.debug("loading props...");
        props.load(fsi);
        logger.debug("props file length: " + props.size()); 
        fsi.close();
        String authType = props.getProperty("authentication") ;
        
        if(authType != null && authType.equals("file"))
        {
            String u = "";
            String p = "";          
        
            u = props.getProperty("username");
            p = props.getProperty("password");
                                
            if(userName.equals(u) && password.equals(p))
            {
                userLoggedIn = true ;
            }
            else {
                errors.add("invalidLogin", new ActionMessage(
                        "gov.nih.nci.nautilus.ui.struts.form.invalidLogin.error"));
                request.getSession().invalidate();
            }
        }
        else if(authType != null && authType.equals("ldap"))
        {
            UserCredentials user = null;                    
            
            if(secManager.authenticate(userName, password)){
				user = secManager.authorization(userName);
				}
            if(user != null)
            {                           	
                userLoggedIn = true ;
                //userName = user.getFirstName();
            } 
            else {
                errors.add("invalidLogin", new ActionMessage(
                        "gov.nih.nci.nautilus.ui.struts.form.invalidLogin.error"));
                request.getSession().invalidate();
            }
        }
                    
                    
        
        if(userLoggedIn)
        {
            session.setAttribute("logged", "yes");
            session.setAttribute("name", userName);
            
            
            //userProvisioningManager = SecurityManager.getUserProvisioningManager();
            //User user = userProvisioningManager.getUser(userName);
            User user = CsmSecurityManager.getInstance("ispy").getUser(userName);
            
            
 //   		userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager("applicationContext"); 
            //userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager("ispy");
    		//User userAll = new User();
    		//SearchCriteria searchCriteria = new UserSearchCriteria(userAll);
    		//List list = userProvisioningManager.getObjects(searchCriteria);
    		//List<User> allUsers = new ArrayList<User>(list);
    		
            List<User> allUsers =  CsmSecurityManager.getInstance("ispy").findAllUsers();
            
    		for(User userPrcs : allUsers){
    			if(userPrcs.getUserId().equals(user.getUserId())){
    				allUsers.remove(userPrcs);
    				break;
    			}
    		}
    		session.setAttribute("currentUser", user);
    		session.setAttribute("allUsers", allUsers);
         
 //           UserListBean userListBean = new UserListBean();

            ISPYListValidator listValidator = new ISPYListValidator();
            
            String filePath = System.getProperty("gov.nih.nci.ispyportal.data_directory");
            
 //           userListBean = ISPYListLoader.loadLists(userListBean,ispyConstants.ALL_USER_LISTS, filePath, listValidator);  
            // load IspySpecific clinical status lists
 //           userListBean = ISPYListLoader.loadStatusGroups(userListBean);
            
            
//    		String userID = (String) session.getAttribute("name"); 
//            UserListLoaderDB userListLoaderDB = (UserListLoaderDB)SpringContext.getBean("userListLoaderDB");
        		
//           userListBean = userListLoaderDB.loadUserListFromDB( userID);

            //add userListBean to session
 //           UserListBeanHelper userListBeanHelper = new UserListBeanHelper(session.getId());
 //           userListBeanHelper.addBean(session.getId(),CacheConstants.USER_LISTS,userListBean);
            //session.setAttribute(CacheConstants.USER_LISTS,userListBean);
  

            //load database-dependent dropdowns
            UILookupMapLoader uiLookupMapLoader = (UILookupMapLoader)SpringContext.getBean("uiLookupLoader");
            Map<String,Collection> uiLookupMap = uiLookupMapLoader.getMap();
            session.setAttribute(ispyConstants.UI_LOOKUPS,uiLookupMap);
        
            //add by min you
            isAdminRole = CsmSecurityManager.getInstance("ispy").isAdmin(userName);
        	 
       	    webUser = securityHelper.findByUsername(userName, true);             
            
            acceptAgreement = securityHelper.isAcceptAgreement(webUser);
                      
            
            // Log the action
            Date now = Calendar.getInstance().getTime();
            Login login = new Login(now, request.getRemoteAddr(),webUser);
            securityHelper.save(login);
            
            
            
            if (isAdminRole)
          	   session.setAttribute(Constants.CSM_GROUP_NAME,"ADMIN_USER");
             else
             {            	 
             	session.setAttribute(Constants.CSM_GROUP_NAME,"NOT_ADMIN_USER");
             }
        
        
        }
        
    } catch (Exception e) {
        logger.error("Problem logging in");
        logger.error(e);       
        errors.add("invalidLogin", new ActionMessage(
                    "gov.nih.nci.nautilus.ui.struts.form.invalidLogin.error"));
        request.getSession().invalidate();
        if(fsi != null)
            try {
                fsi.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
      }
      finally   {
        if(fsi != null)
            try {
                fsi.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
     }
    
    
    
	return errors;
}
/**
 * @return Returns the userLoggedIn.
 */
public boolean isUserLoggedIn() {
    return userLoggedIn;
}

/**
 * @return Returns the adminRole.
 */
public boolean isAdminRole() {
    return this.isAdminRole;
}


/**
 * @return Returns the acceptAgreement.
 */
public boolean isAcceptedAgreement() {
    return this.acceptAgreement;
}

/**
 * @param userLoggedIn The userLoggedIn to set.
 */
public void setUserLoggedIn(boolean userLoggedIn) {
    this.userLoggedIn = userLoggedIn;
}
}
