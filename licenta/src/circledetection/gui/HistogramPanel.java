package circledetection.gui;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HistogramPanel extends JPanel{
    private static int numBands;

	// Retrieves a histogram for the image.
   
	
	public static PlanarImage getHistogram(PlanarImage img) {
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

	     // Print 3-band histogram.
	     for (int i=0; i< hist.getNumBins().length; i++) {
	        System.out.println(hist.getBinSize(0, i) + " ");
	     }
	     return dst;
	}
	
	private void createPlot()
	{
		
	}

	

	     


}
