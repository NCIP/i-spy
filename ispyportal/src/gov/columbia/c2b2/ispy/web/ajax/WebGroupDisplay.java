/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.ajax;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.ispy.util.UserGroupBean;
import gov.nih.nci.ispy.util.UserListHelperDB;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.columbia.c2b2.ispy.web.ajax.CommonListFunctions;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.columbia.c2b2.ispy.fileLoad.LoadLog;
import gov.columbia.c2b2.ispy.fileLoad.LogFileContent;
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
	
	public static String getFile(String fileID)	{
		//create a list of allowable types		
//System.out.println("FEILD"+fileID);
		return CommonListFunctions.getFileContentById(fileID);
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
    	String outOfValidation;

        JSONObject jsonMess = new JSONObject();
        String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
        String outDir= System.getProperty("gov.c2b2.columbia.ispyportal.outProcDir");
        HttpSession session = ExecutionContext.get().getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
    	respond = CommonListFunctions.processFile(input, validScript, uploadScript);

    	content = CommonListFunctions.getContentOfOut(respond);
    	jsonMess.put("message", content);
    	outOfValidation=CommonListFunctions.mvOutFile(respond);
    	
	    LoadLog entry = new LoadLog();
	    entry.setFileName(input);
	    entry.setUsrId(currentUser.getUserId());
	    entry.setValidScriptName(validScript);
	    entry.setUpdateDate(new Timestamp(new Date().getTime()));
	    entry.setUploadStatus('P');
	    entry.setProcOutFileName(outOfValidation);
	    
		LoadLogBean loadLogRec = (LoadLogBean)SpringContext.getBean("loadLogBean");
		ArrayList<LogFileContent> files = new ArrayList<LogFileContent>();
		
	    LogFileContent fileContent = new LogFileContent();   
	    fileContent.setFileName(validScript);
	    fileContent.setFileContent(CommonListFunctions.getContentOfFile(procDir, validScript));
	    files.add(fileContent);
	    fileContent = new LogFileContent();
	    fileContent.setFileName(outOfValidation);
	    fileContent.setFileContent(CommonListFunctions.getContentOfFile(outDir, outOfValidation));
	    files.add(fileContent);

	    Long recID = loadLogRec.insertLogRec(entry, files);
 	
    	session.setAttribute("inputFileName", input);
    	session.setAttribute("uploadScriptName", uploadScript);
    	session.setAttribute("RecordID", recID);
    	return jsonMess.toString();
 
    }
    
    public static String processConfUpload(){
    	String respond;
    	Long numOfRecords;
    	String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
        JSONObject jsonMess = new JSONObject();
    	HttpSession session = ExecutionContext.get().getSession(false);
    	String uploadScript = (String) session.getAttribute("uploadScriptName");
    	String input = (String) session.getAttribute("inputFileName");
		String userID = (String) session.getAttribute("name");
		Long recID = (Long) session.getAttribute("RecordID");
        String extension = new Timestamp(new Date().getTime()).toString();
        String time = extension.replace(' ', '-');
        time = time.replaceAll(":", "-");
        String outFileName = userID+time;
    	
        respond = CommonListFunctions.processUploadInput(uploadScript, outFileName, input);
		CommonListFunctions.rmOutFile("out.upload");
	    LoadLogBean loadLogRec = (LoadLogBean)SpringContext.getBean("loadLogBean");
	    LoadLog entry = loadLogRec.getLoadLogById(recID); 
	    entry.setUploadScriptName(uploadScript);
	    entry.setUpdateDate(new Timestamp(new Date().getTime()));
	    entry.setUploadStatus('S');
	    entry.setoutFileName(outFileName);

		String[] process = respond.split(" ");
		if(process[22].equals("logical") && process[23].equals("record") && process[24].equals("count")){
			numOfRecords = Long.parseLong(process[25].substring(0, process[25].indexOf("\n")));
	        entry.setNumRecs(numOfRecords);
			jsonMess.put("messageF", "The file was uploaded to database successfully");
		} else{
	        entry.setNumRecs(Long.parseLong("0"));
			jsonMess.put("messageF", "The Errors returned by upload script");
		}
			
		ArrayList<LogFileContent> files = new ArrayList<LogFileContent>(entry.getFiles());
	    LogFileContent fileContent = new LogFileContent();
	    
	    fileContent.setLogId(recID);
	    fileContent.setFileName(uploadScript);
	    fileContent.setFileContent(CommonListFunctions.getContentOfFile(procDir, uploadScript));
	    files.add(fileContent);
	    fileContent = new LogFileContent();
	    fileContent.setLogId(recID);
	    fileContent.setFileName(outFileName);
	    fileContent.setFileContent(respond);
	    files.add(fileContent);

		
		loadLogRec.insertLogRec(entry, files);
		return jsonMess.toString();
    }
}