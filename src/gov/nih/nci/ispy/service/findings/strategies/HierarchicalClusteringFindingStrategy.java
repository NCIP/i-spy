package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.HierarchicalClusteringRequest;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.ispy.dto.query.ISPYHierarchicalClusteringQueryDTO;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;
import gov.nih.nci.ispy.service.annotation.ISPYDataType;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */




public class HierarchicalClusteringFindingStrategy extends SessionBasedFindingStrategy {
	private static Logger logger = Logger.getLogger(HierarchicalClusteringFindingStrategy.class);	
	@SuppressWarnings("unused")
	private ISPYHierarchicalClusteringQueryDTO myQueryDTO;
	private ReporterGroup reporterGroup; 
	private SampleGroup sampleGroup;
	
	private Collection<CloneIdentifierDE> reportersNotFound;
	private Collection<GeneIdentifierDE> genesNotFound;
	private Collection<SampleIDDE> samplesNotFound;
//	private ClinicalDataQuery clinicalDataQuery;
//	private GeneExpressionQuery geneExprQuery;
	private HierarchicalClusteringRequest hcRequest;
	private HCAFinding hcFinding;
	private AnalysisServerClientManager analysisServerClientManager;
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	private List<String> labtrackIds = Collections.EMPTY_LIST;
	
