package gov.nih.nci.ispy.service.findings;

import java.util.List;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.ClinicalFinding;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.PatientData;

public class ISPYClinicalFinding extends Finding  {

	private List<PatientData> patientData = null;
	private ISPYclinicalDataQueryDTO dto = null;
	
	public ISPYClinicalFinding(String sessionId, String taskId, ISPYclinicalDataQueryDTO dto, FindingStatus status) {
		super(sessionId, taskId, status);
		this.dto = dto;
	}

	public void setPatientData(List<PatientData> patientData) {
		this.patientData = patientData;	
	}
	
	public List<PatientData> getPatientData() { return patientData; }
}
