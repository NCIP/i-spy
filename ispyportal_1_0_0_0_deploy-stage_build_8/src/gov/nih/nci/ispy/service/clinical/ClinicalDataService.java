package gov.nih.nci.ispy.service.clinical;

import java.util.Set;

import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

/**
 * The ClinicalDataService should be implemented by all services
 * (fileBased and DBbased) that serve ISPY clinical data.
 * @author harrismic
 *
 */

public interface ClinicalDataService {

	public Set<String> getPatientDIDs(ISPYclinicalDataQueryDTO dto);
	
	public Set<PatientData> getClinicalData(ISPYclinicalDataQueryDTO dto);
	
}
