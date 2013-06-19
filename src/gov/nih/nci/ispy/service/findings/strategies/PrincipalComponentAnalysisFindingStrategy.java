package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.PrincipalComponentAnalysisRequest;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.ispy.dto.query.ISPYPrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;
import gov.nih.nci.ispy.service.annotation.ISPYDataType;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */




public class PrincipalComponentAnalysisFindingStrategy extends SessionBasedFindingStrategy {
	private static Logger logger = Logger.getLogger(PrincipalComponentAnalysisFindingStrategy.class);	
	@SuppressWarnings("unused")
	private ISPYPrincipalComponentAnalysisQueryDTO myQueryDTO;
	private ReporterGroup reporterGroup; 
	private SampleGroup sampleGroup = new SampleGroup("validatedSamples");

//    private Collection<ClinicalDataQuery> clinicalQueries;
//	private GeneExpressionQuery geneExprQuery;
	private Collection<CloneIdentifierDE> reportersNotFound;
	private Collection<GeneIdentifierDE> genesNotFound;
	private Collection<SampleIDDE> samplesNotFound;
	private PrincipalComponentAnalysisRequest pcaRequest;
	private PrincipalComponentAnalysisFinding pcaFinding;
	private AnalysisServerClientManager analysisServerClientManager;
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	
	
