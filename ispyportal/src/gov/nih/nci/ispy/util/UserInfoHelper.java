/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.columbia.c2b2.ispy.list.ShareList;
import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;
import gov.columbia.c2b2.ispy.info.LogInfo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserInfoHelper {
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
	
	public void setLogInfo(Long userID, String[] refIds, String userAction, Long actionRef){
		ArrayList<LogInfo> newInfo = new ArrayList<LogInfo>();
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();
		
		for(int i=0;i<refIds.length;i++){
			LogInfo inf = new LogInfo();
			inf.setUsrId(userID);
			inf.setRefId(Long.parseLong(refIds[i].trim()));
			inf.setUserAction(userAction);
			inf.setActionRef(actionRef);
			inf.setActionStatus('N');
			inf.setUpdateTime(new Timestamp(new Date().getTime()));
			theSession.saveOrUpdate(inf);
//			newInfo.add(inf);
		}		
		theSession.flush();
		tx.commit();
	}
	
	public String[] getRefId(String[] membersID){

		String theHQL = "";
		Query theQuery = null;
		Long user_ID;
		String[] refIds = new String[membersID.length];
		Session theSession = this.sessionFactory.getCurrentSession();
		for(int i=0;i<membersID.length;i++){
			
			theHQL = "select usrId from GroupMembers where memberId = '" + Long.parseLong(membersID[i].trim()) + "'";
			theQuery = theSession.createQuery(theHQL);
			user_ID = (Long) theQuery.uniqueResult();
			if(user_ID.SIZE>0){
				refIds[i] = user_ID.toString();
			} else refIds[i] = "";
		}
		
		return refIds;
	}
	
	public String[] getRefIdByGr(Long grId){

		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long user_ID;
		
		Session theSession = this.sessionFactory.getCurrentSession();
		theHQL = "select usrId from GroupMembers where GrId = " + grId;
		theQuery = theSession.createQuery(theHQL);
		objs = theQuery.list();
		String[] refIds = new String[objs.size()];
		int i=0;
		for(Object temp : objs){
			refIds[i] = (String)temp.toString();
			i++;
		}
/*		
		String[] refIds = new String[objs.size()];
		for(int i=0;i<objs.size();i++){
			refIds[i] = objs[i];
		}
*/		
		
		
/*		
		
		for(int i=0;i<membersID.length;i++){
			
			theHQL = "select usrId from GroupMembers where memberId = '" + Integer.parseInt(membersID[i].trim()) + "'";
			theQuery = theSession.createQuery(theHQL);
			user_ID = (Integer) theQuery.uniqueResult();
			if(user_ID.SIZE>0){
				refIds[i] = user_ID.toString();
			} else refIds[i] = "";
		}
*/
		return refIds;
	}
}
