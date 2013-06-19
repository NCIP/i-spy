/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.util.PropertyLoader;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.imaging.ImagingDataServiceFactory;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
/**
 * @todo comment this!
 * @author BhattarR, BauerD
 *
 */




public class ApplicationContext{
	private static Map mappings = new HashMap();
	private static Logger logger = Logger.getLogger(ApplicationContext.class);
	private static Properties labelProps = null;
	//private static Properties messagingProps = null;
    private static Document doc =null;
   /**
    * COMMENT THIS
    * @return
    */
    public static Properties getLabelProperties() {
        return labelProps;
    }
    public static Map getDEtoBeanAttributeMappings() {
    	return mappings;
    }
//    public static Properties getJMSProperties(){
//    	return messagingProps;
//    }
    @SuppressWarnings("unused")
	public static void init() {
    	 logger.debug("Loading Application Resources");
         labelProps = PropertyLoader.loadProperties(ispyConstants.APPLICATION_RESOURCES);
         String appPropertiesFileName = null;
         //messagingProps = PropertyLoader.loadProperties(ispyConstants.JMS_PROPERTIES);
//         try {
//	          logger.debug("Bean to Attribute Mappings");
//	          InputStream inStream = QueryHandler.class.getResourceAsStream(RembrandtConstants.DE_BEAN_FILE_NAME);
//	          assert true:inStream != null;
//	          DOMParser p = new DOMParser();
//	          p.parse(new InputSource(inStream));
//	          doc = p.getDocument();
//	          assert(doc != null);
//	          logger.debug("Begining DomainElement to Bean Mapping");
//	          mappings = new DEBeanMappingsHandler().populate(doc);
//	          logger.debug("DomainElement to Bean Mapping is completed");
//	          QueryHandler.init();
//	      } catch(Throwable t) {
//	         logger.error(new IllegalStateException("Error parsing deToBeanAttrMappings.xml file: Exception: " + t));
//	      }
     
        try {
        	
           //Load the the application properties and set them as system properties
 		   Properties ispyPortalProperties = new Properties();
 		   appPropertiesFileName = System.getProperty("gov.nih.nci.ispyportal.propertiesFile");
 		   
 		  
 		   logger.info("Attempting to load application system properties from file: " + appPropertiesFileName);
 		   
 		   FileInputStream in = new FileInputStream(appPropertiesFileName);
 		   ispyPortalProperties.load(in);
 		   
 		   if (ispyPortalProperties.isEmpty()) {
 		     logger.error("Error: no properties found when loading properties file: " + appPropertiesFileName);
 		   }
 		    		   
 		   String key = null;
 		   String val = null;
 		   for (Iterator i = ispyPortalProperties.keySet().iterator(); i.hasNext(); ) {
 			  key = (String) i.next();
 			  val = ispyPortalProperties.getProperty(key);
 		      System.setProperty(key, val);
 		   }
        	
           //initialize the ClinicalFileBasedQueryService
           
 		   
 		   
 		   //ClinicalDataService cqs = ClinicalFileBasedQueryService.getInstance();
           
             ClinicalDataService cqs = ClinicalDataServiceFactory.getInstance();
           
           //String clinicalDataFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ispy_clinical_data_14MARCH06.txt";
           //logger.info("Initializing file based clinical data service fileName=" + clinicalDataFileName);
           //int clinRecordsLoaded  = cqs.setClinicalDataFile(clinicalDataFileName);
           //logger.info("Clinical data service initialized successfully loaded numRecords=" + clinRecordsLoaded);
        

           //String patientDataFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ispy_patient_data_5_11.txt";
           String patientDataFileName = System.getProperty("gov.nih.nci.ispyportal.data_directory") + System.getProperty("gov.nih.nci.ispyportal.patient_data");
           logger.info("Clinical data service loading patient data fileName=" + patientDataFileName);
           
           
           //TODO put back if using file based
           //int patientRecordsLoaded = cqs.setPatientDataMap(patientDataFileName);
           //logger.info("Clinical data service successfully loaded patient data numRecords=" + patientRecordsLoaded);
           
           
           IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
           //String idMapperFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ID_Mapping_5-4-06.txt";
           //logger.info("Initializing file based id mapper service fileName=" + idMapperFileName);
           
           logger.info("Trying to get system property for mapper file: fileName=" + System.getProperty("gov.nih.nci.ispyportal.id_mapping_file"));
           String idMapperFileName = System.getProperty("gov.nih.nci.ispyportal.data_directory") + System.getProperty("gov.nih.nci.ispyportal.id_mapping_file");
           logger.info("Initializing file based id mapper service fileName=" + idMapperFileName);
           int idRecLoaded = idMapper.setMappingFile(idMapperFileName);
           logger.info("Id mapper service initialized successfully loaded numRecords=" + idRecLoaded);
           
           @SuppressWarnings("unused") AnalysisServerClientManager analysisServerClientManager = AnalysisServerClientManager.getInstance();
		   analysisServerClientManager.setCache(ApplicationFactory.getBusinessTierCache());
		   //analysisServerClientManager.setMessagingProperties(messagingProps);
		   
		   //create the file based annotation service
		   
		   
		   GeneExprAnnotationService gxAnnotService = GeneExprAnnotationServiceFactory.getInstance();
		   //String annotFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ispy_gene_annotations.txt";
		   //String annotFileName = ISPYContextListener.getDataFilesDirectoryPath() + File.separatorChar + "ispy_gx_annotations_5-19-06.txt";
		   
		   ImagingDataServiceFactory.getImagingDataService();  //will initialize the service
		   
		   logger.info("ImagingDataService initialized.");
		   
		   analysisServerClientManager.setGeneExprAnnotationService(gxAnnotService);
		   
		   
		   	   		  
		   String jmsProviderURL = System.getProperty("gov.nih.nci.ispyportal.jms.jboss_url");
		   String jndiFactoryName = System.getProperty("gov.nih.nci.ispyportal.jms.factory_jndi");
		   String requestQueueName = System.getProperty("gov.nih.nci.ispyportal.jms.analysis_request_queue");
		   String responseQueueName = System.getProperty("gov.nih.nci.ispyportal.jms.analysis_response_queue");
		   
		   analysisServerClientManager.setJMSparameters(jmsProviderURL, jndiFactoryName, requestQueueName, responseQueueName);
		   
		   analysisServerClientManager.establishQueueConnection();
		   
		} catch (NamingException e) {
	        logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(e.getMessage());
			logger.error(e);
		} catch (JMSException e) {
	        logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(e.getMessage());
			logger.error(e);
		} catch (IOException e) {
		    logger.error("IOError loading properties file: " + appPropertiesFileName);
		    logger.error(e);
		}  catch(Throwable t) {
			//logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(t.getMessage());
			logger.error(t);
		}

    }
}
