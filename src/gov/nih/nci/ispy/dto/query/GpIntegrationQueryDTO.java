package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.Collection;
import java.util.List;





public interface GpIntegrationQueryDTO extends QueryDTO{
	/**
	 * @return Returns the arrayPlatformDE.
	 */
	public ArrayPlatformDE getArrayPlatformDE();

	/**
	 * @param arrayPlatformDE The arrayPlatformDE to set.
	 */
	public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE);

	/**
	 * @return Returns the geneIdentifierDEs.
	 */
	public abstract Collection<GeneIdentifierDE> getGeneIdentifierDEs();

	/**
	 * @param geneIdentifierDEs The geneIdentifierDEs to set.
	 */
	public abstract void setGeneIdentifierDEs(
			Collection<GeneIdentifierDE> geneIdentifierDEs);

	/**
	 * @return Returns the reporterIdentifierDEs.
	 */
	public abstract Collection<CloneIdentifierDE> getReporterIdentifierDEs();

	/**
	 * @param reporterIdentifierDEs The reporterIdentifierDEs to set.
	 */
	public abstract void setReporterIdentifierDEs(
			Collection<CloneIdentifierDE> reporterIdentifierDEs);
}
