package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.caintegrator.application.util.StringUtils;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ClinicalFileBasedQueryService {

	private Map<String, ClinicalData> clinicalDataMap = new HashMap<String,ClinicalData>();
	private Map<TimepointType, Set<ClinicalData>> timepointMap = new HashMap<TimepointType, Set<ClinicalData>>(); 
	private Map<String, PatientData> patientDataMap = new HashMap<String, PatientData>();
	private boolean clinicalDataFileSet = false;
	private static ClinicalFileBasedQueryService instance = null;
	private static Logger logger = Logger.getLogger(ClinicalFileBasedQueryService.class);
	
	private ClinicalFileBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static ClinicalFileBasedQueryService getInstance() {
		
		  if (instance == null) {
		    instance = new ClinicalFileBasedQueryService();
		  }
		  return instance;
	}
	
	public int setPatientDataMap(String patientDataFileName) {
		
		int numRecordsLoaded = 0;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(patientDataFileName));
			
			String line = null;
			String[] tokens = null;
			
			patientDataMap.clear();
			
			while ((line=in.readLine()) != null) {
				tokens = line.split("\t", -2);
								
				PatientData pd = new PatientData(tokens[0]);
				
				pd.setDataExtractDT(tokens[1]);
				pd.setInst_ID(tokens[2]);
				pd.setAgeCat(tokens[3]);
				pd.setRace_ID(tokens[4]);
				pd.setSSTAT(tokens[5]);
				pd.setSURVDTD(tokens[6]);
				pd.setChemo(tokens[7]);
				pd.setTAM(tokens[8]);
				pd.setHerceptin(tokens[9]);
				pd.setMenoStatus(tokens[10]);
				pd.setSentinelNodeSample(tokens[11]);
				pd.setSentinelNodeResult(tokens[12]);
				pd.setHistologicGradeOS(tokens[13]);
				pd.setER_TS(tokens[14]);
				pd.setPGR_TS(tokens[15]);
				pd.setHER2CommunityPOS(tokens[16]);
				pd.setHER2CommunityMethod(tokens[17]);
				pd.setSurgeryLumpectomy(tokens[18]);
				pd.setSurgeryMastectomy(tokens[19]);
				pd.setINITLUMP_FUPMAST(tokens[20]);
				pd.setSurgery(tokens[21]);
				pd.setDCISOnly(tokens[22]);
				pd.setPTumor1SZCM_MICRO(tokens[23]);
				pd.setHistologicGradePS(tokens[24]);
				pd.setNumPosNodes(tokens[25]);
				pd.setNodesExamined(tokens[26]);
				pd.setPathologyStage(tokens[27]);
				pd.setRTTherapy(tokens[28]);
				pd.setRTBreast(tokens[29]);
				pd.setRTBOOST(tokens[30]);
				pd.setRTAXILLA(tokens[31]);
				pd.setRTSNODE(tokens[32]);
				pd.setRTIMAMNODE(tokens[33]);
				pd.setRTChestW(tokens[34]);
				pd.setRTOTHER(tokens[35]);
				pd.setTSizeClinical(tokens[36]);
				pd.setNSizeClinical(tokens[37]);
				pd.setStageTE(tokens[38]);
				pd.setStageNE(tokens[39]);
				pd.setStageME(tokens[40]);
				pd.setClinicalStage(tokens[41]);
				pd.setClinRespT1_T2(tokens[42]);
				pd.setClinRespT1_T3(tokens[43]);
				pd.setClinRespT1_T4(tokens[44]);	
				patientDataMap.put(pd.getISPY_ID(), pd);
				numRecordsLoaded++;
				
			}
	
			
		}  catch (IOException e) {
			logger.error("Error reading patientDataFileName=" + patientDataFileName);
			logger.error(e);
			return -numRecordsLoaded;
		}
		
		return numRecordsLoaded;
		
	}
	
	public int setClinicalDataFile(String clinicalDataFileName)  {
		
		  int recordCount = 0;
		
		  try {
		
		  BufferedReader in = new BufferedReader(new FileReader(clinicalDataFileName));
		  String line = null;
		  
		  ClinicalData clinicalData = null;
		  
		   
		  //Clinical Data file format:
		  //
		  //LAB_TRACK_ID	PATIENT_DID	TIME_POINT	DISEASE_STAGE	CLINICAL_RESPONSE	LD	ER	PR	HER2	TUMOR_MORPHOLOGY	PRITUM_NUCLEAR_GRADE	PRIMTUMAR_HISTOTYPE	GROSS_TUMOR_SZ	MICRO_TUMOR_SZ	AGENT_NAME	MRI_PERCENTAGE_CHANGE
		  //
		  
		  //Pattern pattern = Pattern.compile("(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t([\\S|x0B]*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)");
		  //Pattern pattern = Pattern.compile("([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)");		  
		  
		  //reset the map
		  //clinicalDataMap.clear();
		  
		  timepointMap.clear();
		  
		  //Matcher matcher = null;
		  String[] tokens;
		  
		  while ((line=in.readLine())!= null) {
		    
			//System.out.println("processing line=" + line);
			  
			//matcher = pattern.matcher(line);
			
//			if (!matcher.find()) {
//			  throw new IOException("Clinical data file has a format problem.");
//			}
			  
			tokens = line.split("\t", -2);
			
		    String labtrackId = tokens[0];
		    
		    //NOTE: labtrackId in no longer associated with the clinical data. Labtrack ids should
		    //be gotten through the IdMapper service.
		    
		    //System.out.println("labtrackId=" + labtrackId);
		    
		    String patientDID = tokens[1];
		    
		    //System.out.println("patientId=" + patientId);
		    
		    TimepointType timepoint = TimepointType.valueOf(tokens[2]);
		    
		    clinicalData = new ClinicalData(patientDID, timepoint);
		    
//		  LAB_TRACK_ID	PATIENT_DID	TIME_POINT	DISEASE_STAGE	CLINICAL_RESPONSE	LD	ER	PR	HER2	TUMOR_MORPHOLOGY	PRITUM_NUCLEAR_GRADE	PRIMTUMAR_HISTOTYPE	GROSS_TUMOR_SZ	MICRO_TUMOR_SZ	AGENT_NAME	MRI_PERCENTAGE_CHANGE
			
		    
		    if (StringUtils.isEmptyStr(tokens[3])) {
		      clinicalData.setDiseaseStage(ClinicalStageType.MISSING);
		    }
		    else {
		      clinicalData.setDiseaseStage(ClinicalStageType.valueOf(tokens[3]));
		    }
		    
		    clinicalData.setClinicalResponseFromString(tokens[4]);
		    
		    clinicalData.setLongestDiameter(StringUtils.getDouble(tokens[5]));
		 
		    clinicalData.setErValue(StringUtils.getDouble(tokens[6]));
		  
		    clinicalData.setPrValue(StringUtils.getDouble(tokens[7]));
		   
		    clinicalData.setHer2Value(StringUtils.getDouble(tokens[8]));
		    
		    clinicalData.setTumorMorphology(tokens[9]);
		    
		    //System.out.println("tumorMorphology=" + matcher.group(10));
		    
		    clinicalData.setPrimaryTumorNuclearGrade(tokens[10]);
		    
		    clinicalData.setPrimaryTumorHistologyType(tokens[11]);
		    		   
		    clinicalData.setGrossTumorSizeInCM(StringUtils.getDouble(tokens[12]));
		  
		    clinicalData.setMicroscopeTumorSizeInCM(StringUtils.getDouble(tokens[13]));
		    
		    
		    if (!StringUtils.isEmptyStr(tokens[14])) {
		      List<String> chemAgents = StringUtils.extractTokens(tokens[14],"\\|" );
		      clinicalData.setChemicalAgents(chemAgents);
		    }
		    
		
		    clinicalData.setMRIpctChange(StringUtils.getDouble(tokens[15]));
		    
		   
		    clinicalDataMap.put(patientDID, clinicalData);
		    
		    Set<ClinicalData> timepointDataSet= timepointMap.get(clinicalData.getTimepoint());
		    if (timepointDataSet == null) {
		      timepointDataSet = new HashSet<ClinicalData>();
		      timepointMap.put(clinicalData.getTimepoint(), timepointDataSet);
		    }
		    timepointDataSet.add(clinicalData);
		    recordCount++;
		  }
		  
		  clinicalDataFileSet = true;
		  logger.info("Successfully loaded clinicalDataFile=" + clinicalDataFileName + " numRecords=" + recordCount );
		  }
		  catch (IOException ex) {
		    logger.error("Caught IOException while loading clinical data file=" + clinicalDataFileName + " recordCount=" + recordCount);
		    logger.error(ex);
		    return -recordCount;
		  }
		  return recordCount;
	}
	
	/**
	 * This method returns all labtrack ids for samples
	 * taken at the specified timepoint.
	 * @param timepoint
	 */
