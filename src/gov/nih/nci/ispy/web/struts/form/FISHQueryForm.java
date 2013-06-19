package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.ispy.service.common.TimepointType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;




 /**
 * This class encapsulates the properties of an caintergator
 * BaseForm object, it is a parent class for all form objects 
 * 
 * 
 * @author BhattarR,ZhangD
 *
 */






public class FISHQueryForm extends BaseForm implements Serializable{
    
    private static Logger logger = Logger.getLogger(FISHQueryForm.class);
    
    private String analysisResultName = "";    
    private String[] timepoints;    
    private Collection timepointCollection = new ArrayList();
    private String[] biomarkers;
    private String[] cnaStatus;
    
    public FISHQueryForm(){
        for (TimepointType timepointType : TimepointType.values()){
            timepointCollection.add(new LabelValueBean(timepointType.toString(),timepointType.name()));
        }
    }
    
    
    /**
     * @return Returns the analysisResultName.
     */
    public String getAnalysisResultName() {
        return analysisResultName;
    }
    /**
     * @param analysisResultName The analysisResultName to set.
     */
    public void setAnalysisResultName(String analysisResultName) {
        this.analysisResultName = analysisResultName;
    }
    /**
     * @return Returns the biomarkers.
     */
    public String[] getBiomarkers() {
        return biomarkers;
    }
    /**
     * @param biomarkers The biomarkers to set.
     */
    public void setBiomarkers(String[] biomarkers) {
        this.biomarkers = biomarkers;
    }
    /**
     * @return Returns the cnaStatus.
     */
    public String[] getCnaStatus() {
        return cnaStatus;
    }
    /**
     * @param cnaStatus The cnaStatus to set.
     */
    public void setCnaStatus(String[] cnaStatus) {
        this.cnaStatus = cnaStatus;
    }
    /**
     * @return Returns the timepointCollection.
     */
    public Collection getTimepointCollection() {
        return timepointCollection;
    }
    /**
     * @param timepointCollection The timepointCollection to set.
     */
    public void setTimepointCollection(Collection timepointCollection) {
        this.timepointCollection = timepointCollection;
    }
    /**
     * @return Returns the timepoints.
     */
    public String[] getTimepoints() {
        return timepoints;
    }
    /**
     * @param timepoints The timepoints to set.
     */
    public void setTimepoints(String[] timepoints) {
        this.timepoints = timepoints;
    }
	
}
	