package gov.nih.nci.ispy.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import gov.nih.nci.caintegrator.security.PublicUserPool;
import gov.nih.nci.caintegrator.security.EncryptionUtil;
import gov.nih.nci.ispy.util.ISPYPublicUserPool;



public class ISPYGenePatternHome {
	public static String gpPoolString = ":GP30:RBT";
	private static Logger logger = Logger.getLogger(ISPYGenePatternHome.class);
	public static String gpHomeURL(HttpServletRequest request)
		throws Exception {
    	HttpSession session = request.getSession();
    	String ticketString = null;
		String gpserverURL = System.getProperty("gov.nih.nci.ispyportal.gp.server")!=null ? 
				(String)System.getProperty("gov.nih.nci.ispyportal.gp.server") : "localhost:8080"; //default to localhost
		try {
		//*	
			String ispyUser = (String)session.getAttribute("name");
			String publicUser = System.getProperty("gov.nih.nci.ispyportal.gp.publicuser.name");
			//String password = System.getProperty("gov.nih.nci.ispyportal.gp.publicuser.password");
			
			if (ispyUser.equals(publicUser)){
				String gpUser = (String)session.getAttribute(ISPYPublicUserPool.PUBLIC_USER_NAME);
				if (gpUser == null){
					PublicUserPool pool = ISPYPublicUserPool.getInstance();
					gpUser = pool.borrowPublicUser();
					session.setAttribute(ISPYPublicUserPool.PUBLIC_USER_NAME, gpUser);
					session.setAttribute(ISPYPublicUserPool.PUBLIC_USER_POOL, pool);
				}
				ispyUser = gpUser;
			}
			String encryptKey = System.getProperty("gov.nih.nci.ispyportal.gp.desencrypter.key");
			String urlString = EncryptionUtil.encrypt(ispyUser+ gpPoolString, encryptKey);
			urlString = URLEncoder.encode(urlString, "UTF-8");
			ticketString = gpserverURL+"gp?ticket="+ urlString;
			
			logger.debug(ticketString);
			URL url;
            try {
            	url = new java.net.URL(ticketString);
            	URLConnection conn = url.openConnection();
            	final int size = conn.getContentLength();
            	logger.debug(Integer.toString(size));

            } catch (Exception e) {
            	logger.error(e.getMessage());
            }
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
			throw new Exception(e.getMessage());
		}
		//ticketString
		System.out.println("ticketString is..............." + ticketString);
		
        return ticketString;
    }

}