	public HierarchicalClusteringFindingStrategy(String sessionId, String taskId, HierarchicalClusteringQueryDTO queryDTO) throws ValidationException {
		
		super(sessionId, taskId);
		
		//Check if the passed query is valid
		if(validate(queryDTO)){
			myQueryDTO = (ISPYHierarchicalClusteringQueryDTO) queryDTO;
			
			hcRequest = new HierarchicalClusteringRequest(getSessionId(),getTaskId());
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
		}
		/*
		 * Set the Finding into the cache! YOU HAVE TO DO THIS!
		 */
		
		FindingStatus currentStatus = FindingStatus.Running;
		hcFinding = new HCAFinding(sessionId, taskId, currentStatus, null);
		hcFinding.setQueryDTO(myQueryDTO);
		cacheManager.addToSessionCache(sessionId, taskId, hcFinding);
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * 
	 */
	public boolean createQuery() throws FindingsQueryException {
		boolean flag = true;

//		//create a ClinicalDataQuery to contrain by Insitition group
//		clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
//		InstitutionCriteria institutionCriteria = new InstitutionCriteria();
//		institutionCriteria.setInstitutions(myQueryDTO.getInstitutionDEs());
//		clinicalDataQuery.setInstitutionCriteria( institutionCriteria);
//		

    
		return flag;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		
		
		
		//ClinicalFileBasedQueryService qs = ClinicalFileBasedQueryService.getInstance();
		
		IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
		
		sampleGroup = new SampleGroup("HCSamples");
		//sampleGroup.addAll(qs.getLabtrackIdsForTimepoints(myQueryDTO.getTimepoints()));
		
		ArrayPlatformType arrayPlatform = myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType();
		Set<TimepointType> timepoints = new HashSet<TimepointType>(myQueryDTO.getTimepoints());
		Set<SampleInfo> samples = null;
		
		if (arrayPlatform == ArrayPlatformType.AGILENT) {
		  samples = idMapper.getSamplesForDataTypeAndTimepoints(ISPYDataType.AGILENT, timepoints);
		}
		else if (arrayPlatform == ArrayPlatformType.CDNA_ARRAY_PLATFORM) {
		  samples = idMapper.getSamplesForDataTypeAndTimepoints(ISPYDataType.CDNA, timepoints);
		}
		
		if (samples != null) {
		  for (SampleInfo si : samples) {
		    sampleGroup.add(si.getLabtrackId());
		  }
		}
		
		//Get the samples to cluster
	
		
		//Get Sample Ids from DB
//		if(clinicalDataQuery != null){
//			CompoundQuery compoundQuery;
//			Resultant resultant;
//			try {
//				compoundQuery = new CompoundQuery(clinicalDataQuery);
//				compoundQuery.setAssociatedView(ViewFactory
//	                .newView(ViewType.CLINICAL_VIEW));
//				resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
//	  		}
//	  		catch (Throwable t)	{
//	  			logger.error("Error Executing the query/n"+ t.getMessage());
//	  			throw new FindingsQueryException("Error executing clinical query/n"+t.getMessage());
//	  		}
//
//			if(resultant != null) {      
//		 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
//		 		Viewable view = resultant.getAssociatedView();
//		 		if(resultsContainer != null)	{
//		 			if(view instanceof ClinicalSampleView){
//		 				try {
//		 					//1. Get the sample Ids from the return Clinical query
//							Collection<SampleIDDE> sampleIDDEs = StrategyHelper.extractSampleIDDEs(resultsContainer);
//							//2. validate samples so that GE data exsists for these samples
//							Collection<SampleIDDE> validSampleIDDEs = DataValidator.validateSampleIds(sampleIDDEs);
//							//3. Extracts sampleIds as Strings
//							Collection<String> sampleIDs = StrategyHelper.extractSamples(validSampleIDDEs);
//							if(sampleIDs != null){
//								//3.1 add them to SampleGroup
//								sampleGroup = new SampleGroup(clinicalDataQuery.getQueryName(),validSampleIDDEs.size());
//								sampleGroup.addAll(sampleIDs);
////								//3.2 Find out any samples that were not processed  
//								Set<SampleIDDE> set = new HashSet<SampleIDDE>();
//								set.addAll(sampleIDDEs); //samples from the original query
//								//3.3 Remove all samples that are validated								set.removeAll(validSampleIDDEs);
//								samplesNotFound = set;
//								
//							}
//						} catch (OperationNotSupportedException e) {
//							logger.error(e.getMessage());
//				  			throw new FindingsQueryException(e.getMessage());
//						} catch (Exception e) {
//							e.printStackTrace();
//							logger.error(e.getMessage());
//				  			throw new FindingsQueryException(e.getMessage());
//						}
//
//	 				}	
//		 		}
//			}
//		}
//
//		//Get Reporters from DB
//		if(myQueryDTO.getGeneIdentifierDEs() != null ||
//				myQueryDTO.getReporterIdentifierDEs() != null){
//			if(	myQueryDTO.getReporterIdentifierDEs() != null){
//					Collection<CloneIdentifierDE> validCloneDEs;
//					try {
//						validCloneDEs = DataValidator.validateReporters(myQueryDTO.getReporterIdentifierDEs());
//
//					//Create a set of submitted Reporters 
//					Set<CloneIdentifierDE> set = new HashSet<CloneIdentifierDE>();
//					set.addAll(myQueryDTO.getReporterIdentifierDEs());
//					// Find out if any reports were not validated
//					set.removeAll(validCloneDEs);
//					reportersNotFound = set;
//					
//					Collection<String> reporters = StrategyHelper.extractReporters(validCloneDEs);
//					if(reporters != null){
//						this.reporterGroup = new ReporterGroup(myQueryDTO.getQueryName(),reporters.size());
//						reporterGroup.addAll(reporters);
//						
//					}
//					} catch (Exception e) {
//						e.printStackTrace();
//						logger.error(e.getMessage());
//			  			throw new FindingsQueryException(e.getMessage());
//					}
//				
//				}
     		if(	myQueryDTO.getGeneIdentifierDEs() != null){
//				Collection<GeneIdentifierDE> validGeneDEs;
//				try {
//					validGeneDEs = DataValidator.validateGenes(myQueryDTO.getGeneIdentifierDEs());
//
//				//Create a set of submitted Reporters 
//				Set<GeneIdentifierDE> set = new HashSet<GeneIdentifierDE>();
//				set.addAll(myQueryDTO.getGeneIdentifierDEs());
//				// Find out if any reports were not validated
//				set.removeAll(validGeneDEs);
//				genesNotFound = set;
                
               
                GeneExprAnnotationService geService = GeneExprAnnotationServiceFactory.getInstance();
                //remove items from DEs as Strings
                Collection<GeneIdentifierDE> deList = myQueryDTO.getGeneIdentifierDEs();
                Collection<String> stringList = new ArrayList();
                for(GeneIdentifierDE de: deList){
                    stringList.add(de.getValueObject());
                }
                ArrayPlatformType arrayType= myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType();                
                Collection<String> reporters = geService.getReporterNamesForGeneSymbols(stringList,arrayType);
//				
//				Collection<String> reporters = StrategyHelper.extractGenes(validGeneDEs);
				if(reporters != null){
					this.reporterGroup = new ReporterGroup(myQueryDTO.getQueryName(),reporters.size());
					reporterGroup.addAll(reporters);
					
				}
//				} catch (Exception e) {
//					e.printStackTrace();
//					logger.error(e.getMessage());
//		  			throw new FindingsQueryException(e.getMessage());
//				}
			
			}
			
			return true;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#analyzeResultSet()
	 */
	public boolean analyzeResultSet() throws FindingsAnalysisException {
		hcRequest = new HierarchicalClusteringRequest(getSessionId(), getTaskId());
		hcRequest.setVarianceFilterValue(myQueryDTO.getGeneVectorPercentileDE().getDecimalValue());
		
		ArrayPlatformType arrayPlatform = myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType();
		
		hcRequest.setArrayPlatform(arrayPlatform);
		
		if (arrayPlatform == ArrayPlatformType.AGILENT) {
		  //hcRequest.setDataFileName("ISPY_DataMatrix_10MARCH06.Rda");
		  hcRequest.setDataFileName(System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix"));
		}
		else if (arrayPlatform == ArrayPlatformType.CDNA_ARRAY_PLATFORM) {
		  //hcRequest.setDataFileName("ISPY.cDNA.dataMatrix_5-5-06.Rda");
		  hcRequest.setDataFileName(System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix"));
		}
		
		hcRequest.setClusterBy(myQueryDTO.getClusterTypeDE().getValueObject());
		if(reporterGroup != null){
			hcRequest.setReporterGroup(reporterGroup);
		}
		if(sampleGroup != null){
			hcRequest.setSampleGroup(sampleGroup);
		}
        try {
			analysisServerClientManager.sendRequest(hcRequest);
		} catch (JMSException e) {
			logger.error(e.getMessage());
  			throw new FindingsAnalysisException(e.getMessage());
		} catch(Exception e){
			logger.error(e.getMessage());
			throw new FindingsAnalysisException("Error in setting HierarchicalClusteringRequest object");
		}
        return true;
	}

    


	public Finding getFinding() {
		return hcFinding;
	}



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof HierarchicalClusteringQueryDTO){
			_valid = true;
            ISPYHierarchicalClusteringQueryDTO hcQueryDTO = (ISPYHierarchicalClusteringQueryDTO)queryDTO;
				
			try {
						ValidationUtility.checkForNull(hcQueryDTO.getTimepoints());
						ValidationUtility.checkForNull(hcQueryDTO.getQueryName());
					
					if( hcQueryDTO.getGeneVectorPercentileDE() == null){
						throw new ValidationException("HC query should be contrained by atleast one of the following: GeneVector, Genes or Reporters");
					}
				} catch (ValidationException ex) {

					logger.error(ex.getMessage());
					throw ex;
				}
		}
		return _valid;	
	}
}
