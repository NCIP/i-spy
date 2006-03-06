package gov.nih.nci.ispy.web.struts.action;


import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.struts.form.LoginForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
