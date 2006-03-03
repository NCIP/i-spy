package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.caintegrator.application.util.StringUtils;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.io.BufferedReader;
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
	
	public void setClinicalDataFile(String clinicalDataFileName) throws IOException {
		
		  BufferedReader in = new BufferedReader(new FileReader(clinicalDataFileName));
		  String line = null;
		  
		  ClinicalData clinicalData = null;
		   
		  //Clinical Data file format:
		  //
		  //LAB_TRACK_ID	PATIENT_DID	TIME_POINT	DISEASE_STAGE	CLINICAL_RESPONSE	LD	ER	PR	HER2	TUMOR_MORPHOLOGY	PRITUM_NUCLEAR_GRADE	PRIMTUMAR_HISTOTYPE	GROSS_TUMOR_SZ	MICRO_TUMOR_SZ	AGENT_NAME	MRI_PERCENTAGE_CHANGE
		  //
		  
		  //Pattern pattern = Pattern.compile("(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t([\\S|x0B]*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)");
		  Pattern pattern = Pattern.compile("([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)");		  
		  
		  //reset the map
		  clinicalDataMap.clear();
		  Matcher matcher = null;
		  
		  while ((line=in.readLine())!= null) {
		    
			//System.out.println("processing line=" + line);
			  
			matcher = pattern.matcher(line);
			
			if (!matcher.find()) {
			  throw new IOException("Clinical data file has a format problem.");
			}
			
		    String labtrackId = matcher.group(1);
		    
		    //System.out.println("labtrackId=" + labtrackId);
		    
		    String patientId = matcher.group(2);
		    
		    //System.out.println("patientId=" + patientId);
		    
		    TimepointType timepoint = TimepointType.valueOf(matcher.group(3));
		    
		    clinicalData = new ClinicalData(labtrackId, patientId, timepoint);
		    
//		  LAB_TRACK_ID	PATIENT_DID	TIME_POINT	DISEASE_STAGE	CLINICAL_RESPONSE	LD	ER	PR	HER2	TUMOR_MORPHOLOGY	PRITUM_NUCLEAR_GRADE	PRIMTUMAR_HISTOTYPE	GROSS_TUMOR_SZ	MICRO_TUMOR_SZ	AGENT_NAME	MRI_PERCENTAGE_CHANGE
			
		    
		    if (StringUtils.isEmptyStr(matcher.group(4))) {
		      clinicalData.setDiseaseStage(DiseaseStageType.MISSING);
		    }
		    else {
		      clinicalData.setDiseaseStage(DiseaseStageType.valueOf(matcher.group(4)));
		    }
		    
		    clinicalData.setClinicalResponseFromString(matcher.group(5));
		    
		    if (!StringUtils.isEmptyStr(matcher.group(6))) {
		      clinicalData.setLongestDiameter(Double.parseDouble(matcher.group(6)));
		    }
		    
		    if (!StringUtils.isEmptyStr(matcher.group(7))) {
		      clinicalData.setErValue(Double.parseDouble(matcher.group(7)));
		    }
		    
		    if (!StringUtils.isEmptyStr(matcher.group(8))) {
		      clinicalData.setPrValue(Double.parseDouble(matcher.group(8)));
		    }
		    
		    if (!StringUtils.isEmptyStr(matcher.group(9))) {
		      clinicalData.setHer2Value(Double.parseDouble(matcher.group(9)));
		    }
		    
		    clinicalData.setTumorMorphology(matcher.group(10));
		    
		    //System.out.println("tumorMorphology=" + matcher.group(10));
		    
		    clinicalData.setPrimaryTumorNuclearGrade(matcher.group(11));
		    
		    clinicalData.setPrimaryTumorHistologyType(matcher.group(12));
		    
		    if (!StringUtils.isEmptyStr(matcher.group(13))) {
		      clinicalData.setGrossTumorSizeInCM(Double.parseDouble(matcher.group(13)));
		    }
		    
		    if (!StringUtils.isEmptyStr(matcher.group(14))) {
		      clinicalData.setMicroscopeTumorSizeInCM(Double.parseDouble(matcher.group(14)));
		    }
		    
		    if (!StringUtils.isEmptyStr(matcher.group(15))) {
		      List<String> chemAgents = StringUtils.extractTokens(matcher.group(15),"\\|" );
		      clinicalData.setChemicalAgents(chemAgents);
		    }
		    
		    if (!StringUtils.isEmptyStr(matcher.group(16))) {
		      clinicalData.setMRIpctChange(Double.parseDouble(matcher.group(16)));
		    }
		     
		    clinicalDataMap.put(labtrackId, clinicalData);
		    
		    Set<ClinicalData> timepointDataSet= timepointMap.get(clinicalData.getTimepoint());
		    if (timepointDataSet == null) {
		      timepointDataSet = new HashSet<ClinicalData>();
		      timepointMap.put(clinicalData.getTimepoint(), timepointDataSet);
		    }
		    timepointDataSet.add(clinicalData);
		    
		  }
		  
		  clinicalDataFileSet = true;
		
	}
	
	/**
	 * This method returns all labtrack ids for samples
	 * taken at the specified timepoint.
	 * @param timepoint
	 */
	public Set<String> getLabtrackIdsForTimepoint(TimepointType timepoint) {
		Set<String> labtrackIds = new HashSet<String>();
		
		Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
		if (clinDataSet != null) {
			for (ClinicalData cd:clinDataSet) {
			  labtrackIds.add(cd.getLabtrackId());
			}
		}
		return labtrackIds;
	}
	
	public Set<String> getLabtrackIdsForDiseaseStage(TimepointType timepoint, Set<DiseaseStageType> diseaseStageSet) {
		Set<String> labtrackIds = new HashSet<String>();
		ClinicalData clinData;
		
		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint); 
		
		for (String labtrackId:idsToCheck) {
		  clinData = clinicalDataMap.get(labtrackId);
		  for (DiseaseStageType ds:diseaseStageSet) {
			  if (ds==clinData.getDiseaseStage()) {
			    labtrackIds.add(labtrackId);
			  }
			  else if (ds.name().endsWith("All")) {
			     //check the prefix if it matches then add the labtrackId
				 String cdDiseaseStage = clinData.getDiseaseStage().name();
				 String[] cdStgTokens = cdDiseaseStage.split("_");
				 String[] stgTokens = ds.name().split("_");
				 if (cdStgTokens[0].equals(stgTokens[0])) {
				   labtrackIds.add(labtrackId);
				 }
			  }
		  }
		}
		return labtrackIds;
	}
	
	public Set<String> getLabtrackIdsForClinicalResponse(TimepointType timepoint, Set<ClinicalResponseType> clinicalResponseSet) {
		Set<String> labtrackIds = new HashSet<String>();
		ClinicalData clinData;
		
		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
		
		for (String labtrackId:idsToCheck) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinicalResponseSet.contains(clinData.getClinicalResponse())) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Collection<String> getLabtrackIdsForERstatus(TimepointType timepoint, Set<ERstatusType> erStatusSet) {
		Set<String> labtrackIds = new HashSet<String>();
		ClinicalData clinData;
		
		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
		
		for (String labtrackId:idsToCheck) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (erStatusSet.contains(clinData.getErStatus())) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Set<String> getLabtrackIdsForPRstatus(TimepointType timepoint, Set<PRstatusType> prStatusSet) {
		Set<String> labtrackIds = new HashSet<String>();
		ClinicalData clinData;
		
		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
		
		for (String labtrackId:idsToCheck) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (prStatusSet.contains(clinData.getPrStatus())) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Set<String> getLabtrackIdsForHER2status(TimepointType timepoint, Set<HER2statusType> her2StatusSet) {
		Set<String> labtrackIds = new HashSet<String>();
		ClinicalData clinData;
		
		Set<String> idsToCheck = getLabtrackIdsForTimepoint(timepoint);
		
		for (String labtrackId:idsToCheck) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (her2StatusSet.contains(clinData.getHER2status())) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public ClinicalData getClinicalDataForLabtrackId(String labtrackId) {
	  return clinicalDataMap.get(labtrackId);
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
	public List<ClinicalData> getClinicalDataForLabtrackIds(List<String> labtrackIds) {
		List<ClinicalData> clinicalDataList = new ArrayList<ClinicalData>();
		ClinicalData clinData = null;
		for (String labtrackId:labtrackIds) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData != null) {
		    clinicalDataList.add(clinData);
		  }
		  else {
		    logger.warn("No clinical data found for labtrackId=" + labtrackId);
		  }
		}
		return clinicalDataList;
	}
	
	
	public Map<String, ClinicalData> getClinicalDataMapForLabtrackIds(Collection<String> labtrackIds) {
	  Map<String, ClinicalData> retMap = new HashMap<String, ClinicalData>();
	  ClinicalData clinData = null;
	  for (String id:labtrackIds) {
	    clinData = clinicalDataMap.get(id);
	    if (clinData != null) {
	      retMap.put(id, clinData);
	    }
	    else {
	      logger.warn("No clinical data found for labtrackId=" + id);
	    }
	  }
	  return retMap;
	}

	public Set<String> getLabtrackIdsForTimepoints(List<TimepointType> timepoints) {
		Set<String> ids = new HashSet<String>();
		for (TimepointType tp:timepoints) {
		  ids.addAll(getLabtrackIdsForTimepoint(tp));
		}
		return ids;
	}
	
	
	/**
	 * Will get the labtrack ids associated with the parameters set in the ISPYclinicalDataQueryDTO
	 * @param clinicalDataQueryDTO
	 * @return
	 */
	public Set<String> getLabtrackIds(ISPYclinicalDataQueryDTO cDTO) {
	
		Set<TimepointType> timepoints = cDTO.getTimepointValues();
		Set<String> labtrackIds = new HashSet<String>();
		Boolean executedQuery = false;
		
		for (TimepointType tp:timepoints) {
			executedQuery = false;
		  
			if ((cDTO.getClinicalResponseValues()!= null)&&(!cDTO.getClinicalResponseValues().isEmpty())) {
		      labtrackIds.addAll(getLabtrackIdsForClinicalResponse(tp,cDTO.getClinicalResponseValues()));
		      executedQuery = true;
		    }
			
			if ((cDTO.getDiseaseStageValues() != null)&&(!cDTO.getDiseaseStageValues().isEmpty())) {
			  labtrackIds.addAll(getLabtrackIdsForDiseaseStage(tp,cDTO.getDiseaseStageValues()));
			  executedQuery = true;
			}
			
			if ((cDTO.getErStatusValues() != null)&&(!cDTO.getErStatusValues().isEmpty())) {
			  labtrackIds.addAll(getLabtrackIdsForERstatus(tp, cDTO.getErStatusValues()));
			  executedQuery = true;
			}
			
			if ((cDTO.getHer2StatusValues() != null)&&(!cDTO.getHer2StatusValues().isEmpty())) {
			  labtrackIds.addAll(getLabtrackIdsForHER2status(tp, cDTO.getHer2StatusValues()));
			  executedQuery = true;
			}
			
			if ((cDTO.getPrStatusValues() != null)&&(!cDTO.getPrStatusValues().isEmpty())) {
			  labtrackIds.addAll(getLabtrackIdsForPRstatus(tp, cDTO.getPrStatusValues()));		
			  executedQuery = true;
			}
			
			if (!executedQuery) {
			  labtrackIds.addAll(getLabtrackIdsForTimepoint(tp));
			}
				
		}
		
		return labtrackIds;
	}

}
