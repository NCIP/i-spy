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
import gov.columbia.c2b2.ispy.jdbc.GrDbConn;

public class GrDbIns {

  public static int insMesRec (String queryText, ArrayList query){

  Connection conn = null;
  SQLWarning warning = null;
  Statement stmt = null;
  ResultSet results;
/*
  conn = GrDbConn.getMessConn();
  warning = GrDbConn.getMessWarning(conn);
  stmt = GrDbConn.getMessStat(conn);
*/
//System.out.println("value of queryText|" + queryText + "|");
// EXECUTE SQL
       boolean ret = false;
       int updateCount = 0;
       try {
       ret = stmt.execute(queryText);
       if (ret == true){
            results = stmt.getResultSet();
       }
       else{
            updateCount = stmt.getUpdateCount();
       }
       } catch(Exception e){

          System.out.println(e);
       }
    if(updateCount == 1){
    for(int i=0;i<query.size();i++){
// EXECUTE SQL
//System.out.println("value of query 2 |" + (String)query.get(i) + "|" + i + "|");
       try {
       ret = stmt.execute((String) query.get(i));
       if (ret == true){
            results = stmt.getResultSet();
       }
       else{
            updateCount = stmt.getUpdateCount();
       }
       } catch(Exception e){

          System.out.println(e);
       }
     }
    }

//System.out.println("value of mess |" + subject + "|" + body + "|" + updateCount + "|");
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
// CLOSE STATEMENT
    try {
       stmt.close();
    } catch (Exception e) {

       System.out.println(e);
    }
  // CLOSE CONNECTION
  try {
      conn.close();
  } catch (Exception e) {
 
      System.out.println(e);
  }
  return(updateCount);

   } // ent of insMesRec
} //ent of messDbIns
