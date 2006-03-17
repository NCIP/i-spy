package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
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

  ER_POS { public Color getColor() { return Color.GREEN; }
           public String toString() { return "ER+"; }},
  ER_NEG { public Color getColor() { return Color.BLUE; }
           public String toString() { return "ER-";}},
  UNKNOWN { public Color getColor() { return Color.GRAY; }},
  MISSING { public Color getColor() { return Color.GRAY; }};
 

  public static List<ERstatusType>getDisplayValues(){
      List<ERstatusType> displayValues = new ArrayList<ERstatusType>();
      ERstatusType[] values = ERstatusType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(ERstatusType.MISSING) &&
                  !values[i].equals(ERstatusType.UNKNOWN)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
  public abstract Color getColor();
  
}
