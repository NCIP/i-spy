package gov.nih.nci.ispy.dto.query;

import java.util.List;

import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;import gov.nih.nci.ispy.service.clinical.TimepointType;
;

public interface ISPYHierarchicalClusteringQueryDTO extends HierarchicalClusteringQueryDTO {
	public void setTimepoints(List<TimepointType> timepoints);
	public List<TimepointType> getTimepoints();
}
