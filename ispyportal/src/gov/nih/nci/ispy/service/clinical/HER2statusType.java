package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum HER2statusType implements Serializable {

  HER2_POS { public Color getColor() { return Color.GREEN; }
             public String toString() { return "HER2+"; }
             public boolean show() { return true;}},
  HER2_NEG { public Color getColor() { return Color.BLUE; }
             public String toString() { return "HER2-"; }
             public boolean show() { return true; }},
  HER2_INDETERMINATE { 
             public Color getColor() { return Color.GRAY; }
             public String toString() { return "HER2?"; }
             public boolean show() { return false; }},
  UNKNOWN { public Color getColor() { return Color.GRAY; }
            public boolean show() { return false;}},
  MISSING { public Color getColor() { return Color.GRAY; }
            public boolean show() { return false;}};
    
  public static List<HER2statusType>getDisplayValues(){
      List<HER2statusType> displayValues = new ArrayList<HER2statusType>();
      HER2statusType[] values = HER2statusType.values();
      for(int i =0; i < values.length; i++){
          if(values[i].show()){
              displayValues.add(values[i]);  
          }
      }
      return displayValues;
  }
  
  public static HER2statusType getTypeForString(String str) {
	
	if ((str==null)||(str.trim().length()==0)) return MISSING;
	
	String strUC =  str.toUpperCase();
	
	if  (strUC.startsWith("POS")) {
	  return HER2_POS;
	}
	
	if (strUC.startsWith("NEG")) {
	  return HER2_NEG;
	}
	
	if (strUC.startsWith("INDET")) {
	  return HER2_INDETERMINATE;
	}
	
	return UNKNOWN;
	  
  }
  
  public abstract Color getColor();
  public abstract boolean show();
  
}
