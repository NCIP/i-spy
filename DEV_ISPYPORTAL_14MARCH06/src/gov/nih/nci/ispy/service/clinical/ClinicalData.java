package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.caintegrator.application.util.StringUtils;

/**
 * This class will hold the clinical data returned by the
 * ClinicalDataQueryService.   
 * 
 * It represents the clinical data corresponding to a given
 * patient a specific timepoint.
 * 
 * @author harrismic
 *
 */

public class ClinicalData implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String patientId;
	private String labtrackId;
	private TimepointType timepoint;
	private DiseaseStageType diseaseStage;
	private ERstatusType erStatus;
	private Double erValue = null;     //the summary ER score
	private Double prValue = null;     //the summary PR score
	private Double her2Value = null;   //the summary HER2 score
	private PRstatusType prStatus;
	private HER2statusType HER2status;
	private ClinicalResponseType clinicalResponse;
	private Double longestDiameter = null;
	private String tumorMorphology;
	private String primaryTumorNuclearGrade;
	private String primaryTumorHistologyType;
	private Double grossTumorSizeInCM = null; //Need to check units
	private Double microscopeTumorSizeInCM = null;
	private List<String> chemicalAgents = new ArrayList<String>();
	private Double MRIpctChange = null;  //change in tumor size (measured by MRI) wrt the baseline measurement


	public ClinicalData(String labtrackId, String patientId, TimepointType timepoint) {
	  this.labtrackId = labtrackId;
	  this.patientId = patientId;
	  this.timepoint = timepoint;
	}


	public DiseaseStageType getDiseaseStage() {
		return diseaseStage;
	}


	public void setDiseaseStage(DiseaseStageType diseaseStage) {
		this.diseaseStage = diseaseStage;
	}


	public ERstatusType getErStatus() {
		return erStatus;
	}


    public void setErStatus(ERstatusType erStatus) {
		this.erStatus = erStatus;
	}


	public HER2statusType getHER2status() {
		return HER2status;
	}


    public void setHER2status(HER2statusType her2status) {
		HER2status = her2status;
	}


	public String getLabtrackId() {
		return labtrackId;
	}


	public String getPatientId() {
		return patientId;
	}


	public ClinicalResponseType getPatientResponse() {
		return clinicalResponse;
	}


	public void setPatientResponse(ClinicalResponseType clinicalResponse) {
		this.clinicalResponse = clinicalResponse;
	}


	public PRstatusType getPrStatus() {
		return prStatus;
	}


	public void setPrStatus(PRstatusType prStatus) {
		this.prStatus = prStatus;
	}


	public List<String> getChemicalAgents() {
		return chemicalAgents;
	}


	public void setChemicalAgents(List<String> chemicalAgents) {
		this.chemicalAgents = chemicalAgents;
	}


	public ClinicalResponseType getClinicalResponse() {
		return clinicalResponse;
	}


	public void setClinicalResponse(ClinicalResponseType clinicalResponse) {
		this.clinicalResponse = clinicalResponse;
	}


	public Double getErValue() {
		return erValue;
	}


	public void setErValue(Double erValue) {
		this.erValue = erValue;
		
		if (erValue == null) {
		  setErStatus(ERstatusType.MISSING);
		}
		else if (erValue > 0.0) {
		  setErStatus(ERstatusType.ER_POS);
		}
		else {
		  setErStatus(ERstatusType.ER_NEG);
		}
	}


	public Double getGrossTumorSizeInCM() {
		return grossTumorSizeInCM;
	}


	public void setGrossTumorSizeInCM(Double grossTumorSizeInCM) {
		this.grossTumorSizeInCM = grossTumorSizeInCM;
	}


	public Double getHer2Value() {
		return her2Value;
	}


	public void setHer2Value(Double her2Value) {
		this.her2Value = her2Value;
		
		if (her2Value == null) {
		  setHER2status(HER2statusType.MISSING);
		}
		else if (her2Value > 0.0) {
		  setHER2status(HER2statusType.HER2_POS);
		}
		else {
		  setHER2status(HER2statusType.HER2_NEG);
		}
		
	}


	public Double getLongestDiameter() {
		return longestDiameter;
	}


	public void setLongestDiameter(Double longestDiameter) {
		this.longestDiameter = longestDiameter;
	}


	public Double getMicroscopeTumorSizeInCM() {
		return microscopeTumorSizeInCM;
	}


	public void setMicroscopeTumorSizeInCM(Double microscopeTumorSizeInCM) {
		this.microscopeTumorSizeInCM = microscopeTumorSizeInCM;
	}


	public Double getMRIpctChange() {
		return MRIpctChange;
	}


	public void setMRIpctChange(Double ipctChange) {
		MRIpctChange = ipctChange;
	}


	public String getPrimaryTumorHistologyType() {
		return primaryTumorHistologyType;
	}


	public void setPrimaryTumorHistologyType(String primaryTumorHistologyType) {
		this.primaryTumorHistologyType = primaryTumorHistologyType;
	}


	public String getPrimaryTumorNuclearGrade() {
		return primaryTumorNuclearGrade;
	}


	public void setPrimaryTumorNuclearGrade(String primaryTumorNuclearGrade) {
		this.primaryTumorNuclearGrade = primaryTumorNuclearGrade;
	}


	public Double getPrValue() {
		return prValue;
	}


	public void setPrValue(Double prValue) {
		this.prValue = prValue;
		
		if (prValue == null) {
		  setPrStatus(PRstatusType.MISSING);
		}
		else if (prValue > 0.0) {
		  setPrStatus(PRstatusType.PR_POS);
		}
		else {
		  setPrStatus(PRstatusType.PR_NEG);
		}
	}


	public TimepointType getTimepoint() {
		return timepoint;
	}


	public String getTumorMorphology() {
		return tumorMorphology;
	}


	public void setTumorMorphology(String tumorMorphology) {
		this.tumorMorphology = tumorMorphology;
	}

    public void setDiseaseStageFromString(String diseaseStage) {
    	if ((diseaseStage==null)||(diseaseStage.trim().length()==0)) {
  		  setDiseaseStage(DiseaseStageType.MISSING);
  		}
    	else {
    	  setDiseaseStage(DiseaseStageType.valueOf(diseaseStage));	
    	}
    }

	public void setClinicalResponseFromString(String responseStr) {
		
		if (StringUtils.isEmptyStr(responseStr)) {
		  setClinicalResponse(ClinicalResponseType.MISSING);
		}
		else if (responseStr.length() == 2) {
		  setClinicalResponse(ClinicalResponseType.valueOf(responseStr));
		}
		else if (responseStr.equalsIgnoreCase("Progressive Disease")) {
		  setClinicalResponse(ClinicalResponseType.PD);
		}
		else if (responseStr.equalsIgnoreCase("Stable Disease")) {
		  setClinicalResponse(ClinicalResponseType.SD);	
		}
		else {
		  setClinicalResponse(ClinicalResponseType.UNKNOWN);	
		}
		
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("LabtrackId:").append("\t").append(labtrackId).append("\n");
		sb.append("PatiendId:").append("\t").append(patientId).append("\n");
		sb.append("Timepoint:").append("\t").append(timepoint).append("\n");
		sb.append("DiseaseStage:").append("\t").append(diseaseStage).append("\n");
		sb.append("ERstatus:").append("\t").append(erStatus).append("\n");		
		sb.append("erValue:").append("\t").append(erValue).append("\n");
		sb.append("prStatus:").append("\t").append(prStatus).append("\n");
		sb.append("prValue:").append("\t").append(prValue).append("\n");
		sb.append("HER2status:").append("\t").append(HER2status).append("\n");
		sb.append("HER2Value:").append("\t").append(her2Value).append("\n");
		sb.append("ClinicalResponse:").append("\t").append(clinicalResponse).append("\n");
		sb.append("LongestDiameter:").append("\t").append(longestDiameter).append("\n");
		sb.append("TumorMorphology").append("\t").append(tumorMorphology).append("\n");
		sb.append("PrimaryTumorNuclearGrade:").append("\t").append(primaryTumorNuclearGrade).append("\n");
		sb.append("PrimaryTumorHistologyType:").append("\t").append(primaryTumorHistologyType).append("\n");
		sb.append("GrossTumorSizeInCM:").append("\t").append(grossTumorSizeInCM).append("\n");
		sb.append("MicroscopeTumorSizeInCM").append("\t").append(microscopeTumorSizeInCM).append("\n");
		sb.append("ChemicalAgents:").append("\t").append(StringUtils.concatinateStrings(chemicalAgents, "|")).append("\n");
		sb.append("MRIpctChange:").append("\t").append(MRIpctChange).append("\n");
		
		return sb.toString();
	}


}
