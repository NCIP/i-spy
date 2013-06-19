package gov.nih.nci.ispy.web.ajax;

import gov.nih.nci.caintegrator.application.bean.FindingReportBean;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ajax.CommonListFunctions;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.web.helper.ISPYListValidator;
import gov.nih.nci.ispy.web.helper.ReportGeneratorHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import uk.ltd.getahead.dwr.ExecutionContext;





public class DynamicReportGenerator {

		
	public DynamicReportGenerator()	{}
	
	
    
    public void generateDynamicReport(String key, Map<String, String> params, String stylesheet)   {
        String html = new String();      
        
        HttpSession session = ExecutionContext.get().getSession(false);
        PresentationTierCache ptc = CacheFactory.getPresentationTierCache();
        BusinessTierCache btc = CacheFactory.getBusinessTierCache();
        HttpServletRequest request = ExecutionContext.get().getHttpServletRequest();
//      HttpServletResponse response = ExecutionContext.get().getHttpServletResponse();
        
        //lets hold a list of xml generating jobs, so we dont keep kicking off the same job
        ArrayList jobs = session.getAttribute("xmlJobs")!=null ? (ArrayList) session.getAttribute("xmlJobs") : new ArrayList();
        
        //only generate XML if its not already cached...leave off for debug
        //RCL - remove this constraint for now, to avoid caching for tasks with the same key/id
        //if(ptc.getPersistableObjectFromSessionCache(session.getId(), key) == null && !jobs.contains(key)) {
            Object o = btc.getObjectFromSessionCache(session.getId(), key);
            Finding finding = (Finding) o; 
            //generate the XML and cached it
            ReportGeneratorHelper.generateReportXML(finding);
            if(!jobs.contains(key))
                jobs.add(key);
            session.setAttribute("xmlJobs", jobs);
        //}
        Object ob = ptc.getPersistableObjectFromSessionCache(session.getId(), key);
        if(ob != null && ob instanceof FindingReportBean)   {
            try {
                FindingReportBean frb = (FindingReportBean) ob;
                Document reportXML = (Document) frb.getXmlDoc();
            
                html = ReportGeneratorHelper.renderReport(params, reportXML,stylesheet);
                
                jobs.remove(key);
                session.setAttribute("xmlJobs", jobs);
            }
            catch(Exception e)  {
                html = "Error Generating the report.";
            }
        }
        else    {
            html = "Error generating the report";
        }
        //out the XHTML in the session for reference in presentation...could store in Prescache
        session.setAttribute(key+"_xhtml", html);
        return;
    }
	
	/*
	public Map saveTmpReporter(String rep)	{
		Map results = new HashMap();
		
		HttpSession session = ExecutionContext.get().getSession(false);
		//put the reporter into an arraylist in the session...doenst exist? create it
		ArrayList al = new ArrayList();
		if(session.getAttribute("tmpReporterList") != null)	{
			al = (ArrayList) session.getAttribute("tmpReporterList");
		}
		if(!rep.equals("") && !al.contains(rep)){
			al.add(rep); // add it
			session.setAttribute("tmpReporterList", al); //put back in session
		}
		String tmpReporters = "";
		for(int i = 0; i<al.size(); i++)
			tmpReporters += al.get(i) + "<br/>";
	
		results.put("count", al.size());
		results.put("reporters", tmpReporters);
		
		return results;
	}
	*/
	public Map saveTmpReporter(String elem){
		return saveTmpGeneric("tmpReporterList", elem);
	}
	
	public Map saveTmpGene(String elem){
		return saveTmpGeneric("tmpGeneList", elem);
	}
	
	public Map saveTmpGeneFromArray(String[] elems)	{
		Map results = new HashMap();
		for(String el : elems){
			results = saveTmpGene(el);
		}
		
		return results;
	}
	
	
	public Map saveTmpGeneric(String type, String elem)	{
		Map results = new HashMap();
		
		HttpSession session = ExecutionContext.get().getSession(false);
		//put the element into an arraylist in the session...doesnt exist? create it
		ArrayList al = new ArrayList();
		if(session.getAttribute(type) != null)	{
			al = (ArrayList) session.getAttribute(type);            
		}
        
		if(!elem.equals("") && !al.contains(elem)){
			al.add(elem); // add it
			session.setAttribute(type, al); //put back in session
		}
		String tmpElems = "";
		for(int i = 0; i<al.size(); i++)
			tmpElems += al.get(i) + "<br/>";
	
		results.put("count", al.size());
		results.put("elements", tmpElems);
		
		return results;
	}
	
	
	
