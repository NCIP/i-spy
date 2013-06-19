/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYCategoricalCorrelationQueryDTO;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.CategoricalCorrelationForm;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;





public class CategoricalCorrelationAction extends DispatchAction {
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
        
        CategoricalCorrelationForm categoricalCorrelationForm = (CategoricalCorrelationForm) form;
        HttpSession session = request.getSession();
       ISPYCategoricalCorrelationQueryDTO categoricalCorrelationQueryDTO = createISPYCategoricalCorrelationQueryDTO(categoricalCorrelationForm,session);
       
       ISPYFindingsFactory factory = new ISPYFindingsFactory();
       Finding finding = null;
        try {
            finding = factory.createCategoricalCorrelationFinding(categoricalCorrelationQueryDTO,session.getId(),categoricalCorrelationQueryDTO.getQueryName());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (FrameworkException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                
        return mapping.findForward("viewResults");
    }
    
    
    private ISPYCategoricalCorrelationQueryDTO createISPYCategoricalCorrelationQueryDTO(CategoricalCorrelationForm categoricalCorrelationForm, HttpSession session) {
        String sessionId = session.getId();
        UserList userList;
        ArrayList<UserList> patientLists = new ArrayList<UserList>();
        UserListBeanHelper listHelper = new UserListBeanHelper(session);
        ISPYCategoricalCorrelationQueryDTO dto = new ISPYCategoricalCorrelationQueryDTO();
        
        dto.setQueryName(categoricalCorrelationForm.getAnalysisResultName());
        
        //TODO: need null checks etc for all this
        //get userlists
        for(int i=0; i<categoricalCorrelationForm.getSelectedGroups().length;i++){            
            userList = listHelper.getUserList(categoricalCorrelationForm.getSelectedGroups()[i]);
            patientLists.add(userList);            
        }
        dto.setPatientLists(patientLists);
       
        //set continuousTypes
        String[] uiDropdownString = categoricalCorrelationForm.getYaxis().split("#");
        String myClassName = uiDropdownString[0];
        String myValueName = uiDropdownString[1];    
        Enum myType = EnumHelper.createType(myClassName,myValueName);
        dto.setContinuousType1((ContinuousType) myType);
      
        
                
        if(!categoricalCorrelationForm.getReporterY().equalsIgnoreCase("none")){
            dto.setReporter1Name(categoricalCorrelationForm.getReporterY());
            //set gene name
            dto.setGeneY(categoricalCorrelationForm.getGeneY());
            
            //set platform type
            uiDropdownString = categoricalCorrelationForm.getPlatformY().split("#");
            myClassName = uiDropdownString[0];
            myValueName = uiDropdownString[1];    
            myType = EnumHelper.createType(myClassName,myValueName);
            dto.setPlatformTypeY((ArrayPlatformType) myType);
        }
        
        return dto;
    }


    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        CategoricalCorrelationForm categoricalCorrelationForm = (CategoricalCorrelationForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        HttpSession session = request.getSession();
        
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(request.getSession());
        categoricalCorrelationForm.setExistingGroupsList(clinicalGroupRetriever.getClinicalGroupsCollection());
        
        
        return mapping.findForward("backToCategoricalCorrelation");
    }
   

        
    
    
    
}
