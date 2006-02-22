package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.DiseaseStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.TimepointType;

import java.util.EnumSet;

public class ISPYclinicalDataQueryDTO implements ClinicalQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String queryName;
	
	private EnumSet<TimepointType> timepointValues;
	
	private EnumSet<ClinicalResponseType> clinicalResponseValues;
	
	private EnumSet<DiseaseStageType> diseaseStageValues;
	
	private EnumSet<ERstatusType> erStatusValues;
	
	private EnumSet<HER2statusType> her2StatusValues;
	
	private EnumSet<PRstatusType> prStatusValues;
	
	
	public ISPYclinicalDataQueryDTO() {
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


	public void setQueryName(String name) {
	  this.queryName = name;
		
	}


	public String getQueryName() {
	  return queryName;
	}

}
