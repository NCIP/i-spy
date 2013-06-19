package gov.nih.nci.ispy.web.struts.form;




import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.web.helper.UIFormValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;








public class PrincipalComponentForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(BaseForm.class);
	
    private String[] timepoints;
    
    private Collection timepointCollection = new ArrayList();    
    
    private String analysisResultName = "";    
        
    private float variancePercentile = 70;
    
    private String filterType = "default";
    
    private String geneSetName;
    
    private List geneSetNameList;
    
    private String reporterSetName;
    
    private String arrayPlatform = "";    
    
	public PrincipalComponentForm(){
        
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
     * @return Returns the arrayPlatform.
     */
    public String getArrayPlatform() {
        return arrayPlatform;
    }

    /**
     * @param arrayPlatform The arrayPlatform to set.
     */
    public void setArrayPlatform(String arrayPlatform) {
        this.arrayPlatform = arrayPlatform;
    }

    
       /**
     * @return Returns the variancePercentile.
     */
    public float getVariancePercentile() {
        return variancePercentile;
    }

    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public void setVariancePercentile(float variancePercentile) {
        this.variancePercentile = variancePercentile;
    }

   
    /**
     * @return Returns the filterType.
     */
    public String getFilterType() {
        return filterType;
    }


    /**
     * @param filterType The filterType to set.
     */
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }


    /**
     * @return Returns the geneSetName.
     */
    public String getGeneSetName() {
        return geneSetName;
    }


    /**
     * @param geneSetName The geneSetName to set.
     */
    public void setGeneSetName(String geneSetName) {
        this.geneSetName = geneSetName;
    }


    /**
     * @return Returns the reporterSetName.
     */
    public String getReporterSetName() {
        return reporterSetName;
    }


    /**
     * @param reporterSetName The reporterSetName to set.
     */
    public void setReporterSetName(String reporterSetName) {
        this.reporterSetName = reporterSetName;
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


    /**
     * Method validate
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        
       // Analysis name cannot be blank
        errors = UIFormValidator.validateQueryName(analysisResultName, errors);
       
        //Timepoints cannot be blank
        errors = UIFormValidator.validateHCTimepoints(timepoints, errors);
       
        return errors;
    }
    
  
    /**
     * Method reset
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        arrayPlatform = ""; 
        filterType = "default";
        
      
    }


    /**
     * @return Returns the geneSetNameList.
     */
    public List getGeneSetNameList() {
        return geneSetNameList;
    }


    /**
     * @param geneSetNameList The geneSetNameList to set.
     */
    public void setGeneSetNameList(List geneSetNameList) {
        this.geneSetNameList = geneSetNameList;
    }

    


    
}
