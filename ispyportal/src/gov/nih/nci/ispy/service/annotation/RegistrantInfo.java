/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.annotation;

import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	public List<SampleInfo> getSamplesForDataTypeAndTimepoint(ISPYDataType dataType, TimepointType timepoint) {
	  List<SampleInfo> retList = new ArrayList<SampleInfo>();
	  
	  for (SampleInfo sample : associatedSamples) {
		 if ((sample.getDataTypes().contains(dataType))&&(sample.getTimepoint()==timepoint)) {
		   retList.add(sample);
		 }
	  }
	  
	  return retList;
	}
	
	public Set<SampleInfo> getSamplesForDataTypeAndTimepoints(ISPYDataType dataType, Set<TimepointType> timepoints) {
	  	Set<SampleInfo> retSet = new HashSet<SampleInfo>();
	  	
	  	for (SampleInfo sample : associatedSamples) {
	  	  if ((sample.getDataTypes().contains(dataType))&&(timepoints.contains(sample.getTimepoint()))) {
	  		 retSet.add(sample);
	  	  }
	  	}
	  	
	  	return retSet;
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
	
	/**
	 * Return the sample info object associated with the specified labtrack id.
	 * @param labtrackId
	 * @return
	 */
	public SampleInfo getSample(String labtrackId) {
	  for (SampleInfo si : associatedSamples) {
	     if (si.getLabtrackId().equals(labtrackId)) {
	       return si;
	     }
	  }
	  return null;
	}

	public String getRegistrationId() {
		return registrationId;
	}

}
