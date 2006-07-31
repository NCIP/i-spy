package gov.nih.nci.ispy.service.ihc;


import gov.nih.nci.ispy.dto.query.IHCqueryDTO;

import java.util.Set;

public interface IHCDataService {

	public Set<String> getPatientDIDs(IHCqueryDTO dto);
	
	public Set<IHCData> getIHCData(IHCqueryDTO dto);
	
	public Set<String> getBiomarkers();
}
