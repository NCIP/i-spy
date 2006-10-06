package gov.nih.nci.ispy.ui.graphing.chart.plot;

import gov.nih.nci.caintegrator.application.graphing.PlotPoint;
import gov.nih.nci.caintegrator.enumeration.AxisType;
import gov.nih.nci.caintegrator.ui.graphing.data.DataRange;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.ui.graphing.data.ISPYPlotPoint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;

public class ISPYCorrelationScatterPlot {

	private Collection<ISPYPlotPoint> dataPoints;
	private JFreeChart corrChart = null;
	private String xLabel;
	private String yLabel;
	private Double corrValue;
	private NumberFormat nf = NumberFormat.getNumberInstance();
	private static final double glyphSize = 8.0;
	
	public ISPYCorrelationScatterPlot(Collection<ISPYPlotPoint> dataPoints, String xLabel, String yLabel, Double correlationValue) {
		this.dataPoints = dataPoints;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.corrValue = correlationValue;
		nf.setMinimumFractionDigits(1);
		createChart();
		
	}

	private void createChart() {
		
		String title = "Correlation Scatter Plot  correlationCoefficient=" + nf.format(corrValue);
		
		corrChart = ChartFactory.createScatterPlot(title,xLabel, yLabel, null,  PlotOrientation.VERTICAL,
	            true, 
	            true, 
	            false );
		
		XYPlot plot = (XYPlot) corrChart.getPlot();
		
		buildLegend();
		
	    plot.setNoDataMessage(null);
	    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
	    renderer.setToolTipGenerator(new StandardXYToolTipGenerator());
	    renderer.setUseOutlinePaint(true);
        plot.setRangeCrosshairVisible(false);
	    plot.setDomainCrosshairVisible(false);
	        
//	     XYShapeAnnotation annotation = new XYShapeAnnotation(new Rectangle2D.Double(25.0, 25.0, 5, 5));
//	        
//	     plot.addAnnotation(annotation);
	       
	        
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis  =	(NumberAxis) plot.getRangeAxis();
        
        
        //should determine axis range using datapoints.
        List<PlotPoint> plotPoints = new ArrayList<PlotPoint>(dataPoints);
        DataRange Xrange = PlotPoint.getDataRange(plotPoints, AxisType.X_AXIS );
        DataRange Yrange = PlotPoint.getDataRange(plotPoints, AxisType.Y_AXIS);
        
//        Double xAbsMax = Math.max(Math.abs(Xrange.getMaxRange()), Math.abs(Xrange.getMinRange()));
//        Double yAbsMax = Math.max(Math.abs(Yrange.getMaxRange()), Math.abs(Yrange.getMinRange()));
//        
//        Double maxAbsVal = Math.max(xAbsMax, yAbsMax);
           
        domainAxis.setAutoRangeIncludesZero(false);
         
        
        double xTick = 10.0;
        double yTick = 10.0;
        
        double xdist = Math.abs(Xrange.getMaxRange()-Xrange.getMinRange());
        double ydist = Math.abs(Yrange.getMaxRange()-Yrange.getMinRange());
        if (xdist < 10.0) {
          xTick = 1.0;
        }
        
        
        if (ydist < 10.0) {
          yTick = 1.0;
        }
        
//        if (maxAbsVal <= 25.0) {
//          tickUnit =5.0;
//        }
//        else if (maxAbsVal <= 50.0) {
//          tickUnit = 10.0;
//        }
        
        domainAxis.setTickUnit(new NumberTickUnit(xTick));
        rangeAxis.setTickUnit(new NumberTickUnit(yTick));
        
        //double glyphScaleFactor = (maxAbsVal*2.0)/600.0;   //assuming 600 pixels for the graph
        double xScale = xdist / 600.0;
        //double glyphScaleFactor = 1.0;
        double yScale = ydist / 600.0;
        
        
        //double adjAbsVal = Math.ceil(maxAbsVal + (glyphScaleFactor*8.0));
        
        //domainAxis.setRange(-maxAbsVal, maxAbsVal);
        double xMin = Xrange.getMinRange()-xScale*glyphSize;
        double xMax = Xrange.getMaxRange()+xScale*glyphSize;
        double yMin = Yrange.getMinRange()-yScale*glyphSize;
        double yMax = Yrange.getMaxRange()+yScale*glyphSize;
        
        domainAxis.setRange(xMin, xMax);
        
        //rangeAxis.setRange(-maxAbsVal, maxAbsVal);
        rangeAxis.setRange(yMin, yMax);
        
	    createGlyphsAndAddToPlot(plot, xScale, yScale); 	 
	    
	   // Paint p = new GradientPaint(0, 0, Color.white, 1000, 0, Color.green);
	    //try and match the UI e9e9e9
	    Paint p = new Color(233, 233, 233);
	    
	    corrChart.setBackgroundPaint(p);
		
		
	}

