package gov.nih.nci.ispy.service.clinical;


public class ClinicalDataServiceFactory {

	public ClinicalDataServiceFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static ClinicalDataService getInstance() {
	  //return ClinicalCGOMBasedQueryService.getInstance();
	  return ClinicalFileBasedQueryService.getInstance();
	}

}
