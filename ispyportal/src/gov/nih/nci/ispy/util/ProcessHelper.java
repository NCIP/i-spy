/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.servlet.http.HttpSession;
import uk.ltd.getahead.dwr.ExecutionContext;

public class ProcessHelper {
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
	
	
	public Long getLoginNameCurrent(){
/*		
		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long user_ID;
		
		HttpSession session = ExecutionContext.get().getSession(false);
		String userID = (String) session.getAttribute("name");

		
		Session theSession = this.sessionFactory.getCurrentSession();
		
		// Get user id by login name
		theHQL = "select userId from UserInfo where loginName = '" + userID + "'";
		theQuery = theSession.createQuery(theHQL);
		user_ID = (Long) theQuery.uniqueResult();
        return user_ID;
*/
		HttpSession session = ExecutionContext.get().getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        return currentUser.getUserId();
	}

}
