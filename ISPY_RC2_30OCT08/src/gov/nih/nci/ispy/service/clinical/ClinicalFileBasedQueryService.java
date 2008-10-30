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
import org.apache.commons.validator.GenericValidator;

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
		int lineNum=0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(patientDataFileName));
			
			String line = null;
			String[] tokens = null;
			
			patientDataMap.clear();
			
			while ((line=in.readLine()) != null) {
				if(line.indexOf("1125")!=-1){
				  System.out.println("line+:"+line);
				}
				lineNum++;
				tokens = line.split("\t", -2);
								
				PatientData pd = new PatientData(getToken(tokens,0));
				
				pd.setDataExtractDT(getToken(tokens,1));
				
				pd.setInst_ID(getToken(tokens,2));
				pd.setAgeCategory(getToken(tokens,3));
				
				String ageStr = getToken(tokens,4);
					if ((ageStr!=null)&&(ageStr.trim().length()>0)) {
				  pd.setPatientAge(Double.valueOf(ageStr.trim()));					  
				}
								
				pd.setRace_ID(getToken(tokens,5));
				pd.setSSTAT(getToken(tokens,6));
				pd.setSURVDTD(getToken(tokens,7));
				pd.setChemo(getToken(tokens,8));
				pd.setChemoCat(getToken(tokens,9));
			    pd.setDosedenseanthra(getToken(tokens,10));
				pd.setDosedensetaxane(getToken(tokens,11));
				pd.setTAM(getToken(tokens,12));
				pd.setHerceptin(getToken(tokens,13));
				pd.setMenoStatus(getToken(tokens,14));
				pd.setSentinelNodeSample(getToken(tokens,15));
				pd.setSentinelNodeResult(getToken(tokens,16));
				pd.setHistTypeOfInvasiveTumor(getToken(tokens,17));
				pd.setHistologicGradeOS(getToken(tokens,18));
				pd.setER_TS(getToken(tokens,19));
				pd.setPGR_TS(getToken(tokens,20));
				pd.setHER2CommunityPOS(getToken(tokens,21));
				pd.setHER2CommunityMethod(getToken(tokens,22));
				pd.setSurgeryLumpectomy(getToken(tokens,23));
				pd.setSurgeryMastectomy(getToken(tokens,24));
				pd.setINITLUMP_FUPMAST(getToken(tokens,25));
				pd.setSurgery(getToken(tokens,26));
				pd.setDCISOnly(getToken(tokens,27));
				//  this is original Ptumor1szcm_micro, came from the clinical table, now it needs
				// to be replaced with Ptumor1szcm_micro_1, from the pathology table, and the field should be String, not double anymore
				/*String doubleStr = getToken(tokens,28);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setPTumor1SZCM_MICRO(Double.valueOf(doubleStr.trim()));					  
				}
				*/
				
				pd.setPtumor1szcm_micro_1(getToken(tokens,28));
				
				pd.setHistTypePS(getToken(tokens,29));
				
				pd.setHistologicGradePS(getToken(tokens,30));
				pd.setNumPosNodes(getToken(tokens,31));
				pd.setNodesExamined(getToken(tokens,32));
				pd.setPathologyStage(getToken(tokens,33));
				pd.setReasonNoSurg(getToken(tokens,34));
				pd.setRTTherapy(getToken(tokens,35));
				pd.setRTBreast(getToken(tokens,36));
				pd.setRTBOOST(getToken(tokens,37));
				pd.setRTAXILLA(getToken(tokens,38));
				pd.setRTSNODE(getToken(tokens,39));
				pd.setRTIMAMNODE(getToken(tokens,40));
				pd.setRTChestW(getToken(tokens,41));
				pd.setRTOTHER(getToken(tokens,42));
				pd.setTSizeClinical(getToken(tokens,43));
				pd.setNSizeClinical(getToken(tokens,44));
				pd.setStageTE(getToken(tokens,45));
				pd.setStageNE(getToken(tokens,46));
				pd.setStageME(getToken(tokens,47));
				pd.setClinicalStage(getToken(tokens,48));
				pd.setClinRespT1_T2(getToken(tokens,49));
				pd.setClinRespT1_T3(getToken(tokens,50));
				pd.setClinRespT1_T4(getToken(tokens,51));	
				
				pd.setLES_T1(getToken(tokens,52));
				pd.setLES_T2(getToken(tokens,53));
				pd.setLES_T3(getToken(tokens,54));
				pd.setLES_T4(getToken(tokens,55));
				
				String doubleStr = getToken(tokens,56);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
					  pd.setLdT1(Double.valueOf(doubleStr.trim()));
				}
				doubleStr = getToken(tokens,57);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
					  pd.setLdT2(Double.valueOf(doubleStr.trim()));
				}
				
				doubleStr = getToken(tokens,58);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
					  pd.setLdT3(Double.valueOf(doubleStr.trim()));
				}
				doubleStr = getToken(tokens,59);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
					  pd.setLdT4(Double.valueOf(doubleStr.trim()));
				}
				
				doubleStr = getToken(tokens,60);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T2(Double.valueOf(doubleStr.trim()));
				}
