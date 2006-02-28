package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Disease Stage 
 * 
 * @author harrismic
 *
 */
public enum DiseaseStageType implements Serializable {
	II_All,II_A,II_B,III_All,III_A,III_B,III_C,Unknown,Missing;
    
    public static List<DiseaseStageType>getDisplayValues(){
          List<DiseaseStageType> displayValues = new ArrayList<DiseaseStageType>();
          DiseaseStageType[] values = DiseaseStageType.values();
          for(int i =0; i < values.length; i++){
              if(!values[i].equals(DiseaseStageType.Unknown) &&
                      !values[i].equals(DiseaseStageType.Missing)){
                  displayValues.add(values[i]);
                  
              }
          }
          return displayValues;
      }
}
