package gov.nih.nci.ispy.util;

import org.apache.log4j.Logger;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;

/**
 * This class uses the loadLists method from list loader to read any text files
 * placed in the dat_files directory of the project. It also loads (as "userLists")
 * all the predefined clinical status groups found in the ISPY study(e.g ER+, ER-, etc)
 * @author rossok
 * @param 
 *
 */

public class ISPYPublicUserPool {
	private static Logger logger = Logger.getLogger(ISPYPublicUserPool.class);
	private static ISPYPublicUserPool instance = null;

    private ObjectPool pool;

    private ISPYPublicUserPool()
    {
        PoolableObjectFactory factory = new ISPYPublicUserFactory();
        String size = System.getProperty("gov.nih.nci.ispyportal.gp.publicuser.poolsize");
        pool = new StackObjectPool(factory, Integer.parseInt(size));
    }
    
    public static ISPYPublicUserPool getInstance(){
    	if (instance != null)
    		return instance;
    	else 
    		return new ISPYPublicUserPool();
    }
    public String borrowPublicUser(){
    	try{
    		return (String)pool.borrowObject();
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		return null;
    	}
    }
    public void returnPublicUser(String user){
    	try{
    		pool.returnObject(user);
    	}catch (Exception e){
    		logger.error(e.getMessage());
    	}
    }
}
