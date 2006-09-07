package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.CorrelationType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;

import java.util.ArrayList;
import java.util.List;

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
    private String correlationMethod;
    private List continuousCollection = new ArrayList();
    private List correlationMethodCollection = new ArrayList();
    private List arrayPlatformCollection = new ArrayList();
    private String platformY;
    private String platformX;
    private String analysisResultName = "";
    
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
        
        //setup continuousCollection
        for(ContinuousType ct : ContinuousType.values()){
            continuousCollection.add(new LabelValueBean(ct.toString(),ct.name()));
        }
        
        //setup correlationMethodCollection
        for(CorrelationType cmt : CorrelationType.values()){
            correlationMethodCollection.add(new LabelValueBean(cmt.toString(),cmt.name()));
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

    

}
