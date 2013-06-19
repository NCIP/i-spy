/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.Collection;
import java.util.List;


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

public class ClassComparisonQueryDTOImpl implements ISPYClassComparisonQueryDTO {
	
	/**
	 * This class captures the significance and/or magnitude of the difference between groups of biological
	 * specimen of which the gene expression is measured by BioAssays.
	 * 
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
	private static final long serialVersionUID = 1L;
    private String queryName;
	private StatisticalSignificanceDE statisticalSignificanceDE ;
	private StatisticTypeDE statisticTypeDE ;
	private MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE ;
	private ArrayPlatformDE arrayPlatformDE;
	private ExprFoldChangeDE exprFoldChangeDE;
	private List<ClinicalQueryDTO> comparisonGroups;
	private Collection<InstitutionDE> institutionDEs;
	
	
	private ISPYclinicalDataQueryDTO baselineGrpSpecifier;
	private ISPYclinicalDataQueryDTO comparisonGrpSpecifier;
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getMultiGroupComparisonAdjustmentTypeDE()
	 */
	public MultiGroupComparisonAdjustmentTypeDE getMultiGroupComparisonAdjustmentTypeDE() {
		return multiGroupComparisonAdjustmentTypeDE;
		
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setMultiGroupComparisonAdjustmentTypeDE(gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE)
	 */
	public void setMultiGroupComparisonAdjustmentTypeDE(
			MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE) {
		this.multiGroupComparisonAdjustmentTypeDE = multiGroupComparisonAdjustmentTypeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getStatisticalSignificanceDE()
	 */
	public StatisticalSignificanceDE getStatisticalSignificanceDE() {
		return statisticalSignificanceDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setStatisticalSignificanceDE(gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE)
	 */
	public void setStatisticalSignificanceDE(
			StatisticalSignificanceDE statisticalSignificanceDE) {
		this.statisticalSignificanceDE = statisticalSignificanceDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getStatisticTypeDE()
	 */
	public StatisticTypeDE getStatisticTypeDE() {
		return statisticTypeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setStatisticTypeDE(gov.nih.nci.caintegrator.dto.de.StatisticTypeDE)
	 */
	public void setStatisticTypeDE(StatisticTypeDE statisticTypeDE) {
		this.statisticTypeDE = statisticTypeDE;
	}
	public void setQueryName(String name) {
		this.queryName = name;
	}
	public String getQueryName() {
		return queryName;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getArrayPlatformDE()
	 */
	public ArrayPlatformDE getArrayPlatformDE() {
		return arrayPlatformDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setArrayPlatformDE(gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE)
	 */
	public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE) {
		this.arrayPlatformDE = arrayPlatformDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getExprFoldChangeDE()
	 */
	public ExprFoldChangeDE getExprFoldChangeDE() {
		return exprFoldChangeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setExprFoldChangeDE(gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE)
	 */
	public void setExprFoldChangeDE(ExprFoldChangeDE exprFoldChangeDE) {
		this.exprFoldChangeDE = exprFoldChangeDE;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#getComparisonGroups()
	 */
	public List<ClinicalQueryDTO> getComparisonGroups() {
		return comparisonGroups;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.critieria.ClassComparisonQueryDTO#setComparisonGroups(java.util.Collection)
	 */
	public void setComparisonGroups(List<ClinicalQueryDTO> comparisonGroups) {
		this.comparisonGroups = comparisonGroups;
	}
	/**
	 * @return Returns the institutionDEs.
	 */
	public Collection<InstitutionDE> getInstitutionDEs() {
		return institutionDEs;
	}
	/**
	 * @param institutionDEs The institutionDEs to set.
	 */
	public void setInstitutionDEs(Collection<InstitutionDE> institutionDEs) {
		this.institutionDEs = institutionDEs;
	}
//	public ISPYclinicalDataQueryDTO getBaselineGrpSpecifier() {
//		return baselineGrpSpecifier;
//	}
//	
//	public void setBaselineGrpSpecifier(ISPYclinicalDataQueryDTO baselineGrpSpecifier) {
//		this.baselineGrpSpecifier = baselineGrpSpecifier;
//	}
//	
//	public ISPYclinicalDataQueryDTO getComparisonGrpSpecifier() {
//		return comparisonGrpSpecifier;
//	}
//	
//	public void setComparisonGrpSpecifier(
//			ISPYclinicalDataQueryDTO comparisonGrpSpecifier) {
//		this.comparisonGrpSpecifier = comparisonGrpSpecifier;
//	}
	
	

	
	

    

}
