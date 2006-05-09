/**
 * This validator will be unique to ISPY's validation
 * process.
 */
package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rossok
 *
 */
public class ISPYListValidator implements ListValidator{
    List<String> invalidList = new ArrayList<String>();
    List<String> validList = new ArrayList<String>();

    public List<String> getValidList(ListType listType, List<String> myList) {
       if(listType == ListType.PatientDID){
            IdMapperFileBasedService idMapper = null;
            idMapper = IdMapperFileBasedService.getInstance();
            List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(myList);
            Set<RegistrantInfo> set = new HashSet<RegistrantInfo>();
            set.addAll( entries );
            for(RegistrantInfo ri : set){
                validList.add(ri.getRegistrationId());
            }
        }
       else{
           validList = myList;
       }
        return validList;
    }

    public List<String> getInvalidList(ListType listType, List<String> myList) {
        if(listType == ListType.PatientDID){
            IdMapperFileBasedService idMapper = null;
            idMapper = IdMapperFileBasedService.getInstance();
            invalidList = idMapper.getInvalidMapperEntriesForIds(myList);
        }
        
        return invalidList;
    }

}
