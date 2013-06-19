/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.ispy.web.helper.UIFormValidator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;


 /**
 * This class encapsulates the properties of an clinical
 * data query * 
 * 
 * @author Rossok
 *
 */






public class ClinicalQueryForm extends BaseForm implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(ClinicalQueryForm.class);
    
    //Clinical parameters
    
	private String[] clinicalStages;    
    private List clinicalStageCollection;    
    private String[] histologyType;    
    private List histologyTypeCollection = new ArrayList();    
    private String[] nuclearGrade;    
    private List nuclearGradeCollection = new ArrayList();    
    //private String agentFilter = "or";
    private String[] agents;
    private List agentsCollection = new ArrayList();    
    private String[] response;
    private List responseCollection = new ArrayList(); 
    private List operators = new ArrayList();
    private String diameterOperator;    
    private String diameter = null;
    private String pathTumorSizeOperator;
    private String pathTumorSize; 
    private String rcbOperator;
    private String rcbSize;
    private String rcbTimepointRange;
    private List rcbTimepointRangeCollection = new ArrayList();
    private String[] receptorStatus;
    private List receptorCollection;  
 
    
    
    private String[] age;
    private List ageCollection;
    private String[] race;
    private List raceCollection;
    
    //MR parameters
    private String[] morphology;
    private List morphologyCollection;
    private String ldLengthOperator;
    private double ldLength;
    
    private String ldPercentChangeOperator;
    private double ldPercentChange;    
    private String ldTimepointRange;
    private List ldTimepointRangeCollection = new ArrayList();
    
    //Receptor status
    private String[] pcrStatus;
    private List pcrCollection;
    
    private String analysisResultName = "";
  
	

    public ClinicalQueryForm(){
        for (Operator operator : Operator.values()){
            if(operator.equals(Operator.GE) || operator.equals(Operator.LE)){
            operators.add(new LabelValueBean(operator.toString(),operator.name()));
            }
        }        
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

    
//    /**
//     * @return Returns the agentFilter.
//     */
//    public String getAgentFilter() {
//        return agentFilter;
//    }
//
//
//
//
//    /**
//     * @param agentFilter The agentFilter to set.
//     */
//    public void setAgentFilter(String agentFilter) {
//        this.agentFilter = agentFilter;
//    }




    /**
     * @return Returns the agents.
     */
    public String[] getAgents() {
        return agents;
    }




    /**
     * @param agents The agents to set.
     */
    public void setAgents(String[] agents) {
        this.agents = agents;
    }




    



    /**
     * @return Returns the diameter.
     */
    public String getDiameter() {
        return diameter;
    }




    /**
     * @param diameter The diameter to set.
     */
    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }




    /**
     * @return Returns the diameterOperator.
     */
    public String getDiameterOperator() {
        return diameterOperator;
    }




    /**
     * @param diameterOperator The diameterOperator to set.
     */
    public void setDiameterOperator(String diameterOperator) {
        this.diameterOperator = diameterOperator;
    }




    /**
     * @return Returns the clinicalStageCollection.
     */
    public List getClinicalStageCollection() {
        return clinicalStageCollection;
    }




    /**
     * @param clinicalStageCollection The clinicalStageCollection to set.
     */
    public void setClinicalStageCollection(List clinicalStageCollection) {
        this.clinicalStageCollection = clinicalStageCollection;
    }




    /**
     * @return Returns the clinicalStages.
     */
    public String[] getClinicalStages() {
        return clinicalStages;
    }




    /**
     * @param clinicalStages The clinicalStages to set.
     */
    public void setClinicalStages(String[] clinicalStages) {
        this.clinicalStages = clinicalStages;
    }




    /**
     * @return Returns the histologyType.
     */
    public String[] getHistologyType() {
        return histologyType;
    }




    /**
     * @param histologyType The histologyType to set.
     */
    public void setHistologyType(String[] histologyType) {
        this.histologyType = histologyType;
    }




    /**
     * @return Returns the histologyTypeCollection.
     */
    public List getHistologyTypeCollection() {
        return histologyTypeCollection;
    }




    /**
     * @param histologyTypeCollection The histologyTypeCollection to set.
     */
    public void setHistologyTypeCollection(List histologyTypeCollection) {
        this.histologyTypeCollection = histologyTypeCollection;
    }




    /**
     * @return Returns the ldLength.
     */
    public double getLdLength() {
        return ldLength;
    }




    /**
     * @param ldLength The ldLength to set.
     */
    public void setLdLength(double ldLength) {
        this.ldLength = ldLength;
    }




    /**
     * @return Returns the ldLengthOperator.
     */
    public String getLdLengthOperator() {
        return ldLengthOperator;
    }




    /**
     * @param ldLengthOperator The ldLengthOperator to set.
     */
    public void setLdLengthOperator(String ldLengthOperator) {
        this.ldLengthOperator = ldLengthOperator;
    }




    /**
     * @return Returns the ldPercentChange.
     */
    public double getLdPercentChange() {
        return ldPercentChange;
    }




    /**
     * @param ldPercentChange The ldPercentChange to set.
     */
    public void setLdPercentChange(double ldPercentChange) {
        this.ldPercentChange = ldPercentChange;
    }




    /**
     * @return Returns the ldPercentChangeOperator.
     */
    public String getLdPercentChangeOperator() {
        return ldPercentChangeOperator;
    }




    /**
     * @param ldPercentChangeOperator The ldPercentChangeOperator to set.
     */
    public void setLdPercentChangeOperator(String ldPercentChangeOperator) {
        this.ldPercentChangeOperator = ldPercentChangeOperator;
    }




    /**
     * @return Returns the pathTumorSize.
     */
    public String getPathTumorSize() {
        return pathTumorSize;
    }

    /**
     * @return Returns the pathTumorSizeOperator.
     */
    public String getPathTumorSizeOperator() {
        return pathTumorSizeOperator;
    }




    /**
     * @param pathTumorSizeOperator The pathTumorSizeOperator to set.
     */
    public void setPathTumorSizeOperator(String pathTumorSizeOperator) {
        this.pathTumorSizeOperator = pathTumorSizeOperator;
    }




    /**
     * @param pathTumorSize The pathTumorSize to set.
     */
    public void setPathTumorSize(String pathTumorSize) {
        this.pathTumorSize = pathTumorSize;
    }




    public String getRcbOperator() {
		return rcbOperator;
	}

	public void setRcbOperator(String rcbOperator) {
		this.rcbOperator = rcbOperator;
	}

	public String getRcbSize() {
		return rcbSize;
	}

	public void setRcbSize(String rcbSize) {
		this.rcbSize = rcbSize;
	}
	
	

	public String getRcbTimepointRange() {
		return rcbTimepointRange;
	}

	public void setRcbTimepointRange(String rcbTimepointRange) {
		this.rcbTimepointRange = rcbTimepointRange;
	}

	public List getRcbTimepointRangeCollection() {
		return rcbTimepointRangeCollection;
	}

	public void setRcbTimepointRangeCollection(List rcbTimepointRangeCollection) {
		this.rcbTimepointRangeCollection = rcbTimepointRangeCollection;
	}

	
	public List getPcrCollection() {
		return pcrCollection;
	}

	public void setPcrCollection(List pcrCollection) {
		this.pcrCollection = pcrCollection;
	}

	public String[] getPcrStatus() {
		return pcrStatus;
	}

	public void setPcrStatus(String[] pcrStatus) {
		this.pcrStatus = pcrStatus;
	}

	/**
     * @return Returns the morphology.
     */
    public String[] getMorphology() {
        return morphology;
    }




    /**
     * @param morphology The morphology to set.
     */
    public void setMorphology(String[] morphology) {
        this.morphology = morphology;
    }




    /**
     * @return Returns the nuclearGrade.
     */
    public String[] getNuclearGrade() {
        return nuclearGrade;
    }




    /**
     * @param nuclearGrade The nuclearGrade to set.
     */
    public void setNuclearGrade(String[] nuclearGrade) {
        this.nuclearGrade = nuclearGrade;
    }




    /**
     * @return Returns the nuclearGradeCollection.
     */
    public List getNuclearGradeCollection() {
        return nuclearGradeCollection;
    }




    /**
     * @param nuclearGradeCollection The nuclearGradeCollection to set.
     */
    public void setNuclearGradeCollection(List nuclearGradeCollection) {
        this.nuclearGradeCollection = nuclearGradeCollection;
    }




    /**
     * @return Returns the receptorStatus.
     */
    public String[] getReceptorStatus() {
        return receptorStatus;
    }




    /**
     * @param receptorStatus The receptorStatus to set.
     */
    public void setReceptorStatus(String[] receptorStatus) {
        this.receptorStatus = receptorStatus;
    }




    /**
     * @return Returns the response.
     */
    public String[] getResponse() {
        return response;
    }




    /**
     * @param response The response to set.
     */
    public void setResponse(String[] response) {
        this.response = response;
    }




    /**
     * @return Returns the responseCollection.
     */
    public List getResponseCollection() {
        return responseCollection;
    }




    /**
     * @param responseCollection The responseCollection to set.
     */
    public void setResponseCollection(List responseCollection) {
        this.responseCollection = responseCollection;
    }

    /**
     * @return Returns the agentsCollection.
     */
    public List getAgentsCollection() {
        return agentsCollection;
    }

    /**
     * @param agentsCollection The agentsCollection to set.
     */
    public void setAgentsCollection(List agentsCollection) {
        this.agentsCollection = agentsCollection;
    }

    
    /**
     * @return Returns the mriTimepointRange.
     */
    public String getLdTimepointRange() {
        return ldTimepointRange;
    }

    /**
     * @param mriTimepointRange The mriTimepointRange to set.
     */
    public void setLdTimepointRange(String ldTimepointRange) {
        this.ldTimepointRange = ldTimepointRange;
    }

    /**
     * @return Returns the mriTimepointRangeCollection.
     */
    public List getLdTimepointRangeCollection() {
        return ldTimepointRangeCollection;
    }

    /**
     * @param mriTimepointRangeCollection The mriTimepointRangeCollection to set.
     */
    public void setLdTimepointRangeCollection(List ldTimepointRangeCollection) {
        this.ldTimepointRangeCollection = ldTimepointRangeCollection;
    }

    /**
     * @return Returns the receptorCollection.
     */
    public List getReceptorCollection() {
        return receptorCollection;
    }

    /**
     * @param receptorCollection The receptorCollection to set.
     */
    public void setReceptorCollection(List receptorCollection) {
        this.receptorCollection = receptorCollection;
    }

    /**
     * @return Returns the age.
     */
    public String[] getAge() {
        return age;
    }

    /**
     * @param age The age to set.
     */
    public void setAge(String[] age) {
        this.age = age;
    }

    /**
     * @return Returns the ageCollection.
     */
    public List getAgeCollection() {
        return ageCollection;
    }

    /**
     * @param ageCollection The ageCollection to set.
     */
    public void setAgeCollection(List ageCollection) {
        this.ageCollection = ageCollection;
    }

    /**
     * @return Returns the race.
     */
    public String[] getRace() {
        return race;
    }

    /**
     * @param race The race to set.
     */
    public void setRace(String[] race) {
        this.race = race;
    }

    /**
     * @return Returns the raceCollection.
     */
    public List getRaceCollection() {
        return raceCollection;
    }

    /**
     * @param raceCollection The raceCollection to set.
     */
    public void setRaceCollection(List raceCollection) {
        this.raceCollection = raceCollection;
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
        
        //validate the diameter and pathological tumor size values
        if(diameter!=null && diameter.trim().length()>0){        
            errors = UIFormValidator.parseDouble(diameter,"diameter",errors);
        }
        if(pathTumorSize!=null && pathTumorSize.trim().length()>0){
        errors = UIFormValidator.parseDouble(pathTumorSize,"pathTumorSize",errors);
        }

        return errors;
    }

    /**
     * @return Returns the morphologyCollection.
     */
    public List getMorphologyCollection() {
        return morphologyCollection;
    }

    /**
     * @param morphologyCollection The morphologyCollection to set.
     */
    public void setMorphologyCollection(List morphologyCollection) {
        this.morphologyCollection = morphologyCollection;
    }
    
}
	