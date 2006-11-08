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
        
        //GET BIOMARKERS
        StringBuilder theHQL = new StringBuilder();
        // Start of the where clause
        theHQL.append("from ProteinBiomarker AS p  ");
        Session theSession = HibernateUtil.getSession();
        theSession.beginTransaction();
        Query theQuery = theSession.createQuery(theHQL.toString());
        System.out.println("HQL: " + theHQL.toString());        
        Collection objs = theQuery.list();
        ArrayList<String> biomarkers = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.IHC_BIOMARKERS,biomarkers);
        
        //GET INTENSITIES
        
        
        return lookupMap;
    }

}
