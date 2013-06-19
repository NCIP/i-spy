package gov.nih.nci.ispy.web.taglib;

import gov.nih.nci.caintegrator.analysis.messaging.DataPoint;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.enumeration.ClinicalFactorType;
import gov.nih.nci.caintegrator.ui.graphing.util.ImageMapUtil;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.findings.ISPYCorrelationFinding;
import gov.nih.nci.ispy.ui.graphing.chart.plot.ColorByType;
import gov.nih.nci.ispy.ui.graphing.chart.plot.ISPYCorrelationScatterPlot;
import gov.nih.nci.ispy.ui.graphing.data.ISPYPlotPoint;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ISPYImageFileHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
public class CorrScatterPlotTag extends AbstractGraphingTag {

	private String beanName = "";
	private String taskId = "";
    private String colorBy = "";
    private String components ="";
    
    private Collection<ISPYPlotPoint> plotPoints = new ArrayList();
	private List<JFreeChart> jFreeChartsList;
    private JFreeChart chart = null;
    private static Logger logger = Logger.getLogger(CorrScatterPlotTag.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = ApplicationFactory.getBusinessTierCache();
    
	public int doStartTag() {
		chart = null;
		plotPoints.clear();

		
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter out = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		
		try {
            //retrieve the Finding from cache and build the list of PCAData points
            ISPYCorrelationFinding corrFinding = (ISPYCorrelationFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
            
            Collection<ClinicalFactorType> clinicalFactors = new ArrayList<ClinicalFactorType>();
            List<String> sampleIds = new ArrayList<String>();
            
            
            List<DataPoint> points = corrFinding.getDataPoints();
            
            ClinicalDataService cqs = ClinicalDataServiceFactory.getInstance();
        	IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
            
            List<ISPYPlotPoint> plotPoints = new ArrayList<ISPYPlotPoint>();
            ISPYPlotPoint pp;
            SampleInfo si;
            ISPYclinicalDataQueryDTO dto;
            Set<String> sampleHolder = new HashSet<String>(); //set just holds one entry need this for the dto
            Set<PatientData> dataHolder = new HashSet<PatientData>();
            PatientData pd = null;
            for (DataPoint p : points) {
               pp = new ISPYPlotPoint(p.getId());
               pp.setX(p.getX());
               pp.setY(p.getY());
               pp.setZ(p.getZ());
               
               String patientId = null;
               
               if (corrFinding.isSampleBased()) {
                 si = idMapper.getSampleInfoForLabtrackId(p.getId());       
                 if (si != null) {
	               pp.setSampleInfo(si);
	               patientId = si.getISPYId();
                 }
                 else {
            	   logger.warn("Could not get sample info for DataPoint=" + p.getId());
                 }
               }
               else if (corrFinding.isPatientBased()) {
                 patientId = p.getId();
               }
                 
               if (patientId != null) {  
	               dto = new ISPYclinicalDataQueryDTO();
	               sampleHolder.clear();
	               sampleHolder.add(patientId);
	               dto.setRestrainingSamples(sampleHolder);
	               dataHolder.clear();
	               dataHolder = cqs.getClinicalData(dto);
	               
	               if (dataHolder.size() == 1) {
	                 Iterator i = dataHolder.iterator();
	                 pd = (PatientData) i.next();
	                 pp.setPatientData(pd);
	               }
	               else {
	                 logger.error("Internal Error. Did not get back correct patient data for  patientId=" + patientId);
	               }
               }
               
               plotPoints.add(pp);
            }
            
            
            ISPYCorrelationScatterPlot plot = new ISPYCorrelationScatterPlot(plotPoints, corrFinding.getGroup1Name(), corrFinding.getGroup2Name(), corrFinding.getContinuousType1(), corrFinding.getContinuousType2(), corrFinding.getCorrelationValue(),ColorByType.valueOf(ColorByType.class,colorBy.toUpperCase()));
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
			 StringWriter sw = new StringWriter();
		     PrintWriter pw  = new PrintWriter(sw);
		     e.printStackTrace(pw);
		    logger.error(sw.toString());
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
