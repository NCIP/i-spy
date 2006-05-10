package gov.nih.nci.ispy.web.struts.action;


import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListGenerator;
import gov.nih.nci.ispy.util.ISPYListManager;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.struts.form.LoginForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class LoginAction extends Action
{
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    private static String SEPERATOR = File.separator;
    private  ISPYListManager uploadManager =(ISPYListManager) ISPYListManager.getInstance();
    
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		LoginForm f = (LoginForm) form;
		String userName=f.getUserName();
		String password = f.getPassword();
		
		boolean valid = false;		
		//load the props file
		Properties props = new Properties();
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
        FileInputStream fsi = null;
        
	    try {
            fsi = new FileInputStream(context.getRealPath("WEB-INF")+ SEPERATOR + "users.properties"); 
	        logger.debug("loading props...");
	   		props.load(fsi);
	   		logger.debug("props file length: " + props.size());	
            fsi.close();
			
			String u = "";
			String p = "";			
		
				u = props.getProperty("username");
				p = props.getProperty("password");
								
				if(userName.equals(u) && password.equals(p))
				{
					//set something in the session here
					valid = true;

					session.setAttribute("logged", "yes");
                    session.setAttribute("name", userName);
                    UserListBean userListBean = new UserListBean();
                    /*
                     * instantiate pre-cooked user lists. Files are .txt files in our 
                     * directory.
                     */
                    File userFile = new File(ispyConstants.DEFAULT_PATIENT_USER_LIST1);
                    File userFile2 = new File(ispyConstants.DEFAULT_GENE_USER_LIST1);
                    List<String> patientUserList1 = UserListGenerator.generateList(userFile);
                    List<String> geneUserList1 = UserListGenerator.generateList(userFile2);
                    if(!patientUserList1.isEmpty()){
                        UserList myList = uploadManager.createList(ListType.PatientDID, "defaultPatientList", patientUserList1);
                        if(myList!=null){
                            userListBean.addList(myList);
                        }
                    }
                    if(!geneUserList1.isEmpty()){
                        UserList myList = uploadManager.createList(ListType.GeneSymbol, "defaultGeneList", geneUserList1);
                        if(myList!=null){
                            userListBean.addList(myList);
                        }
                    }
                    //add userListBean to session
                    session.setAttribute(ispyConstants.USER_LISTS,userListBean);
                    
					return (mapping.findForward("success"));	
				}
			
		} catch (Exception e) {
		    logger.error("Can't read user props");
		    logger.error(e);
            if(fsi != null)
                fsi.close();
          }
          finally   {
            if(fsi != null)
                fsi.close();
         }
		
		if(valid)
			return (mapping.findForward("success"));
		else
			return (mapping.findForward("failure"));
			
		
	}
}
