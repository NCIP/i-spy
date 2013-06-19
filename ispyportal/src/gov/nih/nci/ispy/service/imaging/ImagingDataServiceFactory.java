/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.imaging;

public class ImagingDataServiceFactory {
	
  
	
  public static ImagingDataService getImagingDataService() {
	  
	return ImagingFileBasedQueryService.getInstance();  
	  
  }
}
