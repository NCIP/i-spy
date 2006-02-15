package gov.nih.nci.ispy.service.clinical;

import java.util.List;
import java.util.Collection;
import java.util.Collections;

public class ClinicalDataFileBasedQueryService {

	public ClinicalDataFileBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method returns all labtrack ids for samples
	 * taken at the specified timepoint.
	 * @param timepoint
	 */
	public Collection<String> getLabtrackIdsForTimepoint(int timepoint) {
		return Collections.EMPTY_SET;
	}
	
	public Collection<String> getLabtrackIdsForDiseaseStage(DiseaseStageType stage) {
	   return Collections.EMPTY_SET;
	}
	
	public Collection<String> getLabtrackIdsForPatientResponse(PatientResponseType responseType) {
	  return Collections.EMPTY_SET;
	}
	
	public Collection<String> getLabtrackIdsForERstatus(ERstatusType erStatus) {
	  return Collections.EMPTY_SET;
	}
	
	public Collection<String> getLabtrackIdsForPRstatus(PRstatusType prStatus) {
	  return Collections.EMPTY_SET;
	}
	
	public Collection<String> getLabtrackIdsForHER2status(HER2statusType her2Status) {
	  return Collections.EMPTY_SET;
	}
	
	public ClinicalData getClinicalDataForLabtrackId(String labtrackId) {
		return null;
	}
	
	/**
	 * This method will return clinical data for the specified list of 
	 * labtrack ids.  In addition the order will be preserved so that the list of 
	 * clinical data objects returned will match the order of the labtrackIds pass in the
	 * input.
	 * 
	 * @param labtrackIds
	 * @return
	 */
	public List<ClinicalData> getClinicalDataForLabtrackIds(List<String> labtrackIds) {
		return Collections.EMPTY_LIST;
	}

}
