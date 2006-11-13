package gov.nih.nci.ispy.service.ihc;

import gov.nih.nci.breastCancer.dto.BreastCancerClinicalFindingCriteria;
import gov.nih.nci.breastCancer.dto.StudyParticipantCriteria;
import gov.nih.nci.breastCancer.service.ClinicalFindingHandler;
import gov.nih.nci.caintegrator.domain.finding.bean.SpecimenBasedMolecularFinding;
import gov.nih.nci.caintegrator.domain.finding.clinical.bean.ClinicalFinding;
import gov.nih.nci.caintegrator.domain.finding.clinical.breastCancer.bean.BreastCancerClinicalFinding;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.IHCFinding;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LevelOfExpressionIHCFinding;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.IHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.protein.ProteinBiomarkerCriteia;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.caintegrator.studyQueryService.finding.SpecimenBasedMolecularFindingHandler;
import gov.nih.nci.caintegrator.studyQueryService.ihc.LevelOfExpressionIHCFindingHandler;
import gov.nih.nci.caintegrator.util.HibernateUtil;
import gov.nih.nci.ispy.dto.query.IHCLevelOfExpressionQueryDTO;

import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.AgeCategoryType;
import gov.nih.nci.ispy.service.clinical.ClinicalCGOMBasedQueryService;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.common.TimepointType;



