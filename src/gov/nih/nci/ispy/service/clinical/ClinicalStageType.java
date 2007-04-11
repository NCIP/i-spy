package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Disease Stage 
 * 
 * @author harrismic
 *
 */
public enum ClinicalStageType implements Serializable {

	STAGE0 { public Color getColor() { return Color.GRAY; }
	    public String toString() { return "Stage 0"; }},
	
	
	I { public Color getColor() { return Color.GRAY; }
	    public String toString() { return "Stage I"; }},
	
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
	        
    IV { public Color getColor() { return Color.CYAN.brighter(); }
        public String toString() { return "Stage 4"; }},
	
	UNKNOWN { public Color getColor() { return Color.GRAY; }},
	
	MISSING { public Color getColor() { return Color.GRAY; }};
	
	
    public static List<ClinicalStageType>getDisplayValues(){
          List<ClinicalStageType> displayValues = new ArrayList<ClinicalStageType>();
          ClinicalStageType[] values = ClinicalStageType.values();
          for(int i =0; i < values.length; i++){
              if(!values[i].equals(ClinicalStageType.UNKNOWN) &&
                      !values[i].equals(ClinicalStageType.MISSING)){
                  displayValues.add(values[i]);
                  
              }
          }
          return displayValues;
      }
    
    public static ClinicalStageType getTypeforValue(int value) {
		  /*
		   * Clinical Staging at Baseline
1= Stage 0
2= Stage I
3= Stage IIA
4=Stage IIB
5= Stage IIIA
6= Stage IIIB
7=Stage IIIC
8= Stage IV

		   */
		  
		  switch(value) {
		  case 1: return ClinicalStageType.STAGE0;
		  case 2: return ClinicalStageType.I;
		  case 3: return ClinicalStageType.II_A;
		  case 4: return ClinicalStageType.II_B;
		  case 5: return ClinicalStageType.III_A;
		  case 6: return ClinicalStageType.III_B;
		  case 7: return ClinicalStageType.III_C;
		  case 8: return ClinicalStageType.IV;
		  default:return ClinicalStageType.UNKNOWN;
		  }
    }
    
    public abstract Color getColor();
}
