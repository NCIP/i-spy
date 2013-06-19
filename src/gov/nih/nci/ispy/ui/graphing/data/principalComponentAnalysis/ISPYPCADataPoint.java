/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.ui.graphing.data.principalComponentAnalysis;

import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.common.TimepointType;





public class ISPYPCADataPoint extends PrincipalComponentAnalysisDataPoint {

	private Double tumorMRIpctChange;
	private ClinicalResponseType clinicalResponse;
	private TimepointType timepoint;
	private ClinicalStageType clinicalStage;
	private String ISPY_ID;
	
	public ISPYPCADataPoint(String sampleId) {
		super(sampleId);
	}
	
	public ISPYPCADataPoint(String sampleId, double pc1value, double pc2value, double pc3value) {
	  super(sampleId, pc1value, pc2value, pc3value);
	}

	public ClinicalResponseType getClinicalResponse() {
		return clinicalResponse;
	}

	public void setClinicalResponse(ClinicalResponseType clinicalResponse) {
		this.clinicalResponse = clinicalResponse;
	}

	public ClinicalStageType getClinicalStage() {
		return clinicalStage;
	}

	public void setClinicalStage(ClinicalStageType clinicalStage) {
		this.clinicalStage = clinicalStage;
	}

	public TimepointType getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(TimepointType timepoint) {
		this.timepoint = timepoint;
	}

	public Double getTumorMRIpctChange() {
		return tumorMRIpctChange;
	}

	public void setTumorMRIpctChange(Double tumorMRIpctChange) {
		this.tumorMRIpctChange = tumorMRIpctChange;
	}
	
	public String toString() {
		
	   StringBuffer sb = new StringBuffer();
	   //sb.append(getISPY_ID() + " ");
	   sb.append(getSampleId() + " ");
	   sb.append(getISPY_ID() + " ");
	   sb.append(getTimepoint() + " ");
	  
	   if (getClinicalResponse()!= ClinicalResponseType.MISSING) {
	     sb.append("CLIN_RESP: " +getClinicalResponse() + " ");
	   }
	   
	   if (getClinicalStage()!= ClinicalStageType.MISSING) {
	     sb.append("STAGE: " + getClinicalStage() + " ");
	   }
	   
//	   if (getTumorMRIpctChange()!= null) {
//	     sb.append("MRI_%: " + getTumorMRIpctChange());
//	   }
		
	   return sb.toString();
	}

	public String getISPY_ID() {
		return ISPY_ID;
	}

	public void setISPY_ID(String ispy_id) {
		ISPY_ID = ispy_id;
	}

}
