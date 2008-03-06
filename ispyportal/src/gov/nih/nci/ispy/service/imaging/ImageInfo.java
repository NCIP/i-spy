package gov.nih.nci.ispy.service.imaging;

public class ImageInfo {

	private String imageId;
	private String seriesId;
	private String studyId;
	
	public ImageInfo(String imageId, String seriesId, String studyId) {
	  this.imageId = imageId;
	  this.seriesId = seriesId;
	  this.studyId = studyId;
	}

	public String getImageId() {
		return imageId.trim();
	}

	public String getSeriesId() {
		return seriesId.trim();
	}
	
	public String getStudyId() {
	    return studyId.trim();
	}

}
