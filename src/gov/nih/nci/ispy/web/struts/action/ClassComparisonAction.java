package gov.nih.nci.ispy.web.struts.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.UserList;
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
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.dto.query.PatientUserListQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.DiseaseStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClassHelper;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.ClassComparisonForm;

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
        String sessionId = request.getSession().getId();  
        HttpSession session = request.getSession();
        ClassComparisonQueryDTO classComparisonQueryDTO = createClassComparisonQueryDTO(classComparisonForm,sessionId, session);
        
        
        ISPYFindingsFactory factory = new ISPYFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createClassComparisonFinding(classComparisonQueryDTO,sessionId,classComparisonQueryDTO.getQueryName());
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
        String sessionId = request.getSession().getId();        
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever();
        classComparisonForm.setExistingGroupsList(clinicalGroupRetriever.getClinicalGroupsCollection(request.getSession()));
        
        return mapping.findForward("backToClassComparison");
    }
        
    private ClassComparisonQueryDTO createClassComparisonQueryDTO(ClassComparisonForm classComparisonQueryForm, String sessionId, HttpSession session){

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
         *       etc), making as many ispyClinicalQueryDTOs as there are "selected groups". As we
         *       cycle through the groups, we decide which "type" the current, selected
         *       group is part of, and add values to their respective EnumSets accordingly.
         *       We then add to the QueryDTO all of the EnumSets and their selected types. 
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
         *       cycle through the groups, we decide which "type" the current, selected
         *       group is part of, and add values to their respective EnumSets accordingly. These
         *       EnumSets will be used twice, once for each QueryDTO we create.
         *       
         *      -We create 2 QueryDTOs. We set the baseline timepoint value to one and add
         *       the EnumSets we created above. Then we set the comparison timepoint to the other 
         *       and add the SAME EnumSets to it. In this way, the comparison is based on the timepoint
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
                /*
                 * instantiate blank EnumSets
                 */
                EnumSet<TimepointType> timepoints = EnumSet.noneOf(TimepointType.class);
                EnumSet<ClinicalResponseType> clinicalResponses = EnumSet.noneOf(ClinicalResponseType.class);
                EnumSet<DiseaseStageType> diseaseStages = EnumSet.noneOf(DiseaseStageType.class);
                EnumSet<ERstatusType> erStatus = EnumSet.noneOf(ERstatusType.class);
                EnumSet<HER2statusType> her2Status = EnumSet.noneOf(HER2statusType.class);
                EnumSet<PRstatusType> prStatus = EnumSet.noneOf(PRstatusType.class);
                
                
                 
                
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
                 * parse the selected groups, create the appropriate EnumType and add it
                 * to its respective EnumSet
                 */
                String[] uiDropdownString = classComparisonQueryForm.getSelectedGroups()[i].split("#");
                String myClassName = uiDropdownString[0];
                String myValueName = uiDropdownString[1];
               
                Class myClass = ClassHelper.createClass(myClassName);
                
                if(myClass.isInstance(new UserList())){
                    PatientUserListQueryDTO patientQueryDTO = new PatientUserListQueryDTO(session,myValueName);
                    clinicalQueryCollection.add(patientQueryDTO);
                    if(i==1){//the second group is always baseline
                        patientQueryDTO.setBaseline(true);
                    }
                    patientQueryDTO.setTimepointValues(timepoints);
                }
                else{
                    ISPYclinicalDataQueryDTO ispyClinicalDataQueryDTO = new ISPYclinicalDataQueryDTO();
                    if(i==1){//the second group is always baseline
                        ispyClinicalDataQueryDTO.setBaseline(true);
                    }
                    ispyClinicalDataQueryDTO.setTimepointValues(timepoints); 
                    
                    Enum myType = EnumHelper.createType(myClassName,myValueName);
                    if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.ClinicalResponseType.class) {
                        clinicalResponses.add((ClinicalResponseType) myType);
                    }
                    if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.DiseaseStageType.class) {
                        diseaseStages.add((DiseaseStageType) myType);
                    }
                    if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.ERstatusType.class) {
                        erStatus.add((ERstatusType) myType);
                    }
                    if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.HER2statusType.class) {
                        her2Status.add((HER2statusType) myType);
                    }
                    if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.PRstatusType.class) {
                        prStatus.add((PRstatusType) myType);
                    } 
                    ispyClinicalDataQueryDTO.setClinicalResponseValues(clinicalResponses);
                    ispyClinicalDataQueryDTO.setDiseaseStageValues(diseaseStages);
                    ispyClinicalDataQueryDTO.setErStatusValues(erStatus);
                    ispyClinicalDataQueryDTO.setHer2StatusValues(her2Status);
                    ispyClinicalDataQueryDTO.setPrStatusValues(prStatus);
                    clinicalQueryCollection.add(ispyClinicalDataQueryDTO);
                }
            }
            /*
             * set the QueryDTO in the collection and move on to the next one
             */
            classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);
        }
        
        else if(classComparisonQueryForm.getTimepointRange().equals("across")){
            TimepointType acrossTimepointBase;
            TimepointType timepointComparison;
            
            /*
             * instantiate blank EnumSets
             */
            EnumSet<ClinicalResponseType> clinicalResponses = EnumSet.noneOf(ClinicalResponseType.class);
            EnumSet<DiseaseStageType> diseaseStages = EnumSet.noneOf(DiseaseStageType.class);
            EnumSet<ERstatusType> erStatus = EnumSet.noneOf(ERstatusType.class);
            EnumSet<HER2statusType> her2Status = EnumSet.noneOf(HER2statusType.class);
            EnumSet<PRstatusType> prStatus = EnumSet.noneOf(PRstatusType.class);
            EnumSet<TimepointType> timepointsBase = EnumSet.noneOf(TimepointType.class); 
            EnumSet<TimepointType> timepointsComp = EnumSet.noneOf(TimepointType.class);
           
            /*
             * parse the selected groups, create the appropriate EnumType and add it
             * to its respective EnumSet. Do this once as the same EnumSets are used
             * in both QueryDTOs.
             */
            for (int i = 0;i<classComparisonQueryForm.getSelectedGroups().length; i++){
                
                    String acrossTimepointBaseString = EnumHelper.getEnumTypeName(classComparisonQueryForm.getTimepointBaseAcross(),TimepointType.values());
                    String timepointComparisonString = EnumHelper.getEnumTypeName(classComparisonQueryForm.getTimepointComparison(),TimepointType.values());
                    
                    String[] uiDropdownString = classComparisonQueryForm.getSelectedGroups()[i].split("#");
                    String myClassName = uiDropdownString[0];
                    String myValueName = uiDropdownString[1];
                    
                    Class myClass = ClassHelper.createClass(myClassName);
                    
                    /* check to see what type of class the selected group is.
                     * if the group is a user-defined list, create the appropriate ClinicalQueryDTO, 
                     * then create 2 groups with the baseline defined
                     * by the first timepoint. If the class is a predefined group, do the exact same, setting values in the 
                     * DTO as needed. The latter class obviuosly needs more variables set because a query needs to be
                     * initially made to extract the appropriate patientDIDs. However, with a userlist, they are already defined,
                     * so all you need is a timepoint and baseline.
                     */
                    if(myClass.isInstance(new UserList())){
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
                    }
                    else{                        
                        Enum myType = EnumHelper.createType(myClassName,myValueName);
                        if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.ClinicalResponseType.class) {
                            clinicalResponses.add((ClinicalResponseType) myType);
                        }
                        if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.DiseaseStageType.class) {
                            diseaseStages.add((DiseaseStageType) myType);
                        }
                        if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.ERstatusType.class) {
                            erStatus.add((ERstatusType) myType);
                        }
                        if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.HER2statusType.class) {
                            her2Status.add((HER2statusType) myType);
                        }
                        if (myType.getDeclaringClass() == gov.nih.nci.ispy.service.clinical.PRstatusType.class) {
                            prStatus.add((PRstatusType) myType);
                        } 
                        /*
                         * create the basline QueryDTO
                         */
                        ISPYclinicalDataQueryDTO ispyClinicalDataQueryDTOBase = new ISPYclinicalDataQueryDTO();
                                 if(acrossTimepointBaseString!=null){
                                     acrossTimepointBase = TimepointType.valueOf(acrossTimepointBaseString);
                                     timepointsBase.add(acrossTimepointBase);
                                 }                                
                        ispyClinicalDataQueryDTOBase.setBaseline(true);                       
                        ispyClinicalDataQueryDTOBase.setTimepointValues(timepointsBase); 
                        ispyClinicalDataQueryDTOBase.setClinicalResponseValues(clinicalResponses);
                        ispyClinicalDataQueryDTOBase.setDiseaseStageValues(diseaseStages);
                        ispyClinicalDataQueryDTOBase.setErStatusValues(erStatus);
                        ispyClinicalDataQueryDTOBase.setHer2StatusValues(her2Status);
                        ispyClinicalDataQueryDTOBase.setPrStatusValues(prStatus);
                        clinicalQueryCollection.add(ispyClinicalDataQueryDTOBase);
                         
                        /*
                         * create the comparison QueryDTO
                         */
                        ISPYclinicalDataQueryDTO ispyClinicalDataQueryDTOComparison = new ISPYclinicalDataQueryDTO();
                                if(timepointComparisonString!=null){
                                     timepointComparison = TimepointType.valueOf(timepointComparisonString);
                                     timepointsComp.add(timepointComparison);
                                 }                           
                        ispyClinicalDataQueryDTOComparison.setTimepointValues(timepointsComp); 
                        ispyClinicalDataQueryDTOComparison.setClinicalResponseValues(clinicalResponses);
                        ispyClinicalDataQueryDTOComparison.setDiseaseStageValues(diseaseStages);
                        ispyClinicalDataQueryDTOComparison.setErStatusValues(erStatus);
                        ispyClinicalDataQueryDTOComparison.setHer2StatusValues(her2Status);
                        ispyClinicalDataQueryDTOComparison.setPrStatusValues(prStatus);
                        clinicalQueryCollection.add(ispyClinicalDataQueryDTOComparison);
                    }
                }
               
               
                
               /*
                * add both QueryDTOs to the classComparisonQueryDTO
                */
               classComparisonQueryDTO.setComparisonGroups(clinicalQueryCollection);            
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
