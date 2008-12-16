package gov.nih.nci.ispy.web.reports.servlets;

import gov.nih.nci.caintegrator.application.bean.FindingReportBean;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.report.LevelOfExpressionIHCReport;
import gov.nih.nci.caintegrator.application.report.LossOfExpressionIHCReport;
import gov.nih.nci.caintegrator.application.report.P53Report;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.service.findings.ISPYIHCLevelOfExpressionFinding;
import gov.nih.nci.ispy.service.findings.ISPYIHCLossOfExpressionFinding;
import gov.nih.nci.ispy.service.findings.P53Finding;
import gov.nih.nci.ispy.web.reports.quick.QuickClinicalReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelDownloadServlet extends HttpServlet {

    /**
     * Constructor of the object.
     */
    public ExcelDownloadServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // response.setContentType("text/html");
        // PrintWriter out = response.getWriter();
        String key = request.getParameter("key");        
        String filePath = "";
        File file = null;
        OutputStream os = null;
        FileInputStream fin = null;
        String f = null;
        HSSFWorkbook wb = null;
        
        if (key != null) {
            long randomness = System.currentTimeMillis();
            PresentationTierCache ptc = CacheFactory.getPresentationTierCache();
            FindingReportBean frb = (FindingReportBean) ptc
                    .getPersistableObjectFromSessionCache(request.getSession()
                            .getId(), key);
            if (frb != null && frb.getFinding() != null) {
                if(frb.getFinding() instanceof ISPYIHCLevelOfExpressionFinding){
                    if(frb.getExcelDoc()!=null){
                        wb = frb.getExcelDoc();
                    }
                    else{                        
                        wb = LevelOfExpressionIHCReport.getReportExcel(frb.getFinding(), new HashMap());                        
                        frb.setExcelDoc(wb);
                        ptc.addPersistableToSessionCache(frb.getFinding().getSessionId(), frb.getFinding().getTaskId(), frb);
                    }                  
                }
                else if(frb.getFinding() instanceof ISPYIHCLossOfExpressionFinding){
                    if(frb.getExcelDoc()!=null){
                        wb = frb.getExcelDoc();
                    }
                    else{                        
                        wb = LossOfExpressionIHCReport.getReportExcel(frb.getFinding(), new HashMap());                        
                        frb.setExcelDoc(wb);
                        ptc.addPersistableToSessionCache(frb.getFinding().getSessionId(), frb.getFinding().getTaskId(), frb);
                    }  
                }
                
                else if(frb.getFinding() instanceof P53Finding){
                    if(frb.getExcelDoc()!=null){
                        wb = frb.getExcelDoc();
                    }
                    else{                        
                        wb = P53Report.getReportExcel(frb.getFinding(), new HashMap());                        
                        frb.setExcelDoc(wb);
                        ptc.addPersistableToSessionCache(frb.getFinding().getSessionId(), frb.getFinding().getTaskId(), frb);
                    }  
                }
                else if(frb.getFinding() instanceof ISPYClinicalFinding){
                    if(frb.getExcelDoc()!=null){
                        wb = frb.getExcelDoc();
                    }
                    else{                        
                        wb = QuickClinicalReport.getReportExcel(frb.getFinding());                        
                        frb.setExcelDoc(wb);
                        ptc.addPersistableToSessionCache(frb.getFinding().getSessionId(), frb.getFinding().getTaskId(), frb);
                    }  
                }


               
                if (wb != null) {
                    try {
                        // actually write the xls to the output stream                        
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition",
                                "attachment; filename=xlsReport" + randomness + ".xls");

                        

                        file = File.createTempFile("werd", null);
                        FileOutputStream fileOut = new FileOutputStream(file);
                        wb.write(fileOut);
                        os = response.getOutputStream();
                        fin = new FileInputStream(file);

                        byte[] buf = new byte[4096];
                        int count = 0;

                        while (true) {
                            int n = fin.read(buf);

                            if (n == -1) {
                                break;
                            }

                            count = count + n;
                            os.write(buf, 0, n);
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        if (os != null) {
                            os.flush();
                        }

                        if (fin != null) {
                            fin.close();
                        }
                    }

                } else {
                    PrintWriter out = response.getWriter();
                    out.println("No Records Available for this query");
                }
            
        } else {
            PrintWriter out = response.getWriter();
            out.println("Error Generating the CSV.");
        }
        }
}
    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException
     *             if an error occure
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
