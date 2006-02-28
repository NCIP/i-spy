package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Progresterone receptor status
 * 
 * @author harrismic
 *
 */
public enum PRstatusType implements Serializable {
  PR_POS{
      public String toString()
      { 
          return "PR+";
      }},
      PR_NEG{
          public String toString()
          { 
              return "PR-";
          }},Unknown,Missing;
  
  public static List<PRstatusType>getDisplayValues(){
      List<PRstatusType> displayValues = new ArrayList<PRstatusType>();
      PRstatusType[] values = PRstatusType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(PRstatusType.Missing) &&
                  !values[i].equals(PRstatusType.Unknown)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
}
