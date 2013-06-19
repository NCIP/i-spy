/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.clinical;

public enum AgeCategoryType {
	AGE_18_30  { public String toString() { return "1=  18-30"; }
	             public String getLookupString() { return toString();}} ,
	AGE_30_40  { public String toString() { return "2= >30-40"; }
    			 public String getLookupString() { return toString();}} ,
	AGE_40_50  { public String toString() { return "3= >40-50"; }
	 			 public String getLookupString() { return toString();}} ,	 
    
	AGE_50_60  { public String toString() { return "4= >50-60"; }
   			     public String getLookupString() { return toString();}} ,			 
    
    AGE_60_70  { public String toString() { return "5= >60-70"; }
                 public String getLookupString() { return toString();}} ,    
                 
   	AGE_70_80  { public String toString() { return "6= >70-80"; }
	 			 public String getLookupString() { return toString();}} ,	 		 
	
	AGE_80_89  { public String toString() { return "7= >80-89"; }
	 			 public String getLookupString() { return toString();}} ,	 		 	
	
	AGE_GE_89_OR_NA  { public String toString() { return "> =89 not available"; }
	 			       public String getLookupString() { return toString();}};
	 			 
	 			 
	 public abstract String getLookupString();
	 			
		/**
		 * Get the enum value corresponding to chemoStr. valueOf could not be used because
		 * of special characters in the data(i.e.,   -> ) which are not allowed in an enum name. 
		 * @param chemoStr
		 * @return
		 */
		public static  AgeCategoryType getValueForString(String ageStr) {
		  AgeCategoryType[] ageValues = AgeCategoryType.values();
		  String  lookupStr;
		  
		  if (ageStr == null) {
			 return null;
		  }
		  
		  for (int i=0; i < ageValues.length; i++) {
			 lookupStr = ageValues[i].getLookupString();
		     if (ageStr.equalsIgnoreCase(lookupStr)) {
		    	return ageValues[i];
		     }
		  }
		  return null;
		}

}
