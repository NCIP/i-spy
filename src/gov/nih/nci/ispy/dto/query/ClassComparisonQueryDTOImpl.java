package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;

import java.util.Collection;
import java.util.List;




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
