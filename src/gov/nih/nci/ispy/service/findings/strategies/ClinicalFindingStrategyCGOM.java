package gov.nih.nci.ispy.service.findings.strategies;

import java.util.ArrayList;
import java.util.Set;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalCGOMBasedQueryService;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;

/**
 * Strategy to get Clinical findings from the CGOM.
 *
 */
public class ClinicalFindingStrategyCGOM extends ClinicalFindingStrategy {

	private ISPYClinicalFinding clinicalFinding;
	
	public ClinicalFindingStrategyCGOM(String sessionId, String taskId, ISPYclinicalDataQueryDTO queryDTO) throws ValidationException {
		super(sessionId, taskId, queryDTO);
	}

	public boolean validate(QueryDTO query) throws ValidationException {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean createQuery() throws FindingsQueryException {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean executeQuery() throws FindingsQueryException {
		ClinicalCGOMBasedQueryService cqs = ClinicalCGOMBasedQueryService.getInstance();
		
		
		Set<PatientData> patientData = cqs.getClinicalData(getQueryDTO());
		
	   
	    
	    //put the result into the finding 
	    clinicalFinding = new ISPYClinicalFinding(this.getSessionId(), this.getTaskId(), this.getQueryDTO());
	    clinicalFinding.setPatientData(new ArrayList<PatientData>(patientData));
	    
	    return true;
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
		// TODO Auto-generated method stub
		return false;
	}

	public Finding getFinding() {
		return clinicalFinding;
	}

}
