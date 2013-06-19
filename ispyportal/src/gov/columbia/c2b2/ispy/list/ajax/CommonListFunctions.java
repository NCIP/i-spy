/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.list.ajax;

import gov.columbia.c2b2.ispy.list.ListManager;
import gov.columbia.c2b2.ispy.list.ListOrigin;
import gov.columbia.c2b2.ispy.list.ListSubType;
import gov.columbia.c2b2.ispy.list.ListType;
import gov.columbia.c2b2.ispy.list.ListValidator;
import gov.columbia.c2b2.ispy.list.UserListN;
import gov.columbia.c2b2.ispy.list.ListItem;
import gov.columbia.c2b2.ispy.list.UserListBeanHelper;
import gov.columbia.c2b2.ispy.list.UserListBean;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;
//import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.columbia.c2b2.ispy.list.ShareList;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.ispy.util.ProcessHelper;
import gov.nih.nci.ispy.util.UserGroupBean;
import gov.nih.nci.ispy.util.UserListHelperDB;
import gov.columbia.c2b2.ispy.list.ListToDBFromFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

public class CommonListFunctions {

	
	public CommonListFunctions() {}
	
	public static String getListAsList(ListType ty){
		
		JSONObject res = new JSONObject();
		res.put("listType", ty.toString());
		
		String results = "";
		
		HttpSession session = ExecutionContext.get().getSession(false);
		UserListBeanHelper helper = new UserListBeanHelper(session);
                      
        List patientLists = helper.getLists(ty);
        if (!patientLists.isEmpty()) {
            for (int i = 0; i < patientLists.size(); i++) {
                UserListN list = (UserListN) patientLists.get(i);
                ListManager uploadManager = (ListManager) ListManager.getInstance();
                Map paramMap = uploadManager.getParams(list);
                String commas = StringUtils.join(list.getList().toArray(), ",");
                String sty = list.getListOrigin()!=null && !list.getListOrigin().equals(ListOrigin.Default) ? "color:#A90101" : "color:#000";
                results += ("<li id='" + paramMap.get("listName") + "' title='"+commas+"' style='"+sty+"'>"+paramMap.get("listName")+"</li>");
            }
        } else {
            results = "";
        }
        res.put("LIs", results);
		return res.toString();
	}
	
	/*
    public static String getPatientListAsList()	{
		return getListAsList(ListType.PatientDID);
	}
	
	public static String getGeneListAsList()	{
		return getListAsList(ListType.GeneSymbol);
	}
	*/
	
