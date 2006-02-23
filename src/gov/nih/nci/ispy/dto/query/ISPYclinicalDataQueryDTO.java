package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.DiseaseStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.TimepointType;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ISPYclinicalDataQueryDTO implements ClinicalQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String queryName;
	
	private EnumSet<TimepointType> timepointValues = EnumSet.noneOf(TimepointType.class);
	
	private EnumSet<ClinicalResponseType> clinicalResponseValues = null;
	
	private EnumSet<DiseaseStageType> diseaseStageValues = null;
	
	private EnumSet<ERstatusType> erStatusValues = null;
	
	private EnumSet<HER2statusType> her2StatusValues = null;
	
	private EnumSet<PRstatusType> prStatusValues = null;
	
	
	public ISPYclinicalDataQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Set<ClinicalResponseType> getClinicalResponseValues() {
		return clinicalResponseValues;
	}


	public void setClinicalResponseValues(
			EnumSet<ClinicalResponseType> clinicalResponseValues) {
		this.clinicalResponseValues = clinicalResponseValues;
	}


	public Set<DiseaseStageType> getDiseaseStageValues() {
		return diseaseStageValues;
	}


	public void setDiseaseStageValues(EnumSet<DiseaseStageType> diseaseStageValues) {
		this.diseaseStageValues = diseaseStageValues;
	}


	public Set<ERstatusType> getErStatusValues() {
		return erStatusValues;
	}


	public void setErStatusValues(EnumSet<ERstatusType> erStatusValues) {
		this.erStatusValues = erStatusValues;
	}


	public Set<HER2statusType> getHer2StatusValues() {
		return her2StatusValues;
	}


	public void setHer2StatusValues(EnumSet<HER2statusType> her2StatusValues) {
		this.her2StatusValues = her2StatusValues;
	}


	public Set<PRstatusType> getPrStatusValues() {
		return prStatusValues;
	}


	public void setPrStatusValues(EnumSet<PRstatusType> prStatusValues) {
		this.prStatusValues = prStatusValues;
	}


	public Set<TimepointType> getTimepointValues() {
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