//	public Set<String> getLabtrackIdsForTimepoint(TimepointType timepoint) {
//		Set<String> labtrackIds = new HashSet<String>();
//		
//		Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
//		if (clinDataSet != null) {
//			for (ClinicalData cd:clinDataSet) {
//			  labtrackIds.add(cd.getLabtrackId());
//			}
//		}
//		return labtrackIds;
//	}
	
	/**
	 * Get the clinical data for a given timepoint.
	 */
	public Set<ClinicalData> getClinicalDataForTimepoint(TimepointType timepoint) {
		
		return timepointMap.get(timepoint);
		
	}
	
//	public Set<String> getLabtrackIdsForDiseaseStage(TimepointType timepoint, Set<DiseaseStageType> diseaseStageSet) {
//		Set<String> labtrackIds = new HashSet<String>();
//		ClinicalData clinData;
//		
//		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint); 
//		
//		for (String labtrackId:idsToCheck) {
//		  clinData = clinicalDataMap.get(labtrackId);
//		  for (DiseaseStageType ds:diseaseStageSet) {
//			  if (ds==clinData.getDiseaseStage()) {
//			    labtrackIds.add(labtrackId);
//			  }
//			  else if (ds.name().endsWith("ALL")) {
//			     //check the prefix if it matches then add the labtrackId
//				 String cdDiseaseStage = clinData.getDiseaseStage().name();
//				 String[] cdStgTokens = cdDiseaseStage.split("_");
//				 String[] stgTokens = ds.name().split("_");
//				 if (cdStgTokens[0].equals(stgTokens[0])) {
//				   labtrackIds.add(labtrackId);
//				 }
//			  }
//		  }
//		}
//		return labtrackIds;
//	}
	
	public Set<String> getPatientDIDsForDiseaseStage(TimepointType timepoint, Set<ClinicalStageType> diseaseStageSet) {
	
		Set<String> patientDIDs = new HashSet<String>();
		
		Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
		
		for (ClinicalData clinData : clinDataSet) {
		  for (ClinicalStageType ds:diseaseStageSet) {
			  if (ds==clinData.getDiseaseStage()) {
			    patientDIDs.add(clinData.getPatientDID());
			  }
			  else if (ds.name().endsWith("ALL")) {
			     //check the prefix if it matches then add the labtrackId
				 String cdDiseaseStage = clinData.getDiseaseStage().name();
				 String[] cdStgTokens = cdDiseaseStage.split("_");
				 String[] stgTokens = ds.name().split("_");
				 if (cdStgTokens[0].equals(stgTokens[0])) {
				   patientDIDs.add(clinData.getPatientDID());
				 }
			  }
		  }	
		}
		
		return patientDIDs;
	}
	