//				
				doubleStr = getToken(tokens,61);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T3(Double.valueOf(doubleStr.trim()));
			    }
//				System.out.println(" line = in the  setPatientDataMap()M method: 017");
				doubleStr = getToken(tokens,62);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT1_T4(Double.valueOf(doubleStr.trim()));
			    }
				
				doubleStr = getToken(tokens,63);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT2_T3(Double.valueOf(doubleStr.trim()));
			    }
				doubleStr = getToken(tokens,64);
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT2_T4(Double.valueOf(doubleStr.trim()));
			    }
				
				doubleStr = getToken(tokens,65);
				
				if ((doubleStr!=null)&&(doubleStr.trim().length()>0)) {
				  pd.setMriPctChangeT3_T4(Double.valueOf(doubleStr.trim()));
			    }
								
				
				pd.setMorphology(getToken(tokens,66));
				
			// make sure it is 68 or 67 when real file gets generated
				// residual cancer burden index, number field
				
				pd.setRcbIndexSize(getToken(tokens,68));
			    
				
				
				// pathology complete response
				pd.setPcr(getToken(tokens,69));
				
               // InSituHisto
				pd.setInSituHisto(getToken(tokens,70));		
				
				
				//invDzHisto
				
				pd.setInvDzHisto(getToken(tokens,71));		
				
				
	            //invDzMultiFoc
				
				pd.setInvDzMultiFoc(getToken(tokens,72));		
			
			    // invDzCellularity				
				
				pd.setInvDzCellularity(getToken(tokens,73));	
				
				
               // surgMargins			
				
				pd.setSurgMargins(getToken(tokens,74));	
				
               // yT			
				
				pd.setYT(getToken(tokens,75));	
				
              // yN		
				
				pd.setYN(getToken(tokens,76));	
				
              // yM		
				
				pd.setYM(getToken(tokens,77));	
				
				//inSituDz

				pd.setInSituDz(getToken(tokens,78));	
				
                //	inSituSpan

				pd.setInSituSpan(getToken(tokens,79));
				
				//%InSitu
				
				pd.setPercentInSitu(getToken(tokens,80));
				
			
	            //inSituGrade
				
				pd.setInSituGrade(getToken(tokens,81));
				
               //invDz
				
				pd.setInvDz(getToken(tokens,82));
				
				//lVI
				
				pd.setLVI(getToken(tokens,83));
				
				
               //metSzLN
				
				pd.setMetSzLN(getToken(tokens,84));
				
				//rcbClass
				
				pd.setRcbClass(getToken(tokens,85));
				
				// rCB_PATHSZ_1
				
				pd.setRCB_PATHSZ_1(getToken(tokens,86));
				
              // rCB_PATHSZ_2
				
				pd.setRCB_PATHSZ_2(getToken(tokens,87));
				
				// ptumor1szcm_micro_2
				
              // ptumor1szcm_micro_1, this is to replace ptumor1szcm_micro, which is token 28
				
				pd.setPtumor1szcm_micro_2(getToken(tokens,88));
			
              // Height			
						
				pd.setHeight(new Long(getToken(tokens,89)));
				
				 // Weight			
				
				pd.setWeight(new Long(getToken(tokens,90)));
			
				
				 // BSA		
				if(getToken(tokens,91)!= null && !getToken(tokens,91).equals("")){
				    pd.setBsa(new Double(getToken(tokens,91)));
				}
				
				 // ERPOS			
				
				pd.setERpos(getToken(tokens,92));
			
				
				 // PGRPOS		
				
				pd.setPgRpos(getToken(tokens,93));
				
                // FINENEEDLE		
				
				pd.setFineNeedle(getToken(tokens,94));		
			
				
                 // CORENEEDLE
		
				
				pd.setCoreNeedle(getToken(tokens,95));
			
			
				
                // INCISIONAL
		
				
				pd.setIncisional(getToken(tokens,96));
				
                // BILATERALCA	
				
				pd.setBilateralCa(getToken(tokens,97));
				
                 // LATERALITY	
				
				pd.setLaterality(getToken(tokens,98));
				
               // RTBRTD
				
				pd.setRtBrTD(getToken(tokens,99));
				
              // RTBOTD				
				pd.setRtBoTD(getToken(tokens,100));
			
              // RTAXTD				
				pd.setRtAxTD(getToken(tokens,101));
			
              // RTSNTD				
				pd.setRtSNTD(getToken(tokens,102));
			
              // RTIMTD				
				pd.setRtIMTD(getToken(tokens,103));
			
              // RTCWTD
				
				pd.setRtCWTD(getToken(tokens,104));
			
              // RTOTTD

				
				pd.setRtOtTD(getToken(tokens,105));
			
              // LOCALPROGRESS		

				
				pd.setLocalProgress(getToken(tokens,106));
			
              // DISTPROGRESS

				
				pd.setDistProgress(getToken(tokens,107));
			
              // T4BASELINE

				
				pd.setT4Baseline(getToken(tokens,108));
			
              // T4EARLY
			
				pd.setT4Early(getToken(tokens,109));
			
              // T4INT
				
				pd.setT4Int(getToken(tokens,110));
				
              // T4PRES

				
				pd.setT4PreS(getToken(tokens,111));
				
              // BASEAXILLARY

				
				pd.setBaseAxillary(getToken(tokens,112));
				
              // EARLYAXILLARY

				
				pd.setEarlyAxillary(getToken(tokens,113));
				
              // INTAXILLARY

				
				pd.setIntAxillary(getToken(tokens,114));
				
              // PRESAXILLARY

				
				pd.setPreSAxillary(getToken(tokens,115));
				
              // BASEINTERNALM

				
				pd.setBaseInternalM(getToken(tokens,116));
				
              // EARLYINTERNALM

				
				pd.setEarlyInternalM(getToken(tokens,117));
				
              // INTINTERNALM

				
				pd.setIntInternalM(getToken(tokens,118));
				
              // PRESINTERNALM

				
				pd.setPreSInternalM(getToken(tokens,119));
				
              // BASESUPRA

				
				pd.setBaseSupra(getToken(tokens,120));
				
              // EARLYSUPRA
				
				pd.setEarlySupra(getToken(tokens,121));
				
              // INTSUPRA
				
				pd.setIntSupra(getToken(tokens,122));
				
              // PRESSUPRA				
				pd.setPreSSupra(getToken(tokens,123));
				
              // BASEINFRA
				
				pd.setBaseInfra(getToken(tokens,124));
				
              // EARLYINFRA
				
				pd.setEarlyInfra(getToken(tokens,125));
				
              // INTINFRA
				
				pd.setIntInfra(getToken(tokens,126));
				
              // PRESINFRA
				
				pd.setPreSInfra(getToken(tokens,127));		
				
				
				//DFS
				
				String longStr = getToken(tokens,128);
				
				if ((longStr!=null)&&(longStr.trim().length()>0)) {
				    pd.setDFS(new Long(longStr));
				}
				
              //DFS_ind
				
				longStr = getToken(tokens,129);
				
				if ((longStr!=null)&&(longStr.trim().length()>0)) {
				    pd.setDFS_ind(new Long(longStr));
				}
				
			
				
				//LocalSiteIpBreast
				
				pd.setLocalSiteIpBreast(getToken(tokens,130));
				
				
				
               //LocalSiteAxillaryNode
				
				pd.setLocalSiteAxillaryNode(getToken(tokens,131));
				
                //LocalSiteSupraNode
				
			  pd.setLocalSiteSupraNode(getToken(tokens,132));
			
				
                 //LocalSiteChestW
			  
			  pd.setLocalSiteChestW(getToken(tokens,133));				
				
				
				// LocalSiteIMammaryN
				
			  pd.setLocalSiteIMammaryN(getToken(tokens,134));					
				
				
	           // LocalSiteInfrNodes
			  pd.setLocalSiteInfrNodes(getToken(tokens,135));			
				
				
				
               // LocalSiteAxilla
			  pd.setLocalSiteAxilla( getToken(tokens,136));
				
				
					
                // DistSite
				
				pd.setDistSite(getToken(tokens,137));
				
				
                // LocalProgTimeD
				
                longStr = getToken(tokens,138);
				
				if ((longStr!=null)&&(longStr.trim().length()>0)) {
				    pd.setLocalProgTimeD(new Long(longStr));
				}	
				
				
               // DistProgTimeD
				
				 longStr = getToken(tokens,139);
					
				if ((longStr!=null)&&(longStr.trim().length()>0)) {
					    pd.setDistProgTimeD(new Long(longStr));
					}	
				
				
				patientDataMap.put(pd.getISPY_ID(), pd);
				numRecordsLoaded++;
			
			}
	
			
		}  catch (IOException e) {
			logger.error("Error reading patientDataFileName=" + patientDataFileName + " lineNum=" + lineNum);
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
	 * This method gets the all patient DIDs without any constraints in the 
	 * clinical data query dto . 
	 */
	
	public Set<String> getAllPatientDIDs() {
		Set<String> patientDIDs = new HashSet<String>();
		for (PatientData pd : patientDataMap.values()) {
		      patientDIDs.add(pd.getISPY_ID());
		  }
		return patientDIDs;		
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
			
		  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);
		}
		
		//Get IDs for ER status
		if ((cDTO.getErStatusValues() != null)&&(!cDTO.getErStatusValues().isEmpty())) {
			
			queryResult = getPatientDIDsForERstatus(cDTO.getErStatusValues());
			
			patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  
		}
		
		//Get IDs for HER2 status
		if ((cDTO.getHer2StatusValues() != null)&&(!cDTO.getHer2StatusValues().isEmpty())) {
		  
			queryResult = getPatientDIDsForHER2status(cDTO.getHer2StatusValues());
			
			patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  			
		}
		
		//Get IDs for PR status
		if ((cDTO.getPrStatusValues() != null)&&(!cDTO.getPrStatusValues().isEmpty())) {
		 		 
		  queryResult = getPatientDIDsForPRstatus(cDTO.getPrStatusValues());
			
		  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  			
		  
		}
				
		
		//Get IDs for Clinical Response
		for (TimepointType tp:timepoints) {					  
			if ((cDTO.getClinicalResponseValues()!= null)&&(!cDTO.getClinicalResponseValues().isEmpty())) {
		      //patientDIDs.addAll(getPatientDIDsForClinicalResponse(tp,cDTO.getClinicalResponseValues()));
		      
			  queryResult = getPatientDIDsForClinicalResponse(tp,cDTO.getClinicalResponseValues());
				
			  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  		
		    }
		}
		
		//Get IDs for NeoAdjuvantChemoRegimen
		if ((cDTO.getAgentValues()!=null) &&(!cDTO.getAgentValues().isEmpty())) {
		  //patientDIDs.addAll(getPatientDIDsForNeoAdjuvantChemoRegimen(cDTO.getAgentValues()));
		  queryResult = getPatientDIDsForNeoAdjuvantChemoRegimen(cDTO.getAgentValues());
			
		  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  	
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
		  
		  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  	
			
		}
		
		if (cDTO.getPathTumorSize()!=null) {
		  Double size = cDTO.getPathTumorSize();
		  Operator operator = cDTO.getPathTumorSizeOperator();
		  
		  queryResult = getPatientsDIDsForPrimaryPathoMicroscopicTumorSize(size, operator);
		  
		  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  
		  
		}
		
		// get ids for residual cancer burden index
		
		if (cDTO.getRcbSize()!=null) {
			  Double size = cDTO.getRcbSize();
			  Operator operator = cDTO.getRcbOperator();
			  
			  queryResult = getPatientsDIDsForRCBIndexSize(size, operator);
			  
			  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  
			  
			}
		
		// get patient ids for patholology complete response
		
		if ((cDTO.getPcrValues()!= null)&&(!cDTO.getPcrValues().isEmpty())) {
		      
			  queryResult = getPatientDIDsForPcr(cDTO.getPcrValues());
				
			  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  		
		}
		
		
		
		//Get IDs for AgeCategory
		if (cDTO.getAgeCategoryValues()!=null) {
			  queryResult = getPatientDIDsForAgeCategory(cDTO.getAgeCategoryValues());
				
			  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  	
		}
		
		//Get IDs for Race		
		if (cDTO.getRaceValues()!=null) {
		  queryResult = getPatientDIDsForRace(cDTO.getRaceValues());
			
		  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  	
		}
		
		
		//Get the ids for morphology
		if ((cDTO.getMorphologyValues()!= null)&&(!cDTO.getMorphologyValues().isEmpty())) {
		      
			  queryResult = getPatientDIDsForMorphology(cDTO.getMorphologyValues());
				
			  patientDIDs = addToPatientDIDs(patientDIDs, queryResult);	  		
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

	private Set<String> addToPatientDIDs(Set<String>patientDIDs, Set<String>queryResult){
		if (patientDIDs == null) {
			patientDIDs = new HashSet<String>();
			patientDIDs.addAll(queryResult);
		} 
		else {
			patientDIDs.retainAll(queryResult);
		}
		return patientDIDs;
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
	
	// get pt dids for rcb index size
	
	private Set<String> getPatientsDIDsForRCBIndexSize(Double size, Operator operator) {
		Double pdSize = null;
		Set<String> patientDIDs = new HashSet<String>();
		
		for (PatientData pd : patientDataMap.values()) {
			if(GenericValidator.isDouble(pd.getRcbIndexSize())) {		  
		      pdSize = Double.valueOf(pd.getRcbIndexSize());
		      System.out.println("pdSize:"+pdSize);
			
			
		  if (pdSize != null) {
			  if ((operator == Operator.GE) && (pdSize >= size)) {
			    patientDIDs.add(pd.getISPY_ID());
			  }
			  else if ((operator == Operator.LE) && (pdSize <= size)) {
			    patientDIDs.add(pd.getISPY_ID());
			  }
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
			else if (changeType == PercentLDChangeType.PERCENT_LD_CHANGE_T2_T3) {
			  changeValue = pd.getMriPctChangeT2_T3();
			}
			else if (changeType == PercentLDChangeType.PERCENT_LD_CHANGE_T2_T4) {
			  changeValue = pd.getMriPctChangeT2_T4();
			}
			else if (changeType == PercentLDChangeType.PERCENT_LD_CHANGE_T3_T4) {
			  changeValue = pd.getMriPctChangeT3_T4();
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
		
		for(int i=0;  i<raceValues.size();i++) 
		{System.out.println("raceValues.toString()"+raceValues.toString());
			}

		for (PatientData pd : patientDataMap.values()) {
			
		    if (raceValues.contains(pd.getRace())) {
		      patientDIDs.add(pd.getISPY_ID());
		    }
		  }
		
		return patientDIDs;
	}
	
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
	
//	 get patient dids for patholgoy complete response
	private Set<String> getPatientDIDsForPcr(EnumSet<PcrType> pcrSet) {
		Set<String> patientDIDs = new HashSet<String>();
		for(int i=0;  i<pcrSet.size();i++) 
		{System.out.println("pcrSet.toString()"+pcrSet.toString());
			}
		
		for (PatientData pd : patientDataMap.values()) {
			System.out.println("pd.getPcr():"+pd.getPcr());
			
			if (pcrSet.contains(pd.getPcrType())) {
			  patientDIDs.add(pd.getISPY_ID());
			}
		}
		return patientDIDs;
	}

	public Set<PatientData> getClinicalData(ISPYclinicalDataQueryDTO dto) {
		Set<String> patientDIDs = null;
		if (dto.isReturnAll())
			patientDIDs = getAllPatientDIDs();
		else
			patientDIDs = getPatientDIDs(dto);
		List<PatientData> patientDataList = getPatientDataForPatientDIDs(new ArrayList<String>(patientDIDs));
		return new HashSet<PatientData>(patientDataList);
	}

}
