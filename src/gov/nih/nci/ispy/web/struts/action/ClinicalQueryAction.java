package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.AgeCategoryType;
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
        ISPYClinicalFinding finding = null;
        
        finding = factory.createClinicalFinding(clinicalDataQueryDTO, session.getId(), clinicalDataQueryDTO.getQueryName());
           
        return mapping.findForward("viewResults");
    }
    
    private ISPYclinicalDataQueryDTO createClinicalDataQueryDTO(ClinicalQueryForm clinicalForm, HttpSession session) {
		ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
        UserListBeanHelper helper = new UserListBeanHelper(session);
        UserList myCurrentList;
        Set<String> tempRestrainingSamples = new HashSet<String>();
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
        if(clinicalForm.getPatientGroup()!=null && !clinicalForm.getPatientGroup().equals("none")){
            myCurrentList = helper.getUserList(clinicalForm.getPatientGroup());
            if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                tempRestrainingSamples.addAll(myCurrentList.getList());
            }
        }
        
        //set disease stage groups
        if(clinicalForm.getClinicalStages()!=null && clinicalForm.getClinicalStages().length>0){
            String[] stages = clinicalForm.getClinicalStages();
            for(int i=0; i<stages.length;i++){
                myCurrentList = helper.getUserList(stages[i]);
                if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                    //attempt to intersect lists
                    if(!tempRestrainingSamples.isEmpty()){
                        tempRestrainingSamples.retainAll(myCurrentList.getList());
                    }
                    else{
                        tempRestrainingSamples.addAll(myCurrentList.getList());  
                    }
                }
            }
        }       
        
        //set histology type groups
        if(clinicalForm.getHistologyType()!=null && clinicalForm.getHistologyType().length>0){
            String[] histology = clinicalForm.getHistologyType();
            for(int i=0; i<histology.length;i++){
                myCurrentList = helper.getUserList(histology[i]);
                if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                    //attempt to intersect lists
                    if(!tempRestrainingSamples.isEmpty()){
                        tempRestrainingSamples.retainAll(myCurrentList.getList());
                    }
                    else{
                        tempRestrainingSamples.addAll(myCurrentList.getList());  
                    }
                }
            }
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
        }
        
        //set clinical response groups
        if(clinicalForm.getResponse()!=null && clinicalForm.getResponse().length>0){
            String[] responses = clinicalForm.getResponse();
            for(int i=0; i<responses.length;i++){
                myCurrentList = helper.getUserList(responses[i]);
                if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                    //attempt to intersect lists
                    if(!tempRestrainingSamples.isEmpty()){
                        tempRestrainingSamples.retainAll(myCurrentList.getList());
                    }
                    else{
                        tempRestrainingSamples.addAll(myCurrentList.getList());  
                    }
                }
            }
        }
        
        //set receptor groups
        if(clinicalForm.getReceptorStatus()!=null && clinicalForm.getReceptorStatus().length>0){
            String[] receptors = clinicalForm.getReceptorStatus();
            for(int i=0; i<receptors.length;i++){
                myCurrentList = helper.getUserList(receptors[i]);
                if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                    //attempt to intersect lists
                    if(!tempRestrainingSamples.isEmpty()){
                        tempRestrainingSamples.retainAll(myCurrentList.getList());
                    }
                    else{
                        tempRestrainingSamples.addAll(myCurrentList.getList());  
                    }
                }
            }
        }
        
        if(clinicalForm.getPatientGroup()!=null && !clinicalForm.getPatientGroup().equals("none")){
            //attempt to intersect lists
            myCurrentList = helper.getUserList(clinicalForm.getPatientGroup());
            if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                //attempt to intersect lists
                if(!tempRestrainingSamples.isEmpty()){
                    tempRestrainingSamples.retainAll(myCurrentList.getList());
                }
                else{
                    tempRestrainingSamples.addAll(myCurrentList.getList());  
                }
            }
        }
        
        if(!tempRestrainingSamples.isEmpty()){
            dto.setRestrainingSamples(tempRestrainingSamples);
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
        }
        
        //set diameter
        if(clinicalForm.getDiameter()!=null){
            dto.setDiameter(clinicalForm.getDiameter());
            dto.setDiameterOperator(Operator.valueOf(clinicalForm.getDiameterOperator()));
        }
        
        //set micro size
        if(clinicalForm.getPathTumorSize()!=null){
            dto.setPathTumorSize(clinicalForm.getPathTumorSize());
            dto.setPathTumorSizeOperator(Operator.valueOf(clinicalForm.getPathTumorSizeOperator()));
        }
        
        //set morphology keywords
        if(clinicalForm.getMorphology()!=null && !clinicalForm.getMorphology().equals("")){
            //separate the keywords by parsing string separated by lines
            String[] keywords = clinicalForm.getMorphology().split(System.getProperty("line.separator"));
            dto.setMorphology(keywords);
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
        }
      
		return dto;
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
        
        return mapping.findForward("backToClinicalQuery");
    }
  
    
}
