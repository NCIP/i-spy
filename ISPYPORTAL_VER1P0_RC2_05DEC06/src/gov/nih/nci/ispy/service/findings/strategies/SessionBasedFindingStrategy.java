package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;


/*
 * This class should really be moved up into app-commons or spec
 * 
 */
public abstract class SessionBasedFindingStrategy implements FindingStrategy {

	private String sessionId;
	private String taskId;
	
	
	public SessionBasedFindingStrategy(String sessionId, String taskId) {
		this.sessionId = sessionId;
		this.taskId = taskId;
	}


	public String getSessionId() {
		return sessionId;
	}


	public String getTaskId() {
		return taskId;
	}

}
