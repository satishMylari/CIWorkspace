package com.pa.dao;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.pa.util.DBConnection;
import com.pa.util.ReportConstants;

public class ReportDao {
	public static Logger log = Logger.getLogger(ReportDao.class);
	
	
	 
	public ReportDao()
	{
	}
		
	public List getProjectDetails()
	{
		log.info(" Inside getProjectDetails");
		 Connection connection = null;
		 Statement st = null;
		 ResultSet rs = null;
		 DBConnection dbConn =null;
		 List projectlist=new ArrayList();
		 List hudsonProjectlist=new ArrayList();
		 try
		 {
		try {
			    dbConn = new DBConnection();
			    connection = dbConn.getConnection();
				st = connection.createStatement();
				String query = "select project from pmd_report where build_rundate in (select max(build_rundate) from pmd_report where cicount is not null group by project)";
				rs = st.executeQuery(query);
				while (rs.next()) {
					String project = rs.getString("project");
					projectlist.add(project);
			    }
				
				URL url = new URL(ReportConstants.HUDSON_URL);
				//URL url = new URL("http://172.21.0.71:8080/hudson/view/All/api/xml");
				URLConnection conn = url.openConnection();
				String userpass = ReportConstants.HUDSON_USER_NAME + ":" + ReportConstants.HUDSON_PASSWORD;
				String basicAuth = ReportConstants.BASIC + new String(new Base64().encode(userpass.getBytes()));
				conn.setRequestProperty ("Authorization", basicAuth);
				Document doc = parseXML(conn.getInputStream());
		        NodeList descNodes = doc.getElementsByTagName("name");

		        for(int i=0; i<descNodes.getLength();i++)
		        {
		        	hudsonProjectlist.add(descNodes.item(i).getTextContent());
		        }
		        if(hudsonProjectlist.contains("All"))
		        {
		        	hudsonProjectlist.remove("All");
		        }
		   
		        createNewprojects(projectlist,hudsonProjectlist);
		          	         
		        
			} catch (SQLException ex) {
				log.info("Exception Occure in SQL ");
			}
		   connection.close();
		 } catch (Exception e) {
				e.printStackTrace();
			}
		 
		 
		return 	hudsonProjectlist;
			
	}
	
