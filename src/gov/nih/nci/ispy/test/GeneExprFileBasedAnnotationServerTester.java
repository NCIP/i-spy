package gov.nih.nci.ispy.test;

import gov.nih.nci.ispy.service.annotation.GeneExprFileBasedAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterResultset;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
		  Map<String, ReporterResultset> gxannotMap = gxannot.getAnnotationsMapForReporters(reporterIds);
		  dumpMap(gxannotMap);
		  System.out.println("mapSize=" + gxannotMap.size());
		}
		catch (Exception ex) {
		  ex.printStackTrace(System.out);
		}

	}
	
	public static void dumpMap(Map<String,ReporterResultset> geneAnnotMap) {
	
		Set<String> keys = geneAnnotMap.keySet();
		ReporterResultset reporterAnnotation;
		for (String key:keys) {
		  reporterAnnotation = geneAnnotMap.get(key);
		  dumpReporterAnnotation(reporterAnnotation);
		}
	}

	private static void dumpStringCollection(Collection<String> strings) {
	
	  for (Iterator i=strings.iterator(); i.hasNext(); ) {
		 String str = (String) i.next();
		 System.out.print(str);
		 if (i.hasNext()) System.out.print("|");
	  }
		
	}
	
	private static void dumpReporterAnnotation(ReporterResultset reporterAnnotation) {
	  System.out.println("ReporterName=" +reporterAnnotation.getReporter().getValue());
	  System.out.print("  GeneSymbols:");
	  dumpStringCollection(reporterAnnotation.getAssociatedGeneSymbols());
	  System.out.println();
	  
	  System.out.print("  GenbankAcc:");
	  dumpStringCollection(reporterAnnotation.getAssiciatedGenBankAccessionNos());
	  System.out.println();
	  
	  System.out.print("  LocusLink: ");
	  dumpStringCollection(reporterAnnotation.getAssociatedLocusLinkIDs());
	  System.out.println();
	  
	  System.out.print("  Pathways");
	  dumpStringCollection(reporterAnnotation.getAssociatedPathways());
	  System.out.println();
	  
	  System.out.print("  GO:");
	  dumpStringCollection(reporterAnnotation.getAssociatedGOIds());
	  System.out.println();
	  System.out.println("=============================");
	}

}
