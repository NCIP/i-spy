/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

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
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
