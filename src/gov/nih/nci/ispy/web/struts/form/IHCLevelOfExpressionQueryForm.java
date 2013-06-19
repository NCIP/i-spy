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






public class IHCLevelOfExpressionQueryForm extends BaseForm implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(IHCLevelOfExpressionQueryForm.class);
    
    private String analysisResultName = "";    
    private String[] timepoints;    
    private Collection timepointCollection = new ArrayList();
    private String[] biomarkers;
    private Collection biomarkersCollection = new ArrayList();
    private String[] stainIntensity;
    private Collection stainIntensityCollection = new ArrayList();
    private int upPercentPositive = 100;
    private int lowPercentPositive = 0;
    private int absolutePercentPositive = 0;
    private String[] stainLocalization;
    private Collection stainLocalizationCollection = new ArrayList();    
    private String[] stainDistribution;
    private Collection stainDistributionCollection = new ArrayList();
    

    public IHCLevelOfExpressionQueryForm(){
        for (TimepointType timepointType : TimepointType.values()){
            timepointCollection.add(new LabelValueBean(timepointType.toString(),timepointType.name()));
        }    
        
    }
    
    /**
     * @return Returns the biomarkersCollection.
     */
    public Collection getBiomarkersCollection() {
        return biomarkersCollection;
    }


    /**
     * @param biomarkersCollection The biomarkersCollection to set.
     */
    public void setBiomarkersCollection(Collection biomarkersCollection) {
        this.biomarkersCollection = biomarkersCollection;
    }


    /**
     * @return Returns the stainDistributionCollection.
     */
    public Collection getStainDistributionCollection() {
        return stainDistributionCollection;
    }


    /**
     * @param stainDistributionCollection The stainDistributionCollection to set.
     */
    public void setStainDistributionCollection(
            Collection stainDistributionCollection) {
        this.stainDistributionCollection = stainDistributionCollection;
    }


    /**
     * @return Returns the stainIntensityCollection.
     */
    public Collection getStainIntensityCollection() {
        return stainIntensityCollection;
    }


    /**
     * @param stainIntensityCollection The stainIntensityCollection to set.
     */
    public void setStainIntensityCollection(Collection stainIntensityCollection) {
        this.stainIntensityCollection = stainIntensityCollection;
    }


    /**
     * @return Returns the stainLocalizationCollection.
     */
    public Collection getStainLocalizationCollection() {
        return stainLocalizationCollection;
    }


    /**
     * @param stainLocalizationCollection The stainLocalizationCollection to set.
     */
    public void setStainLocalizationCollection(
            Collection stainLocalizationCollection) {
        this.stainLocalizationCollection = stainLocalizationCollection;
    }


    /**
     * @return Returns the lowPercentPositive.
     */
    public int getLowPercentPositive() {
        return lowPercentPositive;
    }


    /**
     * @param lowPercentPositive The lowPercentPositive to set.
     */
    public void setLowPercentPositive(int lowPercentPositive) {
        this.lowPercentPositive = lowPercentPositive;
    }
 

	public int getAbsolutePercentPositive() {
		return absolutePercentPositive;
	}

	public void setAbsolutePercentPositive(int absolutePercentPositive) {
		this.absolutePercentPositive = absolutePercentPositive;
	}

	/**
     * @return Returns the stainDistribution.
     */
    public String[] getStainDistribution() {
        return stainDistribution;
    }


    /**
     * @param stainDistribution The stainDistribution to set.
     */
    public void setStainDistribution(String[] stainDistribution) {
        this.stainDistribution = stainDistribution;
    }


    /**
     * @return Returns the stainLocalization.
     */
    public String[] getStainLocalization() {
        return stainLocalization;
    }


    /**
     * @param stainLocalization The stainLocalization to set.
     */
    public void setStainLocalization(String[] stainLocalization) {
        this.stainLocalization = stainLocalization;
    }


    /**
     * @return Returns the upPercentPositive.
     */
    public int getUpPercentPositive() {
        return upPercentPositive;
    }


    /**
     * @param upPercentPositive The upPercentPositive to set.
     */
    public void setUpPercentPositive(int upPercentPositive) {
        this.upPercentPositive = upPercentPositive;
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
     * @return Returns the stainIntensity.
     */
    public String[] getStainIntensity() {
        return stainIntensity;
    }
    /**
     * @param stainIntensity The stainIntensity to set.
     */
    public void setStainIntensity(String[] stainIntensity) {
        this.stainIntensity = stainIntensity;
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
	