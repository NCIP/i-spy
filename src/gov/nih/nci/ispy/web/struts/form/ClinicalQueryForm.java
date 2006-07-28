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




/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
    private String[] age;
    private List ageCollection;
    private String[] race;
    private List raceCollection;
    
    //MR parameters
    private String morphology;
    private String ldLengthOperator;
    private double ldLength;
    
    private String ldPercentChangeOperator;
    private double ldPercentChange;    
    private String ldTimepointRange;
    private List ldTimepointRangeCollection = new ArrayList();
    
    //Receptor status
    private String[] receptorStatus;
    private List receptorCollection;
    
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




    /**
     * @return Returns the morphology.
     */
    public String getMorphology() {
        return morphology;
    }




    /**
     * @param morphology The morphology to set.
     */
    public void setMorphology(String morphology) {
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
    
}
	