/**
 * This helper accesses the UserListBean and can be instantiated
 * anywhere. Depending on how and where the developer instantiates this class
 * the Helper can will access the UserListBean through the session, by
 * either recieving the session itself, or, in this case, having DWR access
 * the UserListBean on it own.
 */
package gov.columbia.c2b2.ispy.list;


import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.nih.nci.caintegrator.application.cache.CacheConstants;
import gov.nih.nci.caintegrator.application.cache.PresentationCacheManager;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.ispy.util.UserListHelperDB;
import gov.nih.nci.ispy.util.UserListLoaderDB;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

/**
 * @author rossok
 * UserListBeanHelper is the touchpoint for any app using list management service
 * The class uses the presentation tier cache to store a userlist bean containing
 * all user lists.
 *
 */
public class UserListBeanHelper{
    private HttpSession session;
    private String sessionId;
    private UserListBean userListBean;
    private static Logger logger = Logger.getLogger(UserListBeanHelper.class);
    public PresentationTierCache presentationTierCache = PresentationCacheManager.getInstance();
    
    /**
     * recommended constructor
     * @param presentationCacheId
     */
    
    public UserListBeanHelper(String presentationCacheId){
        this.userListBean = (UserListBean)presentationTierCache.getNonPersistableObjectFromSessionCache(presentationCacheId,CacheConstants.USER_LISTS);
    }
    
    
    public void addBean(String presntationCacheId, String key, UserListBean userListBean){
        presentationTierCache.addNonPersistableToSessionCache(presntationCacheId, CacheConstants.USER_LISTS, userListBean);        
    }
    
    /**
     * deprecated constructor -- used to support legacy code
     * @param userListBean
     * @deprecated
     */
    public UserListBeanHelper(UserListBean userListBean){
        this.userListBean = userListBean;
    }
    
    /**
     * deprecated constructor -- used to support legacy code
     * @param userListBean
     * @deprecated
     */    
    public UserListBeanHelper(HttpSession session){
//        this.userListBean = (UserListBean)presentationTierCache.getNonPersistableObjectFromSessionCache(session.getId(),CacheConstants.USER_LISTS);
        User currentUser = (User) session.getAttribute("currentUser");
        UserListBean newList = new UserListBean();
		UserListLoaderDB userListLoaderDB = (UserListLoaderDB)SpringContext.getBean("userListLoaderDB");
        this.userListBean = userListLoaderDB.loadUserListFromDB(currentUser.getUserId());
    }
    
    /**
     * deprecated constructor -- used to support legacy code
     * @param userListBean
     * @deprecated
     */
    public UserListBeanHelper(){       
        session = ExecutionContext.get().getSession(false); 
        
   /*     
        sessionId = ExecutionContext.get().getSession(false).getId(); 
        this.userListBean = (UserListBean)presentationTierCache.getNonPersistableObjectFromSessionCache(session.getId(),CacheConstants.USER_LISTS); 
//		String userID = (String) session.getAttribute("name"); 
    */    
        
        User currentUser = (User) session.getAttribute("currentUser");
        UserListBean newList = new UserListBean();
		UserListLoaderDB userListLoaderDB = (UserListLoaderDB)SpringContext.getBean("userListLoaderDB");
        this.userListBean = userListLoaderDB.loadUserListFromDB(currentUser.getUserId());


	      System.out.println("Debug");
    }
    
    public UserListBeanHelper(Integer share){       
        session = ExecutionContext.get().getSession(false); 
//		String userID = (String) session.getAttribute("name"); 
        User currentUser = (User) session.getAttribute("currentUser");
		UserListLoaderDB userListLoaderDBS = (UserListLoaderDB)SpringContext.getBean("userListLoaderDB");
        this.userListBean = userListLoaderDBS.loadSharedListFromDB(currentUser.getUserId());
    }
    

    
    public void addList(UserListN userList) {
        userListBean.addListToDB(userList);        
    }
    
    public void removeList(String listID) {
        userListBean.removeList(listID); 
    }
    
    public String removeListFromAjax(String listID)   {
        removeList(listID);
        return listID;
    }
    
    public void shareTheList(Long listID, String[] groupIds){
    	userListBean.shareListGroup(listID, groupIds);
    }
    
    public void addItemToList(String listName, String listItem) {
        UserListN userList =  userListBean.getList(listName);
        ListItem item = new ListItem(listItem,listName);
        userList.getListItems().add(item);
        userList.setItemCount(userList.getItemCount()+1); 
    }
    
