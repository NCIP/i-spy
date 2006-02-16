package gov.nih.nci.ispy.web.struts.form;




import gov.nih.nci.caintegrator.enumeration.DistanceMatrixType;
import gov.nih.nci.caintegrator.enumeration.LinkageMethodType;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;






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

public class HierarchicalClusteringForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(BaseForm.class);
	
    private String analysisResultName = "";
    
    private String distanceMatrix = "Correlation";
    
    private Collection distanceMatrixCollection = new ArrayList();
    
    private String linkageMethod = "Average";
    
    private Collection linkageMethodCollection = new ArrayList();
    
    private int variancePercentile = 95;
    
    private String filterType = "default";
    
    private String geneSetName = "";
    
    private String reporterSetName = "";
    
    private String clusterBy = "Samples";
    
    private String arrayPlatform = "";
    
    private String diffExpGenes = "diffExpGenes";
    
    private String diffExpReporters = "diffExpReporters";
    
    private String constraintVariance = "constraintVariance";

	public HierarchicalClusteringForm(){
       
        //set up lookups for  HierarchicalClusteringForm
        
        for (DistanceMatrixType distanceMatrixType : DistanceMatrixType.values()){
            distanceMatrixCollection.add(new LabelValueBean(distanceMatrixType.toString(),distanceMatrixType.name()));
        }
        for (LinkageMethodType linkageMethodType : LinkageMethodType.values()){
            linkageMethodCollection.add(new LabelValueBean(linkageMethodType.toString(),linkageMethodType.name()));
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
    public int getVariancePercentile() {
        return variancePercentile;
    }

    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public void setVariancePercentile(int variancePercentile) {
        this.variancePercentile = variancePercentile;
    }


    /**
     * @return Returns the constraintVariance.
     */
    public String getConstraintVariance() {
        return constraintVariance;
    }


    /**
     * @param constraintVariance The constraintVariance to set.
     */
    public void setConstraintVariance(String constraintVariance) {
        this.constraintVariance = constraintVariance;
    }


    /**
     * @return Returns the diffExpGenes.
     */
    public String getDiffExpGenes() {
        return diffExpGenes;
    }


    /**
     * @param diffExpGenes The diffExpGenes to set.
     */
    public void setDiffExpGenes(String diffExpGenes) {
        this.diffExpGenes = diffExpGenes;
    }


    /**
     * @return Returns the diffExpReporters.
     */
    public String getDiffExpReporters() {
        return diffExpReporters;
    }


    /**
     * @param diffExpReporters The diffExpReporters to set.
     */
    public void setDiffExpReporters(String diffExpReporters) {
        this.diffExpReporters = diffExpReporters;
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
        //errors = UIFormValidator.validateQueryName(analysisResultName, errors);   
       
       
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
        analysisResultName = "";           
        arrayPlatform = "";             
      
    }

}
