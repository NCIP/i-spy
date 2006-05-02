/**
 * This class is responsible for all uploaded user lists
 */
package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author rossok
 *
 */
public class ISPYListManager implements ListManager{
   
    private static ISPYListManager instance = null;
    
    public ISPYListManager(){
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static ISPYListManager getInstance() {
        
          if (instance == null) {
            instance = new ISPYListManager();
          }
          return instance;
    }
   
    
    public UserList createList(ListType listType, String listName, List<String> undefinedList) {
        UserList userList = new UserList();
        if(undefinedList!=null){
            undefinedList = validate(undefinedList, listType);
            userList.setList(undefinedList);
            //set the name
            userList.setName(listName);
            //set the list type
            userList.setListType(listType);
            try {
                Date date = new Date();                
                userList.setDateCreated(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            userList.setItemCount(userList.getList().size());
        }
        
        return userList;
    }

    public List<String> validate(List<String> myList, ListType listType) {
        ISPYListValidator listValidator = new ISPYListValidator();
        myList = listValidator.getValidList(listType,myList);
        return myList;
    }

    public Map<String,String> getParams(UserList userList) {        
        Map<String, String> listParams = new HashMap<String,String>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.US);
        Integer count = userList.getItemCount();
        String date = dateFormat.format(userList.getDateCreated());
        listParams.put("listName", userList.getName());
        listParams.put("date", date);
        listParams.put("items", count.toString());
        listParams.put("type",userList.getListType().toString());
        
        return listParams;
    }

}
