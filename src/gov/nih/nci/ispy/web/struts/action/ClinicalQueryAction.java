package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.AgeCategoryType;
import gov.nih.nci.ispy.service.clinical.MorphologyType;
import gov.nih.nci.ispy.service.clinical.NeoAdjuvantChemoRegimenType;
import gov.nih.nci.ispy.service.clinical.PercentLDChangeType;
import gov.nih.nci.ispy.service.clinical.RaceType;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.ClinicalQueryForm;

import java.util.EnumSet;
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

public class ClinicalQueryAction extends DispatchAction {
	
	private UserCredentials credentials;  
	private static Logger logger = Logger.getLogger(ClassComparisonAction.class);
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
        ClinicalQueryForm clinicalForm = (ClinicalQueryForm) form;
        HttpSession session = request.getSession();
        
       
        ISPYclinicalDataQueryDTO clinicalDataQueryDTO = createClinicalDataQueryDTO(clinicalForm, session);
        
        
        ISPYFindingsFactory factory = new ISPYFindingsFactory();
        ISPYClinicalFinding finding = factory.createClinicalFinding(clinicalDataQueryDTO, session.getId(), clinicalDataQueryDTO.getQueryName());
           
        return mapping.findForward("viewResults");
    }
    
    private ISPYclinicalDataQueryDTO createClinicalDataQueryDTO(ClinicalQueryForm clinicalForm, HttpSession session) {
		ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
        UserListBeanHelper helper = new UserListBeanHelper(session);
        Set<String> restrainingSamples = new HashSet<String>();
        dto.setQueryName(clinicalForm.getAnalysisResultName());
		
        /**
         * Grab custom as well as default userlists OR enumsets from all fields 
         * where these lists are available. Within the dropdown selections,
         * the query should join(OR) the selections and among all the fields, these
         * selections should be intersected(ANDed) with the other fields. The selections
         * take the form of PatientDIDs(samples) and are all added to the 
         * "restraining samples" list.
         */
        
        //set custom patient group
        boolean returnAll = true;
        if(clinicalForm.getPatientGroup()!=null && !clinicalForm.getPatientGroup().equals("none")){
        	Set<String> patientGroupIds = new HashSet<String>();
        	String[] patientGroupNames = new String[1];
        	patientGroupNames[0] = clinicalForm.getPatientGroup();
        	addSamplesFromUserLists(helper, patientGroupIds, patientGroupNames);
        	intersectOrAddSamples(restrainingSamples, patientGroupIds);
        	returnAll = false;
        }
                
        //set disease stage groups        
        if(clinicalForm.getClinicalStages()!=null && clinicalForm.getClinicalStages().length>0){
        	Set<String> clinicalStageIds = new HashSet<String>();
            String[] stages = clinicalForm.getClinicalStages();
            addSamplesFromUserLists(helper, clinicalStageIds, stages );
            intersectOrAddSamples(restrainingSamples,clinicalStageIds);
            returnAll = false;
        }       
        
        //set histology type groups        
        if(clinicalForm.getHistologyType()!=null && clinicalForm.getHistologyType().length>0){
        	Set<String> histologyIds = new HashSet<String>();
            String[] histology = clinicalForm.getHistologyType();
            addSamplesFromUserLists(helper, histologyIds, histology);
            intersectOrAddSamples(restrainingSamples, histologyIds);
            returnAll = false;
        }
                      
        //set agent groups
        if(clinicalForm.getAgents()!=null && clinicalForm.getAgents().length>0){
            EnumSet<NeoAdjuvantChemoRegimenType> agentSet = EnumSet.noneOf(NeoAdjuvantChemoRegimenType.class);
            String[] agents = clinicalForm.getAgents();
            for(int i=0; i<agents.length;i++){
                String[] uiDropdownString = agents[i].split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];    
                Enum myType = EnumHelper.createType(myClassName,myValueName);
                if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.NeoAdjuvantChemoRegimenType.class) {
                    agentSet.add((NeoAdjuvantChemoRegimenType) myType);
                }                
            }
            dto.setAgentValues(agentSet);
            returnAll = false;
        }
        
        //set clinical response groups
        
        if(clinicalForm.getResponse()!=null && clinicalForm.getResponse().length>0){
	    	Set<String> clinicalResponseIds = new HashSet<String>();
	        String[] responses = clinicalForm.getResponse();
	        addSamplesFromUserLists(helper, clinicalResponseIds, responses);
	        intersectOrAddSamples(restrainingSamples,clinicalResponseIds);  
	        returnAll = false;
        }
        
        
        
        //set receptor groups
       
        if(clinicalForm.getReceptorStatus()!=null && clinicalForm.getReceptorStatus().length>0){
        	Set<String> receptorIds = new HashSet<String>();
            String[] receptors = clinicalForm.getReceptorStatus();
            addSamplesFromUserLists(helper, receptorIds, receptors);
            intersectOrAddSamples(restrainingSamples, receptorIds);
            returnAll = false;
        }
                       
        if(!restrainingSamples.isEmpty()){
            dto.setRestrainingSamples(restrainingSamples);
        }
        
        //set age params
        if(clinicalForm.getAge()!=null && clinicalForm.getAge().length>0){
            EnumSet<AgeCategoryType> ageSet = EnumSet.noneOf(AgeCategoryType.class);
            String[] ages = clinicalForm.getAge();
            for(int i=0; i<ages.length;i++){
                String[] uiDropdownString = ages[i].split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];    
                Enum myType = EnumHelper.createType(myClassName,myValueName);
                if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.AgeCategoryType.class) {
                    ageSet.add((AgeCategoryType) myType);
                }                
            }
            dto.setAgeCategoryValues(ageSet);
            returnAll = false;
        }
        
        //set race params
        if(clinicalForm.getRace()!=null && clinicalForm.getRace().length>0){
            EnumSet<RaceType> raceSet = EnumSet.noneOf(RaceType.class);
            String[] races = clinicalForm.getRace();
            for(int i=0; i<races.length;i++){
                String[] uiDropdownString = races[i].split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];    
                Enum myType = EnumHelper.createType(myClassName,myValueName);
                if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.RaceType.class) {
                    raceSet.add((RaceType) myType);
                }                
            }
            dto.setRaceValues(raceSet);
            returnAll = false;
        }
        
        //set diameter
        if(clinicalForm.getDiameter()!=null && clinicalForm.getDiameter().trim().length()>0){
            dto.setDiameter(Double.parseDouble(clinicalForm.getDiameter()));
            dto.setDiameterOperator(Operator.valueOf(clinicalForm.getDiameterOperator()));
            returnAll = false;
        }
        
        //set micro size
        if(clinicalForm.getPathTumorSize()!=null && clinicalForm.getPathTumorSize().trim().length()>0){
            dto.setPathTumorSize(Double.parseDouble(clinicalForm.getPathTumorSize()));
            dto.setPathTumorSizeOperator(Operator.valueOf(clinicalForm.getPathTumorSizeOperator()));
            returnAll = false;
        }
        
        //set morphology keywords
        if(clinicalForm.getMorphology()!=null && clinicalForm.getMorphology().length>0){
            EnumSet<MorphologyType> morphologySet = EnumSet.noneOf(MorphologyType.class);
            String[] morphology = clinicalForm.getMorphology();
            for(int i=0; i<morphology.length;i++){
                String[] uiDropdownString = morphology[i].split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];    
                Enum myType = EnumHelper.createType(myClassName,myValueName);
                if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.MorphologyType.class) {
                    morphologySet.add((MorphologyType) myType);
                }                
            }
            
            //separate the keywords by parsing string separated by lines
            //String[] keywords = clinicalForm.getMorphology().split(System.getProperty("line.separator"));
            dto.setMorphologyValues(morphologySet);
            returnAll = false;
        }
        
        //set ld size ... future impl
        //dto.setLdLength(clinicalForm.getLdLength());
        //dto.setLdLengthOperator(clinicalForm.getLdLengthOperator());
        
        //set ld percent change
        if(clinicalForm.getLdTimepointRange()!=null && !clinicalForm.getLdTimepointRange().equals("none")){
                String percentLDChangeType = clinicalForm.getLdTimepointRange();            
                String[] uiDropdownString = percentLDChangeType.split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];    
                Enum myType = EnumHelper.createType(myClassName,myValueName);
                if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.PercentLDChangeType.class) {
                    dto.setPercentLDChangeType((PercentLDChangeType) myType);
                }                
           
            
            dto.setLdPercentChange(clinicalForm.getLdPercentChange());
            dto.setLdPercentChangeOperator(Operator.valueOf(clinicalForm.getLdPercentChangeOperator()));
            returnAll = false;
        }
        dto.setReturnAll(returnAll);
		return dto;
	}
    
    /**
     * This method will get each user list in the array of list names and add all samples
     * in the list to the sample set
     * @param userListNames
     */
    private void addSamplesFromUserLists(UserListBeanHelper helper, Set<String> sampleSet, String[] userListNames) {
    	UserList myCurrentList;
    	for(int i=0; i<userListNames.length;i++){
            myCurrentList = helper.getUserList(userListNames[i]);
            if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
              sampleSet.addAll(myCurrentList.getList());
            }
        }
    }
    
    /**
     * This method will retain all samples in the sampleCollection that intersect with the samples 
     * in the samplesToIntersect set. If the sample collection is empty then the samples will be 
     * added to the sampleCollection
     * 
     * @param sampleCollection
     * @param samplesToIntersect
     */
    private void intersectOrAddSamples(Set<String> sampleCollection, Set<String> samplesToIntersect) {
    	
    	 if (samplesToIntersect.isEmpty())  {
    		 return;
    	 }
    	
    	 if(sampleCollection.isEmpty()){
    		 sampleCollection.addAll(samplesToIntersect);               
         }
         else{
        	 sampleCollection.retainAll(samplesToIntersect);
         }
    }

	public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        ClinicalQueryForm clinicalForm = (ClinicalQueryForm) form;
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(request.getSession());        
        clinicalForm.setClinicalStageCollection(clinicalGroupRetriever.getClinicalStageCollection());
        clinicalForm.setResponseCollection(clinicalGroupRetriever.getClinicalResponseCollection());
        clinicalForm.setReceptorCollection(clinicalGroupRetriever.getReceptorCollection());
        clinicalForm.setPatientGroupCollection(clinicalGroupRetriever.getCustomPatientCollection());
        clinicalForm.setAgentsCollection((clinicalGroupRetriever.getAgentsCollection()));
        clinicalForm.setLdTimepointRangeCollection(clinicalGroupRetriever.getLdPercentChangeCollection());
        clinicalForm.setRaceCollection(clinicalGroupRetriever.getRaceCollection());
        clinicalForm.setAgeCollection(clinicalGroupRetriever.getAgeCollection());
        clinicalForm.setMorphologyCollection(clinicalGroupRetriever.getMorphologyCollection());
        
        return mapping.findForward("backToClinicalQuery");
    }
  
    
}
