package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.IHCqueryDTO;

public class IHCFindingStrategyCGOM extends IHCFindingStrategy {

	public IHCFindingStrategyCGOM(String sessionId, String taskId, IHCqueryDTO queryDTO)  throws ValidationException {
		super(sessionId, taskId, queryDTO);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
