/**
 * This validator will be unique to ISPY's validation
 * process.
 */
package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;
import gov.nih.nci.caintegrator.application.lists.UserList;

/**
 * @author rossok
 *
 */
public class ISPYListValidator implements ListValidator{

    public UserList getValidList(ListType listType, UserList unvalidatedList) {
        // TODO Auto-generated method stub
        return null;
    }

    public UserList getInvalidList() {
        // TODO Auto-generated method stub
        return null;
    }

}
