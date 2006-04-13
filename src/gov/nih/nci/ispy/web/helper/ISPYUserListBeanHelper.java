/**
 * This helper accesses the UserListBean and can be instantiated
 * anywhere. Depending on how and where the developer instantiates this class
 * the Helper can will access the UserListBean through the session, by
 * either recieving the session itself, or, in this case, having DWR access
 * the UserListBean on it own.
 */
package gov.nih.nci.ispy.web.helper;


import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import uk.ltd.getahead.dwr.ExecutionContext;

/**
 * @author rossok
 *
 */
public class ISPYUserListBeanHelper implements UserListBeanHelper{
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private HttpSession session;
    private String sessionId;
    private UserListBean userListBean;
    private ISPYListValidator listValidator = new ISPYListValidator();
    
    public ISPYUserListBeanHelper(UserListBean userListBean){
        this.userListBean = userListBean;
    }
    
    public ISPYUserListBeanHelper(HttpSession session){
        userListBean = (UserListBean) session.getAttribute("userListBean");        
        
    }
    public ISPYUserListBeanHelper(){       
        session = ExecutionContext.get().getSession(false); 
        sessionId = ExecutionContext.get().getSession(false).getId(); 
        userListBean = (UserListBean) session.getAttribute("userListBean");        
               
    }
    public void addList(UserList userList) {
        userListBean.addList(userList);        
    }
    
    public void removeList(String listName) {
        userListBean.removeList(listName);
        
    }
    public void addItemToList(String listName, String listItem) {
        UserList userList =  userListBean.getList(listName);
        userList.getList().add(listItem);
        userList = listValidator.getValidList(userList.getListType(),userList);
    }
    
    public void removeItemFromList(String listName, String listItem) {        
        UserList userList =  userListBean.getList(listName);
        userList.getList().remove(listItem);
    }
    
    public Document getDetailsFromList(String listName) {
        UserList userList = userListBean.getList(listName);
        
        Document document =  DocumentHelper.createDocument();
        Element list = document.addElement("list");
        Element type = list.addAttribute("type", userList.getListType().toString());
        for(String i : userList.getList()){
            Element item = list.addElement("item");
            item.addText(i);
        }
        
        
        return document;
    }
    
    public List<UserList> getLists(ListType listType) {
        List<UserList> typeList = new ArrayList<UserList>();
        
        for(UserList list : userListBean.getEntireList()){
            if(list.getListType() == listType){
                typeList.add(list);
            }
        }
        return typeList;
    }

   

}
