package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.ispy.util.ispyConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

public class P53Retriever {
	
	  private Map lookupMap;
	  
	  public P53Retriever(HttpSession session){        
	       this.lookupMap = (Map) session.getAttribute(ispyConstants.UI_LOOKUPS);
	   }
	    
	    
	  public List getMutationStatuses(){        
	        Collection mutationStatusCollectiion = (Collection) lookupMap.get(ispyConstants.P53_MUTATION_STATUS);      
	        ArrayList<String> mutationStatusList = new ArrayList<String>(mutationStatusCollectiion);
	        ArrayList<LabelValueBean> mutationStatusCollectiionList = new ArrayList<LabelValueBean>();
	        for(String mutationStatusName: mutationStatusList){
	        	mutationStatusCollectiionList.add(new LabelValueBean(mutationStatusName,mutationStatusName));
	        }
	      return mutationStatusCollectiionList;
	    }
	  
	  public List getMutationTypes(){        
	        Collection mutationTypeCollectiion = (Collection) lookupMap.get(ispyConstants.P53_MUTATION_TYPE);      
	        ArrayList<String> mutationtypeList = new ArrayList<String>(mutationTypeCollectiion);
	        ArrayList<LabelValueBean> mutationTypeCollectiionList = new ArrayList<LabelValueBean>();
	        for(String mutationTypeName: mutationtypeList){
	        	mutationTypeCollectiionList.add(new LabelValueBean(mutationTypeName,mutationTypeName));
	        }
	      return mutationTypeCollectiionList;
	    }
}
