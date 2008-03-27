package gov.columbia.c2b2.ispy.web.ajax;

import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;
import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;
import gov.columbia.c2b2.ispy.info.LogInfo;
import gov.columbia.c2b2.ispy.fileLoad.LoadLog;
import gov.columbia.c2b2.ispy.fileLoad.LogFileContent;
import gov.nih.nci.security.UserProvisioningManager;


import gov.nih.nci.ispy.util.UserGroupBean;
import gov.nih.nci.ispy.util.UserInfoBean;
import gov.nih.nci.ispy.util.UserGroupHelper;
import gov.nih.nci.ispy.util.LogInfoBean;
import gov.nih.nci.ispy.util.UserInfoHelper;
import gov.nih.nci.ispy.util.UserListHelperDB;
import gov.nih.nci.ispy.util.LoadLogBean;
import gov.nih.nci.security.authorization.domainobjects.User;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;

import uk.ltd.getahead.dwr.ExecutionContext;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.security.SecurityManager;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
//import com.jcraft.jsch.UserInfo;
import gov.columbia.c2b2.ispy.fileLoad.RemoteHelper;
import gov.columbia.c2b2.ispy.fileLoad.MyUserInfo;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommonListFunctions {

	public CommonListFunctions() {
	}

	public static String getAllGroups() {
		
		HttpSession session = ExecutionContext.get().getSession(false);
//		String userID = (String) session.getAttribute("name");
		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
		User currentUser = (User) session.getAttribute("currentUser");

		UserGroupBean grLoader = (UserGroupBean) SpringContext
				.getBean("userGroupBean");

		ArrayList<GroupManager> groups = (ArrayList<GroupManager>) grLoader
				.getMap(currentUser.getUserId());

//		ArrayList<UserInfo> users = getUserInfo();
		
		JSONArray groupContainerArray = new JSONArray();

		String lName = "";
		if(groups.size()>0){
		for (GroupManager groupPrcs : groups) {
			JSONObject groupContainer = new JSONObject();

			JSONArray myJSONmembers = new JSONArray();
			JSONArray myJSONusers = new JSONArray();
			
//			groupContainer.put("groupName", groupPrcs.getGrName().replaceAll("'", "&#180;").replaceAll("\"", "&#34;").replaceAll("\\\\", "/"));
//			groupContainer.put("groupName", groupPrcs.getGrName().replaceAll("'", "&#180;").replaceAll("\"", "&#34;"));
			groupContainer.put("groupName", groupPrcs.getGrName());
			groupContainer.put("groupID", groupPrcs.getGrId().toString());

			ArrayList<GroupMembers> members = new ArrayList<GroupMembers>(
					groupPrcs.getMembers());
			if (members.size() > 0) {
				for (GroupMembers memberPrcs : members) {
					JSONObject jsonMember = new JSONObject();

					jsonMember.put("memberID", memberPrcs.getMemberId()
							.toString());

//					User getUid = new User();
//					getUid = memberPrcs.getUserInfo();
					User getUid = new User();
					for(User usr: allUsers){
						if(usr.getUserId().equals(memberPrcs.getUsrId())){
							getUid = usr;
							jsonMember.put("LastName", getUid.getLastName());
							jsonMember.put("FirstName", getUid.getFirstName());
							jsonMember.put("LoginName", getUid.getLoginName());
							jsonMember.put("UserID", getUid.getUserId().toString());
							myJSONmembers.add(jsonMember);
							break;
						}
					}
/*					
					jsonMember.put("LastName", getUid.getLastName());
					jsonMember.put("FirstName", getUid.getFirstName());
					jsonMember.put("LoginName", getUid.getLoginName());
					jsonMember.put("UserID", getUid.getUserId().toString());
					myJSONmembers.add(jsonMember);
*/
				}
				groupContainer.put("members", myJSONmembers);

				if (allUsers.size() > 0) {
					for (User usersPrcs : allUsers) {
						if ((FindUser(usersPrcs.getUserId(), members) < 1)) {
							JSONObject jsonUser = new JSONObject();
							jsonUser.put("LastName", usersPrcs.getLastName());
							jsonUser.put("FirstName", usersPrcs.getFirstName());
							jsonUser.put("LoginName", usersPrcs.getLoginName());
							jsonUser.put("UserID", usersPrcs.getUserId()
									.toString());
							myJSONusers.add(jsonUser);
						} else
							continue;
					}
					groupContainer.put("users", myJSONusers);
				}

			} else {
				groupContainer.put("members", "");
				if (allUsers.size() > 0) {
					for (User usersPrcs : allUsers) {
						JSONObject jsonUser = new JSONObject();
						jsonUser.put("LastName", usersPrcs.getLastName());
						jsonUser.put("FirstName", usersPrcs.getFirstName());
						jsonUser.put("LoginName", usersPrcs.getLoginName());
						jsonUser
								.put("UserID", usersPrcs.getUserId().toString());
						myJSONusers.add(jsonUser);
					}
					groupContainer.put("users", myJSONusers);
				}
			}

			groupContainerArray.add(groupContainer);
		}
		}
		return groupContainerArray.toString();

	}
	
	public static String getAllUsers() {
		HttpSession session = ExecutionContext.get().getSession(false);
		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
//		ArrayList<UserInfo> users = getUserInfo();
		
		JSONArray usersArray = new JSONArray();
		for (User usersPrcs : allUsers) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("LastName", usersPrcs.getLastName());
			jsonUser.put("FirstName", usersPrcs.getFirstName());
			jsonUser.put("LoginName", usersPrcs.getLoginName());
			jsonUser
					.put("UserID", usersPrcs.getUserId().toString());
			usersArray.add(jsonUser);
		}
		return usersArray.toString();
		
	}
	
	public static String getAllLogs() {
		ArrayList<LogInfo> logs = getLogInfo();
		
		JSONArray logsArray = new JSONArray();
		
		for (LogInfo logPrcs : logs) {
			JSONObject jsonLog = new JSONObject();
			jsonLog.put("userName", logPrcs.getUserName());
			jsonLog.put("groupName", logPrcs.getGroupName());
			jsonLog.put("listName", logPrcs.getListName());
			jsonLog.put("ACTION", logPrcs.getUserAction());
			logsArray.add(jsonLog);
		}

		return logsArray.toString();
		
	}
	

	
	private static ArrayList<LogInfo> getLogInfo (){
/*		
		HttpSession session = ExecutionContext.get().getSession(false);
		String userID = (String) session.getAttribute("name");
*/
		HttpSession session = ExecutionContext.get().getSession(false);
//		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
        User currentUser = (User) session.getAttribute("currentUser");
//        allUsers.add(currentUser);
        
		LogInfoBean usrLoader = (LogInfoBean) SpringContext.getBean("logInfoBean");
		ArrayList<LogInfo> logs = (ArrayList<LogInfo>) usrLoader.getLogs(currentUser.getUserId());
				
		return logs;
	}
	
	
	public static String getAllUplodaInfo() {
		
		LoadLogBean logLoader = (LoadLogBean) SpringContext.getBean("loadLogBean");
		ArrayList<LoadLog> logs = (ArrayList<LoadLog>) logLoader.getLoadLog();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.US);
		JSONArray loadLogsArray = new JSONArray();
		
		for (LoadLog logPrcs : logs) {
			JSONObject jsonLog = new JSONObject();
			jsonLog.put("recID", logPrcs.getRecId().toString());
			jsonLog.put("inputFileName", logPrcs.getFileName());
			jsonLog.put("validationScript", logPrcs.getValidScriptName());
			if(null !=logPrcs.getUploadScriptName()){
				jsonLog.put("uploadScript", logPrcs.getUploadScriptName());
			} else jsonLog.put("uploadScript", "N/A");
			if(null != logPrcs.getoutFileName()){
				jsonLog.put("outFileName", logPrcs.getoutFileName());
			} else jsonLog.put("outFileName", "N/A");
//			jsonLog.put("uploadStatus", logPrcs.getUploadStatus());
			if(null != logPrcs.getNumRecs()){
				jsonLog.put("numOfRecords", logPrcs.getNumRecs().toString());
			} else jsonLog.put("numOfRecords", "N/A");
			jsonLog.put("logDate", dateFormat.format(logPrcs.getUpdateDate()).toString());
			
			JSONArray fileContArray = new JSONArray();
			Set<LogFileContent> theFiles = logPrcs.getFiles();
			for(LogFileContent contPrcs : theFiles){
				JSONObject jsonLogFile = new JSONObject();
				if(null != contPrcs.getRecId()){
					jsonLogFile.put("recID", contPrcs.getRecId().toString());
				}
				if(null != contPrcs.getLogId()){
					jsonLogFile.put("logID", contPrcs.getLogId().toString());
				}
				if(null != contPrcs.getFileName()){
					jsonLogFile.put("logName", contPrcs.getFileName());
				}
				if(null != contPrcs.getFileContent()){
					jsonLogFile.put("fileContent", contPrcs.getFileContent());
				}
				fileContArray.add(jsonLogFile);
			}
			
			jsonLog.put("files", fileContArray);
			loadLogsArray.add(jsonLog);
		}

		return loadLogsArray.toString();
		
	}

	public static String getFileContentById(String fileID) {
		String fId = fileID.substring(1, (fileID.length() -1));
		Long id = Long.parseLong(fId.trim());
		LoadLogBean logLoader = (LoadLogBean) SpringContext.getBean("loadLogBean");
		LogFileContent file = (LogFileContent) logLoader.getFilebyID(id);
		
				JSONObject jsonLogFile = new JSONObject();

				if(null != file.getFileName()){
					jsonLogFile.put("fileName", file.getFileName());
				}
				if(null != file.getFileContent()){
					jsonLogFile.put("fileContent", file.getFileContent());
				}



		return jsonLogFile.toString();
		
	}	
	
	
	private static ArrayList<User> getUserInfo (){
		HttpSession session = ExecutionContext.get().getSession(false);
//		String userID = (String) session.getAttribute("name");
		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
		
/*
		UserInfoBean usrLoader = (UserInfoBean) SpringContext
				.getBean("userInfoBean");

		ArrayList<UserInfo> users = (ArrayList<UserInfo>) usrLoader
				.getUsers(userID);
*/
		return allUsers;
	}
	
	public static User getUserInfoByID (Long userID){
		HttpSession session = ExecutionContext.get().getSession(false);
		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
        User currentUser = (User) session.getAttribute("currentUser");		
		User returnUser = new User();
		if(currentUser.getUserId().equals(userID)){
			returnUser = currentUser;
		} else{
			for(User prcs : allUsers){
				if(prcs.getUserId().equals(userID)){
					returnUser = prcs;
					break;
				}
			}
		}
		
		
/*
		UserInfoBean usrLoader = (UserInfoBean) SpringContext.getBean("userInfoBean");
		UserInfo users = (UserInfo) usrLoader.getUsersByID(userID);
*/
		return returnUser;
	}
	
	public static String getInputFile(String ext) {
        String cmdout = "";
        RemoteHelper.connectMgc();
        String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
//           cmdout = RemoteHelper.sendCommand("ls -ltr; /ou1/home/bin/sqlldr ispy/wxdmbov@test control=/home/oracle/work/clin.ctl log=sample_load.log skip=1");
//        System.out.println(cmdout);
//        cmdout = RemoteHelper.sendCommand("cd upload/work; ls -r *."+ext);
        cmdout = RemoteHelper.sendCommand("cd "+procDir+"; ls -r *."+ext);
//        cmdout = RemoteHelper.sendCommand("/Users/bvd7001/work/test/process1.pl input.dat");
//        cmdout = RemoteHelper.sendCommand("/usr/local/SDK/jdk/bin/java RunCommand");
 //       cmdout = RemoteHelper.sendCommand(". ./.bash_profile; echo $ORACLE_SID; cd work; sqlldr ispy/wxdmbov@test control=clin.ctl log=sample_load.log skip=1");
        cmdout = cmdout.replaceAll("\n", "-");
        String[] inFiles = cmdout.split("-");
        
		JSONArray filesArray = new JSONArray();
		if(inFiles.length>0){
		for(int i=0;i<inFiles.length;i++){
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("FileName", inFiles[i]);
			filesArray.add(jsonUser);
		}
		}

		return filesArray.toString();
		
	}
	
	public static String processFile(String input, String validScript, String uploadScript) {
        String cmdout = "";
        String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
        RemoteHelper.connectMgc();
        cmdout = RemoteHelper.sendCommand("cd "+procDir+"; ./"+validScript+" "+input);
		return cmdout;
		
	}
	
	public static String getContentOfFile(String dir, String fileName) {
        String cmdout = "";
        RemoteHelper.connectMgc();
        cmdout = RemoteHelper.sendCommand("cd "+dir+"; cat "+fileName);        
		return cmdout;
		
	}
	
	public static String getContentOfOut(String fileName) {
		String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
        String cmdout = "";
        RemoteHelper.connectMgc();
        cmdout = RemoteHelper.sendCommand("cd "+procDir+"; cat "+fileName);        
		return cmdout;
		
	}
	
	public static String getContentOfOutF(String fileName) {
        String cmdout = "";
        String uploadDir= System.getProperty("gov.c2b2.columbia.ispyportal.uploadDir");
        RemoteHelper.connectMgc();
        cmdout = RemoteHelper.sendCommand("cd "+uploadDir+"; cat "+fileName);        
		return cmdout;
		
	}
	public static void rmOutFile(String fileName) {
		String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
        RemoteHelper.connectMgc();
        RemoteHelper.sendCommand("cd "+procDir+"; rm -f "+fileName);        
		
	}
	public static String processUpload(String uploadScript, String outFileName) {
		String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
		String dbUser= System.getProperty("gov.c2b2.columbia.ispyportal.dbuser");
		String dbPass= System.getProperty("gov.c2b2.columbia.ispyportal.dbpass");
		String dbSID= System.getProperty("gov.c2b2.columbia.ispyportal.dbsid");
		String outDir= System.getProperty("gov.c2b2.columbia.ispyportal.uploadDir");
        String cmdout = "";
        RemoteHelper.connectMgc();

        cmdout = RemoteHelper.sendCommand(". ./.bash_profile; cd "+procDir+"; sqlldr "+dbUser+"/"+dbPass+"@"+dbSID+" control="+uploadScript+" log="+outDir+"/"+outFileName+".log skip=0");
  
		return cmdout;
		
	}
	
	public static String processUploadInput(String uploadScript, String outFileName, String inputFile) {
		String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
		String dbUser= System.getProperty("gov.c2b2.columbia.ispyportal.dbuser");
		String dbPass= System.getProperty("gov.c2b2.columbia.ispyportal.dbpass");
		String dbSID= System.getProperty("gov.c2b2.columbia.ispyportal.dbsid");
		String outDir= System.getProperty("gov.c2b2.columbia.ispyportal.uploadDir");
		
        String cmdout = "";
        RemoteHelper.connectMgc();

        cmdout = RemoteHelper.sendCommand(". ./.bash_profile; cd "+procDir+"; sqlldr "+dbUser+"/"+dbPass+"@"+dbSID+" control="+uploadScript+" data="+inputFile+" log="+outDir+"/"+outFileName+".log skip=1");
  
		return cmdout;
		
	}
	public  static String mvOutFile(String outFile) {
		String procDir= System.getProperty("gov.c2b2.columbia.ispyportal.procDir");
		String outDir= System.getProperty("gov.c2b2.columbia.ispyportal.outProcDir");
        String cmdout = "";
        String extension = new Timestamp(new Date().getTime()).toString();
        String time = extension.replace(' ', '-');
        String newName = outFile+time;
        RemoteHelper.connectMgc();

        cmdout = RemoteHelper.sendCommand("cd "+procDir+"; mv -f "+outFile+" "+outDir+"/"+newName);
        return newName;
		
	}
	