    public Map saveTmpSamplesFromArray(String[] elems) {
        Map results = new HashMap();
        for(String el : elems){
            results = saveTmpSamplesFromClinical(el);
        }
        
        return results;
    }
    
	public Map saveTmpSamplesFromClinical(String elem){
		return saveTmpGeneric("clinical_tmpSampleList", elem);
	}
	
	
	/*
	public Map removeTmpReporter(String rep)	{
		HttpSession session = ExecutionContext.get().getSession(false);
		ArrayList al = new ArrayList();
		if(session.getAttribute("tmpReporterList") != null)	{
			al = (ArrayList) session.getAttribute("tmpReporterList");
		}
		al.remove(rep); // nuke it
		session.setAttribute("tmpReporterList", al); //put back in session
		String tmpReporters = "";
		for(int i = 0; i<al.size(); i++)
			tmpReporters += al.get(i) + "<br/>";
		
		Map results = new HashMap();
		results.put("count", al.size());
		results.put("reporters", tmpReporters);
		return results;
	}
	*/
	
	
	public Map removeTmpGeneric(String type, String elem)	{
		HttpSession session = ExecutionContext.get().getSession(false);
		ArrayList al = new ArrayList();
		if(session.getAttribute(type) != null)	{
			al = (ArrayList) session.getAttribute(type);
		}
		al.remove(elem); // nuke it
		session.setAttribute(type, al); //put back in session
		String tmpElems = "";
		for(int i = 0; i<al.size(); i++)
			tmpElems += al.get(i) + "<br/>";
		
		Map results = new HashMap();
		results.put("count", al.size());
		results.put("elements", tmpElems);
		return results;
	}
	
	
	public Map removeTmpGene(String elem)	{
		return removeTmpGeneric("tmpGeneList", elem);
	}
	
	/*
	public Map removeTmpSample(String elem)	{
		return removeTmpGeneric("pca_tmpSampleList", elem);
	}*/
	
	public Map removeTmpSampleFromClinical(String elem)	{
		return removeTmpGeneric("clinical_tmpSampleList", elem);
	}
	
	public void clearTmpReporters()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("tmpReporterList"); //put back in session
	}
	
	public void clearTmpGenes()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("tmpGeneList"); 
	}
	/*
	public void clearTmpSamples()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("pca_tmpSampleList"); //put back in session
	}
	*/
	public void clearTmpSamplesFromClinical()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("clinical_tmpSampleList"); //put back in session
	}
	
	/*
	public String saveGenes(String commaSepList, String name)	{
		return "pass"; //using new stuff instead
	}
	*/
	/*		//already commented out
	public String saveReporters(String commaSepList, String name)	{
		String success = "fail";
		HttpSession session = ExecutionContext.get().getSession(false);
		PresentationTierCache ptc = CacheFactory.getPresentationTierCache();
		//SessionCriteriaBag sessionCriteriaBag = ptc.getSessionCriteriaBag(session.getId());
		
		//hold the list of DE's
		List<DomainElement> domainElementList = new ArrayList();
		
		try {
			//break the comma list
			StringTokenizer tk = new StringTokenizer(commaSepList, ",");
			while (tk.hasMoreTokens()) {
				String element = tk.nextToken();
				//iterate through and create domain elements
				CloneIdentifierDE cloneIdentifierDE = new CloneIdentifierDE.ProbesetID(element);
				domainElementList.add(cloneIdentifierDE);
			}

			//check name for collision...or dont, so to enable overwriting
			sessionCriteriaBag.putUserList(ListType.CloneProbeSetIdentifierSet,name,domainElementList); 
			ptc.putSessionCriteriaBag(session.getId(),sessionCriteriaBag);
			success = "pass";
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}
	*/
	
	public String saveSamples(String commaSepList, String name)	{
        String success = "fail";
        String[] listArr = StringUtils.split(commaSepList, ",");
        List<String> list = Arrays.asList(listArr);
        try {
            ISPYListValidator listValidator = new ISPYListValidator(gov.nih.nci.caintegrator.application.lists.ListType.PatientDID, list);            
            success = CommonListFunctions.createGenericList(gov.nih.nci.caintegrator.application.lists.ListType.PatientDID, list, name, listValidator);
        }
        catch(Exception e) {
            //most likely cant access the session
        }
		
		return success;
	}
	

}