//	public Set<String> getLabtrackIdsForClinicalResponse(TimepointType timepoint, Set<ClinicalResponseType> clinicalResponseSet) {
//		Set<String> labtrackIds = new HashSet<String>();
//		ClinicalData clinData;
//		
//		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
//		
//		for (String labtrackId:idsToCheck) {
//		  clinData = clinicalDataMap.get(labtrackId);
//		  if (clinicalResponseSet.contains(clinData.getClinicalResponse())) {
//		    labtrackIds.add(labtrackId);
//		  }
//		}
//		return labtrackIds;
//	}
	
	public Set<String> getPatientDIDsForClinicalResponse(TimepointType timepoint, Set<ClinicalResponseType> clinicalResponseSet) {
        Set<String> patientDIDs = new HashSet<String>();
		
		Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
		
		for (ClinicalData clinData : clinDataSet) {
		  if (clinicalResponseSet.contains(clinData.getClinicalResponse())) {
		    patientDIDs.add(clinData.getPatientDID());
		  }
		}
		
		return patientDIDs;
	}
	
//	public Collection<String> getLabtrackIdsForERstatus(TimepointType timepoint, Set<ERstatusType> erStatusSet) {
//		Set<String> labtrackIds = new HashSet<String>();
//		ClinicalData clinData;
//		
//		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
//		
//		for (String labtrackId:idsToCheck) {
//		  clinData = clinicalDataMap.get(labtrackId);
//		  if (erStatusSet.contains(clinData.getErStatus())) {
//		    labtrackIds.add(labtrackId);
//		  }
//		}
//		return labtrackIds;
//	}
	
	public Set<String> getPatientDIDsForERstatus(TimepointType timepoint, Set<ERstatusType> erStatusSet) {
	  
	  Set<String> patientDIDs = new HashSet<String>();
	  
	  Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
		
	  for (ClinicalData clinData : clinDataSet) {
	    if (erStatusSet.contains(clinData.getErStatus())) {
	      patientDIDs.add(clinData.getPatientDID());
	    }
	  }
	  
	  return patientDIDs;
	}
	
	
