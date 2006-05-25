package gov.nih.nci.ispy.web.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.application.cache.*;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.bean.FindingReportBean;

//import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.ispy.web.helper.ReportGeneratorHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;

import uk.ltd.getahead.dwr.ExecutionContext;



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class DynamicReportGenerator {

		
	public DynamicReportGenerator()	{}
	
	public void generateDynamicReport(String key, Map<String, String> params)	{
		String html = new String();

		HttpSession session = ExecutionContext.get().getSession(false);
		PresentationTierCache ptc = CacheFactory.getPresentationTierCache();
		BusinessTierCache btc = CacheFactory.getBusinessTierCache();
		HttpServletRequest request = ExecutionContext.get().getHttpServletRequest();
//		HttpServletResponse response = ExecutionContext.get().getHttpServletResponse();
		
		//lets hold a list of xml generating jobs, so we dont keep kicking off the same job
		ArrayList jobs = session.getAttribute("xmlJobs")!=null ? (ArrayList) session.getAttribute("xmlJobs") : new ArrayList();
		
		//only generate XML if its not already cached...leave off for debug
		//RCL - remove this constraint for now, to avoid caching for tasks with the same key/id
		//if(ptc.getPersistableObjectFromSessionCache(session.getId(), key) == null && !jobs.contains(key))	{
			Object o = btc.getObjectFromSessionCache(session.getId(), key);
			Finding finding = (Finding) o; 
			//generate the XML and cached it
			ReportGeneratorHelper.generateReportXML(finding);
			if(!jobs.contains(key))
				jobs.add(key);
			session.setAttribute("xmlJobs", jobs);
		//}
		Object ob = ptc.getPersistableObjectFromSessionCache(session.getId(), key);
		if(ob != null && ob instanceof FindingReportBean)	{
			try	{
				FindingReportBean frb = (FindingReportBean) ob;
				Document reportXML = (Document) frb.getXmlDoc();
			
				html = ReportGeneratorHelper.renderReport(params, reportXML,"cc_report.xsl");
				
				jobs.remove(key);
				session.setAttribute("xmlJobs", jobs);
			}
			catch(Exception e)	{
				html = "Error Generating the report.";
			}
		}
		else	{
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
	
	
	
	/*
	public Map saveTmpSamples(String elem){
		return saveTmpGeneric("pca_tmpSampleList", elem);
	}
	
	public Map saveTmpSamplesFromClinical(String elem){
		return saveTmpGeneric("clinical_tmpSampleList", elem);
	}
	*/
	
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
	}
	
	public Map removeTmpSampleFromClinical(String elem)	{
		return removeTmpGeneric("clinical_tmpSampleList", elem);
	}
	
	public void clearTmpReporters()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("tmpReporterList"); //put back in session
	}
	*/
	public void clearTmpGenes()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("tmpGeneList"); 
	}
	/*
	public void clearTmpSamples()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("pca_tmpSampleList"); //put back in session
	}
	
	public void clearTmpSamplesFromClinical()	{
		HttpSession session = ExecutionContext.get().getSession(false);
		session.removeAttribute("clinical_tmpSampleList"); //put back in session
	}
	*/
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
	/*
	public String saveSamples(String commaSepList, String name)	{
		String success = "fail";
		HttpSession session = ExecutionContext.get().getSession(false);
		PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
		SessionCriteriaBag sessionCriteriaBag = ptc.getSessionCriteriaBag(session.getId());
		
		//hold the list of DE's
		List<DomainElement> domainElementList = new ArrayList();
		
		try {
			//break the comma list
			StringTokenizer tk = new StringTokenizer(commaSepList, ",");
			while (tk.hasMoreTokens()) {
				String element = tk.nextToken();
				//iterate through and create domain elements
				SampleIDDE sampleDE = new SampleIDDE(element);
				domainElementList.add(sampleDE);
			}

			//check name for collision...or dont, so to enable overwriting
			if(sessionCriteriaBag.getUserList(ListType.SampleIdentifierSet,name) != null)	{
				//make a new name
	            long randomness = System.currentTimeMillis(); //prevent image caching
	            name = name + "_" + randomness;
			}
			sessionCriteriaBag.putUserList(ListType.SampleIdentifierSet,name,domainElementList); 
			ptc.putSessionCriteriaBag(session.getId(),sessionCriteriaBag);
			success = "pass";
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}
	*/

}
