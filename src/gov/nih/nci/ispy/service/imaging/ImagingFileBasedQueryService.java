package gov.nih.nci.ispy.service.imaging;

import gov.nih.nci.ispy.service.clinical.PatientData;

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
    
    public String buildImagingLink(PatientData pd){
        PatientImagingInfo info = getPatientImagingInfo(pd.getISPY_ID());
        String link = System.getProperty("gov.nih.nci.ispyportal.imaging.ncia_url");
        if(info!=null){
            ImageInfo pre = info.getPreImage();
            ImageInfo post = info.getPostImage();
            if(pre!=null && post!=null){
                link+="?source=ISPY&image1Label=Pre&image1TrialId=ISPY&image1PatientId=1.3.6.1.4.1.9328.50.1.0001&image1StudyInstanceUid=1.3.6.1.4.1.9328.50.1.2&image1SeriesInstanceUid=1.3.6.1.4.1.9328.50.1.3&image1ImageSopInstanceUid=1.3.6.1.4.1.9328.50.1.39408&image1dataName=Longest%20Diameter&image1dataValue=25&image1dataName=XXXX&image1dataValue=1111&image2Label=Post&image2TrialId=ISPY&image2PatientId=1.3.6.1.4.1.9328.50.1.0001&image2StudyInstanceUid=1.3.6.1.4.1.9328.50.1.4&image2SeriesInstanceUid=1.3.6.1.4.1.9328.50.1.5&image2ImageSopInstanceUid=1.3.6.1.4.1.9328.50.1.39408&image2dataName=Longest%20Diameter&image2dataValue=18&image2dataName=YYYY&image2dataValue=564";
            }            
        }
        else{
            link+="?source=ISPY&image1Label=Pre&image1TrialId=ISPY&image1PatientId=1.3.6.1.4.1.9328.50.1.0001&image1StudyInstanceUid=1.3.6.1.4.1.9328.50.1.2&image1SeriesInstanceUid=1.3.6.1.4.1.9328.50.1.3&image1ImageSopInstanceUid=1.3.6.1.4.1.9328.50.1.39408&image1dataName=Longest%20Diameter&image1dataValue=25&image1dataName=XXXX&image1dataValue=1111&image2Label=Post&image2TrialId=ISPY&image2PatientId=1.3.6.1.4.1.9328.50.1.0001&image2StudyInstanceUid=1.3.6.1.4.1.9328.50.1.4&image2SeriesInstanceUid=1.3.6.1.4.1.9328.50.1.5&image2ImageSopInstanceUid=1.3.6.1.4.1.9328.50.1.39408&image2dataName=Longest%20Diameter&image2dataValue=18&image2dataName=YYYY&image2dataValue=564";
        }
        
        return link;
    }

}
