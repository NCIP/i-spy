package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.columbia.c2b2.ispy.fileLoad.LoadLog;
import gov.columbia.c2b2.ispy.fileLoad.LogFileContent;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LoadLogBean {

	private SessionFactory sessionFactory;

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

		Session theSession = this.sessionFactory.getCurrentSession();
		
		theHQL = "from LoadLog order by updateDate desc";
		theQuery = theSession.createQuery(theHQL);
		System.out.println("HQL: " + theHQL);
		objs = theQuery.list();
		
		ArrayList<LoadLog> logs = new ArrayList<LoadLog>(objs);
//        this.users = new ArrayList<UserInfo>(objs);
		return logs;
	}
	
	public LoadLog getLoadLogById(Long id){
		LoadLog lRec = new LoadLog();
		
		Session theSession = this.sessionFactory.getCurrentSession();
		lRec = (LoadLog) theSession.get(LoadLog.class, id);
		
		return lRec;
	}

	public LogFileContent getFilebyID(Long id){
		LogFileContent lRec = new LogFileContent();
		
		Session theSession = this.sessionFactory.getCurrentSession();
		lRec = (LogFileContent) theSession.get(LogFileContent.class, id);
		
		return lRec;
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
			
			theHQL = "delete from LogFileContent where logId in (";
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
	
	public Long insertLogRec(LoadLog entry, ArrayList<LogFileContent> files) {
		Set<LogFileContent> filesDB = new HashSet<LogFileContent>();
		Session theSession = this.sessionFactory.getCurrentSession();
		Transaction tx = theSession.beginTransaction();	
/*
	        theSession.saveOrUpdate(entry);
	        for(LogFileContent contPrcs : files){
	        	contPrcs.setLogId(entry.getRecId());
	        }
*/
	        filesDB.addAll(files);
	        entry.setFiles(filesDB);
	        theSession.saveOrUpdate(entry);
			theSession.flush();
			tx.commit();
		return entry.getRecId();	
			
		}
	
	public Long insertContentRec(LogFileContent entry) {

		Session theSession = this.sessionFactory.getCurrentSession();
		Transaction tx = theSession.beginTransaction();
	        theSession.save(entry);
			theSession.flush();
			tx.commit();
		return entry.getRecId();	
			
		}
	
}
