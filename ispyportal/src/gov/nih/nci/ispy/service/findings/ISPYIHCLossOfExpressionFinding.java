package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;

public class ISPYIHCLossOfExpressionFinding extends Finding  {
	
	
	public ISPYIHCLossOfExpressionFinding(String sessionId, String taskId) {
		super(sessionId, taskId, FindingStatus.Completed);
	}
	
}
