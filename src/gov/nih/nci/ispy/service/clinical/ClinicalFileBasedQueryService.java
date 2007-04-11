package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
		    
		    String patientDataFileName = System.getProperty("gov.nih.nci.ispyportal.data_directory") + System.getProperty("gov.nih.nci.ispyportal.patient_data");
	        logger.info("Clinical data service loading patient data fileName=" + patientDataFileName);
	           
	        int patientRecordsLoaded = instance.setPatientDataMap(patientDataFileName);
	        logger.info("Clinical data service successfully loaded patient data numRecords=" + patientRecordsLoaded);
	           
		  }
		  return instance;
	}
	
	
	/**
	 * This method checks the tokens array length and returns the token at the 
	 * specified index.  If the index is longer than the array of tokens, null is returned
	 * 
	 * @param tokens
	 * @return
	 */
	private String getToken(String[] tokens, int index) {
	  if (index >= tokens.length) return null;
	  
	  return tokens[index];
		
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
								
				PatientData pd = new PatientData(getToken(tokens,0));
				
				pd.setDataExtractDT(getToken(tokens,1));
				pd.setInst_ID(getToken(tokens,2));
				pd.setAgeCategory(getToken(tokens,3));
				pd.setRace_ID(getToken(tokens,4));
				pd.setSSTAT(getToken(tokens,5));
				pd.setSURVDTD(getToken(tokens,6));
				pd.setChemo(getToken(tokens,7));
				pd.setTAM(getToken(tokens,8));
				pd.setHerceptin(getToken(tokens,9));
				pd.setMenoStatus(getToken(tokens,10));
				pd.setSentinelNodeSample(getToken(tokens,11));
				pd.setSentinelNodeResult(getToken(tokens,12));
				pd.setHistologicGradeOS(getToken(tokens,13));
				pd.setER_TS(getToken(tokens,14));
				pd.setPGR_TS(getToken(tokens,15));
				pd.setHER2CommunityPOS(getToken(tokens,16));
				pd.setHER2CommunityMethod(getToken(tokens,17));
				pd.setSurgeryLumpectomy(getToken(tokens,18));
				pd.setSurgeryMastectomy(getToken(tokens,19));
				pd.setINITLUMP_FUPMAST(getToken(tokens,20));
				pd.setSurgery(getToken(tokens,21));
				pd.setDCISOnly(getToken(tokens,22));
				
				String doubleStr = getToken(tokens,23);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setPTumor1SZCM_MICRO(Double.valueOf(doubleStr.trim()));					  
				}
				
				pd.setHistologicGradePS(getToken(tokens,24));
				pd.setNumPosNodes(getToken(tokens,25));
				pd.setNodesExamined(getToken(tokens,26));
				pd.setPathologyStage(getToken(tokens,27));
				pd.setRTTherapy(getToken(tokens,28));
				pd.setRTBreast(getToken(tokens,29));
				pd.setRTBOOST(getToken(tokens,30));
				pd.setRTAXILLA(getToken(tokens,31));
				pd.setRTSNODE(getToken(tokens,32));
				pd.setRTIMAMNODE(getToken(tokens,33));
				pd.setRTChestW(getToken(tokens,34));
				pd.setRTOTHER(getToken(tokens,35));
				pd.setTSizeClinical(getToken(tokens,36));
				pd.setNSizeClinical(getToken(tokens,37));
				pd.setStageTE(getToken(tokens,38));
				pd.setStageNE(getToken(tokens,39));
				pd.setStageME(getToken(tokens,40));
				pd.setClinicalStage(getToken(tokens,41));
				pd.setClinRespT1_T2(getToken(tokens,42));
				pd.setClinRespT1_T3(getToken(tokens,43));
				pd.setClinRespT1_T4(getToken(tokens,44));	
				
				pd.setChemoCat(getToken(tokens,45));
				pd.setDosedenseanthra(getToken(tokens,46));
				pd.setDosedensetaxane(getToken(tokens,47));
				
				pd.setLES_T1(getToken(tokens,48));
				pd.setLES_T2(getToken(tokens,49));
				pd.setLES_T3(getToken(tokens,50));
				pd.setLES_T4(getToken(tokens,51));
				
				
				doubleStr = getToken(tokens,52);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T2(Double.valueOf(doubleStr.trim()));
				}
//				
				doubleStr = getToken(tokens,53);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T3(Double.valueOf(doubleStr.trim()));
			    }
//				
				doubleStr = getToken(tokens,54);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T4(Double.valueOf(doubleStr.trim()));
			    }
