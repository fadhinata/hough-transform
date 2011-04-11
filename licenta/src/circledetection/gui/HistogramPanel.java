package circledetection.gui;

import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;



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
		int number = hist.getNumBands();
		double []doubleValues = new double[number * hist.getBins()[0].length]; int d=0;
		for (int i=0; i< number ; i++) {
			value = hist.getBins()[i];
			for(int j=0; j< value.length; j++)
				doubleValues[d++] = value[j];		     
		}   
//		double[] value = new double[100];
//		Random generator = new Random();
//		for (int i = 1; i < 100; i++)
//			value[i] = generator.nextDouble();
//		int number = 10;

		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);

		dataset.addSeries("Histogram", doubleValues, 255,0,255);

		PlotOrientation orientation = PlotOrientation.VERTICAL;

		JFreeChart chart = ChartFactory.createHistogram("", null,
				null, dataset, orientation, false, false, false);
		

		return chart;
	}
	
	public void init()
	{
		JFreeChart chart =createPlot(); 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setSize(200,200);
		
		chartPanel.setVisible(true);
		this.add(chartPanel);
	}

	

	     


}
