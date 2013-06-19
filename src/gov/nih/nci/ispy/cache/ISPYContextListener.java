package gov.nih.nci.ispy.cache;

import gov.nih.nci.caintegrator.application.cache.SessionTracker;
import gov.nih.nci.ispy.util.ApplicationContext;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * This listener has one purpose and that is to notify the
 * SessionTracker class when the application context has been 
 * loaded or unloaded. Check the application Web.xml for the
 * intialization of this listener.
 * 
 * @author BauerD
 * Feb 17, 2005
 * 
 */

       


public class ISPYContextListener implements ServletContextListener {
	private static String contextPath;
	private static String dataFilesDirectoryPath;
    private static String SEPERATOR = File.separator;
	/** 
	 * this method is fired whenever the application server loads the context
	 * that this listener is added to in the web.xml
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext();
		contextPath = context.getRealPath(SEPERATOR);
		dataFilesDirectoryPath = context.getRealPath(SEPERATOR+"WEB-INF" + SEPERATOR + "data_files" + SEPERATOR);
		SessionTracker.setAppplicationRunning(true);
		ApplicationContext.init();
		
	}

	/**
	 * this method is fired whenever the application server unloads 
	 * the context that this listener is added to in the web.xml
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		SessionTracker.setAppplicationRunning(false);

	}
	
	public static String getContextPath() {
		return contextPath;
	}

	public static String getDataFilesDirectoryPath() { 
	  return dataFilesDirectoryPath;
	}
}
