package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.ispy.dto.query.IHCqueryDTO;

public abstract class IHCFindingStrategy implements FindingStrategy {

	private String sessionId;
	private String taskId;
	private IHCqueryDTO queryDTO;
	
	public IHCFindingStrategy(String sessionId, String taskId, IHCqueryDTO queryDTO )  throws ValidationException {
		this.sessionId = sessionId;
		this.taskId = taskId;
		this.queryDTO = queryDTO;
	}

	public IHCqueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(IHCqueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	

}
