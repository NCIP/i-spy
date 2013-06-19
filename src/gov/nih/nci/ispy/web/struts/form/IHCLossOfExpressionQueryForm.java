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
	