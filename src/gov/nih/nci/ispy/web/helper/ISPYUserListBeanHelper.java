/**
 * This helper accesses the UserListBean and can be instantiated
 * anywhere. Depending on how and where the developer instantiates this class
 * the Helper can will access the UserListBean through the session, by
 * either recieving the session itself, or, in this case, having DWR access
 * the UserListBean on it own.
 */
package gov.nih.nci.ispy.web.helper;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.nih.nci.caintegrator.application.cache.PresentationTierCache;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.ispy.cache.ISPYContextListener;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;
import gov.nih.nci.ispy.web.xml.Transformer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


import uk.ltd.getahead.dwr.ExecutionContext;

/**
 * @author rossok
 *
 */
public class ISPYUserListBeanHelper implements UserListBeanHelper{
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private HttpSession session;
    private String sessionId;
    private UserListBean userListBean;
    private ISPYListValidator listValidator = new ISPYListValidator();
    private static Logger logger = Logger.getLogger(ISPYUserListBeanHelper.class);
    
    
    public ISPYUserListBeanHelper(UserListBean userListBean){
        this.userListBean = userListBean;
    }
    
    public ISPYUserListBeanHelper(HttpSession session){
        userListBean = (UserListBean) session.getAttribute(ispyConstants.USER_LISTS);        
        
    }
    public ISPYUserListBeanHelper(){       
        session = ExecutionContext.get().getSession(false); 
        sessionId = ExecutionContext.get().getSession(false).getId(); 
        userListBean = (UserListBean) session.getAttribute("userListBean");        
               
    }
    public void addList(UserList userList) {
        userListBean.addList(userList);        
    }
    
    public void removeList(String listName) {
        userListBean.removeList(listName);
        
    }
    public void addItemToList(String listName, String listItem) {
        UserList userList =  userListBean.getList(listName);
        userList.getList().add(listItem);
    }
    
    public void removeItemFromList(String listName, String listItem) {        
        UserList userList =  userListBean.getList(listName);
        userList.getList().remove(listItem);
    }
    
    public Document getDetailsFromList(String listName) {
        UserList userList = userListBean.getList(listName);
        
        Document document =  DocumentHelper.createDocument();
        Element list = document.addElement("list");
        Element type = list.addAttribute("type", userList.getListType().toString());
        Element name = list.addAttribute("name", userList.getName());
        
        for(String i : userList.getList()){
            Element item = list.addElement("item");
            Element value = item.addElement("value");
            value.addText(i);
        }
        
        
        return document;
    }
    
    public List<UserList> getLists(ListType listType) {
        List<UserList> typeList = new ArrayList<UserList>();
        
        if(!userListBean.getEntireList().isEmpty()){
            for(UserList list : userListBean.getEntireList()){
                if(list.getListType() == listType){
                    typeList.add(list);
                }
            }
        }
        return typeList;
    }
    
    public void renderListDetails(HttpServletRequest request, Document listXML, String xsltFilename, JspWriter out) {
        File styleSheet = new File(ISPYContextListener.getContextPath()+"/xsl/"+xsltFilename);
        // load the transformer using JAX
        logger.debug("Applying XSLT "+xsltFilename);
        Transformer transformer;
        try {
            
            transformer = new Transformer(styleSheet);
            Document transformedDoc = transformer.transform(listXML);
            
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer;
                writer = new XMLWriter(out, format );
                
                writer.write( transformedDoc );
                
                
            
        }catch (UnsupportedEncodingException uee) {
            logger.error("UnsupportedEncodingException");
            logger.error(uee);
        }catch (IOException ioe) {
            logger.error("IOException");
            logger.error(ioe);
        }
    }
     
   

}
