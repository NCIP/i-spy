/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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






public class IHCLossOfExpressionQueryForm extends BaseForm implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(IHCLossOfExpressionQueryForm.class);
    
    private String analysisResultName = "";    
    private String[] timepoints;    
    private Collection timepointCollection = new ArrayList();
    private String[] biomarkers;  
    private Collection biomarkersCollection = new ArrayList();    
    private List operators = new ArrayList();
    private String invasiveRangeOperator;
    private int invasiveRange;
    private String benignRangeOperator;
    private int benignRange;
    private String[] lossResult;
    private Collection lossCollection = new ArrayList();
    

    public IHCLossOfExpressionQueryForm(){
        
        for (TimepointType timepointType : TimepointType.values()){
            timepointCollection.add(new LabelValueBean(timepointType.toString(),timepointType.name()));
        }  
        operators.add(new LabelValueBean("any","none"));
        for (Operator operator : Operator.values()){            
            if(operator.equals(Operator.EQ) || operator.equals(Operator.GE) || operator.equals(Operator.LE)){
            operators.add(new LabelValueBean(operator.toString(),operator.name()));            }
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
     * @return Returns the benignRange.
     */
    public int getBenignRange() {
        return benignRange;
    }

    /**
     * @param benignRange The benignRange to set.
     */
    public void setBenignRange(int benignRange) {
        this.benignRange = benignRange;
    }

    /**
     * @return Returns the benignRangeOperator.
     */
    public String getBenignRangeOperator() {
        return benignRangeOperator;
    }

    /**
     * @param benignRangeOperator The benignRangeOperator to set.
     */
    public void setBenignRangeOperator(String benignRangeOperator) {
        this.benignRangeOperator = benignRangeOperator;
    }

    /**
     * @return Returns the invasiveRange.
     */
    public int getInvasiveRange() {
        return invasiveRange;
    }

    /**
     * @param invasiveRange The invasiveRange to set.
     */
    public void setInvasiveRange(int invasiveRange) {
        this.invasiveRange = invasiveRange;
    }

    /**
     * @return Returns the invasiveRangeOperator.
     */
    public String getInvasiveRangeOperator() {
        return invasiveRangeOperator;
    }

    /**
     * @param invasiveRangeOperator The invasiveRangeOperator to set.
     */
    public void setInvasiveRangeOperator(String invasiveRangeOperator) {
        this.invasiveRangeOperator = invasiveRangeOperator;
    }

    /**
     * @return Returns the lossCollection.
     */
    public Collection getLossCollection() {
        return lossCollection;
    }

    /**
     * @param lossCollection The lossCollection to set.
     */
    public void setLossCollection(Collection lossCollection) {
        this.lossCollection = lossCollection;
    }

    /**
     * @return Returns the lossResult.
     */
    public String[] getLossResult() {
        return lossResult;
    }

    /**
     * @param lossResult The lossResult to set.
     */
    public void setLossResult(String[] lossResult) {
        this.lossResult = lossResult;
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
     * @return Returns the operators.
     */
    public List getOperators() {
        return operators;
    }

    /**
     * @param operators The operators to set.
     */
    public void setOperators(List operators) {
        this.operators = operators;
    }
	
}
	