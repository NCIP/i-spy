/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.ihc;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LossOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.p53.P53FindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.caintegrator.studyQueryService.ihc.LossOfExpressionIHCFindingHandler;
import gov.nih.nci.caintegrator.studyQueryService.p53.P53FindingHandler;
import gov.nih.nci.ispy.service.annotation.SampleInfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class P53Service {
	
	 private static P53Service ourInstance = null;
	 
	 /**
	     * Singleton for the database based query service
	     * 
	     * @return the static instance of the query service
	     */
	 
	 public static P53Service getInstance(){
		 
		 if(ourInstance == null){
			 ourInstance = new P53Service();			 
		 }
		 
		 return ourInstance;
	 }

	 
	  /**
	     * accepts a P53 Criteria , retrieves findings from db. Assumes a tables mapped to appropriate
	     * object fields. 
	     * @param criteria
	     * @return theFindings
	     */ 
	    public Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> getFindings(P53FindingCriteria criteria){
	        P53FindingHandler theHandler = (P53FindingHandler) SpringContext.getBean("p53FindingsHandler");
	        Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> theFindings = theHandler.getP53Findings(criteria);
	        return theFindings;
	    }
	    
	    
	    /**
	     * Convenience method that takes sampleInfo, builds specimen criteria itself and fetches findings
	     * -KR
	     * @param sampleIds
	     * @return theFindings
	     */
	    public Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> getFindingsFromSampleInfo(Set<SampleInfo> sampleInfo){
	        SpecimenCriteria theSPCriteria = new SpecimenCriteria();
	        P53FindingCriteria criteria = new P53FindingCriteria();
	        Set<String> patientDids = new HashSet<String>();
	        Set<String> timepoints = new HashSet<String>();
	        for(SampleInfo info : sampleInfo){
	            if(info.getISPYId()!=null && info.getTimepoint()!=null){
	                patientDids.add(info.getISPYId());
	                timepoints.add(info.getTimepoint().toString());
	            }            
	        }
	        theSPCriteria.setPatientIdentifierCollection(patientDids);
	        theSPCriteria.setTimeCourseCollection(timepoints);
	        criteria.setSpecimenCriteria(theSPCriteria);        
	        Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> theFindings = getFindings(criteria);
	        return theFindings;
	     
	    }
	    
}
