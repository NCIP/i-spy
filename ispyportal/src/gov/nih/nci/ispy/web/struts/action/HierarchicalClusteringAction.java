/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.action;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.columbia.c2b2.ispy.list.ListType;
import gov.columbia.c2b2.ispy.list.UserListBeanHelper;
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
