/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Collection;

import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.columbia.c2b2.ispy.list.UserListN;
import gov.columbia.c2b2.ispy.info.LogInfo;
import gov.columbia.c2b2.ispy.web.ajax.CommonListFunctions;
import gov.nih.nci.ispy.util.UserGroupHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LogInfoBean {

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
	
	public ArrayList <LogInfo> getLogs(Long userID){

		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long user_ID;
		LogInfo tempLog = new LogInfo();
		User ui = new User();
		GroupManager gr = new GroupManager();
//		UserListN list = new UserListN();
		UserListN list = null;
		UserGroupHelper getGr = new UserGroupHelper();
		UserListHelperDB getList = new UserListHelperDB();
		

		Session theSession = this.sessionFactory.getCurrentSession();
		Transaction tx = theSession.beginTransaction();
/*		
		theHQL = "select userId from UserInfo where loginName = '" + userID + "'";
		theQuery = theSession.createQuery(theHQL);
		user_ID = (Long) theQuery.uniqueResult();
		
		theHQL = "";
		theQuery = null;
*/		
		// GET All users from the system exclude session owner
		
		theHQL = "from LogInfo WHERE refId = " + userID + " and actionStatus = 'N'";
		theQuery = theSession.createQuery(theHQL);
		System.out.println("HQL: " + theHQL);
		objs = theQuery.list();
		ArrayList<LogInfo> logs = new ArrayList<LogInfo>(objs);
/* need to be uncommented after testing */	

		for(LogInfo logPrcs : logs){
			user_ID = logPrcs.getUsrId();
			ui = CommonListFunctions.getUserInfoByID(user_ID);
			logPrcs.setUserName(ui.getLastName()+", "+ui.getFirstName());
			if(logPrcs.getUserAction().equalsIgnoreCase("GRADD") || logPrcs.getUserAction().equalsIgnoreCase("GRDEL")){
				theHQL = "from GroupManager where grId = " +logPrcs.getActionRef();
				theQuery = theSession.createQuery(theHQL);
				gr = (GroupManager) theQuery.uniqueResult();
				if(gr == null){
					logPrcs.setGroupName("The Group deleted");
					
				} else logPrcs.setGroupName(gr.getGrName());
			} else if(logPrcs.getUserAction().equalsIgnoreCase("SHADD")){
				theHQL = "from UserListN where id = " +logPrcs.getActionRef();
				theQuery = theSession.createQuery(theHQL);
				list = (UserListN) theQuery.uniqueResult();			
//				list = getList.getListByID(logPrcs.getActionRef());
				if(list != null){
				logPrcs.setListName(list.getName());
				}
			}

			tempLog = (LogInfo) theSession.load(LogInfo.class, logPrcs.getRecId());
			tempLog.setActionStatus('A');
			theSession.saveOrUpdate(tempLog);

		}
		return logs;

	}
}
