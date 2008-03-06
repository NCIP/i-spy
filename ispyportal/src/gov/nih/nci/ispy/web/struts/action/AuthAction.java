package gov.nih.nci.ispy.web.struts.action;

import org.apache.log4j.Logger;
import org.apache.struts.actions.*;
import org.apache.struts.action.*;

import gov.nih.nci.ispy.util.ispyConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ltd.getahead.dwr.ExecutionContext;
import javax.servlet.http.*;
import java.io.*;

public class AuthAction extends Action {

    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    private static String SEPERATOR = File.separator;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
    	
		HttpSession session = request.getSession();
		String auth = (String) session.getAttribute("auth");
		if(auth.equals("ADMIN")){
			return mapping.findForward("admin");
		} else{
			return mapping.findForward("reg");
		}       
    } //end blank
}
