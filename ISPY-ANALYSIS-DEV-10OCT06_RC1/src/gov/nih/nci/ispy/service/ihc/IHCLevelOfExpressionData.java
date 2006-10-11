package gov.nih.nci.ispy.service.ihc;

public class IHCLevelOfExpressionData implements IHCData
{

	private String  ispy_id, 
	                intensityOfStrain,
	                localizationOfStain,
	                distributionOfStain,
	                timePoint,
	                bioMarker;
	
	private int percentPosNum;
	private int percentPosNumMax;
	private int percentPosNumMin;
	
	public String getDistributionOfStain() {
		return distributionOfStain;
	}
	public void setDistributionOfStain(String distributionOfStain) {
		this.distributionOfStain = distributionOfStain;
	}
	public String getIntensityOfStrain() {
		return intensityOfStrain;
	}
	public void setIntensityOfStrain(String intensityOfStrain) {
		this.intensityOfStrain = intensityOfStrain;
	}
	public String getIspy_id() {
		return ispy_id;
	}
	public void setIspy_id(String ispy_id) {
		this.ispy_id = ispy_id;
	}
	public String getLocalizationOfStain() {
		return localizationOfStain;
	}
	public void setLocalizationOfStain(String localizationOfStain) {
		this.localizationOfStain = localizationOfStain;
	}
	public int getPercentPosNum() {
		return percentPosNum;
	}
	public void setPercentPosNum(int percentPosNum) {
		this.percentPosNum = percentPosNum;
	}
	public int getPercentPosNumMax() {
		return percentPosNumMax;
	}
	public void setPercentPosNumMax(int percentPosNumMax) {
		this.percentPosNumMax = percentPosNumMax;
	}
	public int getPercentPosNumMin() {
		return percentPosNumMin;
	}
	public void setPercentPosNumMin(int percentPosNumMin) {
		this.percentPosNumMin = percentPosNumMin;
	}
	public String getBioMarker() {
		return bioMarker;
	}
	public void setBioMarker(String bioMarker) {
		this.bioMarker = bioMarker;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}

	
}
