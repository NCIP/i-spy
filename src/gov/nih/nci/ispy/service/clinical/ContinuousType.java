/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;





public enum ContinuousType implements Serializable {
	GENE { public String toString() { return "Gene"; }},
	PERCENT_LD_CHANGE { public String toString() { return "PERCENT_LD_CHANGE";}},
    PERCENT_LD_CHANGE_T1_T2{ public String toString() { return "PERCENT LD CHANGE_T1_T2";}},
    PERCENT_LD_CHANGE_T1_T3{ public String toString() { return "PERCENT_LD_CHANGE_T1_T3";}},
    PERCENT_LD_CHANGE_T1_T4{ public String toString() { return "PERCENT_LD_CHANGE_T1_T4";}};
	
	public static TimepointType getEndpoint(ContinuousType ct) {
	  switch(ct) {
	  case PERCENT_LD_CHANGE_T1_T2 : return TimepointType.T2;
	  case PERCENT_LD_CHANGE_T1_T3: return TimepointType.T3;
	  case PERCENT_LD_CHANGE_T1_T4: return TimepointType.T4;
	  }
	  return null;
	}
	
}

