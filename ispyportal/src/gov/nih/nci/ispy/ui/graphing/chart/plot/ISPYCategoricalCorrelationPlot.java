/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.ui.graphing.chart.plot;

import gov.nih.nci.caintegrator.analysis.messaging.DataPointVector;
import gov.nih.nci.caintegrator.analysis.messaging.ReporterInfo;
import gov.nih.nci.ispy.service.clinical.ContinuousType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import gov.nih.nci.caintegrator.ui.graphing.chart.plot.BoxAndWhiskerCoinPlotRenderer;
import gov.nih.nci.caintegrator.ui.graphing.chart.plot.FaroutOutlierBoxAndWhiskerCalculator;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class ISPYCategoricalCorrelationPlot {

	private Collection<DataPointVector> categoryData;
	private JFreeChart catCorrChart = null;
	private String xLabel;
	private String yLabel;
	private ContinuousType continuousType;
	private NumberFormat nf = NumberFormat.getNumberInstance();
	private static final double glyphSize = 8.0;
	private ColorByType colorBy = ColorByType.CLINICALRESPONSE;
	private List<ReporterInfo> reporterInfoList;
	
	public ISPYCategoricalCorrelationPlot(List<DataPointVector> categoryData, List<ReporterInfo> reporterInfoList, String xLabel, String yLabel, ContinuousType contType, ColorByType colorBy) {
		this.categoryData = categoryData;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.continuousType = contType;
		this.colorBy = colorBy;
		this.reporterInfoList = reporterInfoList;
		nf.setMinimumFractionDigits(1);
		createChart();		
	}

	private void createChart() {
		
		String title = "Categorical Plot";
		
		CategoryDataset chartData = createChartData();
		
		CategoryAxis domainAxis = new CategoryAxis(null);
		NumberAxis rangeAxis = new NumberAxis(yLabel);
		//BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		
		BoxAndWhiskerCoinPlotRenderer renderer = new BoxAndWhiskerCoinPlotRenderer();
		renderer.setDisplayAllOutliers(true);
		CategoryPlot plot = new CategoryPlot(chartData, domainAxis, rangeAxis, renderer);
		
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        45.0 * Math.PI / 180.0));
		catCorrChart = new JFreeChart(title, plot);
		catCorrChart.removeLegend();
	    //renderer.setFillBox(false);
	    String cat =(String) chartData.getColumnKey(0);
	   	    
	    //renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	    renderer.setToolTipGenerator(new CategoryToolTipGenerator() { 
		public String generateToolTip(CategoryDataset dataset,int series, int item) {
			String tt="";
			NumberFormat formatter = new DecimalFormat(".####");
			String key = "";
		    //String s = formatter.format(-1234.567);  // -001235
		    if(dataset instanceof DefaultBoxAndWhiskerCategoryDataset){
			    DefaultBoxAndWhiskerCategoryDataset ds = (DefaultBoxAndWhiskerCategoryDataset)dataset;
			    try	{
					String med = formatter.format(ds.getMedianValue(series, item));
					tt += "Median: " + med + "<br/>";
					tt += "Mean: " + formatter.format(ds.getMeanValue(series, item))+"<br/>";
					tt += "Q1: " + formatter.format(ds.getQ1Value(series, item))+"<br/>";
					tt += "Q3: " + formatter.format(ds.getQ3Value(series, item))+"<br/>";
					tt += "Max: " + formatter.format(
							FaroutOutlierBoxAndWhiskerCalculator.getMaxFaroutOutlier(ds,series, item))+"<br/>";
					tt += "Min: " + formatter.format(
							FaroutOutlierBoxAndWhiskerCalculator.getMinFaroutOutlier(ds,series, item))+"<br/>";
					//tt += "<br/><br/>Please click on the box and whisker to view a plot for this reporter.<br/>";
					//tt += "X: " + ds.getValue(series, item).toString()+"<br/>";
					//tt += "<br/><a href=\\\'#\\\' id=\\\'"+ds.getRowKeys().get(series)+"\\\' onclick=\\\'alert(this.id);return false;\\\'>"+ds.getRowKeys().get(series)+" plot</a><br/><br/>";
					key = ds.getRowKeys().get(series).toString();
			    }
			    catch(Exception e) {}
		    }
		    
			return  tt;						}

	});
	    
	    
//        renderer.setToolTipGenerator(new CategoryToolTipGenerator() {
//        	
//			public String generateToolTip(CategoryDataset dataset,int series, int item) {
//				String tt="";
//				NumberFormat formatter = new DecimalFormat(".####");
//				String key = "";
//			    //String s = formatter.format(-1234.567);  // -001235
//				StringBuffer sb = new StringBuffer();
//			    if(dataset instanceof DefaultBoxAndWhiskerCategoryDataset){
//			    	DefaultBoxAndWhiskerCategoryDataset ds = (DefaultBoxAndWhiskerCategoryDataset)dataset;
//				    try	{
//				    	
//				    	String str;
//				    	str = (String) ds.getColumnKey(item);
//				    	sb.append(str).append(" N=");
//				    	BoxAndWhiskerItem bwitem = ds.getItem(series,item);
//				    	
//						str = formatter.format(ds.getMedianValue(series, item));
//						sb.append("Median: ").append(str);
//						str = formatter.format(ds.getMeanValue(series, item));
//						sb.append(" Mean: ").append(str);
//						str = formatter.format(ds.getMinRegularValue(series, item));
//						sb.append(" Min: ").append(str);
//						str = formatter.format(ds.getMaxRegularValue(series, item));
//						sb.append(" Max: ").append(str);
//						str = formatter.format(ds.getQ1Value(series, item));
//						sb.append(" Q1: ").append(str);	
//						str = formatter.format(ds.getQ3Value(series, item));
//						sb.append(" Q3: ").append(str);
//						
//				    }
//				    catch(Exception e) {}
//			    }
//			    
//				return sb.toString();
//				
//			}
//
//		});
		
	    plot.setNoDataMessage(null);	
	}
	
	public JFreeChart getChart() {
		return catCorrChart;
	}
	
	private List<Double> removeNaNandNulls(List<Double> inputList) {
	  List<Double> dList = new ArrayList<Double>();
	  for (Double d : inputList) {
	    if ((d != null) && (!d.isNaN())) {
	      dList.add(d);
	    }
	  }
	  return dList;
	}

	private DefaultBoxAndWhiskerCategoryDataset createChartData() {
		
		DefaultBoxAndWhiskerCategoryDataset dataSet 
        = new DefaultBoxAndWhiskerCategoryDataset();
		
		
		int i=0;
		ReporterInfo ri;
		String sequenceName = "";
		for (DataPointVector vec : categoryData) {
			if ((reporterInfoList!=null)&&(!reporterInfoList.isEmpty())) {
			  ri = reporterInfoList.get(i);
			  if (ri != null) {
			     sequenceName = ri.getGeneSymbol() + " " + ri.getReporterName();
			  }
			}
			//only using the X component of the vector to hold the data y and z are not used.
			List<Double> values = removeNaNandNulls(vec.getXValues());
			String categoryName = vec.getName() + " N=" + values.size();
			dataSet.add(values,sequenceName,categoryName);
			
		}
		return dataSet;
	}


	
	
}
