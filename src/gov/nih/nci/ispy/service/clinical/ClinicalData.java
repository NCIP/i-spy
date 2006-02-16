package gov.nih.nci.ispy.service.clinical;

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

public class ClinicalData {

	private String patientId;
	private String labtrackId;
	private int timepoint;
	private DiseaseStageType diseaseStage;
	private ERstatusType erStatus;
	private double erValue = Double.MIN_VALUE;     //the summary ER score
	private double prValue = Double.MIN_VALUE;     //the summary PR score
	private double her2Value = Double.MIN_VALUE;   //the summary HER2 score
	private PRstatusType prStatus;
	private HER2statusType HER2status;
	private ClinicalResponseType clinicalResponse;
	private double longestDiameter = Double.MIN_VALUE;
	private String tumorMorphology;
	private String primaryTumorNuclearGrade;
	private String primaryTumorHistologyType;
	private double grossTumorSizeInCM = Double.MIN_VALUE; //Need to check units
	private double microscopeTumorSizeInCM = Double.MIN_VALUE;
	private List<String> chemicalAgents = new ArrayList<String>();
	private double MRIpctChange = Double.MIN_VALUE;  //change in tumor size (measured by MRI) wrt the baseline measurement


	public ClinicalData(String labtrackId, String patientId, int timepoint) {
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


	public double getErValue() {
		return erValue;
	}


	public void setErValue(double erValue) {
		this.erValue = erValue;
		
		if (erValue > 0.0) {
		  setErStatus(ERstatusType.ER_Pos);
		}
		else {
		  setErStatus(ERstatusType.ER_Neg);
		}
	}


	public double getGrossTumorSizeInCM() {
		return grossTumorSizeInCM;
	}


	public void setGrossTumorSizeInCM(double grossTumorSizeInCM) {
		this.grossTumorSizeInCM = grossTumorSizeInCM;
	}


	public double getHer2Value() {
		return her2Value;
	}


	public void setHer2Value(double her2Value) {
		this.her2Value = her2Value;
		
		if (her2Value > 0.0) {
		  setHER2status(HER2statusType.HER2_Pos);
		}
		else {
		  setHER2status(HER2statusType.HER2_Neg);
		}
		
	}


	public double getLongestDiameter() {
		return longestDiameter;
	}


	public void setLongestDiameter(double longestDiameter) {
		this.longestDiameter = longestDiameter;
	}


	public double getMicroscopeTumorSizeInCM() {
		return microscopeTumorSizeInCM;
	}


	public void setMicroscopeTumorSizeInCM(double microscopeTumorSizeInCM) {
		this.microscopeTumorSizeInCM = microscopeTumorSizeInCM;
	}


	public double getMRIpctChange() {
		return MRIpctChange;
	}


	public void setMRIpctChange(double ipctChange) {
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


	public double getPrValue() {
		return prValue;
	}


	public void setPrValue(double prValue) {
		this.prValue = prValue;
		
		if (prValue > 0.0) {
		  setPrStatus(PRstatusType.PR_Pos);
		}
		else {
		  setPrStatus(PRstatusType.PR_Neg);
		}
	}


	public int getTimepoint() {
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
  		  setDiseaseStage(DiseaseStageType.Missing);
  		}
    	else {
    	  setDiseaseStage(DiseaseStageType.valueOf(diseaseStage));	
    	}
    }

	public void setClinicalResponseFromString(String responseStr) {
		
		if (StringUtils.isEmptyStr(responseStr)) {
		  setClinicalResponse(ClinicalResponseType.Missing);
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
		  setClinicalResponse(ClinicalResponseType.Unknown);	
		}
		
	}


}
