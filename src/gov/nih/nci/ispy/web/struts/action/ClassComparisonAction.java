/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYClassComparisonQueryDTO;
import gov.nih.nci.ispy.dto.query.PatientUserListQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.ClassComparisonForm;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;





public class ClassComparisonAction extends DispatchAction {
	
	private UserCredentials credentials;  
	private static Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    /***
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action.  These default values should
     * be verified as useful in all cases.
     */
    private MultiGroupComparisonAdjustmentType ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE = MultiGroupComparisonAdjustmentType.FWER;
    private StatisticalMethodType ERROR_STATISTICAL_METHOD_TYPE = StatisticalMethodType.TTest;
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
        ClassComparisonForm classComparisonForm = (ClassComparisonForm) form;
        HttpSession session = request.getSession();
        ClassComparisonQueryDTO classComparisonQueryDTO = createClassComparisonQueryDTO(classComparisonForm, session);
        
        
        ISPYFindingsFactory factory = new ISPYFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createClassComparisonFinding(classComparisonQueryDTO,session.getId(),classComparisonQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        
        return mapping.findForward("viewResults");
    }
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        ClassComparisonForm classComparisonForm = (ClassComparisonForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(request.getSession());
        classComparisonForm.setExistingGroupsList(clinicalGroupRetriever.getClinicalGroupsCollection());
        
        
        return mapping.findForward("backToClassComparison");
    }
        
    private ClassComparisonQueryDTO createClassComparisonQueryDTO(ClassComparisonForm classComparisonQueryForm, HttpSession session){
        String sessionId = session.getId();  
        ISPYClassComparisonQueryDTO classComparisonQueryDTO = (ISPYClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
        classComparisonQueryDTO.setQueryName(classComparisonQueryForm.getAnalysisResultName());        
        List<ClinicalQueryDTO> clinicalQueryCollection = new ArrayList<ClinicalQueryDTO>();        
        
       
        /**Create the clinical query DTO collection from the selected groups in the form
         * and the timepoints..either fixed or across. Set these querys in the class
         * comparison dto for groups to compare-
         * 
         * NOTE: Many comments as this is a long process. The workflow is as follows--
         *  1)If the user decides to use a fixed timepoint:
         *      -Cycle through the "selected groups"(e.g. - Complete Response, Partial Response, 
         *       etc), making as many ispyClinicalQueryDTOs as there are "selected groups". Grab the
         *       samples from these groups and populate a PatientQueryDTO.
         *       Finally we add this QueryDTO to the QueryDTOCollection.
         *      
         *      -We set the timepoint of BOTH QueryDTOs the same, fixed value(e.g. T1).
         *      
         *      -Once the loop is completed, we add the QueryDTOCollection, with its 2
         *       QueryDTOs to the ClassComparisonQueryDTO to be sent to the strategy.
         *   
         *   2)If the user decides to compare across timepoints:
         *      -Cycle through the "selected groups"(e.g. - Complete Response, Partial Response, 
         *       etc), making as many ispyClinicalQueryDTOs as there are "selected groups". As we
         *       cycle through the groups, Grab the
         *       samples from these groups and populate a PatientQueryDTO. These
         *       samples will be used twice, once for each QueryDTO we create.
         *       
         *      -We create 2 QueryDTOs. We set the baseline timepoint value to one and add
         *       the PatientQueryDTOs we created above. Then we set the comparison timepoint to the other 
         *       and add the SAME PatientQueryDTOs to it. In this way, the comparison is based on the timepoint
         *       selection and NOT the "selected groups" selection. We add each to 
         *       the queryDTOCollection.
         *       
         *      -We add the QueryDTOCollection, with its 2
         *       QueryDTOs to the ClassComparisonQueryDTO to be sent to the strategy.
         * 
         * 
         * -KR
         */
        if(classComparisonQueryForm.getTimepointRange().equals("fixed") && classComparisonQueryForm.getSelectedGroups().length == 2){           
            for (int i = 0;i<classComparisonQueryForm.getSelectedGroups().length;i++){                 
                EnumSet<TimepointType> timepoints = EnumSet.noneOf(TimepointType.class);                
                /*
                 * set timepoints
                 */
                TimepointType fixedTimepointBase;//timepoint for both queryDTOs        
                String fixedTimepointString = EnumHelper.getEnumTypeName(classComparisonQueryForm.getTimepointBaseFixed(),TimepointType.values());
                    if(fixedTimepointString!=null){
                        fixedTimepointBase = TimepointType.valueOf(fixedTimepointString);
                        timepoints.add(fixedTimepointBase);
                    }
                    
                
                /*
                 * create a patientQueryDTO and put the userlist's samples into it. May change in future
                 * as this was an implementation when we were using Enums to populate this dropdown instead
                 * of pre-cooked userlists that already contained the samples we need.
                 */
                String myValueName = classComparisonQueryForm.getSelectedGroups()[i];                
                    PatientUserListQueryDTO patientQueryDTO = new PatientUserListQueryDTO(session,myValueName);
                    clinicalQueryCollection.add(patientQueryDTO);
                    if(i==1){//the second group is always baseline
                        patientQueryDTO.setBaseline(true);
                    }
                    patientQueryDTO.setTimepointValues(timepoints);
            }

            /*
             * set the QueryDTO in the collection and move on to the next one
             */
            classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);
        }
        
        else if(classComparisonQueryForm.getTimepointRange().equals("across")){
            TimepointType acrossTimepointBase;
            TimepointType timepointComparison;
            
            EnumSet<TimepointType> timepointsBase = EnumSet.noneOf(TimepointType.class);
            EnumSet<TimepointType> timepointsComp = EnumSet.noneOf(TimepointType.class);
            
            for (int i = 0;i<classComparisonQueryForm.getSelectedGroups().length; i++){
                
                    String acrossTimepointBaseString = EnumHelper.getEnumTypeName(classComparisonQueryForm.getTimepointBaseAcross(),TimepointType.values());
                    String timepointComparisonString = EnumHelper.getEnumTypeName(classComparisonQueryForm.getTimepointComparison(),TimepointType.values());
                     
                    /* create a patientQueryDTO and put the userlist's samples into it. May change in future
                     * as this was an implementation when we were using Enums to populate this dropdown instead
                     * of pre-cooked userlists that already contained the samples we need. create the appropriate ClinicalQueryDTO, 
                     * then create 2 groups with the baseline defined by the first timepoint. 
                     */
                    String myValueName = classComparisonQueryForm.getSelectedGroups()[i];
                    
                    
                        PatientUserListQueryDTO patientQueryDTOBase = new PatientUserListQueryDTO(session,myValueName);
                        if(acrossTimepointBaseString!=null){
                            acrossTimepointBase = TimepointType.valueOf(acrossTimepointBaseString);
                            timepointsBase.add(acrossTimepointBase);
                        } 
                        patientQueryDTOBase.setBaseline(true);
                        patientQueryDTOBase.setTimepointValues(timepointsBase);
                        clinicalQueryCollection.add(patientQueryDTOBase);
                        
                        PatientUserListQueryDTO patientQueryDTOComparision = new PatientUserListQueryDTO(session,myValueName);
                        if(timepointComparisonString!=null){
                            timepointComparison = TimepointType.valueOf(timepointComparisonString);
                            timepointsComp.add(timepointComparison);
                        }     
                        patientQueryDTOComparision.setBaseline(false);
                        patientQueryDTOComparision.setTimepointValues(timepointsComp);
                        clinicalQueryCollection.add(patientQueryDTOComparision);
                    
               /*
                * add both QueryDTOs to the classComparisonQueryDTO
                */
               classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);            
        }
        
        }
        
