package com.pa.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.pa.util.DBConnection;
import com.pa.util.ReportConstants;


/**
 * Servlet implementation class ProjectdetailsServlet
 */
@WebServlet("/ProjectdetailsServlet")
public class ProjectdetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ProjectdetailsServlet.class);

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectdetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String project=null;
		 String  pmdbcount=null;
		 String checkstylebcount =null;
		 String jspbcount=null;
		 String propertiesbcount=null;
		 String ncssbcount=null;
		 String jlcbcount=null;
		 String pmdexclusions=null;
		 String checkstyleexclusions=null;
		 
		
		String select=request.getParameter("select");
		String submit=request.getParameter("submit");
		if(null !=select && !(select.equalsIgnoreCase("Please Select One")))
		{
			 response.setContentType(ReportConstants.CONTENT_TYPE);  
		     response.setCharacterEncoding(ReportConstants.ENCODING); 
		     JSONObject o=new JSONObject();
			 Connection connection = null;
			 Statement st = null;
			 ResultSet rs = null;
			 DBConnection dbConn = new DBConnection();
			 try
			 {
			try {
				    dbConn = new DBConnection();
				    connection = dbConn.getConnection();
					st = connection.createStatement();
					String query ="select * from pmd_basecount where project='"+select+"'" ;
					st = connection.createStatement();
					rs = st.executeQuery(query);
					while (rs.next()) {
						 project = rs.getString("project");
						 o.put("project",project);
						 pmdbcount=rs.getString("pmdbcount");
						 o.put("pmdbcount",pmdbcount);
						 checkstylebcount=rs.getString("checkstylebcount");
						 o.put("checkstylebcount",checkstylebcount);
						 jspbcount=rs.getString("jspbcount");
						 o.put("jspbcount",jspbcount);
						 propertiesbcount=rs.getString("propertiesbcount");
						 o.put("propertiesbcount",propertiesbcount);
						 ncssbcount=rs.getString("ncssbcount");
						 o.put("ncssbcount",ncssbcount);
						 jlcbcount=rs.getString("jlcbcount");
						 o.put("jlcbcount",jlcbcount);
						 pmdexclusions=rs.getString("pmdexclusions");
						 o.put("pmdexclusions",pmdexclusions);
						 checkstyleexclusions=rs.getString("checkstyleexclusions");
						 o.put("checkstyleexclusions",checkstyleexclusions);
					}
				} catch (SQLException ex) {
					log.info("Exception Occure in SQL ");
				}
			   connection.close();
			 } catch (Exception e) {
					e.printStackTrace();
				}
			 response.getWriter().print(o);	  
		    
		}else if(null !=select && select.equalsIgnoreCase("Please Select One"))
		{
			JSONObject o=null;
		    response.getWriter().print(o);
		}
		
		if(null !=submit && (submit.equalsIgnoreCase("update")) )
		{
			String	projectName=request.getParameter("project");
			String  pmdbcountvalue=request.getParameter("pmdbcount");
			String  checkstylebcountvalue=request.getParameter("checkstylebcount");
			String  jspbcountvalue=request.getParameter("jspbcount");
			String  propertiesbcountvalue=request.getParameter("propertiesbcount");
			String  ncssbcountvalue=request.getParameter("ncssbcount");
			String  jlcbcountvalue=request.getParameter("jlcbcount");
			String  pmdexclusionsvalue=request.getParameter("pmdexclusions");
			String  checkstyleexclusionsvalue=request.getParameter("checkstyleexclusions");
			Connection connection = null;
			Statement st = null;
			DBConnection dbConn = new DBConnection();
			
			
			 try
			 {
			try {
				
				SimpleDateFormat formatter=new SimpleDateFormat(ReportConstants.INSERT_DATE_FORMAT);
				String currentdate=formatter.format(new Date());
				String dateInString =ReportConstants.END_DATE ;
									 
				String enddate=formatter.format(formatter.parse(dateInString));
			 
				    dbConn = new DBConnection();
				    connection = dbConn.getConnection();
				    st = connection.createStatement();
				    
				    st.execute("ALTER SESSION SET NLS_DATE_FORMAT='DD-MON-YYYY HH24:MI:SS'");
				    String sql ="UPDATE pmd_basecount SET END_DATE = TO_DATE('"+ currentdate + "','MM-DD-YYYY HH:MI:SS') , VERSION_NO='"+ReportConstants.ONE+"' where project= '"+ projectName +"' and VERSION_NO='"+ReportConstants.ZERO+"'";
				    st.executeUpdate(sql);
					
					String insertsql="INSERT INTO pmd_basecount Values"
							  +"('"+projectName+"','"+pmdbcountvalue+"','"+ checkstylebcountvalue+"','"+jspbcountvalue+"','"+propertiesbcountvalue+"'"
							  + " ,'"+ncssbcountvalue+"','"+jlcbcountvalue+"','"+pmdexclusionsvalue+"','"+checkstyleexclusionsvalue+"','"+ReportConstants.ZERO+"',"
							  + "TO_DATE('"+ currentdate + "','MM-DD-YYYY HH:MI:SS'),TO_DATE('"+ enddate + "','MM-DD-YYYY HH:MI:SS'))"; 
				    st.execute(insertsql);
					st.execute("commit");
					
					response.setContentType(ReportConstants.CONTENT_TYPE);  
				    response.setCharacterEncoding(ReportConstants.ENCODING);
				    response.reset();
					
				    JSONObject o=new JSONObject();
					o.put("message", ReportConstants.SUCCESS_MSG);
					response.getWriter().print(o);
				    st.close();
				} catch (SQLException ex) {
					log.info("Exception Occure in SQL ");
					ex.printStackTrace();
				}
			   connection.close();
			 } catch (Exception e) {
					e.printStackTrace();
				}
		}
		
	}

}