import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class IHCCGOMBasedQueryService implements IHCDataService {
	
	private static IHCCGOMBasedQueryService ourInstance = null;
	private static Logger logger = Logger.getLogger(IHCCGOMBasedQueryService.class);


	public IHCCGOMBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}

	 /**
     * Singleton for the database based query service
     * 
     * @return the static instance of the query service
     */
    public static IHCCGOMBasedQueryService getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new IHCCGOMBasedQueryService();
        }
        return ourInstance;
    }
	 /**
     * Populate a set of patient ID's matching the criteria in the DTO
     * 
     * @param inDTO, is the DTO used to query for the patient ID's
     * 
     * @return the set of patient ID's corresponding to the DTO
     */
	
	public Set<IHCLevelOfExpressionData> getIHCLevelOfExpData(IHCLevelOfExpressionQueryDTO dto) {
		
		    logger.debug("Entering getIHCLevelOfExpData");
		    Set<IHCLevelOfExpressionData> theReturnSet = new HashSet<IHCLevelOfExpressionData>();
	        try
	        {
	            // Get the findings
	            Collection<? extends SpecimenBasedMolecularFinding> theFindings = getFindings(dto);

	            logger.info("Matched " + theFindings.size() + " Findings");

	            // Loop through the set and get the ID's
	            for (SpecimenBasedMolecularFinding theFinding : theFindings)
	            	
	            {   LevelOfExpressionIHCFinding theLevelOfExpFinding = (LevelOfExpressionIHCFinding) theFinding;
	                theReturnSet.add(populateIHCLevelOfExpressionData(theLevelOfExpFinding));
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
	
	
	  // Create/populate a IHCLevelOfExpressinData based on the LevelOfExpressionIHCFinding finding
    private IHCLevelOfExpressionData populateIHCLevelOfExpressionData(LevelOfExpressionIHCFinding inFinding)
    {
    	logger.debug("Entering populateIHCLevelOfExpressionData");
    	IHCLevelOfExpressionData theIHCLevelOfExpData = new IHCLevelOfExpressionData();
    	
    	 //////////////////////////////////////////////////////////
        // Map the intensity of stain
        //////////////////////////////////////////////////////////
        if (inFinding.getStainIntensity() != null)
        {
        	theIHCLevelOfExpData.setIntensityOfStrain(inFinding.getStainIntensity());
        }
    	
        ////////////////////////////////////////////////////////
        // map percent positive absolute number
        //////////////////////////////////////////////////////////
        if (inFinding.getPercentPositive() != null)
        {
        	theIHCLevelOfExpData.setPercentPosNum(inFinding.getPercentPositive());
        }
         
        // map the percent positive lower side
        
        if (inFinding.getPercentPositiveRangeMin() != null)
        {
        	theIHCLevelOfExpData.setPercentPosNumMin(inFinding.getPercentPositiveRangeMin());
        }
        
       // map the percent positive upper side
        
        if (inFinding.getPercentPositiveRangeMax() != null)
        {
        	theIHCLevelOfExpData.setPercentPosNumMax(inFinding.getPercentPositiveRangeMax());
        }
        
        // map the localization of stain
        
        if (inFinding.getStainLocalization() != null)
        {
        	theIHCLevelOfExpData.setLocalizationOfStain(inFinding.getStainLocalization());
        }
        
       // map the distribution of stain
        
        if (inFinding.getStainDistribution() != null)
        {
        	theIHCLevelOfExpData.setDistributionOfStain(inFinding.getStainDistribution());
        }
        
        // map the time point
        
        if (inFinding.getSpecimen().getTimePoint() != null) {
        	theIHCLevelOfExpData.setTimePoint(inFinding.getSpecimen().getTimePoint().toString());
        }
        
       // map the biomarker
        
        if (inFinding.getProteinBiomarker().getName() != null) {
        	theIHCLevelOfExpData.setBioMarker(inFinding.getProteinBiomarker().getName());
        }
        
        logger.debug("Exiting populateIHCLevelOfExpressionData");

        return theIHCLevelOfExpData;
    	
    }
	   
   private Collection<? extends SpecimenBasedMolecularFinding> getFindings(IHCLevelOfExpressionQueryDTO inDTO){
		  logger.debug("getIHCLevelOfExpressionFindings");
		  
		  LevelOfExpressionIHCFindingCriteria theIHCLevelOfExpCriteria = new LevelOfExpressionIHCFindingCriteria();
		  SpecimenCriteria theSPCriteria = new SpecimenCriteria();
		  ProteinBiomarkerCriteia thePBCriteria = new ProteinBiomarkerCriteia();
		  
		  // intensity of stain
		  
		  if (inDTO.getIntensityOfStainValues() != null) {
			  EnumSet<IntensityOfStainType> theIntensityValues = inDTO.getIntensityOfStainValues();
			  Set<String> theIntensityNames = new HashSet<String>();
			  for (IntensityOfStainType theIntensity : theIntensityValues) {
				  if (theIntensity.equals(IntensityOfStainType.NEGATIVE))
	                {
					  theIntensityNames.add("NEGATIVE");
	                }
				  else if (theIntensity.equals(IntensityOfStainType.BORDERLINE))
	                {
					  theIntensityNames.add("BORDERLINE");
	                }
				  else if (theIntensity.equals(IntensityOfStainType.WEAK))
	                {
					  theIntensityNames.add("WEAK");
	                }
				  else if (theIntensity.equals(IntensityOfStainType.MODERATE_STRONG))
	                {
					  theIntensityNames.add("MODERATE_STRONG");
	                }
				  else if (theIntensity.equals(IntensityOfStainType.UNEVALUABLE))
	                {
					  theIntensityNames.add("UNEVALUABLE");
	                }
	            
			  }
			  if (theIntensityNames.size() == 0)
	            {
				  theIntensityNames.add("NO_MATCH");
	            }
			  theIHCLevelOfExpCriteria.setStainIntensityCollection(theIntensityNames);
	     

		  }
		  
		  // time point
		  
		  if (inDTO.getTimepointValues() != null) {
			  EnumSet<TimepointType> theTPs = inDTO.getTimepointValues();
			  Set<String> theTPNames = new HashSet<String>();
			  for (TimepointType theTimePoint : theTPs) {
				  if (theTimePoint.equals(TimepointType.T1))
	                {
					  theTPNames.add("T1");
	                }
				  else if (theTimePoint.equals(TimepointType.T2))
	                {
					  theTPNames.add("T2");
	                }
				  else if (theTimePoint.equals(TimepointType.T3))
	                {
					  theTPNames.add("T3");
	                }
				  else if (theTimePoint.equals(TimepointType.T4))
	                {
					  theTPNames.add("T4");
	                }
				 
	            
			  }
			  if (theTPNames.size() == 0)
	            {
				  theTPNames.add("NO_MATCH");
	            }
			  theSPCriteria.setTimeCourseCollection(theTPNames);
			  theIHCLevelOfExpCriteria.setSpecimenCriteria(theSPCriteria);
	     

		  }
		  
		  // protein biomarker
		  
		  if (inDTO.getBiomarkers() != null) {
			  Set<String> theBiomarkers = inDTO.getBiomarkers();
			  thePBCriteria.setProteinNameCollection(theBiomarkers);		 
			  theIHCLevelOfExpCriteria.setProteinBiomarkerCrit(thePBCriteria);	     

		  }
		  
		  // percertPosNum
		  
		  if (inDTO.getAbsolutePercentPositiveValue() != null) {
			  theIHCLevelOfExpCriteria.setPercentPositive(inDTO.getAbsolutePercentPositiveValue());			  
		  }
		  
		  // percent percent positive lower
		  
		  if (inDTO.getPercentPositiveMinValue() != null) {
			  theIHCLevelOfExpCriteria.setPercentPositiveRangeMin(inDTO.getPercentPositiveMinValue());			  
		  }
          // percent percent positive Upper
		  
		  if (inDTO.getPercentPositiveMaxValue() != null) {
			  theIHCLevelOfExpCriteria.setPercentPositiveRangeMax(inDTO.getPercentPositiveMaxValue());			  
		  }
		  
		  // localization of stain
		  
		  if (inDTO.getLocalizationValues() != null) {
			  EnumSet<LocalizationType> theLocalizationValues = inDTO.getLocalizationValues();
			  Set<String> theLocalizationNames = new HashSet<String>();   
			  for (LocalizationType theLocalization : theLocalizationValues) {
				  if (theLocalization.equals(LocalizationType.NONE))
	                {
					  theLocalizationNames.add("NONE");
	                }
				  else if (theLocalization.equals(LocalizationType.MEMBRANE))
	                {
					  theLocalizationNames.add("MEMBRANE");
	                }
				  else if (theLocalization.equals(LocalizationType.NUCLEUS))
	                {
					  theLocalizationNames.add("NUCLEUS");
	                }
				  else if (theLocalization.equals(LocalizationType.CYTOPLASM))
	                {
					  theLocalizationNames.add("CYTOPLASM");
	                }
				  else if (theLocalization.equals(LocalizationType.MEMBRANE_AND_CYTOPLASM))
	                {
					  theLocalizationNames.add("MEMBRANE_AND_CYTOPLASM");
	                }
				  else if (theLocalization.equals(LocalizationType.NUCLEAR_AND_CYTOPLASM))
	                {
					  theLocalizationNames.add("NUCLEAR_AND_CYTOPLASM");
	                }
				  else if (theLocalization.equals(LocalizationType.NA))
	                {
					  theLocalizationNames.add("NA");
	                }
	            
			  }
			  if (theLocalizationNames.size() == 0)
	            {
				  theLocalizationNames.add("NO_MATCH");
	            }
			  theIHCLevelOfExpCriteria.setStainLocalizationCollection(theLocalizationNames);
	     

		  }
		  
		  // distribution of stain
		  
		  
		  if (inDTO.getDistributionValues() != null) {
			  EnumSet<DistributionType> theDistributionValues = inDTO.getDistributionValues();
			  Set<String> theDistributionNames = new HashSet<String>();  
			  for (DistributionType theDistribution : theDistributionValues) {
				  if (theDistribution.equals(LocalizationType.NONE))
	                {
					  theDistributionNames.add("NONE");
	                }
				  else if (theDistribution.equals(DistributionType.HOMOGENOUS))
	                {
					  theDistributionNames.add("HOMOGENOUS");
	                }
				  else if (theDistribution.equals(DistributionType.HETEROGENOUS))
	                {
					  theDistributionNames.add("HETEROGENOUS");
	                }
				
	            
			  }
			  if (theDistributionNames.size() == 0)
	            {
				  theDistributionNames.add("NO_MATCH");
	            }
			  theIHCLevelOfExpCriteria.setStainDistributionCollection(theDistributionNames);
	     

		  }
		  
		  
		  

	        // Do the query
	        //SpecimenBasedMolecularFindingHandler theHandler = theIHCLevelOfExpCriteria.getHandler();
		  
		    LevelOfExpressionIHCFindingHandler theHandler = theIHCLevelOfExpCriteria.getHandler();
	        Collection<? extends SpecimenBasedMolecularFinding> theFindings = theHandler.getLevelExpFindings(theIHCLevelOfExpCriteria);

	        logger.debug("Exiting getFindings");

	        return theFindings;
	    }
		
	  
	  // Testing purposes
	    static public void main(String args[])
	    {
	    	
	    }
	  

}
