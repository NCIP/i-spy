/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.analysis.messaging.CorrelationResult;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.CorrelationFinding;
import gov.nih.nci.ispy.service.clinical.ContinuousType;

public class ISPYCorrelationFinding extends CorrelationFinding {
	
	private static final long serialVersionUID = 1L;
	private ContinuousType continuousType1;
	private ContinuousType continuousType2;
	private boolean isPatientBased = false;
	private boolean isSampleBased = false;
	
	
	public ISPYCorrelationFinding(String sessionId, String taskId, FindingStatus status) {
	  super(sessionId, taskId, status);
	}
	
	public ISPYCorrelationFinding(String sessionId, String taskId, FindingStatus status, CorrelationResult result) {
	  super(sessionId, taskId, status, result);
	}

	public ContinuousType getContinuousType1() {
		return continuousType1;
	}

	public void setContinuousType1(ContinuousType continuousType1) {
		this.continuousType1 = continuousType1;
	}

	public ContinuousType getContinuousType2() {
		return continuousType2;
	}

	public void setContinuousType2(ContinuousType continuousType2) {
		this.continuousType2 = continuousType2;
	}

	public boolean isPatientBased() {
		return isPatientBased;
	}

	public void setPatientBased(boolean isPatientBased) {
		this.isPatientBased = isPatientBased;
	}

	public boolean isSampleBased() {
		return isSampleBased;
	}

	public void setSampleBased(boolean isSampleBased) {
		this.isSampleBased = isSampleBased;
	}
	
}	
