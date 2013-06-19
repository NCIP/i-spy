/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.taglib;

import gov.nih.nci.caintegrator.analysis.messaging.PCAresultEntry;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.enumeration.ClinicalFactorType;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint.PCAcomponent;
import gov.nih.nci.caintegrator.ui.graphing.util.ImageMapUtil;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.ui.graphing.chart.plot.ColorByType;
import gov.nih.nci.ispy.ui.graphing.chart.plot.ISPYPrincipalComponentAnalysisPlot;
import gov.nih.nci.ispy.ui.graphing.data.principalComponentAnalysis.ISPYPCADataPoint;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.helper.ISPYImageFileHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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




public class PCAPlotTag extends AbstractGraphingTag {

	private String beanName = "";
	private String taskId = "";
    private String colorBy = "";
    private String components ="";
    private List<PCAresultEntry> pcaResults = null;
    private Collection<PrincipalComponentAnalysisDataPoint> pcaData = new ArrayList();
	private List<JFreeChart> jFreeChartsList;
    private JFreeChart chart = null;
    private static Logger logger = Logger.getLogger(PCAPlotTag.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = ApplicationFactory.getBusinessTierCache();
    
	public int doStartTag() {
		chart = null;
		pcaResults = null;
		pcaData.clear();

		
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter out = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		
		try {
            //retrieve the Finding from cache and build the list of PCAData points
            PrincipalComponentAnalysisFinding principalComponentAnalysisFinding = (PrincipalComponentAnalysisFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
            
            Collection<ClinicalFactorType> clinicalFactors = new ArrayList<ClinicalFactorType>();
            List<String> sampleIds = new ArrayList<String>();
            Map<String,PCAresultEntry> pcaResultMap = new HashMap<String, PCAresultEntry>();
            
            pcaResults = principalComponentAnalysisFinding.getResultEntries();
            for (PCAresultEntry pcaEntry : pcaResults) {
                sampleIds.add(pcaEntry.getSampleId());
                pcaResultMap.put(pcaEntry.getSampleId(), pcaEntry);
            }
            
            
            //Get the clinical data for the sampleIds
        	ClinicalDataService cqs = ClinicalDataServiceFactory.getInstance();
        	IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
            //Map<String, ClinicalData> clinicalDataMap = cqs.getClinicalDataMapForLabtrackIds(sampleIds);              
            
            PCAresultEntry entry;
            //ClinicalData clinData;
            //PatientData patientData;
            SampleInfo si;
            TimepointType timepoint;
            for (String id: sampleIds){
                
                entry  = pcaResultMap.get(id); 
                ISPYPCADataPoint pcaPoint = new ISPYPCADataPoint(id,entry.getPc1(),entry.getPc2(),entry.getPc3());
            
                si = idMapper.getSampleInfoForLabtrackId(id);
                
                //clinData = cqs.getClinicalDataForPatientDID(si.getRegistrantId(), si.getTimepoint());
                //patientData = cqs.getPatientDataForPatientDID(si.getISPYId());
                Set<String> ispyIds = new HashSet<String>();
                ispyIds.add(si.getISPYId());
                ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
                dto.setRestrainingSamples(ispyIds);
                Set<PatientData> pdSet = cqs.getClinicalData(dto);
                for(PatientData patientData: pdSet){
                    pcaPoint.setISPY_ID(si.getISPYId());
                    timepoint = si.getTimepoint();
                    
                    pcaPoint.setTimepoint(timepoint);
                    
                    if (patientData != null) {
    	                pcaPoint.setClinicalStage(patientData.getClinicalStage());
    	                
    	                int clinRespVal;
    	                Double mriPctChange = null;
    	                if (timepoint == TimepointType.T1) {
    	                  pcaPoint.setClinicalResponse(ClinicalResponseType.NA);
    	                  pcaPoint.setTumorMRIpctChange(null);
    	                }
    	                else if (timepoint == TimepointType.T2) {
    	                  clinRespVal = PatientData.parseValue(patientData.getClinRespT1_T2());
    	                  //set the clinical respoinse to the T1_T2
    	                  pcaPoint.setClinicalResponse(ClinicalResponseType.getTypeForValue(clinRespVal));
    	                  pcaPoint.setTumorMRIpctChange(patientData.getMriPctChangeT1_T2());
    	                }
    	                else if (timepoint == TimepointType.T3) {
    	                  //set the clinical response to T1_T3
    	                  clinRespVal = PatientData.parseValue(patientData.getClinRespT1_T3());
    	                  pcaPoint.setClinicalResponse(ClinicalResponseType.getTypeForValue(clinRespVal));
    	                  pcaPoint.setTumorMRIpctChange(patientData.getMriPctChangeT1_T3());
    	                }
    	                else if (timepoint == TimepointType.T4) {
    	                  //set the clinical response to T1_T4
    	                  clinRespVal = PatientData.parseValue(patientData.getClinRespT1_T4());
    	                  pcaPoint.setClinicalResponse(ClinicalResponseType.getTypeForValue(clinRespVal));
    	                  pcaPoint.setTumorMRIpctChange(patientData.getMriPctChangeT1_T4());
    	                }
    	                else {
    	                  pcaPoint.setClinicalResponse(ClinicalResponseType.UNKNOWN);
    	                  pcaPoint.setTumorMRIpctChange(null);
    	                }
                    }
                       
                    pcaData.add(pcaPoint);
                }
            }
          
            
            //check the components to see which graph to get
			if(components.equalsIgnoreCase("PC1vsPC2")){                 
                ISPYPrincipalComponentAnalysisPlot plot = new ISPYPrincipalComponentAnalysisPlot(pcaData, PCAcomponent.PC2, PCAcomponent.PC1, ColorByType.valueOf(ColorByType.class,colorBy.toUpperCase()));
            	chart = plot.getChart();                
            }
            if(components.equalsIgnoreCase("PC1vsPC3")){            	
                ISPYPrincipalComponentAnalysisPlot plot = new ISPYPrincipalComponentAnalysisPlot(pcaData, PCAcomponent.PC3, PCAcomponent.PC1, ColorByType.valueOf(ColorByType.class,colorBy.toUpperCase()));
            	chart = plot.getChart();                
            }
            if(components.equalsIgnoreCase("PC2vsPC3")){
            	ISPYPrincipalComponentAnalysisPlot plot = new ISPYPrincipalComponentAnalysisPlot(pcaData, PCAcomponent.PC3, PCAcomponent.PC2, ColorByType.valueOf(ColorByType.class,colorBy.toUpperCase()));
            	chart = plot.getChart();
            }
            
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
            
            out.print(ImageMapUtil.getBoundingRectImageMapTag(mapName,false,info));
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
