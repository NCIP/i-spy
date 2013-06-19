/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.CorrelationType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;

public class ISPYCorrelationScatterQueryDTO implements CorrelationQueryDTO {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ContinuousType continuousType1;
    private ContinuousType continuousType2;
    private String reporter1Name;
    private String reporter2Name;
    private CorrelationType correlationType;
    private UserList patientList;
    private ArrayPlatformType platformTypeX;
    private ArrayPlatformType platformTypeY;
    private String geneX;
    private String geneY;
    private String queryName;

    public void setQueryName(String name) {
      this.queryName = name;        
    }


    public String getQueryName() {
      return queryName;
    }
    
    /**
     * @return Returns the geneX.
     */
    public String getGeneX() {
        return geneX;
    }

    /**
     * @param geneX The geneX to set.
     */
    public void setGeneX(String geneX) {
        this.geneX = geneX;
    }

    /**
     * @return Returns the geneY.
     */
    public String getGeneY() {
        return geneY;
    }

    /**
     * @param geneY The geneY to set.
     */
    public void setGeneY(String geneY) {
        this.geneY = geneY;
    }

    /**
     * @return Returns the continuousType1.
     */
    public ContinuousType getContinuousType1() {
        return continuousType1;
    }

    /**
     * @param continuousType1 The continuousType1 to set.
     */
    public void setContinuousType1(ContinuousType continuousType1) {
        this.continuousType1 = continuousType1;
    }

    /**
     * @return Returns the continuousType2.
     */
    public ContinuousType getContinuousType2() {
        return continuousType2;
    }

    /**
     * @param continuousType2 The continuousType2 to set.
     */
    public void setContinuousType2(ContinuousType continuousType2) {
        this.continuousType2 = continuousType2;
    }

    /**
     * @return Returns the correlationType.
     */
    public CorrelationType getCorrelationType() {
        return correlationType;
    }

    /**
     * @param correlationType The correlationType to set.
     */
    public void setCorrelationType(CorrelationType correlationType) {
        this.correlationType = correlationType;
    }

    /**
     * @return Returns the patientList.
     */
    public UserList getPatientList() {
        return patientList;
    }

    /**
     * @param patientList The patientList to set.
     */
    public void setPatientList(UserList patientList) {
        this.patientList = patientList;
    }

    /**
     * @return Returns the reporter1Name.
     */
    public String getReporter1Name() {
        return reporter1Name;
    }

    /**
     * @param reporter1Name The reporter1Name to set.
     */
    public void setReporter1Name(String reporter1Name) {
        this.reporter1Name = reporter1Name;
    }

    /**
     * @return Returns the reporter2Name.
     */
    public String getReporter2Name() {
        return reporter2Name;
    }

    /**
     * @param reporter2Name The reporter2Name to set.
     */
    public void setReporter2Name(String reporter2Name) {
        this.reporter2Name = reporter2Name;
    }

    public ISPYCorrelationScatterQueryDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    

    /**
     * @return Returns the platformTypeX.
     */
    public ArrayPlatformType getPlatformTypeX() {
        return platformTypeX;
    }

    /**
     * @param platformTypeX The platformTypeX to set.
     */
    public void setPlatformTypeX(ArrayPlatformType platformTypeX) {
        this.platformTypeX = platformTypeX;
    }

    /**
     * @return Returns the platformTypeY.
     */
    public ArrayPlatformType getPlatformTypeY() {
        return platformTypeY;
    }

    /**
     * @param platformTypeY The platformTypeY to set.
     */
    public void setPlatformTypeY(ArrayPlatformType platformTypeY) {
        this.platformTypeY = platformTypeY;
    }

}
