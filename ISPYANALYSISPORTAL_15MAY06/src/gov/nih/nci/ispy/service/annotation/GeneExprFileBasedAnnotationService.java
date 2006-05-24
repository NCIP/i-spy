package gov.nih.nci.ispy.service.annotation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.*;

import org.apache.log4j.Logger;

import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.caintegrator.application.util.StringUtils;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
//import gov.nih.nci.caintegrator.application.service.annotation.ReporterResultset;
//import gov.nih.nci.caintegrator.dto.de.DatumDE;

public class GeneExprFileBasedAnnotationService extends GeneExprAnnotationService {

	private String annotationFileName;  //full path name to the annotation file.
	private static GeneExprFileBasedAnnotationService instance = null;
	//private Map<String, ReporterResultset> reporterMap = new HashMap<String, ReporterResultset>(55000);
	private Map<String, ReporterAnnotation> reporterMap = new HashMap<String, ReporterAnnotation>(55000);
	private Map<String, Set<ReporterAnnotation>> gene2reporterMap = new HashMap<String, Set<ReporterAnnotation>>(45000);
	private boolean annotationFileSet = false;
	private static Logger logger = Logger.getLogger(GeneExprFileBasedAnnotationService.class);

	
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
	public int setAnnotationFile(String annotationFileName)  {
	  //set and load the annotation file
	  //set the instance variable 
		
	  int recordCount = 0;
		
	  try {
		  
		  this.annotationFileName = annotationFileName;
		
		  BufferedReader in = new BufferedReader(new FileReader(annotationFileName));
		  String line = null;
		  String reporterName = null;
		  String geneSymbolsStr = null;
		  String genbankAccsStr = null;
		  String locusLinkIdsStr = null;
		  String pathwaysStr = null;
		  String goIdsStr = null;
		  //ReporterResultset reporterAnnotation = null;
		  ReporterAnnotation reporterAnnotation = null; 
		  
		  //Annotation file has the format 
		  //ReporterName\tGeneSymbol\tGenbankAcc\tLocusLinkId\tPathway\tGO
		  
		  //Pattern pattern = Pattern.compile("(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)\t(\\S*)");
		  Pattern pattern = Pattern.compile("([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)");
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
		   
		    
		    //@TODO need to parse the array platform type out of the file
		    reporterAnnotation = new ReporterAnnotation(reporterName, ArrayPlatformType.AGILENT);
		    
		    //reporterAnnotation = new ReporterResultset(new DatumDE(DatumDE.PROBESET_ID, reporterName));
		
		    //System.out.println(">> Setting data for reporter reporterName=" + reporterName);
		    
		
		    geneSymbols = StringUtils.extractTokens(geneSymbolsStr, "\\|");
		    if (!geneSymbols.isEmpty()) {
		      reporterAnnotation.setGeneSymbols(geneSymbols);
		      
		      //add the reporter annotation to the gene symbol map
		      Set<ReporterAnnotation> reporters = null;
		      for (String geneSymbol : geneSymbols) {
		        reporters = gene2reporterMap.get(geneSymbol);
		        if (reporters == null) {
		          reporters = new HashSet<ReporterAnnotation>(3);
		          gene2reporterMap.put(geneSymbol, reporters);
		        }
		        reporters.add(reporterAnnotation);
		      }
		      
		      
		    }
		    //System.out.println("   geneSymbols:  " + geneSymbolsStr);
		    
		 
		    genbankAccessions = StringUtils.extractTokens(genbankAccsStr, "\\|");
		    if (!genbankAccessions.isEmpty()) {
		      reporterAnnotation.setGenbankAccessions(genbankAccessions);
		    }
		    //System.out.println("    genbankAcc: " + genbankAccsStr);
		    
		    
		  
		    locusLinkIds = StringUtils.extractTokens(locusLinkIdsStr, "\\|");
		    if (!locusLinkIds.isEmpty()) {
		      reporterAnnotation.setLocusLinkIds(locusLinkIds);
		    }
		    //System.out.println("   locusLinkIds: " + locusLinkIdsStr);
		    
		    
		  
		    pathways = StringUtils.extractTokens(pathwaysStr, "\\|");
		    if (!pathways.isEmpty()) {
		      reporterAnnotation.setPathwayIds(pathways);
		    }
		    //System.out.println("   pathwaysStr: " + pathwaysStr);
		    
		    
		
		    goIds = StringUtils.extractTokens(goIdsStr, "\\|");
		    if (!goIds.isEmpty()) {
		      reporterAnnotation.setGOIds(goIds);
		    }
		    //System.out.println("  goIdsStr: " + goIdsStr);
		    
		    
		    reporterMap.put(reporterName, reporterAnnotation);
		    recordCount++;
			  
		  }
		  
		  annotationFileSet = true;
		  logger.info("Successfully loaded gene expression annotation fileName=" + annotationFileName + " recordCount=" + recordCount);
	  }
	  catch (IOException ex) {
	    logger.error("Caught IOException while loading gene annotation file=" + annotationFileName + " recordCount=" + recordCount);
	    logger.error(ex);
	    return -recordCount;
	  }
	  return recordCount;
	}
	
	

	public static GeneExprAnnotationService getInstance() {
		
	  if (instance == null) {
	    instance = new GeneExprFileBasedAnnotationService();
	  }
	  return instance;
	}


	public Map<String, ReporterAnnotation> getAnnotationsMapForReporters(List<String> reporterIDs) throws Exception {
		
		if (!annotationFileSet) {
		  throw new IllegalStateException("Must call setAnnotationFile() before calling getAnnotationsMapForReporters().");
		}
		
		Map<String, ReporterAnnotation> returnMap = new HashMap<String, ReporterAnnotation>();
		
	
		ReporterAnnotation reporterAnnotation;
		for (String reporterId: reporterIDs) {
		  reporterAnnotation = reporterMap.get(reporterId);
		  
		  if (reporterAnnotation != null) {
		    returnMap.put(reporterId, reporterAnnotation);
		  }
		}
		
		
		return returnMap;
	}

	public List<ReporterAnnotation> getAnnotationsListForReporters(List<String> reporterIDs) throws Exception {
		
		if (!annotationFileSet) {
		  throw new IllegalStateException("Must call setAnnotationFile() before calling getAnnotationsMapForReporters().");
		}
		
		List<ReporterAnnotation> returnList = new ArrayList<ReporterAnnotation>();
		
	
		ReporterAnnotation reporterAnnotation;
		for (String reporterId: reporterIDs) {
		  reporterAnnotation = reporterMap.get(reporterId);
		  
		  if (reporterAnnotation != null) {
		    returnList.add(reporterAnnotation);
		  }
		}
		
		
		return returnList;
	}
	
	/**
	 * Get the reporters for a given gene symbol
	 * @param geneSymbol
	 * @return
	 */
	public Set<ReporterAnnotation> getReportersForGeneSymbol(String geneSymbol) {
	   Set<ReporterAnnotation> reporters = gene2reporterMap.get(geneSymbol);
	   return reporters;
	}
	
	/**
	 * Get the reporters for a collection of gene symbols
	 * @param geneSymbols
	 * @return
	 */
	public Set<String> getReporterNamesForGeneSymbols(Collection<String> geneSymbols, ArrayPlatformType arrayPlatform) {
		
	
		
		Set<String> reporterNames = new HashSet<String>();
		if (geneSymbols == null) {
		  return Collections.emptySet();
		}
		
		Set<ReporterAnnotation> reporters = null;
		for (String geneSymbol:geneSymbols) {
		  reporters = getReportersForGeneSymbol(geneSymbol);
		  if (reporters != null) {
			  for (ReporterAnnotation reporter : reporters) {
				if (arrayPlatform == reporter.getArrayPlatform()) {
			      reporterNames.add(reporter.getReporterId());
				}
			  }
		  }
		  else {
			  logger.warn("No reporters found for gene symbol=" + geneSymbol + " for arrayPlatform=" + arrayPlatform);
		  }
		}
		return reporterNames;
	}

	public String getAnnotationFileName() {
		return annotationFileName;
	}
	
}