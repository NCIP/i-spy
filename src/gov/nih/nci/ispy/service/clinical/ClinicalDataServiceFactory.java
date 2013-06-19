/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.clinical;


public class ClinicalDataServiceFactory {

	public ClinicalDataServiceFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static ClinicalDataService getInstance() {
	  //return ClinicalCGOMBasedQueryService.getInstance();
	  return ClinicalFileBasedQueryService.getInstance();
	}

}
