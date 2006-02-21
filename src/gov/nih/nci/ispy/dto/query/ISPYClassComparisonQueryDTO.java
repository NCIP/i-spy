package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.ispy.service.clinical.TimepointType;

import java.util.List;

public interface ISPYClassComparisonQueryDTO extends ClassComparisonQueryDTO{
  public List<TimepointType> getBaselineTimepoints();
  public void setBaselineTimepoints(List<TimepointType> baselineTimepoints);
  
  public List<TimepointType> getComparisonGroupTimepoints();
  public void setComparisonGroupTimepoints(List<TimepointType> comparisonGrpTimepoints);
}