//	public Set<String> getLabtrackIdsForPRstatus(TimepointType timepoint, Set<PRstatusType> prStatusSet) {
//		Set<String> labtrackIds = new HashSet<String>();
//		ClinicalData clinData;
//		
//		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
//		
//		for (String labtrackId:idsToCheck) {
//		  clinData = clinicalDataMap.get(labtrackId);
//		  if (prStatusSet.contains(clinData.getPrStatus())) {
//		    labtrackIds.add(labtrackId);
//		  }
//		}
//		return labtrackIds;
//	}
	
	public Set<String> getPatientDIDsForPRstatus(TimepointType timepoint, Set<PRstatusType> prStatusSet) {
		  
		  Set<String> patientDIDs = new HashSet<String>();
		  
		  Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
			
		  for (ClinicalData clinData : clinDataSet) {
		    if (prStatusSet.contains(clinData.getPrStatus())) {
		      patientDIDs.add(clinData.getPatientDID());
		    }
		  }
		  
		  return patientDIDs;
	}
	
//	public Set<String> getLabtrackIdsForHER2status(TimepointType timepoint, Set<HER2statusType> her2StatusSet) {
//		Set<String> labtrackIds = new HashSet<String>();
//		ClinicalData clinData;
//		
//		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
//		
//		for (String labtrackId:idsToCheck) {
//		  clinData = clinicalDataMap.get(labtrackId);
//		  if (her2StatusSet.contains(clinData.getHER2status())) {
//		    labtrackIds.add(labtrackId);
//		  }
//		}
//		return labtrackIds;
//	}
	
	
	public Set<String> getPatientDIDsForHER2status(TimepointType timepoint, Set<HER2statusType> her2StatusSet) {
		  
		  Set<String> patientDIDs = new HashSet<String>();
		  
		  Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
			
		  for (ClinicalData clinData : clinDataSet) {
		    if (her2StatusSet.contains(clinData.getHER2status())) {
		      patientDIDs.add(clinData.getPatientDID());
		    }
		  }
		  
		  return patientDIDs;
	}
	
	
	/**
	 * This method will return clinical data for the specified list of 
	 * labtrack ids.  In addition the order will be preserved so that the list of 
	 * clinical data objects returned will match the order of the labtrackIds pass in the
	 * input.
	 * 
	 * @param labtrackIds
	 * @return
	 */
