/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.user;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class WebUser implements Serializable {

    public static String FIELD_ID = "GovColumbiaC2b2IspyUserWebUser_id";
    public static String FIELD_USERNAME = "GovColumbiaC2b2IspyUserWebUser_username";
    public static String FIELD_EMAIL = "GovColumbiaC2b2IspyUserWebUser_email";
    public static String FIELD_PASSWORD = "GovColumbiaC2b2IspyUserWebUser_password";
    public static String FIELD_FIRSTNAME = "GovColumbiaC2b2IspyUserWebUser_firstName";
    public static String FIELD_LASTNAME = "GovColumbiaC2b2IspyUserWebUser_lastName";
    public static String FIELD_TITLE = "GovColumbiaC2b2IspyUserWebUser_title";
    public static String FIELD_INSTITUTION = "GovColumbiaC2b2IspyUserWebUser_institution";
    public static String FIELD_DEPARTMENT = "GovColumbiaC2b2IspyUserWebUser_department";
    public static String FIELD_ADDRESS1 = "GovColumbiaC2b2IspyUserWebUser_address1";
    public static String FIELD_ADDRESS2 = "GovColumbiaC2b2IspyUserWebUser_address2";
    public static String FIELD_CITY = "GovColumbiaC2b2IspyUserWebUser_city";
    public static String FIELD_STATE = "GovColumbiaC2b2IspyUserWebUser_state";
    public static String FIELD_ZIP = "GovColumbiaC2b2IspyUserWebUser_zip";
    public static String FIELD_COUNTRY = "GovColumbiaC2b2IspyUserWebUser_country";
    public static String FIELD_PHONE = "GovColumbiaC2b2IspyUserWebUser_phone";
    public static String FIELD_INTENDEDUSE = "GovColumbiaC2b2IspyUserWebUser_intendedUse";
    public static String FIELD_REGISTEREDDATE = "GovColumbiaC2b2IspyUserWebUser_registeredDate";
    public static String FIELD_ACCEPTDATE = "GovColumbiaC2b2IspyUserWebUser_registeredDate";
    public static String FIELD_REGISTRATIONKEY = "GovColumbiaC2b2IspyUserWebUser_registrationKey";
    public static String FIELD_NUMBEROFEXPORTS = "GovColumbiaC2b2IspyUserWebUser_numberOfExports";
    public static String FIELD_LOGINS = "GovColumbiaC2b2IspyUserWebUser_logins";
    public static String FIELD_STATUS = "GovColumbiaC2b2IspyUserWebUser_status";
    public static String FIELD_AUDITINFO = "GovColumbiaC2b2IspyUserWebUser_auditInfo";

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private String firstName;

    /** nullable persistent field */
    private String lastName;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String institution;

    /** nullable persistent field */
    private String department;

    /** nullable persistent field */
    private String address1;

    /** nullable persistent field */
    private String address2;

    /** nullable persistent field */
    private String city;

    /** nullable persistent field */
    private String state;

    /** nullable persistent field */
    private String zip;

    /** nullable persistent field */
    private String country;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String intendedUse;

    /** nullable persistent field */
    private java.util.Date registeredDate;
    
    /** nullable persistent field */
    private java.util.Date acceptDate;

    /** nullable persistent field */
    private int registrationKey;

    /** nullable persistent field */
    private int numberOfExports;

    /** persistent field */
    private Set logins;   

    /** persistent field */
    private gov.columbia.c2b2.ispy.user.Status status;

    /** persistent field */
    private gov.columbia.c2b2.ispy.user.AuditInfo auditInfo;

    /** full constructor */
    public WebUser(java.lang.String username, java.lang.String email, java.lang.String password, java.lang.String firstName, java.lang.String lastName, java.lang.String title, java.lang.String institution, java.lang.String department, java.lang.String address1, java.lang.String address2, java.lang.String city, java.lang.String state, java.lang.String zip, java.lang.String country, java.lang.String phone, java.lang.String intendedUse, java.util.Date registeredDate, int registrationKey, int numberOfExports, Set logins, Set roles, Set files, Status status, AuditInfo auditInfo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.institution = institution;
        this.department = department;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
        this.intendedUse = intendedUse;
        this.registeredDate = registeredDate;
        this.registrationKey = registrationKey;
        this.acceptDate = acceptDate;
        this.logins = logins;        
        this.status = status;
        this.auditInfo = auditInfo;
    }

    /** default constructor */
    public WebUser() {
    }

    /** minimal constructor */
    public WebUser(Set logins, Set roles, Set files, Status status, AuditInfo auditInfo) {
        this.logins = logins;       
        this.status = status;
        this.auditInfo = auditInfo;
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    /** 
     * The username (often the user's email).
     */
    public java.lang.String getUsername() {
        return this.username;
    }

    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    /** 
     * The user email.
     */
    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /** 
     * The user's password
     */
    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    /** 
     * User first name.
     */
    public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    /** 
     * User last name.
     */
    public java.lang.String getLastName() {
        return this.lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    /** 
     * The user's job title.
     */
    public java.lang.String getTitle() {
        return this.title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    /** 
     * The user's institution.
     */
    public java.lang.String getInstitution() {
        return this.institution;
    }

    public void setInstitution(java.lang.String institution) {
        this.institution = institution;
    }

    /** 
     * The user's department.
     */
    public java.lang.String getDepartment() {
        return this.department;
    }

    public void setDepartment(java.lang.String department) {
        this.department = department;
    }

    public java.lang.String getAddress1() {
        return this.address1;
    }

    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }

    public java.lang.String getAddress2() {
        return this.address2;
    }

    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
    }

    public java.lang.String getCity() {
        return this.city;
    }

    public void setCity(java.lang.String city) {
        this.city = city;
    }

    public java.lang.String getState() {
        return this.state;
    }

    public void setState(java.lang.String state) {
        this.state = state;
    }

    public java.lang.String getZip() {
        return this.zip;
    }

    public void setZip(java.lang.String zip) {
        this.zip = zip;
    }

    public java.lang.String getCountry() {
        return this.country;
    }

    public void setCountry(java.lang.String country) {
        this.country = country;
    }

    public java.lang.String getPhone() {
        return this.phone;
    }

    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    /** 
     * Brief Description of Intended Data Use.
     */
    public java.lang.String getIntendedUse() {
        return this.intendedUse;
    }

    public void setIntendedUse(java.lang.String intendedUse) {
        this.intendedUse = intendedUse;
    }

    /** 
     * The date the user registered.
     */
    public java.util.Date getRegisteredDate() {
        return this.registeredDate;
    }

    /** 
     * The date the user accept agreement.
     */
    public java.util.Date getAcceptDate() {
        return this.acceptDate;
    }

    public void setRegisteredDate(java.util.Date registeredDate) {
        this.registeredDate = registeredDate;
    }
    
    public void setAcceptDate(java.util.Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public int getRegistrationKey() {
        return this.registrationKey;
    }

    public void setRegistrationKey(int registrationKey) {
        this.registrationKey = registrationKey;
    }

    public int getNumberOfExports() {
        return this.numberOfExports;
    }

    public void setNumberOfExports(int numberOfExports) {
        this.numberOfExports = numberOfExports;
    }

    /** 
     * Login tracking information.
     */
    public java.util.Set getLogins() {
        return this.logins;
    }

    public void setLogins(java.util.Set logins) {
        this.logins = logins;
    }   
     

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AuditInfo getAuditInfo() {
        return this.auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof WebUser) ) return false;
        WebUser castOther = (WebUser) other;
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
