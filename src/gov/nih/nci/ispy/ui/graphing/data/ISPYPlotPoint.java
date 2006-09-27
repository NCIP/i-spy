package gov.nih.nci.ispy.ui.graphing.data;

import gov.nih.nci.caintegrator.application.graphing.PlotPoint;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.clinical.TimepointType;

public class ISPYPlotPoint extends PlotPoint {

	private PatientData patientData = null;
	private SampleInfo  sampleInfo = null;
	
	public ISPYPlotPoint(String id) {
		super(id);
	}
	
	public String toString() {
	  StringBuffer sb = new StringBuffer();
	  sb.append("SampleId=").append(getId());
	  sb.append(" X=").append(getX()).append(" Y=").append(getY()).append(" Z=").append(getZ()).append("\n");
	  if (patientData != null) {
	    sb.append(patientData);
	  }
	  return sb.toString();
	}

	public PatientData getPatientData() {
		return patientData;
	}

	public void setPatientData(PatientData patientData) {
		this.patientData = patientData;
	}
	
	public SampleInfo getSampleInfo() {
		return sampleInfo;
	}

	public void setSampleInfo(SampleInfo sampleInfo) {
		this.sampleInfo = sampleInfo;
	}
	
	public Double getMRITumorPctChange() {
	  if ((sampleInfo == null) || (patientData == null)) {
		  return null;
	  }
	  
	  if (sampleInfo.getTimepoint() == TimepointType.T2) {
		return patientData.getMriPctChangeT1_T2();
	  }
	  else if (sampleInfo.getTimepoint() == TimepointType.T3) {
		return patientData.getMriPctChangeT1_T3();
	  }
	  else if (sampleInfo.getTimepoint() == TimepointType.T4) {
		return patientData.getMriPctChangeT1_T4();
	  }
	  
	  return null;
	}
	
	public ClinicalResponseType getClinicalResponse() {
	  if ((sampleInfo == null) || (patientData == null)) {
		  return null;
	  }
	 
	  return patientData.getClinicalResponse(sampleInfo.getTimepoint());
	}
	
	public String getTag() {
	  StringBuffer sb = new StringBuffer();	
	  sb.append(patientData.getISPY_ID()).append(" ").append(sampleInfo.getLabtrackId()).append(sampleInfo.getTimepoint());
	  return sb.toString();
	}

	
}