//	public List<ClinicalData> getClinicalDataForLabtrackIds(List<String> labtrackIds) {
//		List<ClinicalData> clinicalDataList = new ArrayList<ClinicalData>();
//		ClinicalData clinData = null;
//		for (String labtrackId:labtrackIds) {
//		  clinData = clinicalDataMap.get(labtrackId);
//		  if (clinData != null) {
//		    clinicalDataList.add(clinData);
//		  }
//		  else {
//		    logger.warn("No clinical data found for labtrackId=" + labtrackId);
//		  }
//		}
//		return clinicalDataList;
//	}
	
	public PatientData getPatientDataForPatientDID(String patientDID) {
		
		PatientData pd = patientDataMap.get(patientDID);
		
		if (pd == null) {
		  logger.warn("No patient data object found for patientDID=" + patientDID);
		}
		
		return pd;
	}
	
	public List<PatientData> getPatientDataForPatientDIDs(List<String> patientDIDs) {
		
		List<PatientData> retList = new ArrayList<PatientData>();
		for (String patientDID : patientDIDs) {
		  PatientData pd = getPatientDataForPatientDID(patientDID);
		  if (pd != null) {
		    retList.add(pd);
		  }
		}
		return retList;	
	}
	
	public ClinicalData getClinicalDataForPatientDID(String patientDID, TimepointType timepoint) {
	  
		Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
		
		for (ClinicalData cd : clinDataSet) {
		  if (cd.getPatientDID().equals(patientDID)) {
		    return cd;
		  }
		}
		
		return null;
	}
	
	public List<ClinicalData> getClinicalDataForPatientDIDs(List<String> patientDIDs, TimepointType timepoint) {
		List<ClinicalData> clinicalDataList = new ArrayList<ClinicalData>();
        ClinicalData clinData = null;
		
        for (String patientDID : patientDIDs) {
        
          clinData = getClinicalDataForPatientDID(patientDID, timepoint);
          if (clinData != null) {
            clinicalDataList.add(clinData);
          }
          else {
            logger.warn("No clinical data found for patientDID=" + patientDID);
          }
        }
        return clinicalDataList;
		
	}
	
	
//	public Map<String, ClinicalData> getClinicalDataMapForLabtrackIds(Collection<String> labtrackIds) {
//	  Map<String, ClinicalData> retMap = new HashMap<String, ClinicalData>();
//	  ClinicalData clinData = null;
//	  for (String id:labtrackIds) {
//	    clinData = clinicalDataMap.get(id);
//	    if (clinData != null) {
//	      retMap.put(id, clinData);
//	    }
//	    else {
//	      logger.warn("No clinical data found for labtrackId=" + id);
//	    }
//	  }
//	  return retMap;
//	}

