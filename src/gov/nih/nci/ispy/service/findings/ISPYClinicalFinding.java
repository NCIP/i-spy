/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.PatientData;

import java.util.List;

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
