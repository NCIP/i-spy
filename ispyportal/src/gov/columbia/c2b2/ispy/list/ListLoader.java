/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.list;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

public class ListLoader {
    private static Logger logger = Logger.getLogger(ListLoader.class);
    
    /**
     * preloaded lists are to be stored and referenced from some "master" file upon successful login.
     * The method parses this "master" text file that describes each of the files to be preloaded. It decides what type
     * the list needs to be and creates appropriate userlist objects accordingly. If this is
     * successful, the lists are place in the UserListBean...currently held in the session.
     * 
     * @param userListBean
     * @param fileName
     * @param listValidator
     * @return
     * @author KR
     * @throws OperationNotSupportedException 
     */
   
    public static UserListBean loadLists(UserListBean userListBean, String fileName, String dataFilePath, ListValidator listValidator) throws OperationNotSupportedException {
        String masterFilePath = dataFilePath + File.separatorChar + fileName;
        logger.info("Initializing "+ fileName);
        File masterUserFile = new File(masterFilePath);        
        
        /*initialize the types of lists we want. The format for each is the name of the list,
         * as a text file, followed by # sign, then the type of lists.
         * In this case, an example would be, testList.txt#GeneSymbol
         */
        List<String> allLists = UserListGenerator.generateList(masterUserFile);
        ListManager listManager = ListManager.getInstance();
        /*
         * go through the master file, read in the names and types of lists to be created,
         * then, one by one, parse through the list and create appropriate UserLists and place in the
         * UserListBean. Curently, we assume, there are no subtypes for text files, so we make it default.
         */
        if(!allLists.isEmpty()){
            for(String f : allLists){
                String[] userList = f.split("#");
                String textFileName = userList[0];
                String listType = userList[1];
                int count = 0;
                
                //instantiate the enum listType
                ListType myType = ListType.valueOf(listType);
                
                //find the appropriate text file and create a list of strings
                try{
                    textFileName = dataFilePath + File.separatorChar + textFileName;
                    logger.info("Initializing "+textFileName);
                    File userTextFile = new File(textFileName);
                    List<String> myTextList = UserListGenerator.generateList(userTextFile);
                    
                    //now I have a listype and the list of string I want, create a userList from this  
                    listValidator.validate(myType, myTextList);
                    UserListN myUserList = listManager.createList(myType, "default"+myType.toString()+(count+1), myTextList, listValidator);
                    //flag all these that are auto loaded as default
                    myUserList.setListOrigin(ListOrigin.Default);
                    //place the list in the userListBean
                    count++;
                    if(myUserList!=null){
                        userListBean.addList(myUserList);
                    }
                }catch(NullPointerException e){
                    logger.error("file was not found. " + e);               
                } catch (OperationNotSupportedException e) {
                    logger.error("Error:" + e);    
                    throw new OperationNotSupportedException();
                }
            }
        }
        //return populated userlistBean
        return userListBean;
    }

    public List<UserListN> loadUserLists(){
        
        return null;
    }
}
