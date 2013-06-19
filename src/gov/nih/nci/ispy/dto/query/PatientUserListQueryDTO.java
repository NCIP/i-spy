/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

public class PatientUserListQueryDTO implements ClinicalQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String queryName;    
    private boolean isBaseline = false;	
	private List<String> patientDIDs = new ArrayList<String>();
    private EnumSet<TimepointType> timepointValues = EnumSet.noneOf(TimepointType.class);
    
	
	public PatientUserListQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    public PatientUserListQueryDTO(HttpSession session, String listName) {
        super();
        setQueryName(listName);
        setPatientDIDs(session);
    }
	
    public void setPatientDIDs(HttpSession session){        
        UserListBeanHelper helper = new UserListBeanHelper(session);
        patientDIDs = helper.getItemsFromList(this.queryName);        
    }
    
    /**
     * @return Returns the patientDIDs.
     */
    public List<String> getPatientDIDs() {
        return patientDIDs;
    }

	public void setQueryName(String name) {
	  this.queryName = name;		
	}


	public String getQueryName() {
	  return queryName;
	}


    /**
     * @return Returns the isBaseline.
     */
    public boolean isBaseline() {
        return isBaseline;
    }


    /**
     * @param isBaseline The isBaseline to set.
     */
    public void setBaseline(boolean isBaseline) {
        this.isBaseline = isBaseline;
    }
    
    public Set<TimepointType> getTimepointValues() {
        return timepointValues;
    }


    public void setTimepointValues(EnumSet<TimepointType> timepointValues) {
        this.timepointValues = timepointValues;
    }

}
