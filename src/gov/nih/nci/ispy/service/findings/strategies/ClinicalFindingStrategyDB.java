package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

/**
 * Strategy to get Clinical findings from the data base.
 *
 */
public class ClinicalFindingStrategyDB extends ClinicalFindingStrategy {

	public ClinicalFindingStrategyDB(String sessionId, String taskId, ISPYclinicalDataQueryDTO queryDTO) throws ValidationException {
		super(sessionId, taskId, queryDTO);
	}

	public boolean validate(QueryDTO query) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean createQuery() throws FindingsQueryException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean executeQuery() throws FindingsQueryException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
		// TODO Auto-generated method stub
		return false;
	}

	public Finding getFinding() {
		// TODO Auto-generated method stub
		return null;
	}

}
