package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
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
	
  PR_POS { public Color getColor() { return Color.GREEN; }
           public String toString() {return "PR+"; }},
  PR_NEG { public Color getColor() { return Color.BLUE; }
           public String toString() {return "PR-";}},
  UNKNOWN { public Color getColor() { return Color.GRAY; }},
  MISSING { public Color getColor() { return Color.GRAY; }};
    
  public static List<PRstatusType>getDisplayValues(){
      List<PRstatusType> displayValues = new ArrayList<PRstatusType>();
      PRstatusType[] values = PRstatusType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(PRstatusType.MISSING) &&
                  !values[i].equals(PRstatusType.UNKNOWN)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
  public abstract Color getColor();

  public static PRstatusType getTypeForString(String str) {
	if ((str==null)||(str.trim().length()==0)) return MISSING;
	
	String strUC = str.toUpperCase();
	
	if (strUC.startsWith("POS")) { return PR_POS; }
	
	if (strUC.startsWith("NEG")) { return PR_NEG; }
	
	return UNKNOWN;
	
	
  }
  
}
