package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.web.helper.UIFormValidator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class CategoricalCorrelationForm extends BaseForm{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String yaxis;    
    private String reporterY;   
    private String geneY;    
    private List continuousCollection = new ArrayList();    
    private List arrayPlatformCollection = new ArrayList();
    private String platformY;    
    private String analysisResultName = "";    
    private String [] existingGroups;    
    private List existingGroupsList;    
    private String [] selectedGroups;
    
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

    public CategoricalCorrelationForm() {
        
        //setup arrayPlatformCollection
        for(ArrayPlatformType apt: ArrayPlatformType.values()){
            if(apt==ArrayPlatformType.AGILENT || apt == ArrayPlatformType.CDNA_ARRAY_PLATFORM){
                arrayPlatformCollection.add(new LabelValueBean(apt.toString(), apt.getDeclaringClass().getCanonicalName() + "#" + apt.name()));
            }
        }
              
        //setup continuous collection
        for(ContinuousType continuousType : ContinuousType.values()){
            continuousCollection.add(new LabelValueBean(continuousType.toString(),continuousType.getDeclaringClass().getCanonicalName() + "#" + continuousType.name()));
        } 
        
     }

    /**
     * @return Returns the reporterY.
     */
    public String getReporterY() {
        return reporterY;
    }

    /**
     * @param reporterY The reporterY to set.
     */
    public void setReporterY(String reporterY) {
        this.reporterY = reporterY;
    }

    
    /**
     * @return Returns the yaxis.
     */
    public String getYaxis() {
        return yaxis;
    }

    /**
     * @param yaxis The yaxis to set.
     */
    public void setYaxis(String yaxis) {
        this.yaxis = yaxis;
    }

    /**
     * @return Returns the continuousCollection.
     */
    public List getContinuousCollection() {
        return continuousCollection;
    }

    /**
     * @param continuousCollection The continuousCollection to set.
     */
    public void setContinuousCollection(List continuousCollection) {
        this.continuousCollection = continuousCollection;
    }

    /**
     * @return Returns the arrayPlatformCollection.
     */
    public List getArrayPlatformCollection() {
        return arrayPlatformCollection;
    }

    /**
     * @param arrayPlatformCollection The arrayPlatformCollection to set.
     */
    public void setArrayPlatformCollection(List arrayPlatformCollection) {
        this.arrayPlatformCollection = arrayPlatformCollection;
    }

    /**
     * @return Returns the platformY.
     */
    public String getPlatformY() {
        return platformY;
    }

    /**
     * @param platformY The platformY to set.
     */
    public void setPlatformY(String platformY) {
        this.platformY = platformY;
    }

    
    /**
     * @return Returns the geneY.
     */
    public String getGeneY() {
        return geneY;
    }

    /**
     * @param geneY The geneY to set.
     */
    public void setGeneY(String geneY) {
        this.geneY = geneY;
    }

     /**
     * @return Returns the existingGroups.
     */
    public String[] getExistingGroups() {
        return existingGroups;
    }

    /**
     * @param existingGroups The existingGroups to set.
     */
    public void setExistingGroups(String[] existingGroups) {
        this.existingGroups = existingGroups;
    }

    /**
     * @return Returns the existingGroupsList.
     */
    public List getExistingGroupsList() {
        return existingGroupsList;
    }

    /**
     * @param existingGroupsList The existingGroupsList to set.
     */
    public void setExistingGroupsList(List existingGroupsList) {
        this.existingGroupsList = existingGroupsList;
    }

    /**
     * @return Returns the selectedGroups.
     */
    public String[] getSelectedGroups() {
        return selectedGroups;
    }

    /**
     * @param selectedGroups The selectedGroups to set.
     */
    public void setSelectedGroups(String[] selectedGroups) {
        this.selectedGroups = selectedGroups;
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
      
        //Analysis Query Name cannot be blank
        errors = UIFormValidator.validateQueryName(analysisResultName, errors);
        
        //validate selected groups
        errors = UIFormValidator.validateSelectedGroups(selectedGroups, errors);
        
        //validate axises
        errors = UIFormValidator.validateAxis(null, yaxis, errors);  
        
        //validate reporters
        errors = UIFormValidator.validateReporterSelection(null, yaxis, null, reporterY, errors);       
       
       
       
        return errors;
    }


}
