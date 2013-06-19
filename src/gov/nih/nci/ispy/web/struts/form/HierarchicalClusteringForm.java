package gov.nih.nci.ispy.web.struts.form;




import gov.nih.nci.caintegrator.enumeration.DistanceMatrixType;
import gov.nih.nci.caintegrator.enumeration.LinkageMethodType;
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








public class HierarchicalClusteringForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(BaseForm.class);
	
    private String analysisResultName = "";
    
    private String[] timepoints;
    
    private Collection timepointCollection = new ArrayList();
    
    private String distanceMatrix = "Correlation";
    
    private Collection distanceMatrixCollection = new ArrayList();
    
    private String linkageMethod = "Average";
    
    private Collection linkageMethodCollection = new ArrayList();
    
    private float variancePercentile = 95;
    
    private String filterType = "default";
    
    private String geneSetName = "";
    
    private List geneSetNameList;
    
    private String reporterSetName = "";
    
    private String clusterBy = "Samples";
    
    private String arrayPlatform = "";

	public HierarchicalClusteringForm(){
       
        //set up lookups for  HierarchicalClusteringForm
        
        for (DistanceMatrixType distanceMatrixType : DistanceMatrixType.values()){
            distanceMatrixCollection.add(new LabelValueBean(distanceMatrixType.toString(),distanceMatrixType.name()));
        }
        for (LinkageMethodType linkageMethodType : LinkageMethodType.values()){
            linkageMethodCollection.add(new LabelValueBean(linkageMethodType.toString(),linkageMethodType.name()));
        }
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
     * @return Returns the clusterBy.
     */
    public String getClusterBy() {
    	logger.debug("cluserBy selection is: "+clusterBy);
        return clusterBy;
    }




    /**
     * @param clusterBy The clusterBy to set.
     */
    public void setClusterBy(String clusterBy) {
        this.clusterBy = clusterBy;
    }




    /**
     * @return Returns the distanceMatrix.
     */
    public String getDistanceMatrix() {
        return distanceMatrix;
    }




    /**
     * @param distanceMatrix The distanceMatrix to set.
     */
    public void setDistanceMatrix(String distanceMatrix) {
    	logger.debug("Setting distanceMatrix to "+distanceMatrix);
        this.distanceMatrix = distanceMatrix;
    }



    /**
     * @return Returns the linkageMethod.
     */
    public String getLinkageMethod() {
        return linkageMethod;
    }




    /**
     * @param linkageMethod The linkageMethod to set.
     */
    public void setLinkageMethod(String linkageMethod) {
        this.linkageMethod = linkageMethod;
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
     * @return Returns the distanceMatrixCollection.
     */
    public Collection getDistanceMatrixCollection() {
        return distanceMatrixCollection;
    }

    /**
     * @param distanceMatrixCollection The distanceMatrixCollection to set.
     */
    public void setDistanceMatrixCollection(Collection distanceMatrixCollection) {
        this.distanceMatrixCollection = distanceMatrixCollection;
    }

    /**
     * @return Returns the linkageMethodCollection.
     */
    public Collection getLinkageMethodCollection() {
        return linkageMethodCollection;
    }

    /**
     * @param linkageMethodCollection The linkageMethodCollection to set.
     */
    public void setLinkageMethodCollection(Collection linkageMethodCollection) {
        this.linkageMethodCollection = linkageMethodCollection;
    }

    /**
     * @return Returns the timepoint.
     */
    public String[] getTimepoints() {
        return timepoints;
    }

    /**
     * @param timepoint The timepoint to set.
     */
    public void setTimepoints(String [] timepoints) {
        this.timepoints = timepoints;
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
        
        //Analysis name cannot be blank
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
        filterType = "default";
        arrayPlatform = ""; 
       
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
