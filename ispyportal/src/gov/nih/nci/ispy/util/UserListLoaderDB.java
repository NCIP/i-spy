/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;


import gov.columbia.c2b2.ispy.list.UserListBean;
import gov.columbia.c2b2.ispy.list.UserListN;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserListLoaderDB{
	   private static Logger logger = Logger.getLogger(UserListLoaderDB.class);
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
 
    public UserListLoaderDB() {
        super();
        // TODO Auto-generated constructor stub
    }
    
//    public  UserListBean loadUserListFromDB(UserListBean userListBean, String userId){
    public  UserListBean loadUserListFromDB(Long userId){
 //   public void loadUserListFromDB(){
		String theHQL = "";
		Query theQuery = null;
		Long user_ID;
//		List<UserListN> listN = new ArrayList<UserListN>();
		UserListBean userListBean = new UserListBean();
		Session theSession = this.sessionFactory.getCurrentSession();
/*
		// Get user id by login name
		theHQL = "select userId from UserInfo where loginName = '" + userId + "'";
		theQuery = theSession.createQuery(theHQL);
		user_ID = (Long) theQuery.uniqueResult();
		
		theHQL = "";
		theQuery = null;
*/	
		// GET Lists from DB
        Collection<UserListN> userLists = null;

 
		
 //       theHQL = "select distinct ul from UserListN ul where ul.userId = :login"; 
		theHQL = "from UserListN WHERE userId = " + userId + "order by name";
        theQuery = theSession.createQuery(theHQL);
 //       theQuery.setParameter("login", user_ID);
        System.out.println("HQL: " + theHQL);        
        userLists = theQuery.list();

       for(UserListN list: userLists){
            logger.debug("List name: " + list.getName());
//            listN.add(list);
            userListBean.addList(list);
        }       
//       return listN;
		return userListBean; 	
    }
    
    public  UserListBean loadSharedListFromDB(Long userId){
    			String theHQL = "";
    			Query theQuery = null;
    			Long user_ID;
    			UserListBean userListBean = new UserListBean();
    			Session theSession = this.sessionFactory.getCurrentSession();
/*
    			// Get user id by login name
    			theHQL = "select userId from UserInfo where loginName = '" + userId + "'";
    			theQuery = theSession.createQuery(theHQL);
    			user_ID = (Long) theQuery.uniqueResult();
    			theHQL = "";
    			theQuery = null;
 */   			
    			theHQL = "select GrId from GroupMembers where usrId = "+userId;
    			theQuery = theSession.createQuery(theHQL);
    			Collection<Long> groupIds = null;
    			groupIds = theQuery.list();
    			if(groupIds.size()>0){
    			String idTemp = "";
    			for(Long id : groupIds){
    				idTemp +=id.toString()+",";
    			}
    			theHQL = "select listId from ShareList where grId in ("+idTemp.substring(0, (idTemp.length() - 1))+")";
    			
    			theQuery = theSession.createQuery(theHQL);
    			groupIds = null;
    			groupIds = theQuery.list();
    			if(groupIds.size()>0){
    			idTemp = "";
    			for(Long id : groupIds){
    				idTemp +=id.toString()+",";
    			}
    			theHQL = "from UserListN where id in ("+idTemp.substring(0, (idTemp.length() - 1))+") order by name";
    			
//    			String groupList = StringUtils.join(idTemp, ',');
    			
    			// GET Lists from DB
    	        Collection<UserListN> userLists = null;
    	        

    	 
    			
    	 //       theHQL = "select distinct ul from UserListN ul where ul.userId = :login"; 
    	//		theHQL = "from UserListN WHERE userId = " + user_ID + "order by name";
    	        theQuery = theSession.createQuery(theHQL);
    	 //       theQuery.setParameter("login", user_ID);
    	        System.out.println("HQL: " + theHQL);        
    	        userLists = theQuery.list();

    	       for(UserListN list: userLists){
    	            logger.debug("List name: " + list.getName());
//    	            listN.add(list);
    	            userListBean.addList(list);
    	        } 
    			}
    			}
//    	       return listN;
    			return userListBean; 	
    	    }
    
    public  UserListN getListFromDB(Long listID){

    			String theHQL = "";
    			Query theQuery = null;
    			UserListN listN = new UserListN();
    			Session theSession = this.sessionFactory.getCurrentSession();
    			theHQL = "from UserListN WHERE id = " + listID;
    	        theQuery = theSession.createQuery(theHQL);
    	        System.out.println("HQL: " + theHQL);        
    	        listN = (UserListN) theQuery.uniqueResult();

    	        return listN;
    	    }
}
