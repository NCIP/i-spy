/**
 * The List Generator can be used to create List of strings retrived
 * from the formFile sent to it. The assumed format of the file to be parsed is
 * .txt for now
 */
package gov.nih.nci.ispy.web.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.log4j.Logger;

/**
 * @author rossok
 * 
 */
public class ISPYUserListGenerator {

    private static Logger logger = Logger
            .getLogger(ISPYUserListGenerator.class);
    
    public List generateList(FileItem formFile) {
        

        List<String> tempList = new ArrayList<String>();

        if ((formFile != null)
                && (formFile.getName().endsWith(".txt") || formFile.getName()
                        .endsWith(".TXT"))) {
            try {
                InputStream stream = formFile.getInputStream();
                String inputLine = null;
                BufferedReader inFile = new BufferedReader(
                        new InputStreamReader(stream));

                int count = 0;

                while ((inputLine = inFile.readLine()) != null) {
                    if (isAscii(inputLine)) { // make sure all data is ASCII

                        inputLine = inputLine.trim();
                        count++;
                        tempList.add(inputLine);

                    }
                }// end of while

                inFile.close();
            } catch (IOException ex) {
                logger.error("Errors when uploading file:" + ex.getMessage());
            }
        }

        return tempList;

    }


    public List oldgenerateList(HttpServletRequest req) {
        FileItem formFile = null;
        try {

            FileUpload fup = new FileUpload();
            boolean isMultipart = FileUpload.isMultipartContent(req);
            // Create a new file upload handler
            // System.out.println(isMultipart);
            DiskFileUpload upload = new DiskFileUpload();

            // Parse the request
            List<FileItem> items = upload.parseRequest(req);

            for (FileItem item : items) {
                if (item.isFormField()) {
                    // System.out.println("its a field");
                } else {
                    // System.out.println("its a file");
                    // System.out.println(item.getName());
                    formFile = item;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        List<String> tempList = new ArrayList<String>();

        if ((formFile != null)
                && (formFile.getName().endsWith(".txt") || formFile.getName()
                        .endsWith(".TXT"))) {
            try {
                InputStream stream = formFile.getInputStream();
                String inputLine = null;
                BufferedReader inFile = new BufferedReader(
                        new InputStreamReader(stream));

                int count = 0;

                while ((inputLine = inFile.readLine()) != null) {
                    if (isAscii(inputLine)) { // make sure all data is ASCII

                        inputLine = inputLine.trim();
                        count++;
                        tempList.add(inputLine);

                    }
                }// end of while

                inFile.close();
            } catch (IOException ex) {
                logger.error("Errors when uploading file:" + ex.getMessage());
            }
        }

        return tempList;

    }

    /**
     * <p>
     * Checks whether the string is ASCII 7 bit.
     * </p>
     * 
     * @param str
     *            the string to check
     * @return false if the string contains a char that is greater than 128
     */
    public static boolean isAscii(String str) {
        boolean flag = false;
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) > 128) {
                    return false;
                }
            }
            flag = true;
        }
        return flag;
    }
}
