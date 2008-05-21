package gov.nih.nci.ispy.web.reports.quick;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class QuickReporterReport {

	public static StringBuffer quickReporterReport(List<String> reporters,String session,String taskId){
		StringBuffer html = new StringBuffer();
		Document document = DocumentHelper.createDocument();
		Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
		Element tr = null;
		Element td = null;
		
		tr = table.addElement("tr").addAttribute("class", "header");
		td = tr.addElement("td").addAttribute("class", "header").addText("Reporter");
		td = tr.addElement("td").addAttribute("class", "header").addText("Gene Symbol");
		td = tr.addElement("td").addAttribute("class", "header").addText("GenBank AccId");
		td = tr.addElement("td").addAttribute("class", "header").addText("LocusLink");
		
		if(reporters != null)	{
			try {
                GeneExprAnnotationService geService =  GeneExprAnnotationServiceFactory.getInstance();
                
				List<ReporterAnnotation> reporterAnnotations = geService.getAnnotationsListForReporters(reporters);
				for(ReporterAnnotation reporterAnnotation: reporterAnnotations){
					if(reporterAnnotation != null){
						
						tr = table.addElement("tr").addAttribute("class", "data");
						
						//String reporter = reporterAnnotation.getReporter()!=null ? reporterAnnotation.getReporter().getValue().toString() : "N/A";
						td = tr.addElement("td").addText(reporterAnnotation.getReporterId());
						//html.append("ReporterID :" +reporterResultset.getReporter().getValue().toString() + "<br/>");
						Collection<String> geneSymbols = (Collection<String>)reporterAnnotation.getGeneSymbols();
						String genes = "";
						if(geneSymbols != null){
							genes = StringUtils.join(geneSymbols.toArray(), ",");
						}
						if(!genes.equals("")){
						td = tr.addElement("td").addText(genes).addAttribute("class", "gene").addAttribute("name", "gene").addAttribute("id", genes).addElement("input").addAttribute("type","checkbox").addAttribute("name","checkable").addAttribute("class","saveElement").addAttribute("value",genes);
                        }
                        else{
                        td = tr.addElement("td").addText(genes).addAttribute("class", "gene").addAttribute("name", "gene").addAttribute("id", genes);   
                        }
						Collection<String> genBank_AccIDS = (Collection<String>)reporterAnnotation.getGenbankAccessions();
						String accs = "";
						if(genBank_AccIDS != null){
							accs = StringUtils.join(genBank_AccIDS.toArray(), ",");
						}
						
						td = tr.addElement("td").addText(accs);
						
						Collection<String> locusLinkIDs = (Collection<String>)reporterAnnotation.getLocusLinkIds();
						String ll = "";
						if(locusLinkIDs != null){
							ll = StringUtils.join(locusLinkIDs.toArray(), ",");
						}
						
						td = tr.addElement("td").addText(ll);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//return html;
		return html.append(document.asXML());
	}
}
