package gov.nih.nci.ispy.service.annotation;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterResultset;
import gov.nih.nci.caintegrator.dto.de.DatumDE;

public class GeneExprFileBasedAnnotationService extends GeneExprAnnotationService  {

	private String annotationFileName;  //full path name to the annotation file.
	private static GeneExprFileBasedAnnotationService instance = null;
	private Map<String, ReporterResultset> reporterMap = new HashMap();
	private boolean annotationFileSet = false;
	
	
	private GeneExprFileBasedAnnotationService() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	/**
	 * Loads the specified annotation file and sets the instance variable.
	 * This instance will be returned by subsequent calls to getInstance().
	 * 
	 * @param annotationFileName
	 */
	public void setAnnotationFile(String annotationFileName) throws IOException {
	  //set and load the annotation file
	  //set the instance variable 
	  BufferedReader in = new BufferedReader(new FileReader(annotationFileName));
	  String line = null;
	  String reporterName = null;
	  String geneSymbolsStr = null;
	  String genbankAccsStr = null;
	  String locusLinkIdsStr = null;
	  String pathwaysStr = null;
	  String goIdsStr = null;
	  StringTokenizer t = null;
	  ReporterResultset reporterAnnotation = null;
	  
	  List<String> geneSymbols = new ArrayList<String>();
	  List<String> genbankAccessions = new ArrayList<String>();
	  List<String> locusLinkIds = new ArrayList<String>();
	  List<String> pathways = new ArrayList<String>();
	  List<String> goIds = new ArrayList<String>();
	  
	  
	  //reset the map
	  reporterMap.clear();
	  
	  while ((line=in.readLine())!= null) {
	    t = new StringTokenizer(line, "\t");
		
	    reporterName = t.nextToken();
	    
	    geneSymbolsStr = null;
	    if (t.hasMoreTokens()) {
	      geneSymbolsStr = t.nextToken();
	    }
	   
	    
	    genbankAccsStr = null;
	    if (t.hasMoreTokens()) {
	      genbankAccsStr = t.nextToken();
	    }
	    
	    locusLinkIdsStr = null;
	    if (t.hasMoreTokens()) {
	      locusLinkIdsStr = t.nextToken();
	    }
	    
	    pathwaysStr = null;
	    if (t.hasMoreTokens()) {
	      pathwaysStr = t.nextToken();
	    }
	    
	    goIdsStr = null;
	    if (t.hasMoreTokens()) {
	      goIdsStr = t.nextToken();
	    }
	    
	    reporterAnnotation = new ReporterResultset(new DatumDE(DatumDE.PROBESET_ID, reporterName));
	
		geneSymbols.clear();
	    extractTokens(geneSymbols, geneSymbolsStr, "|");
	    reporterAnnotation.setAssociatedGeneSymbols(geneSymbols);
	    
	    
	    genbankAccessions.clear();
	    extractTokens(genbankAccessions, genbankAccsStr, "|");
	    reporterAnnotation.setAssiciatedGenBankAccessionNos(genbankAccessions);
	    
	    locusLinkIds.clear();
	    extractTokens(locusLinkIds, locusLinkIdsStr, "|");
	    reporterAnnotation.setAssociatedLocusLinkIDs(locusLinkIds);
	    
	    pathways.clear();
	    extractTokens(pathways, pathwaysStr, "|");
	    reporterAnnotation.setAssociatedPathways(pathways);
	    
	    goIds.clear();
	    extractTokens(goIds, goIdsStr, "|");
	    reporterAnnotation.setAssociatedGOIds(goIds);
	    
	    reporterMap.put(reporterName, reporterAnnotation);
		  
	  }
	  
	  annotationFileSet = true;
	  
	}
	
	

	/**
	 * This method will extract tokens from a string containing multiple tokens 
	 * and place the tokens in a collection.  This is used for example, to parse strings containing multiple GO or PATHWAY ids.
	 * @param tokenCollection
	 * @param tokenStr
	 * @param delimeter
	 */
	private void extractTokens(List<String> tokenCollection, String tokenStr, String delimeter) {
	  if (tokenStr == null) return;
	  StringTokenizer t = new StringTokenizer(tokenStr, delimeter);
	  while (t.hasMoreTokens()) {
	    tokenCollection.add(t.nextToken().trim());
	  }
	}



	@Override
	public static GeneExprAnnotationService getInstance() {
		
	  if (instance == null) {
	    instance = new GeneExprFileBasedAnnotationService();
	  }
	  return instance;
	}

	@Override
	public Map<String, ReporterResultset> getAnnotationsMapForReporters(List<String> reporterIDs) throws Exception {
		
		if (!annotationFileSet) {
		  throw new IllegalStateException("Must call setAnnotationFile() before calling getAnnotationsMapForReporters().");
		}
		
		return reporterMap;
	}

}
