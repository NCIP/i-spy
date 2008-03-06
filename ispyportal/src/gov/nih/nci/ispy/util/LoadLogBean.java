package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Collection;

import gov.columbia.c2b2.ispy.fileLoad.LoadLog;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LoadLogBean {

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

	public ArrayList <LoadLog> getLoadLog(){

		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long user_ID;

		Session theSession = this.sessionFactory.getCurrentSession();
		
		theHQL = "from LoadLog order by updateDate desc";
		theQuery = theSession.createQuery(theHQL);
		System.out.println("HQL: " + theHQL);
		objs = theQuery.list();
		
		ArrayList<LoadLog> logs = new ArrayList<LoadLog>(objs);
//        this.users = new ArrayList<UserInfo>(objs);
		return logs;
	}
	
	public void removeRecords(String[] recID) {
		String theHQL = "";
		Query theQuery = null;

		Session theSession = this.sessionFactory.getCurrentSession();
		Transaction tx = theSession.beginTransaction();
		// Transaction tx = theSession.beginTransaction();
		if(recID.length>0){
			theHQL = "delete from LoadLog where recId in (";
			for(int i=0;i<recID.length;i++){
				theHQL += recID[i] +", ";
			}
			theHQL = theHQL.substring(0, (theHQL.length() -2)) +")";
			theQuery = theSession.createQuery(theHQL);
			theQuery.executeUpdate();
			theSession.flush();
			tx.commit();
		}

	}
	
	public void insertLogRec(LoadLog entry) {
		String theHQL = "";
		Query theQuery = null;

		Session theSession = this.sessionFactory.getCurrentSession();
		Transaction tx = theSession.beginTransaction();
	        theSession.save(entry);
			theSession.flush();
			tx.commit();
		}
	
}
