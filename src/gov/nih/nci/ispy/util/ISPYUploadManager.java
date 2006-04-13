/**
 * This class is responsible for all uploaded user lists
 */
package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.application.lists.ListGenerator;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UploadManager;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rossok
 *
 */
public class ISPYUploadManager implements UploadManager{
   
    private static ISPYUploadManager instance = null;
    
    public ISPYUploadManager(){
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static ISPYUploadManager getInstance() {
        
          if (instance == null) {
            instance = new ISPYUploadManager();
          }
          return instance;
    }
    
    public UserList createList(ListType listType, String listName, File formFile) {
        UserList userList;
        ListGenerator listGenerator = new ListGenerator();
        userList = listGenerator.createList(listType,formFile,listName);
        if(userList!=null){
            userList = validate(userList, listType);
        }
        return userList;
    }

    public UserList validate(UserList myList, ListType listType) {
        ISPYListValidator listValidator = new ISPYListValidator();
        myList = listValidator.getValidList(listType,myList);
        return myList;
    }

    public Map getParams(UserList userList) {
        Map listParams = new HashMap();
        listParams.put("listName", userList.getName());
        return listParams;
    }

}
