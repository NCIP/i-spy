package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class ClinicalFileBasedServiceTester {

	public ClinicalFileBasedServiceTester() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClinicalFileBasedQueryService cqs = ClinicalFileBasedQueryService.getInstance();
        //String clinicalDataFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ispy_clinical_data_14MARCH06.txt";
        //logger.info("Initializing file based clinical data service fileName=" + clinicalDataFileName);
        //int clinRecordsLoaded  = cqs.setClinicalDataFile(clinicalDataFileName);
        //logger.info("Clinical data service initialized successfully loaded numRecords=" + clinRecordsLoaded);
     

        //String patientDataFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ispy_patient_data_5_11.txt";
        String patientDataFileName = args[0];
        System.out.println("Clinical data service loading patient data fileName=" + patientDataFileName);
        int patientRecordsLoaded = cqs.setPatientDataMap(patientDataFileName);
        System.out.println("Clinical data service successfully loaded patient data numRecords=" + patientRecordsLoaded);
        
        EnumSet<NeoAdjuvantChemoRegimenType> chemoRegimen = EnumSet.of(NeoAdjuvantChemoRegimenType.AC_TD);
        
        EnumSet<HER2statusType> her2 = EnumSet.of(HER2statusType.HER2_POS);
        
        ISPYclinicalDataQueryDTO cDTO = new ISPYclinicalDataQueryDTO();
        
        cDTO.setAgentValues(chemoRegimen);
        cDTO.setHer2StatusValues(her2);
        
        Set<String> restrainingSamples = new HashSet<String>();
        
        restrainingSamples.add("1086");
        restrainingSamples.add("1015");
        restrainingSamples.add("1061");
        restrainingSamples.add("1077");
        restrainingSamples.add("1093");
        restrainingSamples.add("1115");
        
        cDTO.setRestrainingSamples(restrainingSamples);
        
        Set<String> patientDIDs = cqs.getPatientDIDs(cDTO);
        
        dumpDIDs(patientDIDs);

	}
	
	public static void dumpDIDs(Set<String> DIDlist) {
	  for (String id: DIDlist) {
	    System.out.println(id);
	  }
	}

}
