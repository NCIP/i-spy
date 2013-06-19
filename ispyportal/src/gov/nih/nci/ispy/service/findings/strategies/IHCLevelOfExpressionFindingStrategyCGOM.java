/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.ispy.service.findings.ISPYIHCLevelOfExpressionFinding;
import gov.nih.nci.ispy.service.ihc.LevelOfExpressionIHCService;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.Collection;

public class IHCLevelOfExpressionFindingStrategyCGOM extends SessionBasedFindingStrategy {
	
	
    private LevelOfExpressionIHCFindingCriteria criteria;
    private ISPYIHCLevelOfExpressionFinding finding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
    

	public IHCLevelOfExpressionFindingStrategyCGOM(String sessionId, String taskId, LevelOfExpressionIHCFindingCriteria criteria)  throws ValidationException {
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
        LevelOfExpressionIHCService loeService = LevelOfExpressionIHCService.getInstance();
        finding = new ISPYIHCLevelOfExpressionFinding(this.getSessionId(), this.getTaskId());
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
