package gov.nih.nci.ispy.service.clinical;

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
	private DiseaseStageType diseaseStage;
	private ERstatusType erStatus;
	private PRstatusType prStatus;
	private HER2statusType HER2status;
	private PatientResponseType patientResponse;
	
	
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


	public PatientResponseType getPatientResponse() {
		return patientResponse;
	}


	public void setPatientResponse(PatientResponseType patientResponse) {
		this.patientResponse = patientResponse;
	}


	public PRstatusType getPrStatus() {
		return prStatus;
	}


	public void setPrStatus(PRstatusType prStatus) {
		this.prStatus = prStatus;
	}

}
