package gov.nih.nci.ispy.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import gov.nih.nci.caintegrator.application.lists.ListType;

public class ISPYListFilter {
	
	public static ListType[] values()	{

		ListType[] lsa = {ListType.PatientDID,ListType.GeneSymbol}; //no duplicates here
		
		return lsa;
	}
}
