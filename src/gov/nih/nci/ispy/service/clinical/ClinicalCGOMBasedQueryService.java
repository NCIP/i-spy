package gov.nih.nci.ispy.service.clinical;

/**
 * caIntegrator License
 * 
 * Copyright 2001-2006 Science Applications International Corporation ("SAIC"). 
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

import gov.nih.nci.breastCancer.dto.BreastCancerClinicalFindingCriteria;
import gov.nih.nci.breastCancer.dto.StudyParticipantCriteria;
import gov.nih.nci.breastCancer.service.ClinicalFindingHandler;
import gov.nih.nci.caintegrator.domain.common.bean.NumericMeasurement;
import gov.nih.nci.caintegrator.domain.finding.clinical.bean.ClinicalFinding;
import gov.nih.nci.caintegrator.domain.finding.clinical.breastCancer.bean.BreastCancerClinicalFinding;
import gov.nih.nci.caintegrator.domain.study.bean.Activity;
import gov.nih.nci.caintegrator.domain.study.bean.Procedure;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.util.HibernateUtil;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.util.*;

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
                theReturnSet.add(theFinding.getStudyParticipant().getId());
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
            populateT2ToT4Data(theReturnSet);
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

        PatientData thePatientData = new PatientData(inFinding.getStudyParticipant().getId());

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
        // Map the survival status - TODO make sure the data is in the DB correctly
        //////////////////////////////////////////////////////////
        if (inFinding.getStudyParticipant().getSurvivalStatus() != null)
        {
            thePatientData.setSSTAT(inFinding.getStudyParticipant().getSurvivalStatus());
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
            Set<Activity> theActivities = inFinding.getStudyParticipant().getActivityCollection();

            for (Activity theActivity : theActivities)
            {
                /////////////////////////////////////////////////////////
                // Handle the procedure types
                /////////////////////////////////////////////////////////
                if (theActivity instanceof Procedure)
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
                                thePatientData.setRTTherapy("2=Yes");
                            }
                            else
                            {
                                thePatientData.setRTTherapy("1=No");
                            }
                        }
                        else
                        {
                            ///////////////////////////////////////////////////
                            // Handle the different locations
                            ///////////////////////////////////////////////////
                            if (theProcedure.getTargetSiteCode().equals("RTBREAST"))
                            {
                                thePatientData.setRTBreast(theStatus);
                            }
                            else if (theProcedure.getTargetSiteCode().equals("RTBOOST"))
                            {
                                thePatientData.setRTBOOST(theStatus);
                            }
                            else if (theProcedure.getTargetSiteCode().equals("RTAXILLA"))
                            {
                                thePatientData.setRTAXILLA(theStatus);
                            }
                            else if (theProcedure.getTargetSiteCode().equals("RTSNODE"))
                            {
                                thePatientData.setRTSNODE(theStatus);
                            }
                            else if (theProcedure.getTargetSiteCode().equals("RTIMAMNODE"))
                            {
                                thePatientData.setRTIMAMNODE(theStatus);
                            }
                            else if (theProcedure.getTargetSiteCode().equals("RTCHESTW"))
                            {
                                thePatientData.setRTChestW(theStatus);
                            }
                            else if (theProcedure.getTargetSiteCode().equals("RTOTHER"))
                            {
                                thePatientData.setRTOTHER(theStatus);
                            }
                        }
                    }
                }
            }
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
            thePatientData.setMorphPatternBsl(inFinding.getNodalMorphology().getValue());
        }

        /////////////////////////////////////////////////////////
        // Handle the nodes examined
        /////////////////////////////////////////////////////////
        if (inFinding.getNumNodesExamined() != null)
        {
            thePatientData.setNodesExamined(inFinding.getNumNodesExamined().getAbsoluteValue().toString());
        }

        /////////////////////////////////////////////////////////
        // Handle the nodes positive
        /////////////////////////////////////////////////////////
        if (inFinding.getNumPosNodes() != null)
        {
            thePatientData.setNumPosNodes(inFinding.getNumPosNodes().getAbsoluteValue().toString());
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

        logger.debug("Exiting populatePatientData");

        return thePatientData;
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

            System.out.println("Putting: " + thePatientData.getISPY_ID() + " " + thePatientData);
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

            theSPCriteria.setAgentNames(theAgentNames);
            theBCCriteria.setStudyParticipantCriteria(theSPCriteria);
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
