package gov.nih.nci.ispy.test;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.ispy.service.annotation.GeneExprFileBasedAnnotationService;
import gov.nih.nci.ispy.service.clinical.ClinicalData;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClinicalFileBasedQueryServiceTester {

public ClinicalFileBasedQueryServiceTester() {
	super();
	// TODO Auto-generated constructor stub
}
	
	

public static void main(String[] args) {
	// TODO Auto-generated method stub
	
	ClinicalFileBasedQueryService clinicalQS = (ClinicalFileBasedQueryService) ClinicalFileBasedQueryService.getInstance();
	
	try {
	  clinicalQS.setClinicalDataFile("C:\\eclipse\\workspace\\ispyportal\\WebRoot\\WEB-INF\\data_files\\ispy_clinical_data_sample.txt");
	}
	catch (IOException ex) {
	  ex.printStackTrace(System.out);
	}
	
	List<String> labtrackIds = new ArrayList<String>();
	labtrackIds.add("209515");
	labtrackIds.add("209521");
	
	try {
	  List<ClinicalData> clinicalDataList = clinicalQS.getClinicalDataForLabtrackIds(labtrackIds);
	  System.out.println("clinicalDataList=" + clinicalDataList.size());
	  for (ClinicalData cd : clinicalDataList) {
	     dumpClinicalData(cd);
	  }
	}
	catch (Exception ex) {
	  ex.printStackTrace(System.out);
	}

}



private static void dumpStringCollection(Collection<String> strings) {

  for (Iterator i=strings.iterator(); i.hasNext(); ) {
	 String str = (String) i.next();
	 System.out.print(str);
	 if (i.hasNext()) System.out.print("|");
  }
	
}

private static void dumpClinicalData(ClinicalData clinicalData) {
  System.out.println();
  System.out.println(clinicalData);
  System.out.println("===========================");
}
	
}
