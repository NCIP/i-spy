/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.ispy.dto.query.GpIntegrationQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.ClinicalFinding;
import gov.nih.nci.caintegrator.service.findings.CompoundClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.CopyNumberFinding;
import gov.nih.nci.caintegrator.service.findings.CorrelationFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.FindingsFactory;
import gov.nih.nci.caintegrator.service.findings.GEIntensityFinding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.caintegrator.service.findings.KMFinding;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LossOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.p53.P53FindingCriteria;
import gov.nih.nci.ispy.dto.query.ISPYCategoricalCorrelationQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYCorrelationScatterQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.findings.strategies.CategoricalCorrelationFindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.ClassComparisonFindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.ClinicalFindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.ClinicalFindingStrategyFile;
import gov.nih.nci.ispy.service.findings.strategies.CorrelationFindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.HierarchicalClusteringFindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.IHCLevelOfExpressionFindingStrategyCGOM;
import gov.nih.nci.ispy.service.findings.strategies.IHCLossOfExpressionFindingStrategyCGOM;
import gov.nih.nci.ispy.service.findings.strategies.P53FindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.PrincipalComponentAnalysisFindingStrategy;
import gov.nih.nci.ispy.service.findings.strategies.GPIntegrationFindingStrategy;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */




public class ISPYFindingsFactory implements FindingsFactory {
	private static Logger logger = Logger.getLogger(ISPYFindingsFactory.class);
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createClassComparisonFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public ClassComparisonFinding createClassComparisonFinding(ClassComparisonQueryDTO queryDTO, String sessionID, String taskID) throws FrameworkException  {
		ClassComparisonFinding finding = null;
		try {
			ClassComparisonFindingStrategy strategy = new  ClassComparisonFindingStrategy(sessionID,queryDTO.getQueryName(),queryDTO );
			strategy.createQuery();
			strategy.executeQuery();
			strategy.analyzeResultSet();
			finding = (ClassComparisonFinding)strategy.getFinding();

		} catch (ValidationException e) {
			logger.error(e);
			changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
			throw(e);
		} catch (FindingsQueryException e) {
			logger.error(e);
			changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
			throw(e);
		} catch (FindingsAnalysisException e) {
			logger.error(e);
			changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
			throw(e);
		}
		return finding;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.FindingsFactory#createPCAFinding(gov.nih.nci.caintegrator.dto.query.QueryDTOold)
	 */
	public PrincipalComponentAnalysisFinding createPCAFinding(PrincipalComponentAnalysisQueryDTO queryDTO, String sessionID, String taskID) throws FrameworkException {
	PrincipalComponentAnalysisFinding finding = null;
		try {
			PrincipalComponentAnalysisFindingStrategy strategy = new  PrincipalComponentAnalysisFindingStrategy(sessionID,queryDTO.getQueryName(),queryDTO );
			strategy.createQuery();
			strategy.executeQuery();
			strategy.analyzeResultSet();
			finding = (PrincipalComponentAnalysisFinding)strategy.getFinding();

		} catch (ValidationException e) {
			logger.error(e);
			changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
			throw(e);
		} catch (FindingsQueryException e) {
			logger.error(e);
			changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
			throw(e);
		} catch (FindingsAnalysisException e) {
			logger.error(e);
			changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
			throw(e);
		}
		return finding;
	}


	public KMFinding createKMFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public CopyNumberFinding createCopyNumberFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Create a P53Finding by executing the P53 strategy
	 */
	public void createP53Finding(P53FindingCriteria criteria, String sessionId, String taskId) {
	    try {
            P53FindingStrategy strategy = new P53FindingStrategy(sessionId, taskId, criteria);		
        		try {
        			
        			strategy.createQuery();
        			strategy.executeQuery();
        		    strategy.analyzeResultSet();
        		
        		} catch (FindingsQueryException e) {
        			logger.error("Caught FindingsQueryExcpetion in IHCLevelFindingStrategy");
        			logger.error(e);
        		} catch (FindingsAnalysisException e) {
        			logger.error("Caught FindingsAnalsysisException in IHCLevelFindingStrategy");
        			logger.error(e);
        		}
		
        		
		}
		catch (ValidationException ex) {
		  logger.error("Caught validationException when creating ihcLevel finding strategy: sessionId=" + sessionId + " taskId=" + taskId + " queryName=" + criteria.getQueryName());
		  logger.error(ex);
		}
	
	}
    
	
	/**
	 * Create a IHCLevelOfExpressionFinding by executing the IHCLevelOfExpression strategy
	 */
	public void createIHCLevelOfExpressionFinding(LevelOfExpressionIHCFindingCriteria criteria, String sessionId, String taskId) {
	    try {
            IHCLevelOfExpressionFindingStrategyCGOM strategy = new IHCLevelOfExpressionFindingStrategyCGOM(sessionId, taskId, criteria);		
        		try {
        			
        			strategy.createQuery();
        			strategy.executeQuery();
        		    strategy.analyzeResultSet();
        		
        		} catch (FindingsQueryException e) {
        			logger.error("Caught FindingsQueryExcpetion in IHCLevelFindingStrategy");
        			logger.error(e);
        		} catch (FindingsAnalysisException e) {
        			logger.error("Caught FindingsAnalsysisException in IHCLevelFindingStrategy");
        			logger.error(e);
        		}
		
        		
		}
		catch (ValidationException ex) {
		  logger.error("Caught validationException when creating ihcLevel finding strategy: sessionId=" + sessionId + " taskId=" + taskId + " queryName=" + criteria.getQueryName());
		  logger.error(ex);
		}
	
	}
    
    /**
     * Create a IHCLossOfExpressionFinding by executing the IHCLossOfExpression strategy
     */
    public void createIHCLossOfExpressionFinding(LossOfExpressionIHCFindingCriteria criteria, String sessionId, String taskId) {
        try {
            IHCLossOfExpressionFindingStrategyCGOM strategy = new IHCLossOfExpressionFindingStrategyCGOM(sessionId, taskId, criteria);        
                try {
                    
                    strategy.createQuery();
                    strategy.executeQuery();
                    strategy.analyzeResultSet();
                
                } catch (FindingsQueryException e) {
                    logger.error("Caught FindingsQueryExcpetion in ClinicalFindingStrategy");
                    logger.error(e);
                } catch (FindingsAnalysisException e) {
                    logger.error("Caught FindingsAnalsysisException in ClinicalFindingStrategy");
                    logger.error(e);
                }
        
                
        }
        catch (ValidationException ex) {
          logger.error("Caught validationException when creating clinical finding strategy: sessionId=" + sessionId + " taskId=" + taskId + " queryName=" + criteria.getQueryName());
          logger.error(ex);
        }
    
    }
	
	
	/**
	 * Create a clinical finding by executing the clinical strategy
	 */
	/*public IHCFinding createIHCFinding(IHCqueryDTO query, String sessionId, String taskId) {
		IHCFinding ihcFinding = null;
		
		//Will substitute database version when it is ready
		try {
			
			// once the db version is done, needs to swap with db one
		IHCFindingStrategy strategy = new IHCFindingStrategyFile(sessionId, taskId, query);
		
		try {
			
			strategy.createQuery();
			strategy.executeQuery();
		    strategy.analyzeResultSet();
		
		} catch (FindingsQueryException e) {
			logger.error("Caught FindingsQueryExcpetion in ClinicalFindingStrategy");
			logger.error(e);
		} catch (FindingsAnalysisException e) {
			logger.error("Caught FindingsAnalsysisException in ClinicalFindingStrategy");
			logger.error(e);
		}
		
		ihcFinding = (IHCFinding) strategy.getFinding();
		}
		catch (ValidationException ex) {
		  logger.error("Caught validationException when creating clinical finding strategy: sessionId=" + sessionId + " taskId=" + taskId + " queryName=" + query.getQueryName());
		  logger.error(ex);
		}
		
		
		return ihcFinding;
	}*/
	/**
	 * Create a clinical finding by executing the clinical strategy
	 */
	public ISPYClinicalFinding createClinicalFinding(ISPYclinicalDataQueryDTO query, String sessionId, String taskId) {
		ISPYClinicalFinding clinicalFinding = null;
		
		//Will substitute database version when it is ready
		try {
		ClinicalFindingStrategy strategy = new ClinicalFindingStrategyFile(sessionId, taskId, query);
		
		//ClinicalFindingStrategy strategy = new ClinicalFindingStrategyCGOM(sessionId, taskId, query);
		
		
		try {
			
			strategy.createQuery();
			strategy.executeQuery();
		    strategy.analyzeResultSet();
		
		} catch (FindingsQueryException e) {
			logger.error("Caught FindingsQueryExcpetion in ClinicalFindingStrategy");
			logger.error(e);
		} catch (FindingsAnalysisException e) {
			logger.error("Caught FindingsAnalsysisException in ClinicalFindingStrategy");
			logger.error(e);
		}
		
		clinicalFinding = (ISPYClinicalFinding) strategy.getFinding();
		}
		catch (ValidationException ex) {
		  logger.error("Caught validationException when creating clinical finding strategy: sessionId=" + sessionId + " taskId=" + taskId + " queryName=" + query.getQueryName());
		  logger.error(ex);
		}
		
		
		return clinicalFinding;
	}

	public HCAFinding createHCAFinding(HierarchicalClusteringQueryDTO queryDTO,String sessionID, String taskID) throws FrameworkException {
        HCAFinding finding = null;
        try {
            HierarchicalClusteringFindingStrategy strategy = new  HierarchicalClusteringFindingStrategy(sessionID,queryDTO.getQueryName(),queryDTO );
            strategy.createQuery();
            strategy.executeQuery();
            strategy.analyzeResultSet();
            finding = (HCAFinding)strategy.getFinding();

        } catch (ValidationException e) {
            logger.error(e);
            changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
            throw(e);
        } catch (FindingsQueryException e) {
            logger.error(e);
            changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
            throw(e);
        } catch (FindingsAnalysisException e) {
            logger.error(e);
            changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
            throw(e);
        }
        return finding;
	}

	public void createGPIntegrationSampleIds(GpIntegrationQueryDTO queryDTO,String sessionID) throws FrameworkException {
		//SampleGroup[] sampleGroups = null;
        try {
        	GPIntegrationFindingStrategy strategy = new  GPIntegrationFindingStrategy(sessionID,queryDTO.getQueryName(),queryDTO );
            strategy.createQuery();
            strategy.executeQuery();
            //sampleGroups = strategy.getSampleGroups();

        } catch (ValidationException e) {
            logger.error(e);
            changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
            throw(e);
        } catch (FindingsQueryException e) {
            logger.error(e);
            changeStatusToError(sessionID,queryDTO.getQueryName(),e.getMessage());
            throw(e);
        } 
        //return sampleGroups;
	}
	
	public GEIntensityFinding createGEIntensityFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object createCustomFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Used internally to handle execeptions by changing the status of the Findings object  
	 * 
	 * 
	 * @param sessionID
	 * @param taskID
	 * @param comment
	 * 
	 */
	private void changeStatusToError(String sessionID, String taskID, String comment){
		Finding finding = cacheManager.getSessionFinding(sessionID, taskID);
		if(finding != null){
			FindingStatus newStatus = FindingStatus.Error;
			newStatus.setComment(comment);
			finding.setStatus(newStatus) ;
			
		}
		cacheManager.addToSessionCache(sessionID, taskID, finding);

	}

	public ClinicalFinding createClinicalFinding(QueryDTO query) {
		// TODO Auto-generated method stub
		return null;
	}

    public Finding createCorrelationScatterFinding(ISPYCorrelationScatterQueryDTO correlationScatterQueryDTO, String id, String queryName) throws FrameworkException{
        CorrelationFinding finding = null;
        try {
            CorrelationFindingStrategy strategy = new CorrelationFindingStrategy(id,queryName,correlationScatterQueryDTO);
            strategy.createQuery();
            strategy.executeQuery();
            strategy.analyzeResultSet();
            finding = (CorrelationFinding)strategy.getFinding();

//        } catch (ValidationException e) {
//            logger.error(e);
//            changeStatusToError(id,queryName,e.getMessage());
//            throw(e);
        } catch (FindingsQueryException e) {
            logger.error(e);
            changeStatusToError(id,queryName,e.getMessage());
            throw(e);
        } catch (FindingsAnalysisException e) {
            logger.error(e);
            changeStatusToError(id,queryName,e.getMessage());
            throw(e);
        }
        return finding;
    }
    
    public Finding createCategoricalCorrelationFinding(ISPYCategoricalCorrelationQueryDTO categoricalCorrelationQueryDTO, String id, String queryName) throws FrameworkException{
        ISPYCategoricalCorrelationFinding finding = null;
        try {
            CategoricalCorrelationFindingStrategy strategy = new CategoricalCorrelationFindingStrategy(id,queryName,categoricalCorrelationQueryDTO);
            strategy.createQuery();
            strategy.executeQuery();
            strategy.analyzeResultSet();
            finding = (ISPYCategoricalCorrelationFinding)strategy.getFinding();

//        } catch (ValidationException e) {
//            logger.error(e);
//            changeStatusToError(id,queryName,e.getMessage());
//            throw(e);
        } catch (FindingsQueryException e) {
            logger.error(e);
            changeStatusToError(id,queryName,e.getMessage());
            throw(e);
        } catch (FindingsAnalysisException e) {
            logger.error(e);
            changeStatusToError(id,queryName,e.getMessage());
            throw(e);
        }
        return finding;
    }


	public CompoundClassComparisonFinding createCompoundClassComparisonFinding(List<ClassComparisonQueryDTO> queryList, String sessionID, String taskID, List<String> reporterList) throws FrameworkException {
		// TODO Auto-generated method stub
		return null;
	}
    
}
