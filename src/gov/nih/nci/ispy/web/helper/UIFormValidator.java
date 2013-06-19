/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.helper;


import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.ispy.util.ispyConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * @author BauerD 
 * Dec 15, 2004 
 * This class is used to validate input fields from the UI
 *  
 */




public class UIFormValidator {
    private static Logger logger = Logger.getLogger(UIFormValidator.class);
    
   
	public static ActionErrors validateQueryName(String queryName,
			ActionErrors errors) {
		if ((queryName == null || queryName.length() < 1)) {
			errors.add("analysisResultName", new ActionMessage(
					"gov.nih.nci.nautilus.ui.struts.form.analysisResultName.no.error"));
		}
		return errors;
	}
    
    public static ActionErrors parseDouble(String doubleString, String field, ActionErrors errors){
        try{
            Double.parseDouble(doubleString);
         }catch(NumberFormatException e){
             logger.debug("the value " + doubleString + " cannot be parsed");
             if(field.equalsIgnoreCase("pathTumorSize")){
                 errors.add("pathTumorSizeParse", new ActionMessage("gov.nih.nci.nautilus.ui.struts.form.parse.error"));
             }
             if(field.equalsIgnoreCase("diameter")){
                 errors.add("diameterParse", new ActionMessage("gov.nih.nci.nautilus.ui.struts.form.parse.error"));
             }
         }
        return errors;
    }
    
    public static ActionErrors validateSelectedGroups(String[] selectedGroups, String timepointRange, String timepointBaseAcross, String timepointComparison, HttpSession session, ActionErrors errors){
            if(timepointRange.equalsIgnoreCase("fixed")){
                if (selectedGroups == null || selectedGroups.length != 2){
                    errors.add("selectedGroups1", new ActionMessage(
                            "gov.nih.nci.nautilus.ui.struts.form.groups.two.error"));
                    return errors;
                }
                String[] comparisonGroup = selectedGroups[0].split("#");
                String comparisonClass = comparisonGroup[0];
                String[] baselineGroup = selectedGroups[1].split("#");
                String baselineClass = baselineGroup[0];
                if(baselineClass.equals("gov.nih.nci.caintegrator.application.lists.UserList") && comparisonClass.equals("gov.nih.nci.caintegrator.application.lists.UserList")){
                    List<String> userLists = new ArrayList<String>();
                    userLists.add(comparisonGroup[1]);
                    userLists.add(baselineGroup[1]);
                    UserListBeanHelper helper = new UserListBeanHelper(session);                    
                    if(helper.isIntersection(userLists)){
                        errors.add("selectedGroups2", new ActionMessage(
                        "gov.nih.nci.nautilus.ui.struts.form.groups.intersect.error")); 
                    }
                }
                
            }
            else if(timepointRange.equalsIgnoreCase("across")){
                if (timepointBaseAcross.equals(timepointComparison)){
                    errors.add("timepoints", new ActionMessage(
                    "gov.nih.nci.nautilus.ui.struts.form.timepoints.same.error"));
                }
                if (selectedGroups == null || selectedGroups.length != 1){
                    errors.add("selectedGroups3", new ActionMessage(
                            "gov.nih.nci.nautilus.ui.struts.form.groups.no.error"));
                    return errors;
                }
                
            }

        return errors;
    }
    
    public static ActionErrors validateSelectedGroups(String[] selectedGroups, ActionErrors errors){
        if (selectedGroups == null || selectedGroups.length < 1){
            errors.add("selectedGroups3", new ActionMessage(
                    "gov.nih.nci.nautilus.ui.struts.form.groups.no.error"));            
        }
        return errors;
    }
    
    public static ActionErrors validateHCTimepoints(String[] timepoints,
            ActionErrors errors) {
        if ((timepoints == null || timepoints.length < 1)) {
            errors.add("timepoints", new ActionMessage(
                    "gov.nih.nci.nautilus.ui.struts.form.timepoints.no.error"));
        }
        return errors;
    }

    public static ActionErrors validatePatientGroup(String patientGroup, ActionErrors errors) {
        if(patientGroup.equalsIgnoreCase("none")){
//            errors.add("patients", new ActionMessage(
//                    "gov.nih.nci.nautilus.ui.struts.form.patients.no.error"));
        }
        return errors;
    }

    public static ActionErrors validateReporterSelection(String xaxis, String yaxis, String reporterX, String reporterY, ActionErrors errors) {
        if(xaxis!=null){
            if(xaxis.equalsIgnoreCase(ispyConstants.CONTINUOUS_GENE_STRING) && (reporterX.equalsIgnoreCase("none") || reporterX.equalsIgnoreCase(""))){
                errors.add("reporterSelectionX", new ActionMessage(
                    "gov.nih.nci.nautilus.ui.struts.form.reporterSelection.no.error"));
            }
        }
        if(yaxis.equalsIgnoreCase(ispyConstants.CONTINUOUS_GENE_STRING) && reporterY.equalsIgnoreCase("none")){
            errors.add("reporterSelectionY", new ActionMessage(
                "gov.nih.nci.nautilus.ui.struts.form.reporterSelection.no.error"));
        }
        return errors;
    }

    public static ActionErrors validateAxis(String xaxis, String yaxis, ActionErrors errors) {
        if(xaxis!=null){
            if(xaxis.equalsIgnoreCase("none")){
            errors.add("xaxis", new ActionMessage(
            "gov.nih.nci.nautilus.ui.struts.form.axis.no.error"));
            }
        }
        if(yaxis.equalsIgnoreCase("none")){
            errors.add("yaxis", new ActionMessage(
            "gov.nih.nci.nautilus.ui.struts.form.axis.no.error"));
        }
        
        if(xaxis!=null){
            if (!xaxis.equalsIgnoreCase(ispyConstants.CONTINUOUS_GENE_STRING) && !yaxis.equalsIgnoreCase(ispyConstants.CONTINUOUS_GENE_STRING)) {
            	errors.add("nogene", new ActionMessage(
                "gov.nih.nci.nautilus.ui.struts.form.axis.notbothgene.error"));
            }
        }
        
        return errors;
    }
    
}