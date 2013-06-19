/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.annotation;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.caintegrator.application.util.StringUtils;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
//import gov.nih.nci.caintegrator.application.service.annotation.ReporterResultset;
//import gov.nih.nci.caintegrator.dto.de.DatumDE;

public class GeneExprFileBasedAnnotationService implements GeneExprAnnotationService {

	private String annotationFileName;  //full path name to the annotation file.
	private static GeneExprFileBasedAnnotationService instance = null;
	//private Map<String, ReporterResultset> reporterMap = new HashMap<String, ReporterResultset>(55000);
	private Map<String, ReporterAnnotation> reporterMap = new HashMap<String, ReporterAnnotation>(55000);
	private Map<String, Set<ReporterAnnotation>> gene2reporterMap = new HashMap<String, Set<ReporterAnnotation>>(45000);
	private boolean annotationFileSet = false;
	private String arrayTypeStr;
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
		  
		  //Pattern pattern = Pattern.compile("([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)");
		  
		  //reset the map
		  reporterMap.clear();
		  //Matcher matcher = null;
		  
		  List<String> geneSymbols = null;
		  List<String> genbankAccessions = null;
		  List<String> locusLinkIds = null;
		  List<String> pathways = null;
		  List<String> goIds = null;
		  
		  String[] tokens;
		  while ((line=in.readLine())!= null) {
		    
			//System.out.println("processing line=" + line);
			  
//			matcher = pattern.matcher(line);
//			
//			if (!matcher.find()) {
//			  throw new IOException("Annotation file has a format problem.");
//			}
			  
			tokens = line.split("\t", -2);
			
//		    reporterName = matcher.group(1);
//		    geneSymbolsStr = matcher.group(2);
//		    genbankAccsStr = matcher.group(3);
//		    locusLinkIdsStr = matcher.group(4); 
//		    pathwaysStr = matcher.group(5);
//		    goIdsStr = matcher.group(6);
			
			reporterName = tokens[0];
			geneSymbolsStr = tokens[1];
			arrayTypeStr =  tokens[2];
			
			if (tokens.length > 3) {
			  genbankAccsStr = tokens[3];
			}
			else {
			  genbankAccsStr = "";
			}
			
			if (tokens.length > 4) {
			  locusLinkIdsStr = tokens[4];
			}
			else {
			  locusLinkIdsStr = "";
			}
			
			if (tokens.length > 5) {
			  pathwaysStr = tokens[5];
			}
			else {
			  pathwaysStr = "";
			}
			
			if (tokens.length > 6) {
			  goIdsStr = tokens[6];
			}
			else {
			  goIdsStr = "";
			}
		    
		    //@TODO need to parse the array platform type out of the file
			if (arrayTypeStr.equalsIgnoreCase("AGILENT")) {
		      reporterAnnotation = new ReporterAnnotation(reporterName, ArrayPlatformType.AGILENT);
			}
			else if (arrayTypeStr.equalsIgnoreCase("CDNA")) {
				reporterAnnotation = new ReporterAnnotation(reporterName, ArrayPlatformType.CDNA_ARRAY_PLATFORM);
			}
					
		    
		    //reporterAnnotation = new ReporterResultset(new DatumDE(DatumDE.PROBESET_ID, reporterName));
		
		    //System.out.println(">> Setting data for reporter reporterName=" + reporterName);
		    
		
		    geneSymbols = StringUtils.extractTokens(geneSymbolsStr, "\\|");
		    if (!geneSymbols.isEmpty()) {
		      reporterAnnotation.setGeneSymbols(geneSymbols);
		      
		      //add the reporter annotation to the gene symbol map
		      Set<ReporterAnnotation> reporters = null;
		      for (String geneSymbol : geneSymbols) {
		        //reporters = gene2reporterMap.get(geneSymbol);
		    	reporters = getReportersForGeneSymbol(geneSymbol);
		        if (reporters == null) {
		          reporters = new HashSet<ReporterAnnotation>(3);
		          //gene2reporterMap.put(geneSymbol, reporters);
		          storeGene2ReporterMapping(geneSymbol, reporters);
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
		    
		    storeReporterMapping(reporterName, reporterAnnotation);
		    //reporterMap.put(reporterName, reporterAnnotation);
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
	  catch (Exception ex2) {
	    logger.error("Caught Exception while loading gene annotation file=" + annotationFileName + " recordCount=" + recordCount);
	    logger.error(ex2);
	    return -recordCount;
	  }
	  return recordCount;
	}
	
	/**
	 * Method handles case issues when storing to the hash map
	 * @param geneSymbol
	 * @param reporters
	 */
	private void storeGene2ReporterMapping(String geneSymbol, Set<ReporterAnnotation> reporters) {
	  String geneSymbolUC = geneSymbol.toUpperCase().trim();
	  gene2reporterMap.put(geneSymbolUC, reporters);
	}
	
	/**
	 * Method handles case issues when storing to the hash map
	 * @param reporterName
	 * @param annotation
	 */
	private void storeReporterMapping(String reporterName, ReporterAnnotation annotation) {
	  String reporterNameUC = reporterName.toUpperCase().trim();
	  reporterMap.put(reporterNameUC, annotation);
	}
	
	

	public static GeneExprAnnotationService getInstance() {
		
	  if (instance == null) {
         //if no previous instance of service, load up the annotation file
	    instance = new GeneExprFileBasedAnnotationService();
        String annotFileName = System.getProperty("gov.nih.nci.ispyportal.data_directory") + System.getProperty("gov.nih.nci.ispyportal.gx_annotation_file");
        logger.info("Initializing GeneExprAnnotationService file=" + annotFileName);
        
        //set file and calculate duration and number of records loaded
        int gxRecLoaded = instance.setAnnotationFile(annotFileName);           
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Finished initializing GeneExprAnnotationService file=" + annotFileName + " time=" + elapsedTime + " numRecords=" + gxRecLoaded);
           
           
	  }
	  return instance;
	}


	public Map<String, ReporterAnnotation> getAnnotationsMapForReporters(List<String> reporterIDs) throws Exception {

		if (!annotationFileSet) {
		  throw new IllegalStateException("Must call setAnnotationFile() before calling getAnnotationsMapForReporters().");
		}
		
		Map<String, ReporterAnnotation> returnMap = new HashMap<String, ReporterAnnotation>();
		
	
		ReporterAnnotation reporterAnnotation;
		String reporterIdUC = null;
		for (String reporterId: reporterIDs) {
		  //reporterAnnotation = reporterMap.get(reporterId);
		  reporterIdUC = reporterId.toUpperCase().trim();
		  reporterAnnotation = getAnnotationForReporter(reporterIdUC);
		  
		  if (reporterAnnotation != null) {
		    returnMap.put(reporterIdUC, reporterAnnotation);
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
		  //reporterAnnotation = reporterMap.get(reporterId);
		  reporterAnnotation = getAnnotationForReporter(reporterId);
		  if (reporterAnnotation != null) {
		    returnList.add(reporterAnnotation);
		  }
		}
		
		
		return returnList;
	}
	
	/**
	 * Handles case issues with looking up reporterIds
	 * @param reporterId
	 * @return
	 */
	private ReporterAnnotation getAnnotationForReporter(String reporterId) {
	  String reporterIdUC = reporterId.toUpperCase().trim();
	  ReporterAnnotation annotation = reporterMap.get(reporterIdUC);
	  
	  return annotation;
	  
	}
	
	/**
	 * Get the reporters for a given gene symbol
	 * @param geneSymbol
	 * @return
	 */
	public Set<ReporterAnnotation> getReportersForGeneSymbol(String geneSymbol) {
	   String geneSymbolUC = geneSymbol.toUpperCase().trim();
	   Set<ReporterAnnotation> reporters = gene2reporterMap.get(geneSymbolUC);
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
