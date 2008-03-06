package gov.columbia.c2b2.ispy.web.ajax;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.ispy.util.UserGroupBean;
import gov.nih.nci.ispy.util.UserListHelperDB;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.columbia.c2b2.ispy.web.ajax.CommonListFunctions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import gov.columbia.c2b2.ispy.fileLoad.LoadLog;
import gov.nih.nci.ispy.util.LoadLogBean;
import java.sql.Blob;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

public class WebGroupDisplay {
/**
 * Basically a wrapper for app-commons/application/lists/ajax/CommonListFunctions
 * except this specifically sets the ListValidator for this context and passes it off
 * to the CommonListFunctions
 *
 */
	private UserGroupBean userGroupBean = new UserGroupBean();

	public WebGroupDisplay() {}
	
	

	public static String getAllGroups()	{
		//create a list of allowable types		
		return CommonListFunctions.getAllGroups();
	}
	
	public static String getAllUploadLogs()	{
		//create a list of allowable types		
		return CommonListFunctions.getAllUplodaInfo();
	}
	
    public static String getUsersList(){
    	return CommonListFunctions.getAllUsers();
    }
    
    public static String getAllLogs(){
    	return CommonListFunctions.getAllLogs();
    }
    
    public Long removeGroupFromAjax(Long groupID){
    	CommonListFunctions.removeGroup(groupID);
    	return groupID;
    }
    
    public void deleteSelectedMembers (Long groupID, String[] members){
    	CommonListFunctions.removeMembers(groupID, members);
    }
    
    public void addSelectedMembers(Long groupID, String grName, String[] members){
    	CommonListFunctions.addMembersToGroup(groupID, grName, members);
    }

    public void deleteSelectedRecs(String[] members){
    	CommonListFunctions.deleteSelectedRecs(members);
    }
    
    public static String getInputFile(String ext){
    	return CommonListFunctions.getInputFile(ext);
    }
    
    public static String processFile(String input, String validScript, String uploadScript){
    	
    	
    	String respond;
    	String content;
        Long numOfRecords;
        JSONObject jsonMess = new JSONObject();
        
        HttpSession session = ExecutionContext.get().getSession(false);
    	
    	respond = CommonListFunctions.processFile(input, validScript, uploadScript);
 //   	session.setAttribute("outFile", respond);
    	
 /*   	
    	if("PROCESSED".equals(respond.substring(0, 9))){
    		jsonMess.put("message", "The file was processed with no error");
    	} else{
    		jsonMess.put("message", "The file was processed with found duplicates");
    	}
 */ 	
    	content = CommonListFunctions.getContentOfOut(respond);
    	jsonMess.put("message", content);
    	CommonListFunctions.mvOutFile(respond);
    	session.setAttribute("inputFileName", input);
    	session.setAttribute("validScriptName", validScript);
    	session.setAttribute("uploadScriptName", uploadScript);
    	session.setAttribute("FileOutContent", content);
    	return jsonMess.toString();
 
 //   	return "test";
    }
    
    public static String processConfUpload(){
    	String respond;
    	Long numOfRecords;
        JSONObject jsonMess = new JSONObject();
    	HttpSession session = ExecutionContext.get().getSession(false);
    	String uploadScript = (String) session.getAttribute("uploadScriptName");
		String userID = (String) session.getAttribute("name");
        String extension = new Timestamp(new Date().getTime()).toString();
        String time = extension.replace(' ', '-');
        time = time.replaceAll(":", "-");
        String outFileName = userID+time;
    	
		respond = CommonListFunctions.processUpload(uploadScript, outFileName);
		String[] process = respond.split(" ");
		if(process[22].equals("logical") && process[23].equals("record") && process[24].equals("count")){
	//		String nuOfR = process[25];
//			int index = process[25].indexOf("\n");
			CommonListFunctions.rmOutFile("out.dat");
			numOfRecords = Long.parseLong(process[25].substring(0, process[25].indexOf("\n")));
	        User currentUser = (User) session.getAttribute("currentUser");
	        String input = (String) session.getAttribute("inputFileName");
	        String validScript = (String) session.getAttribute("validScriptName");
	        String outContent = (String) session.getAttribute("FileOutContent");
	        LoadLog entry = new LoadLog();
	        entry.setFileName(input);
	        entry.setNumRecs(numOfRecords);
	        entry.setUsrId(currentUser.getUserId());
	        entry.setUploadScriptName(uploadScript);
	        entry.setValidScriptName(validScript);
	        entry.setUpdateDate(new Timestamp(new Date().getTime()));
	        entry.setUploadStatus('S');
//	        entry.setoutFileName((String) session.getAttribute("outFile"));
	        entry.setoutFileName(outFileName);
//	        entry.setOutContent(CommonListFunctions.getContentOfOutF(outFileName));
	        entry.setOutContent(respond);
			LoadLogBean loadLogRec = (LoadLogBean)SpringContext.getBean("loadLogBean");
			loadLogRec.insertLogRec(entry);
			jsonMess.put("messageF", "The file was uploaded to database successfully");
		} else{
			jsonMess.put("messageF", "The Errors returned by upload script");
		}

//		return jsonMess.toString();
		return "The file was uploaded to database successfully";
    }
}
