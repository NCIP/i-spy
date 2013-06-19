/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UILookupMapLoader {
    static private Map<String, Collection> lookupMap = new HashMap<String, Collection>();
    // This is the Hibernate Session factory that is injected by Spring
    private SessionFactory sessionFactory;
    
    /**
     * @return Returns the sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory The sessionFactory to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Map<String, Collection> getMap(){
        //Session theSession = HibernateUtil.getSession();
        Session theSession = sessionFactory.getCurrentSession();
        //Session theSession = HibernateUtil.getSessionFactory().getCurrentSession();
        //theSession.beginTransaction();
        String theHQL = "";
        Query theQuery = null;
        Collection objs = null;
        
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
        
        
         //GET P53 mutation status
        theHQL = "select distinct p53Mutation.mutationStatus from P53MutationFinding p53Mutation where p53Mutation.mutationStatus!=null";
        theQuery = theSession.createQuery(theHQL);
        System.out.println("HQL: " + theHQL);        
        objs = theQuery.list();
        ArrayList<String> mutationStatuses = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.P53_MUTATION_STATUS,mutationStatuses);
        
        
        //GET P53 mutation type
        theHQL = "select distinct p53Mutation.mutationType from P53MutationFinding p53Mutation where p53Mutation.mutationType!=null";
        theQuery = theSession.createQuery(theHQL);
        System.out.println("HQL: " + theHQL);        
        objs = theQuery.list();
        ArrayList<String> mutationTypes = new ArrayList<String>(objs);
        lookupMap.put(ispyConstants.P53_MUTATION_TYPE,mutationTypes);      
        
        
        
        
        return lookupMap;
    }

}
