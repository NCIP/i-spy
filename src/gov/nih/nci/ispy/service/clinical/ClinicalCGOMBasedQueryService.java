/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.clinical;

/**
 * 
 * 
 */

import gov.nih.nci.breastCancer.dto.BreastCancerClinicalFindingCriteria;
import gov.nih.nci.breastCancer.dto.StudyParticipantCriteria;
import gov.nih.nci.breastCancer.service.ClinicalFindingHandler;
import gov.nih.nci.caintegrator.domain.common.bean.NumericMeasurement;
import gov.nih.nci.caintegrator.domain.finding.clinical.bean.ClinicalFinding;
import gov.nih.nci.caintegrator.domain.finding.clinical.breastCancer.bean.BreastCancerClinicalFinding;
import gov.nih.nci.caintegrator.domain.study.bean.Activity;
import gov.nih.nci.caintegrator.domain.study.bean.Procedure;
import gov.nih.nci.caintegrator.domain.study.bean.Surgery;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.util.HibernateUtil;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Class used to populate the PatientData from the OM/Database.
 */
public class ClinicalCGOMBasedQueryService implements ClinicalDataService
{
    private static final String POSITIVE_RESULT = "1=Yes";
    private static final String NEGATIVE_RESULT = "0=No";
    private static ClinicalCGOMBasedQueryService ourInstance = null;
    private static Logger logger = Logger.getLogger(ClinicalCGOMBasedQueryService.class);

    /**
     * Populate a set of patient ID's matching the criteria in the DTO
     * 
     * @param inDTO, is the DTO used to query for the patient ID's
     * 
     * @return the set of patient ID's corresponding to the DTO
     */
    public Set<String> getPatientDIDs(ISPYclinicalDataQueryDTO dto)
    {
        logger.debug("Entering getPatientDIDs");

        Set<String> theReturnSet = new HashSet<String>();
        try
        {
            // Get the findings
            Collection<? extends ClinicalFinding> theFindings = getFindings(dto);

            logger.info("Matched " + theFindings.size() + " Findings");

            // Loop through the set and get the ID's
            for (ClinicalFinding theFinding : theFindings)
            {
                theReturnSet.add(theFinding.getStudyParticipant().getStudySubjectIdentifier());
            }

            logger.info("DIDs " + theReturnSet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Error getting patient IDs: ", e);
        }

        // We've traversed the OM; close the session
        finally
        {
            HibernateUtil.closeSession();
        }

        logger.debug("Exiting getPatientDIDs");

        return theReturnSet;
    }

    /**
     * Populate a set of PatientData matching the values in the DTO
     * 
     * @param inDTO, is the DTO used to query for the findings used to populate the PatientData
     * 
     * @return the set of PatientData corresponding to tge DTO
     */
    public Set<PatientData> getClinicalData(ISPYclinicalDataQueryDTO inDTO)
    {
        logger.debug("Entering getClinicalData");

        Set<PatientData> theReturnSet = new HashSet<PatientData>();
        try
        {
            Collection<? extends ClinicalFinding> theFindings = getFindings(inDTO);

            logger.info("Matched " + theFindings.size() + " Findings");

            for (ClinicalFinding theFinding : theFindings)
            {
                BreastCancerClinicalFinding theBCFinding = (BreastCancerClinicalFinding) theFinding;
                theReturnSet.add(populatePatientData(theBCFinding));
            }

            // Populate the T2 to T4 data in one big chunk
            if (theReturnSet.size() > 0)
            {
                populateT2ToT4Data(theReturnSet);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Error getting clinical data: ", e);
        }
        finally
        {
            // We've traversed the OM; close the session
            HibernateUtil.closeSession();
        }

        logger.debug("Exiting getClinicalData");

        return theReturnSet;
    }

    // Create/populate a PatientData based on the clinical finding
    private PatientData populatePatientData(BreastCancerClinicalFinding inFinding)
    {
        logger.debug("Entering populatePatientData");

        PatientData thePatientData = new PatientData(inFinding.getStudyParticipant().getStudySubjectIdentifier());

        //////////////////////////////////////////////////////////
        // Map the age 
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getAgeAtDiagnosis() != null)
        {
            NumericMeasurement theAgeAtDiagnosis = inFinding.getStudyParticipant().getAgeAtDiagnosis();

            EnumSet<AgeCategoryType> theAgeEnums = EnumSet.allOf(AgeCategoryType.class);

            String theAgeRange = theAgeAtDiagnosis.getMinValue().intValue() + "-" + theAgeAtDiagnosis.getMaxValue().intValue();

            for (AgeCategoryType theAgeCategory : theAgeEnums)
            {
                if (theAgeCategory.toString().contains(theAgeRange))
                {
                    thePatientData.setAgeCategory(theAgeCategory);
                    break;
                }
            }
        }

        //////////////////////////////////////////////////////////
        // Map the institute
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getInstitutionName() != null)
        {
            thePatientData.setInst_ID(inFinding.getStudyParticipant().getInstitutionName());
        }

        //////////////////////////////////////////////////////////
        // Map the race 
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getRaceCode() != null)
        {
            EnumSet<RaceType> theRaceEnums = EnumSet.allOf(RaceType.class);

            for (RaceType theRaceType : theRaceEnums)
            {
                if (theRaceType.toString().contains(inFinding.getStudyParticipant().getRaceCode()))
                {
                    thePatientData.setRace_ID(theRaceType);
                    break;
                }
            }
        }

