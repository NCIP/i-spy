package gov.nih.nci.ispy.service.clinical;

import java.util.EnumSet;

public class ClinicalDataSpecifier {

	private EnumSet<TimepointType> timepointValues;
	
	private EnumSet<ClinicalResponseType> clinicalResponseValues;
	
	private EnumSet<DiseaseStageType> diseaseStageValues;
	
	private EnumSet<ERstatusType> erStatusValues;
	
	private EnumSet<HER2statusType> her2StatusValues;
	
	private EnumSet<PRstatusType> prStatusValues;
	
	
	public ClinicalDataSpecifier() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EnumSet<ClinicalResponseType> getClinicalResponseValues() {
		return clinicalResponseValues;
	}


	public void setClinicalResponseValues(
			EnumSet<ClinicalResponseType> clinicalResponseValues) {
		this.clinicalResponseValues = clinicalResponseValues;
	}


	public EnumSet<DiseaseStageType> getDiseaseStageValues() {
		return diseaseStageValues;
	}


	public void setDiseaseStageValues(EnumSet<DiseaseStageType> diseaseStageValues) {
		this.diseaseStageValues = diseaseStageValues;
	}


	public EnumSet<ERstatusType> getErStatusValues() {
		return erStatusValues;
	}


	public void setErStatusValues(EnumSet<ERstatusType> erStatusValues) {
		this.erStatusValues = erStatusValues;
	}


	public EnumSet<HER2statusType> getHer2StatusValues() {
		return her2StatusValues;
	}


	public void setHer2StatusValues(EnumSet<HER2statusType> her2StatusValues) {
		this.her2StatusValues = her2StatusValues;
	}


	public EnumSet<PRstatusType> getPrStatusValues() {
		return prStatusValues;
	}


	public void setPrStatusValues(EnumSet<PRstatusType> prStatusValues) {
		this.prStatusValues = prStatusValues;
	}


	public EnumSet<TimepointType> getTimepointValues() {
		return timepointValues;
	}


	public void setTimepointValues(EnumSet<TimepointType> timepointValues) {
		this.timepointValues = timepointValues;
	}

}
