/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.util;

 
import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.logging.api.logger.util.ApplicationProperties;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.ObjectPrivilegeMap;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.Privilege;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroupRoleContext;
import gov.nih.nci.security.authorization.domainobjects.Role;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.dao.SearchCriteria;
import gov.nih.nci.security.dao.UserSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.junk.RandomIntGenerator;
import gov.nih.nci.security.util.ObjectSetUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;



/**
 * 
 * 
 * @author Vinay Kumar (Ekagra Software Technologies Ltd.)
 */
public class TestClient {
	static UserProvisioningManager upm = null;
	static AuthorizationManager am = null;
	static AuthenticationManager am1 = null;
	
	public TestClient()
	{
		try{
		am1 = SecurityServiceProvider.getAuthenticationManager("ispy");
		upm = SecurityServiceProvider.getUserProvisioningManager("ispy");
		am = SecurityServiceProvider.getAuthorizationManager("ispy");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void testPrivilegeCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		Privilege p1 = new Privilege();
		p1.setName("Update");
		p1.setDesc("Update Access");
		Privilege p2 = new Privilege();
		p2.setName("Read");
		p2.setDesc("Read Access");
		Privilege p3 = new Privilege();
		p3.setName("Create");
		p3.setDesc("Create Access");
		Privilege p4 = new Privilege();
		p4.setName("Delete");
		p4.setDesc("Update Access");
		try{
			upm.createPrivilege(p1);
			upm.createPrivilege(p2);
			upm.createPrivilege(p3);
			upm.createPrivilege(p4);
			//System.out.println("The returned id is:"+p.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void testGetProtectionElements(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			java.util.Set pes = upm.getProtectionElements("7");
			System.out.println(pes.size());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void getProtectionGroups(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			 upm.getProtectionGroups();
			//System.out.println(pes.size());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void testPrivilegeDelete(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			upm.removePrivilege("1");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void getProtectionGroupById(String id){
		
		
		try{
			ProtectionGroup child = upm.getProtectionGroupById(id);
//			ProtectionGroup parent = child.getParentProtectionGroup();
			System.out.println(" >>>>>>>>>>>>> " + child.getProtectionGroupId());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	public void testPrivilegeFind(){
		UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			Privilege p = upm.getPrivilege("Read");
			System.out.println("The returned Name:"+p.getName());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	*/
	
	public void testModifyCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			//Privilege p = upm.getPrivilege("2");
			Privilege p = new Privilege();
			p.setName("Create");
			p.setDesc("Create Access");
			upm.modifyPrivilege(p);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void testRoleCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			//for(int i=1;i<11;i++){
				Role r = new Role();
				r.setName("Admin1234");
				r.setDesc("Admin role 1234 desc");
				Byte b = new Byte("1");
				r.setActive_flag(b.byteValue());
				upm.createRole(r);
			//}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testRoleDelete(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			upm.removeRole("2");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void getPrincipals(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			am.getPrincipals("  ");
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void secureCollection(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			am.secureCollection("  ",new ArrayList());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void getPrivilegeMap1(){
		// just object id
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			ArrayList al = new ArrayList();
			ProtectionElement pe1 = new ProtectionElement();
			pe1.setObjectId("TestElement1");
			//pe1.setAttribute("TestElement1");
			al.add(pe1);
			Collection map = am.getPrivilegeMap("testcaseuser1",al);
			Iterator it = map.iterator();
			while (it.hasNext())
			{
				ObjectPrivilegeMap opm = (ObjectPrivilegeMap)it.next();
				ProtectionElement pe = opm.getProtectionElement();
				System.out.println("The Protection Element is : " + pe.getObjectId()+" : " + pe.getAttribute());
				Collection privList = opm.getPrivileges();
				Iterator it2 = privList.iterator();
				while (it2.hasNext())
				{
					Privilege priv = (Privilege)it2.next();
					System.out.println("The Privilege is : " + priv.getName());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void getPrivilegeMap2(){
		// both object id and attribute
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			ArrayList al = new ArrayList();
			ProtectionElement pe1 = new ProtectionElement();
			pe1.setObjectId("TestElement2");
			pe1.setAttribute("TestElement2");
			al.add(pe1);
			Collection map = am.getPrivilegeMap("testcaseuser2",al);
			Iterator it = map.iterator();
			while (it.hasNext())
			{
				ObjectPrivilegeMap opm = (ObjectPrivilegeMap)it.next();
				ProtectionElement pe = opm.getProtectionElement();
				System.out.println("The Protection Element is : " + pe.getObjectId()+" : " + pe.getAttribute());
				Collection privList = opm.getPrivileges();
				Iterator it2 = privList.iterator();
				while (it2.hasNext())
				{
					Privilege priv = (Privilege)it2.next();
					System.out.println("The Privilege is : " + priv.getName());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void getPrivilegeMap3(){
		// thru group
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			ArrayList al = new ArrayList();
			ProtectionElement pe1 = new ProtectionElement();
			pe1.setObjectId("TestElement3");
			al.add(pe1);
			Collection map = am.getPrivilegeMap("testcaseuser3",al);
			Iterator it = map.iterator();
			while (it.hasNext())
			{
				ObjectPrivilegeMap opm = (ObjectPrivilegeMap)it.next();
				ProtectionElement pe = opm.getProtectionElement();
				System.out.println("The Protection Element is : " + pe.getObjectId()+" : " + pe.getAttribute());
				Collection privList = opm.getPrivileges();
				Iterator it2 = privList.iterator();
				while (it2.hasNext())
				{
					Privilege priv = (Privilege)it2.next();
					System.out.println("The Privilege is : " + priv.getName());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void getPrivilegeMap4(){
		// Wrong info
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			ArrayList al = new ArrayList();
			ProtectionElement pe1 = new ProtectionElement();
			pe1.setObjectId("NoSuchElement");
			al.add(pe1);
			Collection map = am.getPrivilegeMap("testcaseuser1",al);
			Iterator it = map.iterator();
			while (it.hasNext())
			{
				ObjectPrivilegeMap opm = (ObjectPrivilegeMap)it.next();
				ProtectionElement pe = opm.getProtectionElement();
				System.out.println("The Protection Element is : " + pe.getObjectId()+" : " + pe.getAttribute());
				Collection privList = opm.getPrivileges();
				Iterator it2 = privList.iterator();
				while (it2.hasNext())
				{
					Privilege priv = (Privilege)it2.next();
					System.out.println("The Privilege is : " + priv.getName());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testUserDelete(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			upm.removeUser("87");
			upm.removeUser("88");
			upm.removeUser("89");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testModifyRole(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			Role r = upm.getRoleById("3");
			r.setDesc("Updated Test Role Desc 2");
			upm.modifyRole(r);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void testGetProtectionGroupById(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		try{
			ProtectionElement pe = upm.getProtectionElementById("20044");
			System.out.println("Protection Element" + pe.getProtectionElementName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	public void assignPrivilegeToRoles(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		//String[] privilegeIds = {"1", "2","3"};
		//String[] privilegeIds = {"1","2"};
		String[] privilegeIds = {"1","2"};
		//String[] privilegeIds = {};
		String roleId = "1";
		try{
			upm.assignPrivilegesToRole(roleId,privilegeIds);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void assignToProtectionGroups(){
		
		String[] protectionGroupIds = {"80","81","87","93"};
		
		String protectionElementId = "19";
		try{
			upm.assignToProtectionGroups(protectionElementId,protectionGroupIds);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void getPrivileges(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("security");
		
		String roleId = "1";
		try{
			java.util.Collection result = upm.getPrivileges(roleId);
			Iterator it = result.iterator();
			while(it.hasNext()){
				Privilege p = (Privilege)it.next();
				System.out.println(p.getId().toString()+":"+p.getName()+":"+p.getDesc());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testGroupCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			for(int i=1;i<4;i++){
				Group grp = new Group();
				grp.setGroupName("Group_Name_"+i);
				grp.setGroupDesc("Group_Desc_"+i);
				upm.createGroup(grp);
			}
			 
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void testUserCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{

				User user = new User();
				user.setLoginName("mintest1");
				user.setFirstName("Min");
				user.setLastName("You");
				user.setDepartment("c2b2");
				user.setPassword("mintest1");
				user.setEmailId("my2248@c2b2.columbia.edu");
				user.setOrganization("c2b2");
				
				upm.createUser(user);
				System.out.println("The returned id is "+user.getUserId());
				System.out.println("The user Name is "+user.getLoginName());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	public void getUser(String userName){
		
		String loginName = userName;
		try{
			User user = upm.getUser(loginName);
			System.out.println(user.getFirstName()+":"+user.getLastName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testProtectionGroupCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			for(int i=1;i<101;i++){
				ProtectionGroup pg = new ProtectionGroup();
				pg.setProtectionGroupName("protection_group_name_="+i);
				pg.setProtectionGroupDescription("PG_Desc_"+i);
				upm.createProtectionGroup(pg);
				System.out.println("The returned id is"+pg.getProtectionGroupId());
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	public void testProtectionGroupModify(){
		UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			ProtectionGroup pg = upm.getProtectionGroup(new Long("2"));
			ProtectionGroup pg1 = upm.getProtectionGroup(new Long("50"));
			pg1.setParentProtectionGroup(pg);
			upm.modifyProtectionGroup(pg1);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	*/
	public void testProtectionElementCreate(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			/**
			for(int i=1;i<100000;i++){
				ProtectionElement pe = new ProtectionElement();
				pe.setProtectionElementName("PE_Name_"+i);
				pe.setObjectId("X_Y_Z_"+i);
				pe.setProtectionElementDescription("PE_Desc"+i);
				
				upm.createProtectionElement(pe);
				System.out.println("The returned id is"+pe.getProtectionElementId());
			}
			*/
			ProtectionElement pe = new ProtectionElement();
			pe.setProtectionElementId(new Long(20));
			upm.createProtectionElement(pe);
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error:"+ex.getMessage());
		}
	}
	
	public void getProtectionElement(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			
			ProtectionElement pe = upm.getProtectionElement("X_Y_Z_9");
			System.out.println("The name is"+pe.getProtectionElementName());
			//pe = upm.getProtectionElement(new Long("15"));
			System.out.println("The name is"+pe.getProtectionElementName());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	public void addUserToGroup(){
		UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			
			upm.addUserToGroup("2","15");
			upm.addUserToGroup("34","15");
			upm.addUserToGroup("33","15");
			upm.addUserToGroup("26","15");
			upm.addUserToGroup("2","16");
			upm.addUserToGroup("2","445");
			upm.addUserToGroup("5","45");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	*/
	public void removeUserFromGroup(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			
			//upm.addUserToGroup("2","15");
			upm.removeUserFromGroup("2","15");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void assignUserToGroup(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			String[] gl = new String[1];
			gl[0] = "0";
			upm.assignUserToGroup("RBTuser", "SUPER_USER");
			//upm.assignUserToGroup("RBTuser", "SUPER_USER");
			//upm.removeUserFromGroup("2","15");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void assignProtectioElement(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			String[] peIds = {"22","33","44","55"};
			String pgId = "3";
			//upm.assignProtectionElements(pgId,peIds);
			AuthorizationManager am = (AuthorizationManager)upm;
			am.assignProtectionElement("protection_group_name_=1","45 ","yrh");
			
		}catch(Exception ex){
			System.out.println("Error:"+ex.getMessage());
		}
	}
	
	public void assignUserRoleToProtectionGroup(){
		//UserProvisioningManager upm = SecurityServiceProvider.getUserProvisioningManger("Security");
		
		try{
			//String[] peIds = {"10","12","13","15"};
			//String pgId = "10";
			//upm.assignProtectionElements(pgId,peIds);
			//Role r = upm.getRoleById("2");
			//upm.assignUserRoleToProtectionGroup("700",new String[]{"54"},"35");
			upm.assignGroupRoleToProtectionGroup("151","162",new String[]{"60","57"});
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void priv_populatePgPe(){
		RandomIntGenerator rit = new RandomIntGenerator(19,20015);
		String[] peIds = new String[100];
		for(int i=0;i<100;i++){	    	 	
			int k = rit.draw();
			peIds[i]= String.valueOf(k);
		}
		rit = new RandomIntGenerator(35,132);
		String pg_id = String.valueOf(rit.draw());
		
		try{
			upm.assignProtectionElements(pg_id,peIds);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void priv_populateUgrpg(){
		RandomIntGenerator rit = new RandomIntGenerator(55,63);
		String[] roleIds = new String[2];
		for(int i=0;i<2;i++){	    	 	
			int k = rit.draw();
			roleIds[i]= String.valueOf(k);
		}
		rit = new RandomIntGenerator(1,5000);
		String user_id = String.valueOf(rit.draw());
		rit = new RandomIntGenerator(34,133);
		String pg_id = String.valueOf(rit.draw());
		
		try{
			upm.assignUserRoleToProtectionGroup(user_id,roleIds,pg_id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void populateUgrpg(){
		for(int i=1;i<10000;i++){	    	 	
			priv_populateUgrpg();
		}
	}
	
	public void populatePgPe(){
		for(int i=1;i<100;i++){	    	 	
			priv_populatePgPe();
		}
	}
	public void checkPermission(){
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			System.out.println(System.currentTimeMillis());
			//System.out.println(am.checkPermission("login_name_4322","x_y_z_11919","Delete"));
			//System.out.println(am.checkPermission("kumarvi","csmupt",null));
			//System.out.println(am.checkPermission("login_name_2121","x_y_z_17385","Delete"));
			//System.out.println(System.currentTimeMillis());
			boolean f = am.checkPermission("kumar1234","PE3","READ");
			//boolean f = am.checkPermission("login_name_3","X_Y_Z_999","abc","update");
			
			System.out.println("Got result"+f);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error:"+ex.getMessage());
		}
	}
	public void deAssignProtectionElement(){
		try{
			AuthorizationManager am = (AuthorizationManager)upm;
			
			am.deAssignProtectionElements("  rrrrr","X_Y_Z_2121");
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error:"+ex.getMessage());
		}
	}
	public void testKunalCode(){
		try{
			Collection associatedGroups = (Collection)upm.getGroups("5020");
            Group group = new Group();
            group.setGroupName("Group_Name%");
            SearchCriteria searchCriteria = new GroupSearchCriteria(group);
            Collection totalGroups = (Collection)upm.getObjects(searchCriteria);
            Collection availableGroups = ObjectSetUtil.minus(totalGroups,associatedGroups);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void getGroups(String id){
		try{
			Collection cal = upm.getGroups(id);
			Iterator it = cal.iterator();
			while(it.hasNext()){
				Group gp = (Group)it.next();
				System.out.println(gp.getGroupName());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void getProtectionGroupRoleContextForUser(String id){
		try{
			Collection cal = upm.getProtectionGroupRoleContextForUser(id);
			Iterator it = cal.iterator();
			while(it.hasNext()){
				ProtectionGroupRoleContext gp = (ProtectionGroupRoleContext)it.next();
				System.out.println(gp.getProtectionGroup().getProtectionGroupName());
				System.out.println(gp.getRoles().size());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void getProtectionGroupRoleContextForGroup(String id){
		try{
			Collection cal = upm.getProtectionGroupRoleContextForGroup(id);
			Iterator it = cal.iterator();
			while(it.hasNext()){
				ProtectionGroupRoleContext gp = (ProtectionGroupRoleContext)it.next();
				System.out.println(gp.getProtectionGroup().getProtectionGroupName());
				System.out.println(gp.getRoles().size());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void getObjects(){
		try{
			//Role role = new Role();
			//role.setName("role_name_1");
			//Group grp = new Group();
			User user = new User();
			//user.setLoginName("login_name_1");
			//user.setFirstName("%");
			//user.setDepartment("security");
			//ProtectionElement pe = new ProtectionElement();
			//pe.setObjectId("%");
			//pe.setProtectionElementName("PE_name_3");
			//grp.setGroupName("g%");
			//SearchCriteria sc = new RoleSearchCriteria(role);
			//SearchCriteria sc = new ProtectionElementSearchCriteria(pe);
			SearchCriteria sc = new UserSearchCriteria(user);
			System.out.print(new java.util.Date());
			List result = upm.getObjects(sc);
			System.out.println(result.size());
			System.out.print(new java.util.Date());
			   Iterator it = result.iterator();
			   //while(it.hasNext()){
			   //	Role p = (Role)it.next();
			   	//User usr = (User)it.next();
			   	//System.out.println(usr.getFirstName()+":"+usr.getLastName());
			   //}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void assignOwners(){
		try{
			String[] uids = {"1","2"};
			String peIds = "3";
			upm.assignOwners(peIds,uids);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testSecureObject(){
		try{
			//Role r = upm.getRoleById("55");
			//Role r1 = (Role)upm.secureObject("kumarvi",new Privilege());
			Privilege pr = new Privilege();
			pr.setName("ABD");
			pr.setId(new Long("23"));
			pr.setDesc("SUJHBikhnkjnik");
			
			Privilege r1 = (Privilege)upm.secureObject("kumar1234",pr);
			System.out.println(r1.getDesc());
			System.out.println(r1.getId());
			System.out.println(r1.getName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testUserPEPrivilegeObject() {
		try {
			upm.getProtectionElementPrivilegeContextForUser("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void testGroupPEPrivilegeObject() {
		try {
			upm.getProtectionElementPrivilegeContextForGroup("3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void testSetOwnerForProtectionElement() {
		try {
			upm.setOwnerForProtectionElement("620_TigerWoods","620_golf_score",null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Test cases for parent protection Group 
	 *
	 */
	public void getProtectionElementPrivilegeContextForUser(){
		try{
		Set result = upm.getProtectionElementPrivilegeContextForUser("15");
		System.out.println(result.size());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void getProtectionElementPrivilegeContextForGroup(){
		try{
		Set result = upm.getProtectionElementPrivilegeContextForGroup("10");
		System.out.println(result.size());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void secureUpdate(){
		try{
			Privilege orig = new Privilege();
			orig.setId(new Long("23"));
			orig.setName("Testing");
			orig.setDesc("vinay kumar");
			
			Privilege mut = new Privilege();
			mut.setId(new Long("231"));
			mut.setName("Testing1");
			mut.setDesc("vinay kumar1");
			
			Privilege obj = (Privilege)upm.secureUpdate("kumar1234",orig,mut);
			System.out.println(obj.getId());
			System.out.println(obj.getName());
			System.out.println(obj.getDesc());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testAuthenticationManager()
	{
		try {
			am1 =  SecurityServiceProvider.getAuthenticationManager("security");
		} catch (CSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testAuthorizationManager()
	{
		try {
			am = SecurityServiceProvider.getAuthorizationManager("security");
		} catch (CSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		
	 
}