    public void removeItemFromList(String listName, String listItem, String itemID) {        
        String[] listItemArray;
        if(listItem.contains(" notes")){
            listItemArray = listItem.split(" notes");
            listItem = listItemArray[0];
        }
        else if(listItem.contains(" rank")){
            listItemArray = listItem.split(" rank");
            listItem = listItemArray[0];
        }
        
        UserListN userList =  userListBean.getList(listName);
        for(ListItem l:userList.getListItems()){
            if(l.getName().equalsIgnoreCase(listItem)){
                userList.getListItems().remove(l);
                break;
            }
        }
        //userList.getList().remove(listItem);
   		UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
   		Long itemId = Long.parseLong(itemID.trim());
//   		dbPrcs.dataBasePrcsDelItemN(userList);
//        dbPrcs.dataBasePrcsDelItem(itemId);
   		dbPrcs.dataBasePrcsDelItemS(userList.getId(), itemId);
        userList.setItemCount(userList.getItemCount()-1);           
    }
    
    public UserListN getUserList(String listName){
        return userListBean.getList(listName);      
    }
    
    
    public ListType getUserListType(String listName){
        UserListN myList = userListBean.getList(listName); 
        return myList.getListType();
    }
    
    public String getDetailsFromList(String id) {
    	Long listID = Long.parseLong(id.trim());
        UserListN userList = userListBean.getListById(listID);
        
        JSONObject listDetails = new JSONObject();
        listDetails.put("listName",userList.getName());
        listDetails.put("listType",userList.getListType().toString());
        listDetails.put("listID", userList.getId().toString());
        
        /**
         * new way of retrieving details from lists. Each
         * listItem is treated as such, and NOT just a string.*/
         
//        if(userList.getListItems()!=null && !userList.getListItems().isEmpty()){
//            JSONArray listItems = new JSONArray();             
//            
//            String itemDescription = "";
//            for(ListItem listItem:userList.getListItems()){
//                String name = listItem.getName();
//                itemDescription = name;
//                if(listItem.getNotes()!=null && listItem.getNotes().trim().length()>=1){
//                    itemDescription += " notes: " + listItem.getNotes();
//                }
//                if(listItem.getRank()!=null){
//                    itemDescription += " rank: " + Double.toString(listItem.getRank());
//                }
//                
//                listItems.add(itemDescription);
//            }
//            listDetails.put("validItems", listItems );
//        }
        
        if(userList.getListItems()!=null && !userList.getListItems().isEmpty()){
            JSONArray listItems = new JSONArray();   
            
            for(ListItem listItem:userList.getListItems()){
                String name = listItem.getName();
                JSONObject itemDescription = new JSONObject();
                itemDescription.put("name",name);
                itemDescription.put("itemID", listItem.getId().toString());
                if(listItem.getNotes()!=null && listItem.getNotes().trim().length()>=1){
                    itemDescription.put("notes",listItem.getNotes());
                }
                if(listItem.getRank()!=null){
                    itemDescription.put("rank",Long.toString(listItem.getRank()));
                }
                
                listItems.add(itemDescription);
            }
            listDetails.put("validItems", listItems );
        }
        
        
       /** if(userList.getList()!=null && !userList.getList().isEmpty()){
            JSONArray item = new JSONArray();             
            for(String i : userList.getList()){
                //Element item = list.addElement("item");
                //item.addText(i);
                item.add(i);
            }
            listDetails.put("validItems", item );
        }
        */
        
        if(userList.getInvalidList()!=null && !userList.getInvalidList().isEmpty()){
            JSONArray item = new JSONArray();
            for(String v : userList.getInvalidList()){
                //Element item = list.addElement("invalidItem");
                //item.addText(v);
                item.add(v);
            }
            listDetails.put("invalidItems", item );
        }
        
        return listDetails.toString();
    }
    
    public List<UserListN> getLists(ListType listType) {
        List<UserListN> typeList = new ArrayList<UserListN>();
        
        if(userListBean!=null && userListBean.getEntireList()!=null && !userListBean.getEntireList().isEmpty()){
            for(UserListN list : userListBean.getEntireList()){
                if(list.getListType() == listType){
                    typeList.add(list);
                }
            }
        }
        return typeList;
    }
    
    
    public List<UserListN> getLists(ListType listType, ListSubType listSubType)   {
        //get lists of this type with these subtypes
        List<UserListN> ul = this.getLists(listType);
        List<UserListN> matches = new ArrayList<UserListN>(); 
        //got all the main type, now see if any of these are of any listsubtypes
        for(UserListN u : ul){
            ListSubType lst = u.getListSubType();            
                if(lst.equals(listSubType)){
                    matches.add(u);
                }            
        }
        return matches;
    }
    
