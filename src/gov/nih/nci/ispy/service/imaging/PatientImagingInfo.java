/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.imaging;

public class PatientImagingInfo {

	private ImageInfo preImage = null;
	private ImageInfo postImage = null;
	private String ispy_id = null;
	private String acrinPatientId = null;
	
	public PatientImagingInfo(String ispy_id) {
	  this.ispy_id = ispy_id;
	}
	


	public String getAcrinPatientId() {
		return acrinPatientId;
	}



	public void setAcrinPatientId(String acrinPatientId) {
		this.acrinPatientId = acrinPatientId;
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

	public String getISPY_id() {
		return ispy_id;
	}

}
