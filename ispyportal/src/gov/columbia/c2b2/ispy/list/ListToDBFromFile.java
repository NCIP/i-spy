package gov.columbia.c2b2.ispy.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import gov.columbia.c2b2.ispy.list.UserListBean;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.ispy.util.ProcessHelper;
import gov.nih.nci.ispy.util.UserListHelperDB;

public class ListToDBFromFile {
	
	
    public void loadListFromFile(String name, ListType type, List<String> list, HttpSession session) {
    	UserListBean helper = new UserListBean();
   	 UserListN newList = new UserListN(name, type, list,new ArrayList<String>(),new Date());
       newList.setListOrigin(ListOrigin.Custom);
       newList.setItemCount(list.size());
       
	UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
    dbPrcs.dataBasePrcsS(newList, session);
       
   }
}
