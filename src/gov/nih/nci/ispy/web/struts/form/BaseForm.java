package gov.nih.nci.ispy.web.struts.form;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;


 /**
 * This class encapsulates the properties of an caintergator
 * BaseForm object, it is a parent class for all form objects 
 * 
 * 
 * @author BhattarR,ZhangD
 *
 */






public class BaseForm extends ActionForm implements Serializable{
    
    private static Logger logger = Logger.getLogger(BaseForm.class);
	private String method;	
    private String patientGroup; 
    private List patientGroupCollection; 
    
	
	/**
     * @return Returns the patientGroup.
     */
    public String getPatientGroup() {
        return patientGroup;
    }

    /**
     * @param patientGroup The patientGroup to set.
     */
    public void setPatientGroup(String patientGroup) {
        this.patientGroup = patientGroup;
    }

    public BaseForm(){
		
	}
	
    /**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}

    /**
     * @return Returns the patientGroupCollection.
     */
    public List getPatientGroupCollection() {
        return patientGroupCollection;
    }

    /**
     * @param patientGroupCollection The patientGroupCollection to set.
     */
    public void setPatientGroupCollection(List patientGroupCollection) {
        this.patientGroupCollection = patientGroupCollection;
    }
}
	