package gov.nih.nci.ispy.web.struts.form;
import gov.nih.nci.caintegrator.application.cache.CacheConstants;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.ispy.util.ISPYListLoader;
import gov.nih.nci.ispy.util.UILookupMapLoader;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public final class LoginForm extends BaseForm
{
/**
     * 
     */
    private static final long serialVersionUID = 1L;
private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
private static String SEPERATOR = File.separator;
private boolean userLoggedIn = false;
private String userName;
private String password;
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
            SecurityManager secManager =  SecurityManager.getInstance("ispy") ;
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
                               
            UserListBean userListBean = new UserListBean();

            ISPYListValidator listValidator = new ISPYListValidator();
            
            String filePath = System.getProperty("gov.nih.nci.ispyportal.data_directory");
            
            userListBean = ISPYListLoader.loadLists(userListBean,ispyConstants.ALL_USER_LISTS, filePath, listValidator);  
            // load IspySpecific clinical status lists
            userListBean = ISPYListLoader.loadStatusGroups(userListBean);
            //add userListBean to session
            UserListBeanHelper userListBeanHelper = new UserListBeanHelper(session.getId());
            userListBeanHelper.addBean(session.getId(),CacheConstants.USER_LISTS,userListBean);
            //session.setAttribute(CacheConstants.USER_LISTS,userListBean);  
            
            //load database-dependent dropdowns
            UILookupMapLoader uiLookupMapLoader = (UILookupMapLoader)SpringContext.getBean("uiLookupLoader");
            Map<String,Collection> uiLookupMap = uiLookupMapLoader.getMap();
            session.setAttribute(ispyConstants.UI_LOOKUPS,uiLookupMap);
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
 * @param userLoggedIn The userLoggedIn to set.
 */
public void setUserLoggedIn(boolean userLoggedIn) {
    this.userLoggedIn = userLoggedIn;
}
}
