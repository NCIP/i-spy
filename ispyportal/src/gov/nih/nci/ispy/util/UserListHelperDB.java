package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import gov.columbia.c2b2.ispy.list.UserListN;
import gov.columbia.c2b2.ispy.list.ListItem;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;
//import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.columbia.c2b2.ispy.list.ShareList;
import gov.nih.nci.ispy.util.LoadUserInfo;

import gov.nih.nci.ispy.util.ProcessHelper;
import gov.columbia.c2b2.ispy.list.UserListBean;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import uk.ltd.getahead.dwr.ExecutionContext;

public class UserListHelperDB {
    private HttpSession session;
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
	
	public UserListN getListByID(Long listID) {
		String theHQL = "";
		Query theQuery = null;
		UserListN list = new UserListN();

		Session theSession = this.sessionFactory.getCurrentSession();
		theHQL = "from UserListN where id = " +listID;
		theQuery = theSession.createQuery(theHQL);
		list = (UserListN) theQuery.uniqueResult();
		return list;
	}
	
	public void dataBasePrcs(UserListN userList){

		Session theSession = this.sessionFactory.getCurrentSession();	
        session = ExecutionContext.get().getSession(false);
//        LoadUserInfo loadInfo = new LoadUserInfo();
		Transaction tx = theSession.beginTransaction();
//		String userID = (String) session.getAttribute("name");
//		UserInfo ui = loadInfo.getUserInfo(userID);
		
		User ui = (User) session.getAttribute("currentUser");

//		userList.setUserId(ui.getUserId());
		theSession.saveOrUpdate(userList);
		Long listID = userList.getId();
		
		userList.setAuthor(ui.getLastName() + ", " + ui.getFirstName());
		userList.setInstitute(ui.getOrganization());
		userList.setShareStatus('N');
		ArrayList<ListItem> setID = new ArrayList<ListItem>(userList.getListItemsT());
//		ArrayList<ListItem> setID = new ArrayList<ListItem>(userList.getListItems());
		for(ListItem list : setID){
			list.setListId(listID);
			list.setUpdateDate(new Timestamp(new Date().getTime()));
//			theSession.saveOrUpdate(list);
		}
//		theSession.saveOrUpdate(userList);

		theSession.flush();
		tx.commit();
	}

	public void dataBasePrcsS(UserListN userList, HttpSession session){

		Session theSession = this.sessionFactory.getCurrentSession();
		UserListBean listNew = new UserListBean();
//        session = ExecutionContext.get().getSession(false);
//        LoadUserInfo loadInfo = new LoadUserInfo();
		Transaction tx = theSession.beginTransaction();
//		String userID = (String) session.getAttribute("name");
//		UserInfo ui = loadInfo.getUserInfo(userID);
		User ui = (User) session.getAttribute("currentUser");
		userList.setUserId(ui.getUserId());
		theSession.saveOrUpdate(userList);
		Long listID = userList.getId();
		
		userList.setAuthor(ui.getLastName() + ", " + ui.getFirstName());
		userList.setInstitute(ui.getOrganization());
		userList.setShareStatus('N');
		ArrayList<ListItem> setID = new ArrayList<ListItem>(userList.getListItemsT());
//		ArrayList<ListItem> setID = new ArrayList<ListItem>(userList.getListItems());
		for(ListItem list : setID){
			list.setListId(listID);
			list.setUpdateDate(new Timestamp(new Date().getTime()));
//			theSession.saveOrUpdate(list);
		}
//		listNew.addList(userList);
		theSession.saveOrUpdate(userList);

		theSession.flush();
		tx.commit();
	}
	
	
	public void dataBasePrcsDel(UserListN userList){
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();
		theSession.delete(userList);
		theSession.flush();
		tx.commit();
	}
	
	public void addNewShares(Long listID, List<ShareList> newShares){
	
		String theHQL = "";
		Query theQuery = null;
        UserListN tempList = new UserListN();
        Set<ShareList> shares = new HashSet<ShareList>();
//        shares.addAll(newShares);
        
        theHQL = "from UserListN where id = " + listID;      
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();
		theQuery = theSession.createQuery(theHQL);
		tempList = (UserListN) theQuery.uniqueResult();
		shares.addAll(tempList.getListShares());
		shares.addAll(newShares);
		tempList.setListShares(shares);
//		tempList = (UserListN) theSession.load(UserListN.class, listID);

		for(ShareList list: newShares){
			theSession.saveOrUpdate(list);
			theSession.flush();
			tx.commit();
		}
		theSession.saveOrUpdate(tempList);
		theSession.flush();
		tx.commit();
/*		
		groupId = StringUtils.join(groupIds, ',');
		theHQL = "delete from ShareList where listId =" + listID + " and grId in ("+groupId+")";      
			Session theSession = this.sessionFactory.getCurrentSession();	
			Transaction tx = theSession.beginTransaction();
			theQuery = theSession.createQuery(theHQL);
			tempList = (UserListN) theQuery.uniqueResult();
*/
	}
	
