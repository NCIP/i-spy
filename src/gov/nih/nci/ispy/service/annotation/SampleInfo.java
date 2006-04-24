package gov.nih.nci.ispy.service.annotation;

import gov.nih.nci.ispy.service.clinical.TimepointType;

public class SampleInfo {
	
	private String labtrackId;
	private String registrantId;
	private TimepointType timepoint;
	private SampleCoreType coreType;
	private ISPYDataType dataType;
	private String sectionInfo;

	private String calgId;

	public SampleInfo(String registrantId, String labtrackId) {
	  this.registrantId = registrantId;
	  this.labtrackId = labtrackId;
	}


	public void setDataType(ISPYDataType dataType) {
	  this.dataType = dataType;
	}
	
	public ISPYDataType getDataType() {
	  return dataType;
	}
	


	public String getCalgId() {
		return calgId;
	}

	public void setCalgId(String calgId) {
		this.calgId = calgId;
	}

	public SampleCoreType getCoreType() {
		return coreType;
	}

	public void setCoreType(SampleCoreType coreType) {
		this.coreType = coreType;
	}

	public String getLabtrackId() {
		return labtrackId;
	}
	
	public String getRegistrantId() {
	  return registrantId;
	}

	public String getSectionInfo() {
		return sectionInfo;
	}

	public void setSectionInfo(String sectionInfo) {
		this.sectionInfo = sectionInfo;
	}

	public TimepointType getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(TimepointType timepoint) {
		this.timepoint = timepoint;
	}
	
	public String toString() {
		
	  StringBuffer sb = new StringBuffer();
	  sb.append("Sample labtrackId=").append(getLabtrackId()).append("\t");
	  sb.append(getTimepoint()).append("\t");
	  sb.append(getCoreType()).append("\t");
	  sb.append(getDataType()).append("\t");
	  sb.append(getSectionInfo()).append("\t");
	  sb.append(getCalgId()).append("\n");
		
	  return sb.toString();
	}

}
