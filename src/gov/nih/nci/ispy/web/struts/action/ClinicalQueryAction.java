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
import gov.nih.nci.ispy.service.clinical.PcrType;
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
            
        // set pathology complete Response groups
        if(clinicalForm.getPcrStatus()!=null && clinicalForm.getPcrStatus().length>0){
        	Set<String> pcrIds = new HashSet<String>();
            String[] pcrs = clinicalForm.getPcrStatus();
            addSamplesFromUserLists(helper, pcrIds, pcrs);
            intersectOrAddSamples(restrainingSamples, pcrIds);
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
        
        // set residual cancer burden index
        
        if(clinicalForm.getRcbSize()!=null && clinicalForm.getRcbSize().trim().length()>0){
            dto.setRcbSize(Double.parseDouble(clinicalForm.getRcbSize()));
            dto.setRcbOperator(Operator.valueOf(clinicalForm.getRcbOperator()));
            returnAll = false;
        }
        
        
        //set pathology complete response
        if(clinicalForm.getPcrStatus()!=null && clinicalForm.getPcrStatus().length>0){
            EnumSet<PcrType> pcrSet = EnumSet.noneOf(PcrType.class);
            String[] pcrs = clinicalForm.getPcrStatus();
            for(int i=0; i<pcrs.length;i++){
                String[] uiDropdownString = pcrs[i].split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];    
                Enum myType = EnumHelper.createType(myClassName,myValueName);
                if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.PcrType.class) {
                	pcrSet.add((PcrType) myType);
                }                
            }
            dto.setPcrValues(pcrSet);
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
        clinicalForm.setPcrCollection(clinicalGroupRetriever.getPcrCollection());        
        clinicalForm.setRaceCollection(clinicalGroupRetriever.getRaceCollection());
        clinicalForm.setAgeCollection(clinicalGroupRetriever.getAgeCollection());
        clinicalForm.setMorphologyCollection(clinicalGroupRetriever.getMorphologyCollection());
        
        return mapping.findForward("backToClinicalQuery");
    }
  
    
}
