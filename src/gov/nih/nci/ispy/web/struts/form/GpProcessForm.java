/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.struts.form;


import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

/**
* 
* 
*/

public class GpProcessForm extends ActionForm {
    
 // -------------INSTANCE VARIABLES-----------------------------//
    private static Logger logger = Logger.getLogger(GpProcessForm.class);
    
    private String jobId = "";
    
    private List jobList = null;
    private List processList = null;
    private String processName = "";
    
    private String fileName = "";

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public List getJobList() {
		return jobList;
	}

	public void setJobList(List jobList) {
		this.jobList = jobList;
	}

	public List getProcessList() {
		return processList;
	}

	public void setProcessList(List processList) {
		this.processList = processList;
	}   


}
