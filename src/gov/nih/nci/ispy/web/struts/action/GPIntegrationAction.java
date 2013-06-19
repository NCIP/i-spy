/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.analysis.messaging.IdGroup;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterGroup;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.application.analysis.gp.GenePatternPublicUserPool;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.EncryptionUtil;
import gov.nih.nci.caintegrator.security.PublicUserPool;
import gov.nih.nci.caintegrator.service.task.GPTask;
import gov.nih.nci.ispy.dto.query.ISPYGPIntegrationQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.GpIntegrationForm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
///////////////


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
			logger.error(fwe.getMessage());
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

		String gpModule =  System.getProperty("gov.nih.nci.caintegrator.gp.modulename");
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
		String a_fileName = null;
		if (ispyGPIntegrationQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()
				== ArrayPlatformType.AGILENT){
			r_fileName = System.getProperty("gov.nih.nci.ispyportal.agilent_data_matrix");
			a_fileName = System.getProperty("gov.nih.nci.ispyportal.agilent_data_annotation");
		}
		else {
			r_fileName = System.getProperty("gov.nih.nci.ispyportal.cdna_data_matrix");
			a_fileName = System.getProperty("gov.nih.nci.ispyportal.cdna_data_annotation");
		}
		
		//*** RUN TASK ON THE GP SERVER
		String tid = "209";

		String gpserverURL = System.getProperty("gov.nih.nci.caintegrator.gp.server")!=null ? 
				(String)System.getProperty("gov.nih.nci.caintegrator.gp.server") : "localhost:8080"; //default to localhost
		try {
			String ispyUser = (String)session.getAttribute("name");
			String publicUser = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.name");
			String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
			
			//Check to see the user is already created otherwise create one.
			
			GPServer gpServer = null;
			if (ispyUser.equals(publicUser)){
				String gpUser = (String)session.getAttribute(GenePatternPublicUserPool.PUBLIC_USER_NAME);
				if (gpUser == null){
					PublicUserPool pool = GenePatternPublicUserPool.getInstance();
					gpUser = pool.borrowPublicUser();
					session.setAttribute(GenePatternPublicUserPool.PUBLIC_USER_NAME, gpUser);
					session.setAttribute(GenePatternPublicUserPool.PUBLIC_USER_POOL, pool);
				}
				ispyUser = gpUser;
			}
			String encryptKey = System.getProperty("gov.nih.nci.caintegrator.gp.desencrypter.key");
			String urlString = EncryptionUtil.encrypt(ispyUser+ gpPoolString, encryptKey);
			urlString = URLEncoder.encode(urlString, "UTF-8");
			String ticketString = gpserverURL+"gp?ticket="+ urlString;
			
			
			
			logger.debug(ticketString);
			URL url;
            try {
            	url = new java.net.URL(ticketString);
            	URLConnection conn = url.openConnection();
            	final int size = conn.getContentLength();
            	logger.debug(Integer.toString(size));

            } catch (Exception e) {
            	logger.error(e.getMessage());
            }
			gpServer = new GPServer(gpserverURL, ispyUser, password);

			//GPServer gpServer = new GPServer(gpserverURL, gpuname, password);
			Parameter[] par = new Parameter[filePathList.size() + 3 + 3];
			int currpos= 1;
			for (int i = 0; i < filePathList.size(); i++){
				par[i] = new Parameter("input.filename" + currpos++, filePathList.get(i));
			}
			par[--currpos] = new Parameter("project.name", "ispy");

			//r_fileName = "'/usr/local/genepattern/resources/DataMatrix_ISPY_306cDNA_17May07.Rda'";
			par[++currpos] = new Parameter("array.filename", r_fileName);
			par[++currpos] = new Parameter("annotation.filename", a_fileName);
			
			par[++currpos] = new Parameter("analysis.name", analysisResultName);

			//always just 2
			par[++currpos] = new Parameter("output.cls.file",analysisResultName+".cls");
			par[++currpos] = new Parameter("output.gct.file",analysisResultName+".gct");
			
			//JobResult preprocess = gpServer.runAnalysis(gpModule, par);
			int nowait = gpServer.runAnalysisNoWait(gpModule, par);

			tid = String.valueOf(nowait);			
			request.setAttribute("jobId", tid);
			request.setAttribute("gpStatus", "running");
			session.setAttribute("genePatternServer", gpServer);
			request.setAttribute("genePatternURL", ticketString);
			request.getSession().setAttribute("gptid", tid);
			request.getSession().setAttribute("gpUserId", ispyUser);
			request.getSession().setAttribute("ticketString", ticketString);
			GPTask gpTask = new GPTask(tid, analysisResultName, FindingStatus.Running);
			PresentationTierCache _cacheManager = ApplicationFactory.getPresentationTierCache();
			_cacheManager.addNonPersistableToSessionCache(request.getSession().getId(), "latestGpTask",(Serializable) gpTask); 
		
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
			logger.error(gpModule + " failed...." + e.getMessage());
			throw new Exception(e.getMessage());
		}
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
        		int index = groupTimepoint.lastIndexOf("---");
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

