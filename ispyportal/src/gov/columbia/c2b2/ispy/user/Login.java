package gov.columbia.c2b2.ispy.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Login implements Serializable {

    public static String FIELD_ID = "EduColumbiaC2b2IntragenDataLogin_id";
    public static String FIELD_LOGINDATE = "EduColumbiaC2b2IntragenDataLogin_loginDate";
    public static String FIELD_IPADDRESS = "EduColumbiaC2b2IntragenDataLogin_ipAddress";
    public static String FIELD_USER = "EduColumbiaC2b2IntragenDataLogin_user";

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private java.util.Date loginDate;

    /** nullable persistent field */
    private String ipAddress;

    /** nullable persistent field */
    private WebUser user;

    /** full constructor */
    public Login(java.util.Date loginDate, java.lang.String ipAddress, WebUser user) {
        this.loginDate = loginDate;
        this.ipAddress = ipAddress;
        this.user = user;
    }

    /** default constructor */
    public Login() {
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.util.Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(java.util.Date loginDate) {
        this.loginDate = loginDate;
    }

    /** 
     * The IP address from which the user connected
     */
    public java.lang.String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /** 
     * The User that logged in.
     */
    public WebUser getUser() {
        return this.user;
    }

    public void setUser(WebUser user) {
        this.user = user;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Login) ) return false;
        Login castOther = (Login) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