	public PrincipalComponentAnalysisFindingStrategy(String sessionId, String taskId, PrincipalComponentAnalysisQueryDTO queryDTO) throws ValidationException {
		super(sessionId, taskId);
		
		//Check if the passed query is valid
		if(validate(queryDTO)){
			myQueryDTO = (ISPYPrincipalComponentAnalysisQueryDTO) queryDTO;
		
			pcaRequest = new PrincipalComponentAnalysisRequest(getSessionId(),getTaskId());
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
		pcaFinding = new PrincipalComponentAnalysisFinding(sessionId, taskId, currentStatus, null);
		pcaFinding.setQueryDTO(myQueryDTO);
		cacheManager.addToSessionCache(sessionId, taskId, pcaFinding);
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * This method validates that 2 groups were passed for TTest and Wincoin Test
	 */
	public boolean createQuery() throws FindingsQueryException {
		boolean flag = true;

//		if(myQueryDTO.getComparisonGroups() != null){
//            Collection<ClinicalQueryDTO> clinicalQueryDTOs = myQueryDTO.getComparisonGroups();
//            clinicalQueries = new ArrayList<ClinicalDataQuery>();
//            for(ClinicalQueryDTO clinicalQueryDTO: clinicalQueryDTOs) {
//                clinicalQueries.add((ClinicalDataQuery)clinicalQueryDTO);
//            }
//		}
	    
		return flag;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
        
        //ClinicalFileBasedQueryService qs = ClinicalFileBasedQueryService.getInstance();
        
		IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
		
        sampleGroup = new SampleGroup("PCASamples");
       
        //sampleGroup.addAll(qs.getLabtrackIdsForTimepoints(myQueryDTO.getTimepoints()));
        
        ArrayPlatformType arrayPlatform = myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType();
        
        
        Set<SampleInfo> samples = null;
        Set<TimepointType> timepoints = new HashSet<TimepointType>(myQueryDTO.getTimepoints());
        if (arrayPlatform==ArrayPlatformType.AGILENT) {
          samples = idMapper.getSamplesForDataTypeAndTimepoints(ISPYDataType.AGILENT, timepoints);
        }
        else if (arrayPlatform == ArrayPlatformType.CDNA_ARRAY_PLATFORM) {
          samples =  idMapper.getSamplesForDataTypeAndTimepoints(ISPYDataType.CDNA, timepoints);        	
        }
       
        for (SampleInfo si : samples) {
          sampleGroup.add(si.getLabtrackId());
        }
        
        //idMapper.getSamplesForDataTypeAndTimepoints()
        
        //Get Sample Ids from DB
//		if(clinicalQueries != null){
//			CompoundQuery compoundQuery;
//            for (ClinicalDataQuery clinicalDataQuery: clinicalQueries){
//            Resultant resultant;            
//        			try {
//        				compoundQuery = new CompoundQuery(clinicalDataQuery);
//        				compoundQuery.setAssociatedView(ViewFactory
//        		                .newView(ViewType.CLINICAL_VIEW));
//        				InstitutionCriteria institutionCriteria = new InstitutionCriteria();
//        				institutionCriteria.setInstitutions(myQueryDTO.getInstitutionDEs());
//        				compoundQuery.setInstitutionCriteria( institutionCriteria);
//        				resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
//        	  		}
//        	  		catch (Throwable t)	{
//        	  			logger.error("Error Executing the query/n"+ t.getMessage());
//        	  			throw new FindingsQueryException("Error executing clinical query/n"+t.getMessage());
//        	  		}
//        
//        			if(resultant != null) {      
//        		 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
//        		 		Viewable view = resultant.getAssociatedView();
//        		 		if(resultsContainer != null)	{
//        		 			if(view instanceof ClinicalSampleView){
//        		 				try {
//        		 					//1. Get the sample Ids from the return Clinical query
//        							Collection<SampleIDDE> sampleIDDEs = StrategyHelper.extractSampleIDDEs(resultsContainer);
//        							//2. validate samples so that GE data exsists for these samples
//        							Collection<SampleIDDE> validSampleIDDEs = DataValidator.validateSampleIds(sampleIDDEs);
//        							//3. Extracts sampleIds as Strings
//        							Collection<String> sampleIDs = StrategyHelper.extractSamples(validSampleIDDEs);
//        							if(sampleIDs != null && sampleIDs.size()> 0) {
//        								//3.1 add them to SampleGroup
//        								sampleGroup.addAll(sampleIDs);
//            							//3.2 Find out any samples that were not processed  
//        								Set<SampleIDDE> set = new HashSet<SampleIDDE>();
//        								set.addAll(sampleIDDEs); //samples from the original query
//        								//3.3 Remove all samples that are validated								set.removeAll(validSampleIDDEs);
//        								samplesNotFound = set;
//        								setSamplesNotFound(samplesNotFound);
//        							}
//        							else{ //No samples validated
//        								sampleGroup = null;
//        								throw new FindingsQueryException("None of the samples within the selected query are valid for PCA Analysis");
//        							}
//        						} catch (OperationNotSupportedException e) {
//        							logger.error(e.getMessage());
//        				  			throw new FindingsQueryException(e.getMessage());
//        						} catch (Exception e) {
//        							e.printStackTrace();
//        							logger.error(e.getMessage());
//        				  			throw new FindingsQueryException(e.getMessage());
//        						}
//        
//        	 				}	
//        		 		}
//                    }
//			}
//		}
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
//					if(reporters != null  && reporters.size() > 0){
//						this.reporterGroup = new ReporterGroup(myQueryDTO.getQueryName(),reporters.size());
//						reporterGroup.addAll(reporters);
//						
//					}
//					else{ //No reporters are valid
//						reporterGroup = null;
//						throw new FindingsQueryException("None of the selected reporters are valid for PCA Analysis");
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
				
                /**
                 * Temporary hack until we supplant the new query service...do not know
                 * if DEs will be used, so we use them until we do not need to and extract the strings
                 * from them
                 */
				GeneExprAnnotationService geService = GeneExprAnnotationServiceFactory.getInstance();
                //remove items from DEs as Strings
                Collection<GeneIdentifierDE> deList = myQueryDTO.getGeneIdentifierDEs();
                Collection<String> stringList = new ArrayList();
                for(GeneIdentifierDE de: deList){
                    stringList.add(de.getValueObject());
                }
                ArrayPlatformType arrayType= myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType();                
                Collection<String> reporters = geService.getReporterNamesForGeneSymbols(stringList,arrayType);
//              Collection<String> reporters = StrategyHelper.extractGenes(validGeneDEs);
                if(reporters != null  && reporters.size() > 0){
					this.reporterGroup = new ReporterGroup(myQueryDTO.getQueryName(),reporters.size());
					reporterGroup.addAll(reporters);
					
				}
				else{ //No reporters are valid
					reporterGroup = null;
					throw new FindingsQueryException("No reporters founds for the selected genes for PCA Analysis");
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
		pcaRequest = new PrincipalComponentAnalysisRequest(getSessionId(), getTaskId());
		pcaRequest.setVarianceFilterValue(myQueryDTO.getGeneVectorPercentileDE().getDecimalValue());
		
		ArrayPlatformType arrayPlatform = myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType();
		
		pcaRequest.setPlatform(arrayPlatform);
		
		if (arrayPlatform == ArrayPlatformType.AGILENT) {
	      //pcaRequest.setDataFileName("ISPY_DataMatrix_10MARCH06.Rda");
		  pcaRequest.setDataFileName(System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix"));
		}
		else if (arrayPlatform == ArrayPlatformType.CDNA_ARRAY_PLATFORM) {
		  //pcaRequest.setDataFileName("ISPY.cDNA.dataMatrix_5-5-06.Rda");
		  pcaRequest.setDataFileName(System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix"));
		}
				
		if(reporterGroup != null){
			pcaRequest.setReporterGroup(reporterGroup);
		}
		if(sampleGroup != null){
			pcaRequest.setSampleGroup(sampleGroup);
		}
		else{
			throw new FindingsAnalysisException("PC Analysis requires a set of samples");
		}
        try {
			analysisServerClientManager.sendRequest(pcaRequest);
		} catch (JMSException e) {
			logger.error(e.getMessage());
  			throw new FindingsAnalysisException(e.getMessage());
		} catch(Exception e){
			logger.error(e.getMessage());
			throw new FindingsAnalysisException("Error in setting PrincipalComponentAnalysisRequest object");
		}
        return true;
	}

    


	public Finding getFinding() {
		return pcaFinding;
	}



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof PrincipalComponentAnalysisQueryDTO){
			_valid = true;
			ISPYPrincipalComponentAnalysisQueryDTO pcaQueryDTO = (ISPYPrincipalComponentAnalysisQueryDTO)queryDTO;
				
			try {
						ValidationUtility.checkForNull(pcaQueryDTO.getTimepoints());
						ValidationUtility.checkForNull(pcaQueryDTO.getArrayPlatformDE()) ;
						ValidationUtility.checkForNull(pcaQueryDTO.getQueryName());
					
					if( pcaQueryDTO.getGeneVectorPercentileDE() == null && 
							pcaQueryDTO.getGeneIdentifierDEs()== null &&
							pcaQueryDTO.getReporterIdentifierDEs() == null){
						throw new ValidationException("PCA query should be contrained by atleast one of the following: GeneVector, Genes or Reporters");
					}
				} catch (ValidationException ex) {

					logger.error(ex.getMessage());
					throw ex;
				}
		}
		return _valid;	
	}
	private void setSamplesNotFound(Collection<SampleIDDE>  samplesNotFound ){
		pcaFinding = (PrincipalComponentAnalysisFinding) cacheManager.getSessionFinding(getSessionId(), getTaskId());
		if(pcaFinding != null){
			pcaFinding.setSamplesNotFound(samplesNotFound);
			
		}
		cacheManager.addToSessionCache(getSessionId(), getTaskId(), pcaFinding);

	}
	private void setReportsNotFound(Collection<CloneIdentifierDE> reportersNotFound){
		pcaFinding = (PrincipalComponentAnalysisFinding) cacheManager.getSessionFinding(getSessionId(), getTaskId());
		if(pcaFinding != null){
			pcaFinding.setReportersNotFound(reportersNotFound);
			
		}
		cacheManager.addToSessionCache(getSessionId(), getTaskId(), pcaFinding);

	}
	private void setGenesNotFound(Collection<GeneIdentifierDE> genesNotFound ){
		pcaFinding = (PrincipalComponentAnalysisFinding) cacheManager.getSessionFinding(getSessionId(), getTaskId());
		if(pcaFinding != null){
			pcaFinding.setGenesNotFound(genesNotFound);
			
		}
		cacheManager.addToSessionCache(getSessionId(), getTaskId(), pcaFinding);

	}
}
