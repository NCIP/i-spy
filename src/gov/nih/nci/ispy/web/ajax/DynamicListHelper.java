package gov.nih.nci.ispy.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.ispy.util.ISPYListManager;
import gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
                String commas = StringUtils.join(list.getList().toArray(), ",");
                results += ("<li id='" + paramMap.get("listName") + "' title='"+commas+"'>"+paramMap.get("listName")+"</li>");
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
                String commas = StringUtils.join(list.getList().toArray(), ",");
                results += ("<li id='" + paramMap.get("listName") + "' title='"+commas+"'>"
                        + paramMap.get("listName") + "</li>");
            }
        } else {
            results = "";
        }	
        return results;
	}
	
	public static String createGenericList(ListType type, String[] list, String name){
		//create list w/ type=patient
		String success = "fail";
		ISPYListManager um = ISPYListManager.getInstance();
		try	{
			UserList mylist = um.createList(type, name, Arrays.asList(list));
			ISPYUserListBeanHelper ulbh = new ISPYUserListBeanHelper();
			ulbh.addList(mylist);
			success = "pass";
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return success;
	}
	
	public static String createPatientList(String[] list, String name){
		//create list w/ type=patient
		return DynamicListHelper.createGenericList(ListType.PatientDID, list, name);
	}
	
	public static String createGeneList(String[] list, String name){
		return DynamicListHelper.createGenericList(ListType.GeneSymbol, list, name);
	}
	
	//accept commas seperated lists too
	public static String createGeneList(String commaList, String name){
		String[] list = StringUtils.split(commaList, ",");
		return DynamicListHelper.createGenericList(ListType.GeneSymbol, list, name);
	}
	
	public static String createPatientList(String commaList, String name){
		String[] list = StringUtils.split(commaList, ",");
		return DynamicListHelper.createGenericList(ListType.PatientDID, list, name);
	}
	
}
