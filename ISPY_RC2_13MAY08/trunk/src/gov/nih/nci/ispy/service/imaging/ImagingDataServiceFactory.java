package gov.nih.nci.ispy.service.imaging;

public class ImagingDataServiceFactory {
	
  
	
  public static ImagingDataService getImagingDataService() {
	  
	return ImagingFileBasedQueryService.getInstance();  
	  
  }
}
