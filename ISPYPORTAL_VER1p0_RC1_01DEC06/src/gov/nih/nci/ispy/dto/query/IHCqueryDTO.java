package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.ispy.service.clinical.TimepointType;
import gov.nih.nci.ispy.service.ihc.IntensityOfStainType;
import gov.nih.nci.ispy.service.ihc.LocalizationType;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class IHCqueryDTO implements QueryDTO {

	private Set<String> biomarkers = null;
	private Integer percentPositiveMinValue = null;
	private Integer percentPositiveMaxValue = null;
	private EnumSet<IntensityOfStainType> intensityOfStainValues;
	private EnumSet<LocalizationType> localizationValues;
	private String queryName;
	private Set<String> restrainingSamples;      
		
	private EnumSet<TimepointType> timepointValues = EnumSet.noneOf(TimepointType.class);
	
	public IHCqueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<String> getBiomarkers() {
		return biomarkers;
	}

	public void setBiomarkers(Set<String> biomarkers) {
		this.biomarkers = biomarkers;
	}

	public EnumSet<IntensityOfStainType> getIntensityOfStainValues() {
		return intensityOfStainValues;
	}

	public void setIntensityOfStainValues(
			EnumSet<IntensityOfStainType> intensityOfStainValues) {
		this.intensityOfStainValues = intensityOfStainValues;
	}

	public EnumSet<LocalizationType> getLocalizationValues() {
		return localizationValues;
	}

	public void setLocalizationValues(EnumSet<LocalizationType> localizationValues) {
		this.localizationValues = localizationValues;
	}

	

	public Integer getPercentPositiveMaxValue() {
		return percentPositiveMaxValue;
	}

	public void setPercentPositiveMaxValue(Integer percentPositiveMaxValue) {
		this.percentPositiveMaxValue = percentPositiveMaxValue;
	}

	public Integer getPercentPositiveMinValue() {
		return percentPositiveMinValue;
	}

	public void setPercentPositiveMinValue(Integer percentPositiveMinValue) {
		this.percentPositiveMinValue = percentPositiveMinValue;
	}

	public Set<String> getRestrainingSamples() {
		return restrainingSamples;
	}

	public void setRestrainingSamples(Set<String> restrainingSamples) {
		this.restrainingSamples = restrainingSamples;
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
