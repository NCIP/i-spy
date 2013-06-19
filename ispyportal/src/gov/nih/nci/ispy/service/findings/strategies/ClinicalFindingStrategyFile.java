/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.application.bean.FindingReportBean;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Set;

/**
 * Strategy to get clinical findings from a file.
 * @author harrismic
 *
 */

public class ClinicalFindingStrategyFile extends ClinicalFindingStrategy{

	private ISPYClinicalFinding clinicalFinding;
    private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	
	public ClinicalFindingStrategyFile(String sessionId, String taskId, ISPYclinicalDataQueryDTO queryDTO) throws ValidationException {
		super(sessionId, taskId, queryDTO);
        
        clinicalFinding = new ISPYClinicalFinding(sessionId, taskId, queryDTO, FindingStatus.Running);
        
	}

	public boolean validate(QueryDTO query) throws ValidationException {
	  return true;
	}

	public boolean createQuery() throws FindingsQueryException {
		return true;
	}

	public boolean executeQuery() throws FindingsQueryException {
		//take the query dto and execute a query based on the criteria
		ClinicalFileBasedQueryService cqs = ClinicalFileBasedQueryService.getInstance();
		
		
		Set<PatientData> patientData = cqs.getClinicalData(getQueryDTO());
		
	    //Set<String> patientDIDs = cqs.getPatientDIDs(getQueryDTO());
		
	    //List<PatientData> patientData = cqs.getPatientDataForPatientDIDs(new ArrayList<String>(patientDIDs));
	    
	    
	    
	    //put the result into the finding
	    clinicalFinding.setPatientData(new ArrayList<PatientData>(patientData));        
        FindingReportBean frb = new FindingReportBean();
        frb.setFinding(clinicalFinding);
        ApplicationFactory.getPresentationTierCache().addPersistableToSessionCache(clinicalFinding.getSessionId(),clinicalFinding.getTaskId(), frb);
        
	    return true;
	}

	public boolean analyzeResultSet() throws FindingsAnalysisException {
        clinicalFinding.setStatus(FindingStatus.Completed);
        cacheManager.addToSessionCache(this.getSessionId(), this.getTaskId(), clinicalFinding);        
		return true;
	}

	public Finding getFinding() {
		return clinicalFinding;
	}

}
