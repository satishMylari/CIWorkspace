package com.pa.util;

public class ReportConstants {


	public static final String DB_USERNAME ="hudson";
	public static final String DB_PASSWORD = "hudson";
	public static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	public static final String DRIVER_URL = "jdbc:oracle:thin:@172.21.37.156:1521/pegdb";
	public static final String DATE ="date";
	public static final String PROJECTS ="projects";
	public static final String SUCCESS_MSG ="System Succesfully Updated The Information";
	public static final String FAILURE_MSG ="System Failed updated the information";
	public static final String FILE_PATH="file?path=C:\\Image\\CIReport.jpeg";
	public static final String FIRST="First";
	public static final String SECOND="Second";
	public static final String CIREPORT_PATH="C:\\Image\\CIReport.jpeg";
	public static final String HUDSON_CIREPORT_PATH="/opt/apache-tomcat-5.5.23/webapps/hudson/images/CIReport";
	public static final String NO_DATA="NO DATA!";
	public static final String CONTENT_TYPE="text/plain";
	public static final String ENCODING="UTF-8";
	
	public static final String HUDSON_USER_NAME="admin";
	public static final String HUDSON_PASSWORD="adminHudson";
	public static final String BASIC="Basic ";
	public static final String HUDSON_URL="http://172.21.5.31:8080/hudson/view/All/api/xml";
	
	public static final String PROJECT="project";
	public static final String CICOUNT="cicount";
	
	public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss.SSS";
	public static final String INSERT_DATE_FORMAT="MM-dd-yyyy hh:mm:ss";
	public static final String DATE_FORMAT_PMD="yyyy/mm/dd hh:mm:ss";
	
	public static final String SQL_DATE_FORMAT="MM-DD-YYYY HH:MI:SS";
	public static final int START_INDEX=0;
	public static final int END_INDEX=10;
	
	public static final int WIDTH=640;
	public static final int HEIGHT=480;
	
	/** Constant for constant 1 **/
	public static final int ONE = 1;
	
	/** Constant for constant 0 **/
	public static final int ZERO = 0;
	
	
	/** Constant for constant 0 **/
	public static final String END_DATE ="12-31-2020 00:00:00";
	
	/** Constant for insert query for pmd_report **/
	public static final String QUERY_START_PMDREPORT = "insert into pmd_report values(";
	
	/** Constant for insert query for pmd_basecountt **/
	public static final String QUERY_START_PMDBASECOUNT = "insert into pmd_basecount values(";
	
	public static final String TO_DATE = "TO_DATE(";
	
	/** Constant for single quote **/
	public static final String SINGLE_QUOTE = "'";

	/** Constant for coma **/
	public static final String COMA = ",";
	
	/** Constant for query end **/
	public static final String QUERY_END = ");";
	
	/** Constant for query end **/
	public static final String END_BRACKETS=")";
	
	
	
	

	
}
