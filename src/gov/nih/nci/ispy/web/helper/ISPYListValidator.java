/**
 * This validator will be unique to ISPY's validation
 * process.
 */
package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;

import java.util.List;

/**
 * @author rossok
 *
 */
public class ISPYListValidator implements ListValidator{

    public List<String> getValidList(ListType listType, List<String> myList) {
        // TODO Auto-generated method stub
        return myList;
    }

    public List<String> getInvalidList() {
        // TODO Auto-generated method stub
        return null;
    }

}
