package gov.nih.nci.ispy.web.taglib;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.ispy.web.helper.ISPYImageFileHandler;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

/**
 * this class generates a PCAPlot tag which will take a taskId, the components
 * with which to compare, and possibly a colorBy attribute which colors the
 * samples either by Disease or Gender. Disease is colored by default.
 * @author rossok
 *
 */




public class HCPlotTag extends AbstractGraphingTag {

	private String beanName = "";
	private String taskId = "";
    private String colorBy = "";
    private String components ="";


	
    private static Logger logger = Logger.getLogger(HCPlotTag.class);
	private PresentationTierCache presentationTierCache = CacheFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = CacheFactory.getBusinessTierCache();
    
	public int doStartTag() {
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter jspOut = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		
		try {
            //retrieve the Finding from cache and write the image to a file
            HCAFinding hcaFinding = (HCAFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
            byte[] imageCode = hcaFinding.getImageCode();
            
            //int h = Toolkit.getDefaultToolkit().createImage(imageCode).getHeight(null);
            
            //pass imageHeight=-1 and imageWidth=-1 because we don't know the size of the image
            ISPYImageFileHandler imageHandler = new ISPYImageFileHandler(session.getId(),"png",-1,-1);
			//The final complete path to be used by the webapplication
			String finalPath = imageHandler.getSessionTempFolder();
            String finalURLpath = imageHandler.getFinalURLPath();
            String uniqueFileName = imageHandler.createUniqueChartName(".png");
            
         
  		    try {
  			  OutputStream out  = new FileOutputStream(finalPath);		  
  			  out.write(imageCode, 0, imageCode.length); 			
  			  out.close();
  		    }
  		    catch (IOException ex) {
  		      logger.debug("Error generating HC image imageName=" + uniqueFileName);
  		    }
		
			/*	This is here to put the thread into a loop while it waits for the
			 *	image to be available.  It has an unsophisticated timer but at 
			 *	least it is something to avoid an endless loop.
			 **/ 
            boolean imageReady = false;
            int timeout = 1000;
            FileInputStream inputStream = null;
            while(!imageReady) {
                timeout--;
                try {
                    inputStream = new FileInputStream(finalPath);
                    inputStream.available();
                    imageReady = true;
                    inputStream.close();
                }catch(IOException ioe) {
                    imageReady = false;  
                    if(inputStream != null){
                    	inputStream.close();
                    }
                }
                if(timeout <= 1) {
                    
                    break;
                }
             }
            
            int w =Toolkit.getDefaultToolkit().getImage(finalPath).getWidth(null);
            
            
		    jspOut.print(imageHandler.getImageTag());
        
		}catch (IOException e) {
			logger.error(e);
		}catch(Exception e) {
			logger.error(e);
		}catch(Throwable t) {
			logger.error(t);
		}
	
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		return doAfterEndTag(EVAL_PAGE);
	}
	public void reset() {
		//chartDefinition = createChartDefinition();
	}
	/**
	 * @return Returns the bean.
	 */
	public String getBean() {
		return beanName;
	}
	/**
	 * @param bean The bean to set.
	 */
	public void setBean(String bean) {
		this.beanName = bean;
	}

    /**
     * @return Returns the taskId.
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId The taskId to set.
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return Returns the colorBy.
     */
    public String getColorBy() {
        return colorBy;
    }

    /**
     * @param colorBy The colorBy to set.
     */
    public void setColorBy(String colorBy) {
        this.colorBy = colorBy;
    }

    /**
     * @return Returns the components.
     */
    public String getComponents() {
        return components;
    }

    /**
     * @param components The components to set.
     */
    public void setComponents(String components) {
        this.components = components;
    }
	
}
