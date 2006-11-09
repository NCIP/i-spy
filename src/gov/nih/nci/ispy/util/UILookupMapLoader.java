package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

public class UILookupMapLoader {
    static private Map<String, Collection> lookupMap = new HashMap<String, Collection>();
    
    public static Map<String, Collection> getMap(){
        Session theSession = HibernateUtil.getSession();
        theSession.beginTransaction();
        String theHQL = "";
        Query theQuery = null;
        Collection objs = null;
        
        try{
        //GET BIOMARKERS       
        theHQL = "from ProteinBiomarker AS p";        
        theQuery = theSession.createQuery(theHQL);
        System.out.println("HQL: " + theHQL);        
        objs = theQuery.list();
        ArrayList<String> biomarkers = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.IHC_BIOMARKERS,biomarkers);
                
        //GET INTENSITIES
        theHQL = "select distinct loe.stainIntensity from LevelOfExpressionIHCFinding loe where loe.stainIntensity!=null";
        theQuery = theSession.createQuery(theHQL);
        System.out.println("HQL: " + theHQL);        
        objs = theQuery.list();
        ArrayList<String> intensityValues = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.IHC_STAIN_INTENSITY,intensityValues);
        
        //GET LOCALIZATION
        theHQL = "select distinct loe.stainLocalization from LevelOfExpressionIHCFinding loe where loe.stainLocalization!=null";
        theQuery = theSession.createQuery(theHQL);
        System.out.println("HQL: " + theHQL);        
        objs = theQuery.list();
        ArrayList<String> localeValues = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.IHC_STAIN_LOCALIZATION,localeValues);
        
        }
        finally
        {
            // Close the session if necessart
            if (theSession != null)
            {
                theSession.close();
            }
        }

        return lookupMap;
    }

}
