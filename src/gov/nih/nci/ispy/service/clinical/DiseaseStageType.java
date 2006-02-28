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
	II_All{
        public String toString()
        { 
            return "Stage 2_All";
        }},
        II_A{
            public String toString()
            { 
                return "Stage 2_A";
            }},
            II_B{
                public String toString()
                { 
                    return "Stage 2_B";
                }},
                III_All{
                    public String toString()
                    { 
                        return "Stage 3_All";
                    }},
                    III_A{
                        public String toString()
                        { 
                            return "Stage 3_A";
                        }},
                        III_B{
                            public String toString()
                            { 
                                return "Stage 3_B";
                            }},
                            III_C{
                                public String toString()
                                { 
                                    return "Stage 3_C";
                                }},
                                Unknown,Missing;
    
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
