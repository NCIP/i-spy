/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.ui.graphing.data;

import gov.nih.nci.caintegrator.application.graphing.PlotPoint;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.common.TimepointType;

public class ISPYPlotPoint extends PlotPoint {

	private PatientData patientData = null;
	private SampleInfo  sampleInfo = null;
	private ContinuousType continuousType = null;
	
	
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
	
	public boolean hasSampleInfo() { 
	 
		return sampleInfo != null;
		
	}
	
	public boolean hasPatientData() {
		
       return patientData != null;
       
	}
	
	public Double getMRITumorPctChange(TimepointType timepoint) {
		if (patientData == null) {
			return null;
		}
		
		switch(timepoint) {
		case T2: return patientData.getMriPctChangeT1_T2();
		case T3: return patientData.getMriPctChangeT1_T3();
		case T4: return patientData.getMriPctChangeT1_T4();
		}
		
		return null;
	}
	
	public Double getMRITumorPctChange() {
	  if (sampleInfo == null) {
		  return null;
	  }
	  
	  return getMRITumorPctChange(sampleInfo.getTimepoint());
	}
	
	public ClinicalResponseType getClinicalResponse() {
	  if ((sampleInfo == null) || (patientData == null)) {
		  return null;
	  }
	 
	  return patientData.getClinicalResponse(sampleInfo.getTimepoint());
	}
	
	public TimepointType getTimepoint() {
      if (sampleInfo == null) return null;
      
      return sampleInfo.getTimepoint();
      
	}
	
	public ClinicalStageType getClinicalStage() {
		 if ((sampleInfo == null) || (patientData == null)) {
			  return null;
		  }
		 
		  return patientData.getClinicalStage();
		
	}
	
	public String getTag() {
	  StringBuffer sb = new StringBuffer();	
	   
	  if (sampleInfo != null) {
		    sb.append(sampleInfo.getLabtrackId()).append(" ");
	  }
	  
	  if (patientData != null) {
	    sb.append(patientData.getISPY_ID()).append(" ");
	  }
	  
	  if ((sampleInfo!=null)&&(sampleInfo.getTimepoint()!=null)) {
	    sb.append(sampleInfo.getTimepoint());
	  }
	  
	  return sb.toString();
	}

	public ContinuousType getContinuousType() {
		return continuousType;
	}

	public void setContinuousType(ContinuousType continuousType) {
		this.continuousType = continuousType;
	}

	
}
