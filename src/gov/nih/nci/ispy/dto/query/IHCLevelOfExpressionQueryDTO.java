package gov.nih.nci.ispy.dto.query;


import gov.nih.nci.caintegrator.dto.query.IHCqueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.ihc.DistributionType;
import gov.nih.nci.ispy.service.ihc.IntensityOfStainType;
import gov.nih.nci.ispy.service.ihc.LocalizationType;

import java.util.EnumSet;
import java.util.Set;

public class IHCLevelOfExpressionQueryDTO implements IHCqueryDTO {

	private Set<String> biomarkers = null;
	private Integer percentPositiveMinValue = null;
	private Integer percentPositiveMaxValue = null;
	private Integer absolutePercentPositiveValue = null;
	private EnumSet<IntensityOfStainType> intensityOfStainValues;
	private EnumSet<LocalizationType> localizationValues;
	private EnumSet<DistributionType> distributionValues;
	private String queryName;
	private Set<String> restrainingSamples;      
		
	private EnumSet<TimepointType> timepointValues = EnumSet.noneOf(TimepointType.class);
	
	public IHCLevelOfExpressionQueryDTO() {
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
  
	
	

	public EnumSet<DistributionType> getDistributionValues() {
		return distributionValues;
	}

	public void setDistributionValues(EnumSet<DistributionType> distributionValues) {
		this.distributionValues = distributionValues;
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

	
	public Integer getAbsolutePercentPositiveValue() {
		return absolutePercentPositiveValue;
	}

	public void setAbsolutePercentPositiveValue(Integer absolutePercentPositiveValue) {
		this.absolutePercentPositiveValue = absolutePercentPositiveValue;
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
