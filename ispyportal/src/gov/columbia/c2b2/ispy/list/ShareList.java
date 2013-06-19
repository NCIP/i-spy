/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.list;
import gov.columbia.c2b2.ispy.web.struts.form.GroupMembers;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;
import java.io.Serializable;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.Date;
import java.sql.Timestamp;

public class ShareList extends Object implements Serializable{
	private static final long serialVersionUID = 5729349209858477497L;
	

    private Long shareId;   
    public Long getShareId(){
        return (this.shareId);
    }
    public void setShareId(Long shareId){
        this.shareId=shareId;
    }
    
    private Long listId;   
    public Long getListId(){
        return (this.listId);
    }
    public void setListId(Long listId){
        this.listId=listId;
    }
    
    private Long grId;   
    public Long getGrId(){
        return (this.grId);
    }
    public void setGrId(Long grId){
        this.grId=grId;
    }
    

    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return (this.updateTime);
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime=updateTime;
    }
    
    private Set<GroupManager> groups = new HashSet<GroupManager>();    
    public Set<GroupManager> getGroups(){
    	return this.groups;
    }
    public void setGroups(Set<GroupManager> groups) {
        this.groups=groups;
    }
    
}
