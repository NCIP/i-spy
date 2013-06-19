/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.struts.form.FISHQueryForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;





public class FISHQueryAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(FISHQueryAction.class);
    private UserCredentials credentials;
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    
    /**
     * Method submittal
     * 
     * @param ActionMapping
     *            mapping
     * @param ActionForm
     *            form
     * @param HttpServletRequest
     *            request
     * @param HttpServletResponse
     *            response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FISHQueryForm fishQueryForm = (FISHQueryForm) form;
        String sessionId = request.getSession().getId();
        HttpSession session = request.getSession();
               
        return mapping.findForward("viewResults");
    }
  
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        FISHQueryForm fishQueryForm = (FISHQueryForm) form;
        String sessionId = request.getSession().getId(); 
        HttpSession session = request.getSession();
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(session);        
        fishQueryForm.setPatientGroupCollection(clinicalGroupRetriever.getCustomPatientCollection());
        
        return mapping.findForward("backToFISHQuery");
    }
        
    
    
    
}
