package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.CategoricalCorrelationRequest;
import gov.nih.nci.caintegrator.analysis.messaging.CorrelationRequest;
import gov.nih.nci.caintegrator.analysis.messaging.DataPoint;
import gov.nih.nci.caintegrator.analysis.messaging.DataPointVector;
import gov.nih.nci.caintegrator.analysis.messaging.DoubleVector;
import gov.nih.nci.caintegrator.analysis.messaging.IdGroup;
import gov.nih.nci.caintegrator.analysis.messaging.IdList;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterInfo;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
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
import gov.nih.nci.ispy.dto.query.ISPYCategoricalCorrelationQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYCorrelationScatterQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.ISPYDataType;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYCategoricalCorrelationFinding;
import gov.nih.nci.ispy.service.findings.ISPYCorrelationFinding;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class CategoricalCorrelationFindingStrategy extends SessionBasedFindingStrategy {

	private static Logger logger = Logger.getLogger(CategoricalCorrelationFindingStrategy.class);	
	
	private ISPYCategoricalCorrelationQueryDTO queryDTO;
	private CategoricalCorrelationRequest catCorrRequest;
    private AnalysisServerClientManager analysisServerClientManager;
    private ISPYCategoricalCorrelationFinding correlationFinding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
    private ClinicalDataService clinicalService = ClinicalDataServiceFactory.getInstance();
    //private IdList labtrackIds = new IdList("labIds");
    
	public CategoricalCorrelationFindingStrategy(String sessionId, String taskId, ISPYCategoricalCorrelationQueryDTO correlationQueryDTO) {
	  super(sessionId, taskId);
      queryDTO = correlationQueryDTO;        
      catCorrRequest = new CategoricalCorrelationRequest(getSessionId(),getTaskId());
      
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
    correlationFinding = new ISPYCategoricalCorrelationFinding(sessionId, taskId, currentStatus);
    correlationFinding.setQueryDTO(correlationQueryDTO);
    correlationFinding.setContType(correlationQueryDTO.getContinuousType1());
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
        ISPYDataType dataType = null;
        
        if(queryDTO instanceof ISPYCategoricalCorrelationQueryDTO){
            ISPYCategoricalCorrelationQueryDTO categoricalQueryDTO = (ISPYCategoricalCorrelationQueryDTO) queryDTO;
            ReporterInfo reporterInfo1 = null, reporterInfo2 = null;
            IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
                                   
            ContinuousType ct = categoricalQueryDTO.getContinuousType1();
            
            //populate pdids
            List<UserList> patientLists = categoricalQueryDTO.getPatientLists();
            
                                 		
            if (ct == ContinuousType.GENE) {
              //get the reporter info and do a lookup request to get the reporter values
              //so that they can be plotted in a box and wiskers
              String reporterName = categoricalQueryDTO.getReporter1Name();
              String geneSymbol = categoricalQueryDTO.getGeneY();
              
              ArrayPlatformType arrayPlatform = categoricalQueryDTO.getPlatformTypeY();
              String dataFileName = null;
              if (arrayPlatform == ArrayPlatformType.AGILENT){
            	dataFileName = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
            	dataType = ISPYDataType.AGILENT;
              }
              else if (arrayPlatform == ArrayPlatformType.CDNA_ARRAY_PLATFORM) {
            	dataFileName = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
            	dataType = ISPYDataType.CDNA;
              }
              else {
            	logger.error("Unrecognized ArrayPlatform type for ISPY application");
              }
              
              List<ReporterInfo> reporterInfoList = new ArrayList<ReporterInfo>();
                
              //go through the patient lists and get the samples for each patient for the 
              //appropriate array platform
              SampleGroup sg;
              ReporterInfo ri;
              for (UserList patientList : patientLists) {
            	ri = new ReporterInfo(reporterName,geneSymbol, dataFileName);  
                sg = getSampleGroupForPatientList(patientList,dataType);
                ri.setSampleGroup(sg);
                reporterInfoList.add(ri);
              }
                                         
              //get the group vectors from the R binary file
              catCorrRequest.setReporters(reporterInfoList);
            	 
              
            }
            else {
            	
        	  List<DataPointVector> groupVectors = new ArrayList<DataPointVector>();
              DataPointVector dpv;                                 
              for (UserList patientList : patientLists) {
                dpv = getDataPointsForUserList(patientList, ct);
                if (dpv!=null) {
                  groupVectors.add(dpv);
                }          
              }
              catCorrRequest.setDataVectors(groupVectors);
            }
        }
                                                                                            
		return true;
	}
	
	/**
	 * Get the group of samples associated with a patient list for the given data type.
	 * For now assume that we are using all timepoints.
	 * @param patientList
	 * @param dataType
	 * @return
	 */
	private SampleGroup getSampleGroupForPatientList(UserList patientList, ISPYDataType dataType) {
		 IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
		 List<RegistrantInfo> infoList = im.getMapperEntriesForIds(patientList.getList());
		 SampleGroup group = new SampleGroup(patientList.getName());
		 Set<TimepointType> timepoints = new HashSet<TimepointType>();
		 Set<SampleInfo> infoSet;
		 timepoints.add(TimepointType.T1);
		 timepoints.add(TimepointType.T2);
		 timepoints.add(TimepointType.T3);
		 timepoints.add(TimepointType.T4);
		 for (RegistrantInfo info : infoList) {
		   infoSet = info.getSamplesForDataTypeAndTimepoints(dataType, timepoints);
		   for (SampleInfo sampleInfo : infoSet) {
		     group.add(sampleInfo.getLabtrackId());
		   }
		 }
		 return group;		 
	}

	private DataPointVector getDataPointsForUserList(UserList patientList, ContinuousType continuousType) {
		Set<PatientData>  patientDataSet = getPatientDataForPatientIds(patientList.getList());
		DataPointVector dataVector = new DataPointVector(patientList.getName());
		DataPoint dp;
		String ispyId;
		for (PatientData pd : patientDataSet) {
		  ispyId = pd.getISPY_ID();
		  
		  if ((continuousType == ContinuousType.PERCENT_LD_CHANGE_T1_T2)||(continuousType == ContinuousType.PERCENT_LD_CHANGE)) {
		    dp = new DataPoint(ispyId + "_T1_T2");
		    dp.setX(pd.getMriPctChangeT1_T2());
		    dataVector.addDataPoint(dp);
		  }
		  		  
		  if ((continuousType == ContinuousType.PERCENT_LD_CHANGE_T1_T3)||(continuousType == ContinuousType.PERCENT_LD_CHANGE)) {
		    dp = new DataPoint(ispyId + "_T1_T3");
		    dp.setX(pd.getMriPctChangeT1_T3());
		    dataVector.addDataPoint(dp);
		  }
		  
		  if ((continuousType == ContinuousType.PERCENT_LD_CHANGE_T1_T4) || (continuousType == ContinuousType.PERCENT_LD_CHANGE)) {
		    dp = new DataPoint(ispyId + "_T1_T4");
		    dp.setX(pd.getMriPctChangeT1_T4());
		    dataVector.addDataPoint(dp);
		  }		  		  
		}
		return dataVector;
	}

	
	/**
	 * 
	 * @param ids
	 * @return patient data objects 
	 */
	private Set<PatientData> getPatientDataForPatientIds(List<String> ids) {
		ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
        Set<String> pids = new HashSet<String>();
        pids.addAll(ids);
        dto.setRestrainingSamples(pids);
        Set<PatientData> pd = clinicalService.getClinicalData(dto);
		
        if (pd.size() != ids.size()) {
          logger.warn("Number of patient data objects returned=" + pd.size() + " not equal to number of ids=" + ids.size());
        }
        
        return pd;
	}
	
	public boolean analyzeResultSet() throws FindingsAnalysisException {
        try {
            analysisServerClientManager.sendRequest(catCorrRequest);
        } catch (JMSException e) {
        	logger.error("Caught JMSException when appempting to send request to analysis server. Request=" + catCorrRequest);
        	logger.error(e);
        	return false;
        }
        return true;
	}

	public Finding getFinding() {		
		return correlationFinding;
	}

}
