package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;

/**
 * Clinical Response Type:
 * 	CR  -  Complete Response
 *  PR  -  Partial Response
 *  SD  -  Stable Disease
 *  PD  -  Progressive Disease
 *  
 * @author harrismic
 *
 */
public enum ClinicalResponseType implements Serializable{
  CR,PR,SD,PD,Unknown,Missing
}