    public List<UserListN> getLists(ListType listType, List<ListSubType> listSubTypes)   {
        //get lists of this type with these subtypes
        List<UserListN> ul = this.getLists(listType);
        List<UserListN> matches = new ArrayList<UserListN>(); 
        //got all the main type, now see if any of these are of any listsubtypes
        for(UserListN u : ul){
            for(ListSubType subType:listSubTypes){
                ListSubType lst = u.getListSubType();            
                    if(lst.equals(subType)){
                        matches.add(u);
                    }    
            }        
        }
        return matches;
    }
    
    public List<UserListN> getLists(ListType listType, ListOrigin listOrigin)   {
        //get lists of this type with these subtypes
        List<UserListN> ul = this.getLists(listType);
        List<UserListN> matches = new ArrayList<UserListN>(); 
        //got all the main type, now see if any of these are of any listsubtypes
        for(UserListN u : ul){
            ListOrigin lo = u.getListOrigin();
                if(lo.equals(listOrigin)){
                    matches.add(u);
                }            
        }
        return matches;
    }
    
    
    
    public List<UserListN> getAllCustomLists(){
        List<UserListN> customList = new ArrayList<UserListN>();
        if (userListBean == null || userListBean.getEntireList().isEmpty())
            return customList;
        
        for (UserListN list : userListBean.getEntireList()){
            ListOrigin origin = list.getListOrigin();
            if(origin!=null && origin.equals(ListOrigin.Custom)){
                customList.add(list);
            }
        }
        return customList;
    }
    
    public List<UserListN> getAllCustomListsForType(ListType type){
        List<UserListN> customList = new ArrayList<UserListN>();
        if (userListBean == null || userListBean.getEntireList().isEmpty())
            return customList;
        
        for (UserListN list : userListBean.getEntireList()){
            ListOrigin origin = list.getListOrigin();
            ListType myType = list.getListType();
            if(origin!=null && origin.equals(ListOrigin.Custom)
                    && myType!=null && myType == type){
                customList.add(list);
            }
        }
        return customList;
    }
    
    
    
    public List<UserListN> getAllLists() {
        List<UserListN> allList = new ArrayList<UserListN>();
        if(userListBean!=null){
            for(UserListN list : userListBean.getEntireList()){
                allList.add(list);
            } 
        }
        return allList;
    }
    
    public List<String> getItemsFromList(String listName){
        UserListN userList = userListBean.getList(listName);
        List<String> items = userList.getList();        
        return items;
    }
   
     
    
    /**
     * @NOTE : DE may change in future.
     */
            
    public Collection<GeneIdentifierDE> getGeneDEforList(String listName){
        UserListN geneSetList = userListBean.getList(listName);
        Collection<GeneIdentifierDE> geneIdentifierDECollection = new ArrayList<GeneIdentifierDE>();
        for(String item : geneSetList.getList()){
            GeneIdentifierDE.GeneSymbol gs = new GeneIdentifierDE.GeneSymbol(item);
            geneIdentifierDECollection.add(gs);
        }
        return geneIdentifierDECollection;
    }
    
    
    public Collection getPatientListNames(){ 
        Collection<UserListN> patientSetList = new ArrayList<UserListN>();
        patientSetList = getLists(ListType.PatientDID);  
        Collection patientSetListNames = new ArrayList();
        for(UserListN userListName : patientSetList){
            patientSetListNames.add(userListName.toString());
        }
        return patientSetListNames;
    }
    
    public Collection getGenericListNamesFromString(String listType){ 
        return getGenericListNames(ListType.valueOf(listType));
    }
    
    public Collection getGenericListNamesFromStringWithSubs(String listType, String sub){ 
        //parse the subs
        ListSubType lst = ListSubType.valueOf(sub.trim());
        return getGenericListNamesWithSubTypes(ListType.valueOf(listType), lst);
    }
    
    public Collection getGenericListNames(ListType listType){ 
        Collection<UserListN> setList = new ArrayList<UserListN>();
        setList = getLists(listType);  
        Collection setListNames = new ArrayList();
        for(UserListN userListName : setList){
            setListNames.add(userListName.toString());
        }
        return setListNames;
    }
    
    public Collection getGenericListNamesWithSubTypes(ListType lt, ListSubType sub)  {
         Collection<UserListN> setList = new ArrayList<UserListN>();
         setList = getLists(lt, sub);
         Collection setListNames = new ArrayList();
         for(UserListN userListName : setList){
             setListNames.add(userListName.toString());
         }
         return setListNames;
    }
    
