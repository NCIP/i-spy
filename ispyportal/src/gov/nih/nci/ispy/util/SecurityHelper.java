package gov.nih.nci.ispy.util;

 

import gov.columbia.c2b2.ispy.user.*;
import gov.columbia.c2b2.ispy.util.*;
 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
 
import org.hibernate.Hibernate;
 
 

import java.util.*;

/**
 * User finder
 */
public class SecurityHelper {
    
    // This is the Hibernate Session factory that is injected by Spring
    private SessionFactory sessionFactory;
    
    /**
     * @return Returns the sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory The sessionFactory to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
          this.sessionFactory = sessionFactory;
    }
    
    
    /**
     * Gets all users, ordered by user name.
     */
    public List<WebUser> getAllUsers()
    throws HibernateException {
    	
    	  String theHQL = "";
          Query theQuery = null;          
          
    	 Session theSession = sessionFactory.getCurrentSession();
        
    	 theHQL = "from WebUser as user where user.status<>'D'and user.status<>'R' order by user.lastName";
		 theQuery = theSession.createQuery(theHQL);
		 
		 return theQuery.list();
    	 
    }

    /**
     * Gets all deactivated users, ordered by user name.
     */
    public List<WebUser> getDeactivatedUsers()
     throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
      
  	    theHQL = "from WebUser as user where user.status='D' order by user.status, user.lastName asc";
		theQuery = theSession.createQuery(theHQL);
		     	
