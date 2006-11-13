package gov.nih.nci.ispy.service.findings.strategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gov.nih.nci.caintegrator.domain.finding.bean.SpecimenBasedMolecularFinding;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LevelOfExpressionIHCFinding;
import gov.nih.nci.caintegrator.dto.query.IHCqueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;

import gov.nih.nci.ispy.dto.query.IHCLevelOfExpressionQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalCGOMBasedQueryService;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.service.findings.ISPYIHCLevelOfExpressionFinding;
import gov.nih.nci.ispy.service.ihc.IHCCGOMBasedQueryService;
import gov.nih.nci.ispy.service.ihc.IHCData;
import gov.nih.nci.ispy.service.ihc.IHCLevelOfExpressionData;

public class IHCLevelOfExpressionFindingStrategyCGOM extends IHCFindingStrategy {
	
	private ISPYIHCLevelOfExpressionFinding ihcLevelOfEpxFinding;

	public IHCLevelOfExpressionFindingStrategyCGOM(String sessionId, String taskId, IHCqueryDTO queryDTO)  throws ValidationException {
		super(sessionId, taskId, queryDTO);
		// TODO Auto-generated constructor stub
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
		
		IHCCGOMBasedQueryService ihcqs = IHCCGOMBasedQueryService.getInstance();
		Set<IHCLevelOfExpressionData> levelOfExpIHCData = ihcqs.getIHCLevelOfExpData((IHCLevelOfExpressionQueryDTO)this.getQueryDTO());		
		
	    
	    //put the result into the finding 
		ihcLevelOfEpxFinding = new ISPYIHCLevelOfExpressionFinding(this.getSessionId(), this.getTaskId(), this.getQueryDTO());
		ihcLevelOfEpxFinding.setIHCData(new ArrayList<IHCData>(levelOfExpIHCData));
	    
	    return true;
	    

	    
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
		// TODO Auto-generated method stub
		return false;
	}

	public Finding getFinding() {
		// TODO Auto-generated method stub		
	return ihcLevelOfEpxFinding;
	
	}

}
