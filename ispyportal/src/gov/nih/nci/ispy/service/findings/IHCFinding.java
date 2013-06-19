/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings;

import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.IHCqueryDTO;

public class IHCFinding extends Finding  {

	
	public IHCFinding(String sessionId, String taskId, IHCqueryDTO dto) {
		super(sessionId, taskId, FindingStatus.Completed);
	}

	
}
