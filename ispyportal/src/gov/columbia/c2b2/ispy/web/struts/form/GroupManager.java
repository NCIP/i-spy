/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.web.struts.form;

//import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LevelOfExpressionIHCFinding;

import java.io.Serializable;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.Date;
import java.sql.Timestamp;

public class GroupManager extends Object implements Serializable{
	private static final long serialVersionUID = 5729349209858477497L;
    private Long grId;
    private String grName;
    private char grStatus;
    private Long ownId;
    private char shareStatus;
    private Timestamp updateTime;
    
    private Set<GroupMembers> members = new HashSet<GroupMembers>();


    
    public Long getGrId(){
        return (this.grId);
    }

    public void setGrId(Long grId){
        this.grId=grId;
    }
    
    public String getGrName(){
        return (this.grName);
    }

    public void setGrName(String grName){
        this.grName=grName;
    }
    
    public char getGrStatus(){
        return (this.grStatus);
    }

    public void setGrStatus(char grStatus){
        this.grStatus=grStatus;
    }
    
    public Long getOwnId(){
        return (this.ownId);
    }

    public void setOwnId(Long ownId){
        this.ownId=ownId;
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
    
    
    public Set<GroupMembers> getMembers(){
    	return this.members;
    }
    
    public void setMembers(Set<GroupMembers> members) {
        this.members=members;
    }
    
    private ArrayList<User> users = new ArrayList<User>();
    
    public ArrayList<User> getUsers(){
    	return this.users;
    }
    public void setUsers(ArrayList<User> users) {
        this.users=users;
    }
/*    
    public boolean equals(Object obj) {
		boolean eq = false;
		if (obj instanceof LevelOfExpressionIHCFinding) {
			LevelOfExpressionIHCFinding levelIHC = (LevelOfExpressionIHCFinding) obj;
			Long thisId = getGrId();

			if (thisId != null && thisId.equals(levelIHC.getId())) {
				eq = true;
			}

		}
		return eq;
	}

public int hashCode() {
		int h = 0;

		if (getGrId() != null) {
			h += getGrId().hashCode();
		}

		return h;
	}
*/

}
