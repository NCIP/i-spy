package gov.nih.nci.ispy.test;

import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.ispy.service.annotation.GeneExprFileBasedAnnotationService;

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
		
		  long startTime = System.currentTimeMillis();
		  gxannot.setAnnotationFile("C:\\eclipse\\workspace\\ispyportal\\WebRoot\\WEB-INF\\data_files\\ispy_gene_annotations.txt");
		  long elapsedTime = System.currentTimeMillis() - startTime;
		  System.out.println("elapsed time=" + elapsedTime);
	
		
		
		List<String> reporterIds = new ArrayList<String>();
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100001");
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100011");
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100022");
		reporterIds.add("AGI_HUM1_OLIGO_A_23_P100056");
		
		
		try {
		  Map<String, ReporterAnnotation> gxannotMap = gxannot.getAnnotationsMapForReporters(reporterIds);
		  dumpMap(gxannotMap);
		  System.out.println("mapSize=" + gxannotMap.size());
		}
		catch (Exception ex) {
		  ex.printStackTrace(System.out);
		}

	}
	
	public static void dumpMap(Map<String,ReporterAnnotation> geneAnnotMap) {
	
		Set<String> keys = geneAnnotMap.keySet();
		ReporterAnnotation reporterAnnotation;
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
	
	private static void dumpReporterAnnotation(ReporterAnnotation reporterAnnotation) {
	  System.out.println("ReporterName=" +reporterAnnotation.getReporterId());
	  System.out.print("  GeneSymbols:");
	  dumpStringCollection(reporterAnnotation.getGeneSymbols());
	  System.out.println();
	  
	  System.out.print("  GenbankAcc:");
	  dumpStringCollection(reporterAnnotation.getGenbankAccessions());
	  System.out.println();
	  
	  System.out.print("  LocusLink: ");
	  dumpStringCollection(reporterAnnotation.getLocusLinkIds());
	  System.out.println();
	  
	  System.out.print("  Pathways");
	  dumpStringCollection(reporterAnnotation.getPathwayIds());
	  System.out.println();
	  
	  System.out.print("  GO:");
	  dumpStringCollection(reporterAnnotation.getGOIds());
	  System.out.println();
	  System.out.println("=============================");
	}

}
