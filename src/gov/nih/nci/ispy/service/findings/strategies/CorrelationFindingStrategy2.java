package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.CorrelationRequest;
import gov.nih.nci.caintegrator.analysis.messaging.DataPoint;
import gov.nih.nci.caintegrator.analysis.messaging.DoubleVector;
import gov.nih.nci.caintegrator.analysis.messaging.IdGroup;
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
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class CorrelationFindingStrategy2 extends SessionBasedFindingStrategy {

	private static Logger logger = Logger.getLogger(CorrelationFindingStrategy2.class);	
	
	private CorrelationQueryDTO queryDTO;
	private CorrelationRequest correlationRequest;
    private AnalysisServerClientManager analysisServerClientManager;
    private CorrelationFinding correlationFinding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
    private ClinicalDataService clinicalService = ClinicalDataServiceFactory.getInstance();
    private IdList labtrackIds = new IdList("labIds");
    
	public CorrelationFindingStrategy2(String sessionId, String taskId, CorrelationQueryDTO correlationQueryDTO) {
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
    correlationFinding = new CorrelationFinding(sessionId, taskId, currentStatus);
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
            IdList patientList = null;
            
            
            //populate pdids
            UserList ul = scatterQueryDTO.getPatientList();
            
            
            if (ul != null) {
              patientList = new IdList(ul.getName());
              patientList.addAll(ul.getList());  
            }
            else {
              patientList = new IdList("AllPatients");
              patientList.addAll(im.getAllISPYIds());
              logger.info("No user list specified, using all patient ids. NumIds=" + patientList.size());
            }
            
            correlationRequest.setPatientIds(patientList);
            
            //set the restricting sample ids
            List<RegistrantInfo> rinfo = im.getMapperEntriesForIds(patientList);   
            for(RegistrantInfo info : rinfo){
                List<SampleInfo> sinfo = info.getAssociatedSamples();
                for(SampleInfo s : sinfo){
                    labtrackIds.add(s.getLabtrackId());
                }
            }
            if(!labtrackIds.isEmpty()){
                correlationRequest.setSampleIds(labtrackIds);
            }
            
            //Setup the data vectors
            List<DataPoint> dataPoints1 = null;
            List<DataPoint> dataPoints2 = null;   
            
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
            	ContinuousType ct1 = scatterQueryDTO.getContinuousType1();
            	dataPoints1 = getDataPoints(patientList,ct1,true,false);
                correlationRequest.setVector1(dataPoints1);
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
            	ContinuousType ct2 = scatterQueryDTO.getContinuousType2();
            	dataPoints2 = getDataPoints(patientList, ct2, false,true);
            	correlationRequest.setVector2(dataPoints2);
            }
            //end Y-axis setup
            
            //set correlationMethod
            correlationRequest.setCorrelationType(scatterQueryDTO.getCorrelationType());
            
        }     
        
		return true;
	}
	
	/**
	 * Get a data point
	 * @param sampleInfo
	 * @param pd
	 * @param continuousType
	 * @param setXval
	 * @param setYval
	 * @return
	 */
	private DataPoint getDataPoint(SampleInfo sampleInfo, PatientData pd,  ContinuousType continuousType, boolean setXval, boolean setYval) {
	  DataPoint point = null;
	  
	  if ((sampleInfo == null) || (pd == null)) return null;
	  
	  
	     TimepointType tp = sampleInfo.getTimepoint();
	     Double val = null;
	     
	     switch (continuousType) {
		     case PERCENT_LD_CHANGE:
		       switch(tp) {
			       case T1: val = null; break;
			       case T2: val = pd.getMriPctChangeT1_T2(); break;
			       case T3: val = pd.getMriPctChangeT1_T3(); break;
			       case T4: val = pd.getMriPctChangeT1_T4(); break;	    	   
		       }	    	 
		       break;
		     case PERCENT_LD_CHANGE_T1_T2: {
		    	 if (tp == TimepointType.T2) {
		    	   val = pd.getMriPctChangeT1_T2();
		    	 }
		    	 break; 	       
		     }
		     case PERCENT_LD_CHANGE_T1_T3: {
		    	 if (tp == TimepointType.T3) {
		    	   val = pd.getMriPctChangeT1_T3();
		    	 }
		    	 break;
		     }
		     case PERCENT_LD_CHANGE_T1_T4: { 
		    	if (tp == TimepointType.T4) {
		    		val = pd.getMriPctChangeT1_T4();
		    	}
		    	break;
		     }
	     }
	   
	     
	     if (val != null) {
	    	 point = new DataPoint(sampleInfo.getLabtrackId());
	    	 if (setXval) {
	           point.setX(val);
	    	 }
	    	 
	    	 if (setYval) {
	    	   point.setY(val);
	    	 }
	    	 
	     }
	     
	     return point;
	    	   
	    	 
	}
	
	/**
	 * Get the DataPoints to be used in the correlation analysis 
	 * @param patientId
	 * @param variableType
	 * @return a list  of data points to be used.
	 */
	private List<DataPoint> getDataPoints(IdList patientIds, ContinuousType continuousType, boolean setXval, boolean setYval){
		 
		 RegistrantInfo info;
         List<SampleInfo> sampleInfoList;
         PatientData pd;
         DataPoint dp;
         IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
         List<DataPoint> dataPoints = new ArrayList<DataPoint>();
         IdGroup patientGroup = new IdGroup(patientIds.getName());
         
         if ((patientIds!=null)&&(!patientIds.isEmpty())) {
            patientGroup.addAll(patientIds);
         }
         
         if (patientGroup.isEmpty()) {
           //use all patient ids
           patientGroup = im.getAllISPYIds();	 
         }
        
         for (String pid : patientGroup) {
           //go through each id an get the associated samples.	
           info =im.getMapperEntryForId(pid);
           pd = getPatientDataForId(pid);
           if (info != null) {
             sampleInfoList = info.getAssociatedSamples();
             for (SampleInfo si  : sampleInfoList) {
                dp = getDataPoint(si, pd, continuousType, setXval, setYval);
                if (dp != null) {
            	  dataPoints.add(dp);
                }
             }  
           }
         }
         return dataPoints;
	}
	
	/**
	 * Method to get patient data for a given id using the clinical query
	 * DTO
	 * @param patientId
	 * @return PatientData
	 */
	private PatientData getPatientDataForId(String patientId) {
	  
		ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
        Set<String> pid = new HashSet<String>();
        pid.add(patientId);
        dto.setRestrainingSamples(pid);
        Set<PatientData> pd = clinicalService.getClinicalData(dto);
		
        if (pd.size() > 0) {
          Iterator i = pd.iterator();
          PatientData data = (PatientData) i.next();
          
          if (pd.size() > 1) {
            logger.warn("Found more than one patient data object for patientId=" + patientId);
          }
          
          return data;
        }
       
        logger.warn("No patient data found for patientId=" + patientId);
        return null;
	  
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
        try {
            analysisServerClientManager.sendRequest(correlationRequest);
        } catch (JMSException e) {
        	logger.error("Caught JMSException when appempting to send request to analysis server. Request=" + correlationRequest);
        	logger.error(e);
        	return false;
        }
        return true;
	}

	public Finding getFinding() {		
		return correlationFinding;
	}

}
