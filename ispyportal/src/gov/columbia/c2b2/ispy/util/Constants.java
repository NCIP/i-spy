package gov.columbia.c2b2.ispy.util;

import gov.columbia.c2b2.ispy.user.Status;

/**
 * System-wide constants.
 */
public class Constants {
	
	
	public static final String ACTION_REGISTER = "register";
    public static final String ACTION_SUBMIT_REGISTRATION = "submitRegistration";
    public static final String ACTION_SUBMIT_UPDATE = "submitUpdate";
    public static final String ACTION_RESETPASSWORD = "resetPassword";
    public static final String ACTION_SUBMIT_RESETPASSWORD = "submitResetPassword";
    public static final String ATTRIBUTE_USER_EMAIL = "userEmail";
    public static final String ATTRIBUTE_ORIGIN_EMAIL = "oldEmail";
    public static final String ATTRIBUTE_STATUS_HEADER = "statusHeader";
    public static final String ATTRIBUTE_STATUS_MESSAGE = "statusMESSAGE";
    public static final String ATTERIBUTE_LIST_PROPERTY = "listproperty";
    public static final String ATTRIBUTE_PENDING = "listproperty_pending";
    public static final String ATTRIBUTE_REGISTERED = "listproperty_registered";
    public static final String ATTRIBUTE_ALL = "listproperty_ALL";
    public static final String ATTRIBUTE_USERID_LIST = "userIDList";
    public static final String ATTRIBUTE_INFO = "information";
    public static final String ATTRIBUTE_ERROR = "errors";
     
    public static final String ACTION_LIST_USERS = "listUsers";
    public static final String ACTION_VIEW_USER = "viewUser";
    public static final String ACTION_EDIT_USER = "editUser";
    public static final String ACTION_DELETE_USER = "deactivateUser";
    public static final String ACTION_SAVE_USER = "saveUser";
    public static final String ACTION_SAVE_USER_CANCEL = "saveUserCancel";
    public static final String ACTION_APPROVE_USER = "approveUser";
    public static final String ACTION_DECLINE_USER = "declineUser";
    public static final String ACTION_BULK_DELETE_USER = "bulkdeactivateUser";
    public static final String ACTION_BULK_APPROVE_USER = "bulkapproveUser";
    public static final String ACTION_BULK_DECLINE_USER = "bulkdeclineUser";
    public static final String ACTION_SUBMIT_BULK_DECLINE_USER = "submitbulkdeclineUser";
    public static final String ACTION_SUBMIT_BULK_DELETE_USER = "submitbulkdeactivateUser";
    public static final String ACTION_VIEW_PENDING_USERS = "viewPendingUser";
    public static final String ACTION_BULK_REACTIVATE_USER = "reactivateBulkUser";
    public static final String ACTION_REACTIVATE_USER = "reactiveUser";
    public static final String ACTION_SUBMIT_CANCEL = "cancelSaveUser";    
    public static final String ATTRIBUTE_USER = "theUser";
    public static final String ATTRIBUTE_USER_LIST = "userList";
    public static final String ATTRIBUTE_DENY_REASON = "denyReason";
    public static final String ATTRIBUTE_DEACTIVATVED_USER_LIST = "deactivatedUsers";
    
    public static final String PARAM_USER_ID = "userID";
    
    
    public static final String JSP_USER_LIST = "userList";
    public static final String JSP_USER_VIEW = "userView";
    public static final String JSP_USER_EDIT = "userEdit";
    public static final String JSP_USER_MODIFIED = "userMod";
    public static final String JSP_USER_MODIFIEDEXP = "userModExplanation";
    public static final String JSP_ACTION_STATUS = "actionStatus";
    

    // Object is active
    public static final char STATUS_ACTIVE = 'A';
    // Object is deleted
    public static final char STATUS_DEACTIVATED = 'D';
    // User-only: User is pending approval from admin
    public static final char STATUS_PENDING = 'P';
    // User-only: User is rejected.
    public static final char STATUS_REJECT = 'R';
    // User-only: User is pending confirmation that email address is valid
    public static final char STATUS_CONFIRM = 'C';

    public static final String getStatus(Status status) {
        return getStatus(status.getStatus());
    }

    public static final String getStatus(char status) {
        switch (status) {
            case STATUS_DEACTIVATED:
                return "Deactivated";
            case STATUS_PENDING:
                return "Pending";
            case STATUS_CONFIRM:
                return "Unconfirmed";
            case STATUS_REJECT:
                return "Rejected";
            default:
                return "Active";
        }
    }

    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public static final String[] GENDERS = {"Unknown", "Male", "Female"};

    public static final String INTRAGEN_PROPERTIES = "intragen.properties";
    public static final String APPLICATION_PROPERTIES = "application.properties";

    public static final String PROPERTY_FILE_DOWNLOAD_LOCATION = "file.download.location";
    public static final String PROPERTY_FILE_DATA_LOCATION = "file.data.location";
    public static final String PROPERTY_BASE_URL = "base.url";
    public static final String PROPERTY_SECURE_URL = "secure.url";
    public static final String PROPERTY_SMTP_HOST = "email.smtp.host";
    public static final String PROPERTY_SMTP_PORT = "email.smtp.port";
    public static final String PROPERTY_EMAIL_OVERRIDE_ADDRESS = "email.override";
    public static final String PROPERTY_EMAIL_FROM_SYSTEM = "email.from.system";
    public static final String PROPERTY_CONTACT_US_EMAIL = "contact.us.email";
    public static final String PROPERTY_ACCOUNT_APPROVAL_MODE = "account.approval.mode";
    public static final String REJECT_REASON_TEMPLATE = "reject.reason.template";

    public static final String APPROVAL_MODE_EDU = "edu";
    public static final String APPROVAL_MODE_ADMIN = "admin";

    // todo - put contact email in properties
    public static String GENERAL_ERROR = "We apologize for the inconvencience but an unknown error has occured.  Please contact <a href=mailto:EMAIL@EMAILADDRESS.QQ>EMAIL@EMAILADDRESS.QQ</a> for assistance.";
    public static String ACCESS_ERROR = "You do not have access to this page. Please contact <a href=mailto:EMAIL@EMAILADDRESS.QQ>EMAIL@EMAILADDRESS.QQ</a> for assistance.";

    /**
     * The regular user role (1L)
     */
    public static final long ROLE_USER = 1L;

    /**
     * The admin user role (2L)
     */
    public static final long ROLE_ADMIN = 2L;


    public static final String[] ROLE_NAMES =
            {
                    "(Invalid)",
                    "Registered User",
                    "Administrator",
            };

    public static final String MIME_TYPE_PDF = "application/pdf";

    public static final String SESSION_ID_COOKIE = "sessionCookie";

    // Two week cookie time-out
    public static final int SESSION_ID_COOKIE_MAXAGE = 30 * 60;

    public static String SNP_INFO_FILE_NAME = "SNPInfo.txt";

    public static final String CSM_GROUP_NAME = "CSM_GROUP_NAME";

}