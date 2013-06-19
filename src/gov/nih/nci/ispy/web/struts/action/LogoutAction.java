package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.ispy.util.ispyConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




public final class LogoutAction extends Action
{
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    //private static PresentationTierCache _cacheManager = ApplicationFactory.getPresentationTierCache();
    public ActionForward execute(ActionMapping mapping, ActionForm form,
    								HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        try{
            session.invalidate(); 
            return (mapping.findForward("logout"));
        }
        catch(Exception e){
            logger.error("Can't read user props");
            logger.error(e);
            return (mapping.findForward("dontLogout"));
        }
        
    }
}
