package gov.nih.nci.ispy.service.ihc;



import gov.nih.nci.caintegrator.dto.query.IHCqueryDTO;

import java.util.HashSet;
import java.util.Set;

public class IHCFileBasedQueryService implements IHCDataService {

	private static IHCFileBasedQueryService instance = null;
	
	public IHCFileBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<String> getPatientDIDs(IHCqueryDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<IHCData> getIHCData(IHCqueryDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Set<String> getBiomarkers() {
	  Set<String> biomarkers = new HashSet<String>();
	  
	  biomarkers.add("BCL2");
	  biomarkers.add("EGFR");
	  biomarkers.add("FAK");
	  biomarkers.add("HER2");
	  biomarkers.add("Ki-67");
	  biomarkers.add("P53");
	  biomarkers.add("CCND1");
	  biomarkers.add("P27");
	  return biomarkers;
	}

	public static IHCFileBasedQueryService getInstance() {
		if (instance == null) {
		  instance = new IHCFileBasedQueryService();
		}
		return instance;
	}

	
}
