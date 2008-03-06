package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserGroupBean {
	
	private SessionFactory sessionFactory;

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
		

		public ArrayList <GroupManager> getMap(Long userID){

			String theHQL = "";
			Query theQuery = null;
			Collection objs = null;
			Long user_ID;
			
			Session theSession = this.sessionFactory.getCurrentSession();
/* 		
			// Get user id by login name
			theHQL = "select userId from UserInfo where loginName = '" + userID + "'";
			theQuery = theSession.createQuery(theHQL);
			user_ID = (Long) theQuery.uniqueResult();
			
			theHQL = "";
			theQuery = null;
*/		
			// GET GROUPS
			theHQL = "from GroupManager WHERE ownId = " + userID + " order by grName";
			theQuery = theSession.createQuery(theHQL);
			System.out.println("HQL: " + theHQL);
			objs = theQuery.list();
			
			ArrayList<GroupManager> groups = new ArrayList<GroupManager>();
			groups.addAll(objs);

			return groups;

		}
}
