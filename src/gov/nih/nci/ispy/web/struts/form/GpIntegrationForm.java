/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.form;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.helper.ClinicalGroupRetriever;
import gov.nih.nci.ispy.web.helper.UIFormValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;






public class GpIntegrationForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(BaseForm.class);
	
    private String timepoint;
    
    private String [] existingGroups;
    
    private List existingGroupsList;
    
    private Collection timepointCollection = new ArrayList();
    
    private String [] selectedGroups;
    
    private String arrayPlatform = "";
    
    private String analysisResultName = "";
    
    private String geneSetName = "";
    
    private List geneSetNameList;

	public GpIntegrationForm(){
			
		// Create Lookups for ClassComparisonForm screens 
        for (TimepointType timepointType : TimepointType.values()){
            timepointCollection.add(new LabelValueBean(timepointType.toString(),timepointType.name()));
        }
    }

    /**
     * @return Returns the existingGroups.
     */
    public String[] getExistingGroups() {
        return existingGroups;
    }

    /**
     * @param existingGroups The existingGroups to set.
     */
    public void setExistingGroups(String [] existingGroups) {
        this.existingGroups = existingGroups;
    }

    /**
     * @return Returns the existingGroupsList.
     */
    public List getExistingGroupsList() {
        return this.existingGroupsList;
    }

    /**
     * @param existingGroupsList The existingGroupsList to set.
     */
    public void setExistingGroupsList(List existingGroupsList) {
        this.existingGroupsList = existingGroupsList;
    }

    /**
     * @return Returns the selectedGroups.
     */
    public String [] getSelectedGroups() {
        return selectedGroups;
    }

    /**
     * @param selectedGroups The selectedGroups to set.
     */
    public void setSelectedGroups(String [] selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    /**
     * @return Returns the arrayPlatform.
     */
    public String getArrayPlatform() {
        return arrayPlatform;
    }

    /**
     * @param arrayPlatform The arrayPlatform to set.
     */
    public void setArrayPlatform(String arrayPlatform) {
        this.arrayPlatform = arrayPlatform;
    }
    
    /**
     * @return Returns the timepointCollection.
     */
    public Collection getTimepointCollection() {
        return timepointCollection;
    }

    /**
     * @param timepointCollection The timepointCollection to set.
     */
    public void setTimepointCollection(Collection timepointCollection) {
        this.timepointCollection = timepointCollection;
    }


	public String getAnalysisResultName() {
		return analysisResultName;
	}

	public void setAnalysisResultName(String analysisResultName) {
		this.analysisResultName = analysisResultName;
	}

    /**
     * Method validate
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

        if (this.selectedGroups == null || this.selectedGroups.length < 2)
            errors.add("selectedGroups1", new ActionMessage(
            "gov.nih.nci.nautilus.ui.struts.form.groups.two.or.more.error"));
        //User must select exactly 2 comparison Groups
        //errors = UIFormValidator.validateSelectedGroups(selectedGroups, timepointRange, timepointBaseAcross, timepointComparison, request.getSession(), errors);
        if (errors.size() > 0){
            ClinicalGroupRetriever clinicalGroupRetriever = new ClinicalGroupRetriever(request.getSession());
            this.setExistingGroupsList(clinicalGroupRetriever.getClinicalGroupsCollection());

            UserListBeanHelper listHelper = new UserListBeanHelper(request.getSession());
            //fetch the users gene groups populate the dropdown
            List<String> names = (List<String>) listHelper.getGenericListNames(ListType.Gene);
            List<LabelValueBean> gsNameList = new ArrayList<LabelValueBean>();
                for(String listName : names){
                    gsNameList.add(new LabelValueBean(listName,listName));
                }
                this.setGeneSetNameList(gsNameList);
        }
        return errors;
    }

    /**
     * Method reset
     * 
     * @param ActionMapping
     *            mapping
     * @param HttpServletRequest
     *            request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {            
        arrayPlatform = "";             
    }

	public String getGeneSetName() {
		return geneSetName;
	}

	public void setGeneSetName(String geneSetName) {
		this.geneSetName = geneSetName;
	}

	public List getGeneSetNameList() {
		return geneSetNameList;
	}

	public void setGeneSetNameList(List geneSetNameList) {
		this.geneSetNameList = geneSetNameList;
	}

	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
}