	public List<String> getProjects() {
		List<String> projects = new ArrayList<String>();
		log.info(" Inside getProjects");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		projects.add("Please Select One");
		try {
			try {
				DBConnection dbConn = new DBConnection();
				connection = dbConn.getConnection();
				String query = "select project from pmd_basecount where VERSION_NO=0 order by project asc";
				st = connection.createStatement();
				rs = st.executeQuery(query);
				while (rs.next()) {
					String project = rs.getString("project");
					projects.add(project);

				}
			} catch (SQLException ex) {
				log.info("Exception Occure in SQL ");
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return projects;
	}	
	
	public void updateStatus(String selectedProject) {
		boolean  statusupdate= false;
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			try {
				DBConnection dbConn = new DBConnection();
				connection = dbConn.getConnection();
				st = connection.createStatement();
				String sql = "UPDATE pmd_report SET valid ='"+ReportConstants.ZERO +"' where project in ("+ selectedProject+")";
				st.executeUpdate(sql); 
				 
			} catch (SQLException ex) {
				log.info("Exception Occure in SQL ");
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	private static void createNewprojects(List projectlist,
			List hudsonProjectlist) {

		Collection<String> similar = null;
		Collection<String> different = null;
		Connection connection = null;
		Statement st = null;
		DBConnection dbConn = null;
		String nameQuery = null;
		String InsertnameQuery = null;
		StringBuffer queryBuffer = new StringBuffer();
		StringBuffer insertqueryBuffer = new StringBuffer();
		
		
			try {
				if (projectlist != null && hudsonProjectlist != null) {
					similar = new HashSet<String>(projectlist);
					different = new HashSet<String>();
					SimpleDateFormat formatter = new SimpleDateFormat(ReportConstants.INSERT_DATE_FORMAT);
					String currentdate = formatter.format(new Date());
					String dateInString = ReportConstants.END_DATE;
					String enddate = formatter.format(formatter.parse(dateInString));
					
					nameQuery = new StringBuffer(ReportConstants.QUERY_START_PMDREPORT).toString();
					InsertnameQuery=new StringBuffer(ReportConstants.QUERY_START_PMDBASECOUNT).toString();
					
					dbConn = new DBConnection();
					connection = dbConn.getConnection();
					st = connection.createStatement();
					different.addAll(projectlist);
					different.addAll(hudsonProjectlist);
					different.removeAll(similar);
                    
					st.execute("ALTER SESSION SET NLS_DATE_FORMAT='YYYY/MON/DD HH24:MI:SS'");
					Iterator i = different.iterator();
					
					while (i.hasNext()) {
						String project = i.next().toString();

					/*	String sql = "Insert into PMD_REPORT " + " values ('"
								+ project + "'," + ReportConstants.ZERO + ",'"
								+ currentdate + "','" + ReportConstants.ZERO
								+ "','" + ReportConstants.ZERO + "" + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "" + "','"
								+ ReportConstants.ZERO + "',"
								+ ReportConstants.ZERO + ",'"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "'," + ""
								+ ReportConstants.ZERO + ","
								+ ReportConstants.ZERO + ","
								+ ReportConstants.ZERO + ","
								+ ReportConstants.ONE + ")";*/
						
						String sql=queryBuffer.append(nameQuery).append(ReportConstants.SINGLE_QUOTE).append(project).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.ZERO)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(currentdate).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								  .append(ReportConstants.COMA).append(ReportConstants.ZERO).append(ReportConstants.COMA).append(ReportConstants.ZERO)
								  .append(ReportConstants.COMA).append(ReportConstants.ZERO).append(ReportConstants.COMA).append(ReportConstants.ONE)
								  .append(ReportConstants.END_BRACKETS).toString();
												
                   
						
						/*String insertsql = "INSERT INTO pmd_basecount Values" + "('"
								+ project + "','" + ReportConstants.ZERO
								+ "','" + ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "" + "' ,'"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "','"
								+ ReportConstants.ZERO + "'," + "'"
								+ ReportConstants.ZERO + "',TO_DATE('"
								+ currentdate
								+ "','MM-DD-YYYY HH:MI:SS'),TO_DATE('"
								+ enddate + "','MM-DD-YYYY HH:MI:SS'))";*/
						
						String insertsql=insertqueryBuffer.append(InsertnameQuery).append(ReportConstants.SINGLE_QUOTE).append(project).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.ZERO).append(ReportConstants.SINGLE_QUOTE)
								 .append(ReportConstants.COMA).append(ReportConstants.TO_DATE).append(ReportConstants.SINGLE_QUOTE).append(currentdate)
								 .append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.SQL_DATE_FORMAT)
								 .append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.END_BRACKETS).append(ReportConstants.COMA).append(ReportConstants.TO_DATE)
								 .append(ReportConstants.SINGLE_QUOTE).append(enddate)
								 .append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.COMA).append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.SQL_DATE_FORMAT)
								 .append(ReportConstants.SINGLE_QUOTE).append(ReportConstants.END_BRACKETS).append(ReportConstants.END_BRACKETS).toString();
					
						st.executeUpdate(sql);
						st.executeUpdate(insertsql);
						queryBuffer.delete(0, queryBuffer.length());
						insertqueryBuffer.delete(0, insertqueryBuffer.length());
						
					}
					
				}
			} catch (SQLException ex) {
				log.info("Exception Occure in SQL ");
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
			try {
				st.close();
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		} 

	private static Document parseXML(InputStream stream)
		    throws Exception
		    {
		        DocumentBuilderFactory objDocumentBuilderFactory = null;
		        DocumentBuilder objDocumentBuilder = null;
		        Document doc = null;
		        try
		        {
		            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
		            doc = objDocumentBuilder.parse(stream);
		        }
		        catch(Exception ex)
		        {
		            throw ex;
		        }       

		        return doc;
		    }
}
