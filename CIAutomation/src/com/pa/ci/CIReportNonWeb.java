package com.pa.ci;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.StandardGradientPaintTransformer;


import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

import com.pa.util.DBConnection;
import com.pa.util.ReportConstants;

/**
 * A simple demonstration application showing how to create a line chart using data from a
 * {@link CategoryDataset}.
 */
public class CIReportNonWeb extends ApplicationFrame {

	static class CustomBarRenderer extends BarRenderer
	{

		private Paint colors[];

		public Paint getItemPaint(int i, int j)
		{
			return colors[j % colors.length];
		}

		public CustomBarRenderer(Paint apaint[])
		{
			colors = apaint;
		}
	}

	
	
	public CIReportNonWeb(String title){
		super(title);
	}

    

	/**
     * Creates a sample dataset.
     * 
     * @return The dataset.
     */
    public static CategoryDataset createDataset(String Date) {
        
        // row keys...
        final String series1 =ReportConstants.FIRST;
        final String series2 = ReportConstants.SECOND;

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
        DBConnection dbConn =null;    
        Connection conn = null;
        Statement stmt = null;
       
        
       try{            
         
            
            String sql=null;
            dbConn = new DBConnection();
            conn = dbConn.getConnection();
            stmt = conn.createStatement();
            /*if(null!= selectedProject && !(selectedProject.equals("")) )
            {
            sql = "select project,cicount from pmd_report where build_rundate in (select max(build_rundate) from pmd_report where cicount is not null group by project)" 
                    +"and project in ("+selectedProject +")";
             }else*/ 
            	 if(null!= Date && !(Date.equals("")))
             {
            	 sql = "select project,cicount from pmd_report where build_rundate in (select max(build_rundate) from pmd_report where cicount is not null group by project)" 
                        + "and substr(build_rundate,"+ ReportConstants.START_INDEX+","+ReportConstants.END_INDEX +")='"+Date+"'";
             }
            ResultSet rs = stmt.executeQuery(sql);
            String CIProjectName=null;
            float CICount;
            while (rs.next()){
            	CIProjectName= rs.getString(ReportConstants.PROJECT);
            	CICount=rs.getFloat(ReportConstants.CICOUNT);
            	dataset.addValue(CICount, series1,CIProjectName);
            }
            
        }catch(SQLException e){
        	System.out.println("SQL Exception "+ e.toString());
        }catch (Exception e) {
			e.printStackTrace();
		}

        return dataset;
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    public static JFreeChart createChart(final CategoryDataset dataset) {
        
    	 SimpleDateFormat sdfDate = new SimpleDateFormat(ReportConstants.DATE_FORMAT);//dd/MM/yyyy
 	    Date now = new Date();
 	    String strDate = sdfDate.format(now);
 	    String reportName =new StringBuffer("Continous Integration Report").append(strDate).toString();
        final JFreeChart chart = ChartFactory.createLineChart(
        	reportName,       // chart title
            "Project",                    // domain axis label
            "Violations",                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            false,                      // include legend
            true,                      // tooltips
            false                      // urls
        );


        chart.setBackgroundPaint(Color.WHITE);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        plot.setNoDataMessage(ReportConstants.NO_DATA);
        plot.setBackgroundPaint(null);
        plot.setInsets(new RectangleInsets(10D, 5D, 5D, 5D));
        plot.setOutlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.gray);
        plot.setRangeGridlineStroke(new BasicStroke(1.0F));
		Paint apaint[] = createPaint();
		CustomBarRenderer custombarrenderer = new CustomBarRenderer(apaint);
		custombarrenderer.setBarPainter(new StandardBarPainter());
		custombarrenderer.setDrawBarOutline(true);
		custombarrenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
		//plot.setRenderer(custombarrenderer);
        
        
        plot.setBackgroundPaint(Color.lightGray);
               
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.14999999999999999D);

        CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
      
   
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);
            plot.setRenderer(1,custombarrenderer);
            plot.setDataset(1,dataset);

        return chart;
    }
    
    
    private static Paint[] createPaint()
	{
		Paint apaint[] = new Paint[11];
		apaint[0] = new GradientPaint(0.0F, 0.0F, Color.gray, 0.0F, 0.0F, Color.gray);
		apaint[1] = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, Color.green);
		apaint[2] = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, Color.blue);
		apaint[3] = new GradientPaint(0.0F, 0.0F, Color.orange, 0.0F, 0.0F, Color.orange);
		apaint[4] = new GradientPaint(0.0F, 0.0F, Color.magenta, 0.0F, 0.0F, Color.magenta);
		apaint[5] = new GradientPaint(0.0F, 0.0F, Color.cyan, 0.0F, 0.0F, Color.cyan);
		apaint[6] = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, Color.red);
		apaint[7] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.white);
		apaint[8] = new GradientPaint(0.0F, 0.0F, Color.pink, 0.0F, 0.0F, Color.pink);
		apaint[9] = new GradientPaint(0.0F, 0.0F, Color.yellow, 0.0F, 0.0F, Color.yellow);
		apaint[10] = new GradientPaint(0.0F, 0.0F, Color.darkGray, 0.0F, 0.0F, Color.darkGray);
		return apaint;
	}
    /**
     * Starting point for the demonstration application.
     *
     * @param selectedProject  ignored.
     */
    public static void main() {

              int width = ReportConstants.WIDTH; /* Width of the image */
    	      int height = ReportConstants.HEIGHT; /* Height of the image */ 
      	      File ciReport = new File(ReportConstants.HUDSON_CIREPORT_PATH); 
       	      SimpleDateFormat sdfDate = new SimpleDateFormat(ReportConstants.DATE_FORMAT);//dd/MM/yyyy
    	 	  Date now = new Date();
    	 	  String strDate = sdfDate.format(now);
    	 	  String reportName =new StringBuffer("ContinousIntegrationReport").append(strDate).toString();
    	 	try{   
       	 	  CategoryDataset dataset = createDataset(now.toString());
    	      JFreeChart chart = createChart(dataset);
    	 	  CIReport demo = new CIReport("Line Chart Demo");
              ChartUtilities.saveChartAsJPEG(ciReport,chart,width,height);
       	 	}catch(Exception e){
    	 		System.out.println("Exception While creating the Image "+ e.toString());
    	 	}
    }

}
