package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.DiseaseStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class ClinicalGroupRetriever {
    private List<LabelValueBean> clinicalGroupsCollection = new ArrayList<LabelValueBean>();
    
    /**
     * retrieves all current clinical groups after cycling through all the clinical
     * group types.
     * @return collection of LabelValueBeans to populate an ActionForm 
     * -KR
     */
    public List<LabelValueBean> getClinicalGroupsCollection(){
        
        
        for (DiseaseStageType diseaseStageType : DiseaseStageType.values()){
            clinicalGroupsCollection.add(new LabelValueBean(diseaseStageType.toString(),diseaseStageType.name()));
        }
        for (ClinicalResponseType clinicalResponseType : ClinicalResponseType.values()){
            clinicalGroupsCollection.add(new LabelValueBean(clinicalResponseType.toString(),clinicalResponseType.name()));
        }
        for (ERstatusType erStatusType : ERstatusType.values()){
            clinicalGroupsCollection.add(new LabelValueBean(erStatusType.toString(),erStatusType.name()));
        }
        for (PRstatusType prStatusType : PRstatusType.values()){
            clinicalGroupsCollection.add(new LabelValueBean(prStatusType.toString(),prStatusType.name()));
        }
        for (HER2statusType her2StatusType : HER2statusType.values()){
            clinicalGroupsCollection.add(new LabelValueBean(her2StatusType.toString(),her2StatusType.name()));
        }
        
        return clinicalGroupsCollection;
    }

}
