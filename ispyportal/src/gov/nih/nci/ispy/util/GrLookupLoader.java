package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.domain.annotation.protein.bean.ProteinBiomarker;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;
import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.mes.userList;


public class GrLookupLoader {

	static private Map<String, Collection> lookupMap = new HashMap<String, Collection>();
	// This is the Hibernate Session factory that is injected by Spring
	private SessionFactory sessionFactory;
/*
	private HibernateTransactionManager txManager = null;

	public void setTxManager(HibernateTransactionManager txManager) {
		this.txManager = txManager;
	}
*/
	private ArrayList<GroupManager> groups = new ArrayList<GroupManager>();
	/**
	 * @return Returns the sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            The sessionFactory to set.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	


	/**
	 * 
	 * @return
	 */
//	public Map<String, Collection> getMap(String userID) {
	public Collection <GroupManager> getMap(Long userID, ArrayList<User> allUsers){
		

		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
//		Collection tempObjs = null;
		Long user_ID;
		UserInfo getUid = new UserInfo();
        String lName = "";
        String fName = "";
		Set<GroupMembers> members = new HashSet<GroupMembers>();
				

		Session theSession = sessionFactory.getCurrentSession();
		// Get user id by login name
/*		
		theHQL = "select userId from UserInfo where loginName = '" + userID + "'";
		theQuery = theSession.createQuery(theHQL);
		user_ID = (Long) theQuery.uniqueResult();
		
		theHQL = "";
		theQuery = null;
*/		
		
		// GET GROUPS
		theHQL = "from GroupManager WHERE ownId = " + userID;
//		theHQL = "from GroupManager A, UserInfo B where A.ownId = B.userId and B.loginName = '" + userID +"'";
//		theHQL = "select A.grName from GroupManager A, UserInfo B where A.ownId = B.userId and B.loginName = '" + userID +"'";
//		select distinct loe.stainLocalization from LevelOfExpressionIHCFinding loe where loe.stainLocalization!=null
//		theHQL = "select A.grId as groupID, A.ownId as ownID, A.grName as groupName, A.grStatus as groupStatus, A.shareStatus as shareStatus, A.updateTime as updateTime from GroupManager A, UserInfo B where A.ownId = B.userId and B.loginName = '" + userID +"'";
//		theHQL = "select grId as groupID, ownId as ownID, grName as groupName, grStatus as groupStatus, shareStatus as shareStatus, updateTime as updateTime, members as groupMembers from GroupManager WHERE ownId = " + user_ID;
		theQuery = theSession.createQuery(theHQL);
		System.out.println("HQL: " + theHQL);
		objs = theQuery.list();
		
//		ArrayList<GroupManager> groups = new ArrayList<GroupManager>(objs);
        this.groups = new ArrayList<GroupManager>(objs);
		
        for(int i=0; i<groups.size();i++){
            GroupManager item = new GroupManager();
//          ArrayList<String> items = new ArrayList<String>(groups.get(i));
//          System.out.println(groups.get(i).getClass().getName());
            item = (GroupManager) groups.get(i);
//            add(item);
            ArrayList<GroupMembers> newMembers = new ArrayList<GroupMembers>(item.getMembers());
            for(int j=0;j<newMembers.size();j++){
                    GroupMembers itemJ = new GroupMembers();
                    itemJ = (GroupMembers) newMembers.get(j);
                    User userPrcs = new User();
                    for(User prcsU: allUsers){
                    	if(prcsU.getUserId().equals(itemJ.getUsrId())){
                             userPrcs = prcsU;
                             break;
                    	}
                    }
//                    getUid = itemJ.getUserInfo();
                    lName = userPrcs.getLastName();
                    fName = userPrcs.getFirstName();
                    itemJ.setLastName(lName);
                    itemJ.setFirstName(fName);
                    System.out.println("members name |" +lName + "|" +fName);
            }
//          System.out.println(groups.get(i).getClass().getName());
            System.out.println(item.getGrName());
    }


		return groups;
//		return lookupMap;
	}

}
