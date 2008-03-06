package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;

public class ISPYIHCLevelOfExpressionFinding extends Finding  {
	
	
	public ISPYIHCLevelOfExpressionFinding(String sessionId, String taskId) {
		super(sessionId, taskId, FindingStatus.Completed);
	}
	
}
