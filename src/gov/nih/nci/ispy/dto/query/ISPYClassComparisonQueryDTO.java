package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalDataSpecifier;
import gov.nih.nci.ispy.service.clinical.TimepointType;

import java.util.List;

public interface ISPYClassComparisonQueryDTO extends ClassComparisonQueryDTO{
	
	public ClinicalDataSpecifier getBaselineGrpSpecifier(); 
	
	public void setBaselineGrpSpecifier(ClinicalDataSpecifier baselineGrpSpecifier);
	
	public ClinicalDataSpecifier getComparisonGrpSpecifier(); 
	
	public void setComparisonGrpSpecifier(ClinicalDataSpecifier comparisonGrpSpecifier);
	
}
