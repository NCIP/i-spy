/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.columbia.c2b2.ispy.list.UserListN;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ISPYGPIntegrationQueryDTO implements GpIntegrationQueryDTO {
	private static final long serialVersionUID = 1L;
    private String queryName;
    private String reportersName;
	private ArrayPlatformDE arrayPlatformDE;
	private ReporterGroup reporterGroup; 
	private SampleGroup[] sampleGroups;
	private Collection<GeneIdentifierDE> geneIdentifierDEs;
	private Collection<CloneIdentifierDE> reporterIdentifierDEs;

	private HashMap<String, UserListN> patientLists; 

	private HashMap<String, Set<TimepointType>> timepointLists;
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

	public Collection<GeneIdentifierDE> getGeneIdentifierDEs() {
		return geneIdentifierDEs;
	}
	public void setGeneIdentifierDEs(Collection<GeneIdentifierDE> geneIdentifierDEs) {
		this.geneIdentifierDEs = geneIdentifierDEs;
	}
	public Collection<CloneIdentifierDE> getReporterIdentifierDEs() {
		return reporterIdentifierDEs;
	}
	public void setReporterIdentifierDEs(
			Collection<CloneIdentifierDE> reporterIdentifierDEs) {
		this.reporterIdentifierDEs = reporterIdentifierDEs;
	}

	public ReporterGroup getReporterGroup() {
		return reporterGroup;
	}
	public void setReporterGroup(ReporterGroup reporterGroup) {
		this.reporterGroup = reporterGroup;
	}
	public SampleGroup[] getSampleGroups() {
		return sampleGroups;
	}
	public void setSampleGroups(SampleGroup[] sampleGroups) {
		this.sampleGroups = sampleGroups;
	}

	public HashMap<String, UserListN> getPatientLists() {
		return patientLists;
	}
	public void setPatientLists(HashMap<String, UserListN> patientLists) {
		this.patientLists = patientLists;
	}
	public HashMap<String, Set<TimepointType>> getTimepointLists() {
		return timepointLists;
	}
	public void setTimepointLists(HashMap<String, Set<TimepointType>> timepointLists) {
		this.timepointLists = timepointLists;
	}
	public String getReportersName() {
		return reportersName;
	}
	public void setReportersName(String reportersName) {
		this.reportersName = reportersName;
	}
}
