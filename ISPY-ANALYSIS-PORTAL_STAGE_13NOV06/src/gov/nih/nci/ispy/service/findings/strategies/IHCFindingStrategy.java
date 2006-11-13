package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.IHCqueryDTO;
import gov.nih.nci.caintegrator.exceptions.ValidationException;


public abstract class IHCFindingStrategy extends SessionBasedFindingStrategy {

	private IHCqueryDTO queryDTO;
	
	public IHCFindingStrategy(String sessionId, String taskId, IHCqueryDTO queryDTO )  throws ValidationException {
		super(sessionId, taskId);
		this.queryDTO = queryDTO;
	}

	public IHCqueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(IHCqueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}

}
