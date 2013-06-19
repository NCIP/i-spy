/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;


public abstract class IHCFindingStrategy extends SessionBasedFindingStrategy {

	private QueryDTO queryDTO;
	
	public IHCFindingStrategy(String sessionId, String taskId, QueryDTO queryDTO )  throws ValidationException {
		super(sessionId, taskId);
		this.queryDTO = queryDTO;
	}

	public QueryDTO getQueryDTO() {
		return queryDTO;
	}

	public void setQueryDTO(QueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}

}
