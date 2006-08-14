package gov.nih.nci.ispy.service.imaging;

public class PatientImagingInfo {

	private ImageInfo preImage = null;
	private ImageInfo postImage = null;
	private String patientUID = null;
	
	public PatientImagingInfo(String patientUID) {
	  this.patientUID = patientUID;
	}

	public ImageInfo getPostImage() {
		return postImage;
	}

	public void setPostImage(ImageInfo postImage) {
		this.postImage = postImage;
	}

	public ImageInfo getPreImage() {
		return preImage;
	}

	public void setPreImage(ImageInfo preImage) {
		this.preImage = preImage;
	}

	public String getPatientUID() {
		return patientUID;
	}

}
