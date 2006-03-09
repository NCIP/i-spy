package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum HER2statusType implements Serializable {

  HER2_POS { public Color getColor() { return Color.GREEN; }
             public String toString() { return "HER2+"; }},
  HER2_NEG { public Color getColor() { return Color.BLUE; }
             public String toString() { return "HER2-"; }},
  UNKNOWN { public Color getColor() { return Color.GRAY; }},
  MISSING { public Color getColor() { return Color.GRAY; }};
    
  public static List<HER2statusType>getDisplayValues(){
      List<HER2statusType> displayValues = new ArrayList<HER2statusType>();
      HER2statusType[] values = HER2statusType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(HER2statusType.MISSING) &&
                  !values[i].equals(HER2statusType.UNKNOWN)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
  public abstract Color getColor();
  
}
