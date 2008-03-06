package gov.nih.nci.ispy.service.imaging;

public interface ImagingDataService {

	public boolean hasImagingData(String patientUID);
	
	public PatientImagingInfo getPatientImagingInfo(String patientUID);
	
}
