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
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.protein.ProteinBiomarkerCriteia;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.helper.IHCRetriever;
import gov.nih.nci.ispy.web.struts.form.IHCLevelOfExpressionQueryForm;

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





public class IHCLevelOfExpressionQueryAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(IHCLevelOfExpressionQueryAction.class);
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
    	
    	IHCLevelOfExpressionQueryForm ihcLevelOfExpQueryForm = (IHCLevelOfExpressionQueryForm) form;
        String sessionId = request.getSession().getId();
        HttpSession session = request.getSession();
        
        // put values from the ui page to the IHCLevelOfExpressionQueryDTO
        //IHCLevelOfExpressionQueryDTO ihcLevelOfExpDataQueryDTO = createIHCLevelOfExpQueryDTO(ihcLevelOfExpQueryForm, session);
        LevelOfExpressionIHCFindingCriteria levelOfExpressionIHCFindingCriteria = createLevelOfExpressionIHCFindingCriteria(ihcLevelOfExpQueryForm, session);
        
        // in ISPYFindingsFactory objects, all the findings are created, such as clinical, ihc, class comparison etc        
        ISPYFindingsFactory factory = new ISPYFindingsFactory();
        factory.createIHCLevelOfExpressionFinding(levelOfExpressionIHCFindingCriteria, sessionId, levelOfExpressionIHCFindingCriteria.getQueryName());
        return mapping.findForward("viewResults");        
        
     
    }
    
    private LevelOfExpressionIHCFindingCriteria createLevelOfExpressionIHCFindingCriteria(IHCLevelOfExpressionQueryForm ihcLevelOfExpQueryForm, HttpSession session) {
    	 //IHCLevelOfExpressionQueryDTO dto = new IHCLevelOfExpressionQueryDTO();
         LevelOfExpressionIHCFindingCriteria dto = new LevelOfExpressionIHCFindingCriteria();
         SpecimenCriteria theSPCriteria = new SpecimenCriteria();
         ProteinBiomarkerCriteia thePBCriteria = new ProteinBiomarkerCriteia();
    	 UserListBeanHelper helper = new UserListBeanHelper(session);
    	 UserList myCurrentList;
    	 
    	 
    	 dto.setQueryName(ihcLevelOfExpQueryForm.getAnalysisResultName());
    	 
    	 //set custom patient group    	 
    	 if(ihcLevelOfExpQueryForm.getPatientGroup()!=null && !ihcLevelOfExpQueryForm.getPatientGroup().equals("none")){    		
             Set<String> tempRestrainingPatientDIDs = new HashSet<String>();
             myCurrentList = helper.getUserList(ihcLevelOfExpQueryForm.getPatientGroup());
             if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                 tempRestrainingPatientDIDs.addAll(myCurrentList.getList());
             }
          theSPCriteria.setPatientIdentifierCollection(tempRestrainingPatientDIDs);
         }
    	 
    	 //set time point          
         if(ihcLevelOfExpQueryForm.getTimepoints()!=null && ihcLevelOfExpQueryForm.getTimepoints().length>0){        	 
             Set<String> tpSet = new HashSet<String>();
             String[] tps = ihcLevelOfExpQueryForm.getTimepoints();
             for(int i=0; i<tps.length;i++){
            	 Enum myType = EnumHelper.createType(TimepointType.class.getName(),tps[i]);
                 myType = (TimepointType) myType;
            	 tpSet.add(myType.name());
                 myCurrentList = helper.getUserList(tps[i]);                
             } 
          theSPCriteria.setTimeCourseCollection(tpSet);          
         }         
         
    	 
         // set biomarkers          
         if(ihcLevelOfExpQueryForm.getBiomarkers()!=null && ihcLevelOfExpQueryForm.getBiomarkers().length>0){
             String[] bioMarkers = ihcLevelOfExpQueryForm.getBiomarkers();
             Set<String> bioSet = new HashSet<String>();          
             for(int i=0; i<bioMarkers.length;i++){
            	 String bioMarker = (String)bioMarkers[i];
                 bioSet.add(bioMarker);
            	 
             }
          thePBCriteria.setProteinNameCollection(bioSet);   
          dto.setProteinBiomarkerCrit(thePBCriteria);    
         }    
         
         // set stainIntensity         
         if(ihcLevelOfExpQueryForm.getStainIntensity()!=null && ihcLevelOfExpQueryForm.getStainIntensity().length>0){
             //EnumSet<IntensityOfStainType> stainIntensitySet = EnumSet.noneOf(IntensityOfStainType.class);
             Set<String> stainIntensitySet = new HashSet<String>();
             String[] stainIntensities = ihcLevelOfExpQueryForm.getStainIntensity();
             for(int i=0; i<stainIntensities.length;i++){                 
                     stainIntensitySet.add(stainIntensities[i]);                              
             }
             dto.setStainIntensityCollection(stainIntensitySet);
         }
         
            
         // set persent positive lower side
         if(new Integer(ihcLevelOfExpQueryForm.getLowPercentPositive())!= null){
        	 dto.setPercentPositiveRangeMin(new Integer(ihcLevelOfExpQueryForm.getLowPercentPositive()));      	
         
         }
         // set persent positive upper side
         if(new Integer(ihcLevelOfExpQueryForm.getUpPercentPositive())!= null){
        	 dto.setPercentPositiveRangeMax(new Integer(ihcLevelOfExpQueryForm.getUpPercentPositive()));      	
         
         }
         
         // set localization of stain           
         if(ihcLevelOfExpQueryForm.getStainLocalization()!=null && ihcLevelOfExpQueryForm.getStainLocalization().length>0){
             Set<String> localizationSet = new HashSet<String>();
             String[] localizations = ihcLevelOfExpQueryForm.getStainLocalization();
             for(int i=0; i<localizations.length;i++){                 
                  localizationSet.add(localizations[i]);                            
             }
             dto.setStainLocalizationCollection(localizationSet);
         }
         
         
        /* 
         * DO NOT HAVE DATA FOR DISTRIBUTION RIGHT NOW
         * set distribution of stain         
         if(ihcLevelOfExpQueryForm.getStainDistribution()!=null && ihcLevelOfExpQueryForm.getStainDistribution().length>0){
             Set<String> distributionSet = new HashSet<String>();
             String[] distributions = ihcLevelOfExpQueryForm.getStainDistribution();
             for(int i=0; i<distributions.length;i++){
                 String[] uiDropdownString = distributions[i].split("#");
                 String myClassName = uiDropdownString[0];
                 String myValueName = uiDropdownString[1];    
                 Enum myType = EnumHelper.createType(myClassName,myValueName);
                 if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.ihc.DistributionType.class) {
                	 myType = (DistributionType) myType;
                     distributionSet.add(myType.name());
                 }                
             }
             dto.setStainDistributionCollection(distributionSet);
         }
         */
         dto.setSpecimenCriteria(theSPCriteria);
         
    	 return  dto;
    	 
    }
  
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	IHCLevelOfExpressionQueryForm ihcQueryForm = (IHCLevelOfExpressionQueryForm) form;        
        HttpSession session = request.getSession();
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(session);        
        ihcQueryForm.setPatientGroupCollection(clinicalGroupRetriever.getClinicalGroupsCollection());
        IHCRetriever ihcRetriever = new IHCRetriever(session);
        ihcQueryForm.setBiomarkersCollection(ihcRetriever.getLevelBiomarkers()); 
        ihcQueryForm.setStainIntensityCollection(ihcRetriever.getIntensity());
        ihcQueryForm.setStainLocalizationCollection(ihcRetriever.getLocalization());
        
        return mapping.findForward("backToIHCLevelQuery");
    }
        
    
    
    
}
