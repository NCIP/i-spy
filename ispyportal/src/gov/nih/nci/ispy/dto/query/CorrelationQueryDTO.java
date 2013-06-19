/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;

public interface CorrelationQueryDTO extends QueryDTO {


	public void setQueryName(String name);

	public String getQueryName();

}
