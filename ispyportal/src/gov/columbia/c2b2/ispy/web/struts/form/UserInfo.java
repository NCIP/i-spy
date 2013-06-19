/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.struts.form;

public class UserInfo {
	
    private Long userId;

    public Long getUserId(){
        return (this.userId);
    }

    public void setUserId(Long userId){
        this.userId=userId;
    }
    
    private String loginName;
    
    public String getLoginName(){
        return (this.loginName);
    }

    public void setLoginName(String loginName){
        this.loginName=loginName;
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
    
    private String organization;
    
    public String getOrganization(){
        return (this.organization);
    }

    public void setOrganization(String organization){
        this.organization=organization;
    }
    
    private String department;
    
    public String getDepartment(){
        return (this.department);
    }

    public void setDepartment(String department){
        this.department=department;
    }
    
    private String title;

    public String getTitle(){
        return (this.title);
    }

    public void setTitle(String title){
        this.title=title;
    }
    
    private String phoneNum;
    
    public String getPhoneNum(){
        return (this.phoneNum);
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum=phoneNum;
    }
    
    private String emailID;
    
    public String getEmailID(){
        return (this.emailID);
    }

    public void setEmailID(String emailID){
        this.emailID=emailID;
    }
    
    GroupMembers groupMembers;
    
    public GroupMembers getGroupMembers(){
        return (this.groupMembers);
    }

    public void setGroupMembers(GroupMembers groupMembers){
        this.groupMembers=groupMembers;
    }
}
