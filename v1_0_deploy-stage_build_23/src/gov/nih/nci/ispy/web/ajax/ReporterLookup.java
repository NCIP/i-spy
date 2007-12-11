package gov.nih.nci.ispy.web.ajax;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;
import gov.nih.nci.ispy.web.helper.EnumHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ReporterLookup {

    public ReporterLookup() {}
    
    /**
     * this ajax method looks up reporters using gexpannotation service
     * based on gene symbol and array platform. An addition parameter is 
     * provided so that the callback(javascript) knows where to put the collection
     * This last paramter is an id from the ui element.
     * NOTE: the id passed in WILL be used in the callback...make sure it is there!
     */
    public String lookup(String gene, String arrayPlatform, String uiElement){
        Collection<String> reporters = new HashSet<String>();
        //set up geneCollection
        Collection<String> genes = new ArrayList<String>();
        genes.add(gene);
        //set up array platform by finding the appropriate type
        String[] uiString = arrayPlatform.split("#");
        String myClassName = uiString[0];
        String myValueName = uiString[1];    
        Enum myType = EnumHelper.createType(myClassName,myValueName);
              
        if(myType instanceof ArrayPlatformType){
            GeneExprAnnotationService gs = GeneExprAnnotationServiceFactory.getInstance();
            reporters = gs.getReporterNamesForGeneSymbols(genes,(ArrayPlatformType)myType);
        }
        /* reporterResultArray variable holds reporter objects like the result 
         * list of reporters (in its own array called reporterList), 
         * the results String, and the UIElement
        */
        JSONArray reporterResultArray = new JSONArray();
        JSONObject resultsObject = new JSONObject();
        
        JSONArray reporterList = new JSONArray();
        for(String reporter : reporters){
            reporterList.add(reporter);
        }
        String results = "notFound";
        if(!reporterList.isEmpty()){
            results = "found";            
        }
        resultsObject.put("gene", gene);
        resultsObject.put("results",results);
        resultsObject.put("reporters",reporterList);
        resultsObject.put("elementId", uiElement);
        reporterResultArray.add(resultsObject);
        
        return reporterResultArray.toString();
    }

}
