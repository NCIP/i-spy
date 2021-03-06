/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;


public class P53Finding extends Finding {
	
	public P53Finding(String sessionId, String taskId) {
		super(sessionId, taskId, FindingStatus.Completed);
	}


}
