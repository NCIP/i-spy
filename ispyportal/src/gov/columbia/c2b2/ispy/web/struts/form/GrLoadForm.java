package gov.columbia.c2b2.ispy.web.struts.form;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Session;
import gov.nih.nci.security.authorization.domainobjects.User;

import uk.ltd.getahead.dwr.ExecutionContext;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.ispy.web.struts.form.BaseForm;
import gov.nih.nci.ispy.util.GrLookupLoader;
//import gov.columbia.c2b2.ispy.web.struts.form.User;

public class GrLoadForm extends BaseForm{
	
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(ispyConstants.LOGGER);
    private static String SEPERATOR = File.separator;
/*   
    private SessionFactory sessionFactory;
    

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
*/    

//	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
    public Collection<GroupManager> validate(){
    	
		ActionErrors errors=new ActionErrors();
		
//		HttpSession session = request.getSession();
		
//		ServletContext context = session.getServletContext();
		
//		String userID = (String)session.getAttribute("name");
		
		
		HttpSession session = ExecutionContext.get().getSession(false);


		User currentUser = (User) session.getAttribute("currentUser");
		ArrayList<User> allUsers = (ArrayList<User>) session.getAttribute("allUsers");
/*		
        UILookupMapLoader uiLookupMapLoader = (UILookupMapLoader)SpringContext.getBean("grLookupLoader");
        Map<String,Collection> uiLookupMap = uiLookupMapLoader.getMap();
*/
		GrLookupLoader grLoader = (GrLookupLoader)SpringContext.getBean("grLookupLoader");	
//        Map<String,Collection> grLookupMap = grLoader.getMap(userID);
		Collection<GroupManager> grLookupMap = (Collection<GroupManager>) grLoader.getMap(currentUser.getUserId(), allUsers);
        session.setAttribute(ispyConstants.CSM_GROUPS,grLookupMap);
        

 //       request.setAttribute("groups",grLookupMap);

/*		
        UILookupMapLoader uiLookupMapLoader = (UILookupMapLoader)SpringContext.getBean("uiLookupLoader");
        Map<String,Collection> uiLookupMap = uiLookupMapLoader.getMap();
        session.setAttribute(ispyConstants.UI_LOOKUPS,uiLookupMap);
*/        
//    	return errors;
        return grLookupMap;
	}
}
