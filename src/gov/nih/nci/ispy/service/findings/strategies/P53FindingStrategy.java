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
import gov.nih.nci.caintegrator.studyQueryService.dto.p53.P53FindingCriteria;
import gov.nih.nci.ispy.service.findings.P53Finding;
import gov.nih.nci.ispy.service.ihc.P53Service;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.Collection;

public class P53FindingStrategy extends SessionBasedFindingStrategy {
	
	 private P53FindingCriteria criteria;
	 private P53Finding finding;
	 private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	    
	 
	 public P53FindingStrategy(String sessionId, String taskId, P53FindingCriteria criteria)  throws ValidationException {
			super(sessionId, taskId);
			this.criteria = criteria;
		}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
		
		return false;
	}

	public boolean createQuery() throws FindingsQueryException {
	
		return true;
	}

	public boolean executeQuery() throws FindingsQueryException {
		P53Service p53Service = P53Service.getInstance();
        finding = new P53Finding(this.getSessionId(), this.getTaskId());
        finding.setStatus(FindingStatus.Running);
        Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> theFindings = p53Service.getFindings(criteria);        
        finding.setQueryDTO(criteria);
        finding.setDomainFindings(theFindings);
        cacheManager.addToSessionCache(this.getSessionId(), this.getTaskId(), finding);
        finding.setStatus(FindingStatus.Completed);
        return true;   
		
		
	}

	public Finding getFinding() {
		
		return null;
	}

	public boolean validate(QueryDTO query) throws ValidationException {
		
		return true;
	}

}
