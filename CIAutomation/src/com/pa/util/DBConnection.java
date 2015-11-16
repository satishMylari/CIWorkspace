package com.pa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class DBConnection {

	public static Logger log = Logger.getLogger(DBConnection.class);
	
	Connection connection = null;
	
	 public Connection getConnection() throws SQLException, ClassNotFoundException {
		 try{
			 Class.forName(ReportConstants.DRIVER_NAME);
			 connection = DriverManager.getConnection(
						ReportConstants.DRIVER_URL, ReportConstants.DB_USERNAME, ReportConstants.DB_PASSWORD);
			 log.info("DBConnection::getConnection()::jdbc connection created...............");
				return connection;
		 }
		 catch(SQLException ex){
			 log.info("DBConnection::getConnection():: DB Connection not Created....!" + ex);
		 }
		return connection;
	}
}
