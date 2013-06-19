/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.util;

import javax.naming.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.sql.DataSource;

import gov.columbia.c2b2.ispy.web.struts.form.UserInfo;

public class LoadUserInfo {
	
	private Connection conn = null;
	private SQLWarning warning = null;
	private Statement stmt = null;
	private ResultSet results;
	  
	public UserInfo getUserInfo(String userID){
		UserInfo uinfo = new UserInfo();
		String query = "select USER_ID, FIRST_NAME, LAST_NAME, ORGANIZATION, DEPARTMENT, TITLE, PHONE_NUMBER,"
			+ " EMAIL_ID, START_DATE, END_DATE, UPDATE_DATE from CSM_USER where LOGIN_NAME = '" + userID + "'";
		
		this.conn = getConn();
		this.warning = getWarning(conn);
		this.stmt = getStatmnt(conn);
		
		this.results = getQueryExec(stmt,query);

		uinfo = getUserInfoSet(results);
		closeConn (conn, stmt);
		return uinfo;
	}

	private  UserInfo getUserInfoSet(ResultSet res){

	    UserInfo info = new UserInfo();
	        
		try 
		{
	    		while (res.next())
		        {
	    			info.setUserId(res.getLong("USER_ID"));
	    			info.setFirstName(res.getString("FIRST_NAME"));
	    			info.setLastName(res.getString("LAST_NAME"));
	    			info.setOrganization(res.getString("ORGANIZATION"));
	    			info.setDepartment(res.getString("DEPARTMENT"));
	    			info.setTitle(res.getString("TITLE"));
	    			info.setPhoneNum(res.getString("PHONE_NUMBER"));
	    			info.setEmailID(res.getString("EMAIL_ID"));
			}
	    		res.close();
	    	} catch (Exception e) {
		    
	    		System.out.println(e);
	    	}
	    	return info;
	  }
	   
	   public void closeConn (Connection conn, Statement stmt){
		// CLOSE STATEMENT
		    try {
		       stmt.close();
		    } catch (Exception e) {
			
		       System.out.println(e);
		    }
		//System.out.println("Connection to filemaker before close |" + conn + "|");
		  // CLOSE CONNECTION
		  try {
		      conn.close();
		//System.out.println("Connection to filemaker after close |" + conn + "|");
		  } catch (Exception e) {
		      
		      System.out.println(e);
		  }

		// IS CONNECTION CLOSED
		boolean b = false;
		try {
		    b = conn.isClosed();
		} catch (Exception e) {
		    System.out.println(e);
		}
		if(b == true){
//		      System.out.println("Connection to filemaker is closed |" + conn + "|");
		}
		else{
//		        System.out.println("Connection to filemaker is not closed |" + conn + "|");
		 }
	   }
	   
	   public static ResultSet getQueryExec(Statement st,String queryQ){

			ResultSet results = null;
			try {
		    		results = st.executeQuery(queryQ);
			} catch (Exception e){
			    
		    		System.out.println(e);
			}
			return results;
		  }

		   public static Statement getStatmnt(Connection Conn){

			Statement stmt = null;

			try {
		    	    stmt = Conn.createStatement();
			} catch (Exception e){
			    
		    	    System.out.println(e);
			}
		        return stmt;
		  }

		   public static SQLWarning getWarning(Connection Conn){

		      SQLWarning Warning = null;


		      try {
		         Warning = Conn.getWarnings();

		      if (Warning == null){
//		        System.out.println("No Warnings");
//		        return warning;
		      }

		      while (Warning != null) {
		        System.out.println("Warning: "+Warning);
		        Warning = Warning.getNextWarning();
		      }
		      } catch (Exception e){
			  
		      System.out.println(e);
		      }
		   return Warning;
		   }


		   public static Connection getConn (){

		        java.sql.Connection conn = null;

		        try {

		            Context initContext = new InitialContext();
		            DataSource dataSource = (DataSource)initContext.lookup("java:/ispycsm");
		            System.out.println(dataSource);
//		            System.out.println("getMessConn Connecting to |" + dataSource + "|");
		            conn = dataSource.getConnection();
		            if (null != conn) {
		                System.out.println("Connection successful!");
		            }		   
		            } catch (Exception e) {
				
		               System.out.println (e.getMessage());
		            }
		           return conn;
		   }
}
