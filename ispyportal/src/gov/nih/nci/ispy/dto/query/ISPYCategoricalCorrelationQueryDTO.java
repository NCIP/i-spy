/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.dto.query;


import gov.columbia.c2b2.ispy.list.UserListN;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;

import java.util.ArrayList;

public class ISPYCategoricalCorrelationQueryDTO implements CorrelationQueryDTO {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ContinuousType continuousType1;    
    private String reporter1Name;    
    private ArrayList<UserListN> patientLists = new ArrayList<UserListN>();    
    private ArrayPlatformType platformTypeY;  
    private String geneY;
    private String queryName;

    public void setQueryName(String name) {
      this.queryName = name;        
    }


    public String getQueryName() {
      return queryName;
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
     * @return Returns the patientList.
     */
    public ArrayList<UserListN> getPatientLists() {
        return patientLists;
    }

    /**
     * @param patientList The patientList to set.
     */
    public void setPatientLists(ArrayList<UserListN> patientLists) {
        this.patientLists = patientLists;
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


    public ISPYCategoricalCorrelationQueryDTO() {
        super();
        // TODO Auto-generated constructor stub
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
