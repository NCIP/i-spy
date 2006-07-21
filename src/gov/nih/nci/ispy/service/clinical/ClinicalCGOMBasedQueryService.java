package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.util.Set;
import java.util.Collections;

public class ClinicalCGOMBasedQueryService implements ClinicalDataService {

	private static ClinicalCGOMBasedQueryService instance = null;
	
	public ClinicalCGOMBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Set<String> getPatientDIDs(ISPYclinicalDataQueryDTO dto) {		
		//TODO need to implement this method
		return Collections.emptySet();
	}

	public Set<PatientData> getClinicalData(ISPYclinicalDataQueryDTO dto) {
		//TODO need to implement this method
		return Collections.emptySet();
	}


	public static ClinicalCGOMBasedQueryService getInstance() {
		if (instance == null) {
		    instance = new ClinicalCGOMBasedQueryService();
		  }
		  return instance;
	}

}
