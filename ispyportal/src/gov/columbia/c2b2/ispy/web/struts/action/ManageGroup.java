/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.struts.action;
import gov.nih.nci.ispy.util.ispyConstants;


import gov.columbia.c2b2.ispy.web.struts.form.GrLoadForm;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.actions.*;
import org.apache.struts.action.*;


public final class ManageGroup extends DispatchAction {
	
	   private static final long serialVersionUID = 1L;
	   private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);

	
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	{
		ActionErrors errors=new ActionErrors();
		
		HttpSession session = request.getSession();
		
		ServletContext context = session.getServletContext();
				
		GrLoadForm f = (GrLoadForm) form;
		
        return mapping.findForward("success");
	}
}
