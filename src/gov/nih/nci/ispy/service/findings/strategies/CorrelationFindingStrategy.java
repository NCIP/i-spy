package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.CorrelationRequest;
import gov.nih.nci.caintegrator.analysis.messaging.DoubleVector;
import gov.nih.nci.caintegrator.analysis.messaging.IdList;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterInfo;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.CorrelationFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.CorrelationQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYCorrelationScatterQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class CorrelationFindingStrategy extends SessionBasedFindingStrategy {

	private static Logger logger = Logger.getLogger(CorrelationFindingStrategy.class);	
	
	private CorrelationQueryDTO queryDTO;
	private CorrelationRequest correlationRequest;
    private AnalysisServerClientManager analysisServerClientManager;
    private CorrelationFinding correlationFinding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
    private ClinicalDataService clinicalService = ClinicalDataServiceFactory.getInstance();
    private IdList labtrackIds = new IdList("labIds");
    
	public CorrelationFindingStrategy(String sessionId, String taskId, CorrelationQueryDTO correlationQueryDTO) {
	  super(sessionId, taskId);
      queryDTO = correlationQueryDTO;        
      correlationRequest = new CorrelationRequest(getSessionId(),getTaskId());
      
      try {
          analysisServerClientManager = AnalysisServerClientManager.getInstance();
      } catch (NamingException e) {               
          logger.error(new IllegalStateException("Error getting an instance of  AnalysisServerClientManager" ));
          logger.error(e.getMessage());
          logger.error(e);
      } catch (JMSException e) {                
          logger.error(new IllegalStateException("Error getting an instance of  AnalysisServerClientManager" ));
          logger.error(e.getMessage());
          logger.error(e);
      }
    //}
    /*
     * Set the Finding into the cache! YOU HAVE TO DO THIS!
     */
    
    FindingStatus currentStatus = FindingStatus.Running;
    correlationFinding = new CorrelationFinding(sessionId, taskId, currentStatus, null);
    correlationFinding.setQueryDTO(correlationQueryDTO);
    cacheManager.addToSessionCache(sessionId, taskId, correlationFinding);


	}

	public boolean validate(QueryDTO query) throws ValidationException {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean createQuery() throws FindingsQueryException {
         
        return true;
	}

	public boolean executeQuery() throws FindingsQueryException {
        
        /**check to see if this is a scatter finidng!
         * -KR
         */
        if(queryDTO instanceof ISPYCorrelationScatterQueryDTO){
            ISPYCorrelationScatterQueryDTO scatterQueryDTO = (ISPYCorrelationScatterQueryDTO) queryDTO;
            ReporterInfo reporterInfo;
            IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
            
            //populate pdids
            UserList ul = scatterQueryDTO.getPatientList();
            IdList patientList = new IdList(ul.getName());
            patientList.addAll(ul.getList());
            correlationRequest.setPatientIds(patientList);
            
            //set labTrackIds           
            List<RegistrantInfo> rinfo = im.getMapperEntriesForIds(ul.getList());            
            for(RegistrantInfo info : rinfo){
                List<SampleInfo> sinfo = info.getAssociatedSamples();
                for(SampleInfo s : sinfo){
                    labtrackIds.add(s.getLabtrackId());
                }
            }
            if(!labtrackIds.isEmpty()){
                correlationRequest.setSampleIds(labtrackIds);
            }
            
            //begin setup of the X axis and place in request
            if(scatterQueryDTO.getContinuousType1() == ContinuousType.GENE){
                String dataFilename = "";
                if(scatterQueryDTO.getPlatformTypeX()==ArrayPlatformType.AGILENT){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
                }
                else if(scatterQueryDTO.getPlatformTypeX()==ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
                }
                reporterInfo = new ReporterInfo(scatterQueryDTO.getReporter1Name(),scatterQueryDTO.getGeneX(),dataFilename); 
                correlationRequest.setReporter1(reporterInfo);
            }
            else{ 
                ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
                Set<String> pids = new HashSet<String>(scatterQueryDTO.getPatientList().getList());
                dto.setRestrainingSamples(pids);
                Set<PatientData> pd = clinicalService.getClinicalData(dto);
                List<Double> values = new ArrayList<Double>();
                
                if(scatterQueryDTO.getContinuousType1() == ContinuousType.PERCENT_LD_CHANGE_T1_T2){
                    for(PatientData p : pd){
                        values.add(p.getMriPctChangeT1_T2());
                    }
                }            
                else if(scatterQueryDTO.getContinuousType1() == ContinuousType.PERCENT_LD_CHANGE_T1_T3){                
                    for(PatientData p : pd){
                        values.add(p.getMriPctChangeT1_T3());
                    }
                }
                else if(scatterQueryDTO.getContinuousType1() == ContinuousType.PERCENT_LD_CHANGE_T1_T4){
                    for(PatientData p : pd){
                        values.add(p.getMriPctChangeT1_T4());
                    }
                }
                DoubleVector vector1 = new DoubleVector("correlationVector1", values);
                correlationRequest.setVector1(vector1);
            }
            //end x-axis setup
            
            //begin setup of the Y axis and place in request
            if(scatterQueryDTO.getContinuousType2() == ContinuousType.GENE){
                String dataFilename = "";
                if(scatterQueryDTO.getPlatformTypeY()==ArrayPlatformType.AGILENT){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
                }
                else if(scatterQueryDTO.getPlatformTypeY()==ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
                }
                reporterInfo = new ReporterInfo(scatterQueryDTO.getReporter2Name(),scatterQueryDTO.getGeneY(),dataFilename);                
                correlationRequest.setReporter2(reporterInfo);
            }
            else{ 
                    ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
                    Set<String> pids = new HashSet<String>(scatterQueryDTO.getPatientList().getList());
                    dto.setRestrainingSamples(pids);
                    Set<PatientData> pd = clinicalService.getClinicalData(dto);
                    List<Double> values = new ArrayList<Double>();
                    
                    if(scatterQueryDTO.getContinuousType2() == ContinuousType.PERCENT_LD_CHANGE_T1_T2){
                        for(PatientData p : pd){
                            values.add(p.getMriPctChangeT1_T2());
                        }
                    }            
                    else if(scatterQueryDTO.getContinuousType2() == ContinuousType.PERCENT_LD_CHANGE_T1_T3){                
                        for(PatientData p : pd){
                            values.add(p.getMriPctChangeT1_T3());
                        }
                    }
                    else if(scatterQueryDTO.getContinuousType2() == ContinuousType.PERCENT_LD_CHANGE_T1_T4){
                        for(PatientData p : pd){
                            values.add(p.getMriPctChangeT1_T4());
                        }
                    }
                    DoubleVector vector2 = new DoubleVector("correlationVector2", values);
                    correlationRequest.setVector2(vector2);
                }
            //end Y-axis setup
            
            //set correlationMethod
            correlationRequest.setCorrelationType(scatterQueryDTO.getCorrelationType());
            
        }     
        
		return true;
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
//        try {
//            analysisServerClientManager.sendRequest(correlationRequest);
//        } catch (JMSException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return true;
	}

	public Finding getFinding() {		
		return correlationFinding;
	}

}
