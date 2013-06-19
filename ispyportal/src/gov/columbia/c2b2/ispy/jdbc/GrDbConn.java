/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.columbia.c2b2.ispy.jdbc;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import javax.naming.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import gov.columbia.c2b2.ispy.web.struts.form.GroupManager;


public class GrDbConn {

  public static ArrayList getGroups (String query, String format){

  Connection conn = null;
  SQLWarning warning = null;
  Statement stmt = null;
  ResultSet results;
//  StringBuffer buf = new StringBuffer();
  String buf = null;
  ArrayList al = null;

 
  conn = getConn();
  warning = getWarning(conn);
  stmt = getStatmnt(conn);
  results = getQueryExec(stmt,query);
  if(format.equals("head")){
        al = getGroupsSet(results);
/*        
  }else if(format.equals("text") || format.equals("hist")){
        al = getMessTextSet(results,conn,stmt,format);
  }else if(format.equals("listu")){
        al = getRecipentsSet(results);
*/
  }

  closeConn (conn, stmt);
/*
// CLOSE STATEMENT 
    try {
       stmt.close();
    } catch (Exception e) {
     EmailUtil.emailAlert("Error closing statment in getmesRes messDbConn.java");
       System.out.println(e);
    }
//System.out.println("Connection to filemaker before close |" + conn + "|");
  // CLOSE CONNECTION 
  try {
      conn.close();
//System.out.println("Connection to filemaker after close |" + conn + "|");
  } catch (Exception e) {
      EmailUtil.emailAlert("Error closing connection in getmesres messDbConn.java");
      System.out.println(e);
  }

// IS CONNECTION CLOSED 
boolean b = false;
try {
    b = conn.isClosed();
} catch (Exception e) {
    EmailUtil.emailAlert("Error closing connection in getmesRes in messDbConn");
    System.out.println(e);
}
if(b == true){ 
//	System.out.println("Connection to filemaker is closed |" + conn + "|");
}
else{
//        System.out.println("Connection to filemaker is not closed |" + conn + "|");
 }
*/
   return al;
 }

   public static void closeConn (Connection conn, Statement stmt){
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
//      System.out.println("Connection to filemaker is closed |" + conn + "|");
}
else{
//        System.out.println("Connection to filemaker is not closed |" + conn + "|");
 }


     return;
   }

