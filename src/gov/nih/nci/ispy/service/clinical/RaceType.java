package gov.nih.nci.ispy.service.clinical;

public enum RaceType {
	American_Indian_or_Alaska_Native { public String toString() { return "American Indian or Alaska Native"; }
    								   public String getLookupString() { return toString();}} ,
	Asian { public String toString() { return "Asian"; }
    		public String getLookupString() { return toString();}} ,
	Black_or_African_American { public String toString() { return "Black or African American"; }
    							public String getLookupString() { return toString();}} ,
	Native_Hawaiian_or_Pacific_Islander { public String toString() { return "Native Hawaiian or Pacific Islander"; }
    									  public String getLookupString() { return toString();}} ,
	White { public String toString() { return "White"; }
    		public String getLookupString() { return toString();}}, 
    Unknown  { public String toString() { return "Unknown"; }
			   public String getLookupString() { return toString();}};
	
	
	public abstract String getLookupString();
		
	/**
	 * Get the enum value corresponding to chemoStr. valueOf could not be used because
	 * of special characters in the data(i.e.,   -> ) which are not allowed in an enum name. 
	 * @param chemoStr
	 * @return
	 */
	public static  RaceType getValueForString(String raceStr) {
	  RaceType[] raceValues = RaceType.values();
	  String  lookupStr;
	  
	  if (raceStr == null) {
		 return null;
	  }
	  
	  for (int i=0; i < raceValues.length; i++) {
		 lookupStr = raceValues[i].getLookupString();
	     if (raceStr.equalsIgnoreCase(lookupStr)) {
	    	return raceValues[i];
	     }
	  }
	  return null;
	}
	
}
