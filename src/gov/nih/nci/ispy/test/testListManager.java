package gov.nih.nci.ispy.test;

import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class testListManager {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ListManager uploadManager =ListManager.getInstance();
        Map paramMap;
        UserListBean userListBean = new UserListBean();
        
        List<String> list1 = new ArrayList<String>();
        list1.add("1001");
        list1.add("1002");
        list1.add("1003");
        List<String> list2 = new ArrayList<String>();
        list2.add("1001");
        list2.add("1005");
        list2.add("1007");
        list2.add("1002");
        List<String> list3 = new ArrayList<String>();
        list3.add("1002");
        list3.add("1004");
        list3.add("1013");
        List<String> listNames = new ArrayList<String>();
        listNames.add("myPatientList1");
        listNames.add("myPatientList2");
        listNames.add("myPatientList3");
        
        
        UserList myList1 = uploadManager.createList(ListType.PatientDID, "myPatientList1", list1,new ISPYListValidator());
        UserList myList2 = uploadManager.createList(ListType.PatientDID, "myPatientList2", list2,new ISPYListValidator());
        UserList myList3 = uploadManager.createList(ListType.PatientDID, "myPatientList3", list3,new ISPYListValidator());
        userListBean.addList(myList1);
        userListBean.addList(myList2);
        userListBean.addList(myList3);
        //ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(userListBean);
        
        //helper.uniteLists(listNames,"newList",ListType.PatientDID);
        //helper.intersectLists(listNames,"newList",ListType.PatientDID);
        
    }

}
