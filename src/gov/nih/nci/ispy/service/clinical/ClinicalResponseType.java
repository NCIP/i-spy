package gov.nih.nci.ispy.service.clinical;

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
    CR{
    public String toString()
    { 
        return "Complete Response";
    }},
        PR{
        public String toString()
        { 
            return "Partial Response";
        }},
            SD{
            public String toString()
            { 
                return "Stable Disease";
            }},
                PD{
                    public String toString()
                    { 
                        return "Progressive Disease";
                    }},
                        Unknown,Missing;
  /**
   * This method displays only the permissiable values for display purposes
   *
   */
  public static List<ClinicalResponseType>getDisplayValues(){
      List<ClinicalResponseType> displayValues = new ArrayList<ClinicalResponseType>();
      ClinicalResponseType[] values = ClinicalResponseType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(ClinicalResponseType.Missing) &&
                  !values[i].equals(ClinicalResponseType.Unknown)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
}
