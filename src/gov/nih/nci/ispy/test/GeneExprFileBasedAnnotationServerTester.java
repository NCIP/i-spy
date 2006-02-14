package gov.nih.nci.ispy.test;

import gov.nih.nci.ispy.service.annotation.GeneExprFileBasedAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GeneExprFileBasedAnnotationServerTester {

	public GeneExprFileBasedAnnotationServerTester() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GeneExprFileBasedAnnotationService gxannot = (GeneExprFileBasedAnnotationService) GeneExprFileBasedAnnotationService.getInstance();
		try {
		  gxannot.setAnnotationFile("C:\\eclipse\\workspace\\ispyportal\\WebRoot\\WEB-INF\\data_files\\ispy_gene_annotations.txt");
		}
		catch (IOException ex) {
		  ex.printStackTrace(System.out);
		}
		
		List<String> reporterIds = new ArrayList<String>();
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100001");
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100011");
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100022");
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100056");
		
		
		try {
		  Map gxannotMap = gxannot.getAnnotationsMapForReporters(reporterIds);
		  System.out.println("mapSize=" + gxannotMap.size());
		}
		catch (Exception ex) {
		  ex.printStackTrace(System.out);
		}

	}

}
