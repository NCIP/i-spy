package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class ClinicalFileBasedQueryService implements ClinicalDataService {

	private Map<String, PatientData> patientDataMap = new HashMap<String, PatientData>();
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
				
//              UNCOMMENT THE LINES BELOW WHEN THE NEW CLINICAL DATA IS AVAILABLE
//              THE NEW DATA FILE SHOULD BE SET IN THE Application Context Class
//
				String doubleStr = tokens[45];
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T2(Double.valueOf(doubleStr.trim()));
				}
//				
				doubleStr = tokens[46];
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T3(Double.valueOf(doubleStr.trim()));
			    }
//				
				doubleStr = tokens[47];
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T4(Double.valueOf(doubleStr.trim()));
			    }
//				
//				
//
				pd.setMorphPatternBsl(tokens[48]);
				
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
	
	public Set<String> getPatientDIDsForClinicalStage(Set<ClinicalStageType> clinicalStageSet) {
	
		Set<String> patientDIDs = new HashSet<String>();
		
		for (PatientData pd : patientDataMap.values()) {
		  for (ClinicalStageType cs:clinicalStageSet) {
			  if (cs == pd.getClinicalStage()) {
			    patientDIDs.add(pd.getISPY_ID());
			  }
			  else if (cs.name().endsWith("ALL")) {
			     //check the prefix if it matches then add the labtrackId
				 String cdClinicalStage = pd.getClinicalStage().name();
				 String[] cdStgTokens = cdClinicalStage.split("_");
				 String[] stgTokens = cs.name().split("_");
				 if (cdStgTokens[0].equals(stgTokens[0])) {
				   patientDIDs.add(pd.getISPY_ID());
				 }
			  }
		  }	
		}
		
		return patientDIDs;
	}
		
	public Set<String> getPatientDIDsForClinicalResponse(TimepointType timepoint, Set<ClinicalResponseType> clinicalResponseSet) {
        Set<String> patientDIDs = new HashSet<String>();
		
		for (PatientData pd : patientDataMap.values()) {
		  if (clinicalResponseSet.contains(pd.getClinicalResponse(timepoint))) {
		    patientDIDs.add(pd.getISPY_ID());
		  }
		}
		
		return patientDIDs;
	}
	
	
	public Set<String> getPatientDIDsForERstatus(Set<ERstatusType> erStatusSet) {
	  
	  Set<String> patientDIDs = new HashSet<String>();
	  
	  for (PatientData pd : patientDataMap.values()) {
	    if (erStatusSet.contains(pd.getErStatus())) {
	      patientDIDs.add(pd.getISPY_ID());
	    }
	  }
	  
	  return patientDIDs;
	}
		
	public Set<String> getPatientDIDsForPRstatus(Set<PRstatusType> prStatusSet) {
		  
		  Set<String> patientDIDs = new HashSet<String>();
		  
		  //Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
			
		  for (PatientData pd : patientDataMap.values()) {
		    if (prStatusSet.contains(pd.getPrStatus())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		  
		  return patientDIDs;
	}
	
	
	public Set<String> getPatientDIDsForHER2status(Set<HER2statusType> her2StatusSet) {
		  
		  Set<String> patientDIDs = new HashSet<String>();
		  
		  //Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
			
		  for (PatientData pd : patientDataMap.values()) {
		    if (her2StatusSet.contains(pd.getHER2status())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		  
		  return patientDIDs;
	}
	
	

	
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
	
	
	
	/**
	 * This method gets the patient DIDs corresponding to the constraints in the 
	 * clinical data query dto .  Note in the future may want to add capability to 
	 * and and or constraints.  Currently the the constraints are OR ed.
	 */
	public Set<String> getPatientDIDs(ISPYclinicalDataQueryDTO cDTO) {
	  
		Set<TimepointType> timepoints = cDTO.getTimepointValues();
		Set<String> patientDIDs = null;
		Set<String> queryResult = null;
		
		Set<String> restrainingSamples = cDTO.getRestrainingSamples();
				
		//Get IDs for Clinical Stage
		if ((cDTO.getClinicalStageValues() != null)&&(!cDTO.getClinicalStageValues().isEmpty())) {			
		  queryResult = getPatientDIDsForClinicalStage(cDTO.getClinicalStageValues());
			
		  if (patientDIDs == null) {
		     patientDIDs = new HashSet<String>();
		     patientDIDs.addAll(queryResult);
		  }
		  else {
		    patientDIDs.retainAll(queryResult);
		  }
		}
		
		//Get IDs for ER status
		if ((cDTO.getErStatusValues() != null)&&(!cDTO.getErStatusValues().isEmpty())) {
			
			queryResult = getPatientDIDsForERstatus(cDTO.getErStatusValues());
			
		    if (patientDIDs == null) {
		      patientDIDs = new HashSet<String>();
		      patientDIDs.addAll(queryResult);
		    }
		    else {
		     patientDIDs.retainAll(queryResult);
		    }	  
		}
		
		//Get IDs for HER2 status
		if ((cDTO.getHer2StatusValues() != null)&&(!cDTO.getHer2StatusValues().isEmpty())) {
		  
			queryResult = getPatientDIDsForHER2status(cDTO.getHer2StatusValues());
			
		    if (patientDIDs == null) {
		      patientDIDs = new HashSet<String>();
		      patientDIDs.addAll(queryResult);
		    }
		    else {
		     patientDIDs.retainAll(queryResult);
		    }	  			
		}
		
		//Get IDs for PR status
		if ((cDTO.getPrStatusValues() != null)&&(!cDTO.getPrStatusValues().isEmpty())) {
		 		 
		  queryResult = getPatientDIDsForPRstatus(cDTO.getPrStatusValues());
			
	      if (patientDIDs == null) {
	        patientDIDs = new HashSet<String>();
	        patientDIDs.addAll(queryResult);
	      } 
	      else {
	        patientDIDs.retainAll(queryResult);
	      }	  			
		  
		}
				
		
		//Get IDs for Clinical Response
		for (TimepointType tp:timepoints) {					  
			if ((cDTO.getClinicalResponseValues()!= null)&&(!cDTO.getClinicalResponseValues().isEmpty())) {
		      //patientDIDs.addAll(getPatientDIDsForClinicalResponse(tp,cDTO.getClinicalResponseValues()));
		      
			  queryResult = getPatientDIDsForClinicalResponse(tp,cDTO.getClinicalResponseValues());
				
		      if (patientDIDs == null) {
		        patientDIDs = new HashSet<String>();
		        patientDIDs.addAll(queryResult);
		      } 
		      else {
		        patientDIDs.retainAll(queryResult);
		      }	  		
		    }
		}
		
		//Get IDs for NeoAdjuvantChemoRegimen
		if ((cDTO.getAgentValues()!=null) &&(!cDTO.getAgentValues().isEmpty())) {
		  //patientDIDs.addAll(getPatientDIDsForNeoAdjuvantChemoRegimen(cDTO.getAgentValues()));
		  queryResult = getPatientDIDsForNeoAdjuvantChemoRegimen(cDTO.getAgentValues());
			
	      if (patientDIDs == null) {
	        patientDIDs = new HashSet<String>();
	        patientDIDs.addAll(queryResult);
	      } 
	      else {
	        patientDIDs.retainAll(queryResult);
	      }	  	
		}
		
		//Get IDs for % LD change
		if (cDTO.getPercentLDChangeType()!=null) {
		  
		  // USE THESE to get the % ld values.
		  //cDTO.getLdPercentChange();
		  //cDTO.getLdPercentChangeOperator();
		}
		
		if ((restrainingSamples!=null)&&(!restrainingSamples.isEmpty())) {
		  if (patientDIDs != null) {
		    patientDIDs.retainAll(restrainingSamples);
		  }
		}
		
		
		if (patientDIDs != null) {
		  return patientDIDs;
		}
		
		return Collections.emptySet();
		
	}

	public Set<String> getPatientDIDsForNeoAdjuvantChemoRegimen(EnumSet<NeoAdjuvantChemoRegimenType> agentValues) {
		Set<String> patientDIDs = new HashSet<String>();
		NeoAdjuvantChemoRegimenType patientChemo;
		for (PatientData pd : patientDataMap.values()) {
			
		    if (agentValues.contains(pd.getChemoValue())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		return patientDIDs;
	}

	public Set<PatientData> getClinicalData(ISPYclinicalDataQueryDTO dto) {
		Set<String> patientDIDs = getPatientDIDs(dto);
		List<PatientData> patientDataList = getPatientDataForPatientDIDs(new ArrayList(patientDIDs));
		return new HashSet<PatientData>(patientDataList);
	}

}
