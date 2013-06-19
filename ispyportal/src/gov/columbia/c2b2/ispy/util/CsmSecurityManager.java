/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.util;

 
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.Group;
 
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.SearchCriteria;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.dao.UserSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;

import gov.columbia.c2b2.ispy.user.WebUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.security.sasl.AuthenticationException;

 

import org.apache.log4j.Logger;
/**
 *
 * @author Min You
 *
 */


 

public class CsmSecurityManager {
	 
	private static CsmSecurityManager instance;
	private static Logger logger = Logger.getLogger(CsmSecurityManager.class);
	private static AuthorizationManager authorizationManager;
	private static UserProvisioningManager userProvisioningManager;
	private static String application = "ispy" ;
	
	private CsmSecurityManager(){}
	private CsmSecurityManager(String app){application = app ;}
	public static CsmSecurityManager getInstance(){
		if( instance == null){
			instance = new CsmSecurityManager();
		}
		return instance;
	}
	
	public static CsmSecurityManager getInstance(String app){
		if( instance == null){
			instance = new CsmSecurityManager(app);
		}
		return instance;
	}
	 
	private static AuthorizationManager getAuthorizationManager() throws CSException{
		if(authorizationManager==null) {
			authorizationManager = SecurityServiceProvider.getAuthorizationManager(application);
		}
		return authorizationManager;
	}	
	 
	 
	private static UserProvisioningManager getUserProvisioningManager() throws CSException{
		if(userProvisioningManager==null) {
			userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager(application);
			 
		}
		return userProvisioningManager;
	}	
	
	public User getUser(String loginName)throws CSException{
		 
			return getAuthorizationManager().getUser(loginName);
		 
		}
		 
	
	
	public void createUser(WebUser user){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			
			User u = new User();
			u.setDepartment(user.getDepartment());
			u.setEmailId(user.getEmail());
			u.setFirstName(user.getFirstName());			 
			u.setLastName(user.getLastName());
			u.setLoginName(user.getUsername());
			 
			u.setPassword(user.getPassword());
			u.setPhoneNumber(user.getPhone());
			u.setStartDate(Util.getNow());			 
			
			User existingUser =  getUser(u.getLoginName());
			if( existingUser != null)
			{ 
			  u.setUserId(existingUser.getUserId());
			  u.setStartDate(existingUser.getStartDate());
			  u.setUpdateDate(Util.getNow());	
			  getUserProvisioningManager().removeUser(existingUser.getUserId().toString());
			}
			 
		      getUserProvisioningManager().createUser(u);
			  
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	 public void deleteUser(String login){
	    try{
	    	  
			  getUserProvisioningManager().removeUser(getUser(login).getUserId().toString());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	 
	 public boolean isAdmin(String login){
			try{
				
				User u = getUser(login); 				 
				String userId = u.getUserId().toString();
				Collection cal =getUserProvisioningManager().getGroups(userId);
				Iterator it = cal.iterator();
				while(it.hasNext()){
					Group gp = (Group)it.next();
					if (gp.getGroupName().equals("ADMIN_USER") && 
							gp.getApplication().getApplicationName().equalsIgnoreCase(application))
						return true;
				
				}
				 
			    return false;
			}catch(Exception ex){
				ex.printStackTrace();
			}
			 return false;
		}
	 
	 
	 public boolean isAdmin(User user){
			try{				
				 				 
				String userId = user.getUserId().toString();
				Collection cal =getUserProvisioningManager().getGroups(userId);
				Iterator it = cal.iterator();
				while(it.hasNext()){
					Group gp = (Group)it.next();
					if (gp.getGroupName().equals("ADMIN_USER") && 
							gp.getApplication().getApplicationName().equalsIgnoreCase(application))
						return true;
				
				}
				 
			    return false;
			}catch(Exception ex){
				ex.printStackTrace();
			}
			 return false;
		}
	 
	 
	 public List<User> findAdminUsers() {
		 
		
		   List adminUserList = new ArrayList();
		   List userList = new ArrayList();
		  
		   try {   
			   		    
			    
			   User u = new User();
			    
			   SearchCriteria searchCriteria = new UserSearchCriteria(u);
			   userList = getUserProvisioningManager().getObjects(searchCriteria);
			  
			   for(int i=0; i<userList.size();i++)
			   {
				   
			    
			       if ( isAdmin((User)userList.get(i)))
				     adminUserList.add(userList.get(i));
				   
			   }
			    
		  }   
		  catch (Exception e)
		  {
			  logger.error(e.getMessage());
			  
		  }
		 	
		    return adminUserList;
		      
			 
	    }
	 
	  

	 public List<User> findAllUsers() {
		 
			
		 List userList = new ArrayList();
		  
		   try {   	   		    
			    
			   User u = new User();			    
			   SearchCriteria searchCriteria = new UserSearchCriteria(u);
			   userList = getUserProvisioningManager().getObjects(searchCriteria);
		  }   
		  catch (CSException e)
		  {
			  logger.error(e.getMessage());
			  
		  }
		 	
		    return userList;
		      
			 
	    }

	
	
}
