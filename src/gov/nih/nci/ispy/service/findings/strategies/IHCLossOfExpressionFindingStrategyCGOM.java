package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LossOfExpressionIHCFindingCriteria;
import gov.nih.nci.ispy.service.findings.ISPYIHCLossOfExpressionFinding;
import gov.nih.nci.ispy.service.ihc.LossOfExpressionIHCService;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.Collection;

public class IHCLossOfExpressionFindingStrategyCGOM extends SessionBasedFindingStrategy {
	
	
    private LossOfExpressionIHCFindingCriteria criteria;
    private ISPYIHCLossOfExpressionFinding finding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
    

	public IHCLossOfExpressionFindingStrategyCGOM(String sessionId, String taskId, LossOfExpressionIHCFindingCriteria criteria)  throws ValidationException {
		super(sessionId, taskId);
		this.criteria = criteria;
	}
	

	public boolean validate(QueryDTO query) throws ValidationException {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean createQuery() throws FindingsQueryException {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean executeQuery() throws FindingsQueryException {		
        LossOfExpressionIHCService loeService = LossOfExpressionIHCService.getInstance();
        finding = new ISPYIHCLossOfExpressionFinding(this.getSessionId(), this.getTaskId());
        finding.setStatus(FindingStatus.Running);
        Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> theFindings = loeService.getFindings(criteria);        
        finding.setQueryDTO(criteria);
        finding.setDomainFindings(theFindings);
        cacheManager.addToSessionCache(this.getSessionId(), this.getTaskId(), finding);
        finding.setStatus(FindingStatus.Completed);
        return true;      
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
		// TODO Auto-generated method stub
		return false;
	}

	
    public Finding getFinding() {
        // TODO Auto-generated method stub
        return null;
    }

}
