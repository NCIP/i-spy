package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;


/**
 * Strategy to get clinical findings.
 * @author harrismic
 *
 */
public abstract class ClinicalFindingStrategy implements FindingStrategy {

	private String sessionId;
	private String taskId;
	private ISPYclinicalDataQueryDTO queryDTO;
	
	public ClinicalFindingStrategy(String sessionId, String taskId, ISPYclinicalDataQueryDTO queryDTO) throws ValidationException {
		this.sessionId = sessionId;
		this.taskId = taskId;
		this.queryDTO = queryDTO;
	}

	public ISPYclinicalDataQueryDTO getQueryDTO() {
		return queryDTO;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getTaskId() {
		return taskId;
	}

}
