/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.struts.action;

 
//import gov.nih.nci.ispy.web.struts.form.LoginForm;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.columbia.c2b2.ispy.web.struts.form.RegisterForm;
 
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.columbia.c2b2.ispy.util.*;

public final class RegisterAction extends Action
{
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
   

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		RegisterForm f = (RegisterForm) form;
		 
          if (f.getAction() != null && f.getAction().equals(Constants.ACTION_SUBMIT_REGISTRATION) && f.getSubmitSuccess()== true)
		      return (mapping.findForward("success"));	 
          else
        	  return (mapping.findForward("failure"));	  
		
	}
}
