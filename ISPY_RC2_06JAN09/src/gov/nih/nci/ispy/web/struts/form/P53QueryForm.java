package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.ispy.service.common.TimepointType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.util.LabelValueBean;

public class P53QueryForm extends BaseForm implements Serializable {
	
    private String analysisResultName = "";    
    private String[] timepoints;    
    private Collection timepointCollection = new ArrayList();
    private String[] mutationStatus;
    private Collection mutationStatusCollection = new ArrayList();
    private String[] mutationType;
    private Collection mutationTypeCollection = new ArrayList();
 

    public P53QueryForm(){
        for (TimepointType timepointType : TimepointType.values()){
            timepointCollection.add(new LabelValueBean(timepointType.toString(),timepointType.name()));
        }    
        
    }
	 
	public String getAnalysisResultName() {
		return analysisResultName;
	}
	public void setAnalysisResultName(String analysisResultName) {
		this.analysisResultName = analysisResultName;
	}
	public String[] getTimepoints() {
		return timepoints;
	}
	public void setTimepoints(String[] timepoints) {
		this.timepoints = timepoints;
	}
	public Collection getTimepointCollection() {
		return timepointCollection;
	}
	public void setTimepointCollection(Collection timepointCollection) {
		this.timepointCollection = timepointCollection;
	}
	public String[] getMutationStatus() {
		return mutationStatus;
	}
	public void setMutationStatus(String[] mutationStatus) {
		this.mutationStatus = mutationStatus;
	}
	public Collection getMutationStatusCollection() {
		return mutationStatusCollection;
	}
	public void setMutationStatusCollection(Collection mutationStatusCollection) {
		this.mutationStatusCollection = mutationStatusCollection;
	}
	public String[] getMutationType() {
		return mutationType;
	}
	public void setMutationType(String[] mutationType) {
		this.mutationType = mutationType;
	}
	public Collection getMutationTypeCollection() {
		return mutationTypeCollection;
	}
	public void setMutationTypeCollection(Collection mutationTypeCollection) {
		this.mutationTypeCollection = mutationTypeCollection;
	}
	 
	 
	  

}
