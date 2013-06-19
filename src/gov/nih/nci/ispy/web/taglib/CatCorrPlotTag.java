package gov.nih.nci.ispy.web.taglib;

import gov.nih.nci.caintegrator.analysis.messaging.DataPointVector;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterInfo;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.ui.graphing.util.ImageMapUtil;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.findings.ISPYCategoricalCorrelationFinding;
import gov.nih.nci.ispy.ui.graphing.chart.plot.ColorByType;
import gov.nih.nci.ispy.ui.graphing.chart.plot.ISPYCategoricalCorrelationPlot;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ISPYImageFileHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;

/**
 * this class generates a PCAPlot tag which will take a taskId, the components
 * with which to compare, and possibly a colorBy attribute which colors the
 * samples either by Disease or Gender. Disease is colored by default.
 * @author rossok
 *
 */





/**
 * This class is used to draw graphics for the Correlation Scatter plot
 */
public class CatCorrPlotTag extends AbstractGraphingTag {

	private String beanName = "";
	private String taskId = "";
    private String colorBy = "";
    private String components ="";
        
	private List<JFreeChart> jFreeChartsList;
    private JFreeChart chart = null;
    private static Logger logger = Logger.getLogger(CatCorrPlotTag.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = ApplicationFactory.getBusinessTierCache();
    
	public int doStartTag() {
		

		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter out = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		
		
		ISPYCategoricalCorrelationFinding corrFinding = (ISPYCategoricalCorrelationFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
        
		
	    try {
            List<DataPointVector> dataSet = corrFinding.getDataVectors();
            List<ReporterInfo> reporterInfoList = corrFinding.getCatCorrRequest().getReporters();
            
            
            //get better labels for X and Y axis.
            ContinuousType ct = corrFinding.getContType();
            String xLabel, yLabel;
            if (ct == ContinuousType.GENE) {
               yLabel = "Log base 2 expression value";
            }
            else {
               yLabel = ct.toString();
            }
            
            
            
            //if there are reporters involved then send them in so that they can be used to create
            //a series.
            
            ISPYCategoricalCorrelationPlot plot = new ISPYCategoricalCorrelationPlot(dataSet, reporterInfoList,"Category",yLabel,corrFinding.getContType(),ColorByType.CLINICALRESPONSE);
            
            chart = plot.getChart();
            ISPYImageFileHandler imageHandler = new ISPYImageFileHandler(session.getId(),"png",650,600);
			//The final complete path to be used by the webapplication
			String finalPath = imageHandler.getSessionTempFolder();
            String finalURLpath = imageHandler.getFinalURLPath();
			/*
			 * Create the actual charts, writing it to the session temp folder
			*/ 
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            String mapName = imageHandler.createUniqueMapName();
            //PrintWriter writer = new PrintWriter(new FileWriter(mapName));
			ChartUtilities.writeChartAsPNG(new FileOutputStream(finalPath),chart, 650,600,info);
            //ImageMapUtil.writeBoundingRectImageMap(writer,"PCAimageMap",info,true);
            //writer.close();
			
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
            
            out.print(ImageMapUtil.getBoundingRectImageMapTag(mapName,true,info));
            finalURLpath = finalURLpath.replace("\\", "/");
            long randomness = System.currentTimeMillis(); //prevent image caching
		    out.print("<img id=\"geneChart\" name=\"geneChart\" src=\""+finalURLpath+"?"+randomness+"\" usemap=\"#"+mapName + "\" border=\"0\" />");
            
            
            //(imageHandler.getImageTag(mapFileName));
        
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
