package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.CorrelationRequest;
import gov.nih.nci.caintegrator.analysis.messaging.DataPoint;
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

public class CorrelationFindingStrategy3 extends SessionBasedFindingStrategy {

	private static Logger logger = Logger.getLogger(CorrelationFindingStrategy3.class);	
	
	private CorrelationQueryDTO queryDTO;
	private CorrelationRequest correlationRequest;
    private AnalysisServerClientManager analysisServerClientManager;
    private ISPYCorrelationFinding correlationFinding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
    private ClinicalDataService clinicalService = ClinicalDataServiceFactory.getInstance();
    //private IdList labtrackIds = new IdList("labIds");
    
	public CorrelationFindingStrategy3(String sessionId, String taskId, CorrelationQueryDTO correlationQueryDTO) {
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
    correlationFinding = new ISPYCorrelationFinding(sessionId, taskId, currentStatus);
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
            ReporterInfo reporterInfo1 = null, reporterInfo2 = null;
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
            
            //Setup the data vectors
            List<DataPoint> dataPoints1 = null;
            List<DataPoint> dataPoints2 = null;   
            
            ContinuousType ct1 = scatterQueryDTO.getContinuousType1();
            ContinuousType ct2 = scatterQueryDTO.getContinuousType2();
            
            //determine the timepoints to use
            Set<TimepointType> timepoints = getTimepointsToUse(ct1,ct2);
            ISPYDataType ispyDataType1 = null;
            ISPYDataType ispyDataType2 = null;
            
            boolean usePatientIdsForPoints = ((ct1!=ContinuousType.GENE)&&(ct2!=ContinuousType.GENE));
            correlationFinding.setPatientBased(usePatientIdsForPoints);
            correlationFinding.setSampleBased(!usePatientIdsForPoints);
            correlationFinding.setContinuousType1(ct1);
            correlationFinding.setContinuousType2(ct2);
            
            
            //begin setup of the X axis and place in request
            if(scatterQueryDTO.getContinuousType1() == ContinuousType.GENE){
                String dataFilename = "";
                if(scatterQueryDTO.getPlatformTypeX()==ArrayPlatformType.AGILENT){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
                    ispyDataType1 = ISPYDataType.AGILENT;
                }
                else if(scatterQueryDTO.getPlatformTypeX()==ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
                    ispyDataType1 = ISPYDataType.CDNA;
                }
                reporterInfo1 = new ReporterInfo(scatterQueryDTO.getReporter1Name(),scatterQueryDTO.getGeneX(),dataFilename);
                
                //set the samples to use 
                SampleGroup sg1 = getSamplesToUse(patientList, ispyDataType1, timepoints);
                reporterInfo1.setSampleGroup(sg1);
                
                correlationRequest.setReporter1(reporterInfo1);
            }
            
            if(scatterQueryDTO.getContinuousType2() == ContinuousType.GENE){
                String dataFilename = "";
                if(scatterQueryDTO.getPlatformTypeY()==ArrayPlatformType.AGILENT){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
                    ispyDataType2 = ISPYDataType.AGILENT;
                }
                else if(scatterQueryDTO.getPlatformTypeY()==ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                    dataFilename = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
                    ispyDataType2 = ISPYDataType.CDNA;
                }
                reporterInfo2 = new ReporterInfo(scatterQueryDTO.getReporter2Name(),scatterQueryDTO.getGeneY(),dataFilename);
                
                //set the samples to use
                SampleGroup sg2 = getSamplesToUse(patientList, ispyDataType2, timepoints);
                reporterInfo2.setSampleGroup(sg2);
                correlationRequest.setReporter2(reporterInfo2);
            }
            
            
	        if (scatterQueryDTO.getContinuousType1() != ContinuousType.GENE) {
	        	
	          if (reporterInfo2 == null) {
	            logger.error("reporterInfo2 is null and ct1!=GENE");
	          }
	          dataPoints1 = getDataPoints(reporterInfo2.getSampleGroup(), ct1, true, false);	
	          correlationRequest.setVector1(dataPoints1);
	          correlationRequest.setVector1Name(ct1.toString());
	        }
            
	        if (scatterQueryDTO.getContinuousType2() != ContinuousType.GENE) { 
	          if (reporterInfo1 == null) {
	  	            logger.error("reporterInfo1 is null and ct2!=GENE");
	  	      }
	          dataPoints2 = getDataPoints(reporterInfo1.getSampleGroup(), ct2, false, true);	
		      correlationRequest.setVector2(dataPoints2);
		      correlationRequest.setVector2Name(ct2.toString());	       	        	
	        }
                                                                                                                                           
            //set correlationMethod
            correlationRequest.setCorrelationType(scatterQueryDTO.getCorrelationType());
            
        }     
        
