package gov.nih.nci.ispy.test;

import gov.nih.nci.caintegrator.application.bean.LevelOfExpressionIHCFindingReportBean;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.application.util.PatientComparator;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LevelOfExpressionIHCFinding;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.ihc.LevelOfExpressionIHCFindingHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class LOETest extends
        AbstractDependencyInjectionSpringContextTests {

    private HibernateTemplate hibernateTemplate;
    private LevelOfExpressionIHCFindingHandler levelOfExpressionFindingsHandler;

    /**
     * @return Returns the levelOfExpressionFindingsHandler.
     */
    public LevelOfExpressionIHCFindingHandler getLevelOfExpressionFindingsHandler() {
        return levelOfExpressionFindingsHandler;
    }

    /**
     * @param levelOfExpressionFindingsHandler The levelOfExpressionFindingsHandler to set.
     */
    public void setLevelOfExpressionFindingsHandler(
            LevelOfExpressionIHCFindingHandler levelOfExpressionFindingsHandler) {
        this.levelOfExpressionFindingsHandler = levelOfExpressionFindingsHandler;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public String[] getConfigLocations() {
        return new String[] {
                "file:C:/dev/ispy/test/applicationContext-junit.xml",
                "file:C:/dev/caintegrator-spec/src/applicationContext-services.xml"};
    }

    public void testStudyParticipant() {

                LevelOfExpressionIHCFindingCriteria criteria = new LevelOfExpressionIHCFindingCriteria();
                criteria.setPercentPositiveRangeMin(new Integer(0));
                criteria.setPercentPositiveRangeMax(new Integer(50));
                criteria.setQueryName("test");
                Collection<LevelOfExpressionIHCFinding> theFindings = levelOfExpressionFindingsHandler.getLevelExpFindings(criteria);
//                ArrayList<LevelOfExpressionIHCFindingReportBean> results = new ArrayList<LevelOfExpressionIHCFindingReportBean>();
//                
//                for(LevelOfExpressionIHCFinding loef : theFindings)  {
//                    LevelOfExpressionIHCFindingReportBean reportBean = new LevelOfExpressionIHCFindingReportBean(loef);
//                    results.add(reportBean);
//                }
//                
//                PatientComparator p = new PatientComparator();
//                Collections.sort(results, p);
//                
//                for(int i=0;i<10;i++){
//                    
//                    System.out.println("Patient did: " + results.get(i).getPatientDID() + 
//                            " biomarker: " + results.get(i).getBiomarkerName() + 
//                            " timepoint: " + results.get(i).getTimepoint() + 
//                            " % positive: " + results.get(i).getPercentPositive());
//                }
                

    }
   
}