private	static Integer FindUser(Long userID, ArrayList<GroupMembers> members){
		
		Integer flag = 0;
		for (GroupMembers memberPrcs : members) {
//			User getUid = new User();
//			getUid = memberPrcs.getUserInfo();
			Long id;
//			id = getUid.getUserId();
			id = memberPrcs.getUsrId();
			if(id.equals(userID)){ flag = 1;}
		}
//		flag = 1;
		return flag;
	}
	public static void removeGroup (Long groupID){

		HttpSession session = ExecutionContext.get().getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
		
		UserInfoHelper setLog = (UserInfoHelper) SpringContext.getBean("userInfoHelper");
		String[] refIds = setLog.getRefIdByGr(groupID);
		setLog.setLogInfo(currentUser.getUserId(), refIds, "GRDEL", groupID);
		
		UserGroupHelper grRemove = (UserGroupHelper) SpringContext
                    .getBean("userGroupHelper");
		grRemove.removeGroup(groupID);
		
		UserListHelperDB delSharedRef = (UserListHelperDB)SpringContext.getBean("userListHelperDB");
		delSharedRef.deleteAllSharesByGroup(groupID);
	}

	public static void removeMembers (Long groupID, String[] membersID){

				HttpSession session = ExecutionContext.get().getSession(false);
		        User currentUser = (User) session.getAttribute("currentUser");
				
				UserInfoHelper setLog = (UserInfoHelper) SpringContext.getBean("userInfoHelper");
				String[] refIds = setLog.getRefId(membersID);
				setLog.setLogInfo(currentUser.getUserId(), refIds, "GRDEL", groupID);
				
				UserGroupHelper grRemove = (UserGroupHelper) SpringContext
		        .getBean("userGroupHelper");
					grRemove.removeMember(groupID, membersID);
			}
	
	public static void deleteSelectedRecs (String[] membersID){
		
		LoadLogBean recRemove = (LoadLogBean) SpringContext
        .getBean("loadLogBean");
			recRemove.removeRecords(membersID);
	}
	
	public static void addMembersToGroup(Long groupID, String grName, String[] membersID){
		
		HttpSession session = ExecutionContext.get().getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");

		
		UserGroupHelper grAdd = (UserGroupHelper) SpringContext
        			.getBean("userGroupHelper");
		groupID=grAdd.addMembersPrcs(groupID, grName, currentUser.getUserId(), membersID);

		UserInfoHelper setLog = (UserInfoHelper) SpringContext.getBean("userInfoHelper");
		setLog.setLogInfo(currentUser.getUserId(), membersID, "GRADD", groupID);
	}

}
