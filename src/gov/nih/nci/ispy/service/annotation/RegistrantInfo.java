package gov.nih.nci.ispy.service.annotation;

import java.util.ArrayList;
import java.util.List;

public class RegistrantInfo {

	private String registrationId;
	
	private List<SampleInfo> associatedSamples = new ArrayList<SampleInfo>();
	
	
	/**
	 * 
	 * @param registrationId the participant registration id
	 */
	public RegistrantInfo(String registrationId) {
	  this.registrationId = registrationId;	
	}
	
	/**
	 * Insert an id at a given position in the list of associated ids.
	 * @param index
	 * @param id
	 */
	public void addSample(SampleInfo sample) {
       associatedSamples.add(sample);
	}
	
	public List<SampleInfo> getSamplesForDataTypeAndTimepoint(ISPYDataType dataType, int timepoint) {
	  List<SampleInfo> retList = new ArrayList<SampleInfo>();
	  
	  for (SampleInfo sample : associatedSamples) {
		 if ((sample.getDataType()==dataType)&&(sample.getTimepoint()==timepoint)) {
		   retList.add(sample);
		 }
	  }
	  
	  return retList;
	}
	
	public String toString() {
	  StringBuffer sb = new StringBuffer();
	  
	  sb.append("RegistrationId : ").append(registrationId).append("\n");
	  for (SampleInfo sample: associatedSamples) {
		  sb.append("   ").append(sample);
	  }
	  sb.append("\n");
	  return sb.toString();
	}

	public List<SampleInfo> getAssociatedSamples() {
		return associatedSamples;
	}

	public String getRegistrationId() {
		return registrationId;
	}

}
