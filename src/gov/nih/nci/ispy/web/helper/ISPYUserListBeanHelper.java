/**
 * This helper accesses the UserListBean and can be instantiated
 * anywhere. Depending on how and where the developer instantiates this class
 * the Helper can will access the UserListBean through the session, by
 * either recieving the session itself, or, in this case, having DWR access
 * the UserListBean on it own.
 */
package gov.nih.nci.ispy.web.helper;


import java.util.List;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

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
    
    public ISPYUserListBeanHelper(HttpSession session){
        userListBean = (UserListBean) session.getAttribute("userListBean");        
        
    }
    public ISPYUserListBeanHelper(){       
        session = ExecutionContext.get().getSession(false); 
        sessionId = ExecutionContext.get().getSession(false).getId(); 
        userListBean = (UserListBean) session.getAttribute("userListBean");        
               
    }
    public void addList(UserList userList) {
        // TODO Auto-generated method stub
        
    }
    public void removeList(String listName) {
        // TODO Auto-generated method stub
        
    }
    public void addItemToList(String listName, String listItem) {
        // TODO Auto-generated method stub
        
    }
    public void removeItemFromList(String listName, String listItem) {
        // TODO Auto-generated method stub
        
    }
    public Document getDetailsFromList(String listName) {
        // TODO Auto-generated method stub
        return null;
    }
    public List<UserList> getLists(ListType listType) {
        // TODO Auto-generated method stub
        return null;
    }

}
