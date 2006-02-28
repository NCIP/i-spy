package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Estrogen Receptor status
 * 
 * @author harrismic
 *
 */
public enum ERstatusType implements Serializable {
  ER_POS{
      public String toString()
      { 
          return "ER+";
      }},
      ER_NEG{
          public String toString()
          { 
              return "ER-";
          }},Unknown,Missing;
  
  public static List<ERstatusType>getDisplayValues(){
      List<ERstatusType> displayValues = new ArrayList<ERstatusType>();
      ERstatusType[] values = ERstatusType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(ERstatusType.Missing) &&
                  !values[i].equals(ERstatusType.Unknown)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
}
