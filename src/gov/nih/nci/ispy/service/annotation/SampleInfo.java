package gov.nih.nci.ispy.service.annotation;

public class SampleInfo {
	
	private String labtrackId;
	private int timepoint;
	private SampleCoreType coreType;
	private String sectionInfo;
	private String agilentLabtrackId;
	private String cdnaLabtrackId;
	private String calgId;

	public SampleInfo(String labtrackId) {
	  this.labtrackId = labtrackId;
	}

	public String getAgilentLabtrackId() {
		return agilentLabtrackId;
	}

	public void setAgilentLabtrackId(String agilentLabtrackId) {
		this.agilentLabtrackId = agilentLabtrackId;
	}

	public String getCalgId() {
		return calgId;
	}

	public void setCalgId(String calgId) {
		this.calgId = calgId;
	}

	public String getCdnaLabtrackId() {
		return cdnaLabtrackId;
	}

	public void setCdnaLabtrackId(String cdnaLabtrackId) {
		this.cdnaLabtrackId = cdnaLabtrackId;
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

	public String getSectionInfo() {
		return sectionInfo;
	}

	public void setSectionInfo(String sectionInfo) {
		this.sectionInfo = sectionInfo;
	}

	public int getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(int timepoint) {
		this.timepoint = timepoint;
	}
	
	public String toString() {
		
	  StringBuffer sb = new StringBuffer();
	  sb.append("Sample labtrackId=").append(getLabtrackId()).append("\t");
	  sb.append(getTimepoint()).append("\t");
	  sb.append(getCoreType()).append("\t");
	  sb.append(getSectionInfo()).append("\t");
	  sb.append(getAgilentLabtrackId()).append("\t");
	  sb.append(getCdnaLabtrackId()).append("\t");
	  sb.append(getCalgId()).append("\n");
		
	  return sb.toString();
	}

}
