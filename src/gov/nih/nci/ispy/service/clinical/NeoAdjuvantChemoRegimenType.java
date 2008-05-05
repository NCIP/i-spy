package gov.nih.nci.ispy.service.clinical;

public enum NeoAdjuvantChemoRegimenType {
	AC   { public String toString() { return "1=AC"; }
		   public String getLookupString() { return toString();}} ,
	EC   { public String toString() { return "2=EC"; }
	       public String getLookupString() { return toString();}} ,
	FEC  { public String toString() { return "3=FEC"; }
	       public String getLookupString() { return toString();}},
	FAC  { public String toString() { return "4=FAC"; }
		   public String getLookupString() { return toString();}},
	A    { public String toString() { return "5=A"; }
	       public String getLookupString() { return toString();}},
	AC_TD { public String toString() { return "6=AC->Td"; }
	        public String getLookupString() { return toString();}},
	AC_TP { public String toString() { return "7=AC->Tp"; }
			public String getLookupString() { return toString();}},
	A_TD { public String toString() { return "8=A->Td"; }
		   public String getLookupString() { return toString();}},
	A_TP { public String toString() { return "9=A->Tp"; }
		   public String getLookupString() { return toString();}},
	AC_Tp { public String toString() { return "10=AC->Tp"; }
			public String getLookupString() { return toString();}},
	AC_TDH { public String toString() { return "12=AC->TdH"; }
			 public String getLookupString() { return toString();}},
	AC_TPH { public String toString() { return "13=AC->TpH"; }
			 public String getLookupString() { return toString();}},
	FEC_TP { public String toString() { return "14=FEC->Tp"; }
			 public String getLookupString() { return toString();}},
	EC_TP { public String toString() { return "15=EC->Tp"; }
	   		public String getLookupString() { return toString();}},
	AC_TPTD { public String toString() { return "16=AC->TpTd"; }
			  public String getLookupString() { return toString();}},
	A_TP_C { public String toString() { return "17=A->Tp->C"; }
			 public String getLookupString() { return toString();}},
	ACV_TP { public String toString() { return "18=ACV->Tp"; }
	   		 public String getLookupString() { return toString();}},
	AC_TD_XELODA { public String toString() { return "19=AC->Td->Xeloda"; }
	  			   public String getLookupString() { return toString();}},
	EC_TP_CARBOPLATIN { public String toString() { return "20=EC->Tp->Carboplatin"; }
						public String getLookupString() { return toString();}},
	FEC_TP_ABRAXANE { public String toString() { return "21=FEC->Tp->Abraxane"; }
					  public String getLookupString() { return toString();}},
	AC_TD_NAVELBINE_XELODA { public String toString() { return "22=AC->Td->Navelbine->Xeloda"; }
	 						 public String getLookupString() { return toString();}},
	AC_TP_VINORELBINE_TARCENA { public String toString() { return "23=AC->Tp->Vinorelbine->Tarcena"; }
	 							public String getLookupString() { return toString();}},
	C_TP { public String toString() { return "24=C->Tp"; }
	 							public String getLookupString() { return toString();}},
	ECTP_TP { public String toString() { return "25=ECTp->Tp"; }
	 							public String getLookupString() { return toString();}};
   
   
	public abstract String getLookupString();
	
	/**
	 * Get the enum value corresponding to chemoStr. valueOf could not be used because
	 * of special characters in the data(i.e.,   -> ) which are not allowed in an enum name. 
	 * @param chemoStr
	 * @return
	 */
	public static  NeoAdjuvantChemoRegimenType getValueForString(String chemoStr) {
	  NeoAdjuvantChemoRegimenType[] chemoValues = NeoAdjuvantChemoRegimenType.values();
	  String  lookupStr;
	  
	  if (chemoStr == null) {
		 return null;
	  }
	  
	  for (int i=0; i < chemoValues.length; i++) {
		 lookupStr = chemoValues[i].getLookupString();
	     if (chemoStr.equalsIgnoreCase(lookupStr)) {
	    	return chemoValues[i];
	     }
	  }
	  return null;
	}
}
