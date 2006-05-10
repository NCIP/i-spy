package gov.nih.nci.ispy.web.ajax;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.ispy.util.ISPYListManager;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicListHelper {

	
	public DynamicListHelper() {}
	
	
	public static String getListAsList(ListType ty){
		String results = "";
		
		HttpSession session = ExecutionContext.get().getSession(false);
		ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
                      
        List patientLists = helper.getLists(ty);
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
	
	public static String getPatientListAsList()	{
		/*
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
		*/
		return DynamicListHelper.getListAsList(ListType.PatientDID);
	}
	
	public static String getGeneListAsList()	{
		/*
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
        return results;\
        */
		return DynamicListHelper.getListAsList(ListType.GeneSymbol);
	}
	
	public static String createGenericList(ListType type, String[] list, String name){
		
		//no duplicates
		HashSet<String> h = new HashSet<String>();
		for (int i = 0; i < list.length; i++)
			h.add(list[i]);
		List<String> cleanList = new ArrayList<String>();
		for(String n : h)	{
			cleanList.add(n);
		}
		
		String success = "fail";
		ISPYListManager um = ISPYListManager.getInstance();
		try	{
			UserList mylist = um.createList(type, name, cleanList);
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

	public static String exportListasTxt(String name, HttpSession session){
		String txt = "";
		
		ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
		
        List<String> listItems = helper.getItemsFromList(name);
        txt = StringUtils.join(listItems.toArray(), "\r\n");
		return txt;
	}
	/*
	//accept commas seperated lists too
	public static String createGeneList(String commaList, String name){
		String[] list = StringUtils.split(commaList, ",");
		return DynamicListHelper.createGenericList(ListType.GeneSymbol, list, name);
	}
	
	public static String createPatientList(String commaList, String name){
		String[] list = StringUtils.split(commaList, ",");
		return DynamicListHelper.createGenericList(ListType.PatientDID, list, name);
	}
	*/
	
	public static Document getAllLists(String listType){
		
	   //HttpSession session = ExecutionContext.get().getSession(false);
	   //ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
	   ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper();
	   
	   Collection<String> myLists = new ArrayList<String>();
	   
	   Document listDoc = DocumentHelper.createDocument();
	   Element list = listDoc.addElement("lists");
	   
	   if(listType.equals(ListType.PatientDID.toString()))	{
		   list.addAttribute("type", "patient");
		   myLists = helper.getPatientListNames();
	   }
	   else	if(listType.equals(ListType.GeneSymbol.toString()))	{
		   myLists = helper.getGeneSymbolListNames();     
		   list.addAttribute("type", "gene");
	   }
	   
	   for(String listName : myLists){
		 UserList ul = helper.getUserList(listName);
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.US);
		 if(ul!=null)	
			 list.addElement("list").addAttribute("name", ul.getName()).addAttribute("date", dateFormat.format(ul.getDateCreated()).toString()).addAttribute("items", String.valueOf(ul.getItemCount())).addAttribute("invalid", String.valueOf(ul.getInvalidList().size()));
	   }
	   
	   
	   return listDoc;
	}
	
}
