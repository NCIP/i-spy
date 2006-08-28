package gov.nih.nci.ispy.service.imaging;

import gov.nih.nci.ispy.service.clinical.PatientData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ImagingFileBasedQueryService implements ImagingDataService {

	private static ImagingFileBasedQueryService instance = null;
	
	private static Logger logger = Logger.getLogger(ImagingFileBasedQueryService.class);
	
	private Map<String,PatientImagingInfo> patientImageMap = new HashMap<String,PatientImagingInfo>();
	
	public ImagingFileBasedQueryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static ImagingDataService getInstance() {
		
		String imageMappingFile = System.getProperty("gov.nih.nci.ispyportal.data_directory") + System.getProperty("gov.nih.nci.ispyportal.image_mapping_file");
		int numMappings = 0;
		if (instance == null) {
		   instance = new ImagingFileBasedQueryService();
		   numMappings = instance.loadMappingData(imageMappingFile);
		   
		   logger.info("Successfully loaded numMappings=" + numMappings);
		}
		
		return instance;
	}

	private int loadMappingData(String imageMappingFile) {
		int numMappings = 0;
		
		try {
		  
		  ImageInfo preImageInfo = null;
		  ImageInfo postImageInfo = null;
		  PatientImagingInfo patientImageInfo = null;
		  BufferedReader in = new BufferedReader(new FileReader(imageMappingFile));
		  String line = null;
		  String[] tokens = null;
		  String ispy_id, acrin_id;
		  
		  while ((line = in.readLine())!= null) {
		    tokens = line.split("\t", -2);
		    acrin_id = tokens[0];
		    ispy_id = tokens[1];
		    patientImageInfo = new PatientImagingInfo(ispy_id);
		    patientImageInfo.setAcrinPatientId(acrin_id);
		    		    
		    preImageInfo = new ImageInfo(tokens[2], tokens[3],tokens[4]);
		    postImageInfo = new ImageInfo(tokens[5], tokens[6],tokens[7]);
		    
		    patientImageInfo.setPreImage(preImageInfo);
		    patientImageInfo.setPostImage(postImageInfo);
		    
		    patientImageMap.put(patientImageInfo.getISPY_id(), patientImageInfo);
		    numMappings++;
		  }
			
		}
		catch (IOException ex) {		 
		  logger.error("Error loading image mapping file: " + imageMappingFile);
		  return -1;
		}
		
		return numMappings;
		
	}


	public boolean hasImagingData(String patientUID) {
		
		return patientImageMap.containsKey(patientUID);
	}

	public PatientImagingInfo getPatientImagingInfo(String patientUID) {
		return patientImageMap.get(patientUID);
	}
    
    public String buildImagingLink(PatientData pd){
        PatientImagingInfo info = getPatientImagingInfo(pd.getISPY_ID());
        String link = System.getProperty("gov.nih.nci.ispyportal.imaging.ncia_url");
        if(info!=null){
            ImageInfo pre = info.getPreImage();
            ImageInfo post = info.getPostImage();
            //remove when we put in real data
            String test = "test";
            int data = 99;
            String acrinId = info.getAcrinPatientId();
            if(pre!=null && post!=null){
                link+="?source=ISPY&image1Label=Pre&image1TrialId=ISPY&image1PatientId=" + acrinId + "&image1StudyInstanceUid=" + pre.getStudyId() + "&image1SeriesInstanceUid=" + pre.getSeriesId() + "&image1ImageSopInstanceUid=" + pre.getImageId() + "&image1dataName=Longest%20Diameter&image1dataValue=" + 25 + "&image1dataName=" + test + "&image1dataValue=" + data + "&image2Label=Post&image2TrialId=ISPY&image2PatientId=" + acrinId + "&image2StudyInstanceUid=" + post.getStudyId() + "&image2SeriesInstanceUid=" + post.getSeriesId() + "&image2ImageSopInstanceUid=" + post.getImageId() + "&image2dataName=Longest%20Diameter&image2dataValue=" + 18 + "&image2dataName=" + test + "&image2dataValue=" + data;
            }            
        }
        else{
            link="";
        }
        
        return link;
    }

}
