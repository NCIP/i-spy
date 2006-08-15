package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;


/**
 * Strategy to get clinical findings.
 * @author harrismic
 *
 */
public abstract class ClinicalFindingStrategy extends SessionBasedFindingStrategy {

	private ISPYclinicalDataQueryDTO queryDTO;
	
	public ClinicalFindingStrategy(String sessionId, String taskId, ISPYclinicalDataQueryDTO queryDTO) throws ValidationException {
		super(sessionId, taskId);		
		this.queryDTO = queryDTO;
	}

	public ISPYclinicalDataQueryDTO getQueryDTO() {
		return queryDTO;
	}

}
