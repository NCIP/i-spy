package gov.nih.nci.ispy.web.taglib;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.ispy.web.reports.quick.QuickClinicalReport;
import gov.nih.nci.ispy.web.reports.quick.QuickReporterReport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;





public class HCPlotReport extends TagSupport {
	
	private String taskId = "";
	//private Logger logger = Logger.getLogger(HCPlotReport.class);
	private static Logger logger = Logger.getLogger(HCPlotReport.class);	
	
	private PresentationTierCache presentationTierCache = CacheFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = CacheFactory.getBusinessTierCache();
	
	private String dv = "--";
	
	public enum ClusterBy { Genes, Reporters }
	
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			HttpSession session = pageContext.getSession();
			StringBuffer xhtml = new StringBuffer();
			if(taskId != null)	{
				
				HCAFinding hcaFinding = (HCAFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
	            List<String> clusterByIds = new ArrayList();
	            //ok, what did we cluster by?...can only be 1
	            if(hcaFinding.getClusteredReporterIDs()!=null && hcaFinding.getClusteredReporterIDs().size() > 0)	{
	            	clusterByIds = (List) hcaFinding.getClusteredReporterIDs();
	            	xhtml.append(quickReporterReport(clusterByIds,session.getId(),hcaFinding.getTaskId()));
	            }
	            else if(hcaFinding.getClusteredSampleIDs()!=null && hcaFinding.getClusteredSampleIDs().size() > 0)	{
	            	clusterByIds = (List) hcaFinding.getClusteredSampleIDs();
	            	xhtml.append(quickSampleReport(clusterByIds,session.getId(),hcaFinding.getTaskId()));
	            }
	            
				out.println(xhtml.toString());
			}
			else	{
				out.println("No Report Available.");
			}
		} 
		catch (Exception ex) {
			throw new Error("All is not well in the world.");
		}
		// Must return SKIP_BODY because we are not supporting a body for this tag.
		return SKIP_BODY;
	}
	public int doEndTag(){
		try {
			//JspWriter out = pageContext.getOut();
			//out.println("</table>");
		} 
		catch (Exception ex){
			//throw new Error("All is not well in the world.");
		}
		return EVAL_PAGE;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public StringBuffer quickReporterReport(List<String> reporters,String sessionId, String taskId){
		
		return QuickReporterReport.quickReporterReport(reporters,sessionId,taskId);
		
		/*
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
				List<ReporterAnnotation> reporterAnnotations = GeneExprFileBasedAnnotationService.getInstance().getAnnotationsListForReporters(reporters);
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
						
						td = tr.addElement("td").addText(genes);
						
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
		*/	
						/*
						Collection<String> goIds = (Collection<String>)reporterResultset.getAssociatedGOIds();
						if(goIds != null){
							for(String goId: goIds){
								html.append("Associtaed GO Id :" +goId + "<br/>");
							}
						}
						Collection<String> pathways = (Collection<String>)reporterResultset.getAssociatedPathways();
						if(pathways != null){
							for(String pathway: pathways){
								html.append("Associated Pathway :" +pathway + "<br/>");
							}
						}
						*/
		/*
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//return html;
		return html.append(document.asXML());
		
		*/
	}
	public StringBuffer quickSampleReport(List<String> sampleIds,String sessionId, String taskId){
		return QuickClinicalReport.quickSampleReport(sampleIds,sessionId,taskId);
	}
	
}
