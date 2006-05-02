package gov.nih.nci.ispy.web.ajax;

import java.util.List;
import java.util.Map;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.ispy.util.ISPYListManager;
import gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper;

import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicListHelper {

	
	public DynamicListHelper() {}
	
	public static String getPatientListAsList()	{
		String results = "";
		
		HttpSession session = ExecutionContext.get().getSession(false);
		ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
                      
        List patientLists = helper.getLists(ListType.PatientDID);
        if (!patientLists.isEmpty()) {
            for (int i = 0; i < patientLists.size(); i++) {
                UserList list = (UserList) patientLists.get(i);
                ISPYListManager uploadManager = (ISPYListManager) ISPYListManager.getInstance();
                Map paramMap = uploadManager.getParams(list);
                results += ("<li id='" + paramMap.get("listName") + "'>"+paramMap.get("listName")+"</li>");
            }
        } else {
            results = "";
        }

		return results;
	}
	
	public static String getGeneListAsList()	{
		String results = "";
		
		HttpSession session = ExecutionContext.get().getSession(false);
		ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
		
        List geneLists = helper.getLists(ListType.GeneSymbol);
        if (!geneLists.isEmpty()) {
            for (int i = 0; i < geneLists.size(); i++) {
                UserList list = (UserList) geneLists.get(i);
                ISPYListManager uploadManager = (ISPYListManager) ISPYListManager.getInstance();
                Map paramMap = uploadManager.getParams(list);
                results += ("<li id='" + paramMap.get("listName") + "'>"
                        + paramMap.get("listName") + "</li>");
            }
        } else {
            results = "";
        }	
        return results;
	}
	
}
