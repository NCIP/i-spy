package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.CorrelationQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import org.apache.log4j.Logger;

public class CorrelationFindingStrategy extends SessionBasedFindingStrategy {

	private static Logger logger = Logger.getLogger(CorrelationFindingStrategy.class);	
	
	private ISPYclinicalDataQueryDTO queryDTO;
	
	
	public CorrelationFindingStrategy(String sessionId, String taskId, CorrelationQueryDTO correlationQueryDTO) {
	  super(sessionId, taskId);
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