        //////////////////////////////////////////////////////////
        // Map the survival status
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getSurvivalStatus() != null)
        {
            String theSStat = "";
            if (inFinding.getStudyParticipant().getSurvivalStatus().equals("Y")) {
                theSStat = "7=Alive";
            }
            else if (inFinding.getStudyParticipant().getSurvivalStatus().equals("N")) {
                theSStat = "8=Dead";
            }
            else if (inFinding.getStudyParticipant().getSurvivalStatus().equals("U")) {
                theSStat = "9=Lost";
            }
            thePatientData.setSSTAT(theSStat);
        }

        //////////////////////////////////////////////////////////
        // Map the days on study
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getDaysOnStudy() != null)
        {
            thePatientData.setSURVDTD(inFinding.getStudyParticipant().getDaysOnStudy().toString());
        }

        //////////////////////////////////////////////////////////
        // Map the HER2 assessment method
        //////////////////////////////////////////////////////////
        if (inFinding.getHer2AssessmentMethod() != null)
        {
            thePatientData.setHER2CommunityMethod(inFinding.getHer2AssessmentMethod().getValue().toString());
        }

        //////////////////////////////////////////////////////////
        // Map the HER2 assessment
        //////////////////////////////////////////////////////////
        if (inFinding.getHer2Assessment() != null)
        {
            thePatientData.setHER2CommunityPOS(inFinding.getHer2Assessment().getValue().toString());
        }

        //////////////////////////////////////////////////////////
        // Map pathlogical tumor size
        //////////////////////////////////////////////////////////
        if (inFinding.getPathologicalTumorSize() != null)
        {
            thePatientData.setPTumor1SZCM_MICRO(inFinding.getPathologicalTumorSize().getAbsoluteValue());
        }

        //////////////////////////////////////////////////////////
        // Map clinical stage
        //////////////////////////////////////////////////////////
        if (inFinding.getClinicalStage() != null)
        {
            thePatientData.setClinicalStage(inFinding.getClinicalStage().getValue());
        }

        //////////////////////////////////////////////////////////
        // Handle the Activity mapping
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getActivityCollection() != null)
        {
            handleActivities(inFinding, thePatientData);
        }

        //////////////////////////////////////////////////////////
        // Map the meno status
        //////////////////////////////////////////////////////////
        if (inFinding.getMenoStatus() != null)
        {
            thePatientData.setMenoStatus(inFinding.getMenoStatus().getValue());
        }

        //////////////////////////////////////////////////////////
        //  Handle the sentinal node information
        //////////////////////////////////////////////////////////
        if (inFinding.getSentinalNodeSampleCollection() != null)
        {
            if (inFinding.getSentinalNodeSampleCollection().getValue().equals("Yes"))
            {
                thePatientData.setSentinelNodeSample(POSITIVE_RESULT);

                if (inFinding.getSentinalNodeResult() != null)
                {
                    if (inFinding.getSentinalNodeResult().getValue().equals("Positive"))
                    {
                        thePatientData.setSentinelNodeResult("1=Positive");
                    }
                    else if (inFinding.getSentinalNodeResult().getValue().equals("Negative"))
                    {
                        thePatientData.setSentinelNodeResult("0=Negative");
                    }
                }
            }
            else
            {
                thePatientData.setSentinelNodeSample(NEGATIVE_RESULT);
            }
        }

        //////////////////////////////////////////////////////////
        //  Handle the PR status
        //////////////////////////////////////////////////////////
        if (inFinding.getProgesteroneReceptorStatus() != null)
        {
            thePatientData.setPGR_TS(inFinding.getProgesteroneReceptorStatus().getValue());
        }

        //////////////////////////////////////////////////////////
        //  Handle the ER status
        //////////////////////////////////////////////////////////
        if (inFinding.getEstrogenReceptorStatus() != null)
        {
            thePatientData.setER_TS(inFinding.getEstrogenReceptorStatus().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the clinical tumor size
        /////////////////////////////////////////////////////////
        if (inFinding.getTumorSizeClinical() != null)
        {
            thePatientData.setTSizeClinical(inFinding.getTumorSizeClinical().getAbsoluteValue().toString());
        }

        /////////////////////////////////////////////////////////
        // Handle the clinical node size
        /////////////////////////////////////////////////////////
        if (inFinding.getNodeSizeClinical() != null)
        {
            thePatientData.setNSizeClinical(inFinding.getNodeSizeClinical().getAbsoluteValue().toString());
        }

        /////////////////////////////////////////////////////////
        // Handle the Stage ME
        /////////////////////////////////////////////////////////
        if (inFinding.getStageMe() != null)
        {
            thePatientData.setStageME(inFinding.getStageMe().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the Stage NE
        /////////////////////////////////////////////////////////
        if (inFinding.getStageNe() != null)
        {
            thePatientData.setStageNE(inFinding.getStageNe().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the Stage TE
        /////////////////////////////////////////////////////////
        if (inFinding.getStageTe() != null)
        {
            thePatientData.setStageTE(inFinding.getStageTe().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the Nodal morphology
        /////////////////////////////////////////////////////////
        if (inFinding.getNodalMorphology() != null)
        {
            thePatientData.setMorphology(inFinding.getNodalMorphology().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the nodes examined
        /////////////////////////////////////////////////////////
        if (inFinding.getNumNodesExamined() != null)
        {
            Integer theNumNodesExamined = inFinding.getNumNodesExamined().getAbsoluteValue().intValue();
            thePatientData.setNodesExaminedPS(theNumNodesExamined.toString());
        }

        /////////////////////////////////////////////////////////
        // Handle the nodes positive
        /////////////////////////////////////////////////////////
        if (inFinding.getNumPosNodes() != null)
        {
            Integer theNumPosNodes = inFinding.getNumPosNodes().getAbsoluteValue().intValue();
            thePatientData.setNumPosNodesPS(theNumPosNodes.toString());
        }

        /////////////////////////////////////////////////////////
        // Handle the histologic grade OS
        /////////////////////////////////////////////////////////
        if (inFinding.getHistologicGradeOS() != null)
        {
            thePatientData.setHistologicGradeOS(inFinding.getHistologicGradeOS().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the histologic grade PS
        /////////////////////////////////////////////////////////
        if (inFinding.getHistologicGradePS() != null)
        {
            thePatientData.setHistologicGradePS(inFinding.getHistologicGradePS().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the chemo regimen
        /////////////////////////////////////////////////////////
        if (inFinding.getChemo() != null)
        {
            thePatientData.setChemo(inFinding.getChemo().getValue());
        }

        /////////////////////////////////////////////////////////
        // Herceptin administered
        /////////////////////////////////////////////////////////
        if (inFinding.getHerceptinReceived() != null)
        {
            thePatientData.setHerceptin(inFinding.getHerceptinReceived().getValue());
        }

        /////////////////////////////////////////////////////////
        // Tamoxifen administered
        /////////////////////////////////////////////////////////
        if (inFinding.getTamoxifenReceived() != null)
        {
            thePatientData.setTAM(inFinding.getTamoxifenReceived().getValue());
        }

        /////////////////////////////////////////////////////////
        // DCIS Only
        /////////////////////////////////////////////////////////
        if (inFinding.getDcisOnly() != null)
        {
            thePatientData.setDCISOnlyPS(inFinding.getDcisOnly().getValue());
        }

        /////////////////////////////////////////////////////////
        // Pathology Stage
        /////////////////////////////////////////////////////////
        if (inFinding.getPathologyStage() != null)
        {
            thePatientData.setPathologyStagePS(inFinding.getPathologyStage().getValue());
        }

        logger.debug("Exiting populatePatientData");

        return thePatientData;
    }

    // Map the different activity types to the patient object
    private void handleActivities(BreastCancerClinicalFinding inFinding,
                                  PatientData inPatientData)
    {
        Set<Activity> theActivities = inFinding.getStudyParticipant().getActivityCollection();

        for (Activity theActivity : theActivities)
        {
            /////////////////////////////////////////////////////////
            // Handle the surgery types
            /////////////////////////////////////////////////////////
            if (theActivity instanceof Surgery)
            {
                Surgery theSurgery = (Surgery) theActivity;

                /////////////////////////////////////////////////////////
                // Specifically handle the surgery
                /////////////////////////////////////////////////////////
                if (theSurgery.getProcedureType() != null && theSurgery.getProcedureType().equals("SURGERY") && theSurgery.getProcedureName() == null)
                {
                    inPatientData.setSurgery(theSurgery.getStatus());
                }
                else if (theSurgery.getProcedureName() != null)
                {
                    ///////////////////////////////////////////////////
                    // Get the status, set it to Yes if it has been 
                    // administered
                    ///////////////////////////////////////////////////
                    String theStatus = NEGATIVE_RESULT;

                    if (theSurgery.getStatus().contains("yes"))
                    {
                        theStatus = POSITIVE_RESULT;
                    }

                    ///////////////////////////////////////////////////
                    // Handle the different surgery types
                    ///////////////////////////////////////////////////
                    if (theSurgery.getProcedureName().equals("SURGERYLUMPECTOMY"))
                    {
                        inPatientData.setSurgeryLumpectomy(theStatus);
                    }
                    else if (theSurgery.getProcedureName().equals("SURGERYMASTECTOMY"))
                    {
                        inPatientData.setSurgeryMastectomy(theStatus);
                    }
                    else if (theSurgery.getProcedureName().equals("INITLUMP_FUPMAST"))
                    {
                        inPatientData.setINITLUMP_FUPMAST(theStatus);
                    }
                }
            }

            /////////////////////////////////////////////////////////
            // Handle the procedure types
            /////////////////////////////////////////////////////////
            else if (theActivity instanceof Procedure)
            {
                Procedure theProcedure = (Procedure) theActivity;

                /////////////////////////////////////////////////////////
                // Specifically handle the radiation
                /////////////////////////////////////////////////////////
                if (theProcedure.getName().equals("RADIATION THERAPY"))
                {
                    ///////////////////////////////////////////////////
                    // Get the status, set it to Yes if it has been 
                    // administered
                    ///////////////////////////////////////////////////
                    String theStatus = NEGATIVE_RESULT;

                    if (theProcedure.getStatus().contains("Yes"))
                    {
                        theStatus = POSITIVE_RESULT;
                    }

                    // No target site; flag for entire therapy, and handle status slightly differently
                    if (theProcedure.getTargetSiteCode() == null)
                    {
                        if (theStatus.equals(POSITIVE_RESULT))
                        {
                            inPatientData.setRTTherapy("2=Yes");
                        }
                        else
                        {
                            inPatientData.setRTTherapy("1=No");
                        }
                    }
                    else
                    {
                        ///////////////////////////////////////////////////
                        // Handle the different locations
                        ///////////////////////////////////////////////////
                        if (theProcedure.getTargetSiteCode().equals("RTBREAST"))
                        {
                            inPatientData.setRTBreast(theStatus);
                        }
                        else if (theProcedure.getTargetSiteCode().equals("RTBOOST"))
                        {
                            inPatientData.setRTBOOST(theStatus);
                        }
                        else if (theProcedure.getTargetSiteCode().equals("RTAXILLA"))
                        {
                            inPatientData.setRTAXILLA(theStatus);
                        }
                        else if (theProcedure.getTargetSiteCode().equals("RTSNODE"))
                        {
                            inPatientData.setRTSNODE(theStatus);
                        }
                        else if (theProcedure.getTargetSiteCode().equals("RTIMAMNODE"))
                        {
                            inPatientData.setRTIMAMNODE(theStatus);
                        }
                        else if (theProcedure.getTargetSiteCode().equals("RTCHESTW"))
                        {
                            inPatientData.setRTChestW(theStatus);
                        }
                        else if (theProcedure.getTargetSiteCode().equals("RTOTHER"))
                        {
                            inPatientData.setRTOTHER(theStatus);
                        }
                    }
                }
            }
        }
    }

    // Populate the specific T2 to T4 data 
    private void populateT2ToT4Data(Set<PatientData> inPatientDataSet)
    {

        // Loop through the patient data set, and create a hashtable based on the DID as
        // well as a list of all the DIDs
        Map<String, PatientData> theHash = new Hashtable<String, PatientData>();
        Set<String> thePatientDIDs = new HashSet<String>();

        for (PatientData thePatientData : inPatientDataSet)
        {
            thePatientDIDs.add(thePatientData.getISPY_ID());
            theHash.put(thePatientData.getISPY_ID(), thePatientData);
        }

        ////////////////////////////////////////////////////////
        // Query for the T2-T4 data.
        ////////////////////////////////////////////////////////
        ISPYclinicalDataQueryDTO theNonBaselineDTO = new ISPYclinicalDataQueryDTO();

        EnumSet<TimepointType> theTimeCourseSet = EnumSet.allOf(TimepointType.class);
        theTimeCourseSet.remove(TimepointType.T1);

        theNonBaselineDTO.setRestrainingSamples(thePatientDIDs);
        theNonBaselineDTO.setTimepointValues(theTimeCourseSet);
        Collection<? extends ClinicalFinding> theFindings = getFindings(theNonBaselineDTO);

        // Loop through the clinical findings and set the appropriate T2-T4 values
        for (ClinicalFinding theFinding : theFindings)
        {
            if (theFinding instanceof BreastCancerClinicalFinding)
            {
                BreastCancerClinicalFinding theBCFinding = (BreastCancerClinicalFinding) theFinding;

                PatientData thePatientData = theHash.get(theBCFinding.getStudyParticipant().getId());

                if (theBCFinding.getTimeCourse() != null)
                {
                    // Handle T2 values
                    if (theBCFinding.getTimeCourse().getName().equals(TimepointType.T2.toString()))
                    {
                        if (theBCFinding.getClinicalResponse() != null)
                        {
                            thePatientData.setClinRespT1_T2(theBCFinding.getClinicalResponse().getValue());
                        }
                        if (theBCFinding.getPercentLDChangeFromBaseline() != null)
                        {
                            thePatientData.setMriPctChangeT1_T2(theBCFinding.getPercentLDChangeFromBaseline().getAbsoluteValue());
                        }
                    }

                    // Handle T3 values
                    else if (theBCFinding.getTimeCourse().getName().equals(TimepointType.T3.toString()))
                    {
                        if (theBCFinding.getClinicalResponse() != null)
                        {
                            thePatientData.setClinRespT1_T3(theBCFinding.getClinicalResponse().getValue());
                        }
                        if (theBCFinding.getPercentLDChangeFromBaseline() != null)
                        {
                            thePatientData.setMriPctChangeT1_T3(theBCFinding.getPercentLDChangeFromBaseline().getAbsoluteValue());
                        }
                    }
                    // Handle T4 values
                    else if (theBCFinding.getTimeCourse().getName().equals(TimepointType.T4.toString()))
                    {
                        if (theBCFinding.getClinicalResponse() != null)
                        {
                            thePatientData.setClinRespT1_T4(theBCFinding.getClinicalResponse().getValue());
                        }
                        if (theBCFinding.getPercentLDChangeFromBaseline() != null)
                        {
                            thePatientData.setMriPctChangeT1_T4(theBCFinding.getPercentLDChangeFromBaseline().getAbsoluteValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * Singleton for the database based query service
     * 
     * @return the static instance of the query service
     */
    public static ClinicalCGOMBasedQueryService getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new ClinicalCGOMBasedQueryService();
        }
        return ourInstance;
    }

    // Get the findings based on the DTO
    private Collection<? extends ClinicalFinding> getFindings(ISPYclinicalDataQueryDTO inDTO)
    {
        logger.debug("Exiting getFindings");

        BreastCancerClinicalFindingCriteria theBCCriteria = new BreastCancerClinicalFindingCriteria();
        StudyParticipantCriteria theSPCriteria = new StudyParticipantCriteria();

        /////////////////////////////////////////////////////
        // Handle the morphology TODO: Fix once it's determined what to do
        /////////////////////////////////////////////////////
        if (inDTO.getMorphologyValues() != null)
        {
            //            EnumSet<MorphologyType> theMorphologyValues = inDTO.getMorphologyValues();
            //            Set<String> theNodalMorphList = new HashSet<String>();
            //
            //            for (MorphologyType theMorphologyType : theMorphologyValues)
            //            {
            //                theNodalMorphList.add(theMorphologyType.name());
            //            }
            //
            //            theBCCriteria.setNodalMorphologyCollection(theNodalMorphList);
        }

        //////////////////////////////////////////////////////////
        // Handle the age 
        //////////////////////////////////////////////////////////
        if (inDTO.getAgeCategoryValues() != null)
        {
            EnumSet<AgeCategoryType> theAgeCategoryValues = inDTO.getAgeCategoryValues();

            Set<NumericMeasurement> theAges = new HashSet<NumericMeasurement>();

            for (AgeCategoryType theAgeCategory : theAgeCategoryValues)
            {
                NumericMeasurement theAgeMeasurement = new NumericMeasurement();

                if (theAgeCategory.equals(AgeCategoryType.AGE_18_30))
                {
                    theAgeMeasurement.setMinValue(18.0);
                    theAgeMeasurement.setMaxValue(30.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_30_40))
                {
                    theAgeMeasurement.setMinValue(30.0);
                    theAgeMeasurement.setMaxValue(40.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_40_50))
                {
                    theAgeMeasurement.setMinValue(40.0);
                    theAgeMeasurement.setMaxValue(50.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_50_60))
                {
                    theAgeMeasurement.setMinValue(50.0);
                    theAgeMeasurement.setMaxValue(60.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_60_70))
                {
                    theAgeMeasurement.setMinValue(60.0);
                    theAgeMeasurement.setMaxValue(70.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_70_80))
                {
                    theAgeMeasurement.setMinValue(70.0);
                    theAgeMeasurement.setMaxValue(80.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_80_89))
                {
                    theAgeMeasurement.setMinValue(80.0);
                    theAgeMeasurement.setMaxValue(89.0);
                }
                else if (theAgeCategory.equals(AgeCategoryType.AGE_GE_89_OR_NA))
                {
                    theAgeMeasurement.setMinValue(89.0);
                    theAgeMeasurement.setMaxValue(9999.0);
                }

                theAges.add(theAgeMeasurement);
            }

            theSPCriteria.setAgeCollection(theAges);
            theBCCriteria.setStudyParticipantCriteria(theSPCriteria);
        }

        /////////////////////////////////////////////////////
        // Handle the agent names list
        /////////////////////////////////////////////////////
        if (inDTO.getAgentValues() != null)
        {
            EnumSet<NeoAdjuvantChemoRegimenType> theAgents = inDTO.getAgentValues();

            Set<String> theAgentNames = new HashSet<String>();

            for (NeoAdjuvantChemoRegimenType theAgent : theAgents)
            {
                theAgentNames.add(theAgent.toString());
            }

            theBCCriteria.setChemoCollection(theAgentNames);
        }

        /////////////////////////////////////////////////////
        // Handle the clinical responses list
        /////////////////////////////////////////////////////
        if (inDTO.getClinicalResponseValues() != null)
        {
            Set<ClinicalResponseType> theClinicalResponses = inDTO.getClinicalResponseValues();

            Set<String> theClinicalResponseNames = new HashSet<String>();

            for (ClinicalResponseType theClinicalResponse : theClinicalResponses)
            {
                if (theClinicalResponse.equals(ClinicalResponseType.CR))
                {
                    theClinicalResponseNames.add("1 = CR");
                }
                else if (theClinicalResponse.equals(ClinicalResponseType.PR))
                {
                    theClinicalResponseNames.add("2 = PR");
                }
                else if (theClinicalResponse.equals(ClinicalResponseType.SD))
                {
                    theClinicalResponseNames.add("3 = Stable Disease");
                }
            }

            if (theClinicalResponseNames.size() == 0)
            {
                theClinicalResponseNames.add("NO_MATCH");
            }
            theBCCriteria.setClinicalResponseCollection(theClinicalResponseNames);
        }

        /////////////////////////////////////////////////////
        // Handle the clinical stages list
        /////////////////////////////////////////////////////
        if (inDTO.getClinicalStageValues() != null)
        {
            Set<ClinicalStageType> theClinicalStages = inDTO.getClinicalStageValues();

            Set<String> theClinicalStageNames = new HashSet<String>();

            for (ClinicalStageType theClinicalStage : theClinicalStages)
            {
                if (theClinicalStage.equals(ClinicalStageType.I))
                {
                    theClinicalStageNames.add("2 = Stage I");
                }
                else if (theClinicalStage.equals(ClinicalStageType.II_ALL))
                {
                    theClinicalStageNames.add("3 = Stage IIA");
                    theClinicalStageNames.add("4 = Stage IIB");
                }
                else if (theClinicalStage.equals(ClinicalStageType.II_A))
                {
                    theClinicalStageNames.add("3 = Stage IIA");
                }
                else if (theClinicalStage.equals(ClinicalStageType.II_B))
                {
                    theClinicalStageNames.add("4 = Stage IIB");
                }
                else if (theClinicalStage.equals(ClinicalStageType.III_ALL))
                {
                    theClinicalStageNames.add("5 = Stage IIIA");
                    theClinicalStageNames.add("6 = Stage IIIB");
                    theClinicalStageNames.add("7 = Stage IIIC");
                }
                else if (theClinicalStage.equals(ClinicalStageType.III_A))
                {
                    theClinicalStageNames.add("5 = Stage IIIA");
                }
                else if (theClinicalStage.equals(ClinicalStageType.III_B))
                {
                    theClinicalStageNames.add("6 = Stage IIIB");
                }
                else if (theClinicalStage.equals(ClinicalStageType.III_C))
                {
                    theClinicalStageNames.add("7 = Stage IIIC");
                }
            }

            // Add something that will not match
            if (theClinicalStageNames.size() == 0)
            {
                theClinicalStageNames.add("NO_MATCH");
            }
            theBCCriteria.setClinicalStageCollection(theClinicalStageNames);
        }

        /////////////////////////////////////////////////////
        // Handle the LD 
        /////////////////////////////////////////////////////
        if (inDTO.getDiameterOperator() != null)
        {
            theBCCriteria.setLongestDiameter(inDTO.getDiameter());
            theBCCriteria.setLongestDiameterOperator(inDTO.getDiameterOperator());
        }

        /////////////////////////////////////////////////////
        // Handle the ER status values
        /////////////////////////////////////////////////////
        if (inDTO.getErStatusValues() != null)
        {
            Set<ERstatusType> theErStatuses = inDTO.getErStatusValues();

            Set<String> theErStatusNames = new HashSet<String>();

            for (ERstatusType theErStatus : theErStatuses)
            {
                if (theErStatus == ERstatusType.ER_NEG)
                {
                    theErStatusNames.add("Negative");
                }
                else if (theErStatus == ERstatusType.ER_POS)
                {
                    theErStatusNames.add("Positive");
                }
            }

            theBCCriteria.setEstrogenReceptorStatusCollection(theErStatusNames);
        }

        /////////////////////////////////////////////////////
        // Handle the HER2 status values
        /////////////////////////////////////////////////////
        if (inDTO.getHer2StatusValues() != null)
        {
            Set<HER2statusType> theHer2Statuses = inDTO.getHer2StatusValues();

            Set<String> theHer2StatusNames = new HashSet<String>();

            for (HER2statusType theHer2Status : theHer2Statuses)
            {
                if (theHer2Status == HER2statusType.HER2_NEG)
                {
                    theHer2StatusNames.add("negative");
                }
                else if (theHer2Status == HER2statusType.HER2_POS)
                {
                    theHer2StatusNames.add("positive");
                }
            }

            theBCCriteria.setHer2AssessmentCollection(theHer2StatusNames);
        }

        /////////////////////////////////////////////////////
        // Handle the LD percent change
        /////////////////////////////////////////////////////
        if (inDTO.getLdPercentChangeOperator() != null && inDTO.getPercentLDChangeType() != null)
        {
            // Grab the second argument after the _
            String theChangeType = inDTO.getPercentLDChangeType().toString();
            String theTimeCourse = theChangeType.substring(theChangeType.indexOf('_') + 1);

            theBCCriteria.setPercentLDChange(inDTO.getLdPercentChange());
            theBCCriteria.setPercentLDChangeOperator(inDTO.getLdPercentChangeOperator());
            theBCCriteria.setPercentLDChangeTimeCourse(theTimeCourse);
        }

        /////////////////////////////////////////////////////
        // Handle the pathological tumor size
        /////////////////////////////////////////////////////
        if (inDTO.getPathTumorSizeOperator() != null)
        {
            theBCCriteria.setPathologicalTumorSize(inDTO.getPathTumorSize());
            theBCCriteria.setPathologicalTumorSizeOperator(inDTO.getPathTumorSizeOperator());
        }

        /////////////////////////////////////////////////////
        // Handle the PR status values
        /////////////////////////////////////////////////////
        if (inDTO.getPrStatusValues() != null)
        {
            Set<PRstatusType> thePrStatuses = inDTO.getPrStatusValues();

            Set<String> thePrStatusNames = new HashSet<String>();

            for (PRstatusType thePrStatus : thePrStatuses)
            {
                if (thePrStatus == PRstatusType.PR_NEG)
                {
                    thePrStatusNames.add("Negative");
                }
                else if (thePrStatus == PRstatusType.PR_POS)
                {
                    thePrStatusNames.add("Positive");
                }
            }

            theBCCriteria.setProgesteronReceptorStatusCollection(thePrStatusNames);
        }

        /////////////////////////////////////////////////////
        // Handle the RaceType values
        /////////////////////////////////////////////////////
        if (inDTO.getRaceValues() != null)
        {
            Set<RaceType> theRaceTypes = inDTO.getRaceValues();

            Set<String> theRaceTypeNames = new HashSet<String>();

            for (RaceType theRaceType : theRaceTypes)
            {
                theRaceTypeNames.add(theRaceType.toString());
            }

            theSPCriteria.setRaceCodeCollection(theRaceTypeNames);
            theBCCriteria.setStudyParticipantCriteria(theSPCriteria);
        }

        /////////////////////////////////////////////////////
        // Handle the TimeCourse values
        /////////////////////////////////////////////////////
        if (inDTO.getTimepointValues() != null)
        {
            Set<TimepointType> theTimepointTypes = inDTO.getTimepointValues();

            Set<String> theTimepointNames = new HashSet<String>();

            for (TimepointType theTimepointType : theTimepointTypes)
            {
                theTimepointNames.add(theTimepointType.toString());
            }

            theBCCriteria.setTimeCourseCollection(theTimepointNames);
        }

        /////////////////////////////////////////////////////
        // Handle the restraining values
        /////////////////////////////////////////////////////
        if (inDTO.getRestrainingSamples() != null)
        {
            theSPCriteria.setStudyParticipantIDCollection(inDTO.getRestrainingSamples());
            theBCCriteria.setStudyParticipantCriteria(theSPCriteria);
        }

        // Do the query
        ClinicalFindingHandler theHandler = theBCCriteria.getHandler();
        Collection<? extends ClinicalFinding> theFindings = theHandler.getFindings(theBCCriteria);

        logger.debug("Exiting getFindings");

        return theFindings;
    }

    // Testing purposes
    static public void main(String args[])
    {
        ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();

        Set<String> theStudyParticipantIDs = new HashSet<String>();
        theStudyParticipantIDs.add("1102");
        theStudyParticipantIDs.add("1002");
        theStudyParticipantIDs.add("1103");
        theStudyParticipantIDs.add("1104");
        theStudyParticipantIDs.add("1105");
        theStudyParticipantIDs.add("1106");
        theStudyParticipantIDs.add("1107");
        theStudyParticipantIDs.add("1108");
        theStudyParticipantIDs.add("1109");
        //dto.setRestrainingSamples(theStudyParticipantIDs);

        EnumSet<PRstatusType> thePrStatuses = EnumSet.allOf(PRstatusType.class);
        //dto.setPrStatusValues(thePrStatuses);

        EnumSet<ERstatusType> theErStatuses = EnumSet.allOf(ERstatusType.class);
        //dto.setErStatusValues(theErStatuses);

        Set<String> theSet = getInstance().getPatientDIDs(dto);

        PercentLDChangeType theChangeType = PercentLDChangeType.PERCENT_LD_CHANGE_T1_T2;
        dto.setPercentLDChangeType(theChangeType);
        dto.setLdPercentChange(10.6);
        dto.setLdPercentChangeOperator(Operator.GE);

        long theStartTime = System.currentTimeMillis();

        Set<PatientData> thePatientSet = getInstance().getClinicalData(dto);

        System.out.println("Number matched: " + thePatientSet.size());

        for (PatientData thePatient : thePatientSet)
        {
            System.out.println("ISpy ID: " + thePatient.getISPY_ID());
            System.out.println("Institute ID: " + thePatient.getInst_ID());
            System.out.println("Age Category: " + thePatient.getAgeCategory());
            System.out.println("Race: " + thePatient.getRace());
            System.out.println("Survival status: " + thePatient.getSSTAT());
            System.out.println("Survival Days: " + thePatient.getSURVDTD());
            System.out.println("Tamoxafin: " + thePatient.getTAM());
            System.out.println("Herceptin: " + thePatient.getHerceptin());
            System.out.println("Sentinal Node Collected: " + thePatient.getSentinelNodeSample());
            System.out.println("Sentinal Node Result: " + thePatient.getSentinelNodeResult());
            System.out.println("Estrogen Status: " + thePatient.getER_TS());
            System.out.println("Progesterone Status: " + thePatient.getPGR_TS());
            System.out.println("T2: " + thePatient.getClinRespT1_T2());
            System.out.println("T3: " + thePatient.getClinRespT1_T3());
            System.out.println("T4: " + thePatient.getClinRespT1_T4());
            System.out.println("T2: " + thePatient.getMriPctChangeT1_T2());
            System.out.println("T3: " + thePatient.getMriPctChangeT1_T3());
            System.out.println("T4: " + thePatient.getMriPctChangeT1_T4());
            System.out.println("RTAXILLA: " + thePatient.getRTAXILLA());
            System.out.println("BOOST: " + thePatient.getRTBOOST());
            System.out.println("Breast: " + thePatient.getRTBreast());
            System.out.println("ChestW: " + thePatient.getRTChestW());
            System.out.println("IMAMNODE: " + thePatient.getRTIMAMNODE());
            System.out.println("OTHER: " + thePatient.getRTOTHER());
            System.out.println("RTSNODE: " + thePatient.getRTSNODE());
            System.out.println("Therapy: " + thePatient.getRTTherapy());

        }
        //System.out.println("Set: " + theSet);

        System.out.println("Total time: " + (System.currentTimeMillis() - theStartTime) / 1000.0);
    }
}
