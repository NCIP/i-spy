package gov.columbia.c2b2.ispy.web.struts.form;

 
 

 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
 
import gov.columbia.c2b2.ispy.user.AuditInfo;
import gov.columbia.c2b2.ispy.user.WebUser;
import gov.columbia.c2b2.ispy.util.CsmSecurityManager;
import gov.columbia.c2b2.ispy.util.Util;
import gov.nih.nci.ispy.web.struts.form.BaseForm; 
import gov.nih.nci.ispy.util.ispyConstants;

public class WebUserListForm extends BaseForm{

	    private static final long serialVersionUID = 1L;
	    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
	    
	    
	    private String action; 
	    private String firstName;    
	    private String lastName;
	    private String email;    
	    private String oldEmail; 
	    private String password;   
	    //private String title;     
	    private String institution;     
	    private String department;    
	    private String address1;    
	    private String address2;    
	    private String city;     
	    private String state;  
	    private String country;     
	    private String phone;     
	    private String intendedUse; 
	    private String confirmPassword;
	    private boolean submitSuccess =false;
	   
	    private CsmSecurityManager secManager =  CsmSecurityManager.getInstance("ispy") ;
	   
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
	     * The user email.
	     */
	    public java.lang.String getOldEmail() {
	        return this.oldEmail;
	    }

	    public void setOldEmail(java.lang.String oldEmail) {
	        this.oldEmail = oldEmail;
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
	     * The user's password
	     */
	    public java.lang.String getConfirmPassword() {
	        return this.confirmPassword;
	    }

	    public void setConfirmPassword(java.lang.String confirmPassword) {
	        this.confirmPassword = confirmPassword;
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

	    public void setAction(java.lang.String action) {
	        this.action = action;
	    }

	    public java.lang.String getAction() {
	        return this.action;
	    }

	    public void setIntendedUse(java.lang.String intendedUse) {
	        this.intendedUse = intendedUse;
	    }
	    
	    
	    public boolean getSubmitSuccess()
	    {
	    	return this.submitSuccess;
	    	
	    }
	    
	    public void loadUser(WebUser user)
	    {
	    	user.setFirstName(firstName);    
	    	user.setLastName(lastName);
	    	user.setEmail(email);    
	    	user.setPassword(password);             
	    	user.setInstitution(institution);     
	    	user.setDepartment(department);    
	    	user.setAddress1(address1);    
	    	user.setAddress2(address2);    
	    	user.setCity(city);     
	    	user.setState(state);  
	    	user.setCountry(country);     
	    	user.setPhone(phone);  
	    	user.setIntendedUse(intendedUse);
	    	user.setInstitution(institution);
	    	user.setAuditInfo(new AuditInfo(null,null,"system",Util.getNow()));
	    	
	    	    	 
	    }
	    
	    
	       
	    
	    public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request)
	   {
	    	
	    	ActionErrors errors = new ActionErrors();  
	    	   	
	    	logger.info("do nothing");
	    		    	
	    	
		     return errors;
	
      }
	    
	   
}
