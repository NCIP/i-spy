/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.domain.annotation.protein.bean.ProteinBiomarker;
import gov.nih.nci.ispy.util.ispyConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

public class IHCRetriever {    
    private Map lookupMap;
    
    public IHCRetriever(HttpSession session){        
        this.lookupMap = (Map) session.getAttribute(ispyConstants.UI_LOOKUPS);
    }
    
    public List getLevelBiomarkers(){        
        Collection biomarkers = (Collection) lookupMap.get(ispyConstants.IHC_BIOMARKERS);      
        ArrayList<ProteinBiomarker> biomarkerList = new ArrayList<ProteinBiomarker>(biomarkers);
        ArrayList<LabelValueBean> biomarkerCollection = new ArrayList<LabelValueBean>();
        for(ProteinBiomarker bioName: biomarkerList){
            if(!bioName.getName().equalsIgnoreCase("P27"))
            biomarkerCollection.add(new LabelValueBean(bioName.getName(),bioName.getName()));
        }
      return biomarkerCollection;
    }
    
    public List getLossBiomarkers(){        
        Collection biomarkers = (Collection) lookupMap.get(ispyConstants.IHC_BIOMARKERS);      
        ArrayList<ProteinBiomarker> biomarkerList = new ArrayList<ProteinBiomarker>(biomarkers);
        ArrayList<LabelValueBean> biomarkerCollection = new ArrayList<LabelValueBean>();
        for(ProteinBiomarker bioName: biomarkerList){
            if(bioName.getName().equalsIgnoreCase("P27"))
            biomarkerCollection.add(new LabelValueBean(bioName.getName(),bioName.getName()));
        }
      return biomarkerCollection;
    }
    
    public List getIntensity(){        
        Collection intensity = (Collection) lookupMap.get(ispyConstants.IHC_STAIN_INTENSITY);      
        ArrayList<String> intensityList = new ArrayList<String>(intensity);
        ArrayList<LabelValueBean> intensityCollection = new ArrayList<LabelValueBean>();
        for(String intensityName: intensityList){
            intensityCollection.add(new LabelValueBean(intensityName,intensityName));
        }
      return intensityCollection;
    }
    
    public List getLocalization(){        
        Collection locale = (Collection) lookupMap.get(ispyConstants.IHC_STAIN_LOCALIZATION);      
        ArrayList<String> localeList = new ArrayList<String>(locale);
        ArrayList<LabelValueBean> localeCollection = new ArrayList<LabelValueBean>();
        for(String localeName: localeList){
            localeCollection.add(new LabelValueBean(localeName,localeName));
        }
      return localeCollection;
    }
    
    public List ihcLossResultCodes(){        
        Collection resultCodes = (Collection) lookupMap.get(ispyConstants.IHC_LOSS_RESULTCODES);      
        ArrayList<String> resultCodesList = new ArrayList<String>(resultCodes);
        ArrayList<LabelValueBean> resultCodesCollection = new ArrayList<LabelValueBean>();
        for(String resultCodeName: resultCodesList){
            resultCodesCollection.add(new LabelValueBean(resultCodeName,resultCodeName));
        }
      return resultCodesCollection;
    }
    

}
