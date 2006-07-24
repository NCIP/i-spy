package gov.nih.nci.ispy.service.clinical;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
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
        
        Set<String> patientDIDs = cqs.getPatientDIDsForNeoAdjuvantChemoRegimen(chemoRegimen);
        
        dumpDIDs(patientDIDs);

	}
	
	public static void dumpDIDs(Set<String> DIDlist) {
	  for (String id: DIDlist) {
	    System.out.println(id);
	  }
	}

}