	public void deleteShares(Long listID, String[] groupIds){
		String theHQL = "";
		Query theQuery = null;
		String groupId = "";

		// Get user id by login name
		groupId = StringUtils.join(groupIds, ',');
		theHQL = "delete from ShareList where listId =" + listID + " and grId in ("+groupId+")";
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();
		theQuery = theSession.createQuery(theHQL);
		int res = theQuery.executeUpdate();
		
		theSession.flush();
		tx.commit();
	}
	
	public void deleteAllSharesByGroup(Long groupId){
		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Set<ShareList> sharePrcs = new HashSet<ShareList>();
		
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();

		theHQL = "select listId from ShareList where grId =" + groupId;
		theQuery = theSession.createQuery(theHQL);
		objs = theQuery.list();

		List<Long> listTemp = new ArrayList<Long>();
		listTemp.addAll(objs);
		if(listTemp.size()>0){
		theHQL = "from UserListN where id in (";
		for(Long list: listTemp){
			theHQL += list+",";
		}
		theHQL = theHQL.substring(0, (theHQL.length() -1));
		theHQL += ")";
		theQuery = theSession.createQuery(theHQL);
		objs = theQuery.list();
		List<UserListN> listToPrcs = new ArrayList<UserListN>(objs);
		for(UserListN list: listToPrcs){
			sharePrcs = list.getListShares();
			for(ShareList share: sharePrcs){
				if(share.getGrId().equals(groupId)){
					sharePrcs.remove(share);
					theSession.save(list);
					theSession.delete(share);
				}
			}
		}
		
		
		
		theSession.flush();
		tx.commit();
		}
		
	}
	
	public void dataBasePrcsDelItem(Long itemID){
		String theHQL = "";
		Query theQuery = null;

		// Get user id by login name
		theHQL = "delete from ListItem where id =" + itemID;
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();
		theQuery = theSession.createQuery(theHQL);
		int res = theQuery.executeUpdate();
		
		theSession.flush();
		tx.commit();		
	}
	
	public void dataBasePrcsDelItemN(UserListN list){
		Session theSession = this.sessionFactory.getCurrentSession();	
		Transaction tx = theSession.beginTransaction();
		theSession.saveOrUpdate(list);
		theSession.flush();
		tx.commit();
	}
	
	public Integer dataBasePrcsDelItemS(Long listId, Long itemId) {
		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long memberId;
		ListItem itemD = new ListItem();
		UserListN listD = new UserListN();
		Long tempId;
		Long memID;
		Integer res = 0;

		Session theSession = this.sessionFactory.getCurrentSession();

		Transaction tx = theSession.beginTransaction();

		theHQL = "from UserListN where id = " + listId;
		theQuery = theSession.createQuery(theHQL);
		objs = theQuery.list();
		listD = (UserListN) theSession.load(UserListN.class, listId);
		ArrayList<ListItem> items = new ArrayList<ListItem>(listD
				.getListItems());
		ArrayList<ListItem> itemsO = new ArrayList<ListItem>(listD
				.getListItemsT());
		if (items.size() > 0) {
			for (ListItem itemPrcs : items) {
				tempId = itemPrcs.getId();
					if (tempId.equals(itemId)) {
						theSession.delete(itemPrcs);
						listD.getListItems().remove(itemPrcs);
						theSession.save(listD);
						res = 0;
						break;
					} else res = 1;				
			}
		}
		
		if (itemsO.size() > 0) {
			for (ListItem itemPrcs : itemsO) {
				tempId = itemPrcs.getId();
					if (tempId.equals(itemId)) {
						theSession.delete(itemPrcs);
						listD.getListItemsT().remove(itemPrcs);
						theSession.save(listD);
						res = 0;
						break;
					} else res = 1;				
			}
		}

		theSession.flush();
		theSession.clear();
		tx.commit();
		return res;
	}
}