        return theQuery.list();
    }
        
    
    public  WebUser findByUsername(String username)
    throws HibernateException {  
        return findByUsername(username, false);
    }

    public WebUser findByUsername(String username, boolean searchDeleted)
    throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
  	    username = username.toUpperCase();
  	    theHQL = "FROM WebUser as user WHERE " + (searchDeleted ? "" : "user.status<>'D' AND ") + "upper(user.username)='" +username + "'";
		theQuery = theSession.createQuery(theHQL);  	 
    	 
        return theQuery.list().isEmpty() ? null : (WebUser) theQuery.list().get(0);
    }

    public WebUser findByEmail(String email)
    throws HibernateException {
        return findByEmail(email, true);
    }

    public WebUser findByEmail(String email, boolean searchDeleted)
    throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
  	    email = email.toUpperCase();
  	    theHQL = "FROM WebUser as user WHERE " + (searchDeleted ? "" : "user.status<>'D' AND ") + "upper(user.email)='" +email + "'";
		theQuery = theSession.createQuery(theHQL);  	 
    	
       
        return theQuery.list().isEmpty() ? null : (WebUser) theQuery.list().get(0);
    	
    	
    	 
    }

    public WebUser findUserByRegistrationKey(String key)
    throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
  	    
  	    theHQL = "FROM WebUser as user WHERE user.registrationKey='" + key + "'";
		theQuery = theSession.createQuery(theHQL);         
         
        return theQuery.list().isEmpty() ? null : (WebUser) theQuery.list().get(0);

    }


    public WebUser findUserByUserID(int id)
    throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
  	    
  	    theHQL = "FROM WebUser as user WHERE user.id='" + id + "'";
		theQuery = theSession.createQuery(theHQL);  
        
        return theQuery.list().isEmpty() ? null : (WebUser) theQuery.list().get(0);

    }

    public List getUserLoginList(WebUser user, int days)
    throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        Date curDate = new Date();
        Date firstDate = new Date(curDate.getTime() - ((long) days * 24L * 60L * 60L * 1000L));
  	    Session theSession = sessionFactory.getCurrentSession();
  	    
  	    theHQL = "FROM Login as l where l.user.id=? AND loginDate BETWEEN ? AND ?";
		theQuery = theSession.createQuery(theHQL);   	       
       
		theQuery.setParameter(0, user.getId(), Hibernate.LONG);
		theQuery.setParameter(1, firstDate, Hibernate.TIMESTAMP);
		theQuery.setParameter(2, curDate, Hibernate.TIMESTAMP);
        return theQuery.list();
    }

    public int getUserLoginCount(WebUser user, int days)
           throws HibernateException {
    	
    	String theHQL = "";
        Query theQuery = null;          
        Date curDate = new Date();
        Date firstDate = new Date(curDate.getTime() - ((long) days * 24L * 60L * 60L * 1000L));
  	    Session theSession = sessionFactory.getCurrentSession();
  	    
  	    theHQL = "SELECT COUNT(l) FROM Login as l where l.user=? AND loginDate BETWEEN ? AND ?";
		theQuery = theSession.createQuery(theHQL);  
         
		theQuery.setParameter(0, user, Hibernate.entity(WebUser.class));
        theQuery .setParameter(1, firstDate, Hibernate.TIMESTAMP);
        theQuery.setParameter(2, curDate, Hibernate.TIMESTAMP);
        System.out.println("User=" + user.getId());
        System.out.println("days=" + days);
        System.out.println("firstDate=" + firstDate);
        System.out.println("curDate=" + curDate);
        return ((Integer) theQuery.uniqueResult()).intValue();
    }

   /* public static List<WebUser> findUsersByRole(long roleId) throws HibernateException {
        return session.find("SELECT user from Role as role LEFT JOIN role.users user where role.id=? AND user.status<>'D'", new Long(roleId), Hibernate.LONG);
    }*/

    public List getUsersByIdList(Object[] userIds)
            throws HibernateException {
        if (userIds == null || userIds.length == 0) {
            return new ArrayList();
        }
        Session theSession = sessionFactory.getCurrentSession();
        Query q = theSession.createQuery("FROM WebUser user WHERE user.id in (:userIdList)");
        q.setParameterList("userIdList", userIds);
        return q.list();
    }

    public WebUser getUserByFirstNameAndLastName(String firstName, String lastName) throws HibernateException {
        
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
  	    firstName = firstName.toUpperCase();
  	    lastName = lastName.toUpperCase();
  	    theHQL = "FROM WebUser as user WHERE " + "upper(user.firstName)=" + firstName + "% AND upper(user.lastName)=" + lastName + "%";
		theQuery = theSession.createQuery(theHQL);    	
       
        return theQuery.list().isEmpty() ? null : (WebUser) theQuery.list().get(0);
        
    }

    public List getUsersLikeFirstNameAndLastName(Session session, String firstName, String lastName) throws HibernateException {
    	String theHQL = "";
        Query theQuery = null;          
        
  	    Session theSession = sessionFactory.getCurrentSession();
  	    firstName = firstName.toUpperCase();
  	    lastName = lastName.toUpperCase();
  	    theHQL = "FROM WebUser as user WHERE " + "upper(user.firstName)=" + firstName + "% AND upper(user.lastName)=" + lastName + "%";
		theQuery = theSession.createQuery(theHQL);    	
       
        return theQuery.list() ;
    }


    /**
     * Returns true if the user has one of these roles.
     */
    public boolean checkRoles(WebUser user, long[] roles) throws Exception {
     /*   if (user == null) {
            return false;
        } else if (roles == null) {
            return true;
        }
        Set roleSet = user.getRoles();
        for (Iterator iterator = roleSet.iterator(); iterator.hasNext();) {
            Role role = (Role) iterator.next();
            long roleID = role.getId().longValue();
            for (int j = 0; j < roles.length; j++) {
                if (roleID == roles[j]) {
                    return true;
                }
            }
        }*/
        return false;
    }

    public WebUser findUserByLogin( String username, String password)
            throws HibernateException {
    	Query theQuery = null;    
    	Session theSession = sessionFactory.getCurrentSession();
        username = username.toUpperCase();        
        theQuery = theSession.createQuery("FROM WebUser AS user WHERE UPPER(user.username)="  + username + " AND user.password=" + password + " AND user.status ='A'");
        //List finds = session.find("FROM User AS user WHERE UPPER(user.username)=? AND user.password= BINARY ?  ", params, types);

        return theQuery.list().isEmpty() ? null : (WebUser) theQuery.list().get(0);
    }
    
    
    public Date findUserLastAccess(WebUser user)
    {
    	 
    	Query theQuery = null;    
    	Session theSession = sessionFactory.getCurrentSession();
     
    	theQuery = theSession.createQuery("FROM Login as l where l.user=? ORDER BY loginDate DESC");
    	theQuery.setParameter(0, user, Hibernate.entity(WebUser.class));
    	if ( theQuery.list().isEmpty() )
    		return null;
    	else
    	{
    		Login l = (Login)theQuery.list().get(0);
    		return l.getLoginDate();
    	}
    	 
    
    }

    public UserSession findUserBySessionId(String sessionId)
            throws HibernateException {
    	
    	Query theQuery = null;    
    	Session theSession = sessionFactory.getCurrentSession();
                
        theQuery = theSession.createQuery("FROM UserSession AS us WHERE us.sessionId=?" + sessionId);
        

        return theQuery.list().isEmpty() ? null : (UserSession) theQuery.list().get(0);
    	
    	 
    }
    
    
    public void save(Object obj)
         throws HibernateException {
    	Session theSession = sessionFactory.getCurrentSession();
    	theSession.save(obj);	 
 
      }
    
    public void update(Object obj)
    throws HibernateException {
	Session theSession = sessionFactory.getCurrentSession();
	theSession.update(obj);	 

    }
      
   
   public boolean isAcceptAgreement(WebUser u)
   {	    
	   if (u != null && u.getStatus().getStatus() == Constants.STATUS_ACTIVE )
		   return true;
	   else
		   return false;
   }
   
   
   public void acceptAgreement(String loginName)
   {
	   WebUser u = this.findByUsername(loginName);
	   if( u != null )
	   {
		   u.setAcceptDate(Util.getNow());
	      
	       u.setStatus(new Status(Constants.STATUS_ACTIVE));
	       update(u);
	   }
   
   }

}
