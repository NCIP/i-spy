package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ClusterTypeDE;
import gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.de.LinkageMethodTypeDE;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.enumeration.ClusterByType;
import gov.nih.nci.caintegrator.enumeration.DistanceMatrixType;
import gov.nih.nci.caintegrator.enumeration.LinkageMethodType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYHierarchicalClusteringQueryDTO;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.findings.ISPYFindingsFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.EnumHelper;
import gov.nih.nci.ispy.web.struts.form.HierarchicalClusteringForm;

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





public class HierarchicalClusteringAction extends DispatchAction {
    private static Logger logger = Logger.getLogger(HierarchicalClusteringAction.class);
    private UserCredentials credentials;
    private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
    private Collection<GeneIdentifierDE> geneIdentifierDECollection;
    private Collection<CloneIdentifierDE> cloneIdentifierDECollection;
    //private SessionCriteriaBag sessionCriteriaBag;
    /***
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action.  These default values should
     * be verified as useful in all cases.
     */
    private DistanceMatrixType ERROR_DISTANCE_MATRIX_TYPE = DistanceMatrixType.Correlation;
    private LinkageMethodType ERROR_LINKAGE_METHOD_TYPE = LinkageMethodType.Average;
    private ClusterByType ERROR_CLUSTER_BY_TYPE = ClusterByType.Samples;
   
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
        HierarchicalClusteringForm hierarchicalClusteringForm = (HierarchicalClusteringForm) form;
        logger.debug("Selected Distance Matrix in HttpServletRequest: " + request.getParameter("distanceMatrix"));
        String sessionId = request.getSession().getId();
        HttpSession session = request.getSession();
       HierarchicalClusteringQueryDTO hierarchicalClusteringQueryDTO = createHierarchicalClusteringQueryDTO(hierarchicalClusteringForm,session); 
        
       
       ISPYFindingsFactory factory = new ISPYFindingsFactory();
       Finding finding = null;
       try {
           finding = factory.createHCAFinding(hierarchicalClusteringQueryDTO,sessionId,hierarchicalClusteringQueryDTO.getQueryName());
       } catch (FrameworkException e) {
           e.printStackTrace();
       }
        
        return mapping.findForward("viewResults");
    }
  
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        HierarchicalClusteringForm hierarchicalClusteringForm = (HierarchicalClusteringForm) form;
        String sessionId = request.getSession().getId(); 
        HttpSession session = request.getSession();
        UserListBeanHelper listHelper = new UserListBeanHelper(session);
        //fetch the users gene groups populate the dropdown
        List<String> names = (List<String>) listHelper.getGenericListNames(ListType.Gene);
        List<LabelValueBean> gsNameList = new ArrayList<LabelValueBean>();
            for(String listName : names){
                gsNameList.add(new LabelValueBean(listName,listName));
            }
            hierarchicalClusteringForm.setGeneSetNameList(gsNameList);
        
        return mapping.findForward("backToHierarchicalClustering");
    }
        
    private HierarchicalClusteringQueryDTO createHierarchicalClusteringQueryDTO(HierarchicalClusteringForm hierarchicalClusteringForm, HttpSession session) {
          String sessionId = session.getId();
          UserListBeanHelper listHelper = new UserListBeanHelper(session);
          ISPYHierarchicalClusteringQueryDTO hierarchicalClusteringQueryDTO = (ISPYHierarchicalClusteringQueryDTO)ApplicationFactory.newQueryDTO(QueryType.HC_QUERY);
          hierarchicalClusteringQueryDTO.setQueryName(hierarchicalClusteringForm.getAnalysisResultName());
          
        //create timepointList
          /*
          * This code is here to deal with an observed problem with the changing 
         * of case in request parameters.  See the class EnumChecker for 
          * enlightenment.
         */
         List<TimepointType> timepointTypes = new ArrayList();
         String[] timepointTypeNames = EnumHelper.getEnumTypeNames(hierarchicalClusteringForm.getTimepoints(),TimepointType.values());
         if(timepointTypeNames!=null) {
            for(int i =0;i<timepointTypeNames.length;i++){
                timepointTypes.add(TimepointType.valueOf(timepointTypeNames[i]));
            }
         }else {
            logger.error("Invalid TimepointType value given in request");    
         }
         hierarchicalClusteringQueryDTO.setTimepoints(timepointTypes);
        
          
         // create GeneVectorPercentileDE
            hierarchicalClusteringQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(hierarchicalClusteringForm.getVariancePercentile()),Operator.GE));
        
       
        /*create GeneIdentifierDEs by looking in the cache for 
        the specified GeneIdentifierDECollection. The key is 
        the geneSet name that was uploaded by the user into the cache.*/
        
        if(hierarchicalClusteringForm.getGeneSetName()!=null && (!hierarchicalClusteringForm.getGeneSetName().equals("") && !hierarchicalClusteringForm.getGeneSetName().equals("none"))){
            geneIdentifierDECollection = listHelper.getGeneDEforList(hierarchicalClusteringForm.getGeneSetName());
            if (geneIdentifierDECollection!=null && !geneIdentifierDECollection.isEmpty()){
                logger.debug("geneIdentifierDECollection was found");
                hierarchicalClusteringQueryDTO.setGeneIdentifierDEs(geneIdentifierDECollection);
            }
            else{
                logger.debug("geneIdentifierDECollection could not be found");
            }
        }
