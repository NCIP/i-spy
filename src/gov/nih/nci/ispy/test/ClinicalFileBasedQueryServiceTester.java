package gov.nih.nci.ispy.test;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.GeneExprFileBasedAnnotationService;
import gov.nih.nci.ispy.service.clinical.ClinicalData;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.DiseaseStageType;
import gov.nih.nci.ispy.service.clinical.TimepointType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
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
	  clinicalQS.setClinicalDataFile("C:\\dev\\ispyportal\\WebRoot\\WEB-INF\\data_files\\ispy_clinical_data_sample.txt");
	}
	catch (IOException ex) {
	  ex.printStackTrace(System.out);
	}
	
//	List<String> labtrackIds = new ArrayList<String>();
//	labtrackIds.add("209515");
//	labtrackIds.add("209521");
//	labtrackIds.add("212619");
	
	
	
	try {
		
	  ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
	  dto.setTimepointValues(EnumSet.of(TimepointType.T2));
	  dto.setDiseaseStageValues(EnumSet.of(DiseaseStageType.II_A, DiseaseStageType.II_B));
	  dto.setClinicalResponseValues(EnumSet.of(ClinicalResponseType.PD, ClinicalResponseType.SD));
	  
	  Set<String> labtrackIds = clinicalQS.getLabtrackIds(dto);
	  
	  
	  
	  //List<ClinicalData> clinicalDataList = clinicalQS.getClinicalDataForLabtrackIds(labtrackIds);
	  //Set<String> labtrackIds = clinicalQS.getLabtrackIdsForTimepoint(TimepointType.T1);
	  List<ClinicalData> clinicalDataList = clinicalQS.getClinicalDataForLabtrackIds(new ArrayList(labtrackIds));
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
