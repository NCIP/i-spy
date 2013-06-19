/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.annotation;

import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.EnumSet;

public class SampleInfo {
	
	private String labtrackId;
	private String ispyId;
	private TimepointType timepoint;
	private SampleCoreType coreType;
	private EnumSet<ISPYDataType> dataTypes = EnumSet.noneOf(ISPYDataType.class);
	private String sectionInfo;

	private String calgId;
	
	public SampleInfo(String ispyId, TimepointType timepoint) {
	  this.ispyId = ispyId;
	  this.timepoint = timepoint;
	}

	public SampleInfo(String ispyId, String labtrackId) {
	  this.ispyId = ispyId;
	  this.labtrackId = labtrackId;
	}


	public void addDataType(ISPYDataType dataType) {
	  this.dataTypes.add(dataType);
	}
	
	public EnumSet<ISPYDataType> getDataTypes() {
	  return dataTypes;
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
	
	public String getISPYId() {
	  return ispyId;
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
	  sb.append(getISPYId()).append("\t");
	  sb.append(getTimepoint()).append("\t");
	  sb.append(getCoreType()).append("\t");
	  sb.append(getDataTypes()).append("\t");
	  sb.append(getSectionInfo()).append("\t");
	  sb.append(getCalgId()).append("\n");
		
	  return sb.toString();
	}

}
