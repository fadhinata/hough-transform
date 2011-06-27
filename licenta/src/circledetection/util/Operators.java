package circledetection.util;

import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

public class Operators {
	

//	public static double[][] getExtrema(PlanarImage source)
//	{
//		RenderedOp op = extrema(source);
//
//		// // Retrieve both the maximum and minimum pixel value
////		Histogram hist = createHistogram(source);
//		double[][] extrema = (double[][]) op.getProperty("extrema");
//		return extrema;
//		
//	}


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
	

//	private static RenderedOp extrema(PlanarImage source) {
//		ParameterBlock pb1 = new ParameterBlock();
//		pb1.addSource(source); // The source image
//		pb1.add(null); // The region of the image to scan
//		pb1.add(1); // The horizontal sampling rate
//		pb1.add(1); // The vertical sampling rate
//
//		// Perform the extrema operation on the source image
//		RenderedOp op = JAI.create("extrema", pb1);
//		return op;
//	}
	
	

//	public static PlanarImage prewitt(PlanarImage source) {
//		float[] prewittVData = {
//			-1.0F, -1.0F, -1.0F,
//			 0.0F, 0.0F, 0.0F,
//			 1.0F, 1.0F, 1.0F
//			};
//			
//		float[] PREWITT_H_DATA = { 
//			 1.0F, 0.0F, -1.0F,
//			 1.0F, 0.0F, -1.0F,
//			 1.0F, 0.0F, -1.0F
//			};
//			
//		KernelJAI prewittKernelV 	= new KernelJAI(3,3,prewittVData);
//		KernelJAI prewittKernelH 	= new KernelJAI(3,3,PREWITT_H_DATA);
//
//		
//		ParameterBlock pb = new ParameterBlock();
//		pb.addSource(source);
//		pb.add(prewittKernelH);
//		pb.add(prewittKernelV);
//		
//		return (PlanarImage) JAI.create("GradientMagnitude", pb);
//	
//	}
	public static PlanarImage scale(PlanarImage sourceForZoom,float width, float height) {

		PlanarImage rendering;
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(sourceForZoom); // The source image
		pb.add(width); // The xScale
		pb.add(height); // The yScale
		pb.add(0.0F); // The x translation
		pb.add(0.0F); // The y translation
		pb.add(Interpolation.getInstance(Interpolation.INTERP_BICUBIC_2)); // The
																			// interpolation

		
		return JAI.create("scale", pb, null);
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
//	public static PlanarImage canny(PlanarImage source) {
//		CannyEdgeDetector ced = new CannyEdgeDetector();
//		ced.setSourceImage(source);
//		ced.process();
//		return ced.getEdgesImage();
//		
//	}

}
