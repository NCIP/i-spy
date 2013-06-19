/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.List;

public interface ISPYPrincipalComponentAnalysisQueryDTO extends PrincipalComponentAnalysisQueryDTO {
  public List<TimepointType> getTimepoints();
  public void setTimepoints(List<TimepointType> timepoints);
}
