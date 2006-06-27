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

import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;


import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicListHelper {

	
	public DynamicListHelper() {}
	
	
	public static String getListAsList(ListType ty){
		String results = "";
		
		HttpSession session = ExecutionContext.get().getSession(false);
		UserListBeanHelper helper = new UserListBeanHelper(session);
                      
        List patientLists = helper.getLists(ty);
        if (!patientLists.isEmpty()) {
            for (int i = 0; i < patientLists.size(); i++) {
                UserList list = (UserList) patientLists.get(i);
                ListManager uploadManager = (ListManager) ListManager.getInstance();
                Map paramMap = uploadManager.getParams(list);
                String commas = StringUtils.join(list.getList().toArray(), ",");
                results += ("<li id='" + paramMap.get("listName") + "' title='"+commas+"'>"+paramMap.get("listName")+"</li>");
            }
        } else {
            results = "";
        }

		return results;
	}
	
    public static String getDefaultPatientListAsList() {
        return DynamicListHelper.getListAsList(ListType.DefaultPatientDID);
    }
    public static String getPatientListAsList()	{
		return DynamicListHelper.getListAsList(ListType.PatientDID);
	}
	
	public static String getGeneListAsList()	{
		return DynamicListHelper.getListAsList(ListType.GeneSymbol);
	}
	
	public static String createGenericList(ListType type, String[] list, String name){
		
		//no duplicates
		HashSet<String> h = new HashSet<String>();
		for (int i = 0; i < list.length; i++)
			h.add(list[i].trim());
		List<String> cleanList = new ArrayList<String>();
		for(String n : h)	{
			cleanList.add(n);
		}
		
		String success = "fail";
		ListManager um = ListManager.getInstance();
        ISPYListValidator listValidator = new ISPYListValidator();
		try	{
			UserList mylist = um.createList(type, name, cleanList, listValidator);
			UserListBeanHelper ulbh = new UserListBeanHelper();
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
    
    public static String createDefaultPatientList(String[] list, String name){
        //create list w/ type=defaultPatient
        return DynamicListHelper.createGenericList(ListType.DefaultPatientDID, list, name);
    }
	
	public static String createGeneList(String[] list, String name){
		return DynamicListHelper.createGenericList(ListType.GeneSymbol, list, name);
	}

	public static String exportListasTxt(String name, HttpSession session){
		String txt = "";
		
		UserListBeanHelper helper = new UserListBeanHelper(session);
		
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
	
	/*
	public static Document getAllLists(String listType){
		
	   //HttpSession session = ExecutionContext.get().getSession(false);
	   //ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
	   UserListBeanHelper helper = new UserListBeanHelper();
	   
	   Collection<String> myLists = new ArrayList<String>();
	   
	   Document listDoc = DocumentHelper.createDocument();
	   Element list = listDoc.addElement("lists");
	   
	   if(listType.equals(ListType.PatientDID.toString()))	{
		   list.addAttribute("type", "patient");
		   myLists = helper.getPatientListNames();
	   }
       else if(listType.equals(ListType.DefaultPatientDID.toString())) {
           myLists = helper.getDefaultPatientListNames();     
           list.addAttribute("type", "defaultPatient");
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
	*/
	
	public static String getAllPatientLists()	{
		return DynamicListHelper.getAllLists(ListType.PatientDID.toString());
	}
	
	public static String getAllDefaultPatientLists()	{
		return DynamicListHelper.getAllLists(ListType.DefaultPatientDID.toString());
	}
	public static String getAllGeneLists()	{
		return DynamicListHelper.getAllLists(ListType.GeneSymbol.toString());
	}
	
	public static String getAllLists(String listType){
        
        UserListBeanHelper helper = new UserListBeanHelper();
        
        Collection<String> myLists = new ArrayList<String>();
        
        JSONObject listContainer = new JSONObject();
       
        JSONArray myJSONLists = new JSONArray();
        
        if(listType.equals(ListType.PatientDID.toString()))  {
            listContainer.put("listType", "patient");
            myLists = helper.getPatientListNames();
        }
        else if(listType.equals(ListType.DefaultPatientDID.toString())) {
            myLists = helper.getDefaultPatientListNames();     
            listContainer.put("listType", "defaultPatient");
        }
        else if(listType.equals(ListType.GeneSymbol.toString())) {
            myLists = helper.getGeneSymbolListNames();     
            listContainer.put("listType", "gene");
        }


        for(String listName : myLists) {
            UserList ul = helper.getUserList(listName);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.US);
            if(ul!=null)  {
                JSONObject jsonListName = new JSONObject();
                jsonListName.put("listName", ul.getName());
                jsonListName.put("listDate", dateFormat.format(ul.getDateCreated()).toString());
                jsonListName.put("itemCount", String.valueOf(ul.getItemCount()));
                jsonListName.put("invalidItems", String.valueOf(ul.getInvalidList().size()));
                myJSONLists.add(jsonListName);
            }
            
        }
        listContainer.put("listItems",myJSONLists);
        
        return listContainer.toString();
     }
	
	public static String uniteLists(String[] sLists, String groupName, String groupType, String action)	{
		
		JSONObject res = new JSONObject();
		String results = "pass";
		
		UserListBeanHelper helper = new UserListBeanHelper();
		try	{
			List<String> al = Arrays.asList(sLists);
			ListType lt = groupType.equals("gene") ? ListType.GeneSymbol : ListType.PatientDID;
			if(action.equals("join"))	{
				helper.uniteLists(al, groupName, lt);
			}
			else	{
                if(helper.isIntersection(al)){
                    helper.intersectLists(al, groupName, lt);
                }
                else results = "fail";
			}
		}
		catch(Exception e){
			results = "fail";
		}
		res.put("results", results);
		res.put("groupType", groupType);
		res.put("action", action);
		
		return res.toString();
	}

}
