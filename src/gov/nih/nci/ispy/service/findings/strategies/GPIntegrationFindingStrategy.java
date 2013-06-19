package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.dto.query.PatientUserListQueryDTO;
import gov.nih.nci.ispy.service.annotation.ISPYDataType;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.dto.query.GpIntegrationQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYGPIntegrationQueryDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * @author sahnih, harrismic
 *
 */




public class GPIntegrationFindingStrategy extends SessionBasedFindingStrategy {
	private static Logger logger = Logger.getLogger(ClassComparisonFindingStrategy.class);	
	@SuppressWarnings("unused")
	private GpIntegrationQueryDTO myQueryDTO;
	
	public GPIntegrationFindingStrategy(String sessionId, String taskId, GpIntegrationQueryDTO queryDTO) throws ValidationException {
		super(sessionId, taskId);
		myQueryDTO = queryDTO;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * This method validates that 2 groups were passed for TTest and Wincoin Test
	 */
	public boolean createQuery() throws FindingsQueryException {
		return true;	
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		ISPYGPIntegrationQueryDTO ispyDTO = (ISPYGPIntegrationQueryDTO)myQueryDTO;
		
		Set<String> keySet = ispyDTO.getPatientLists().keySet();
		SampleGroup[] sampleGroups = new SampleGroup[keySet.size()];

		ArrayPlatformDE arrayPlatformDE = ispyDTO.getArrayPlatformDE();
        ISPYDataType dataType = null;
        if (arrayPlatformDE.getValueObjectAsArrayPlatformType()== ArrayPlatformType.AGILENT){
        	dataType = ISPYDataType.AGILENT;
        }
        else if (arrayPlatformDE.getValueObjectAsArrayPlatformType()== ArrayPlatformType.CDNA_ARRAY_PLATFORM) {
        	dataType = ISPYDataType.CDNA;
        }
        else {
        	logger.error("Unrecognized ArrayPlatform type for ISPY application");
        }
        int count = 0;
		for (String key : keySet){
			UserList userlist = ispyDTO.getPatientLists().get(key);
			Set<TimepointType> timePoints = ispyDTO.getTimepointLists().get(key);
			SampleGroup sg = getSampleGroupForPatientList(userlist, dataType, timePoints, key);
			sampleGroups[count++] = sg;
		}
		ispyDTO.setSampleGroups(sampleGroups);
		
		Collection<GeneIdentifierDE> geneIdentifierDECollection = ispyDTO.getGeneIdentifierDEs();
		if (geneIdentifierDECollection != null && !geneIdentifierDECollection.isEmpty()){
			Collection<String> reporters = new HashSet<String>();
			Collection<String> genes = new ArrayList<String>();
			for (Iterator it = geneIdentifierDECollection.iterator(); it.hasNext();){
				GeneIdentifierDE de = (GeneIdentifierDE)it.next();
				genes.add(de.getValueObject());
			}
			GeneExprAnnotationService gs = GeneExprAnnotationServiceFactory.getInstance();
			reporters = gs.getReporterNamesForGeneSymbols(genes,arrayPlatformDE.getValueObjectAsArrayPlatformType());
			if (reporters != null && !reporters.isEmpty()){
				ReporterGroup reporterGroup = new ReporterGroup(ispyDTO.getReportersName());
				for (String reporter : reporters){
					reporterGroup.add(reporter);
				}
				ispyDTO.setReporterGroup(reporterGroup);
			}
		}
	    return true;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#analyzeResultSet()
	 */
	public boolean analyzeResultSet() throws FindingsAnalysisException {
		return true;
	}

	public Finding getFinding() {
		return null;
	}

	private SampleGroup getSampleGroupForPatientList(UserList patientList, ISPYDataType dataType,
			Set<TimepointType> timePoints, String key) {
		 IdMapperFileBasedService im = IdMapperFileBasedService.getInstance();
		 List<RegistrantInfo> infoList = im.getMapperEntriesForIds(patientList.getList());
		 SampleGroup group = new SampleGroup(key);
		 Set<SampleInfo> infoSet;
		 for (RegistrantInfo info : infoList) {
		   infoSet = info.getSamplesForDataTypeAndTimepoints(dataType, timePoints);
		   for (SampleInfo sampleInfo : infoSet) {
		     group.add(sampleInfo.getLabtrackId());
		   }
		 }
		 return group;		 
	}

	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof ISPYGPIntegrationQueryDTO){
			ISPYGPIntegrationQueryDTO ispyDTO = (ISPYGPIntegrationQueryDTO)queryDTO;
		}		
		return _valid;
	}
}
