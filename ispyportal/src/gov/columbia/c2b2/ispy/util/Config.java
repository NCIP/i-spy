/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.util;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.*;

/**
 * Stores the config for the web application.
 */
public class Config {
    private static Config theConfig;

    static Log log = LogFactory.getLog(Config.class);    
    private String baseUrl = ""; 
    private String smtpHost = "";
    private String smtpPort = "";
    private String emailOverrideAddress = "";
    private String emailFromSystem = "";   
    private String accountApprovalMode = "";
    private String contactUsEmail = "";

    public void setTemplate(String template) {
        this.template = template;
    }
    public String getTemplate() {
        return template;
    }

    private String template = " ";
    
    private List mailRecipients;

    public static void loadConfig(Properties props)
            throws IOException {
        theConfig = new Config();
       
        theConfig.setBaseUrl((String) props.get(Constants.PROPERTY_BASE_URL));
        theConfig.setSmtpHost(props.getProperty(Constants.PROPERTY_SMTP_HOST));
        theConfig.setSmtpPort(props.getProperty(Constants.PROPERTY_SMTP_PORT));
        theConfig.setEmailOverrideAddress(props.getProperty(Constants.PROPERTY_EMAIL_OVERRIDE_ADDRESS));
        theConfig.setEmailFromSystem(props.getProperty(Constants.PROPERTY_EMAIL_FROM_SYSTEM));        
        theConfig.setTemplate(props.getProperty(Constants.REJECT_REASON_TEMPLATE));
        theConfig.setAccountApprovalMode(props.getProperty(Constants.PROPERTY_ACCOUNT_APPROVAL_MODE));
        theConfig.setContactUsEmail(props.getProperty(Constants.PROPERTY_CONTACT_US_EMAIL));
    
    }

    public static Config getConfig() {
        return theConfig;
    }

    

    public String getAccountApprovalMode() {
        if(accountApprovalMode!=null){
        return accountApprovalMode;
        }
        return "";
    }

    public void setAccountApprovalMode(String accountApprovalMode) {
        this.accountApprovalMode = accountApprovalMode;
    }

    

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

     

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getEmailOverrideAddress() {
        return emailOverrideAddress;
    }

    public void setEmailOverrideAddress(String emailOverrideAddress) {
        this.emailOverrideAddress = emailOverrideAddress;
    }

    public String getEmailFromSystem() {
        return emailFromSystem;
    }

    public void setEmailFromSystem(String emailFromSystem) {
        this.emailFromSystem = emailFromSystem;
    }
    
    public String getContactUsEmail() {
        return contactUsEmail;
    }

    public void setContactUsEmail(String contactUsEmail) {
        this.contactUsEmail = contactUsEmail;
    }
    

}

     
     
