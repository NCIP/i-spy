package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYPrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.PrincipalComponentForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;





public class PrincipalComponentAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(ClassComparisonAction.class);
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private Collection<GeneIdentifierDE> geneIdentifierDECollection;
    private Collection<CloneIdentifierDE> cloneIdentifierDECollection;
    private UserCredentials credentials;
    
    
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
        
        PrincipalComponentForm principalComponentForm = (PrincipalComponentForm) form;
        HttpSession session = request.getSession();
        PrincipalComponentAnalysisQueryDTO principalComponentAnalysisQueryDTO = createPrincipalComponentAnalysisQueryDTO(principalComponentForm,session);
        
        ISPYFindingsFactory factory = new ISPYFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createPCAFinding(principalComponentAnalysisQueryDTO,session.getId(),principalComponentAnalysisQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
                
        return mapping.findForward("viewResults");
    }
    
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        PrincipalComponentForm principalComponentForm = (PrincipalComponentForm) form;
        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
        HttpSession session = request.getSession();
        String sessionId = request.getSession().getId(); 
        UserListBeanHelper listHelper = new UserListBeanHelper(session);
        //fetch the users gene groups populate the dropdown
        List<String> names = (List<String>) listHelper.getGenericListNames(ListType.Gene);
        List<LabelValueBean> gsNameList = new ArrayList<LabelValueBean>();
            for(String listName : names){
                gsNameList.add(new LabelValueBean(listName,listName));
            }
        principalComponentForm.setGeneSetNameList(gsNameList);
        
        //principalComponentForm.setGeneSetName(userPreferencesHelper.getGeneSetName());
        //principalComponentForm.setReporterSetName(userPreferencesHelper.getReporterSetName());
         
        return mapping.findForward("backToPrincipalComponent");
    }
    
    private PrincipalComponentAnalysisQueryDTO createPrincipalComponentAnalysisQueryDTO(PrincipalComponentForm principalComponentForm, HttpSession session) {
        String sessionId = session.getId();
        UserListBeanHelper listHelper = new UserListBeanHelper(session);
        ISPYPrincipalComponentAnalysisQueryDTO principalComponentAnalysisQueryDTO = (ISPYPrincipalComponentAnalysisQueryDTO)ApplicationFactory.newQueryDTO(QueryType.PCA_QUERY);
        principalComponentAnalysisQueryDTO.setQueryName(principalComponentForm.getAnalysisResultName());
       
        //create timepointList
        /*
        * This code is here to deal with an observed problem with the changing 
       * of case in request parameters.  See the class EnumChecker for 
        * enlightenment.
       */
       List<TimepointType> timepointTypes = new ArrayList();
       String[] timepointTypeNames = EnumHelper.getEnumTypeNames(principalComponentForm.getTimepoints(),TimepointType.values());
           if(timepointTypeNames!=null) {
              for(int i =0;i<timepointTypeNames.length;i++){
                  timepointTypes.add(TimepointType.valueOf(timepointTypeNames[i]));
              }
           }else {
              logger.error("Invalid TimepointType value given in request");    
           }
       principalComponentAnalysisQueryDTO.setTimepoints(timepointTypes);   
        
        //create GeneVectorPercentileDE
        
            principalComponentAnalysisQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(principalComponentForm.getVariancePercentile()),Operator.GE));
        
        
        /*create GeneIdentifierDEs by looking in the cache for 
        the specified GeneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.*/
        
        if(principalComponentForm.getGeneSetName()!= null && (!principalComponentForm.getGeneSetName().equals("") && !principalComponentForm.getGeneSetName().equals("none"))){
            geneIdentifierDECollection = listHelper.getGeneDEforList(principalComponentForm.getGeneSetName());
            if (geneIdentifierDECollection!=null && !geneIdentifierDECollection.isEmpty()){
                logger.debug("geneIdentifierDECollection was found");
                principalComponentAnalysisQueryDTO.setGeneIdentifierDEs(geneIdentifierDECollection);
            }
            else{
                logger.debug("geneIdentifierDECollection could not be found");
            }
        }
        
        /*create CloneIdentifierDEs by looking in the cache for 
        the specified CloneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.
        The CloneIdentifierDEs will be set as "reporterDECollection" 
        if(principalComponentForm.getReporterSetName()!= null && (!principalComponentForm.getReporterSetName().equals("") || principalComponentForm.getReporterSetName().length()!=0)){
            cloneIdentifierDECollection = sessionCriteriaBag.getUserList(ListType.CloneProbeSetIdentifierSet,principalComponentForm.getReporterSetName());
            if (cloneIdentifierDECollection!=null){
                logger.debug("cloneIdentifierDECollection was found in the cache");
                principalComponentAnalysisQueryDTO.setReporterIdentifierDEs(cloneIdentifierDECollection);
            }
            else{
                logger.debug("cloneIdentifierDECollection could not be found in the cache");
            }
        }*/
        
         
        //create ArrayPlatformDE
        if(principalComponentForm.getArrayPlatform()!=null || principalComponentForm.getArrayPlatform().length()!=0){
            principalComponentAnalysisQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(principalComponentForm.getArrayPlatform()));
        }
        
        return principalComponentAnalysisQueryDTO;
        
    }

        
    
    
    
}
