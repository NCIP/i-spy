package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.caintegrator.application.util.StringUtils;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClinicalFileBasedQueryService {

	private Map<String, ClinicalData> clinicalDataMap = new HashMap<String,ClinicalData>();
	private boolean clinicalDataFileSet = false;
	private static ClinicalFileBasedQueryService instance = null;
	
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
		      clinicalData.setDiseaseStage(DiseaseStageType.Missing);
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
			  
		  }
		  
		  clinicalDataFileSet = true;
		
	}
	
	/**
	 * This method returns all labtrack ids for samples
	 * taken at the specified timepoint.
	 * @param timepoint
	 */
	public Collection<String> getLabtrackIdsForTimepoint(TimepointType timepoint) {
		List<String> labtrackIds = new ArrayList<String>();
		ClinicalData clinData;
		for (String labtrackId:clinicalDataMap.keySet()) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData.getTimepoint()==timepoint) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Collection<String> getLabtrackIdsForDiseaseStage(DiseaseStageType stage) {
		List<String> labtrackIds = new ArrayList<String>();
		ClinicalData clinData;
		for (String labtrackId:clinicalDataMap.keySet()) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData.getDiseaseStage()==stage) {
		    labtrackIds.add(labtrackId);
		  }
		  else if (stage.name().endsWith("All")) {
		     //check the prefix if it matches then add the labtrackId
			 String cdDiseaseStage = clinData.getDiseaseStage().name();
			 String[] cdStgTokens = cdDiseaseStage.split("_");
			 String[] stgTokens = stage.name().split("_");
			 if (cdStgTokens[0].equals(stgTokens[0])) {
			   labtrackIds.add(labtrackId);
			 }
		  }
		}
		return labtrackIds;
	}
	
	public Collection<String> getLabtrackIdsForPatientResponse(ClinicalResponseType clinicalResponse) {
		List<String> labtrackIds = new ArrayList<String>();
		ClinicalData clinData;
		for (String labtrackId:clinicalDataMap.keySet()) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData.getClinicalResponse()==clinicalResponse) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Collection<String> getLabtrackIdsForERstatus(ERstatusType erStatus) {
		List<String> labtrackIds = new ArrayList<String>();
		ClinicalData clinData;
		for (String labtrackId:clinicalDataMap.keySet()) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData.getErStatus()==erStatus) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Collection<String> getLabtrackIdsForPRstatus(PRstatusType prStatus) {
		List<String> labtrackIds = new ArrayList<String>();
		ClinicalData clinData;
		for (String labtrackId:clinicalDataMap.keySet()) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData.getPrStatus()==prStatus) {
		    labtrackIds.add(labtrackId);
		  }
		}
		return labtrackIds;
	}
	
	public Collection<String> getLabtrackIdsForHER2status(HER2statusType her2Status) {
		List<String> labtrackIds = new ArrayList<String>();
		ClinicalData clinData;
		for (String labtrackId:clinicalDataMap.keySet()) {
		  clinData = clinicalDataMap.get(labtrackId);
		  if (clinData.getHER2status()==her2Status) {
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
		  clinicalDataList.add(clinData);
		}
		return clinicalDataList;
	}

	public Collection<String> getLabtrackIdsForTimepoints(List<TimepointType> timepoints) {
		Set<String> ids = new HashSet<String>();
		for (TimepointType tp:timepoints) {
		  ids.addAll(getLabtrackIdsForTimepoint(tp));
		}
		return ids;
	}
	
	public Collection<String> getLabtrackIds(ISPYclinicalDataQueryDTO clinicalDataQueryDTO) {
		return Collections.EMPTY_LIST;
	}

}