	private void buildLegend() {
	  LegendTitle legend = corrChart.getLegend();
	  LegendItemSource[] sources = new LegendItemSource[1];
	  CorrLegendItemSource legendSrc = new CorrLegendItemSource();
	  LegendItem item = null;
		    
	  //Rect=survival less than 10 months
	
	  
	 
	  GeneralPath downtriangle = new GeneralPath();
	  downtriangle.moveTo(-4.0f, -4.0f);
	  downtriangle.lineTo(4.0f, -4.0f);
	  downtriangle.lineTo(0.0f, 4.0f);
	  downtriangle.closePath();
	  item = new LegendItem("Tumor size reduced by 30% or more (MRI)", null, null, null, downtriangle, Color.BLACK);
	  legendSrc.addLegendItem(item);  
	  
	  item = new LegendItem("Tumor size reduced less than 30% or no change (MRI)", null, null, null,new Ellipse2D.Double(0,0,8,8) , Color.BLACK);
	  legendSrc.addLegendItem(item);  
	  
	  
	  GeneralPath uptriangle = new GeneralPath();
	  uptriangle.moveTo(0.0f, -4.0f);
	  uptriangle.lineTo(4.0f, 4.0f);
	  uptriangle.lineTo(-4.0f, 4.0f);
	  uptriangle.closePath();
	  item = new LegendItem("Tumor size increased (MRI)", null, null, null, uptriangle, Color.BLACK);
	  legendSrc.addLegendItem(item);
	  
	  
//	  if (colorBy == ISPYPCAcolorByType.CLINICALRESPONSE) {
//	    
//		for (ClinicalResponseType cr : ClinicalResponseType.values()) {
//		  item = new LegendItem(cr.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), cr.getColor());
//		  legendSrc.addLegendItem(item);
//		}
//
//	  }
//	  else if (colorBy == ISPYPCAcolorByType.DISEASESTAGE) {
//		  
//		  for (ClinicalStageType ds : ClinicalStageType.values()) {
//			if (!ds.name().endsWith("ALL")) {
//		      item = new LegendItem(ds.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), ds.getColor());
//		      legendSrc.addLegendItem(item);
//			}
//		  }
//	  }
//	  else if (colorBy == ISPYPCAcolorByType.TIMEPOINT) {
//		  for (TimepointType tp : TimepointType.values()) {
//			item = new LegendItem(tp.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), tp.getColor());
//			legendSrc.addLegendItem(item);
//		  }
//	  }
	  
	  sources[0] = legendSrc;
	  legend.setSources(sources);
		
	}

	private void createGlyphsAndAddToPlot(XYPlot plot, double xScale, double yScale) {
	  XYShapeAnnotation glyph;
	  Shape glyphShape = null;
	  Color glyphColor;
	  
	  //double glyphSize = 8.0;   //pixels
	  
	  double glyphIncrementX = (glyphSize * xScale)/2.0;
	  double glyphIncrementY = (glyphSize * yScale)/2.0;
	  float gi_x = (float) glyphIncrementX;
	  float gi_y = (float) glyphIncrementY;
	  
	  PatientData pd;
	  
	  double x, y;
	  Double mriPctChange = null;
	  for (ISPYPlotPoint corrPoint : dataPoints) {
	    
	    x = corrPoint.getX();
	    y = corrPoint.getY();
	     	   
	   mriPctChange = corrPoint.getMRITumorPctChange();
	     
	   if (mriPctChange == null) {
		      //data is missing
		      Rectangle2D.Double rect = new Rectangle2D.Double();
			  //rect.setFrameFromCenter(x,y, x+1.25,y+1.25);
		      rect.setFrameFromCenter(x,y, x+glyphIncrementX,y+glyphIncrementY);
			  glyphShape = rect;
		}
	    else if (mriPctChange <= -30.0) {

         //tumor shrank by more than 30% (down arrow)
         GeneralPath gp = new GeneralPath();
		 float xf = (float)x;
	     float yf = (float)y;
	     
	      //make a triangle
	     gp.moveTo(xf,yf);
	     gp.lineTo(xf-gi_x,yf+gi_y);
	     gp.lineTo(xf+gi_x,yf+gi_y);
	     gp.closePath();
	     glyphShape = gp;
	    }
	    else if (mriPctChange > 0.0) {
	      //tumor size increased (up arrow)

	      GeneralPath gp = new GeneralPath();
		  float xf = (float)x;
		  float yf = (float)y;
	      //make a triangle
	      gp.moveTo(xf,yf);
	      gp.lineTo(xf+gi_x,yf-gi_y);
	      gp.lineTo(xf-gi_x,yf-gi_y);
	      gp.closePath();
	      glyphShape = gp;
	      	
//		      Ellipse2D.Double circle = new Ellipse2D.Double();
//		      circle.setFrameFromCenter(x,y, x+2, y+2);
	      
	    }
	    else if ((mriPctChange > -30.0) && (mriPctChange <= 0.0)) {
	      //no change or reduction in tumor size but less than 30% reduction
	      Ellipse2D.Double circle = new Ellipse2D.Double();
	      //circle.setFrameFromCenter(x,y,x+1.25,y+1.25);
	      circle.setFrameFromCenter(x,y,x+gi_x,y+gi_y);
	      glyphShape = circle;	
	    	
	    }
	   
	    
	    glyphColor = Color.BLUE; 
	    //later can set color based on 
	    
	    glyph = new XYShapeAnnotation(glyphShape, new BasicStroke(1.0f), Color.BLACK, glyphColor);
           
            
        String tooltip = corrPoint.getTag();
        
    	
        glyph.setToolTipText(tooltip);
	    plot.addAnnotation(glyph);
	  }
		
	}
	
	public JFreeChart getChart() {
		return corrChart;
	}
	
	private class CorrLegendItemSource implements LegendItemSource {

		private LegendItemCollection items = new LegendItemCollection();
		
		public void addLegendItem(LegendItem item) {
		  items.add(item); 
		}
		
		public LegendItemCollection getLegendItems() {
			return items;
		}
		
	}
	
	

}
