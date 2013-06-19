/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Min You*/
public class UserSession implements Serializable {

    public static String FIELD_SESSIONID = "EduColumbiaC2b2IntragenDataUserSession_sessionId";
    public static String FIELD_LASTLOGINTIME = "EduColumbiaC2b2IntragenDataUserSession_lastLoginTime";
    public static String FIELD_EXPIRATIONTIME = "EduColumbiaC2b2IntragenDataUserSession_expirationTime";
    public static String FIELD_USER = "EduColumbiaC2b2IntragenDataUserSession_user";

    /** identifier field */
    private String sessionId;

    /** nullable persistent field */
    private java.util.Date lastLoginTime;

    /** nullable persistent field */
    private java.util.Date expirationTime;

    /** nullable persistent field */
    private WebUser user;

    /** full constructor */
    public UserSession(java.lang.String sessionId, java.util.Date lastLoginTime, java.util.Date expirationTime, WebUser user) {
        this.sessionId = sessionId;
        this.lastLoginTime = lastLoginTime;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    /** default constructor */
    public UserSession() {
    }

    /** minimal constructor */
    public UserSession(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }

    public java.lang.String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }

    public java.util.Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(java.util.Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public java.util.Date getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(java.util.Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    /** 
     * User associated with this session.
     */
    public WebUser getUser() {
        return this.user;
    }

    public void setUser(WebUser user) {
        this.user = user;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("sessionId", getSessionId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserSession) ) return false;
        UserSession castOther = (UserSession) other;
        return new EqualsBuilder()
            .append(this.getSessionId(), castOther.getSessionId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSessionId())
            .toHashCode();
    }

}