        //Create the foldChange DEs
       
            
            if (classComparisonQueryForm.getFoldChange().equals("list")){
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeAuto()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);
            }
            if (classComparisonQueryForm.getFoldChange().equals("specify")){        
                    UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonQueryForm.getFoldChangeManual()));
                    classComparisonQueryDTO.setExprFoldChangeDE(exprFoldChangeDE);                   
            }
            
        
        //Create arrayPlatfrom DEs
            if(classComparisonQueryForm.getArrayPlatform() != "" || classComparisonQueryForm.getArrayPlatform().length() != 0){       
                ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(classComparisonQueryForm.getArrayPlatform());
                classComparisonQueryDTO.setArrayPlatformDE(arrayPlatformDE);
            }
            
           //Create class comparison DEs
            /*
             * This following code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumChecker for 
             * enlightenment.
             */
           MultiGroupComparisonAdjustmentType mgAdjustmentType; 
           String multiGroupComparisonAdjustmentTypeString= EnumHelper.getEnumTypeName(classComparisonQueryForm.getComparisonAdjustment(),MultiGroupComparisonAdjustmentType.values());
           if(multiGroupComparisonAdjustmentTypeString!=null) {
        	   mgAdjustmentType = MultiGroupComparisonAdjustmentType.valueOf(multiGroupComparisonAdjustmentTypeString);
           }else {
        	   	logger.error("Invalid MultiGroupComparisonAdjustmentType value given in request");
           		logger.error("Selected MultiGroupComparisonAdjustmentType value = "+classComparisonQueryForm.getComparisonAdjustment());
           		logger.error("Using the default MultiGroupComparisonAdjustmentType value = "+ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE);
           		mgAdjustmentType = ERROR_MULTI_GROUP_COMPARE_ADJUSTMENT_TYPE;
           }
           MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE = new MultiGroupComparisonAdjustmentTypeDE(mgAdjustmentType);  ;
           if(!classComparisonQueryForm.getComparisonAdjustment().equalsIgnoreCase("NONE")){
                StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.adjustedpValue);
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
            }
            else{
            	StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(classComparisonQueryForm.getStatisticalSignificance(),Operator.LE,StatisticalSignificanceType.pValue);  
                classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(multiGroupComparisonAdjustmentTypeDE);
                classComparisonQueryDTO.setStatisticalSignificanceDE(statisticalSignificanceDE);
                
            }
           
            if(classComparisonQueryForm.getStatisticalMethod() != "" || classComparisonQueryForm.getStatisticalMethod().length() != 0){
            	/*
                 * This following code is here to deal with an observed problem with the changing 
                 * of case in request parameters.  See the class EnumChecker for 
                 * enlightenment.
                 */
            	StatisticalMethodType statisticalMethodType; 
            	String statisticalMethodTypeString= EnumHelper.getEnumTypeName(classComparisonQueryForm.getStatisticalMethod(),StatisticalMethodType.values());
                 if(statisticalMethodTypeString!=null) {
                	 statisticalMethodType = StatisticalMethodType.valueOf(statisticalMethodTypeString);
                 }else {
              	   	logger.error("Invalid StatisticalMethodType value given in request");
             		logger.error("Selected StatisticalMethodType value = "+classComparisonQueryForm.getStatisticalMethod());
             		logger.error("Using the default StatisticalMethodType type of :"+ERROR_STATISTICAL_METHOD_TYPE);
             		statisticalMethodType = ERROR_STATISTICAL_METHOD_TYPE;
                 }
                StatisticTypeDE statisticTypeDE = new StatisticTypeDE(statisticalMethodType);
                classComparisonQueryDTO.setStatisticTypeDE(statisticTypeDE);
            }
            
            return classComparisonQueryDTO;
    }
    
    
}
