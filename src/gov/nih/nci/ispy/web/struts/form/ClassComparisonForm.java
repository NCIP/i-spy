/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.util.ispyConstants;
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






public class ClassComparisonForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(BaseForm.class);
	
    private String timepointBaseFixed;
    
    private String timepointBaseAcross;
    
    private String timepointComparison = "T2";
    
    private Collection timepointCollection = new ArrayList();
    
    private String timepointRange = "fixed";
    
    private String [] existingGroups;
    
    private List existingGroupsList;
    
    private String [] selectedGroups;
    
    private String baselineGroup;
    
    private String analysisResultName = "";
    
    private String statistic = "default";
    
    private String statisticalMethod = "TTest";
    
    private ArrayList statisticalMethodCollection = new ArrayList();
    
    private String comparisonAdjustment = "NONE";
    
    private ArrayList comparisonAdjustmentCollection = new ArrayList();
    
    private String foldChange = "list";
    
    private String foldChangeAuto = "2";
    
    private List foldChangeAutoList = new ArrayList();
    
    private double foldChangeManual;
    
    private Double statisticalSignificance = .05;
    
    private String arrayPlatform = "";
    
   

	public ClassComparisonForm(){
			
		// Create Lookups for ClassComparisonForm screens 
        for (TimepointType timepointType : TimepointType.values()){
            timepointCollection.add(new LabelValueBean(timepointType.toString(),timepointType.name()));
        }
        
        for (MultiGroupComparisonAdjustmentType multiGroupComparisonAdjustmentType : MultiGroupComparisonAdjustmentType.values()){
            comparisonAdjustmentCollection.add(new LabelValueBean(multiGroupComparisonAdjustmentType.toString(),multiGroupComparisonAdjustmentType.name()));
        }        
        
        for (StatisticalMethodType statisticalMethodType : StatisticalMethodType.values()){
            if(!(statisticalMethodType.equals(StatisticalMethodType.FTest)) && !(statisticalMethodType.equals(StatisticalMethodType.GLM)) && !(statisticalMethodType.equals(StatisticalMethodType.ANOVA))){
            statisticalMethodCollection.add(new LabelValueBean(statisticalMethodType.toString(),statisticalMethodType.name())); 
            }
        }
        
        for (int i=0; i<ispyConstants.FOLD_CHANGE_DEFAULTS.length;i++){
            foldChangeAutoList.add(new LabelValueBean(ispyConstants.FOLD_CHANGE_DEFAULTS[i],ispyConstants.FOLD_CHANGE_DEFAULTS[i]));
        }
        
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
    public void setExistingGroups(String [] existingGroups) {
        this.existingGroups = existingGroups;
    }



    /**
     * @return Returns the existingGroupsList.
     */
    public List getExistingGroupsList() {
        return this.existingGroupsList;
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
    public String [] getSelectedGroups() {
        return selectedGroups;
    }



    /**
     * @param selectedGroups The selectedGroups to set.
     */
    public void setSelectedGroups(String [] selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

	public String getBaselineGroup() {
		return baselineGroup;
	}



	public void setBaselineGroup(String baselineGroup) {
		this.baselineGroup = baselineGroup;
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
     * @return Returns the comparisonAdjustment.
     */
    public String getComparisonAdjustment() {
        return comparisonAdjustment;
    }



    /**
     * @param comparisonAdjustment The comparisonAdjustment to set.
     */
    public void setComparisonAdjustment(String comparisonAdjustment) {
        this.comparisonAdjustment = comparisonAdjustment;
    }



    /**
     * @return Returns the foldChange.
     */
    public String getFoldChange() {
        return foldChange;
    }



    /**
     * @param foldChange The foldChange to set.
     */
    public void setFoldChange(String foldChange) {
        this.foldChange = foldChange;
    }


    /**
     * @return Returns the statistic.
     */
    public String getStatistic() {
        return statistic;
    }



    /**
     * @param statistic The statistic to set.
     */
    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }



    /**
     * @return Returns the statisticalMethod.
     */
    public String getStatisticalMethod() {
        return statisticalMethod;
    }



    /**
     * @param statisticalMethod The statisticalMethod to set.
     */
    public void setStatisticalMethod(String statisticalMethod) {
        this.statisticalMethod = statisticalMethod;
    }
    
    /**
     * @return Returns the comparisonAdjustmentCollection.
     */
    public ArrayList getComparisonAdjustmentCollection() {
        return comparisonAdjustmentCollection;
    }



    /**
     * @param comparisonAdjustmentCollection The comparisonAdjustmentCollection to set.
     */
    public void setComparisonAdjustmentCollection(
            ArrayList comparisonAdjustmentCollection) {
        this.comparisonAdjustmentCollection = comparisonAdjustmentCollection;
    }



    /**
     * @return Returns the statisticalMethodCollection.
     */
    public ArrayList getStatisticalMethodCollection() {
        return statisticalMethodCollection;
    }



    /**
     * @param statisticalMethodCollection The statisticalMethodCollection to set.
     */
    public void setStatisticalMethodCollection(ArrayList statisticalMethodCollection) {
        this.statisticalMethodCollection = statisticalMethodCollection;
    }
    
    /**
     * @return Returns the foldChangeAuto.
     */
    public String getFoldChangeAuto() {
        return foldChangeAuto;
    }



    /**
     * @param foldChangeAuto The foldChangeAuto to set.
     */
    public void setFoldChangeAuto(String foldChangeAuto) {
        this.foldChangeAuto = foldChangeAuto;
    }



    /**
     * @return Returns the foldChangeManual.
     */
    public double getFoldChangeManual() {
        return foldChangeManual;
    }



    /**
     * @param foldChangeManual The foldChangeManual to set.
     */
    public void setFoldChangeManual(double foldChangeManual) {
        this.foldChangeManual = foldChangeManual;
    }
    
    /**
     * @return Returns the statisticalSignificance.
     */
    public Double getStatisticalSignificance() {
        return statisticalSignificance;
    }



    /**
     * @param statisticalSignificance The statisticalSignificance to set.
     */
    public void setStatisticalSignificance(Double statisticalSignificance) {
        this.statisticalSignificance = statisticalSignificance;
    }
    
    /**
     * @return Returns the foldChangeAutoList.
     */
    public List getFoldChangeAutoList() {
        return foldChangeAutoList;
    }



    /**
     * @param foldChangeAutoList The foldChangeAutoList to set.
     */
    public void setFoldChangeAutoList(List foldChangeAutoList) {
        this.foldChangeAutoList = foldChangeAutoList;
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
     * @return Returns the timepointRange.
     */
    public String getTimepointRange() {
        return timepointRange;
    }



    /**
     * @param timepointRange The timepointRange to set.
     */
    public void setTimepointRange(String timepointRange) {
        this.timepointRange = timepointRange;
    }


    /**
     * @return Returns the timepointBase.
     */
    public String getTimepointBaseFixed() {
        return timepointBaseFixed;
    }



    /**
     * @param timepointBase The timepointBase to set.
     */
    public void setTimepointBaseFixed(String timepointBaseFixed) {
        this.timepointBaseFixed = timepointBaseFixed;
    }

    

    /**
     * @return Returns the timepointComparison.
     */
    public String getTimepointComparison() {
        return timepointComparison;
    }



    /**
     * @param timepointComparison The timepointComparison to set.
     */
    public void setTimepointComparison(String timepointComparison) {
        this.timepointComparison = timepointComparison;
    }

    

    /**
     * @return Returns the timepointBaseAcross.
     */
    public String getTimepointBaseAcross() {
        return timepointBaseAcross;
    }



    /**
     * @param timepointBaseAcross The timepointBaseAcross to set.
     */
    public void setTimepointBaseAcross(String timepointBaseAcross) {
        this.timepointBaseAcross = timepointBaseAcross;
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
        
        //User must select exactly 2 comparison Groups
        errors = UIFormValidator.validateSelectedGroups(selectedGroups, timepointRange, timepointBaseAcross, timepointComparison, request.getSession(), errors);
        
        //look at the manual FC to check for neg value
        //this is validated in the UI, so its put here only as a failsafe
        if(foldChangeManual < 0){
            double tmp = Math.abs(foldChangeManual);
            foldChangeManual = tmp;
        }
        
        

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
        statistic = "default";        
        comparisonAdjustment = "NONE";        
        foldChange = "list";      
        foldChangeAuto = "2"; 
        statisticalSignificance = .05;        
        arrayPlatform = "";             
        statisticalMethod = "TTest";
    }

    
}
