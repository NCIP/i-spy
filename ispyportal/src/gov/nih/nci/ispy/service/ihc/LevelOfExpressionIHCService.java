package gov.nih.nci.ispy.service.ihc;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.caintegrator.studyQueryService.ihc.LevelOfExpressionIHCFindingHandler;
import gov.nih.nci.ispy.service.annotation.SampleInfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class LevelOfExpressionIHCService {
    private static LevelOfExpressionIHCService ourInstance = null;
   
    
    /**
     * Singleton for the database based query service
     * 
     * @return the static instance of the query service
     */
    public static LevelOfExpressionIHCService getInstance()
    {        
        if (ourInstance == null)
        {
            ourInstance = new LevelOfExpressionIHCService();
        }
        return ourInstance;
    }
    
    /**
     * accepts a LOE Criteria , retrieves findings from db. Assumes a tables mapped to appropriate
     * object fields. -KR
     * @param criteria
     * @return theFindings
     */
    public Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> getFindings(LevelOfExpressionIHCFindingCriteria criteria){
        LevelOfExpressionIHCFindingHandler theHandler = (LevelOfExpressionIHCFindingHandler) SpringContext.getBean("levelOfExpressionFindingsHandler");
        Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> theFindings = theHandler.getLevelExpFindings(criteria);
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
        LevelOfExpressionIHCFindingCriteria criteria = new LevelOfExpressionIHCFindingCriteria();
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
