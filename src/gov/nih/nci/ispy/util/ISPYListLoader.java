package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListGenerator;
import gov.nih.nci.ispy.cache.ISPYContextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ISPYListLoader {
    private static Logger logger = Logger.getLogger(ISPYListLoader.class);
   
    public static UserListBean loadLists(UserListBean userListBean, String fileName) {
        
        String userListFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + ispyConstants.ALL_USER_LISTS;
        logger.info("Initializing "+userListFileName);
        File userFile = new File(userListFileName);        
        
        /*initialize the types of lists we want. The format for each is the name of the list,
         * as a text file, followed by an underscore, then the type of lists.
         * In this case, an example would be, testList_geneList.txt
         */
        List<String> allLists = UserListGenerator.generateList(userFile);
        List<String> patientLists = new ArrayList<String>();
        List<String> geneSymbolLists = new ArrayList<String>();
        
        /*
         * sort out the patientLists and the gene Lists
         */
        if(!allLists.isEmpty()){
            for(String f : allLists){
                if(f.contains(ispyConstants.PATIENT_USER_LISTS_POSTFIX)){
                    patientLists.add(f);
                }
                else if(f.contains(ispyConstants.GENE_USER_LISTS_POSTFIX)){
                    geneSymbolLists.add(f);
                }
                else{
                    logger.debug("file has not been indicated as a gene or patient lists. check name format.");
                }
            }
        }
        /*
         * find the patient and gene files and turn them into User Lists
         */
        if(!patientLists.isEmpty()){
            ISPYListManager uploadManager =(ISPYListManager) ISPYListManager.getInstance();
            int count = 0;
            for(String p : patientLists){
                try{
                    String patientlistFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + p;
                    File patientFile = new File(patientlistFileName);
                    List<String> patientList = UserListGenerator.generateList(patientFile);
                    if(!patientList.isEmpty()){
                        UserList myList = uploadManager.createList(ListType.PatientDID, "defaultPatientList"+ (count+1), patientList);
                        count++;
                        if(myList!=null){
                            userListBean.addList(myList);
                        }
                    }                    
                }catch(NullPointerException e){
                    logger.error("file was not found. " + e);                    
                }
            }
        }
        if(!geneSymbolLists.isEmpty()){
            ISPYListManager uploadManager =(ISPYListManager) ISPYListManager.getInstance();
            int count = 0;
            for(String g : geneSymbolLists){
                try{
                    String geneSymbolFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + g;
                    File geneSymbolFile = new File(geneSymbolFileName);
                    List<String> geneSymbolList = UserListGenerator.generateList(geneSymbolFile);
                    if(!geneSymbolList.isEmpty()){
                        UserList myList = uploadManager.createList(ListType.GeneSymbol, "defaultGeneList"+ (count+1), geneSymbolList);
                        count++;
                        if(myList!=null){
                            userListBean.addList(myList);
                        }
                    }                    
                }catch(NullPointerException e){
                    logger.error("file was not found. " + e);                    
                }
            }
        }
        //return populated userListBean
        return userListBean;
       
    }

}
