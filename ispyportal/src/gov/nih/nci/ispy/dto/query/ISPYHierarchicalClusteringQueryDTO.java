package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.List;


public interface ISPYHierarchicalClusteringQueryDTO extends HierarchicalClusteringQueryDTO {
	public void setTimepoints(List<TimepointType> timepoints);
	public List<TimepointType> getTimepoints();
}
