package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.AnalysisFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;

public class ISPYCategoricalCorrelationFinding extends AnalysisFinding {

	
	public ISPYCategoricalCorrelationFinding(String session, String task, FindingStatus status) {
		super(session, task, status);
	}

	private static final long serialVersionUID = 1L;

}
