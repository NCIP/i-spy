/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Status implements Serializable {

    public static String FIELD_STATUS = "EduColumbiaC2b2IntragenDataStatus_status";

    /** nullable persistent field */
    private char status;

    /** full constructor */
    public Status(char status) {
        this.status = status;
    }

    /** default constructor */
    public Status() {
    }

    public char getStatus() {
        return this.status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
