package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum HER2statusType implements Serializable {
  HER2_POS, HER2_NEG,Unknown,Missing;
  
  public static List<HER2statusType>getDisplayValues(){
      List<HER2statusType> displayValues = new ArrayList<HER2statusType>();
      HER2statusType[] values = HER2statusType.values();
      for(int i =0; i < values.length; i++){
          if(!values[i].equals(HER2statusType.Missing) &&
                  !values[i].equals(HER2statusType.Unknown)){
              displayValues.add(values[i]);
              
          }
      }
      return displayValues;
  }
  
}
