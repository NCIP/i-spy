package gov.nih.nci.ispy.util;





public final class ispyConstants {

    //Specifies the location in the webapp classes directory
    //to find ApplicationResources.properties
    public static final String  APPLICATION_RESOURCES = "ApplicationResources";
   
    public static final String CACHE_PROPERTIES = "rembrandtCache";

	public static final String LOGGER = "gov.nih.nci.ispy";

	public static final String JSP_LOGGER = "gov.nih.nci.ispy.jsp";

	public static final String DEFAULT_XSLT_FILENAME ="report.xsl";
	
	public static final String DEFAULT_XSLT_CSV_FILENAME ="csv.xsl";
	
	public static final String XSLT_FILE_NAME ="xsltFileName";
	
	public static final String FILTER_PARAM_MAP ="filterParamMap";
	
	public static final String JMS_PROPERTIES = "jms.properties";
    
    public static final String[] FOLD_CHANGE_DEFAULTS = {"2","3","4","5","6","7","8","9","10"};
	
	public static final String USER_PREFERENCES = "userPreferences";
    
    public static final String USER_LISTS = "userLists";
    
    public static final String USER_CREDENTIALS = "UserCredentials";
    
    public static final String ALL_USER_LISTS = "allUserLists.txt";
    
    public static final String[] FISH_BIOMARKERS = {"TOPO2","ERBB2"};
    
    public static final String[] CNA_STATUS = {"Amplified", "Deleted", "Unchanged"};
    
    public static final String[] MRI_TIMEPOINT_RANGE = {"T1_T2","T1_T3","T1_T4"};
   
    public static final String CONTINUOUS_GENE_STRING = "gov.nih.nci.ispy.service.clinical.ContinuousType#GENE";

    public static final String IHC_STAIN_INTENSITY =  "IHC_STAIN_INTENSITY";
    
    public static final String IHC_STAIN_LOCALIZATION =  "IHC_STAIN_LOCALIZATION";
    
    public static final String IHC_BIOMARKERS =  "IHC_BIOMARKERS";
    
    public static final String IHC_LOSS_RESULTCODES =  "IHC_LOSS_RESULTCODES";
    
    public static final String P53_MUTATION_STATUS = "P53_MUTATION_STATUS";
    
    public static final String P53_MUTATION_TYPE = "P53_MUTATION_TYPE";
    
    public static final String TIMEPOINTS =  "TIMEPOINTS";
    
    public static final String UI_LOOKUPS = "UI_LOOKUPS";
    
}
