/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.util.HibernateUtil;
import gov.nih.nci.ispy.service.ihc.LevelOfExpressionIHCService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

public class UILookupMapLoader {
    static private Map<String, Collection> lookupMap = new HashMap<String, Collection>();
    
    public static Map<String, Collection> getMap(){
        Session theSession = HibernateUtil.getSession();
        //Session theSession = HibernateUtil.getSessionFactory().getCurrentSession();
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
        
        //GET IHC_LOSS_RESULTCODES
        theHQL = "select distinct lossExp.lossResult from LossOfExpressionIHCFinding lossExp where lossExp.lossResult!=null";
        theQuery = theSession.createQuery(theHQL);
        System.out.println("HQL: " + theHQL);        
        objs = theQuery.list();
        ArrayList<String> resultCodes = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.IHC_LOSS_RESULTCODES,resultCodes);
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
