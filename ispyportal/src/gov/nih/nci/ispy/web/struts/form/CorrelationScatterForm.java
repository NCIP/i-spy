package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.CorrelationType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.web.helper.UIFormValidator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class CorrelationScatterForm extends BaseForm{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String xaxis;
    private String yaxis;
    private String reporterX;
    private String reporterY;
    private String geneX;
    private String geneY;
    private String correlationMethod;
    private List continuousCollection = new ArrayList();
    private List correlationMethodCollection = new ArrayList();
    private List arrayPlatformCollection = new ArrayList();
    private String platformY;
    private String platformX;
    private String analysisResultName = "";
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

    public CorrelationScatterForm() {
        
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
        
        //setup correlationMethodCollection
        for(CorrelationType cmt : CorrelationType.values()){
            correlationMethodCollection.add(new LabelValueBean(cmt.toString(),cmt.getDeclaringClass().getCanonicalName() + "#" + cmt.name()));
        }
    }

    /**
     * @return Returns the correlationMethod.
     */
    public String getCorrelationMethod() {
        return correlationMethod;
    }

    /**
     * @param correlationMethod The correlationMethod to set.
     */
    public void setCorrelationMethod(String correlationMethod) {
        this.correlationMethod = correlationMethod;
    }

    /**
     * @return Returns the reporterX.
     */
    public String getReporterX() {
        return reporterX;
    }

    /**
     * @param reporterX The reporterX to set.
     */
    public void setReporterX(String reporterX) {
        this.reporterX = reporterX;
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
     * @return Returns the xaxis.
     */
    public String getXaxis() {
        return xaxis;
    }

    /**
     * @param xaxis The xaxis to set.
     */
    public void setXaxis(String xaxis) {
        this.xaxis = xaxis;
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
     * @return Returns the platformX.
     */
    public String getPlatformX() {
        return platformX;
    }

    /**
     * @param platformX The platformX to set.
     */
    public void setPlatformX(String platformX) {
        this.platformX = platformX;
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
     * @return Returns the correlationMethodCollection.
     */
    public List getCorrelationMethodCollection() {
        return correlationMethodCollection;
    }

    /**
     * @param correlationMethodCollection The correlationMethodCollection to set.
     */
    public void setCorrelationMethodCollection(List correlationMethodCollection) {
        this.correlationMethodCollection = correlationMethodCollection;
    }

    /**
     * @return Returns the geneX.
     */
    public String getGeneX() {
        return geneX;
    }

    /**
     * @param geneX The geneX to set.
     */
    public void setGeneX(String geneX) {
        this.geneX = geneX;
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
        
        //validate patient groups
        errors = UIFormValidator.validatePatientGroup(patientGroup, errors);
        
        //validate axises
        errors = UIFormValidator.validateAxis(xaxis, yaxis, errors);  
        
        //validate reporters
        errors = UIFormValidator.validateReporterSelection(xaxis, yaxis, reporterX, reporterY, errors);       
       
       
       
        return errors;
    }


}
