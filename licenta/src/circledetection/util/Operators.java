package circledetection.util;

import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;

import circledetection.gui.HistogramChartPanel;

public class Operators {
	
	public static PlanarImage convertToGrayScale(PlanarImage source) {

		ColorSpace colorSpaceInput = source.getColorModel().getColorSpace();
		ColorModel colorModelInput = RasterFactory.createComponentColorModel(
				source.getSampleModel().getDataType(), colorSpaceInput, false,
				false, Transparency.OPAQUE);

		ImageLayout imageLayoutInput = new ImageLayout();
		imageLayoutInput.setColorModel(colorModelInput);
		RenderingHints RenderingHintsInput = new RenderingHints(
				JAI.KEY_IMAGE_LAYOUT, imageLayoutInput);
		ParameterBlock parameterBlockInput = new ParameterBlock();
		parameterBlockInput.addSource(source);
		parameterBlockInput.add(source.getSampleModel().getDataType());
		PlanarImage sourceWithProfile = JAI.create("format",
				parameterBlockInput, RenderingHintsInput);

		ColorSpace colorSpaceOutput = ColorSpace
				.getInstance(ColorSpace.CS_GRAY);
		ColorModel colorModelOutput = RasterFactory.createComponentColorModel(
				sourceWithProfile.getSampleModel().getDataType(),
				colorSpaceOutput, false, false, Transparency.OPAQUE);

		ImageLayout imageLayoutOutput = new ImageLayout();
		imageLayoutOutput.setSampleModel(colorModelOutput
				.createCompatibleSampleModel(sourceWithProfile.getWidth(),
						sourceWithProfile.getHeight()));
		RenderingHints renderingHintsOutput = new RenderingHints(
				JAI.KEY_IMAGE_LAYOUT, imageLayoutOutput);
		ParameterBlock parameterBlockOutput = new ParameterBlock();
		parameterBlockOutput.addSource(sourceWithProfile);
		parameterBlockOutput.add(colorModelOutput);

		return JAI.create("ColorConvert", parameterBlockOutput,
				renderingHintsOutput);

	}


	public static PlanarImage adjustContrast(PlanarImage source,double min, double max) {
		
//		ParameterBlock pb1 = new ParameterBlock();
//		pb1.addSource(source); // The source image
//		pb1.add(null); // The region of the image to scan
//		pb1.add(1); // The horizontal sampling rate
//		pb1.add(1); // The vertical sampling rate
//
//		// Perform the extrema operation on the source image
//		RenderedOp op = JAI.create("extrema", pb1);
//
//		// // Retrieve both the maximum and minimum pixel value
//		double[] min = (double[]) op.getProperty("minimum");
//		double[] max = (double[]) op.getProperty("maximum");

		
		ParameterBlock pb = null;
		int bands = source.getSampleModel().getNumBands();
		double slope;
		double y_int;

		byte lut[][] = new byte[bands][256];
		double high =max;
		double low = min;

		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < bands; j++) {
				if (high != low) {
					slope = 256.0 / (high - low);
					y_int = 256.0 - slope * high;
				} else {
					slope = 0.0;
					y_int = 0.0;
				}
				int value = (int) (slope * i + y_int);

				if (value < (int) low) 
					value = 0;
				if (value > (int) high) 
					value = 255;
				 else {
					value &= 0xFF;
				}

				lut[j][i] = (byte) value;
			}
		}

		LookupTableJAI lookup = new LookupTableJAI(lut);

		pb = new ParameterBlock();
		pb.addSource(source);
		pb.add(lookup);

		return JAI.create("lookup", pb, null);

	}
	public static Histogram  createHistogram(PlanarImage img) {
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
	    
	}
	public static PlanarImage threshold(PlanarImage source, double[] thresholdValue) {
		ParameterBlock pb1 = new ParameterBlock();
		pb1.addSource(source); // The source image
		pb1.add(null); // The region of the image to scan
		pb1.add(1); // The horizontal sampling rate
		pb1.add(1); // The vertical sampling rate

		// Perform the extrema operation on the source image
		RenderedOp op = JAI.create("extrema", pb1);

		// // Retrieve both the maximum and minimum pixel value
		Histogram hist = createHistogram(source);
		double[] max = (double[]) op.getProperty("maximum");
		double[] min = thresholdValue;
		double[] constant = { 255 };

		
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(source);
		pb.add(min);
		pb.add(max);
		pb.add(constant);
		return JAI.create("threshold", pb);
	
	}

}
