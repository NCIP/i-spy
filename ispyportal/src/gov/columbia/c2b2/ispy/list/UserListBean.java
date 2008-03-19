/**
 * The userlist bean contains all lists created by the user and has session
 * scope. It is accessed by the UserListBean Helper
 */
package gov.columbia.c2b2.ispy.list;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import uk.ltd.getahead.dwr.ExecutionContext;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.ispy.util.UserInfoBean;
import gov.nih.nci.ispy.util.UserInfoHelper;
import gov.nih.nci.ispy.util.UserListHelperDB;
import gov.nih.nci.ispy.util.ProcessHelper;
import gov.nih.nci.ispy.util.UserListLoaderDB;

/**
 * @author rossok
 *
 */
public class UserListBean implements Serializable {

    List<UserListN> userLists = new ArrayList<UserListN>();
    
    public void shareListGroup(Long listID, String[] groupIds){
    	
		String theHQL = "";
		String groupId = "";
		groupId = StringUtils.join(groupIds, ',');
		theHQL = "delete from ShareList where listId =" + listID + " and grId in ("+groupId+")";
		
    	
    	List<ShareList> newShares = new ArrayList<ShareList>();
    	for(String id : groupIds){
    		ShareList shareTemp = new ShareList();
    		shareTemp.setListId(listID);
    		shareTemp.setGrId(Long.valueOf(id.trim()));
    		shareTemp.setUpdateTime(new Timestamp(new Date().getTime()));
    		newShares.add(shareTemp);
    	}
		UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
        dbPrcs.addNewShares(listID, newShares);
/*        
		HttpSession session = ExecutionContext.get().getSession(false);
		String userID = (String) session.getAttribute("name");
		UserInfoBean userFind = (UserInfoBean) SpringContext.getBean("userInfoBean");
		Long userId = userFind.getUidByID(userID);
*/
		HttpSession session = ExecutionContext.get().getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
		
		UserInfoHelper setLog = (UserInfoHelper) SpringContext.getBean("userInfoHelper");
		for(int i=0;i<groupIds.length;i++){
		String[] refIds = setLog.getRefIdByGr(Long.parseLong((groupIds[i])));
		setLog.setLogInfo(currentUser.getUserId(), refIds, "SHADD", listID);
		}
    	
    }
    
    public void shareListGroupDel(Long listID, String[] groupIds){

		UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
        dbPrcs.deleteShares(listID, groupIds);	
    	
    }
    
    public void addList(UserListN userList){
    	userList.setName(this.checkListName(userList.getName()));
    	/*
        for(UserListN list: userLists){        
            if(list.getName().equals(userList.getName())){
            userLists.remove(list);
            break;
           }
         }
        */        
        userLists.add(userList);
    }
    
    public void addListToDB(UserListN userList){
    	ProcessHelper getUserID = (ProcessHelper) SpringContext.getBean("processHelper");

    	userList.setName(this.checkListName(userList.getName()));
    	userList.setUserId(getUserID.getLoginNameCurrent());
        userLists.add(userList);
		UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
        dbPrcs.dataBasePrcs(userList);	
    }
    
    
    public String checkListName(String listName){
    	String iKey = "_renamed";
    	
    	String cleanName = listName;
    	int i = 0;
    	 for(UserListN list: userLists){
    		 
    		 if(list.getName().equalsIgnoreCase(cleanName)){
    			 //hit
    			 if(cleanName.indexOf(iKey)!= -1)	{
    				 String c = cleanName.substring(cleanName.indexOf(iKey)+iKey.length());
    				 Integer it;
    				 try	{
    					 it = Integer.parseInt(c)+1;
    				 }
    				 catch (Exception e) {
    					 it = 1;
					}
    				 cleanName = cleanName.substring(0, cleanName.indexOf(iKey)) +  iKey + it; 
    			 }
    			 else	{
    				 //this is the first hit
    				 cleanName += iKey+"1";
    			 }
    			 cleanName = this.checkListName(cleanName);
    		 }
    	 }
    	//get the names of all the lists in the session
    	//see if listname == any of the current names
    	//if collide, rename new list to listname_1, and recheck until we dont collide
    	return cleanName;
    }
    
    public void removeList(String listID){
    	Long lid = Long.parseLong(listID.trim());
        for(UserListN list: userLists){        
           if(list.getId().equals(lid)){
           userLists.remove(list);
   		UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
        dbPrcs.dataBasePrcsDel(list);
           break;
          }
        }

       
    }
    
    public List<UserListN> getEntireList(){
        return userLists;
    }
    
    public UserListN getList(String listName){
        for(UserListN list : userLists){
            if(list.getName().equals(listName)){
                return list;
            }            
        }
        return null;
        
    }
    
    public UserListN getListByID(String listId){
    	Long lId = Long.parseLong(listId.trim());
        for(UserListN list : userLists){
            if(list.getId().equals(lId)){
                return list;
            }            
        }
        return null;
        
    }

    public UserListN getListById(Long listID){
    	UserListN list = new UserListN();
		UserListLoaderDB userListLoaderDB = (UserListLoaderDB)SpringContext.getBean("userListLoaderDB");
	    list = userListLoaderDB.getListFromDB(listID);   	
    	return list;
    }
}
