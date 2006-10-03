package gov.nih.nci.ispy.dto.query;

import java.util.List;

import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

public interface ISPYPrincipalComponentAnalysisQueryDTO extends PrincipalComponentAnalysisQueryDTO {
  public List<TimepointType> getTimepoints();
  public void setTimepoints(List<TimepointType> timepoints);
}