	//DWR ONLY: doesnt accept ListSubTypes (see below), just sets as ListSubType.Custom
	public static String createGenericList(ListType type, List<String> list, String name, ListValidator lv)	{
		//no duplicates
		HashSet<String> h = new HashSet<String>();
		for (int i = 0; i < list.size(); i++)
			h.add(list.get(i).trim());
		List<String> cleanList = new ArrayList<String>();
		for(String n : h)	{
			cleanList.add(n);
		}
		
		String success = "fail";
		ListManager um = ListManager.getInstance();
        
		try	{
			UserListN mylist = um.createList(type, name, cleanList, lv);
			//set the sub-type to custom 
			mylist.setListOrigin(ListOrigin.Custom);
			//only works thru DWR
			UserListBeanHelper ulbh = new UserListBeanHelper();
			if(ulbh!=null)	{
				ulbh.addList(mylist);
								
				JSONArray validList = new JSONArray();
				JSONArray invalidList = new JSONArray();
				JSONObject listContainer = new JSONObject();
				Set<ListItem> valid = mylist.getListItemsT();
				List<ListItem> invalid = mylist.getInvalidListItems();
				if(valid.size()>0){
					for(ListItem prcsv : valid){
						JSONObject item = new JSONObject();
						item.put("NAME", prcsv.getName());
						validList.add(item);
					}
					
				}
				if(invalid.size()>0){
					for(ListItem prcsi : invalid){
						JSONObject item = new JSONObject();
						item.put("NAME", prcsi.getName());
						invalidList.add(item);						
					}
					
				}
				listContainer.put("VALID", validList);
				listContainer.put("INVALID", invalidList);
				listContainer.put("LISTNAME", mylist.getName());
				
				return listContainer.toString();				
				
//				success = "pass";
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return success;
	}
	
	//DWR ONLY:  takes a list of ListSubTypes, also appends ListSubType.Custom
	public static String createGenericList(ListType type, ListSubType listSubType, List<String> list, String name, ListValidator lv)	{
		//no duplicates
		HashSet<String> h = new HashSet<String>();
		for (int i = 0; i < list.size(); i++)
			h.add(list.get(i).trim());
		List<String> cleanList = new ArrayList<String>();
		for(String n : h)	{
			cleanList.add(n);
		}
		String success = "fail";
		ListManager um = ListManager.getInstance();
		try	{
			UserListN mylist = um.createList(type, name, cleanList, lv);
			if(listSubType!=null){
				mylist.setListSubType(listSubType);
                mylist.setListOrigin(ListOrigin.Custom);
			}
			else	{
				mylist.setListOrigin(ListOrigin.Custom);
			}
			//only works thru DWR
			UserListBeanHelper ulbh = new UserListBeanHelper();
			if(ulbh!=null)	{
				ulbh.addList(mylist);
				success = "pass";
			}
		}
		catch (Exception e) {}
		return success;
	}

	//CAN be used outside DWR...takes a list of ListSubTypes, also appends ListSubType.Custom
	public static String createGenericListWithSession(ListType type, ListSubType listSubType, List<String> list, String name, ListValidator lv, HttpSession session)	{
		//no duplicates
//		UserListBean userListBean = new UserListBean();
//		UserListBeanHelper ulbh = new UserListBeanHelper();
		UserListN mylist = new UserListN();
		ListToDBFromFile upload = new ListToDBFromFile();
		HashSet<String> h = new HashSet<String>();
		for (int i = 0; i < list.size(); i++)
			h.add(list.get(i).trim());
		List<String> cleanList = new ArrayList<String>();
		for(String n : h)	{
			cleanList.add(n);
		}
		String success = "fail";
		ListManager um = ListManager.getInstance();
		try	{
			mylist = um.createList(type, name, cleanList, lv);
			if(listSubType!=null){
				mylist.setListSubType(listSubType);
                mylist.setListOrigin(ListOrigin.Custom);
			}
			else	{
				mylist.setListOrigin(ListOrigin.Custom);
			}
//			UserListBeanHelper ulbh = new UserListBeanHelper(session);
//			UserListBeanHelper ulbh = new UserListBeanHelper();
			
	/* the code added to store the list from file*/
			upload.loadListFromFile(name, type, mylist, session);
		
			
			
			
			JSONArray validList = new JSONArray();
			JSONArray invalidList = new JSONArray();
			JSONObject listContainer = new JSONObject();
			Set<ListItem> valid = mylist.getListItemsT();
			List<ListItem> invalid = mylist.getInvalidListItems();
			if(valid.size()>0){
				for(ListItem prcsv : valid){
					JSONObject item = new JSONObject();
					item.put("NAME", prcsv.getName());
					validList.add(item);
				}
				
			}
			if(invalid.size()>0){
				for(ListItem prcsi : invalid){
					JSONObject item = new JSONObject();
					item.put("NAME", prcsi.getName());
					invalidList.add(item);						
				}
				
			}
			listContainer.put("VALID", validList);
			listContainer.put("INVALID", invalidList);
			listContainer.put("LISTNAME", mylist.getName());
			
			success = listContainer.toString();
	
	/*
			UserListN newList = new UserListN(name,type,list,new ArrayList<String>(),new Date());
	    	ProcessHelper getUserID = (ProcessHelper) SpringContext.getBean("processHelper");

	    	mylist.setUserId(getUserID.getLoginNameCurrent());
			UserListHelperDB dbPrcs = (UserListHelperDB) SpringContext.getBean("userListHelperDB");
	        dbPrcs.dataBasePrcs(newList);	
		*/	
			
	/*                                              */		
			
//			ulbh.addList(mylist);
//			success = "pass";
		}
		catch (Exception e) {}
//		return mylist;
		return success;
	}
	
	public static String exportListasTxt(String id, HttpSession session){
		String txt = "";
		UserListBeanHelper helper = new UserListBeanHelper(session);
        List<String> listItems = helper.getItemsFromList(id);
        txt = StringUtils.join(listItems.toArray(), "\r\n");
		return txt;
	}


	public static String getAllLists(List<String> listTypeList, Integer share){
		
		UserListBeanHelper helper = null;
        
		if(share>0){
			helper = new UserListBeanHelper(share);
		}else{
			helper = new UserListBeanHelper();
		}
        String shareDate = "";
		HttpSession session = ExecutionContext.get().getSession(false);
//		String userID = (String) session.getAttribute("name");
		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
        User currentUser = (User) session.getAttribute("currentUser");		

		UserGroupBean grLoader = (UserGroupBean) SpringContext
				.getBean("userGroupBean");

		ArrayList<GroupManager> groups = (ArrayList<GroupManager>) grLoader
				.getMap(currentUser.getUserId());
		
        JSONArray listContainerArray = new JSONArray();
        for(String listType : listTypeList)	{
//          String listType = "PatientDID";
	        Collection<String> myLists = new ArrayList<String>();
	        
	        JSONObject listContainer = new JSONObject();
	       
	        JSONArray myJSONLists = new JSONArray();
	        
//	        JSONArray myJSONmembers = new JSONArray();
	        
	        listContainer.put("listType", listType);
	        //which do we want to display differently in the UI
	        	        
	        myLists = helper.getGenericListNames(ListType.valueOf(listType));
	        
	        for(String listName : myLists) {
	        	List ids = new ArrayList();
	            UserListN ul = helper.getUserList(listName);
	            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.US);
	            if(ul!=null)  {
                    String listSubType ="";
                    if(ul.getListSubType()!=null){
                        listSubType = "Subtype: " + ul.getListSubType().toString() + " - ";
                    }
                    
                    JSONObject jsonListName = new JSONObject();
                    String listNotes = ul.getNotes();
                    if(ul.getNotes()!=null){                        
                        if(listNotes.length()>100){
                            listNotes = listNotes.substring(0,99);
                        }                        
                    }
                    
                    String style = "";
                    if(ul.getListOrigin()!=null) {
                        jsonListName.put("origin", ul.getListOrigin().toString());                        
                        if(ul.getListOrigin().equals(ListOrigin.Default)){
                            style = "color:#000000";
                            jsonListName.put("highlightType", style);
                        }
                        if(ul.getListOrigin().equals(ListOrigin.Custom)){
                            style = "color:#A90101";
                            jsonListName.put("highlightType", style);
                        }
                        
                    }
                    else{
                        jsonListName.put("highlightType", style);
                    }                
                    jsonListName.put("author", ul.getAuthor());
                   
                    jsonListName.put("notes", listNotes);
	                
	                jsonListName.put("listSubType", listSubType);
//	                jsonListName.put("listName", ul.getName().replaceAll("'", "&#180;").replaceAll("\"", "&#34;").replaceAll("\\\\", "/"));
//	                jsonListName.put("listName", ul.getName().replaceAll("'", "&#180;").replaceAll("\"", "\\\"").replaceAll("\\\\", "/"));
	                jsonListName.put("listName", ul.getName());
	                jsonListName.put("listID", ul.getId().toString());
                    if(ul.getDateCreated()!=null){
                        jsonListName.put("listDate", dateFormat.format(ul.getDateCreated()).toString());
                    }
	                jsonListName.put("itemCount", String.valueOf(ul.getItemCount()));
	                jsonListName.put("invalidItems", String.valueOf(ul.getInvalidList().size()));
	                
	                String commas = StringUtils.join(ul.getList().toArray(), ", ");
	                jsonListName.put("listItems", commas);
	                
	                
/* add list of groups the list shared with and opposite list     */
	                List<ShareList> sharesList = new ArrayList<ShareList>(ul.getListShares());
                	JSONArray listGrSharedWith = new JSONArray();
                	JSONArray listGrNotSharedWith = new JSONArray();
                	
                	JSONArray myJSONmembers = new JSONArray();
	                if(groups.size()>0){
	                	if(sharesList.size()>0){
	                	for(GroupManager groupL : groups){	                		
	                		int shareF = 0;
	                		for(ShareList shares : sharesList){
	                			if(groupL.getGrId().equals(shares.getGrId())){
	                				shareF=1;
	                                if(shares.getUpdateTime()!=null){
	                                    shareDate = dateFormat.format(shares.getUpdateTime()).toString();
	                                }
	                			}		
	                		}
	                		JSONObject shareListDisp = new JSONObject();
	                		shareListDisp.put("groupID", groupL.getGrId().toString());
	                		shareListDisp.put("groupName", groupL.getGrName());
	                		shareListDisp.put("shareDate", shareDate);
	  /*group process****************************************************/
	                		if(shareF>0){
	            			ArrayList<GroupMembers> members = new ArrayList<GroupMembers>(
	            					groupL.getMembers());
	            			
	            			if (members.size() > 0) {
	            				
	            				for (GroupMembers memberPrcs : members) {
	            					User getUid = new User();
	            					for(User usr: allUsers){
	            						if(usr.getUserId().equals(memberPrcs.getUsrId())){
	            							getUid = usr;
	            							
	    	            					JSONObject jsonMember = new JSONObject();
	    	            					jsonMember.put("memberID", memberPrcs.getMemberId()
	    	            							.toString());
	    	            					jsonMember.put("LastName", getUid.getLastName());
	    	            					jsonMember.put("FirstName", getUid.getFirstName());
	    	            					jsonMember.put("LoginName", getUid.getLoginName());
	    	            					jsonMember.put("UserID", getUid.getUserId().toString());
	    	            					jsonMember.put("shareDate", shareDate);
	    	            					myJSONmembers.add(jsonMember);
	            							break;
	            							
	            						}
	            					}
/*	            					
	            					JSONObject jsonMember = new JSONObject();
	            					jsonMember.put("memberID", memberPrcs.getMemberId()
	            							.toString());
	            					jsonMember.put("LastName", getUid.getLastName());
	            					jsonMember.put("FirstName", getUid.getFirstName());
	            					jsonMember.put("LoginName", getUid.getLoginName());
	            					jsonMember.put("UserID", getUid.getUserId().toString());
	            					jsonMember.put("shareDate", shareDate);
	            					myJSONmembers.add(jsonMember);
*/
	            				}
	            				
	            			}
	                		}
	  /*end group process************************************************/
	                		if(shareF>0){
	                			listGrSharedWith.add(shareListDisp);
	                			
	                		}else{
	                			listGrNotSharedWith.add(shareListDisp);
	                		}
	                	}
	                } else{
	                	for(GroupManager groupL : groups){
	                		JSONObject shareListDisp = new JSONObject();
	                		shareListDisp.put("groupID", groupL.getGrId().toString());
	                		shareListDisp.put("groupName", groupL.getGrName());
	                		listGrNotSharedWith.add(shareListDisp);
	                	}
	                	
	                }
	                }
	                jsonListName.put("members", myJSONmembers);
	                jsonListName.put("groupSharedWith", listGrSharedWith);
	                jsonListName.put("groupNotSharedWith", listGrNotSharedWith);
	                myJSONLists.add(jsonListName);
	            }
	            
	        }
	        listContainer.put("listItems",myJSONLists);
	        listContainerArray.add(listContainer);
        }
        return listContainerArray.toString();
     }
	
	public static String uniteLists(String[] sLists, String groupName, String groupType, String action)	{
		
		JSONObject res = new JSONObject();
		String results = "pass";
		
		UserListBeanHelper helper = new UserListBeanHelper();
		try	{
			List<String> al = Arrays.asList(sLists);
			if(action.equals("join"))	{
				helper.uniteLists(al, groupName, ListType.valueOf(groupType));
			}
			else if(action.equalsIgnoreCase("difference"))	{
				results = helper.differenceLists(al, groupName, ListType.valueOf(groupType));
			}
			else	{
                if(helper.isIntersection(al)){
                    helper.intersectLists(al, groupName, ListType.valueOf(groupType));
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
	
	public static String[] parseListType(String combined)	{
		return StringUtils.split(combined, "|");
	}
}
