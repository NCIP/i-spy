package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;

public enum PcrType implements Serializable {
  Yes{ public String toString() { return "Yes";}
       public String getLookupString() { return toString();}} ,
  No{  public String toString() { return "No";}
       public String getLookupString() { return toString();}};
	
  
  
  
  public abstract String getLookupString();
	
	/**
	 * Get the enum value corresponding to pcrStr	
	 * @return
	 */
	public static  PcrType getValueForString(String pcrStr) {
		PcrType[] pcrValues = PcrType.values();
	     String  lookupStr;
	  
	  if (pcrStr == null) {
		 return null;
	  }
	  
	  for (int i=0; i < pcrValues.length; i++) {
		 lookupStr = pcrValues[i].getLookupString();
	     if (pcrStr.equalsIgnoreCase(lookupStr)) {
	    	return pcrValues[i];
	     }
	  }
	  return null;
	}
}
