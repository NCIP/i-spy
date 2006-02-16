package gov.nih.nci.ispy.service.clinical;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will hold the clinical data returned by the
 * ClinicalDataQueryService.   
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
	private double erValue;     //the summary ER score
	private double prValue;     //the summary PR score
	private double her2Value;   //the summary HER2 score
	private PRstatusType prStatus;
	private HER2statusType HER2status;
	private ClinicalResponseType clinicalResponse;
	private double longestDiameter;
	private String tumorMorphology;
	private String primaryTumorNuclearGrade;
	private String primaryTumorHistologyType;
	private double grossTumorSizeInCM; //Need to check units
	private double microscopeTumorSizeInCM;
	private List<String> chemicalAgents = new ArrayList<String>();
	private double MRIpctChange;  //change in tumor size (measured by MRI) wrt the baseline measurement


	public ClinicalData() {
		super();
		// TODO Auto-generated constructor stub
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


	public void setLabtrackId(String labtrackId) {
		this.labtrackId = labtrackId;
	}


	public String getPatientId() {
		return patientId;
	}


	public void setPatientId(String patientId) {
		this.patientId = patientId;
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
	}


	public int getTimepoint() {
		return timepoint;
	}


	public void setTimepoint(int timepoint) {
		this.timepoint = timepoint;
	}


	public String getTumorMorphology() {
		return tumorMorphology;
	}


	public void setTumorMorphology(String tumorMorphology) {
		this.tumorMorphology = tumorMorphology;
	}

}
