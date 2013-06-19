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
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LossOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.protein.ProteinBiomarkerCriteia;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.helper.IHCRetriever;
import gov.nih.nci.ispy.web.struts.form.IHCLossOfExpressionQueryForm;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;





public class IHCLossOfExpressionQueryAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(IHCLossOfExpressionQueryAction.class);
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
    	
    	IHCLossOfExpressionQueryForm ihcLossOfExpQueryForm = (IHCLossOfExpressionQueryForm) form;
        String sessionId = request.getSession().getId();
        HttpSession session = request.getSession();
       
        LossOfExpressionIHCFindingCriteria lossOfExpressionIHCFindingCriteria = createLossOfExpressionIHCFindingCriteria(ihcLossOfExpQueryForm, session);
        
        // in ISPYFindingsFactory objects, all the findings are created, such as clinical, ihc, class comparison etc        
        ISPYFindingsFactory factory = new ISPYFindingsFactory();
        factory.createIHCLossOfExpressionFinding(lossOfExpressionIHCFindingCriteria, sessionId, lossOfExpressionIHCFindingCriteria.getQueryName());
        return mapping.findForward("viewResults");        
        
     
    }
    
    private LossOfExpressionIHCFindingCriteria createLossOfExpressionIHCFindingCriteria(IHCLossOfExpressionQueryForm ihcLossOfExpQueryForm, HttpSession session) {
    	 LossOfExpressionIHCFindingCriteria dto = new LossOfExpressionIHCFindingCriteria();
         SpecimenCriteria theSPCriteria = new SpecimenCriteria();
         ProteinBiomarkerCriteia thePBCriteria = new ProteinBiomarkerCriteia();
    	 UserListBeanHelper helper = new UserListBeanHelper(session);
    	 UserList myCurrentList;
    	 
    	 
    	 dto.setQueryName(ihcLossOfExpQueryForm.getAnalysisResultName());
    	 
    	 //set custom patient group    	 
    	 if(ihcLossOfExpQueryForm.getPatientGroup()!=null && !ihcLossOfExpQueryForm.getPatientGroup().equals("none")){    		
             Set<String> tempRestrainingPatientDIDs = new HashSet<String>();
             myCurrentList = helper.getUserList(ihcLossOfExpQueryForm.getPatientGroup());
             if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                 tempRestrainingPatientDIDs.addAll(myCurrentList.getList());
             }
          theSPCriteria.setPatientIdentifierCollection(tempRestrainingPatientDIDs);
         }
    	 
    	 //set time point          
         if(ihcLossOfExpQueryForm.getTimepoints()!=null && ihcLossOfExpQueryForm.getTimepoints().length>0){        	 
             Set<String> tpSet = new HashSet<String>();
             String[] tps = ihcLossOfExpQueryForm.getTimepoints();
             for(int i=0; i<tps.length;i++){
            	 Enum myType = EnumHelper.createType(TimepointType.class.getName(),tps[i]);
                 myType = (TimepointType) myType;
            	 tpSet.add(myType.name());
                 myCurrentList = helper.getUserList(tps[i]);                
             } 
          theSPCriteria.setTimeCourseCollection(tpSet);          
         }         
         
    	 
         // set biomarkers          
         if(ihcLossOfExpQueryForm.getBiomarkers()!=null && ihcLossOfExpQueryForm.getBiomarkers().length>0){
             String[] bioMarkers = ihcLossOfExpQueryForm.getBiomarkers();
             Set<String> bioSet = new HashSet<String>();          
             for(int i=0; i<bioMarkers.length;i++){
            	 String bioMarker = (String)bioMarkers[i];
                 bioSet.add(bioMarker);
            	 
             }
          thePBCriteria.setProteinNameCollection(bioSet);   
          dto.setProteinBiomarkerCrit(thePBCriteria);    
         }    
         
         //set invasive range
         if(new Integer(ihcLossOfExpQueryForm.getInvasiveRange())!=null && !ihcLossOfExpQueryForm.getInvasiveRangeOperator().equalsIgnoreCase("none")){
             String operatorString= EnumHelper.getEnumTypeToString(ihcLossOfExpQueryForm.getInvasiveRangeOperator(),Operator.values());
             if(operatorString!=null) {                 
                 dto.setInvasiveSumOperator(operatorString);
             }
             dto.setInvasiveSum(new Integer(ihcLossOfExpQueryForm.getInvasiveRange()));
         }
         
         
         //set benign range
         if(new Integer(ihcLossOfExpQueryForm.getBenignRange())!=null && !ihcLossOfExpQueryForm.getBenignRangeOperator().equalsIgnoreCase("none")){
             String operatorString= EnumHelper.getEnumTypeToString(ihcLossOfExpQueryForm.getBenignRangeOperator(),Operator.values());
             if(operatorString!=null) {                 
                 dto.setBenignSumOperator(operatorString);
             }
             dto.setBenignSum(new Integer(ihcLossOfExpQueryForm.getBenignRange()));
         }
         
         //set result code
         if(ihcLossOfExpQueryForm.getLossResult()!=null && ihcLossOfExpQueryForm.getLossResult().length>0){
             String[] codes = ihcLossOfExpQueryForm.getLossResult();
             Set<String> lossSet = new HashSet<String>();          
             for(int i=0; i<codes.length;i++){
                 String code = (String)codes[i];
                 lossSet.add(code);
             }
             dto.setResultCodeCollection(lossSet);    
          }
         
         
         dto.setSpecimenCriteria(theSPCriteria);
         
    	 return  dto;
    	 
    }
  
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	IHCLossOfExpressionQueryForm ihcQueryForm = (IHCLossOfExpressionQueryForm) form;        
        HttpSession session = request.getSession();
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(session);        
        ihcQueryForm.setPatientGroupCollection(clinicalGroupRetriever.getClinicalGroupsCollection());
        IHCRetriever ihcRetriever = new IHCRetriever(session);
        ihcQueryForm.setBiomarkersCollection(ihcRetriever.getLossBiomarkers()); 
        ihcQueryForm.setLossCollection(ihcRetriever.ihcLossResultCodes());
        
        return mapping.findForward("backToIHCLossQuery");
    }
        
    
    
    
}
