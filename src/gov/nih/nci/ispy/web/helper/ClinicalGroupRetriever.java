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
        
        
        for (DiseaseStageType diseaseStageType : DiseaseStageType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(diseaseStageType.toString(),diseaseStageType.getClass().getCanonicalName() + "#" + diseaseStageType.name()));
        }
        for (ClinicalResponseType clinicalResponseType : ClinicalResponseType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(clinicalResponseType.toString(),clinicalResponseType.getClass().getCanonicalName() + "#" + clinicalResponseType.name()));
        }
        for (ERstatusType erStatusType : ERstatusType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(erStatusType.toString(),erStatusType.getClass().getCanonicalName() + "#" + erStatusType.name()));
        }
        for (PRstatusType prStatusType : PRstatusType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(prStatusType.toString(),prStatusType.getClass().getCanonicalName() + "#" + prStatusType.name()));
        }
        for (HER2statusType her2StatusType : HER2statusType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(her2StatusType.toString(),her2StatusType.getClass().getCanonicalName() + "#" + her2StatusType.name()));
        }
        
        return clinicalGroupsCollection;
    }

}
