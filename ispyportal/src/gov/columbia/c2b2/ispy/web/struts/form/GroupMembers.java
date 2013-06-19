/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.struts.form;
import java.sql.Timestamp;
import gov.nih.nci.security.authorization.domainobjects.User;

public class GroupMembers{
    
    private Long memberId;
    private char memberStatus;
    private Long grId;
    private Long usrId;
    private char shareStatus;
    private Timestamp updateTime;
    
    
    public Long getGrId(){
        return (this.grId);
    }

    public void setGrId(Long grId){
        this.grId=grId;
    }
    
    public Long getMemberId(){
        return (this.memberId);
    }

    public void setMemberId(Long memberId){
        this.memberId=memberId;
    }
    
    public char getMemberStatus(){
        return (this.memberStatus);
    }

    public void setMemberStatus(char memberStatus){
        this.memberStatus=memberStatus;
    }
    
    public Long getUsrId(){
        return (this.usrId);
    }

    public void setUsrId(Long usrId){
        this.usrId=usrId;
    }
    
    public char getShareStatus(){
        return (this.shareStatus);
    }

    public void setShareStatus(char shareStatus){
        this.shareStatus=shareStatus;
    }
    
    public Timestamp getUpdateTime(){
        return (this.updateTime);
    }

    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
    
    User userInfo;
    
    public User getUserInfo(){
        return (this.userInfo);
    }

    public void setUserInfo(User userInfo){
        this.userInfo=userInfo;
    }
    
    private String firstName;
    
    public String getFirstName(){
        return (this.firstName);
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    
    private String lastName;
    
    public String getLastName(){
        return (this.lastName);
    }
    
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
 /*   
    String TlastName = userInfo.getLastName();
    String TfirstName = userInfo.getFirstName();
    
   */ 
    
    
}
