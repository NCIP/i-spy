<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.4" xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
	http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">

	
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/applicationContext.xml, classpath*:applicationContext-services.xml</param-value>
	</context-param>
	
	<filter> 
       <filter-name>checkLoginFilter</filter-name>
       <filter-class>gov.nih.nci.ispy.web.filters.CheckLoginFilter</filter-class>
       <init-param>
            <param-name>unauthorizedPage</param-name>
            <param-value>/unauthorized.jsp</param-value>
        </init-param>        
   </filter>
   
   <filter-mapping>
       <filter-name>checkLoginFilter</filter-name>
       <url-pattern>*.xls</url-pattern>       
   </filter-mapping>
   
   <filter-mapping>
       <filter-name>checkLoginFilter</filter-name>
       <url-pattern>*.do</url-pattern>       
   </filter-mapping>
	
	<listener>
		<listener-class>
			gov.nih.nci.caintegrator.application.configuration.ConfigurationListener
		</listener-class>
	</listener>
		

	<listener>
		<listener-class>gov.nih.nci.caintegrator.application.cache.SessionTracker</listener-class>
	</listener>

	<!-- Register ContextListener
		notifies the SessionListener whenever the context is loaded or
		unloaded in the ApplicationServer
	-->
	<listener>
		<listener-class>gov.nih.nci.ispy.cache.ISPYContextListener</listener-class>
	</listener>

	<!-- Action Servlet Configuration -->
	<servlet>
		<servlet-name>action</servlet-name>

		<servlet-class>org.apache.struts.action.ActionServlet</servlet-class>
		<init-param>
			<param-name>config</param-name>
			<param-value>/WEB-INF/struts-config.xml, /WEB-INF/struts-config-registration.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	
	<servlet>
		<servlet-name>excelDownload</servlet-name>
		<servlet-class>gov.nih.nci.ispy.web.reports.servlets.ExcelDownloadServlet</servlet-class>	
	</servlet>

	<servlet>
		<servlet-name>DisplayChart</servlet-name>
		<servlet-class>org.jfree.chart.servlet.DisplayChart</servlet-class>
	</servlet>
	<servlet>
		<servlet-name>dwr-invoker</servlet-name>
		<display-name>DWR Servlet</display-name>
		<servlet-class>uk.ltd.getahead.dwr.DWRServlet</servlet-class>
		<init-param>
			<param-name>debug</param-name>
			<param-value>true</param-value>
		</init-param>
	</servlet>
<!-- 	
  <servlet>
    <description>This is the description of my J2EE component</description>
    <display-name>This is the display name of my J2EE component</display-name>
    <servlet-name>ManageGroup</servlet-name>
    <servlet-class>gov.columbia.c2b2.ispy.web.struts.action.manageGroup</servlet-class>
  </servlet>
-->

	<servlet-mapping>
		<servlet-name>DisplayChart</servlet-name>
		<url-pattern>/servlet/DisplayChart</url-pattern>
	</servlet-mapping>
	<servlet-mapping>
		<servlet-name>dwr-invoker</servlet-name>
		<url-pattern>/dwr/*</url-pattern>
	</servlet-mapping>
	<servlet-mapping>
		<servlet-name>excelDownload</servlet-name>
		<url-pattern>*.excelReport</url-pattern>
	</servlet-mapping>

	<!-- Action Servlet Mapping -->
	<servlet-mapping>
		<servlet-name>action</servlet-name>
		<url-pattern>*.do</url-pattern>
	</servlet-mapping>
<!-- 	
  <servlet-mapping>
    <servlet-name>ManageGroup</servlet-name>
    <url-pattern>/servlet/manageGroup</url-pattern>
  </servlet-mapping>
-->
	<!-- Added to avoid session timeout problems 1 hour session timeout -->
	<session-config>
		<session-timeout>60</session-timeout>
	</session-config>

	<error-page>
		<error-code>404</error-code>
		<location>/404.jsp</location>
	</error-page>
	<error-page>
		<error-code>500</error-code>
		<location>/500.jsp</location>
	</error-page>

	<!-- The Welcome File List -->
	<welcome-file-list>
		<welcome-file>/index.jsp</welcome-file>
	</welcome-file-list>

	<!-- Struts Tag Library Descriptors -->


	<taglib>
		<taglib-uri>/WEB-INF/ispy.tld</taglib-uri>
		<taglib-location>/WEB-INF/ispy.tld</taglib-location>
	</taglib>

	<taglib>
		<taglib-uri>/WEB-INF/struts-bean.tld</taglib-uri>
		<taglib-location>/WEB-INF/struts-bean.tld</taglib-location>
	</taglib>
	<taglib>
		<taglib-uri>/WEB-INF/struts-html.tld</taglib-uri>
		<taglib-location>/WEB-INF/struts-html.tld</taglib-location>
	</taglib>

	<taglib>
		<taglib-uri>/WEB-INF/struts-logic.tld</taglib-uri>
		<taglib-location>/WEB-INF/struts-logic.tld</taglib-location>
	</taglib>
	<taglib>
		<taglib-uri>/WEB-INF/struts-tiles.tld</taglib-uri>
		<taglib-location>/WEB-INF/struts-tiles.tld</taglib-location>
	</taglib>
	<taglib>
		<taglib-uri>/WEB-INF/caintegrator-graphing.tld</taglib-uri>
		<taglib-location>/WEB-INF/caintegrator-graphing.tld</taglib-location>
	</taglib>



</web-app>