//        
//        /*create CloneIdentifierDEs by looking in the cache for 
//        the specified CloneIdentifierDECollection. The key is 
//        the geneSet name that was uploaded by the user into the cache.
//        The CloneIdentifierDEs will be set as "reporterDECollection" */
//        if(hierarchicalClusteringForm.getReporterSetName()!=null || hierarchicalClusteringForm.getReporterSetName().length()!=0){
//            cloneIdentifierDECollection = sessionCriteriaBag.getUserList(ListType.CloneProbeSetIdentifierSet,hierarchicalClusteringForm.getReporterSetName());
//            if (cloneIdentifierDECollection!=null){
//                logger.debug("cloneIdentifierDECollection was found in the cache");
//               //hierarchicalClusteringQueryDTO.setReporterIdentifierDEs(cloneIdentifierDECollection);
//            }
//            else{
//                logger.debug("cloneIdentifierDECollection could not be found in the cache");
//            }
//        }
//        
          // create distance matrix DEs
         logger.debug("Selected DistanceMatrixType in ActionForm:"+hierarchicalClusteringForm.getDistanceMatrix());
        /*
         * This code is here to deal with an observed problem with the changing 
         * of case in request parameters.  See the class EnumChecker for 
         * enlightenment.
         */
        DistanceMatrixType distanceMatrixType = null;
        String myTypeString = EnumHelper.getEnumTypeName(hierarchicalClusteringForm.getDistanceMatrix(),DistanceMatrixType.values());
        if(myTypeString!=null) {
        	distanceMatrixType = DistanceMatrixType.valueOf(myTypeString);
        }else {
        	logger.error("Invalid DistanceMatrixType value given in request");
        	logger.error("Selected DistanceMatrixType value = "+hierarchicalClusteringForm.getDistanceMatrix());
        	logger.error("Using the safety DistanceMatrixType value = "+ERROR_DISTANCE_MATRIX_TYPE);
        	distanceMatrixType = ERROR_DISTANCE_MATRIX_TYPE;
        }
       DistanceMatrixTypeDE distanceMatrixTypeDE = new DistanceMatrixTypeDE(distanceMatrixType);
       hierarchicalClusteringQueryDTO.setDistanceMatrixTypeDE(distanceMatrixTypeDE);
        
        // create linkageMethodDEs
         /*
         * This code is here to deal with an observed problem with the changing 
        * of case in request parameters.  See the class EnumChecker for 
         * enlightenment.
        */
        LinkageMethodType linkageMethodType = null;
        String linkageMethodTypeName = EnumHelper.getEnumTypeName(hierarchicalClusteringForm.getLinkageMethod(),LinkageMethodType.values());
        if(linkageMethodTypeName!=null) {
        	linkageMethodType = LinkageMethodType.valueOf(linkageMethodTypeName);
        }else {
        	logger.error("Invalid LinkageMethodType value given in request");
        	logger.error("Selected LinkageMethodType value = "+hierarchicalClusteringForm.getLinkageMethod());
        	logger.error("Using the safety LinkageMethodType value = "+ERROR_LINKAGE_METHOD_TYPE);
        	linkageMethodType = ERROR_LINKAGE_METHOD_TYPE;
        }
        LinkageMethodTypeDE linkageMethodTypeDE = new LinkageMethodTypeDE(linkageMethodType);
        hierarchicalClusteringQueryDTO.setLinkageMethodTypeDE(linkageMethodTypeDE);
        
        //create ArrayPlatformDE
        if(hierarchicalClusteringForm.getArrayPlatform()!=null || hierarchicalClusteringForm.getArrayPlatform().length()!=0){
            hierarchicalClusteringQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(hierarchicalClusteringForm.getArrayPlatform()));
        }
        
        
        if(!hierarchicalClusteringForm.getClusterBy().equals("")){
        	 /*
             * This code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumChecker for 
            * enlightenment.
            */
        	ClusterByType clusterByType = null;
            String clusterByTypeName = EnumHelper.getEnumTypeName(hierarchicalClusteringForm.getClusterBy(),ClusterByType.values());
            if(clusterByTypeName!=null) {
            	clusterByType = ClusterByType.valueOf(clusterByTypeName);
            }else {
            	logger.error("Invalid ClusterByType value given in request");
            	logger.error("Selected ClusterByType value = "+hierarchicalClusteringForm.getClusterBy());
            	logger.error("Using the safety ClusterByType value = "+ERROR_CLUSTER_BY_TYPE);
            	clusterByType = ERROR_CLUSTER_BY_TYPE;
            }
        	hierarchicalClusteringQueryDTO.setClusterTypeDE(new ClusterTypeDE(clusterByType));
        }
        
        return hierarchicalClusteringQueryDTO;
    }

    
    
}
