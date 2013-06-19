/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.info;
import java.sql.Timestamp;

public class LogInfo{

    private Timestamp updateTime;
    
    private Long recId;
    public Long getRecId(){
        return (this.recId);
    }
    public void setRecId(Long recId){
        this.recId=recId;
    }
    
    private Long usrId;
    public Long getUsrId(){
        return (this.usrId);
    }
    public void setUsrId(Long usrId){
        this.usrId=usrId;
    }
  
    private Long refId;
    public Long getRefId(){
        return (this.refId);
    }
    public void setRefId(Long refId){
        this.refId=refId;
    }
    
    private Long actionRef;
    public Long getActionRef(){
        return (this.actionRef);
    }
    public void setActionRef(Long actionRef){
        this.actionRef=actionRef;
    }
    
    private String userAction;    
    public String getUserAction(){
        return (this.userAction);
    }
    public void setUserAction(String userAction){
        this.userAction=userAction;
    }
    
    private char actionStatus;   
    public char getActionStatus(){
        return (this.actionStatus);
    }
    public void setActionStatus(char actionStatus){
        this.actionStatus=actionStatus;
    }    
 
    public Timestamp getUpdateTime(){
        return (this.updateTime);
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
    
    private String userName;    
    public String getUserName(){
        return (this.userName);
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    private String refName;    
    public String getRefName(){
        return (this.refName);
    }
    public void setRefName(String refName){
        this.refName=refName;
    }

    private String listName;    
    public String getListName(){
        return (this.listName);
    }
    public void setListName(String listName){
        this.listName=listName;
    }
    
    private String groupName;    
    public String getGroupName(){
        return (this.groupName);
    }
    public void setGroupName(String groupName){
        this.groupName=groupName;
    }
}
