<%@ page language="java" %><%@ page buffer="none" %><%@ page import="gov.nih.nci.ispy.web.helper.ReportGeneratorHelper,gov.nih.nci.ispy.util.ispyConstants, org.dom4j.Document,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat,gov.nih.nci.caintegrator.service.findings.*,gov.nih.nci.caintegrator.application.bean.*, gov.nih.nci.ispy.web.factory.*,org.dom4j.Document, gov.nih.nci.ispy.util.*,gov.nih.nci.ispy.web.factory.ApplicationFactory,gov.nih.nci.caintegrator.application.cache.*,gov.nih.nci.ispy.web.xml.ClassComparisonReport,java.util.HashMap" %>
<%

String key = request.getParameter("key")!=null ? (String) request.getParameter("key") : null;
	if(key != null)	{
	
		String csv = "";
		long randomness = System.currentTimeMillis();
		PresentationTierCache ptc = CacheFactory.getPresentationTierCache();
		FindingReportBean frb = (FindingReportBean) ptc.getPersistableObjectFromSessionCache(session.getId(), key);
		if(frb!=null && frb.getFinding()!=null)	{
			Document xmlDocument = null;
			xmlDocument= frb.getXmlDoc();
			/*
			if(frb.getXmlDocCSV()!=null)
				xmlDocument = (Document)frb.getXmlDocCSV();
			else	{
				//generate the XML for CSV (w/annotations) and cache
				xmlDocument = ClassComparisonReport.getReportXML(frb.getFinding(), new HashMap(), true);
				//put frb back in cache
				frb.setXmlDocCSV(xmlDocument);
				ptc.addPersistableToSessionCache(frb.getFinding().getSessionId(),frb.getFinding().getTaskId(), frb);
			}
			*/
			
			if(xmlDocument!=null)	{
				
			
				//generate the CSV
				response.setContentType("application/csv");
				response.setHeader("Content-Disposition", "attachment; filename=report_"+randomness+".csv");
				try{	
					//ReportGeneratorHelper.renderReport(request, xmlDocument,"csv.xsl",out);
					out.println(ReportGeneratorHelper.renderReport(new HashMap(), xmlDocument,"csv.xsl"));
				}
				catch(Exception e)	{
					out.println("Error Generating the report");
				}
			}
			else	{
				out.println("No Records Available for this query__");
			}
		}
		else	{
			out.println("No Records Available for this query");
		}
	}
	else	{
		out.println("Error Generating the CSV.");
	}
%>