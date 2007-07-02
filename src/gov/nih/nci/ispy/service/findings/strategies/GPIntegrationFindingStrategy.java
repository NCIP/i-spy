package gov.nih.nci.ispy.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
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
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.dto.query.GpIntegrationQueryDTO;
import gov.nih.nci.ispy.dto.query.ISPYGPIntegrationQueryDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * @author sahnih, harrismic
 *
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
	/*	
		Set<String> labtrackIds = new HashSet<String>();
        ClinicalDataService qs = ClinicalDataServiceFactory.getInstance();
		IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
		
		for (ClinicalQueryDTO cq : clinicalQueries) {
           List<String> patientDIDs;
           Set<SampleInfo> samples = null;
           SampleGroup sg = new SampleGroup(cq.getQueryName());
          
          if(cq.getClass().isInstance(new ISPYclinicalDataQueryDTO())){
              ISPYclinicalDataQueryDTO icq = (ISPYclinicalDataQueryDTO) cq;
    		  patientDIDs = new ArrayList<String>(qs.getPatientDIDs(icq));
              
              //get the labtrack ids for the patients               
              if (myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()== ArrayPlatformType.AGILENT) {              
                samples = idMapper.getSamplesForDataTypeAndTimepoints(patientDIDs, ISPYDataType.AGILENT,icq.getTimepointValues());
              }
              else if (myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()== ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                samples = idMapper.getSamplesForDataTypeAndTimepoints(patientDIDs, ISPYDataType.CDNA, icq.getTimepointValues());
              }
              if (icq.isBaseline()) {
                classComparisonRequest.setBaselineGroup(sg);
              }
              else {
                classComparisonRequest.setGroup1(sg);
              }
          }
          else if(cq.getClass().isInstance(new PatientUserListQueryDTO())){              
              PatientUserListQueryDTO pq = (PatientUserListQueryDTO) cq;
              patientDIDs = pq.getPatientDIDs();             
              //get the labtrack ids for the patients             
              
              if (myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()== ArrayPlatformType.AGILENT) {              
                samples = idMapper.getSamplesForDataTypeAndTimepoints(patientDIDs, ISPYDataType.AGILENT,pq.getTimepointValues());
              }
              else if (myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()== ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                samples = idMapper.getSamplesForDataTypeAndTimepoints(patientDIDs, ISPYDataType.CDNA, pq.getTimepointValues());
              }
              if (pq.isBaseline()) {
                classComparisonRequest.setBaselineGroup(sg);
              }
              else {
                classComparisonRequest.setGroup1(sg);
              }
          }
		  
		  
		  */
		  /*
		  List<String> sampleIds = new ArrayList<String>();
		  
		  if (samples != null) {		  
			  for (SampleInfo si : samples) {
			    sampleIds.add(si.getLabtrackId());    
			  }	
		  }
		  sg.addAll(sampleIds);
			*/  

		//}
		
//		if(clinicalQueries != null){
//		CompoundQuery compoundQuery;
//			    for (ClinicalDataQuery clinicalDataQuery: clinicalQueries){
//			    Resultant resultant;
//				try {
//					compoundQuery = new CompoundQuery(clinicalDataQuery);
//					compoundQuery.setAssociatedView(ViewFactory
//		                .newView(ViewType.CLINICAL_VIEW));
//					InstitutionCriteria institutionCriteria = new InstitutionCriteria();
//					institutionCriteria.setInstitutions(myQueryDTO.getInstitutionDEs());
//					compoundQuery.setInstitutionCriteria( institutionCriteria);
//					resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
//		  		}
//		  		catch (Throwable t)	{
//		  			logger.error("Error Executing the query/n"+ t.getMessage());
//		  			throw new FindingsQueryException("Error executing clinical query/n"+t.getMessage());
//		  		}
//
//				if(resultant != null) {      
//			 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
//			 		Viewable view = resultant.getAssociatedView();
//			 		if(resultsContainer != null)	{
//			 			if(view instanceof ClinicalSampleView){
//			 				try {
//			 					//1. Get the sample Ids from the return Clinical query
//								Collection<SampleIDDE> sampleIDDEs = StrategyHelper.extractSampleIDDEs(resultsContainer);
//								//2. validate samples so that GE data exsists for these samples
//								Collection<SampleIDDE> validSampleIDDEs = DataValidator.validateSampleIds(sampleIDDEs);
//								//3. Extracts sampleIds as Strings
//								Collection<String> sampleIDs = StrategyHelper.extractSamples(validSampleIDDEs);
//								if(sampleIDs != null){
//									//3.1 add them to SampleGroup
//									SampleGroup sampleGroup = new SampleGroup(clinicalDataQuery.getQueryName(),validSampleIDDEs.size());
//									sampleGroup.addAll(sampleIDs);
//									sampleGroups.add(sampleGroup);
////									//3.2 Find out any samples that were not processed  
//									Set<SampleIDDE> set = new HashSet<SampleIDDE>();
//									set.addAll(sampleIDDEs); //samples from the original query
//									//3.3 Remove all samples that are validated	
//									set.removeAll(validSampleIDDEs);
//									samplesNotFound.addAll(set);									
//							}
//							} catch (OperationNotSupportedException e) {
//								logger.error(e.getMessage());
//					  			throw new FindingsQueryException(e.getMessage());
//							} catch (Exception e) {
//								e.printStackTrace();
//								logger.error(e.getMessage());
//					  			throw new FindingsQueryException(e.getMessage());
//							}
//
//		 				}	
//			 		}
//				}
//			 }
//		}
//		if(samplesNotFound != null && samplesNotFound.size() > 0){
//			setSamplesNotFound(samplesNotFound);
//		}
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



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof ClassComparisonQueryDTO){
			ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)queryDTO;
			try {
				ValidationUtility.checkForNull(classComparisonQueryDTO.getInstitutionDEs());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getArrayPlatformDE()) ;
				ValidationUtility.checkForNull(classComparisonQueryDTO.getComparisonGroups());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getExprFoldChangeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getQueryName());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getStatisticalSignificanceDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getStatisticTypeDE());
					switch (classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject()){
						case NONE:
							if(classComparisonQueryDTO.getStatisticalSignificanceDE().getStatisticType() != StatisticalSignificanceType.pValue)
								throw(new ValidationException("When multiGroupComparisonAdjustmentTypeDE is NONE, Statistical Type should be pValue"));
							break;
						case FWER:
						case FDR:
							if(classComparisonQueryDTO.getStatisticalSignificanceDE().getStatisticType() != StatisticalSignificanceType.adjustedpValue)
								throw(new ValidationException("When multiGroupComparisonAdjustmentTypeDE is FWER or FDR, Statistical Type should be adjusted pValue"));
							break;
						default:
								throw(new ValidationException("multiGroupComparisonAdjustmentTypeDE is does not match any options"));
					}
					_valid = true;
				} catch (ValidationException ex) {
					logger.error(ex.getMessage());
					throw ex;
				}
		}		
		return _valid;
	}
//	private void setSamplesNotFound(Collection<SampleIDDE>  samplesNotFound ){
//		classComparisonFinding = (ClassComparisonFinding) cacheManager.getSessionFinding(getSessionId(), getTaskId());
//		if(classComparisonFinding != null){
//			classComparisonFinding.setSamplesNotFound(samplesNotFound);
//			
//		}
//		cacheManager.addToSessionCache(getSessionId(), getTaskId(), classComparisonFinding);
//
//	}

}