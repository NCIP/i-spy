package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
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
    private UserListBeanHelper helper;
    private List<UserList> patientLists;
    
    public ClinicalGroupRetriever(HttpSession session){
        helper = new UserListBeanHelper(session);
        patientLists = helper.getLists(ListType.PatientDID);
    }
    /**
     * retrieves all current clinical groups after cycling through all the clinical
     * group types.
     * @return collection of LabelValueBeans to populate an ActionForm 
     * -KR
     */
    public List<LabelValueBean> getClinicalGroupsCollection(){        
        List<LabelValueBean> clinicalGroupsCollection = new ArrayList<LabelValueBean>();        
        
        for(UserList patientList: patientLists){
            clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        }
     
        for (ClinicalResponseType clinicalResponseType : ClinicalResponseType.getDisplayValues()){
            clinicalGroupsCollection.add(new LabelValueBean(clinicalResponseType.toString(),clinicalResponseType.getDeclaringClass().getCanonicalName() + "#" + clinicalResponseType.name()));
        }

        return clinicalGroupsCollection;
    }
    
    public List<LabelValueBean> getClinicalStageCollection(){
        List<LabelValueBean> clinicalStageCollection = new ArrayList<LabelValueBean>();
                
        for (ClinicalStageType clinicalStageType : ClinicalStageType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(clinicalStageType.toString().equals(patientLists.get(i).getName())){
                    clinicalStageCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getClass().getCanonicalName() + "#" + patientLists.get(i).getName()));
                }
            }
        }
        return clinicalStageCollection;    }
    
    
    
    public List<LabelValueBean> getClinicalResponseCollection(){
        //handled as enums b/c usually timepoint dependent
        
        List<LabelValueBean> clinicalResponseCollection = new ArrayList<LabelValueBean>();
        
        for (ClinicalResponseType clinicalResponseType : ClinicalResponseType.getDisplayValues()){
            clinicalResponseCollection.add(new LabelValueBean(clinicalResponseType.toString(),clinicalResponseType.getDeclaringClass().getCanonicalName() + "#" + clinicalResponseType.name()));
        }
        return clinicalResponseCollection;
    }
    
    public List<LabelValueBean> getReceptorCollection(){
        List<LabelValueBean> receptorCollection = new ArrayList<LabelValueBean>();
                
        for (ERstatusType erStatusType : ERstatusType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(erStatusType.toString().equals(patientLists.get(i).getName())){
                    receptorCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getClass().getCanonicalName() + "#" + patientLists.get(i).getName()));
                }
            }
        }
        for (PRstatusType prStatusType : PRstatusType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(prStatusType.toString().equals(patientLists.get(i).getName())){
                    receptorCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getClass().getCanonicalName() + "#" + patientLists.get(i).getName()));
                }
            }
        }
        for (HER2statusType her2StatusType : HER2statusType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(her2StatusType.toString().equals(patientLists.get(i).getName())){
                    receptorCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getClass().getCanonicalName() + "#" + patientLists.get(i).getName()));
                }
            }
        }
        return receptorCollection;
    }
 
}
