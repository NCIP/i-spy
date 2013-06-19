/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.fileLoad;
import java.sql.Timestamp;
import java.sql.Blob;

public class LogFileContent {
	
    private Long recId;
    public Long getRecId(){
        return (this.recId);
    }
    public void setRecId(Long recId){
        this.recId=recId;
    }
    
    private Long logId;
    public Long getLogId(){
        return (this.logId);
    }
    public void setLogId(Long logId){
        this.logId=logId;
    }

    private String fileName;    
    public String getFileName(){
        return (this.fileName);
    }
    public void setFileName(String fileName){
        this.fileName=fileName;
    }
    
    private String fileContent;    
    public String getFileContent(){
        return (this.fileContent);
    }
    public void setFileContent(String fileContent){
        this.fileContent=fileContent;
    }
	
	
}
