/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

  
  CR { public Color getColor() { return Color.GREEN; }
  	   public String toString() { return "Complete Response"; }},
  	   
  PR { public Color getColor() { return Color.YELLOW; }
       public String toString() {return "Partial Response"; }},
       
  SD { public Color getColor() { return Color.BLUE; }
       public String toString() {return "Stable Disease"; }},
       
  PD { public Color getColor() { return Color.RED; }
       public String toString() { return "Progressive Disease";}},
       
  INTER_REGIMEN_CHEMO_NOT_GIVEN { public Color getColor() { return Color.GRAY; }
  		public String toString() { return "Inter-Regimen Chemo not given";}},
  		
  NO_SURGERY_PERFORMED { public Color getColor() { return Color.GRAY; }
	public String toString() { return "No surgery performed";}},
       
  UNKNOWN { public Color getColor() { return Color.GRAY; }},
  
  MISSING { public Color getColor() { return Color.GRAY; }}, 
  
  NA { public Color getColor() { return Color.GRAY; }};
  
  /**
   * This method displays only the permissiable values for display purposes
   *
   */
  public static List<ClinicalResponseType>getDisplayValues(){
      List<ClinicalResponseType> displayValues = new ArrayList<ClinicalResponseType>();
      ClinicalResponseType[] values = ClinicalResponseType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(ClinicalResponseType.MISSING) &&
                  !values[i].equals(ClinicalResponseType.UNKNOWN) &&
                  !values[i].equals(ClinicalResponseType.NA)){
              displayValues.add(values[i]);
          }
      }
      return displayValues;
  }
  
  public abstract Color getColor();
  
  public static ClinicalResponseType getTypeForValue(int value) {
	    
	  switch(value) {
	  case 1: return ClinicalResponseType.CR;
	  case 2: return ClinicalResponseType.PR;
	  case 3: return ClinicalResponseType.SD;
	  case 4: return ClinicalResponseType.PD;
	  case 5: return ClinicalResponseType.INTER_REGIMEN_CHEMO_NOT_GIVEN;
	  case 6: return ClinicalResponseType.NO_SURGERY_PERFORMED;
	  default: return ClinicalResponseType.UNKNOWN;
	  }
	  
	  
  }
  
}
