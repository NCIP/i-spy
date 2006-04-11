package gov.nih.nci.ispy.service.annotation;

import java.util.ArrayList;
import java.util.List;

public class IdMapperEntry {

	private String registrationId;
	
	private List<String> associatedIds = null;
	
	
	/**
	 * 
	 * @param registrationId the participant registration id
	 */
	public IdMapperEntry(String registrationId, int size) {
	  this.registrationId = registrationId;	
	  this.associatedIds = new ArrayList<String>(size);
	}
	
	/**
	 * Insert an id at a given position in the list of associated ids.
	 * @param index
	 * @param id
	 */
	public void addAssociatedId(String id) {
       associatedIds.add(id);
	}
	
	public String toString() {
	  StringBuffer sb = new StringBuffer();
	  IdMapperFileBasedService srv = IdMapperFileBasedService.getInstance();
	  
	  sb.append("RegistrationId : ").append(registrationId).append("\n");
	  for (String id: associatedIds) {
		  sb.append(id).append("\t");
	  }
	  sb.append("\n");
	  return sb.toString();
	}

	public List<String> getAssociatedIds() {
		return associatedIds;
	}

	public String getRegistrationId() {
		return registrationId;
	}

}
