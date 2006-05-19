package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

public class ClinicalGroupRetriever {
    private List<LabelValueBean> clinicalGroupsCollection = new ArrayList<LabelValueBean>();
    
    /**
     * retrieves all current clinical groups after cycling through all the clinical
     * group types.
     * @return collection of LabelValueBeans to populate an ActionForm 
     * -KR
     */
    public List<LabelValueBean> getClinicalGroupsCollection(HttpSession session){
        ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(session);
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        
        for(UserList patientList: patientLists){
            clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        }
        
        for (ClinicalStageType clinicalStageType : ClinicalStageType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(clinicalStageType.toString(),clinicalStageType.getDeclaringClass().getCanonicalName() + "#" + clinicalStageType.name()));
        }
        for (ClinicalResponseType clinicalResponseType : ClinicalResponseType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(clinicalResponseType.toString(),clinicalResponseType.getDeclaringClass().getCanonicalName() + "#" + clinicalResponseType.name()));
        }
        for (ERstatusType erStatusType : ERstatusType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(erStatusType.toString(),erStatusType.getDeclaringClass().getCanonicalName() + "#" + erStatusType.name()));
        }
        for (PRstatusType prStatusType : PRstatusType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(prStatusType.toString(),prStatusType.getDeclaringClass().getCanonicalName() + "#" + prStatusType.name()));
        }
        for (HER2statusType her2StatusType : HER2statusType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(her2StatusType.toString(),her2StatusType.getDeclaringClass().getCanonicalName() + "#" + her2StatusType.name()));
        }
        
        
        return clinicalGroupsCollection;
    }

}
