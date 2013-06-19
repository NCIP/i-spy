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
 * Estrogen Receptor status
 * 
 * @author harrismic
 *
 */
public enum ERstatusType implements Serializable {

  ER_POS { public Color getColor() { return Color.GREEN; }
           public String toString() { return "ER+"; }
  		   public boolean show() { return true;}},
  ER_NEG { public Color getColor() { return Color.BLUE; }
           public String toString() { return "ER-";}
           public boolean show() { return true;}},
  UNKNOWN { public Color getColor() { return Color.GRAY; }
            public boolean show() { return false;}},
  MISSING { public Color getColor() { return Color.GRAY; }
            public boolean show() { return false;}};
 

  public static List<ERstatusType>getDisplayValues(){
      List<ERstatusType> displayValues = new ArrayList<ERstatusType>();
      ERstatusType[] values = ERstatusType.values();
      for(int i =0; i < values.length; i++){
          if(values[i].show()){
              displayValues.add(values[i]);   
          }
      }
      return displayValues;
  }
  
  public abstract Color getColor();
  public abstract boolean show();
  
  public static ERstatusType getTypeForString(String erStr) {
    if ((erStr == null)||(erStr.trim().length()==0)) {
      return MISSING;
    }
    
    String str = erStr.toUpperCase();
    if (str.startsWith("POS")) {
      return ER_POS;
    }
    
    if (str.startsWith("NEG")) {
      return ER_NEG;
    }
    return UNKNOWN;
  }
  
}