    public void differenceLists(List<String> listNames, String newListName, ListType listType)  {
        
        //we're only expecting 2 lists here
        if(listNames.size()!=2) {
            return;
        }
        UserListN ulist = userListBean.getList(listNames.get(0));
        List<String> s1 = ulist.getList();
        ulist = userListBean.getList(listNames.get(1));
        List<String> s2 = ulist.getList();
        

        //transforms s1 into the (asymmetric) set difference of s1 and s2. 
        //(For example, the set difference of s1 minus s2 is the set containing all 
        //the elements found in s1 but not in s2.)

        Set<String> difference = new HashSet<String>(s1);
        difference.removeAll(s2);
        UserListN newList = null;
        if(difference.size()>0){
            List dList = new ArrayList();
            dList.addAll(difference);
            Collections.sort(dList, String.CASE_INSENSITIVE_ORDER);
            
            newList = new UserListN(newListName+"_"+listNames.get(0)+"-"+listNames.get(1),listType,dList,new ArrayList<String>(),new Date());
            newList.setListOrigin(ListOrigin.Custom);
            newList.setItemCount(dList.size());
//            userListBean.addList(newList);
            userListBean.addListToDB(newList);
        }
        Set<String> difference2 = new HashSet<String>(s2);
        difference2.removeAll(s1);
        if(difference2.size()>0)    {
            List dList2 = new ArrayList();
            dList2.addAll(difference2);
            Collections.sort(dList2, String.CASE_INSENSITIVE_ORDER);
            newList = null;
            newList = new UserListN(newListName+"_"+listNames.get(1)+"-"+listNames.get(0),listType,dList2,new ArrayList<String>(),new Date());
            newList.setListOrigin(ListOrigin.Custom);
            newList.setItemCount(dList2.size());
//            userListBean.addList(newList);
            userListBean.addListToDB(newList);
        }
        
    }
    
    public void uniteLists(List<String> listNames, String newListName, ListType listType) {
        List<String> items = new ArrayList<String>();
        for(String listName: listNames){
            UserListN list = userListBean.getList(listName);
            if(!list.getList().isEmpty()){
                items.addAll(list.getList());
            }
        }
        Set<String> unitedSet = new HashSet<String>(items);
        items.clear();
        items.addAll(unitedSet);
        Collections.sort(items, String.CASE_INSENSITIVE_ORDER);
        UserListN newList = new UserListN(newListName,listType,items,new ArrayList<String>(),new Date());
        newList.setListOrigin(ListOrigin.Custom);
        newList.setItemCount(items.size());
        userListBean.addListToDB(newList);
    }
    
    public void loadListFromFile(String name, ListType type, List<String> list) {
    	 UserListN newList = new UserListN(name, type, list,new ArrayList<String>(),new Date());
        newList.setListOrigin(ListOrigin.Custom);
        newList.setItemCount(list.size());
        userListBean.addListToDB(newList);
    }
    
    public void intersectLists(List<String> listNames, String newListName, ListType listType) {
        List<String> items = new ArrayList<String>();
        List<UserListN> lists = new ArrayList<UserListN>();
        for(String listName: listNames){
            UserListN list = userListBean.getList(listName);
            lists.add(list);
            if(!list.getList().isEmpty()){
                items.addAll(list.getList());
            }
        }
        Set<String> intersectedList = new HashSet<String>(items);
        for(UserListN ul : lists){
            intersectedList.retainAll(ul.getList());
        }
        items.clear();
        items.addAll(intersectedList);
        Collections.sort(items, String.CASE_INSENSITIVE_ORDER);
        UserListN newList = new UserListN(newListName,listType,items,new ArrayList<String>(),new Date());
        newList.setListOrigin(ListOrigin.Custom);
        newList.setItemCount(items.size());
        userListBean.addListToDB(newList);
//        userListBean.addList(newList);
    }
    
    public boolean isIntersection(List<String> listNames){
        boolean intersection = false;
        List<String> items = new ArrayList<String>();
        List<UserListN> lists = new ArrayList<UserListN>();
        for(String listName: listNames){
            UserListN list = userListBean.getList(listName);
            lists.add(list);
            if(!list.getList().isEmpty()){
                List<String> itemsInList = list.getList();
                for(String i : itemsInList){
                    if(items.contains(i)){
                        intersection = true;
                        break;
                    }
                    else{
                      items.add(i);
                    }
                }
            }
        }
       return intersection;
    }
    

}
