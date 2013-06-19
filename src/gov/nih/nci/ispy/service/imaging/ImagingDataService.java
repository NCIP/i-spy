/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.imaging;

public interface ImagingDataService {

	public boolean hasImagingData(String patientUID);
	
	public PatientImagingInfo getPatientImagingInfo(String patientUID);
	
}
