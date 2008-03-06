package gov.nih.nci.ispy.web.helper;

import gov.columbia.c2b2.ispy.list.ListOrigin;
import gov.columbia.c2b2.ispy.list.ListSubType;
import gov.columbia.c2b2.ispy.list.ListType;
import gov.columbia.c2b2.ispy.list.UserListN;
import gov.columbia.c2b2.ispy.list.UserListBeanHelper;
import gov.nih.nci.ispy.service.clinical.AgeCategoryType;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.MorphologyType;
import gov.nih.nci.ispy.service.clinical.NeoAdjuvantChemoRegimenType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.PcrType;
import gov.nih.nci.ispy.service.clinical.PercentLDChangeType;
import gov.nih.nci.ispy.service.clinical.RaceType;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

public class ClinicalGroupRetriever {
    private UserListBeanHelper helper;
    private List<UserListN> patientLists;
    
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
        for(UserListN patientList: patientLists){
          clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getName()));
        }
        
//        old way        
//        for(UserListN patientList: patientLists){
//            clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
//        }
     
        return clinicalGroupsCollection;
    }
    
    public List<LabelValueBean> getAgentsCollection(){
        List<LabelValueBean> agentsCollection = new ArrayList<LabelValueBean>();
        for(NeoAdjuvantChemoRegimenType agent: NeoAdjuvantChemoRegimenType.values()){
            agentsCollection.add(new LabelValueBean(agent.toString(),agent.getDeclaringClass().getCanonicalName() + "#" + agent.name()));
        }        
        return agentsCollection;
    }
    
    public List<LabelValueBean> getMorphologyCollection(){
        List<LabelValueBean> morphologyCollection = new ArrayList<LabelValueBean>();
        for(MorphologyType morphology: MorphologyType.values()){
            morphologyCollection.add(new LabelValueBean(morphology.toString(),morphology.getDeclaringClass().getCanonicalName() + "#" + morphology.name()));
        }        
        return morphologyCollection;
    }
    
    public List<LabelValueBean> getAgeCollection(){
        List<LabelValueBean> ageCollection = new ArrayList<LabelValueBean>();
        for(AgeCategoryType age: AgeCategoryType.values()){
            ageCollection.add(new LabelValueBean(age.toString(),age.getDeclaringClass().getCanonicalName() + "#" + age.name()));
        }        
        return ageCollection;
    }
    
    public List<LabelValueBean> getRaceCollection(){
        List<LabelValueBean> raceCollection = new ArrayList<LabelValueBean>();
        for(RaceType race: RaceType.values()){
            raceCollection.add(new LabelValueBean(race.toString(),race.getDeclaringClass().getCanonicalName() + "#" + race.name()));
        }        
        return raceCollection;
    }
    
    public List<LabelValueBean> getLdPercentChangeCollection(){
        List<LabelValueBean> ldPercentChangeCollection = new ArrayList<LabelValueBean>();
        for(PercentLDChangeType percentLDChangeType: PercentLDChangeType.values()){
            ldPercentChangeCollection.add(new LabelValueBean(percentLDChangeType.toString(),percentLDChangeType.getDeclaringClass().getCanonicalName() + "#" + percentLDChangeType.name()));
        }        
        return ldPercentChangeCollection;
    }
    
  
    
    public List<LabelValueBean> getPcrCollection(){
        List<LabelValueBean> pcrCollection = new ArrayList<LabelValueBean>();
        for(PcrType pcrType: PcrType.values()){
        	pcrCollection.add(new LabelValueBean(pcrType.toString(),pcrType.getDeclaringClass().getCanonicalName() + "#" + pcrType.name()));
        }        
        return pcrCollection;
    }
    
    public List<LabelValueBean> getClinicalStageCollection(){
        List<LabelValueBean> clinicalStageCollection = new ArrayList<LabelValueBean>();
                
        for (ClinicalStageType clinicalStageType : ClinicalStageType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(clinicalStageType.toString().equals(patientLists.get(i).getName())){
                    clinicalStageCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getName()));
                }
            }
        }
        return clinicalStageCollection;    }
    
    
    
    public List<LabelValueBean> getClinicalResponseCollection(){
        //handled as enums b/c usually timepoint dependent
        
        List<LabelValueBean> clinicalResponseCollection = new ArrayList<LabelValueBean>();
        
        for (ClinicalResponseType clinicalStageType : ClinicalResponseType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(patientLists.get(i).getName().contains(clinicalStageType.toString())){
                    clinicalResponseCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getName()));
                }
            }
        }
        return clinicalResponseCollection;
    }
    
    public List<LabelValueBean> getReceptorCollection(){
        List<LabelValueBean> receptorCollection = new ArrayList<LabelValueBean>();
                
        for (ERstatusType erStatusType : ERstatusType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(erStatusType.toString().equals(patientLists.get(i).getName())){
                    receptorCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getName()));
                }
            }
        }
        for (PRstatusType prStatusType : PRstatusType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(prStatusType.toString().equals(patientLists.get(i).getName())){
                    receptorCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getName()));
                }
            }
        }
        for (HER2statusType her2StatusType : HER2statusType.getDisplayValues()){
            for(int i=0;i<patientLists.size();i++){
                if(her2StatusType.toString().equals(patientLists.get(i).getName())){
                    receptorCollection.add(new LabelValueBean(patientLists.get(i).getName(),patientLists.get(i).getName()));
                }
            }
        }
        return receptorCollection;
    }
 
    public List<LabelValueBean> getCustomPatientCollection(){
        List<LabelValueBean> customPatientCollection = new ArrayList<LabelValueBean>();
        List<UserListN> customList = helper.getLists(ListType.PatientDID, ListOrigin.Custom); 
        for(UserListN list : customList){
            customPatientCollection.add(new LabelValueBean(list.getName(),list.getName()));
        }        
        return customPatientCollection;
    }
    
}
