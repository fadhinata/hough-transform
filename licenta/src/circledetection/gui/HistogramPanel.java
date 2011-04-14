package circledetection.gui;

import java.awt.Color;
import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;


@SuppressWarnings("serial")
public class HistogramPanel extends JPanel{

	private PlanarImage img;
	private RenderedOp nm;
	private RenderedOp eq;
	private PlanarImage dst;

	// Retrieves a histogram for the image.
   public HistogramPanel(PlanarImage img) {
	this.img = img;
}
	
	public RenderedOp getNm() {
	return nm;
}

public RenderedOp getEq() {
	return eq;
}

	private Histogram createHistogram(PlanarImage img) {
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
	     dst = (PlanarImage)JAI.create("histogram", pb, null);

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
	
	private JFreeChart createPlot(PlanarImage img)
	{
		Histogram hist = createHistogram(img);
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
    domainAxis.setVisible(false);
    
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    rangeAxis.setAutoRangeIncludesZero(true);
    // OPTIONAL CUSTOMISATION COMPLETED.
    
    return chart;
	}
	
	public void histogramChart()
	{
		JFreeChart chart =createPlot(img); 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setSize(250,250);
		chartPanel.setVisible(true);
		this.add(chartPanel);
	}
	public void histogramEqChart()
	{
		equalizeHistogram();
		JFreeChart chart =createPlot(eq); 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setSize(250,250);
		chartPanel.setVisible(true);
		this.add(chartPanel);
	}
	public void histogramNmChart()
	{
		normalizeHistogram();
		JFreeChart chart =createPlot(nm); 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setSize(250,250);
		chartPanel.setVisible(true);
		this.add(chartPanel);
	}
	
	private void equalizeHistogram(){
	     // Create an equalization CDF.
		Histogram hist = createHistogram(img);
	     float[][] CDFeq = new float[hist.getNumBands()][];
	     for(int b = 0; b < hist.getNumBands(); b++) {
	         CDFeq[b] = new float[hist.getNumBins(b)];
	         for(int i = 0; i < hist.getNumBins(b); i++) {
	             CDFeq[b][i] = (float)(i+1)/(float)hist.getNumBins(b);
	         }
	     }

	  
	     // Create a histogram-equalized image.
	   eq = JAI.create("matchcdf", dst, CDFeq);

	    

	}
	private void normalizeHistogram()
	{
		Histogram hist = createHistogram(img);
		   // Create a normalization CDF.
	     double[] mean = new double[] {128.0, 128.0, 128.0};
	     double[] stDev = new double[] {64.0, 64.0, 64.0};
	     float[][] CDFnorm = new float[hist.getNumBands()][];
	     for(int b = 0; b < hist.getNumBands(); b++) {
	         CDFnorm[b] = new float[hist.getNumBins(b)];
	         double mu = mean[b];
	         double twoSigmaSquared = 2.0*stDev[b]*stDev[b];
	         CDFnorm[b][0] =
	             (float)Math.exp(-mu*mu/twoSigmaSquared);
	         for(int i = 1; i < hist.getNumBins(b); i++) {
	             double deviation = i - mu;
	             CDFnorm[b][i] = CDFnorm[b][i-1] +
	                (float)Math.exp(-deviation*deviation/twoSigmaSquared);
	         }
	     }
	     for(int b = 0; b < hist.getNumBands(); b++) {
	         double CDFnormLast = CDFnorm[b][hist.getNumBins(b)-1];
	        for(int i = 0; i < hist.getNumBins(b); i++) {
	            CDFnorm[b][i] /= CDFnormLast;
	        }
	     }
	     // Create a histogram-normalized image.
	     nm = JAI.create("matchcdf", dst, CDFnorm);

	}

	

	     


}
