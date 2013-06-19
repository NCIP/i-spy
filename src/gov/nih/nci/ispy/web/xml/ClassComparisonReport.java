/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.xml;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author LandyR
 * Feb 8, 2005
 * 
 */




public class ClassComparisonReport{

	/**
	 * 
	 */
	public ClassComparisonReport() {
		//super();
	}

	public static Document getReportXML(Finding finding, Map filterMapParams) {
		//changed the sig to include an allannotation flag, hence this wrapper method is born
		return getReportXML(finding, filterMapParams, false);
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	
	public static Document getReportXML(Finding finding, Map filterMapParams, boolean allAnnotations) {

		
		allAnnotations = true;  //force this for now ISPY prerelease - RCL 3/2
		
		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		DecimalFormat sciFormat = new DecimalFormat("0.00E0");
		DecimalFormat tmpsciFormat = new DecimalFormat("###0.0000#####################");
		
		/*
		 *  this is for filtering, we will want a p-value filter for CC
		 */
		ArrayList filter_string = new ArrayList();	// hashmap of genes | reporters | cytobands
		String filter_type = "show"; 		// show | hide
		String filter_element = "none"; 	// none | gene | reporter | cytoband

		if(filterMapParams.containsKey("filter_string") && filterMapParams.get("filter_string") != null)
			filter_string = (ArrayList) filterMapParams.get("filter_string");
		if(filterMapParams.containsKey("filter_type") && filterMapParams.get("filter_type") != null)		
			filter_type = (String) filterMapParams.get("filter_type");
		if(filterMapParams.containsKey("filter_element") && filterMapParams.get("filter_element") != null)		
			filter_element = (String) filterMapParams.get("filter_element");
			
		String defaultV = "--";
		String delim = " | ";
		
		Document document = DocumentHelper.createDocument();

			Element report = document.addElement( "Report" );
			Element cell = null;
			Element data = null;
			Element dataRow = null;
			//add the atts
	        report.addAttribute("reportType", "Class Comparison");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = "none";
	        queryName = finding.getTaskId();
	        
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
		    
			StringBuffer sb = new StringBuffer();
			
			int recordCount = 0;
			int totalSamples = 0;
			
			//TODO: instance of
			ClassComparisonFinding ccf = (ClassComparisonFinding) finding;
			
			
			//process the query details
			ArrayList<String> queryDetails = new ArrayList();
			ClassComparisonQueryDTO ccdto = (ClassComparisonQueryDTO)ccf.getQueryDTO();
			String reporterType = ccdto.getArrayPlatformDE().getValueObject();
			
			if(ccdto != null)	{
				String tmp = "";
				tmp = ccdto.getQueryName()!=null ? ccdto.getQueryName() : "";
				queryDetails.add("Query Name: " + tmp);
				tmp = ccdto.getArrayPlatformDE() != null ? ccdto.getArrayPlatformDE().getValue().toString() : "";
				queryDetails.add("Array Platform: " + tmp);
				
				tmp = "";
				List<ClinicalQueryDTO> grps = ccdto.getComparisonGroups()!=null ? ccdto.getComparisonGroups() : new ArrayList();
				Collection grs = new ArrayList();
				for(ClinicalQueryDTO cdto : grps)	{
					if(cdto.getQueryName()!=null)
						grs.add(cdto.getQueryName());
				}
				
				tmp += StringUtils.join(grs.toArray(), ", ") + " (baseline)";
				queryDetails.add("Groups: " + tmp);
					/*
					 noHTMLString = noHTMLString.replaceAll("<", "{");
					 noHTMLString = noHTMLString.replaceAll(">", "}");
					 noHTMLString = noHTMLString.replaceAll("&nbsp;", " ");
					 */
				tmp = ccdto.getExprFoldChangeDE() != null ? ccdto.getExprFoldChangeDE().getValue().toString() : "";
				queryDetails.add("Fold Change: " + tmp);
				//queryDetails.add("Institutions: " + ccdto.getInstitutionDEs());
				tmp = ccdto.getMultiGroupComparisonAdjustmentTypeDE()!=null ? ccdto.getMultiGroupComparisonAdjustmentTypeDE().getValue().toString() : "";
				queryDetails.add("Multi Group: " + tmp);			
				tmp = ccdto.getStatisticalSignificanceDE()!=null ? ccdto.getStatisticalSignificanceDE().getValue().toString() : "";
				queryDetails.add("Stat Sig.: " + tmp);
				tmp = ccdto.getStatisticTypeDE()!=null ? ccdto.getStatisticTypeDE().getValue().toString() : "";
				queryDetails.add("Stat Type: " + tmp);
			}
			/*
			queryDetails.add("Analysis Result name: " + ccform.getAnalysisResultName());
			queryDetails.add("Array Platform: " + ccform.getArrayPlatform());
			queryDetails.add("Baseline group: " + ccform.getBaselineGroup());
			queryDetails.add("Comparison Groups: " + ccform.getSelectedGroups()[0].toString());
			queryDetails.add("Comparison Adjustment: " + ccform.getComparisonAdjustment());
			//queryDetails.add("Comp. Adj. Coll: " + ccform.getComparisonAdjustmentCollection());
			//queryDetails.add("Existing Groups: " + ccform.getExistingGroups());
			//queryDetails.add("Existing group list: " + ccform.getExistingGroupsList());
			//queryDetails.add("Fold Change: " + ccform.getFoldChange());
			queryDetails.add("Fold Change auto: " + ccform.getFoldChangeAuto());
			//queryDetails.add("Fold change auto list: " + ccform.getFoldChangeAutoList());
			//queryDetails.add("Fold change manual: " + ccform.getFoldChangeManual());
			queryDetails.add("Stastic: " + ccform.getStatistic());
			queryDetails.add("Stastical method: " + ccform.getStatisticalMethod());
			//queryDetails.add("Stastical method coll.: " + ccform.getStatisticalMethodCollection());
			queryDetails.add("Stastical significance: " + ccform.getStatisticalSignificance());
			*/
			String qd = "";
			for(String q : queryDetails){
				qd += q + " ||| ";
			}
			
			
			if(ccf != null)	{
				
				Element details = report.addElement("Query_details");
				cell = details.addElement("Data");
				cell.addText(qd);
				cell = null;
				
				Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Reporter");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Group Avg");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
		        	String isAdj = ccf.arePvaluesAdjusted() ? " (Adjusted) " : "";
			        data = cell.addElement("Data").addAttribute("type", "header").addText("P-Value"+isAdj);
			        data = null;
		        cell = null;

			    cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Fold Change");
			        data = null;
		        cell = null;	
		        
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Gene Symbol");
			        data = null;
		        cell = null;
		        
		        //starting annotations...get them only if allAnnotations == true
		        if(allAnnotations)	{
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("GenBank Acc");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Locus link");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("GO Id");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Pathways");
				        data = null;
			        cell = null;
		        }
		        
		    	/* done with the headerRow and SampleRow Elements, time to add data rows */
		       
		        /*
		        Map<String,ReporterResultset> reporterResultsetMap = null;
		        reporterResultsetMap = ccf.getReporterAnnotationsMap();
		        */
		        
		        List<ClassComparisonResultEntry> classComparisonResultEntrys = ccf.getResultEntries();
				List<String> reporterIds = new ArrayList<String>();
				
				for (ClassComparisonResultEntry classComparisonResultEntry: classComparisonResultEntrys){
					if(classComparisonResultEntry.getReporterId() != null){
						reporterIds.add(classComparisonResultEntry.getReporterId());
					}
				}
				
				Map reporterResultsetMap = null;
				try {
                    GeneExprAnnotationService geService =  GeneExprAnnotationServiceFactory.getInstance();
					reporterResultsetMap = geService.getAnnotationsMapForReporters(reporterIds);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				//new stuff
				AnnotationHandler h = new AnnotationHandler();
				Map reporterResultsetMap = null;
				if(allAnnotations){
					//Map<String, ReporterAnnotations> reporterResultsetMap = null;
					try {
						reporterResultsetMap = h.getAllAnnotationsFor(reporterIds);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else	{
					//Map<String, String> reporterResultsetMap = null;
					try {
						reporterResultsetMap = h.getGeneSymbolsFor(reporterIds);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				*/
				
				
				
				/*
				//this looks like a failsafe for the old method
				if(reporterResultsetMap == null)	{
			        try {
			        	reporterResultsetMap = GeneExprAnnotationService.getAnnotationsMapForReporters(reporterIds);
			        }
			        catch(Exception e){}
		        }
		        */
				
		        for(ClassComparisonResultEntry ccre : ccf.getResultEntries())	{

		        	dataRow = report.addElement("Row").addAttribute("name", "dataRow");
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "reporter").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", reporterType).addText(ccre.getReporterId());
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(resultFormat.format(ccre.getMeanGrp1()) + " / " + resultFormat.format(ccre.getMeanBaselineGrp()));
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "pval").addAttribute("class", "data").addAttribute("group", "data");
			        	//String pv = (ccre.getPvalue() == null) ? String.valueOf(ccre.getPvalue()) : "N/A";
			        	String pv = defaultV;
			        	BigDecimal bigd;
			        	try	{
			        		bigd = new BigDecimal(ccre.getPvalue());
			        		pv = bigd.toPlainString();
			        	}
			        	catch (Exception e) {
			        		//missing value
						}
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(pv);
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(resultFormat.format(ccre.getFoldChange())));
			        	data = null;
			        cell = null;
			        
			        
			        //if only showing genes
			        if(!allAnnotations && reporterResultsetMap != null)	{
			        	String reporterId = ccre.getReporterId().toUpperCase().trim();
			        	String genes = reporterResultsetMap.get(reporterId)!=null ? (String)reporterResultsetMap.get(reporterId) : defaultV;
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(genes);
				        	data = null;
				        cell = null;
			        }
			        else	{
				        //get the gene symbols for this reporter
				        //ccre.getReporterId()
				        String genes = defaultV;
				        
				        //start annotations
				        String accIds = defaultV;
				        String llink = defaultV;
				        String go = defaultV;
				        String pw = defaultV;
				          
							if(reporterResultsetMap != null ){ // && reporterIds != null
								//int count = 0;
								String reporterId = ccre.getReporterId().toUpperCase().trim();
								//ReporterResultset reporterResultset = reporterResultsetMap.get(reporterId);
								ReporterAnnotation ra = (ReporterAnnotation) reporterResultsetMap.get(reporterId);
								
								//Collection<String> geneSymbols = (Collection<String>)reporterResultset.getAssiciatedGeneSymbols();
								if(ra!=null){
									List geneSymbols = ra.getGeneSymbols();
									//if(geneSymbols != null)	
									//	genes = geneSymbols.toString();
									
									if(geneSymbols != null){
										genes = StringUtils.join(geneSymbols.toArray(), delim);
									}
									
									Collection<String> genBank_AccIDS = (Collection<String>)ra.getGenbankAccessions();
									if(genBank_AccIDS != null){
										accIds = StringUtils.join(genBank_AccIDS.toArray(), delim);
									}
									Collection<String> locusLinkIDs = (Collection<String>)ra.getLocusLinkIds();
									if(locusLinkIDs != null){
										llink = StringUtils.join(locusLinkIDs.toArray(), delim);
									}
									Collection<String> goIds = (Collection<String>)ra.getGOIds();
									if(goIds != null){
										go = StringUtils.join(goIds.toArray(), delim);
									}
									Collection<String> pathways = (Collection<String>)ra.getPathwayIds();
									if(pathways != null){
										pw = StringUtils.join(pathways.toArray(), delim);
									}
								}
							}
	
						cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(genes);
				        	data = null;
				        cell = null;
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(accIds);
				        	data = null;
				        cell = null;
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(llink);
				        	data = null;
				        cell = null;
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(go);
				        	data = null;
				        cell = null;
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(pw);
				        	data = null;
				        cell = null;
				        
			        }
		        }
			}
			else {
				//TODO: handle this error
				sb.append("<br><Br>Class Comparison is empty<br>");
			}
		    
		    return document;
	}

}

