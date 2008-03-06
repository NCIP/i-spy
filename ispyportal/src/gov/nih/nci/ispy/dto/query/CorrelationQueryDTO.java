package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;

public interface CorrelationQueryDTO extends QueryDTO {


	public void setQueryName(String name);

	public String getQueryName();

}
