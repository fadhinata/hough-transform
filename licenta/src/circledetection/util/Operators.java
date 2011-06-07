package circledetection.util;

import java.awt.EventQueue;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;


import circledetection.gui.HistogramChartPanel;

public class Operators {
	
	public static PlanarImage convertToGrayScale(PlanarImage source) {
//
//		ColorSpace colorSpaceInput = source.getColorModel().getColorSpace();
//		ColorModel colorModelInput = RasterFactory.createComponentColorModel(
//				source.getSampleModel().getDataType(), colorSpaceInput, false,
//				false, Transparency.OPAQUE);
//
//		ImageLayout imageLayoutInput = new ImageLayout();
//		imageLayoutInput.setColorModel(colorModelInput);
//		RenderingHints RenderingHintsInput = new RenderingHints(
//				JAI.KEY_IMAGE_LAYOUT, imageLayoutInput);
//		ParameterBlock parameterBlockInput = new ParameterBlock();
//		parameterBlockInput.addSource(source);
//		parameterBlockInput.add(source.getSampleModel().getDataType());
//		PlanarImage sourceWithProfile = JAI.create("format",
//				parameterBlockInput, RenderingHintsInput);
//
//		ColorSpace colorSpaceOutput = ColorSpace
//				.getInstance(ColorSpace.CS_GRAY);
//		ColorModel colorModelOutput = RasterFactory.createComponentColorModel(
//				sourceWithProfile.getSampleModel().getDataType(),
//				colorSpaceOutput, false, false, Transparency.OPAQUE);
//
//		ImageLayout imageLayoutOutput = new ImageLayout();
//		imageLayoutOutput.setSampleModel(colorModelOutput
//				.createCompatibleSampleModel(sourceWithProfile.getWidth(),
//						sourceWithProfile.getHeight()));
//		RenderingHints renderingHintsOutput = new RenderingHints(
//				JAI.KEY_IMAGE_LAYOUT, imageLayoutOutput);
//		ParameterBlock parameterBlockOutput = new ParameterBlock();
//		parameterBlockOutput.addSource(sourceWithProfile);
//		parameterBlockOutput.add(colorModelOutput);
//
//		return JAI.create("ColorConvert", parameterBlockOutput,
//				renderingHintsOutput);

		PlanarImage dst = null;
        double b = 0.0;
        double[][] matrix = {
                                { .114D, 0.587D, 0.299D, b },
                                { .114D, 0.587D, 0.299D, b },
                                { .114D, 0.587D, 0.299D, b }
                            };

        if ( source != null ) {
            ParameterBlock pb = new ParameterBlock();
            pb.addSource(source);
            pb.add(matrix);
            dst = JAI.create("bandcombine", pb, null);
        }

        return dst;
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

	     PlanarImage dst = createHistogramImage(img);

	     // Retrieve the histogram data.
	     hist = (Histogram) dst.getProperty("histogram");
	 
		Raster raster = img.getExtendedData(img.getBounds(), BorderExtender.createInstance(BorderExtender.BORDER_WRAP));
	     hist.countPixels(raster,null,0, 0,255,1);
	     return hist;
	    
	}


	public static PlanarImage createHistogramImage(PlanarImage img) {
		// Create the parameter block.
	     ParameterBlock pb = new ParameterBlock();
	     pb.addSource(img);               // Specify the source image
	                         // Specify the histogram
	     pb.add(null);                      // No ROI
	     pb.add(1);                         // Sampling
	     pb.add(1);                         // periods

	     // Perform the histogram operation.
	     PlanarImage dst = (PlanarImage)JAI.create("histogram", pb, null);
		return dst;
	}
	public static PlanarImage medianFilter(PlanarImage img, MedianFilterShape mask) {
		ParameterBlock pb = new ParameterBlock();
		pb = new ParameterBlock();
		pb.addSource(img);
		pb.add(mask);
		pb.add(3);
		return (PlanarImage) JAI.create("MedianFilter", pb);

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
//		Histogram hist = createHistogram(source);
		double[] max = (double[]) op.getProperty("maximum");
		double[] min = thresholdValue;
		double[] constant = { 255 };

		
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(source);
		pb.add(min);
//		pb.add(max);
		pb.add(constant);
		return JAI.create("BinaryThreshold", pb);
	
	}
	
	
	public static PlanarImage sobel(PlanarImage source) {
		float[] SOBEL_V_DATA = {
			-1.0F, -2.0F, -1.0F,
			 0.0F,  0.0F,  0.0F,
			 1.0F,  2.0F,  1.0F
			};
			
		float[] SOBEL_H_DATA = {
			 1.0F,  0.0F, -1.0F,
			 2.0F,  0.0F, -2.0F,
			 1.0F,  0.0F, -1.0F
			};
			
		KernelJAI SOBEL_V 	= new KernelJAI(3,3,SOBEL_V_DATA);
		KernelJAI SOBEL_H 	= new KernelJAI(3,3,SOBEL_H_DATA);

		
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(source);
		pb.add(SOBEL_H);
		pb.add(SOBEL_V);
		
		return (PlanarImage) JAI.create("GradientMagnitude", pb);
	
	}
//
//	public static PlanarImage houghEllipse(PlanarImage source, final ParameterBlock pb) {
//				
//		System.out.println("hough ellipses");
//		pb.addSource(source);
//		final EllipseDetectedData detectedData = new EllipseDetectedData();
//		System.out.println(Thread.currentThread().getName());
//		
//		new SwingWorker<PlanarImage, Object>()
//		{
//
//			@Override
//			protected PlanarImage doInBackground() throws Exception {
//				// TODO Auto-generated method stub
//				PlanarImage img = (PlanarImage) JAI.create("HoughEllipses",pb);
//				return null;
//			}};
//		
//	}
//			@Override
//			public void run() {
//				System.out.println("run "+Thread.currentThread().getName());
//				detectedData.setImage(img);
//				System.out.println("hough ellipses after create");
//				
//				
//			}
//		});
////		@SuppressWarnings("unchecked")
////		Object obj = img.getProperty(EllipseDescriptor.DETECTED_ELLIPSES);
////		System.out.println(obj.getClass());
//		return detectedData.getImage(); 
//	}
//

}
