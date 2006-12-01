package gov.nih.nci.ispy.service.ihc.test;

import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LevelOfExpressionIHCFinding;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.protein.ProteinBiomarkerCriteia;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.caintegrator.studyQueryService.test.ihc.IHCLossTest;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.ihc.LevelOfExpressionIHCService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IHCServiceTest extends TestCase {
    
    private LevelOfExpressionIHCFindingCriteria inCriteria;
    private ProteinBiomarkerCriteia protCriteria;
    private SpecimenCriteria spCriteria;
    private Set<SampleInfo> siSet = new HashSet<SampleInfo>();
    
    public void setUp() throws Exception{
        inCriteria = new LevelOfExpressionIHCFindingCriteria();
        protCriteria = new ProteinBiomarkerCriteia();
        spCriteria = new SpecimenCriteria();        
        }
     
    public static Test suite() {
         TestSuite suit =  new TestSuite();          
         suit.addTest(new TestSuite(IHCServiceTest.class));
         return suit;
       }
    public void testIHCSearch() {
        setUpIHCLevelAttributesCriteria();       
        executeSearch();
    }  
    
   
    private void setUpIHCLevelAttributesCriteria() {
        inCriteria = new LevelOfExpressionIHCFindingCriteria();
        Set<String> biomarkers = new HashSet<String>();
        SampleInfo info = new SampleInfo("1025",TimepointType.T3);
        SampleInfo info1 = new SampleInfo("1026",TimepointType.T1);
        SampleInfo info2 = new SampleInfo("1036",TimepointType.T2);        
        siSet.add(info);
        siSet.add(info1);
        siSet.add(info2);
        
    }
    
    private  Collection executeSearch() {
         
          try {
              System.setProperty("gov.nih.nci.caintegrator.configFile","C:/devtools/jboss/jboss-4.0.4.GA/server/default/conf/caIntegratorConfig.xml");
              LevelOfExpressionIHCService loeService = LevelOfExpressionIHCService.getInstance();
              
              Collection<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> findings = loeService.getFindingsFromSampleInfo(siSet);             
              System.out.println("Number of LevelOfExpressionIHC finding Retrieved: " + findings.size());
              
              for (Iterator<? extends gov.nih.nci.caintegrator.domain.finding.bean.Finding> iterator = findings.iterator(); iterator.hasNext();) {
                  LevelOfExpressionIHCFinding lFinding =  (LevelOfExpressionIHCFinding) iterator.next();
                  System.out.println("LevelOfExpressionIHC finding ID: " +
                                      lFinding.getId());
                  System.out.println("protein: " + lFinding.getProteinBiomarker());
                  
                  System.out.println("patient did: " + lFinding.getSpecimen().getPatientDID());
                  System.out.println("specimen id: "+ lFinding.getSpecimen().getId());                 
                  if (lFinding.getSpecimen().getTimePoint() != null)
                     System.out.println("time point:" + lFinding.getSpecimen().getTimePoint());
              }
              //return findings;
          } catch (Throwable t)  {
             System.out.println("LevelExpr Exception in getting data: " + t.toString());
             t.printStackTrace();
         }
          
         return null;
     }   
     
  
     public void testAll() {       
         Collection findings =  executeSearch();
     }    
     
     
     public static void main (String[] args) {
         junit.textui.TestRunner.run(suite());

     }
}