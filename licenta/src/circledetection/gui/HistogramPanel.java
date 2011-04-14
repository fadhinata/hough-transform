package circledetection.gui;

import java.awt.Color;
import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.XYDataset;



@SuppressWarnings("serial")
public class HistogramPanel extends JPanel{

	private PlanarImage img;

	// Retrieves a histogram for the image.
   public HistogramPanel(PlanarImage img) {
	this.img = img;
	init();
}
	
	private Histogram createHistogram() {
	    int[] bins = {256, 256, 256};             // The number of bins.
	     double[] low = {0.0D, 0.0D, 0.0D};        // The low value.
	     double[] high = {256.0D, 256.0D, 256.0D}; // The high value.

	     // Construct the Histogram object.
	     Histogram hist = new Histogram(bins, low, high);

	     // Create the parameter block.
	     ParameterBlock pb = new ParameterBlock();
	     pb.addSource(img);               // Specify the source image
	                         // Specify the histogram
	     pb.add(null);                      // No ROI
	     pb.add(1);                         // Sampling
	     pb.add(1);                         // periods

	     // Perform the histogram operation.
	     PlanarImage dst = (PlanarImage)JAI.create("histogram", pb, null);

	     // Retrieve the histogram data.
	     hist = (Histogram) dst.getProperty("histogram");
	 
		Raster raster = img.getExtendedData(img.getBounds(), BorderExtender.createInstance(BorderExtender.BORDER_WRAP));
	     hist.countPixels(raster,null,0, 0,255,1);
	     return hist;
//	     // Print 3-band histogram.
//	     for (int i=0; i< hist.getNumBins().length; i++) {
//	        System.out.println(hist.getBinSize(0, i) + " ");
//	     }
	    
	}
	
	private JFreeChart createPlot()
	{
		Histogram hist = createHistogram();
		int[] value;
		value = hist.getBins()[0];

		final double[][] data = new double[6][value.length] ;
		for(int i=0; i<value.length; i++)
		{
			data[0][i]= value[i];
		}
		

		final CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				"", "", data);

		final JFreeChart chart = ChartFactory.createAreaChart("", // chart title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips
				false // urls
				);


    chart.setBackgroundPaint(Color.white);

    final CategoryPlot plot = chart.getCategoryPlot();
    plot.setForegroundAlpha(0.5f);
    
    plot.setBackgroundPaint(Color.lightGray);
    plot.setDomainGridlinesVisible(true);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinesVisible(true);
    plot.setRangeGridlinePaint(Color.white);
    
    final CategoryAxis domainAxis = plot.getDomainAxis();
    
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
    // OPTIONAL CUSTOMISATION COMPLETED.
    
    return chart;
	}
	
	public void init()
	{
		JFreeChart chart =createPlot(); 
		ChartPanel chartPanel = new ChartPanel(chart,250,250,200,200,200,200,false,false,false,false,true,true);
		chartPanel.setSize(200,200);
		chartPanel.setVisible(true);
		this.add(chartPanel);
	}

	

	     


}
