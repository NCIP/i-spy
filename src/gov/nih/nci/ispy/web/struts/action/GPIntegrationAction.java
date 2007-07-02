package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.genepattern.client.GPServer;
import org.genepattern.webservice.Parameter;

import gov.nih.nci.ispy.dto.query.ISPYGPIntegrationQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.GpIntegrationForm;

///////////////
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.IdGroup;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.security.EncryptionUtil;
import gov.nih.nci.ispy.util.ISPYPublicUserPool;
///////////////
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

public class GPIntegrationAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(GPIntegrationAction.class);
    private Collection<GeneIdentifierDE> geneIdentifierDECollection;

    private String gpPoolString = ":GP30:RBT";
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	GpIntegrationForm gpForm = (GpIntegrationForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(request.getSession());
        gpForm.setExistingGroupsList(clinicalGroupRetriever.getClinicalGroupsCollection());

        UserListBeanHelper listHelper = new UserListBeanHelper(request.getSession());
        //fetch the users gene groups populate the dropdown
        List<String> names = (List<String>) listHelper.getGenericListNames(ListType.Gene);
        List<LabelValueBean> gsNameList = new ArrayList<LabelValueBean>();
            for(String listName : names){
                gsNameList.add(new LabelValueBean(listName,listName));
            }
            gpForm.setGeneSetNameList(gsNameList);
        return mapping.findForward("success");
    }
    /**
     * Method submittal
     * 
     * @param ActionMapping
     *            mapping
     * @param ActionForm
     *            form
     * @param HttpServletRequest
     *            request
     * @param HttpServletResponse
     *            response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
    	GpIntegrationForm gpForm = (GpIntegrationForm) form;
        String sessionId = request.getSession().getId();
        HttpSession session = request.getSession();
        ISPYGPIntegrationQueryDTO ispyGPIntegrationQueryDTO = createGPIntegrationQueryDTO(gpForm, session); 
      
        // to filter id with time points
        ISPYFindingsFactory findingFactory = new ISPYFindingsFactory();
        try{
        	findingFactory.createGPIntegrationSampleIds(ispyGPIntegrationQueryDTO, sessionId);
        }catch (FrameworkException fwe){
			System.out.println(fwe.getMessage());
			fwe.printStackTrace();	
        }
        SampleGroup[] sampleGroups = ispyGPIntegrationQueryDTO.getSampleGroups();
        //Now let's check id overlapping problem.
        if (isIdOverlap(sampleGroups)){
        	ActionErrors errors = new ActionErrors();
        	errors.add("selectedGroups1", new ActionMessage(
                "gov.nih.nci.nautilus.ui.struts.form.groups.intersect.error"));
        	this.saveErrors(request, errors);
        	return setup(mapping, form, request, response);
        }

        List<List<String>> allStringList = new ArrayList<List<String>>();
        List<String> fileNameList = new ArrayList<String>();
        List<String> idStringList = new ArrayList<String>();
        List<String> reportIdStringList = new ArrayList<String>();

		String gpModule =  "ConvertToGctAndClsFile"; //run this one by default "PreprocessDataset";
		String analysisResultName = (gpForm.getAnalysisResultName()!=null && !gpForm.getAnalysisResultName().equals("")) 
			? (String)gpForm.getAnalysisResultName() : "unnamed_task";

		//for sample Id file
		for (SampleGroup sampleGroup : sampleGroups){
			idStringList.add(getIdsAsDelimitedString(sampleGroup, "\t"));
		}
		allStringList.add(idStringList);
		fileNameList.add("labIdsFile");
		
		//for reporter id file
		ReporterGroup reporterGroup = ispyGPIntegrationQueryDTO.getReporterGroup();
		if (reporterGroup != null && reporterGroup.size() > 0){
			reportIdStringList.add(getIdsAsDelimitedString(reporterGroup, "\t"));
			allStringList.add(reportIdStringList);
			fileNameList.add("reproterIdsFile");
		}
		else {
			reportIdStringList.add("reporter=NONE");
			allStringList.add(reportIdStringList);
			fileNameList.add("reproterIdsFile");
		}
		//For class file
		//makeClassFile(classStringList, sampleGroups);
		//allStringList.add(classStringList);
		//fileNameList.add("ispyClassFile");
		
		//Now let's write them to files
		List<String> filePathList = new ArrayList<String>();
		writeGPFile(filePathList, allStringList, fileNameList);
		
		//Now get the R-binary file name:
		String r_fileName = null;
		if (ispyGPIntegrationQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()
				== ArrayPlatformType.AGILENT)
			r_fileName = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
		else
			r_fileName = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
		
		//*** RUN TASK ON THE GP SERVER
		String tid = "209";

		String gpserverURL = System.getProperty("gov.nih.nci.ispyportal.gp.server")!=null ? 
				(String)System.getProperty("gov.nih.nci.ispyportal.gp.server") : "localhost:8080"; //default to localhost
		try {
		//*	
			String ispyUser = (String)session.getAttribute("name");
			String publicUser = System.getProperty("gov.nih.nci.ispyportal.gp.publicuser.name");
			String password = System.getProperty("gov.nih.nci.ispyportal.gp.publicuser.password");
			
			//Check to see the user is already created otherwise create one.
			
			GPServer gpServer = null;
			if (ispyUser.equals(publicUser)){
				String gpUser = (String)session.getAttribute("gpUsername");
				if (gpUser == null){
					ISPYPublicUserPool pool = ISPYPublicUserPool.getInstance();
					gpUser = pool.borrowPublicUser();
					session.setAttribute("gpUsername", gpUser);
				}
				ispyUser = gpUser;
			}
			String encryptKey = System.getProperty("gov.nih.nci.ispyportal.gp.desencrypter.key");
			String urlString = EncryptionUtil.encrypt(ispyUser+ gpPoolString, encryptKey);
			urlString = URLEncoder.encode(urlString, "UTF-8");
			String ticketString = gpserverURL+"gp?ticket="+ urlString;
			
			System.out.println(ticketString);
			URL url;
            try {
            	url = new java.net.URL(ticketString);
            	URLConnection conn = url.openConnection();
            	final int size = conn.getContentLength();
            	logger.debug(Integer.toString(size));
            	//byte[] b = new byte[size]; 
            	//conn.getInputStream().read(b);
            	//String s = new String(b);
            	//System.out.println(s); //for testing
            } catch (Exception e) {
            	// TODO Auto-generated catch block
            	logger.error(e.getMessage());
            	//e.printStackTrace();
            }
			gpServer = new GPServer(gpserverURL, ispyUser, password);
			//*/
			//GPServer gpServer = new GPServer(gpserverURL, gpuname, password);
			Parameter[] par = new Parameter[filePathList.size() + 3 + 2];
			int currpos= 1;
			for (int i = 0; i < filePathList.size(); i++){
				if (i != filePathList.size() - 1)
					par[i] = new Parameter("input.filename"+currpos, filePathList.get(i));
				else
					par[i] = new Parameter("input.filename" + currpos, filePathList.get(i));
				currpos = currpos+1;
			}
			currpos = currpos - 1;
			par[currpos] = new Parameter("project.name", "ispy");
			
			currpos = currpos+1;
			//r_fileName = "'/usr/local/genepattern/resources/DataMatrix_ISPY_306cDNA_17May07.Rda'";
			par[currpos] = new Parameter("array.filename", r_fileName);
			currpos = currpos+1;
			par[currpos] = new Parameter("analysis.name", analysisResultName);

			//always just 2
			par[++currpos] = new Parameter("output.cls.file",analysisResultName+".cls");
			par[++currpos] = new Parameter("output.gct.file",analysisResultName+".gct");
			
			//JobResult preprocess = gpServer.runAnalysis(gpModule, par);
			int nowait = gpServer.runAnalysisNoWait(gpModule, par);

			//tid = String.valueOf(preprocess.getJobNumber());
			tid = String.valueOf(nowait);
			//LSID = urn:lsid:8080.root.localhost:genepatternmodules:20:2.1.7
			request.setAttribute("jobId", tid);
			request.setAttribute("gpStatus", "running");
			//ISPYPublicUserPool pool = ISPYPublicUserPool.getInstance();
			//String ticketString = gpserverURL+"gp?ticket="+ EncryptionUtil.encrypt(gpuname+ gpPoolString);
			session.setAttribute("genePatternServer", gpServer);
			request.setAttribute("genePatternURL", ticketString);
			request.getSession().setAttribute("gptid", tid);
			//session.setAttribute("genePatternPreprocess", preprocess);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println(gpModule + " failed.");
			//System.out.println(e.getMessage());
			//e.printStackTrace();
			logger.error(gpModule + " failed...." + e.getMessage());
			throw new Exception(e.getMessage());
		}
        //return setup(mapping, form, request, response);
		return mapping.findForward("viewJob");
    }

    private ISPYGPIntegrationQueryDTO createGPIntegrationQueryDTO(GpIntegrationForm gpQueryForm, HttpSession session){
 
        ISPYGPIntegrationQueryDTO gpQueryDTO = (ISPYGPIntegrationQueryDTO)ApplicationFactory.newQueryDTO(QueryType.PROBESET_CLONEID_QUERY_FOR_HOA);
        gpQueryDTO.setQueryName(gpQueryForm.getAnalysisResultName());        

        HashMap<String, UserList> patientLists = new HashMap<String, UserList>();
        HashMap<String, Set<TimepointType>> timepointLists = new HashMap<String, Set<TimepointType>>();
        UserListBeanHelper listHelper = new UserListBeanHelper(session);
        String groupTimepoint= null;
        String groupName = null;
        String timePointName = null;
        if (gpQueryForm.getSelectedGroups() != null && gpQueryForm.getSelectedGroups().length > 0){
        	for(int i=0; i<gpQueryForm.getSelectedGroups().length;i++){    
        		groupTimepoint = gpQueryForm.getSelectedGroups()[i];
        		int index = groupTimepoint.indexOf("---");
        		groupName = groupTimepoint.substring(0, index);
        		timePointName = groupTimepoint.substring(index + 3);
        		UserList userList = listHelper.getUserList(groupName);
        		patientLists.put(groupTimepoint, userList); 
        		String[] times = timePointName.split("-");
        		String fixedTimepointString = null;
        		Set<TimepointType> set = new HashSet<TimepointType>();
        		for (String time : times){
        			TimepointType fixedTimepointBase;//timepoint for both queryDTOs        
        			fixedTimepointString = EnumHelper.getEnumTypeName(time,TimepointType.values());
        			if(fixedTimepointString!=null){
        				fixedTimepointBase = TimepointType.valueOf(fixedTimepointString);
        				set.add(fixedTimepointBase);
        			}
        		}
        		timepointLists.put(groupTimepoint, set);
        	}
        }
        gpQueryDTO.setPatientLists(patientLists);
        gpQueryDTO.setTimepointLists(timepointLists);
 
        //UserListBeanHelper listHelper = new UserListBeanHelper(session);
        if(gpQueryForm.getGeneSetName()!=null && (!gpQueryForm.getGeneSetName().equals("") && !gpQueryForm.getGeneSetName().equals("none"))){
            geneIdentifierDECollection = listHelper.getGeneDEforList(gpQueryForm.getGeneSetName());
            if (geneIdentifierDECollection!=null && !geneIdentifierDECollection.isEmpty()){
                logger.debug("geneIdentifierDECollection was found");
                gpQueryDTO.setGeneIdentifierDEs(geneIdentifierDECollection);
                gpQueryDTO.setReportersName(gpQueryForm.getGeneSetName());
            }
            else{
                logger.debug("geneIdentifierDECollection could not be found");
            }
        }
        //Create arrayPlatfrom DEs
        if(gpQueryForm.getArrayPlatform() != "" || gpQueryForm.getArrayPlatform().length() != 0){       
        	ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(gpQueryForm.getArrayPlatform());
        	gpQueryDTO.setArrayPlatformDE(arrayPlatformDE);
        }

        return gpQueryDTO;
    }    
    private String getIdsAsDelimitedString(IdGroup idGroup, String token){
		StringBuffer sb = new StringBuffer(replaceSpace(idGroup.getGroupName()) + "=");
		for (Iterator i = idGroup.iterator(); i.hasNext(); ) {
		  //sb.append(DOUBLE_QUOTE+(String)i.next()+DOUBLE_QUOTE);
		  sb.append((String)i.next());
		  if (i.hasNext()) {
		    sb.append(token);
		  }
		}
		if (sb.length() == 0)
			return "";
		return sb.toString();
    }
    private void writeGPFile(List<String> filePathList, 
    		List<List<String>> allIdStringList,
    		List<String> fileNameList)throws IOException{
    	int count = 0; 
    	String fileName = null;
    	String fileExtension = ".txt";
		for (List<String> list : allIdStringList){
			if (!list.isEmpty()){
				fileName = fileNameList.get(count);
				if (fileName.equals("ispyClassFile"))
					fileExtension = ".cls";
				else
					fileExtension = ".txt";
				//File idFile =File.createTempFile(fileName, fileExtension, new File("C:\\temp\\ispy"));
				File idFile =File.createTempFile(fileName, fileExtension);
				FileWriter idFw = new FileWriter(idFile);
				for (String ids : list){
					idFw.write(ids);
					idFw.write("\n");
				}
				idFw.close();
				filePathList.add(idFile.getAbsolutePath());
			}
			else
				filePathList.add("");
			count++;
		}
    }
    private boolean isIdOverlap(SampleGroup[] sampleGroups){
    	SampleGroup samples = new SampleGroup();
    	int total = 0;
    	for (SampleGroup sampleGroup : sampleGroups){
    		total = total + sampleGroup.size();
    		samples.addAll(sampleGroup);
    	}
    	if (total > samples.size())
    		return true;
    	return false;
    }
    
    private void makeClassFile(List<String> classStringList, SampleGroup[] sampleGroups){
    	int totalSample = 0;
    	int totalGroup = 0;
    	int always = 1;
    	int base = 0;
    	String line1 = "";
    	StringBuffer line2 = new StringBuffer("#");
    	StringBuffer line3 = new StringBuffer();
    	for (SampleGroup sampleGroup :sampleGroups){
    		totalSample = totalSample + sampleGroup.size();
    		totalGroup++;
    		line2.append("\t");
    		line2.append(sampleGroup.getGroupName());

    		makeLine3(line3, sampleGroup, base);
    		base++;
    	}
    	line1 = Integer.toString(totalSample) + "\t" + 
    		Integer.toString(totalGroup) + "\t" + Integer.toString(always);
    	classStringList.add(line1);
    	classStringList.add(line2.toString());
    	classStringList.add(line3.toString());
    }
    private void makeLine3(StringBuffer sb, SampleGroup sampleGroup, int base){
    	int total = 0;
		for (Iterator i = sampleGroup.iterator(); i.hasNext(); ) {
			String id = (String)i.next();
			  //sb.append(DOUBLE_QUOTE+(String)i.next()+DOUBLE_QUOTE);
			if (base == 0 && total == 0){
				sb.append(base);
			}
			else {
			  sb.append("\t");
			  sb.append(base);
			}
			total++;
		}
    }
    private String replaceSpace(String text){
    	return text.replaceAll(" ", "_");
    }
}

