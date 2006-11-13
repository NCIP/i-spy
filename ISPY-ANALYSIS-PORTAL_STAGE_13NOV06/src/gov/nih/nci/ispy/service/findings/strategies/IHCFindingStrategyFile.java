package gov.nih.nci.ispy.service.findings.strategies;

import java.util.ArrayList;
import java.util.Set;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.query.IHCqueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.IHCLevelOfExpressionQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
import gov.nih.nci.ispy.service.clinical.PatientData;

import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.service.findings.ISPYIHCLevelOfExpressionFinding;
import gov.nih.nci.ispy.service.ihc.IHCData;
import gov.nih.nci.ispy.service.ihc.IHCFileBasedQueryService;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

public class IHCFindingStrategyFile extends IHCFindingStrategy {

  // private IHCFinding ihcFinding = null;
   private ISPYIHCLevelOfExpressionFinding ihcLevelOfExpFinding = null;
   private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
		
	
	public IHCFindingStrategyFile(String sessionId, String taskId, IHCqueryDTO queryDTO)  throws ValidationException {
		super(sessionId, taskId, queryDTO );
		ihcLevelOfExpFinding = new ISPYIHCLevelOfExpressionFinding(sessionId, taskId, queryDTO);
	}

	

	public boolean validate(QueryDTO query) throws ValidationException {
		
		return true;
	}

	public boolean createQuery() throws FindingsQueryException {
		IHCFileBasedQueryService iqs = IHCFileBasedQueryService.getInstance();
		
		
		Set<IHCData> ihcData = iqs.getIHCData(getQueryDTO());
		
	  
	    
//	    
//	    //put the result into the finding 
		ISPYIHCLevelOfExpressionFinding ihcFinding = new ISPYIHCLevelOfExpressionFinding(this.getSessionId(), this.getTaskId(), this.getQueryDTO());
	    ihcFinding.setIHCData(new ArrayList<IHCData>(ihcData));
	    
	    return true;
	}

	public boolean executeQuery() throws FindingsQueryException {
		return true;
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {		
		cacheManager.addToSessionCache(this.getSessionId(), this.getTaskId(), ihcLevelOfExpFinding);
		return true;
	}

	public Finding getFinding() {
	  return ihcLevelOfExpFinding;
	}
	

}
