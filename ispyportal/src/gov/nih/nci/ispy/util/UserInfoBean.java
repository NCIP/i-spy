/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Collection;

import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserInfoBean {
	private SessionFactory sessionFactory;

		private ArrayList<UserInfo> users = new ArrayList<UserInfo>();
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

		public ArrayList <UserInfo> getUsers(String userID){

			String theHQL = "";
			Query theQuery = null;
			Collection objs = null;
			Long user_ID;

			Session theSession = this.sessionFactory.getCurrentSession();

			theHQL = "select userId from UserInfo where loginName = '" + userID + "'";
			theQuery = theSession.createQuery(theHQL);
			user_ID = (Long) theQuery.uniqueResult();
			
			theHQL = "";
			theQuery = null;
			
			// GET All users from the system exclude session owner
			
			theHQL = "from UserInfo WHERE userId != " + user_ID + " order by lastName";
			theQuery = theSession.createQuery(theHQL);
			System.out.println("HQL: " + theHQL);
			objs = theQuery.list();
			
			ArrayList<UserInfo> users = new ArrayList<UserInfo>(objs);
//	        this.users = new ArrayList<UserInfo>(objs);
			return users;
		}
		
		public UserInfo getUsersByID(Long userID){

			String theHQL = "";
			Query theQuery = null;
			Collection objs = null;
			UserInfo user = new UserInfo();
			Session theSession = this.sessionFactory.getCurrentSession();
			
			theHQL = "from UserInfo WHERE userId = " + userID + " order by lastName";
			theQuery = theSession.createQuery(theHQL);
			System.out.println("HQL: " + theHQL);
			objs = theQuery.list();
			user = (UserInfo) theQuery.uniqueResult();
			return user;
		}
		
		public Long getUidByID(String userID){

			String theHQL = "";
			Query theQuery = null;
			Long user;
			Session theSession = this.sessionFactory.getCurrentSession();
			
			theHQL = "select userId from UserInfo WHERE loginName = '" + userID+"'";
			theQuery = theSession.createQuery(theHQL);
			System.out.println("HQL: " + theHQL);
			user = (Long) theQuery.uniqueResult();
			return user;
		}

}