/*
   private static ArrayList getMessTextSet(ResultSet res,Connection conn,Statement stmt, String format){

        ArrayList messageId = new ArrayList();
        String mIID = "";
        try {
                while (res.next())
                {
                // Loop through each column, getting the column
                // data and displaying
    
                textRes resT = new textRes();
                resT.dates = res.getString("Dates");
                String[] tempT = resT.dates.split(" ");
                if(tempT[1].length() <= 5)
                     resT.dates = tempT[0] + " " + tempT[1] + " " + tempT[2];
                else
                     resT.dates = tempT[0] + " " + tempT[1].substring(0,tempT[1].indexOf(':',4)) + " " + tempT[2];
                resT.fromUID = res.getString("FromUID");
                resT.toUID = res.getString("ToUID");
                resT.subject = res.getString("Subject");
              if(resT.subject == null) resT.subject = "&nbsp;";
              else { resT.subject = resT.subject.replaceAll("\"", "\\&#34");
                resT.subject = resT.subject.replaceAll("\\n\\n", "<br>");
                resT.subject = resT.subject.replaceAll("\\r\\n", "<br>");
              }
                resT.text = res.getString("Text");
              if(resT.text == null) {resT.text = "&nbsp;";}
              else{
                resT.text = resT.text.replaceAll("\\n\\n", "<br>");
                resT.text = resT.text.replaceAll("\\r\\n", "<br>");
              }
                resT.messIID = res.getString("messIID");
                resT.messTID = res.getString("messTID");
                resT.seqIT = res.getInt("seqIT");
                mIID = res.getString("messIID");
                resT.nameU = res.getString("nameU");
                resT.nameI = res.getString("nameI");
                messageId.add(resT);
//System.out.println("value of array of strings elements: |" + resS.date + "|" );
        }
        res.close();
    } catch (Exception e) {
	
    System.out.println(e);
    }
   if(format.equals("text")){
// EXECUTE SQL
       String queryText = "UPDATE MessageReceived SET MessageReadFlag = 'Y', ReadTimestamp = DATE()  + TIME() WHERE MessageInstanceID = '" +  mIID + "'";
       boolean ret = false;
       int updateCount = 0;
       try {
       ret = stmt.execute(queryText);
       if (ret == true){
            res = stmt.getResultSet();
       }
       else{
            updateCount = stmt.getUpdateCount();
       }
       } catch(Exception e){
	   
          System.out.println(e);
       }
     if(updateCount >= 1){
// SEND COMMIT
        try {
          conn.commit();
        } catch (Exception e) {
	    
           System.out.println(e);
        }
      }else{
// SEND ROLLBACK
        try {
          conn.rollback();
        } catch (Exception e) {
	  
        System.out.println(e);
        }
     }
   }

    return messageId;
  }
*/
   /*
   private  static ArrayList getRecipentsSet(ResultSet res){

        ArrayList messageId = new ArrayList();
        int recs = 0;

        try
        {
                while (res.next())
                {
                // Loop through each column, getting the column
                // data and displaying

                userList resU = new userList();
                resU.providerUID = res.getString("providerUID");
                resU.providerFN = res.getString("providerFN");
                resU.providerLN = res.getString("providerLN");
                resU.providerTitle = res.getString("providerTitle");
//                if(resU.providerTitle.equals("Case Manager")) resU.statusOfChoice = "C";
                if(resU.providerTitle.equals("Clinician") || resU.providerTitle.equals("Case Manager")) resU.statusOfChoice = "C";
                resU.numRec = recs;
                recs++;
                messageId.add(resU);
//System.out.println("value of array of strings elements: |" + resS.date + "|" );
                }
                res.close();
        } catch (Exception e) {
	   
                System.out.println(e);
        }
//System.out.println("value of array of strings elements: |" + resS.r[4] + "|" );
        return messageId;
  }
*/
   private static ArrayList getGroupsSet(ResultSet res){

        ArrayList groupS = new ArrayList();
        
	try 
	{
    		while (res.next())
	        {
        	// Loop through each column, getting the column
        	// data and displaying

                GroupManager resS = new GroupManager();
                resS.setOwnId(res.getLong("userID"));
                resS.setGrId(res.getLong("groupID"));
                resS.setGrName(res.getString("groupName"));
                resS.setGrStatus(res.getString("groupStatus").charAt(0));
                resS.setShareStatus(res.getString("shareStatus").charAt(0));
 //               resS.setUpdateTime(res.getString("update_time"));
                System.out.println("value of array of strings elements: |" + resS + "|" );
                groupS.add(resS);
 /*               
		resS.dates = res.getString("Dates");
//System.out.println("value of array of date |" + resS.dates + "|" );
                String[] tempT = resS.dates.split(" ");
                if(tempT[1].length() <= 5)
                     resS.dates = tempT[0] + " " + tempT[1] + " " + tempT[2];
                else
                     resS.dates = tempT[0] + " " + tempT[1].substring(0,tempT[1].indexOf(':',4)) + " " + tempT[2];
//System.out.println("value of array of date |" + resS.dates + "|" );
		resS.fromUID = res.getString("FromUID");
		resS.subject = res.getString("Subject");
               if(resS.subject == null) resS.subject = "&nbsp;";
               else {
                  resS.subject = resS.subject.replaceAll("\"", "\\&#34");
                resS.subject = resS.subject.replaceAll("\\n\\n", "<br>");
                resS.subject = resS.subject.replaceAll("\\r\\n", "<br>");
               }
//System.out.println("value of array of subject |" + resS.subject + "|" );
		resS.messIID = res.getString("messIID");
		resS.readFlag = res.getString("ReadFlag");
                resS.nameU = res.getString("nameU");
                messageId.add(resS);
//System.out.println("value of array of strings elements: |" + resS.nameU + "|" );
 
 */
		}
    		res.close();
    	} catch (Exception e) {
	    
    		System.out.println(e);
    	}
    	return groupS;
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
//        System.out.println("No Warnings");
//        return warning;
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
        
        Properties p = new Properties();
        p.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory" ) ;
        p.setProperty("java.naming.provider.url","localhost") ;
        p.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" ) ;

 


        try {

            Context initContext = new InitialContext();
  //          Context envContext  = (Context)initContext.lookup("java:comp/env");
 //           System.out.println(envContext); 
//            DataSource dataSource = (DataSource)envContext.lookup("jdbc/Messages");
            DataSource dataSource = (DataSource)initContext.lookup("java:/ispy");
 //           DataSource dataSource = (DataSource)envContext.lookup("java:/ispy");
            
            System.out.println(dataSource);
//            System.out.println("getMessConn Connecting to |" + dataSource + "|");

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
