package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserGroupHelper {

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

	public GroupManager getGroupByID(Long groupID) {
		String theHQL = "";
		Query theQuery = null;
		GroupManager group = new GroupManager();

		Session theSession = this.sessionFactory.getCurrentSession();
		theHQL = "from GroupManager where grId = " +groupID;
		theQuery = theSession.createQuery(theHQL);
		group = (GroupManager) theQuery.uniqueResult();
		return group;
	}
	
	public void removeGroup(Long groupID) {
		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;

		Session theSession = this.sessionFactory.getCurrentSession();

		// Transaction tx = theSession.beginTransaction();
		theHQL = "from GroupManager";
		theQuery = theSession.createQuery(theHQL);
		objs = theQuery.list();
		GroupManager groupD = new GroupManager();
		groupD = (GroupManager) theSession.load(GroupManager.class, groupID);
		theSession.delete(groupD);
		theSession.flush();
		// tx.commit();
		// theSession.close();
	}

	public void removeMember(Long groupID, String[] membersID) {
		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long memberId;
		GroupMembers memberD = new GroupMembers();
		GroupManager groupD = new GroupManager();
		Long tempId;
		Long memID;

		Session theSession = this.sessionFactory.getCurrentSession();

		Transaction tx = theSession.beginTransaction();

		theHQL = "from GroupManager where grId = " + groupID;
		theQuery = theSession.createQuery(theHQL);
		objs = theQuery.list();
		groupD = (GroupManager) theSession.load(GroupManager.class, groupID);
		ArrayList<GroupMembers> members = new ArrayList<GroupMembers>(groupD
				.getMembers());
		if (members.size() > 0) {
			for (GroupMembers memberPrcs : members) {
				tempId = memberPrcs.getMemberId();
				for (int i = 0; i < membersID.length; i++) {
					memID = Long.parseLong(membersID[i].trim());
					if (tempId.equals(memID)) {
						groupD.getMembers().remove(memberPrcs);
						theSession.save(groupD);
						theSession.delete(memberPrcs);

					}
				}
			}
		}

		theSession.flush();
		tx.commit();
	}

	public Long addMembersPrcs(Long groupID, String grName, Long uID,
			String[] membersID) {
		String theHQL = "";
		Query theQuery = null;
		Collection objs = null;
		Long memberId;
		Long user_ID;
		ArrayList<GroupMembers> members = new ArrayList<GroupMembers>();
		GroupManager groupA = new GroupManager();
		Integer newFlag = 0;
		Long userID;
		Set<GroupMembers> membersAdd = new HashSet<GroupMembers>();

		// if (membersID.length > 0) {
		Session theSession = this.sessionFactory.getCurrentSession();

		Transaction tx = theSession.beginTransaction();
		if (groupID > 0) {
			theHQL = "from GroupManager where grId = " + groupID;
			theQuery = theSession.createQuery(theHQL);
			objs = theQuery.list();
			groupA = (GroupManager) theSession
					.load(GroupManager.class, groupID);
			members = new ArrayList<GroupMembers>(groupA.getMembers());
		} else {
			newFlag = 1;
/*
			theHQL = "select userId from UserInfo where loginName = '" + uID + "'";
			theQuery = theSession.createQuery(theHQL);
			user_ID = (Long) theQuery.uniqueResult();
*/			
			groupA.setOwnId(uID);
			groupA.setGrName(grName);
			groupA.setGrStatus('A');
			groupA.setShareStatus('N');
			groupA.setUpdateTime(new Timestamp(new Date().getTime()));
			theSession.saveOrUpdate(groupA);
		}
		if (membersID.length > 0) {
			for (int i = 0; i < membersID.length; i++) {
				GroupMembers memberA = new GroupMembers();
				userID = Long.parseLong(membersID[i].trim());
				if(newFlag>0){
					memberA.setGrId(groupA.getGrId());
					groupID = groupA.getGrId();
				}
				else
					memberA.setGrId(groupID);
				memberA.setUsrId(userID);
				memberA.setMemberStatus('A');
				memberA.setShareStatus('N');
				memberA.setUpdateTime(new Timestamp(new Date().getTime()));
				members.add(memberA);

			}
			membersAdd.addAll(members);
			groupA.setMembers(membersAdd);
		}
		theSession.saveOrUpdate(groupA);

		theSession.flush();
		tx.commit();
		return groupID;
		// }
	}
}
