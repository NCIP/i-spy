/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.analysis.messaging.AnalysisResult;
import gov.nih.nci.caintegrator.analysis.messaging.CategoricalCorrelationRequest;
import gov.nih.nci.caintegrator.analysis.messaging.CategoricalCorrelationResult;
import gov.nih.nci.caintegrator.analysis.messaging.DataPointVector;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.AnalysisFinding;
import gov.nih.nci.ispy.service.clinical.ContinuousType;

import java.util.List;

public class ISPYCategoricalCorrelationFinding extends AnalysisFinding {

	private CategoricalCorrelationRequest catCorrRequest;
	private CategoricalCorrelationResult catCorrResult;
	private ContinuousType contType; 
	
	public ISPYCategoricalCorrelationFinding(String session, String task, FindingStatus status) {
		super(session, task, status);
	}

	private static final long serialVersionUID = 1L;
	
	public void setAnalysisResult(AnalysisResult results) throws ClassCastException{
		super.setAnalysisResult(results);
		this.catCorrResult = (CategoricalCorrelationResult)results;
	}
	
	public List<DataPointVector> getDataVectors() {
		return catCorrResult.getDataVectors();
	}

	public CategoricalCorrelationResult getCatCorrResult() {
		return catCorrResult;
	}

	public void setCatCorrResult(CategoricalCorrelationResult catCorrResult) {
		this.catCorrResult = catCorrResult;
	}

	public ContinuousType getContType() {
		return contType;
	}

	public void setContType(ContinuousType contType) {
		this.contType = contType;
	}

	public CategoricalCorrelationRequest getCatCorrRequest() {
		return catCorrRequest;
	}

	public void setCatCorrRequest(CategoricalCorrelationRequest catCorrRequest) {
		this.catCorrRequest = catCorrRequest;
	}
	
	

}