//	public Set<String> getLabtrackIdsForTimepoints(List<TimepointType> timepoints) {
//		Set<String> ids = new HashSet<String>();
//		for (TimepointType tp:timepoints) {
//		  ids.addAll(getLabtrackIdsForTimepoint(tp));
//		}
//		return ids;
//	}
	
	
//	public Set<String> getPatientDIDsForTimepoints(List<TimepointType> timepoints) {
//		Set<String> ids = new HashSet<String>();
//		for (TimepointType tp: timepoints) {
//		  ids.addAll(getPatientDIDsForTimepoint(tp));
//		}
//		return ids;
//	}
	
	
	/**
	 * May want to add ability to and/or constraints
	 */
	public Set<String> getPatientDIDs(ISPYclinicalDataQueryDTO cDTO) {
	  
		Set<TimepointType> timepoints = cDTO.getTimepointValues();
		Set<String> patientDIDs = new HashSet<String>();
		Boolean executedQuery = false;
		
		for (TimepointType tp:timepoints) {
			executedQuery = false;
		  
			if ((cDTO.getClinicalResponseValues()!= null)&&(!cDTO.getClinicalResponseValues().isEmpty())) {
		      patientDIDs.addAll(getPatientDIDsForClinicalResponse(tp,cDTO.getClinicalResponseValues()));
		      executedQuery = true;
		    }
			
			if ((cDTO.getDiseaseStageValues() != null)&&(!cDTO.getDiseaseStageValues().isEmpty())) {
			  patientDIDs.addAll(getPatientDIDsForDiseaseStage(tp,cDTO.getDiseaseStageValues()));
			  executedQuery = true;
			}
			
			if ((cDTO.getErStatusValues() != null)&&(!cDTO.getErStatusValues().isEmpty())) {
			  patientDIDs.addAll(getPatientDIDsForERstatus(tp, cDTO.getErStatusValues()));
			  executedQuery = true;
			}
			
			if ((cDTO.getHer2StatusValues() != null)&&(!cDTO.getHer2StatusValues().isEmpty())) {
			  patientDIDs.addAll(getPatientDIDsForHER2status(tp, cDTO.getHer2StatusValues()));
			  executedQuery = true;
			}
			
			if ((cDTO.getPrStatusValues() != null)&&(!cDTO.getPrStatusValues().isEmpty())) {
			  patientDIDs.addAll(getPatientDIDsForPRstatus(tp, cDTO.getPrStatusValues()));		
			  executedQuery = true;
			}
			
			if (!executedQuery) {
			  //add all patientDIDs to the return list?
				
			}
			
		}
		
		return patientDIDs;
		
	}
	
	/**
	 * Will get the labtrack ids associated with the parameters set in the ISPYclinicalDataQueryDTO
	 * @param clinicalDataQueryDTO
	 * @return
	 */
//	public Set<String> getLabtrackIds(ISPYclinicalDataQueryDTO cDTO) {
//	
//		Set<TimepointType> timepoints = cDTO.getTimepointValues();
//		Set<String> labtrackIds = new HashSet<String>();
//		Boolean executedQuery = false;
//		
//		for (TimepointType tp:timepoints) {
//			executedQuery = false;
//		  
//			if ((cDTO.getClinicalResponseValues()!= null)&&(!cDTO.getClinicalResponseValues().isEmpty())) {
//		      labtrackIds.addAll(getLabtrackIdsForClinicalResponse(tp,cDTO.getClinicalResponseValues()));
//		      executedQuery = true;
//		    }
//			
//			if ((cDTO.getDiseaseStageValues() != null)&&(!cDTO.getDiseaseStageValues().isEmpty())) {
//			  labtrackIds.addAll(getLabtrackIdsForDiseaseStage(tp,cDTO.getDiseaseStageValues()));
//			  executedQuery = true;
//			}
//			
//			if ((cDTO.getErStatusValues() != null)&&(!cDTO.getErStatusValues().isEmpty())) {
//			  labtrackIds.addAll(getLabtrackIdsForERstatus(tp, cDTO.getErStatusValues()));
//			  executedQuery = true;
//			}
//			
//			if ((cDTO.getHer2StatusValues() != null)&&(!cDTO.getHer2StatusValues().isEmpty())) {
//			  labtrackIds.addAll(getLabtrackIdsForHER2status(tp, cDTO.getHer2StatusValues()));
//			  executedQuery = true;
//			}
//			
//			if ((cDTO.getPrStatusValues() != null)&&(!cDTO.getPrStatusValues().isEmpty())) {
//			  labtrackIds.addAll(getLabtrackIdsForPRstatus(tp, cDTO.getPrStatusValues()));		
//			  executedQuery = true;
//			}
//			
//			if (!executedQuery) {
//			  labtrackIds.addAll(getLabtrackIdsForTimepoint(tp));
//			}
//				
//		}
//		
//		return labtrackIds;
//	}

}
