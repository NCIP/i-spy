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



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

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
