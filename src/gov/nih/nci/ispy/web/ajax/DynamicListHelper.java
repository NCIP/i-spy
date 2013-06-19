/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

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
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.lists.ajax.CommonListFunctions;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;


import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicListHelper {
/**
 * Basically a wrapper for app-commons/application/lists/ajax/CommonListFunctions
 * except this specifically sets the ListValidator for this context and passes it off
 * to the CommonListFunctions
 *
 */
	
	public DynamicListHelper() {}
	
    public static String getPatientListAsList()	{
		return CommonListFunctions.getListAsList(ListType.PatientDID);
	}
	
	public static String getGeneListAsList()	{
		return CommonListFunctions.getListAsList(ListType.Gene);
	}
	
	public static String createGenericList(String listType, List<String> list, String name) throws OperationNotSupportedException	{
        try {
            String[] tps = CommonListFunctions.parseListType(listType);
            //tps[0] = ListType
            //tps[1] = ListSubType (if not null)
            ListType lt = ListType.valueOf(tps[0]);
            if(tps.length > 1 && tps[1] != null){
                //create a list out of [1]
                ArrayList<ListSubType> lst = new ArrayList();
                lst.add(ListSubType.valueOf(tps[1]));
                ISPYListValidator lv = new ISPYListValidator(ListType.valueOf(tps[0]), ListSubType.valueOf(tps[1]), list);
                return CommonListFunctions.createGenericList(lt, lst, list, name, lv);
            }
            else if(tps.length >0 && tps[0] != null)    {
                //no subtype, only a primary type - typically a PatientDID then
                ISPYListValidator lv = new ISPYListValidator(ListType.valueOf(tps[0]), ListSubType.Custom, list);
                return CommonListFunctions.createGenericList(lt, list, name, lv);
            }
            else    {
                //no type or subtype, not good, force to clinical in catch                
                throw new Exception();
            }
        }
        catch(Exception e)  {
            //try as a patient list as default, will fail validation if its not accepted
            return CommonListFunctions.createGenericList(ListType.PatientDID, list, name, new ISPYListValidator(ListType.PatientDID, list));
        }
    }
	/*
	public static String createGenericList(ListType type, String[] list, String name){
		return CommonListFunctions.createGenericList(type, list, name, new ISPYListValidator());
	}
	*/
	public static String createPatientList(List<String> list, String name) throws OperationNotSupportedException{
        ISPYListValidator listValidator = new ISPYListValidator(ListType.PatientDID,list);
		return CommonListFunctions.createGenericList(ListType.PatientDID, list, name, listValidator);
	}

	public static String createPatientList(String[] list, String name) throws OperationNotSupportedException{ 
        ISPYListValidator listValidator = new ISPYListValidator(ListType.PatientDID,Arrays.asList(list));
		return CommonListFunctions.createGenericList(ListType.PatientDID, Arrays.asList(list), name, listValidator);
	}
	
	public static String getAllLists()	{
		//create a list of allowable types
		ArrayList listTypesList = new ArrayList();
		for(ListType l  : ListType.values())	{
			listTypesList.add(l.toString());
		}
		//call CommonListFunctions.getAllLists(listTypesList);
		return CommonListFunctions.getAllLists(listTypesList);
	}
	
	public static String createGeneList(List<String> list, String name) throws OperationNotSupportedException{
        ISPYListValidator listValidator = new ISPYListValidator(ListType.Gene,list);
        return CommonListFunctions.createGenericList(ListType.Gene, list, name, listValidator);
	}

	public static String exportListasTxt(String name, HttpSession session){
		return CommonListFunctions.exportListasTxt(name, session);
	}

	public static String getAllPatientLists()	{
		List<String> lt = new ArrayList();
		lt.add(ListType.PatientDID.toString());
		return CommonListFunctions.getAllLists(lt);
	}
	
	
	public static String getAllGeneLists()	{
		List<String> lt = new ArrayList();
		lt.add(ListType.Gene.toString());
		return CommonListFunctions.getAllLists(lt);
	}
	/*
	public static String getAllLists(String listType){
        return CommonListFunctions.getAllLists(listType);
     }
	*/
	
	public static String uniteLists(String[] sLists, String groupName, String groupType, String action)	{	
		return CommonListFunctions.uniteLists(sLists, groupName, groupType, action);
	}
}
