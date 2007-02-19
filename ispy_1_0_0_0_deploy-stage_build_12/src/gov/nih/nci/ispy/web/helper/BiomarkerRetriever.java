package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.UserList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

public class BiomarkerRetriever {
    
    /**
     * retrieves all biomarkers
     * @return collection of LabelValueBeans to populate an ActionForm 
     * TODO: change to retrieve from DB when data is loaded 
     * -KR
     */
    public static Set<String> getBiomarkers(){        
        Set<String> biomarkers = new HashSet<String>();      
          biomarkers.add("BCL2");
          biomarkers.add("EGFR");
          biomarkers.add("FAK");
          biomarkers.add("HER2");
          biomarkers.add("Ki-67");
          biomarkers.add("P53");
          biomarkers.add("CCND1");
          biomarkers.add("P27");
      return biomarkers;
    }

}
