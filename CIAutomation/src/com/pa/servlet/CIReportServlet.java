package com.pa.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.pa.ci.CIReport;
import com.pa.dao.ReportDao;
import com.pa.util.ReportConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class CIReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(CIReportServlet.class);
	private static int count = 0;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String imgsrc = null;
		String date = request.getParameter(ReportConstants.DATE);
		String projects = request.getParameter(ReportConstants.PROJECTS);
		String selectedProject = "";
		String selecteddate = "";

		if (null != projects || null != date) {
			CIReport cireport = new CIReport("");
			if (null != projects && !(projects.equals(""))) {
				String[] array = projects.split(",");
				for (int i = 0; i < array.length; i++) {
					if (i == 0) {
						selectedProject = "'" + array[i] + "'";
					} else {
						selectedProject += ",'" + array[i] + "'";
					}
				}

			}
			if (null != date) {
				String[] date1 = date.split("-");
				selecteddate = date1[2] + "/" + date1[0] + "/" + date1[1];

			}
			log.info("selectedProject:::" + selectedProject);
			log.info("selecteddate:::" + selecteddate);
			if (null != selectedProject && !(selectedProject.equals(""))) {

				response.reset();
				response.setContentType("text/JSON");
				response.setCharacterEncoding("UTF-8");
				JSONObject o = new JSONObject();
				try {
					ReportDao reportDao = new ReportDao();
					reportDao.updateStatus(selectedProject);
					o.put("message",ReportConstants.SUCCESS_MSG);

				} catch (Exception e) {
					log.info("Failed to update");
					o.put("message", ReportConstants.FAILURE_MSG);

				}
				response.getWriter().print(o);
			}

			if (null != date && !(date.equals(""))) {
				JSONObject o = new JSONObject();
				response.reset();
				response.setContentType("text/JSON");
				response.setCharacterEncoding("UTF-8");
				try {
					CIReport.main(selecteddate);
				//	imgsrc = "file?path=C:\\Image\\CIReport.jpeg&count="+count;
					imgsrc=ReportConstants.FILE_PATH+"&count="+count;
					count++;
					o.put("img", imgsrc);
					response.getWriter().print(o);
				} catch (Exception e) {
					log.info("Failed to Generate Report");
				}
			}
		}

	}

}
