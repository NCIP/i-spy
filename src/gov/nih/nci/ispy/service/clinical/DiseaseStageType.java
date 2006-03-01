package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

/**
 * Disease Stage 
 * 
 * @author harrismic
 *
 */
public enum DiseaseStageType implements Serializable {

	II_ALL { public Color getColor() { return Color.YELLOW; }
	         public String toString() { return "Stage 2_All";}},
	         
	II_A { public Color getColor() { return Color.ORANGE; }
	       public String toString() { return "Stage 2_A";}},
	       
	II_B { public Color getColor() { return Color.GREEN; }
	       public String toString() { return "Stage 2_B";}},
	
	III_ALL { public Color getColor() { return Color.RED; }
	          public String toString() { return "Stage 3_All"; }},
	
	III_A { public Color getColor() { return Color.MAGENTA; }
	        public String toString() { return "Stage 3_A";}},
	
	III_B { public Color getColor() { return Color.PINK; }
	        public String toString() { return "Stage 3_B";}},
	
	III_C { public Color getColor() { return Color.CYAN; }
	        public String toString() { return "Stage 3_C"; }},
	
	UNKNOWN { public Color getColor() { return Color.GRAY; }},
	
	MISSING { public Color getColor() { return Color.GRAY; }};
	
	
    public static List<DiseaseStageType>getDisplayValues(){
          List<DiseaseStageType> displayValues = new ArrayList<DiseaseStageType>();
          DiseaseStageType[] values = DiseaseStageType.values();
          for(int i =0; i < values.length; i++){
              if(!values[i].equals(DiseaseStageType.UNKNOWN) &&
                      !values[i].equals(DiseaseStageType.MISSING)){
                  displayValues.add(values[i]);
                  
              }
          }
          return displayValues;
      }
    
    public abstract Color getColor();
}
