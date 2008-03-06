package gov.columbia.c2b2.ispy.web.struts.form;
 
import java.util.List;
 
import java.util.Random;

 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
 
import gov.columbia.c2b2.ispy.user.*;
import gov.columbia.c2b2.ispy.util.*;
 
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
 
import gov.nih.nci.ispy.web.struts.form.BaseForm;
import gov.nih.nci.ispy.util.SecurityHelper;
import gov.nih.nci.ispy.util.ispyConstants;



public class RegisterForm extends BaseForm{
	
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    
    private String action; 
    private String firstName;    
    private String lastName;
    private String email;    
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
    	user.setInstitution(institution);
    	user.setIntendedUse(intendedUse);
    	user.setAuditInfo(new AuditInfo(null,null,"system",Util.getNow()));
    	
    	    	 
    }
    
    public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
{
    	   	
    	
    	ActionErrors errors = new ActionErrors();  	       
    	
    	try
    	{
    	   
          if (Constants.ACTION_SUBMIT_REGISTRATION.equals(this.action)) {
            // todo
        	
            WebUser newUser = new WebUser();
            // Load user data from form
            loadUser(newUser);
            
         
           
            // Check for existing user with same email address
            newUser.setUsername(newUser.getEmail());
                     
             
            SecurityHelper securityHelper = (SecurityHelper)SpringContext.getBean("securityHelper");
             
            
            
            WebUser existingUser = securityHelper.findByUsername(newUser.getUsername(), true);
            if (existingUser != null ) {
            	// errors.add("invalidRequest", new ActionMessage(
                // "A user with the specified email already exists."));
            	 
            	if (existingUser.getStatus().getStatus() != 'D')
            	{
            		request.setAttribute(Constants.ATTRIBUTE_STATUS_HEADER, "invalidRequest");
            	    request.setAttribute(Constants.ATTRIBUTE_STATUS_MESSAGE,"A user with the specified email already exists.");
            	}
            	else
            	{
            		request.setAttribute(Constants.ATTRIBUTE_STATUS_HEADER, "invalidRequest");
            	    request.setAttribute(Constants.ATTRIBUTE_STATUS_MESSAGE,"A user with the specified email has been deactivated, Please cantact " + Config.getConfig().getContactUsEmail() +".");
            	}
            
            }
            else
            {
               
               // Go ahead and create user
               newUser.setRegisteredDate(Util.getNow());
               Random random = new Random();
               newUser.setRegistrationKey(random.nextInt());
                 
               newUser.setStatus(new Status(Constants.STATUS_PENDING));
                           
               securityHelper.save(newUser);          
            
               sendAdminEmail(newUser);
               request.setAttribute(Constants.ATTRIBUTE_USER_EMAIL, newUser.getEmail());
               submitSuccess = true;
             }//end else
            
           }//end action
           
    	  }//end try
          catch (Exception e)
          {
        	  logger.error(e.getMessage());
          }
    	
    	
    	
	     return errors;
		 	 
}
    
   private void sendAdminEmail(WebUser user ) throws Exception {
        String body = "User " + user.getFirstName() + " " + user.getLastName() + " has registered with email address " + user.getEmail() + ".\n\n";
         
         body += "User requires approval" + /*due to non-EDU email address.*/  "\n\n";
            
        String fromAddress = Config.getConfig().getEmailFromSystem();
        //List<gov.nih.nci.security.authorization.domainobjects.User> admins = secManager.findAdminUsers(); 
        // Send to all admins
        SMTPMailer mailer = new SMTPMailer();
        
        /*for (gov.nih.nci.security.authorization.domainobjects.User admin : admins) {
            String adminEmail = admin.getEmailId();
        	mailer.sendEmail("ISPY User Registration", body, null, fromAddress, adminEmail);
        }*/
        
        mailer.sendEmail("ISPY User Registration", body, null, fromAddress,  Config.getConfig().getContactUsEmail());
    }
       
    
    
}
