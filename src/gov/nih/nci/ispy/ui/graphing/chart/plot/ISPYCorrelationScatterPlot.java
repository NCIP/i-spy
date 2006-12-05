package gov.nih.nci.ispy.ui.graphing.chart.plot;

import gov.nih.nci.caintegrator.application.graphing.PlotPoint;
import gov.nih.nci.caintegrator.domain.annotation.protein.bean.ProteinBiomarker;
import gov.nih.nci.caintegrator.domain.finding.bean.Finding;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.IHCFinding;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LevelOfExpressionIHCFinding;
import gov.nih.nci.caintegrator.domain.finding.protein.ihc.bean.LossOfExpressionIHCFinding;
import gov.nih.nci.caintegrator.domain.study.bean.Specimen;
import gov.nih.nci.caintegrator.enumeration.AxisType;
import gov.nih.nci.caintegrator.ui.graphing.data.DataRange;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.clinical.ContinuousType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.common.TimepointType;
import gov.nih.nci.ispy.service.ihc.LevelOfExpressionIHCService;
import gov.nih.nci.ispy.service.ihc.LossOfExpressionIHCService;
import gov.nih.nci.ispy.ui.graphing.data.ISPYPlotPoint;
import java.util.Collections;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
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
	private ContinuousType ct1;
	private ContinuousType ct2;
	private Double corrValue;
	private NumberFormat nf = NumberFormat.getNumberInstance();
	private static final double glyphSize = 8.0;
	private ColorByType colorBy = ColorByType.CLINICALRESPONSE;
	private static Logger logger = Logger.getLogger(ISPYCorrelationScatterPlot.class);	
	private Map<String, List<IHCFinding>> ihcData;
	
	
	public ISPYCorrelationScatterPlot(Collection<ISPYPlotPoint> dataPoints, String xLabel, String yLabel, ContinuousType contType1, ContinuousType contType2, Double correlationValue, ColorByType colorBy) {
		this.dataPoints = dataPoints;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.ct1 = contType1;
		this.ct2 = contType2;
		this.corrValue = correlationValue;
		this.colorBy = colorBy;
		nf.setMinimumFractionDigits(1);
		createChart();
		
	}

	private void createChart() {
		
		String title = "Correlation Scatter Plot  correlationCoefficient=" + nf.format(corrValue) + " N=" + dataPoints.size();
		
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
        Set<SampleInfo> infoSet = new HashSet<SampleInfo>();
        
        if ((colorBy == ColorByType.IHC_EXPRESSION_X) || (colorBy == ColorByType.IHC_EXPRESSION_Y)) {
          String geneName = null;
          if ((colorBy == ColorByType.IHC_EXPRESSION_X)&&(ct1 != ContinuousType.GENE)) {
        	 logger.info("User attempted to color by ihc x on non gene continuous type.");
        	 //need to show validation error
        	 return;
          }
          else if ((colorBy == ColorByType.IHC_EXPRESSION_Y)&&(ct2 != ContinuousType.GENE)) {
        	  logger.info("User attempted to color by ihc y on non gene continuous type.");
        	  //need to show validation error
        	  return;
          }
          else {
        	 if (colorBy == ColorByType.IHC_EXPRESSION_X) {
        	   //parse the gene name from the xLabel
        	   geneName = xLabel.substring(0,xLabel.indexOf('_'));
        	 }
        	 else if (colorBy == ColorByType.IHC_EXPRESSION_Y) {
        	   //parse the gene name from the yLabel
        	   geneName = yLabel.substring(0, yLabel.indexOf('_'));
        	 }
          }
        	
          //Get the IHC data for the samples to be graphed	
          LevelOfExpressionIHCService loeService = LevelOfExpressionIHCService.getInstance();
          LossOfExpressionIHCService lossService = LossOfExpressionIHCService.getInstance();
          Set<String> sampleIds = new HashSet<String>();
          String labtrackId;
          SampleInfo info;          
          for (ISPYPlotPoint corrPoint : dataPoints) {
        	  info = corrPoint.getSampleInfo();
        	  if (info != null) {
        		  labtrackId = corrPoint.getSampleInfo().getLabtrackId();
            	  sampleIds.add(labtrackId);
                  infoSet.add(info);
        	  }
        	  else {
        	      logger.warn("Point id=" + corrPoint.getId() + " has no sample info. Skipping point");
        	  }
          }
          
          Collection<? extends Finding> loeFindings = loeService.getFindingsFromSampleInfo(infoSet);
          Collection<? extends Finding> lossFindings = lossService.getFindingsFromSampleInfo(infoSet);
          
          List<IHCFinding> findings = new ArrayList<IHCFinding>();
          
          for (Finding f : loeFindings) {
          	findings.add((IHCFinding) f);
          }
          
          for (Finding f : lossFindings) {
            findings.add((IHCFinding) f);
          }
          
//        Need to handle mapping names to IHC biomarker names.
     	 //EGFR (ok), FAK (PTK2), HER2 (ERBB2), Ki-67, P53, bcl2 (ok), p27
     	 //Translate the gene names into the biomarker names 
     	 //used by ihc. This code should be removed once protein biomarker
     	 //alias is implmented.
     	 String ihcBiomarker = geneName;
     	 if (geneName.equalsIgnoreCase("PTK2")) {
     	   ihcBiomarker = "FAK";
     	 }
     	 else if (geneName.equalsIgnoreCase("ERBB2")) {
     	   ihcBiomarker = "HER2";
     	 }
     	 else if (geneName.equalsIgnoreCase("MKI67")) {
     	   //need to check this one
     	   ihcBiomarker = "Ki-67";
     	 }
     	 else if (geneName.equalsIgnoreCase("TP53")) {
     	   ihcBiomarker = "P53";	 
     	 }
     	 else if (geneName.equalsIgnoreCase("Cdkn1b")) {
     	   ihcBiomarker = "P27";
     	 }
          
          List<IHCFinding> filteredList = getIHCFindingsForBiomarker(findings, ihcBiomarker);
          
          //TEST Case
//          Set<String> testIds = new HashSet<String>();
//          testIds.add("212833");
//          testIds.add("213152");
          
          //Collection<? extends Finding> loeFindings = loeService.getFindingsFromSampleIds(testIds);
          
          
          //Collection<? extends Finding> lossFindings = lossService.getFindingsFromSampleIds(sampleIds);
          
          ihcData = new HashMap<String, List<IHCFinding>>();
          IHCFinding loeFinding;
          List<IHCFinding> findingList;
          Specimen specimen = null;
          String patientDID = null;
          for (Finding finding : filteredList) {
        	loeFinding = (IHCFinding) finding;
        	specimen = loeFinding.getSpecimen();
        	if ((specimen != null) && (specimen.getPatientDID()!=null)) {
        	  patientDID = specimen.getPatientDID();
        	  findingList = ihcData.get(patientDID);
          	
	          if (findingList == null) {
	          	  findingList = new ArrayList<IHCFinding>();
	          	  ihcData.put(patientDID, findingList);
	          }
	          	
	          findingList.add(loeFinding);
        	}
        	else {
              logger.warn("loeFinding id=" + loeFinding.getId() + " has null specimen or patientDID. Skipping..");
        	}
        	
        	
          }
//          IHCFinding lossFinding;
//          for (Finding finding : lossFindings) {
//        	lossFinding = (IHCFinding) finding;
//            ihcData.put(lossFinding.getId(), lossFinding);
//          }
          
        }
        
        
	    createGlyphsAndAddToPlot(plot, xScale, yScale); 	 
	    
	   // Paint p = new GradientPaint(0, 0, Color.white, 1000, 0, Color.green);
	    //try and match the UI e9e9e9
	    Paint p = new Color(233, 233, 233);
	    
	    corrChart.setBackgroundPaint(p);
		
		
	}
	
	/**
	 * 
	 * @param ihcFindings
	 * @param biomarkerName
	 * @return
	 */
	private List<IHCFinding> getIHCFindingsForBiomarker(Collection<? extends Finding> ihcFindings, String biomarkerName) {
	    String name = null;
	    ProteinBiomarker protBM = null;
	    IHCFinding ihcFinding;
	    List<IHCFinding> returnList = new ArrayList<IHCFinding>();
	    
		for (Finding finding : ihcFindings) {
		  ihcFinding = (IHCFinding) finding;
		  protBM = ihcFinding.getProteinBiomarker();
		  name = protBM.getName();
		  if (name.equalsIgnoreCase(biomarkerName)) {
		    returnList.add(ihcFinding);
		  }
		}
		
		return returnList;
		
		
		
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
	  
	  
	 
	  item = new LegendItem("Tumor size change N/A", null, null, null, new Rectangle2D.Double(0,0,8,8), Color.BLACK);
	  legendSrc.addLegendItem(item);
	  
	  if (colorBy == ColorByType.CLINICALRESPONSE) {
	    
		for (ClinicalResponseType cr : ClinicalResponseType.values()) {
		  item = new LegendItem(cr.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), cr.getColor());
		  legendSrc.addLegendItem(item);
		}

	  }
	  else if (colorBy == ColorByType.DISEASESTAGE) {
		  
		  for (ClinicalStageType ds : ClinicalStageType.values()) {
			if (!ds.name().endsWith("ALL")) {
		      item = new LegendItem(ds.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), ds.getColor());
		      legendSrc.addLegendItem(item);
			}
		  }
	  }
	  else if (colorBy == ColorByType.TIMEPOINT) {
		  for (TimepointType tp : TimepointType.values()) {
			item = new LegendItem(tp.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), tp.getColor());
			legendSrc.addLegendItem(item);
		  }
	  }
	  else if ((colorBy == ColorByType.IHC_EXPRESSION_X) || (colorBy == ColorByType.IHC_EXPRESSION_Y)) {
	      for (CorrScatterColorByIHCType ihcType : CorrScatterColorByIHCType.values()) {
	    	item = new LegendItem(ihcType.toString(), null, null, null, new Line2D.Double(0,0,6,6), new BasicStroke(3.0f), ihcType.getColor());
			legendSrc.addLegendItem(item);  
	      }
	  }
	  
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
	   
	    
	    //glyphColor = Color.BLUE; 
	    //later can set color based on 
	   glyphColor = getColorForDataPoint(corrPoint);
	    
	    glyph = new XYShapeAnnotation(glyphShape, new BasicStroke(1.0f), Color.BLACK, glyphColor);
           
            
        String tooltip = corrPoint.getTag();
        
    	
        glyph.setToolTipText(tooltip);
	    plot.addAnnotation(glyph);
	  }
		
	}
	
	private Color getColorForDataPoint(ISPYPlotPoint plotPoint) {
	  Color defaultColor = Color.GRAY;
	  Color retColor = null;
	  ClinicalResponseType clinResp =null;
	  ClinicalStageType clinStage = null;
	  TimepointType timepoint = null;
	  PatientData pd = plotPoint.getPatientData();
	  
	  if (pd == null) {return defaultColor; }
	  
	  if (colorBy == ColorByType.CLINICALRESPONSE) {
		clinResp = plotPoint.getClinicalResponse();
		if (clinResp != null) {
		  retColor = clinResp.getColor();
		}
	  }
	  else if (colorBy == ColorByType.DISEASESTAGE) {
		clinStage = plotPoint.getClinicalStage();
		if (clinStage != null) {
	      retColor = clinStage.getColor();
		}
	  }
	  else if (colorBy == ColorByType.TIMEPOINT) {
	    timepoint = plotPoint.getTimepoint();
	    if (timepoint != null) {
	      retColor = timepoint.getColor();
	    }
	  }
	  else if ((colorBy == ColorByType.IHC_EXPRESSION_X) || (colorBy == ColorByType.IHC_EXPRESSION_Y) ) {
	    SampleInfo info = plotPoint.getSampleInfo();
//	    //get the IHC expression for the point
//	    
//	    String labtrakId = info.getLabtrackId();
//	    
//	    logger.info("geting ihc info for labtrakId=" + labtrakId);
	    
		String patientDID = info.getISPYId();
		String tp = info.getTimepoint().name();
		
		List<IHCFinding> ihcfList = ihcData.get(patientDID);
		IHCFinding theFinding = null;
		if (ihcfList != null) {
			//the list could be null if there are no ihc findings for the patientDID
		    for (IHCFinding ihcf : ihcfList) {
		      if (ihcf.getSpecimen().getTimePoint().equalsIgnoreCase(tp)) {
		        theFinding = ihcf;
		        break;
		      }
		    }
		    
		    if (theFinding != null) {
		      //figure out the color to use
		      if (theFinding instanceof LossOfExpressionIHCFinding) {
		    	  LossOfExpressionIHCFinding loef = (LossOfExpressionIHCFinding) theFinding;
		    	  @SuppressWarnings("unused") String result = loef.getLossResult();
		    	  retColor = getColorForIHCLossResult(result);
		    	  
		      }
		      else if (theFinding instanceof LevelOfExpressionIHCFinding) {
		    	  LevelOfExpressionIHCFinding lossf = (LevelOfExpressionIHCFinding) theFinding;
		    	  @SuppressWarnings("unused") String result = lossf.getOverallExpression();
		    	  retColor = getColorForIHCLevelResult(result);
		      }
		    }
		}
		else {
		  logger.info("PatientDID: " + patientDID + " has no ihc findings. Using default color.");
		}
	  }
	  
	  if (retColor == null) {
	    retColor = defaultColor;
	  }
	  
	  return retColor;
	}
	
	public JFreeChart getChart() {
		return corrChart;
	}
	
	private Color getColorForIHCLevelResult(String result) {

		//OVEREXPRESSED,POSITIVE,HIGH
		String res = result.trim();
		
		if (res.equalsIgnoreCase("OVEREXPRESSED") ||
		    res.equalsIgnoreCase("POSITIVE") ||
			res.equalsIgnoreCase("HIGH")) { 
		  return Color.GREEN;
		}
		
		
		//INTERMEDIATE,BORDERLINE,LOW,WEAK OVEREXPRESSED
		if (res.equalsIgnoreCase("INTERMEDIATE") ||
			    res.equalsIgnoreCase("BORDERLINE") ||
				res.equalsIgnoreCase("LOW") ||
				res.equalsIgnoreCase("WEAK OVEREXPRESSED")) { 
			  return Color.YELLOW;
		}
		
		
		//NEGATIVE,NO OVEREXPRESSION, ZERO
		if (res.equalsIgnoreCase("NEGATIVE") ||
			    res.equalsIgnoreCase("NO OVEREXPRESSION") ||
				res.equalsIgnoreCase("ZERO")) { 
			  return Color.RED;
		}
		
		
		return Color.GRAY;
	}
	
	private Color getColorForIHCLossResult(String result) {

		String res = result.trim();
		
		//NO LOSS
		if (res.equalsIgnoreCase("NO LOSS")) {
		  return Color.GREEN;
		}
		
		if (res.equalsIgnoreCase("PARTIAL LOSS")) {
		  return Color.YELLOW;
		}
		//PARTIAL LOSS
		
		//TOTAL LOSS
		if (res.equalsIgnoreCase("TOTAL LOSS")) {
		  return Color.BLUE;
		}
		
		return Color.GRAY;
		
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