//				
//				
//
				pd.setMorphology(getToken(tokens,55));
				
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
	
	private Set<String> getPatientDIDsForClinicalStage(Set<ClinicalStageType> clinicalStageSet) {
	
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
		
	private Set<String> getPatientDIDsForClinicalResponse(TimepointType timepoint, Set<ClinicalResponseType> clinicalResponseSet) {
        Set<String> patientDIDs = new HashSet<String>();
		
		for (PatientData pd : patientDataMap.values()) {
		  if (clinicalResponseSet.contains(pd.getClinicalResponse(timepoint))) {
		    patientDIDs.add(pd.getISPY_ID());
		  }
		}
		
		return patientDIDs;
	}
	
	
	private Set<String> getPatientDIDsForERstatus(Set<ERstatusType> erStatusSet) {
	  
	  Set<String> patientDIDs = new HashSet<String>();
	  
	  for (PatientData pd : patientDataMap.values()) {
	    if (erStatusSet.contains(pd.getErStatus())) {
	      patientDIDs.add(pd.getISPY_ID());
	    }
	  }
	  
	  return patientDIDs;
	}
		
	private Set<String> getPatientDIDsForPRstatus(Set<PRstatusType> prStatusSet) {
		  
		  Set<String> patientDIDs = new HashSet<String>();
		  
		  //Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
			
		  for (PatientData pd : patientDataMap.values()) {
		    if (prStatusSet.contains(pd.getPrStatus())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		  
		  return patientDIDs;
	}
	
	
	private Set<String> getPatientDIDsForHER2status(Set<HER2statusType> her2StatusSet) {
		  
		  Set<String> patientDIDs = new HashSet<String>();
		  
		  //Set<ClinicalData> clinDataSet = timepointMap.get(timepoint);
			
		  for (PatientData pd : patientDataMap.values()) {
		    if (her2StatusSet.contains(pd.getHER2status())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		  
		  return patientDIDs;
	}
	
	

	
	private PatientData getPatientDataForPatientDID(String patientDID) {
		
		PatientData pd = patientDataMap.get(patientDID);
		
		if (pd == null) {
		  logger.warn("No patient data object found for patientDID=" + patientDID);
		}
		
		return pd;
	}
	
	private List<PatientData> getPatientDataForPatientDIDs(List<String> patientDIDs) {
		
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
		  Double ldPctChange = cDTO.getLdPercentChange();
		  Operator operator = cDTO.getLdPercentChangeOperator();
		  PercentLDChangeType changeType = cDTO.getPercentLDChangeType();
		  
		  queryResult = getPatientDIDsForPctLDchange(ldPctChange, changeType, operator);
		  
		  if (patientDIDs == null) {
		     patientDIDs = new HashSet<String>();
		     patientDIDs.addAll(queryResult);
		  } 
		  else {
		     patientDIDs.retainAll(queryResult);
		  }	  	
			
		}
		
		if (cDTO.getPathTumorSize()!=null) {
		  Double size = cDTO.getPathTumorSize();
		  Operator operator = cDTO.getPathTumorSizeOperator();
		  
		  queryResult = getPatientsDIDsForPrimaryPathoMicroscopicTumorSize(size, operator);
		  
		  if (patientDIDs == null) {
		     patientDIDs = new HashSet<String>();
		     patientDIDs.addAll(queryResult);
		  } 
		  else {
		     patientDIDs.retainAll(queryResult);
		  }	  
		  
		}
		
		//Get IDs for AgeCategory
		if (cDTO.getAgeCategoryValues()!=null) {
			  queryResult = getPatientDIDsForAgeCategory(cDTO.getAgeCategoryValues());
				
		      if (patientDIDs == null) {
		        patientDIDs = new HashSet<String>();
		        patientDIDs.addAll(queryResult);
		      } 
		      else {
		        patientDIDs.retainAll(queryResult);
		      }	  	
		}
		
		//Get IDs for Race		
		if (cDTO.getRaceValues()!=null) {
		  queryResult = getPatientDIDsForRace(cDTO.getRaceValues());
			
	      if (patientDIDs == null) {
	        patientDIDs = new HashSet<String>();
	        patientDIDs.addAll(queryResult);
	      } 
	      else {
	        patientDIDs.retainAll(queryResult);
	      }	  	
		}
		
		
		//Get the ids for morphology
		if ((cDTO.getMorphologyValues()!= null)&&(!cDTO.getMorphologyValues().isEmpty())) {
		      
			  queryResult = getPatientDIDsForMorphology(cDTO.getMorphologyValues());
				
		      if (patientDIDs == null) {
		        patientDIDs = new HashSet<String>();
		        patientDIDs.addAll(queryResult);
		      } 
		      else {
		        patientDIDs.retainAll(queryResult);
		      }	  		
		}
		
				
		if ((restrainingSamples!=null)&&(!restrainingSamples.isEmpty())) {
		  if (patientDIDs != null) {
		    patientDIDs.retainAll(restrainingSamples);
		  }  
          else
            patientDIDs = restrainingSamples;
		}
		
		
		if (patientDIDs != null) {
		  return patientDIDs;
		}
		
		return Collections.emptySet();
		
	}
	
	private Set<String> getPatientDIDsForMorphology(Set<MorphologyType> morphologySet) {
		Set<String> patientDIDs = new HashSet<String>();
		
		for (PatientData pd : patientDataMap.values()) {			
			if (morphologySet.contains(pd.getMorphology())) {
			  patientDIDs.add(pd.getISPY_ID());
			}
		}
		return patientDIDs;
	}
	

	/**
	 * 
	 * @param ageCategoryValues
	 * @return
	 */
	private Set<String> getPatientDIDsForAgeCategory(EnumSet<AgeCategoryType> ageCategoryValues) {
		Set<String> patientDIDs = new HashSet<String>();
		AgeCategoryType ageCategory;
		for (PatientData pd : patientDataMap.values()) {
			ageCategory = pd.getAgeCategory();
		    if (ageCategoryValues.contains(pd.getAgeCategory())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		return patientDIDs;
	}

	private Set<String> getPatientsDIDsForPrimaryPathoMicroscopicTumorSize(Double size, Operator operator) {
		Double pdSize = null;
		Set<String> patientDIDs = new HashSet<String>();
		
		for (PatientData pd : patientDataMap.values()) {
		  
		  pdSize = pd.getPTumor1SZCM_Micro() ;
			
		  if (pdSize != null) {
			  if ((operator == Operator.GE) && (pdSize >= size)) {
			    patientDIDs.add(pd.getISPY_ID());
			  }
			  else if ((operator == Operator.LE) && (pdSize <= size)) {
			    patientDIDs.add(pd.getISPY_ID());
			  }
		  }					  			
		}
		
		return patientDIDs;
	}
	
	private Set<String> getPatientDIDsForClinicalMeasurement(Double diameter, Operator operator) {
//		Double pdSize = null;
//		Set<String> patientDIDs = new HashSet<String>();
//		
//		for (PatientData pd : patientDataMap.values()) {
//		  
//		  pdSize = pd.getNSizeClinical();
//			
//		  if (pdSize != null) {
//			  if ((operator == Operator.GE) && (pdSize >= size)) {
//			    patientDIDs.add(pd.getISPY_ID());
//			  }
//			  else if ((operator == Operator.LE) && (pdSize <= size)) {
//			    patientDIDs.add(pd.getISPY_ID());
//			  }
//		  }					  			
//		}
//		
//		return patientDIDs;
		
		return Collections.emptySet();
	}
	
	

	private Set<String> getPatientDIDsForPctLDchange(Double ldPctChange, PercentLDChangeType changeType, Operator operator) {
		Set<String> patientDIDs = new HashSet<String>();
		Double changeValue = null;
		
		double ldPctReduction = -ldPctChange;
		
		for (PatientData pd : patientDataMap.values()) {
			
			if (changeType == PercentLDChangeType.PERCENT_LD_CHANGE_T1_T2) {
			  changeValue = pd.getMriPctChangeT1_T2();
			}
			else if (changeType == PercentLDChangeType.PERCENT_LD_CHANGE_T1_T3) {
			  changeValue = pd.getMriPctChangeT1_T3();
			}
			else if (changeType == PercentLDChangeType.PERCENT_LD_CHANGE_T1_T4) {
			  changeValue = pd.getMriPctChangeT1_T4();
			}
			
			if (changeValue == null) { continue; }
			
			if (operator == Operator.GE) {
			  //get patients with tumor size reduction of changeValue or more
			  if (changeValue <= ldPctReduction) {
				  patientDIDs.add(pd.getISPY_ID());
			  }
			}
			else if (operator == Operator.LE) {
			  //get patients with tumor size reduction of less that changeValue
			  if (changeValue >= ldPctReduction) {
				  patientDIDs.add(pd.getISPY_ID());
			  }				
			}
		  }
		return patientDIDs;
	}

	private Set<String> getPatientDIDsForNeoAdjuvantChemoRegimen(EnumSet<NeoAdjuvantChemoRegimenType> agentValues) {
		Set<String> patientDIDs = new HashSet<String>();
		NeoAdjuvantChemoRegimenType patientChemo;
		for (PatientData pd : patientDataMap.values()) {
			
		    if (agentValues.contains(pd.getChemoValue())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		return patientDIDs;
	}
	
	
	
	private Set<String> getPatientDIDsForRace(EnumSet<RaceType> raceValues) {
		Set<String> patientDIDs = new HashSet<String>();

		for (PatientData pd : patientDataMap.values()) {
			
		    if (raceValues.contains(pd.getRace())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		return patientDIDs;
	}
	

	public Set<PatientData> getClinicalData(ISPYclinicalDataQueryDTO dto) {
		Set<String> patientDIDs = getPatientDIDs(dto);
		List<PatientData> patientDataList = getPatientDataForPatientDIDs(new ArrayList<String>(patientDIDs));
		return new HashSet<PatientData>(patientDataList);
	}

}
