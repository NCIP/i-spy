package gov.columbia.c2b2.ispy.util;

 
 
import gov.nih.nci.ispy.util.ispyConstants;
  
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.File;
import java.io.FileInputStream;
 
 
import java.util.Properties;

 
 

/**
 * Initializes and destroys the web application context.
 */
public class AdminContextListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
     
	private static String SEPERATOR = File.separator;
    /**
     * Initializes the web application, including the Hibernate session factory.
     */
    public void contextInitialized(ServletContextEvent event) {
        try {
        	 logger.info("Initializing admin app...");

             ServletContext context = event.getServletContext();
          
              //// Initialize Config
              FileInputStream fsi = null;

              Properties props = new Properties();
                     
             
                fsi = new FileInputStream(context.getRealPath("WEB-INF")+ SEPERATOR + "users.properties"); 
                logger.debug("loading props...");
                props.load(fsi);
                logger.debug("props file length: " + props.size()); 
                fsi.close();
                
             
                 Config.loadConfig(props);
            
            
             
        } catch (Exception e) {
            System.out.println("Could not initialize admin app: " + e);
            e.printStackTrace();
        }

    }

    /**
     * Performs shutdown tasks, i.e. stop all background threads.
     */
    public void contextDestroyed(ServletContextEvent event) {
    }
}
