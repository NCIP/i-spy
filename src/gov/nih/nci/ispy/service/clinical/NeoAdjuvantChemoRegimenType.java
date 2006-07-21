package gov.nih.nci.ispy.service.clinical;

public enum NeoAdjuvantChemoRegimenType {
	AC   { public String toString() { return "AC"; }
		   public String getLookupString() { return toString();}} ,
	EC   { public String toString() { return "EC"; }
	       public String getLookupString() { return toString();}} ,
	FEC  { public String toString() { return "FAC"; }
	       public String getLookupString() { return toString();}},
	FAC  { public String toString() { return "FAC"; }
		   public String getLookupString() { return toString();}},
	A    { public String toString() { return "A"; }
	       public String getLookupString() { return toString();}},
	AC_TD { public String toString() { return "AC->Td"; }
	        public String getLookupString() { return toString();}},
	AC_TP { public String toString() { return "AC->Tp"; }
			public String getLookupString() { return toString();}},
	A_TD { public String toString() { return "A->Td"; }
		   public String getLookupString() { return toString();}},
	A_TP { public String toString() { return "A->Tp"; }
		   public String getLookupString() { return toString();}},
	AC_TDH { public String toString() { return "AC->TdH"; }
			 public String getLookupString() { return toString();}},
	AC_TPH { public String toString() { return "AC->TpH"; }
			 public String getLookupString() { return toString();}},
	FEC_TP { public String toString() { return "FEC->Tp"; }
			 public String getLookupString() { return toString();}},
	EC_TP { public String toString() { return "EC->Tp"; }
	   		public String getLookupString() { return toString();}},
	AC_TPTD { public String toString() { return "AC->TpTd"; }
			  public String getLookupString() { return toString();}},
	A_TP_C { public String toString() { return "A->Tp->C"; }
			 public String getLookupString() { return toString();}},
	ACV_TP { public String toString() { return "ACV->Tp"; }
	   		 public String getLookupString() { return toString();}},
	AC_TD_XELODA { public String toString() { return "AC->Td->Xeloda"; }
	  			   public String getLookupString() { return toString();}},
	EC_TP_CARBOPLATIN { public String toString() { return "EC->Tp->Carboplatin"; }
						public String getLookupString() { return toString();}},
	FEC_TP_ABRAXANE { public String toString() { return "FEC->Tp->Abraxane"; }
					  public String getLookupString() { return toString();}},
	AC_TD_NAVELBINE_XELODA { public String toString() { return "AC->Td->Navelbine->Xeloda"; }
	 						 public String getLookupString() { return toString();}},
	AC_TP_VINORELBINE_TARCENA { public String toString() { return "AC->Tp->Vinorelbine->Tarcena"; }
	 							public String getLookupString() { return toString();}};
   
	public abstract String getLookupString();
}
