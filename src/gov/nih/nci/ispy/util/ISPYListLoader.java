package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import gov.nih.nci.caintegrator.application.lists.ListLoader;
import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;

/**
 * This class uses the loadLists method from list loader to read any text files
 * placed in the dat_files directory of the project. It also loads (as "userLists")
 * all the predefined clinical status groups found in the ISPY study(e.g ER+, ER-, etc)
 * @author rossok
 * @param 
 *
 */

public class ISPYListLoader extends ListLoader{

    public ISPYListLoader() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * this custom ispy method queries ispy based on patient clinical status
     * and creates more predefined user lists based on this clinical data. At the time
     * of this writing, only non-timepoint dependent groups are tallied.
     * @throws OperationNotSupportedException 
     */
    public static UserListBean loadStatusGroups(UserListBean userListBean) throws OperationNotSupportedException{
        ClinicalFileBasedQueryService cqs = ClinicalFileBasedQueryService.getInstance();
        ListManager listManager = new ListManager();
        
        /**
         * set up simple ISPYclinicalDataQueryDTO with the appropriate status group enums set
         * in the query to return back a list of PatientDIDs based soley on the status group.
         */
        
        /*
         * for each stage group, load the EnumSet, send the query, return a list,
         * set the list into a UserList and add it to the UserListBean.
         */
        List<ClinicalStageType> cst = ClinicalStageType.getDisplayValues();
        for(ClinicalStageType clinicalStage : cst){
            ISPYclinicalDataQueryDTO cDTO = new ISPYclinicalDataQueryDTO();
            EnumSet<ClinicalStageType> clinicalStages = EnumSet.noneOf(ClinicalStageType.class);
            clinicalStages.add(clinicalStage);
            cDTO.setClinicalStageValues(clinicalStages);
            Set<String> clinicalStagePatients = cqs.getPatientDIDs(cDTO);
                if(!clinicalStagePatients.isEmpty()){
                    List<String> dids = new ArrayList<String>(clinicalStagePatients);
                    ISPYListValidator listValidator = new ISPYListValidator(ListType.PatientDID,dids);
                    UserList myList = listManager.createList(ListType.PatientDID,clinicalStage.toString(),dids,listValidator);
                    if(!myList.getList().isEmpty()){
                    	myList.setListSubType(ListSubType.Default);
                        userListBean.addList(myList);
                    }
                }
        }
        
       List<ERstatusType> est = ERstatusType.getDisplayValues();
       for(ERstatusType erStatus: est){
           ISPYclinicalDataQueryDTO cDTO = new ISPYclinicalDataQueryDTO();
           EnumSet<ERstatusType> erStatuses = EnumSet.noneOf(ERstatusType.class);
           erStatuses.add(erStatus);
           cDTO.setErStatusValues(erStatuses);
           Set<String> erStatusPatients = cqs.getPatientDIDs(cDTO);
               if(!erStatusPatients.isEmpty()){
                   List<String> dids = new ArrayList<String>(erStatusPatients);
                   ISPYListValidator listValidator = new ISPYListValidator(ListType.PatientDID,dids);
                   UserList myList = listManager.createList(ListType.PatientDID,erStatus.toString(),dids,listValidator);
                   if(!myList.getList().isEmpty()){
                	   myList.setListSubType(ListSubType.Default);
                       userListBean.addList(myList);
                   }
               }
       }
       
       List<HER2statusType> hest = HER2statusType.getDisplayValues();
       for(HER2statusType her2Status: hest){
           ISPYclinicalDataQueryDTO cDTO = new ISPYclinicalDataQueryDTO();
           EnumSet<HER2statusType> her2Statuses = EnumSet.noneOf(HER2statusType.class);
           her2Statuses.add(her2Status);
           cDTO.setHer2StatusValues(her2Statuses);
           Set<String> her2StatusPatients = cqs.getPatientDIDs(cDTO);
               if(!her2StatusPatients.isEmpty()){
                   List<String> dids = new ArrayList<String>(her2StatusPatients);
                   ISPYListValidator listValidator = new ISPYListValidator(ListType.PatientDID,dids);
                   UserList myList = listManager.createList(ListType.PatientDID,her2Status.toString(),dids,listValidator);
                   if(!myList.getList().isEmpty()){
                	   myList.setListSubType(ListSubType.Default);
                       userListBean.addList(myList);
                   }
               }
       }
       
       List<PRstatusType> prst = PRstatusType.getDisplayValues();
       for(PRstatusType prStatus: prst){
           ISPYclinicalDataQueryDTO cDTO = new ISPYclinicalDataQueryDTO();
           EnumSet<PRstatusType> prStatuses = EnumSet.noneOf(PRstatusType.class);
           prStatuses.add(prStatus);
           cDTO.setPrStatusValues(prStatuses);
           Set<String> prStatusPatients = cqs.getPatientDIDs(cDTO);
               if(!prStatusPatients.isEmpty()){
                   List<String> dids = new ArrayList<String>(prStatusPatients);
                   ISPYListValidator listValidator = new ISPYListValidator(ListType.PatientDID,dids);
                   UserList myList = listManager.createList(ListType.PatientDID,prStatus.toString(),dids,listValidator);
                   if(!myList.getList().isEmpty()){
                	   myList.setListSubType(ListSubType.Default);
                       userListBean.addList(myList);
                   }
               }
       }       
       return userListBean;
    }
}
