package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;

import java.util.ArrayList;
import java.util.List;

public class ISPYListFilter {
	
	public static ListType[] values()	{

		ListType[] lsa = {ListType.PatientDID,ListType.Gene}; //no duplicates here
		
		return lsa;
	}
    
    public static List<ListSubType> getSubTypesForType(ListType lt) {
        //control the mapping between which subtypes are associated with a primary type
        //for example: when adding a new "Reporter List", the app needs to know
        //which subtypes "Reporter" has so we can add a "IMAGE CLONE REPORTER" list
        ArrayList<ListSubType> lsta = new ArrayList();
        if(lt == ListType.Reporter){
            //list the reporter subtypes here and return them
            //lsta.add(ListSubType.PROBE_SET);
            lsta.add(ListSubType.IMAGE_CLONE);
            lsta.add(ListSubType.DBSNP);
            //lsta.add(ListSubType.SNP_PROBESET);
        }
        else if(lt == ListType.Gene){
            //ListSubType[] lsta = {ListSubType.GENBANK_ACCESSION_NUMBER, ListSubType.GENESYMBOL, ListSubType.LOCUS_LINK};
            //lsta.add(ListSubType.GENBANK_ACCESSION_NUMBER);
            lsta.add(ListSubType.GENESYMBOL);
           // lsta.add(ListSubType.LOCUS_LINK);
        }
        //   Default, Custom, IMAGE_CLONE, PROBE_SET, SNPProbeSet, DBSNP, GENBANK_ACCESSION_NUMBER, GENESYMBOL, LOCUS_LINK;
        return lsta;
    }
}