		return true;
	}
	
	private List<DataPoint> getDataPoints(SampleGroup sampleGroup, ContinuousType ct, boolean setXval, boolean setYval) {
		List<DataPoint> points = new ArrayList<DataPoint>();
		DataPoint p ;
		SampleInfo info;
		PatientData pd; 
		IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
		for (String sampleId : sampleGroup) {
		  info = im.getSampleInfoForLabtrackId(sampleId);
		  pd = getPatientDataForId(info.getISPYId());
		  p = getDataPoint(info,pd, ct, setXval, setYval);
		  points.add(p);
		}
		return points;
	}

	/**
	 * 
	 * @param patientList
	 * @param ispyDataType
	 * @param timepoints
	 * @return SampleGroup
	 */
	private SampleGroup getSamplesToUse(IdList patientList, ISPYDataType ispyDataType, Set<TimepointType> timepoints) {
	   SampleGroup sg = new SampleGroup();
	   IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
	   List<RegistrantInfo> rinfo = im.getMapperEntriesForIds(patientList);   
       for(RegistrantInfo info : rinfo){
         Set<SampleInfo> sinfo = info.getSamplesForDataTypeAndTimepoints(ispyDataType, timepoints);
         for(SampleInfo s : sinfo){
             sg.add(s.getLabtrackId());
         }
       }
       return sg;
	}

	private Double getValueForTimepoint(TimepointType timepoint, PatientData pd){
		
		if ((timepoint == null)||(pd == null)) return null;
		
		switch(timepoint) {
	       case T2: return pd.getMriPctChangeT1_T2();
	       case T3: return pd.getMriPctChangeT1_T3();
	       case T4: return pd.getMriPctChangeT1_T4();	 
		}
		return null;
	}
	
	private TimepointType getEndpoint(ContinuousType continuousType) {
		switch(continuousType){
		case PERCENT_LD_CHANGE_T1_T2: return TimepointType.T2;
		case PERCENT_LD_CHANGE_T1_T3: return TimepointType.T3;
		case PERCENT_LD_CHANGE_T1_T4: return TimepointType.T4;
		}
		return null;
	}
	
	/**
	 * 
	 * @param pd
	 * @param continuousType
	 * @param setXval
	 * @param setYval
	 * @return
	 */
	private DataPoint getDataPoint(PatientData pd, ContinuousType continuousType, boolean setXval, boolean setYval) {
		
		if (pd == null) return null;
		
		DataPoint dp = new DataPoint(pd.getISPY_ID());
		TimepointType tp = getEndpoint(continuousType);
		Double val = getValueForTimepoint(tp,pd);
		
		if (val == null) return null;
		
		if (setXval){
		 dp.setX(val);
		}
		if (setYval) {
		 dp.setY(val);
		}
		return dp;
		
	}
	
	/**
	 * Get a data point
	 * @param sampleInfo
	 * @param pd
	 * @param continuousType
	 * @param setXval
	 * @param setYval
	 * @param usePatientIdsForPoints 
	 * @return
	 */
	private DataPoint getDataPoint(SampleInfo sampleInfo, PatientData pd,  ContinuousType continuousType, boolean setXval, boolean setYval) {
	  DataPoint point = null;
	  
	  if ((sampleInfo == null) || (pd == null)) return null;
	  
	  
	  TimepointType sampleTP = sampleInfo.getTimepoint();
	  Double val = null;
	  
	     
	  if (continuousType == ContinuousType.PERCENT_LD_CHANGE) {
		 val = getValueForTimepoint(sampleTP,pd);
	  }
	  else {
		 TimepointType pctChangeEndTP = getEndpoint(continuousType);
	     val = getValueForTimepoint(pctChangeEndTP, pd);
	  }
	     
	  if (val != null) {
    	point = new DataPoint(sampleInfo.getLabtrackId());
    	 
    	if (setXval) { point.setX(val);}
    	if (setYval) { point.setY(val);}
	    	 
	  }
	     
	  return point;
	    	   
	    	 
	}
	
	/**
	 * Get the DataPoints to be used in the correlation analysis 
	 * @param createPointsBasedOnPatientIds 
	 * @param patientId
	 * @param variableType
	 * @return a list  of data points to be used.
	 */
	private List<DataPoint> getDataPoints(IdList patientIds, ContinuousType continuousType, boolean setXval, boolean setYval, boolean createPointsBasedOnPatientIds){
		 
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
           if (createPointsBasedOnPatientIds){
        	   dp = getDataPoint(pd, continuousType, setXval, setYval);  
        	   if (dp != null){
        	     dataPoints.add(dp);
        	   }
           }
           else  {
        	   //create points based sample ids
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
         }
         return dataPoints;
	}
	
	private Set<TimepointType> getTimepointsToUse(ContinuousType ct1, ContinuousType ct2) {
	
		Set<TimepointType> timepointSet = new HashSet<TimepointType>();
		
		if ((ct1 == ContinuousType.PERCENT_LD_CHANGE)||(ct2 == ContinuousType.PERCENT_LD_CHANGE)) {
		  timepointSet.add(TimepointType.T1);
		  timepointSet.add(TimepointType.T2);
		  timepointSet.add(TimepointType.T3);
		  timepointSet.add(TimepointType.T4);
		}
		else if ((ct1 == ContinuousType.PERCENT_LD_CHANGE_T1_T2)||(ct2 == ContinuousType.PERCENT_LD_CHANGE_T1_T2)) {
		  timepointSet.add(TimepointType.T2);	
		}
		else if ((ct1 == ContinuousType.PERCENT_LD_CHANGE_T1_T3)||(ct2 == ContinuousType.PERCENT_LD_CHANGE_T1_T3)) {
		  timepointSet.add(TimepointType.T3);
		}
		else if ((ct1 == ContinuousType.PERCENT_LD_CHANGE_T1_T4)||(ct2 == ContinuousType.PERCENT_LD_CHANGE_T1_T4)) {
		  timepointSet.add(TimepointType.T4);
		}
		
		return timepointSet;
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
