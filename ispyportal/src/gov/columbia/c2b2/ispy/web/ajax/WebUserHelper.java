package gov.columbia.c2b2.ispy.web.ajax;

 
import gov.columbia.c2b2.ispy.user.WebUser;
import gov.columbia.c2b2.ispy.util.CsmSecurityManager;
 
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.ispy.util.SecurityHelper;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.exceptions.CSException;
 
 
 
 
 
 
 
 

import java.util.*;

/**
 * User finder
 */
public class WebUserHelper {
    
	public static SecurityHelper securityHelper;
	public WebUserHelper()
	{
		  
	}
	
	private static SecurityHelper getSecurityHelper(){
		if(securityHelper==null) {
			securityHelper = (SecurityHelper)SpringContext.getBean("securityHelper");;
			 
		}
		return securityHelper;
	}	
	
    /**
     * Gets all users, ordered by user name.
     */
    public static List<WebUser> getAllUsers()
    {    
        return getSecurityHelper().getAllUsers();    
    }

    /**
     * Gets all deactivated users, ordered by user name.
     */
    public static List<WebUser> getDeactivatedUsers()
    {
    	 return getSecurityHelper().getDeactivatedUsers();		     	
         
    }
        
    
    public static WebUser findByUsername(String username)
     {  
        return findByUsername(username, false);
    }

    public static WebUser findByUsername(String username, boolean searchDeleted)
     {
    	return getSecurityHelper().findByUsername(username, searchDeleted);
    }

    public static WebUser findByEmail(String email)
      {
        return getSecurityHelper().findByEmail(email);
    }

    public static WebUser findByEmail(String email, boolean searchDeleted)
     {
    	return getSecurityHelper().findByEmail(email, searchDeleted);
    
    	 
    }

    public static WebUser findUserByRegistrationKey(String key)
      {
    	return getSecurityHelper().findUserByRegistrationKey(key);
    
    }
    
    public static WebUser findUserByUserID(int id)
     {
    	return getSecurityHelper().findUserByUserID(id);
    }

    public static List getUserLoginList(WebUser user, int days)
     {
    	return getSecurityHelper().getUserLoginList(user, days);
    }

    public static int getUserLoginCount(WebUser user, int days)
    {
    	
    	 return getSecurityHelper().getUserLoginCount(user,days);
    }

   /* public static List<WebUser> findUsersByRole(long roleId) throws HibernateException {
        return session.find("SELECT user from Role as role LEFT JOIN role.users user where role.id=? AND user.status<>'D'", new Long(roleId), Hibernate.LONG);
    }*/

    public static List getUsersByIdList(Object[] userIds)
    {
    	return getSecurityHelper().getUsersByIdList(userIds);
    }

    
    public static Date findUserLastAccess(WebUser user)
    {
    	return getSecurityHelper().findUserLastAccess(user);
    }
     
    public static void acceptAgreement(String loginName)
    {
    	getSecurityHelper().acceptAgreement(loginName);
    }
}
