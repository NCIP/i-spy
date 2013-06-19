package gov.nih.nci.ispy.web.factory;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.service.findings.FindingsFactory;
import gov.nih.nci.ispy.dto.query.ClassComparisonQueryDTOImpl;
import gov.nih.nci.ispy.dto.query.HierarchicalClusteringQueryDTOImpl;
import gov.nih.nci.ispy.dto.query.ISPYGPIntegrationQueryDTO;
import gov.nih.nci.ispy.dto.query.PrincipalComponentAnalysisQueryDTOImpl;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;





public class ApplicationFactory{

	public static QueryDTO newQueryDTO(QueryType queryType) {
		if (queryType == QueryType.CLASS_COMPARISON_QUERY) {              
            return new ClassComparisonQueryDTOImpl();
        }else if (queryType == QueryType.PCA_QUERY) {              
            return new PrincipalComponentAnalysisQueryDTOImpl();
        }else if (queryType == QueryType.HC_QUERY) {              
            return new HierarchicalClusteringQueryDTOImpl();
        }else {
        	//return null;
        	return new ISPYGPIntegrationQueryDTO();
        }
	}
	public static PresentationTierCache getPresentationTierCache() {
		return CacheFactory.getPresentationTierCache();
	}
	
	public static BusinessTierCache getBusinessTierCache() {
		return CacheFactory.getBusinessTierCache();
	}
	
	public static FindingsFactory getFindingsFactory() {
		return new ISPYFindingsFactory();
	}

}
