/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.fileLoad;


import java.sql.Timestamp;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

public class LoadLog {


    
    private Long recId;
    public Long getRecId(){
        return (this.recId);
    }
    public void setRecId(Long recId){
        this.recId=recId;
    }
    
    private Long usrId;
    public Long getUsrId(){
        return (this.usrId);
    }
    public void setUsrId(Long usrId){
        this.usrId=usrId;
    }
    
    private String fileName;    
    public String getFileName(){
        return (this.fileName);
    }
    public void setFileName(String fileName){
        this.fileName=fileName;
    }
    
    private String validScriptName;    
    public String getValidScriptName(){
        return (this.validScriptName);
    }
    public void setValidScriptName(String validScriptName){
        this.validScriptName=validScriptName;
    }
    
    private String procOutFileName;    
    public String getProcOutFileName(){
        return (this.procOutFileName);
    }
    public void setProcOutFileName(String procOutFileName){
        this.procOutFileName=procOutFileName;
    }
    
    private String uploadScriptName;    
    public String getUploadScriptName(){
        return (this.uploadScriptName);
    }
    public void setUploadScriptName(String uploadScriptName){
        this.uploadScriptName=uploadScriptName;
    }
    
    private String outFileName;    
    public String getoutFileName(){
        return (this.outFileName);
    }
    public void setoutFileName(String outFileName){
        this.outFileName=outFileName;
    }
     
    private char uploadStatus;   
    public char getUploadStatus(){
        return (this.uploadStatus);
    }
    public void setUploadStatus(char uploadStatus){
        this.uploadStatus=uploadStatus;
    }    
    
    private Long numRecs;
    public Long getNumRecs(){
        return (this.numRecs);
    }
    public void setNumRecs(Long numRecs){
        this.numRecs=numRecs;
    }
    
    private Timestamp updateDate;
    public Timestamp getUpdateDate(){
        return (this.updateDate);
    }
    public void setUpdateDate(Timestamp updateDate){
        this.updateDate=updateDate;
    }
/*    
    private String outContent;    
    public String getOutContent(){
        return (this.outContent);
    }
    public void setOutContent(String outContent){
        this.outContent=outContent;
    }
 */   
    private Set<LogFileContent> files = new HashSet<LogFileContent>();
    public Set<LogFileContent> getFiles(){
    	return this.files;
    }
    
    public void setFiles(Set<LogFileContent> files) {
        this.files=files;
    }
    
    
}
