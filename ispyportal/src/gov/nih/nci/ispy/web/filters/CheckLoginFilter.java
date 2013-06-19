/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.filters;

import gov.nih.nci.ispy.web.helper.ISPYImageFileHandler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class CheckLoginFilter implements Filter{

    private FilterConfig  filterConfig;
    private ServletContext ctx;
    private static Logger logger = Logger.getLogger(CheckLoginFilter.class);
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        ctx = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

        boolean authorized = false;
        if (request instanceof HttpServletRequest) {
            String isloginpage = ((HttpServletRequest) request).getRequestURI();
            if(isloginpage!=null && isloginpage.endsWith("login.do"))   {
                //just continue, so they can login
                chain.doFilter(request, response);
                return;
            }
            
            if(isloginpage!=null && (isloginpage.endsWith("register.do") || isloginpage.endsWith("requestRegister.do")))   {
                //just continue, so they can login
            	authorized = true;  
            }
            
            HttpSession session = ((HttpServletRequest) request).getSession();
            if(session != null && session.getAttribute("logged")!=null && session.getAttribute("logged").equals("yes")){
                   authorized = true;                
            }
        }

        if (authorized) {
            chain.doFilter(request, response);
            return;
        } else if (filterConfig != null) {
            String unauthorizedPage = filterConfig.getInitParameter("unauthorizedPage");
            if (unauthorizedPage != null && !"".equals(unauthorizedPage)) {
                filterConfig.getServletContext().getRequestDispatcher(unauthorizedPage).forward(request, response);
                return;
            }
        }

        throw new ServletException("Unauthorized access, unable to forward to login page");
    }
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
    
    

}
