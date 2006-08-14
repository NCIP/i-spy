package gov.nih.nci.ispy.service.imaging;

public class ImagingFileBasedQueryService implements ImagingDataService {

	private static ImagingFileBasedQueryService instance = null;
	
	public ImagingFileBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static ImagingDataService getInstance() {
		if (instance == null) {
		   instance = new ImagingFileBasedQueryService();
		}
		
		return instance;
	}

	public boolean hasImagingData(String patientUID) {
		
		//dummy code
		if (patientUID.equals("1001")||patientUID.equals("1002")||patientUID.equals("1003")) {
		  return true;
		}
		return false;
	}

	public PatientImagingInfo getPatientImagingInfo(String patientUID) {
		// TODO Auto-generated method stub
		return null;
	}

}
