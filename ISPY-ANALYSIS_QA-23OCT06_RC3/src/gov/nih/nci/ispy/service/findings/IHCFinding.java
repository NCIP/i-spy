package gov.nih.nci.ispy.service.findings;

import java.util.List;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.ClinicalFinding;
import gov.nih.nci.ispy.dto.query.IHCqueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.ihc.IHCData;

public class IHCFinding extends Finding  {

	private List<IHCData> ihcData = null;
	
	public IHCFinding(String sessionId, String taskId, IHCqueryDTO dto) {
		super(sessionId, taskId, FindingStatus.Completed);
	}

	public void setIHCData(List<IHCData> ihcData) {
		this.ihcData = ihcData;	
	}
	
	public List<IHCData> getIHCData() { return ihcData; }
}
