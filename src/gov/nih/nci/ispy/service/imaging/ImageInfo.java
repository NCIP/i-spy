package gov.nih.nci.ispy.service.imaging;

public class ImageInfo {

	private String imageId;
	private String seriesId;
	
	public ImageInfo(String imageId, String seriesId) {
	  this.imageId = imageId;
	  this.seriesId = seriesId;
	}

	public String getImageId() {
		return imageId;
	}

	
	public String getSeriesId() {
		return seriesId;
	}

}
