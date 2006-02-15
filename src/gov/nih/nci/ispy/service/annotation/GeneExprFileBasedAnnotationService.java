package gov.nih.nci.ispy.service.annotation;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.*;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterResultset;
import gov.nih.nci.caintegrator.dto.de.DatumDE;

public class GeneExprFileBasedAnnotationService extends GeneExprAnnotationService  {

	private String annotationFileName;  //full path name to the annotation file.
	private static GeneExprFileBasedAnnotationService instance = null;
	private Map<String, ReporterResultset> reporterMap = new HashMap<String, ReporterResultset>();
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
	  ReporterResultset reporterAnnotation = null;
	   
	  //Annotation file has the format 
	  //ReporterName\tGeneSymbol\tGenbankAcc\tLocusLinkId\tPathway\tGO
	  
	  Pattern pattern = Pattern.compile("(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)");
	  
	  //reset the map
	  reporterMap.clear();
	  Matcher matcher = null;
	  
	  List<String> geneSymbols = null;
	  List<String> genbankAccessions = null;
	  List<String> locusLinkIds = null;
	  List<String> pathways = null;
	  List<String> goIds = null;
	  
	  while ((line=in.readLine())!= null) {
	    
		//System.out.println("processing line=" + line);
		  
		matcher = pattern.matcher(line);
		
		if (!matcher.find()) {
		  throw new IOException("Annotation file has a format problem.");
		}
		
	    reporterName = matcher.group(1);
	    geneSymbolsStr = matcher.group(2);
	    genbankAccsStr = matcher.group(3);
	    locusLinkIdsStr = matcher.group(4); 
	    pathwaysStr = matcher.group(5);
	    goIdsStr = matcher.group(6);
	   
	    reporterAnnotation = new ReporterResultset(new DatumDE(DatumDE.PROBESET_ID, reporterName));
	
	    //System.out.println(">> Setting data for reporter reporterName=" + reporterName);
	    
	
	    geneSymbols = extractTokens(geneSymbolsStr, "\\|");
	    if (!geneSymbols.isEmpty()) {
	      reporterAnnotation.setAssociatedGeneSymbols(geneSymbols);
	    }
	    //System.out.println("   geneSymbols:  " + geneSymbolsStr);
	    
	 
	    genbankAccessions = extractTokens(genbankAccsStr, "\\|");
	    if (!genbankAccessions.isEmpty()) {
	      reporterAnnotation.setAssiciatedGenBankAccessionNos(genbankAccessions);
	    }
	    //System.out.println("    genbankAcc: " + genbankAccsStr);
	    
	    
	  
	    locusLinkIds = extractTokens(locusLinkIdsStr, "\\|");
	    if (!locusLinkIds.isEmpty()) {
	      reporterAnnotation.setAssociatedLocusLinkIDs(locusLinkIds);
	    }
	    //System.out.println("   locusLinkIds: " + locusLinkIdsStr);
	    
	    
	  
	    pathways = extractTokens(pathwaysStr, "\\|");
	    if (!pathways.isEmpty()) {
	      reporterAnnotation.setAssociatedPathways(pathways);
	    }
	    //System.out.println("   pathwaysStr: " + pathwaysStr);
	    
	    
	
	    goIds = extractTokens(goIdsStr, "\\|");
	    if (!goIds.isEmpty()) {
	      reporterAnnotation.setAssociatedGOIds(goIds);
	    }
	    //System.out.println("  goIdsStr: " + goIdsStr);
	    
	    
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
	private List<String> extractTokens(String line, String delim) {
	  List<String> tokenCollection = new ArrayList<String>();
	  if (line != null) {
	    String[] tokens = line.split(delim);
	  
	    for (int i=0; i < tokens.length; i++) {
	      tokenCollection.add(tokens[i].trim());
	    }
	  }
	  return tokenCollection;
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
		
		Map<String, ReporterResultset> returnMap = new HashMap<String, ReporterResultset>();
		
	
		ReporterResultset reporterAnnotation;
		for (String reporterId: reporterIDs) {
		  reporterAnnotation = reporterMap.get(reporterId);
		  
		  if (reporterAnnotation != null) {
		    returnMap.put(reporterId, reporterAnnotation);
		  }
		}
		
		
		return returnMap;
	}

}
