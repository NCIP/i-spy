/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import java.util.HashSet;
import java.util.Set;

import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.studyQueryService.dto.ihc.LevelOfExpressionIHCFindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.p53.P53FindingCriteria;
import gov.nih.nci.caintegrator.studyQueryService.dto.protein.ProteinBiomarkerCriteia;
import gov.nih.nci.caintegrator.studyQueryService.dto.study.SpecimenCriteria;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.helper.IHCRetriever;
import gov.nih.nci.ispy.web.helper.P53Retriever;
import gov.nih.nci.ispy.web.struts.form.IHCLevelOfExpressionQueryForm;
import gov.nih.nci.ispy.web.struts.form.P53QueryForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class P53QueryAction extends DispatchAction {
	
	
	
	 public ActionForward submit(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
		  
		    P53QueryForm p53QueryForm = (P53QueryForm) form;  	    
	        String sessionId = request.getSession().getId();
	        HttpSession session = request.getSession();
	        
	        P53FindingCriteria  p53FindingCriteria = createP53FindingCriteria(p53QueryForm,session);
	        
	        // in ISPYFindingsFactory objects, all the findings are created, such as clinical, ihc, class comparison etc        
	        ISPYFindingsFactory factory = new ISPYFindingsFactory();
	        factory.createP53Finding(p53FindingCriteria, sessionId, p53FindingCriteria.getQueryName());
	        return mapping.findForward("viewResults");  	     
	    }
	    
	 
	 private P53FindingCriteria createP53FindingCriteria(P53QueryForm p53QueryForm, HttpSession session) {
    	
         SpecimenCriteria theSPCriteria = new SpecimenCriteria();    	 
    	 P53FindingCriteria dto = new P53FindingCriteria();    	 
    	 UserListBeanHelper helper = new UserListBeanHelper(session);
    	 UserList myCurrentList;
    	 
    	 
    	 dto.setQueryName(p53QueryForm.getAnalysisResultName());
    	 
    	 //set custom patient group    	 
    	 if(p53QueryForm.getPatientGroup()!=null && !p53QueryForm.getPatientGroup().equals("none")){    		
             Set<String> tempRestrainingPatientDIDs = new HashSet<String>();
             myCurrentList = helper.getUserList(p53QueryForm.getPatientGroup());
             if(myCurrentList!=null && !myCurrentList.getList().isEmpty()){
                 tempRestrainingPatientDIDs.addAll(myCurrentList.getList());
             }
             theSPCriteria.setPatientIdentifierCollection(tempRestrainingPatientDIDs);
         }
    	 
    	 //set time point          
         if(p53QueryForm.getTimepoints()!=null && p53QueryForm.getTimepoints().length>0){        	 
             Set<String> tpSet = new HashSet<String>();
             String[] tps = p53QueryForm.getTimepoints();
             for(int i=0; i<tps.length;i++){
            	 Enum myType = EnumHelper.createType(TimepointType.class.getName(),tps[i]);
                 myType = (TimepointType) myType;
            	 tpSet.add(myType.name());
                 myCurrentList = helper.getUserList(tps[i]);                
             } 
          theSPCriteria.setTimeCourseCollection(tpSet);          
         }         
         
    	 
         // set mutationStatus          
         if(p53QueryForm.getMutationStatus()!=null && p53QueryForm.getMutationStatus().length>0){
             String[] mutationStatuses = p53QueryForm.getMutationStatus();
             Set<String> mutationStatusSet = new HashSet<String>();          
             for(int i=0; i<mutationStatuses.length;i++){
            	 String mutattionStatus= (String)mutationStatuses[i];
            	 mutationStatusSet.add(mutattionStatus);            	 
             }        
          dto.setMutationStatusCollection(mutationStatusSet);    
         }    
         
         // set mutationType       
         if(p53QueryForm.getMutationType()!=null && p53QueryForm.getMutationType().length>0){
             Set<String> mutationTypeSet = new HashSet<String>();
             String[] mutationTypes = p53QueryForm.getMutationType();
             for(int i=0; i<mutationTypes.length;i++){                 
            	 mutationTypeSet.add(mutationTypes[i]);                              
             }
             dto.setMutationTypeCollection(mutationTypeSet);
         }
                     
         dto.setSpecimenCriteria(theSPCriteria);         
    	 return  dto;    	 
    }
  
	
	 public ActionForward setup(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	    	P53QueryForm p53QueryForm = (P53QueryForm) form;        
	        HttpSession session = request.getSession();
	        ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(session);        
	        p53QueryForm.setPatientGroupCollection(clinicalGroupRetriever.getClinicalGroupsCollection());
	        P53Retriever p53Retriever = new P53Retriever(session);
	        p53QueryForm.setMutationStatusCollection(p53Retriever.getMutationStatuses()); 
	        p53QueryForm.setMutationTypeCollection(p53Retriever.getMutationTypes());
	        
	        return mapping.findForward("backToP53Query");
	    }

}
