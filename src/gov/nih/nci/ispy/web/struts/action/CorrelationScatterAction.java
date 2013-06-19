/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.CorrelationType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYCorrelationScatterQueryDTO;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.CorrelationScatterForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;





public class CorrelationScatterAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(CorrelationScatterAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    
    private UserCredentials credentials;
    
    
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
        
        CorrelationScatterForm correlationScatterForm = (CorrelationScatterForm) form;
        HttpSession session = request.getSession();
       ISPYCorrelationScatterQueryDTO correlationScatterQueryDTO = createISPYCorrelationScatterQueryDTO(correlationScatterForm,session);
       
       ISPYFindingsFactory factory = new ISPYFindingsFactory();
       Finding finding = null;
        try {
            finding = factory.createCorrelationScatterFinding(correlationScatterQueryDTO,session.getId(),correlationScatterQueryDTO.getQueryName());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (FrameworkException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                
        return mapping.findForward("viewResults");
    }
    
    
    private ISPYCorrelationScatterQueryDTO createISPYCorrelationScatterQueryDTO(CorrelationScatterForm correlationScatterForm, HttpSession session) {
        String sessionId = session.getId();
        UserListBeanHelper listHelper = new UserListBeanHelper(session);
        ISPYCorrelationScatterQueryDTO dto = new ISPYCorrelationScatterQueryDTO();
        
        dto.setQueryName(correlationScatterForm.getAnalysisResultName());
        
        //TODO: need null checks etc for all this
        //set patient userlist in dto
        dto.setPatientList(listHelper.getUserList(correlationScatterForm.getPatientGroup()));
        
        //set continuousTypes
        String[] uiDropdownString = correlationScatterForm.getXaxis().split("#");
        String myClassName = uiDropdownString[0];
        String myValueName = uiDropdownString[1];    
        Enum myType = EnumHelper.createType(myClassName,myValueName);
        dto.setContinuousType1((ContinuousType) myType);
        
        uiDropdownString = correlationScatterForm.getYaxis().split("#");
        myClassName = uiDropdownString[0];
        myValueName = uiDropdownString[1];    
        myType = EnumHelper.createType(myClassName,myValueName);
        dto.setContinuousType2((ContinuousType) myType);
        
        //set reporters
        if(!correlationScatterForm.getReporterX().equalsIgnoreCase("none")){
            dto.setReporter1Name(correlationScatterForm.getReporterX());
            //set gene name
            dto.setGeneX(correlationScatterForm.getGeneX());
            
            //set platform type
            uiDropdownString = correlationScatterForm.getPlatformX().split("#");
            myClassName = uiDropdownString[0];
            myValueName = uiDropdownString[1];    
            myType = EnumHelper.createType(myClassName,myValueName);
            dto.setPlatformTypeX((ArrayPlatformType) myType);
        }
        
        if(!correlationScatterForm.getReporterY().equalsIgnoreCase("none")){
            dto.setReporter2Name(correlationScatterForm.getReporterY());
            //set gene name
            dto.setGeneY(correlationScatterForm.getGeneY());
            
            //set platform type
            uiDropdownString = correlationScatterForm.getPlatformY().split("#");
            myClassName = uiDropdownString[0];
            myValueName = uiDropdownString[1];    
            myType = EnumHelper.createType(myClassName,myValueName);
            dto.setPlatformTypeY((ArrayPlatformType) myType);
        }
        
        //set correlationType
        uiDropdownString = correlationScatterForm.getCorrelationMethod().split("#");
        myClassName = uiDropdownString[0];
        myValueName = uiDropdownString[1];    
        myType = EnumHelper.createType(myClassName,myValueName);
        dto.setCorrelationType((CorrelationType) myType);
        
        return dto;
    }


    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        CorrelationScatterForm correlationScatterForm = (CorrelationScatterForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        HttpSession session = request.getSession();
        
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(session);        
        correlationScatterForm.setPatientGroupCollection(clinicalGroupRetriever.getClinicalGroupsCollection());
        
        
        return mapping.findForward("backToCorrelationScatter");
    }
   

        
    
    
    
}
