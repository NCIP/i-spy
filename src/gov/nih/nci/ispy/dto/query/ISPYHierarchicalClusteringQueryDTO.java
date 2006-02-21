package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;;

public interface ISPYHierarchicalClusteringQueryDTO extends HierarchicalClusteringQueryDTO {
	public void setTimepoint(int timepoint);
	public int getTimepoint();
}
