/**
 * This class is responsible for all uploaded user lists
 */
package gov.nih.nci.ispy.util;

import java.io.File;
import java.util.Map;

import gov.nih.nci.caintegrator.application.lists.UploadManager;
import gov.nih.nci.caintegrator.application.lists.UserList;

/**
 * @author rossok
 *
 */
public class ISPYUploadManager implements UploadManager{

    public void createList(Enum listType, String listName, File formFile) {
        // TODO Auto-generated method stub
        
    }

    public UserList validate(UserList unvalidatedList) {
        // TODO Auto-generated method stub
        return null;
    }

    public void putInUserListBean(UserList validatedList) {
        // TODO Auto-generated method stub
        
    }

    public Map getParams(UserList userList) {
        // TODO Auto-generated method stub
        return null;
    }

}
