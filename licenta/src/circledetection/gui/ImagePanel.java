package circledetection.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ColorModel;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.ImageLayout;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;
import javax.swing.JPanel;

import com.sun.media.jai.widget.DisplayJAI;
@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements MouseListener{

	private PlanarImage source = null;
//    private RenderableImage renderableSource;
//	private Vector<PlanarImage> sources;
	private DisplayJAI display;
	private int width;
	private int height;
	private float scaleFactor = 1.0f;
//	private PlanarImage dst;
//	private JScrollPane scrollPane;
	
    public ImagePanel()
    {
    
    	this.setLayout(new BorderLayout());
       	setBackground(Color.black);
       	this.addMouseListener(this);

    	
    }
    

    
	public void createImage(String filename) {

		
			source = JAI.create("fileload", filename );
		    width = source.getWidth();
		    height = source.getHeight();
		    this.setSize(width,height); 
			display= new DisplayJAI();
			add(display,BorderLayout.CENTER);
    	  	
		    
	 	      
	     
	      
	}



	public void display() { 
	
		//scale(ApplicationFrame.getPrefferedSize().width,ApplicationFrame.getPrefferedSize().height , true);
		display.set(source);
		
	}
	
	public void convertToGrayScale()
	{
//		 
//	     int bits[] = new int[] {8};
//	     ColorModel cm = new ComponentColorModel(cs, bits, false, false,
//	                                             Transparency.OPAQUE,
//	                                             DataBuffer.TYPE_BYTE);
//	     source = ColorConvertDescriptor.create(source, cm, null); 
	     
				ColorSpace colorSpaceInput = source.getColorModel().getColorSpace();
				ColorModel colorModelInput = RasterFactory.createComponentColorModel(
		    	 source.getSampleModel().getDataType(),
		    	 colorSpaceInput, false, false, Transparency.OPAQUE);

		    	 ImageLayout imageLayoutInput = new ImageLayout();
		    	 imageLayoutInput.setColorModel(colorModelInput);
		    	 RenderingHints RenderingHintsInput = new RenderingHints(
		    	 JAI.KEY_IMAGE_LAYOUT, imageLayoutInput);
		    	 ParameterBlock parameterBlockInput = new ParameterBlock();
		    	 parameterBlockInput.addSource(source);
		    	 parameterBlockInput.add(source.getSampleModel().getDataType());
		    	 PlanarImage sourceWithProfile = JAI.create("format",
		    	 parameterBlockInput, RenderingHintsInput);

				ColorSpace colorSpaceOutput =	ColorSpace.getInstance(ColorSpace.CS_GRAY);
				ColorModel colorModelOutput = RasterFactory
		    	 .createComponentColorModel(sourceWithProfile
		    	 .getSampleModel().getDataType(), colorSpaceOutput,
		    	 false, false, Transparency.OPAQUE);

		    	 ImageLayout imageLayoutOutput = new ImageLayout();
		    	 imageLayoutOutput.setSampleModel(colorModelOutput
		    	 .createCompatibleSampleModel(sourceWithProfile
		    	 .getWidth(), sourceWithProfile
		    	 .getHeight()));
		    	 RenderingHints renderingHintsOutput = new RenderingHints(
		    	 JAI.KEY_IMAGE_LAYOUT, imageLayoutOutput);
		    	 ParameterBlock parameterBlockOutput = new ParameterBlock();
		    	 parameterBlockOutput.addSource(sourceWithProfile);
		    	 parameterBlockOutput.add(colorModelOutput);

		    	source = JAI.create("ColorConvert", parameterBlockOutput,
		    	 renderingHintsOutput);
		     

	     
	}

	 public void scale(float width, float height) {

	        PlanarImage rendering;
	        ParameterBlock pb = new ParameterBlock();
	          pb.addSource(source);                   // The source image
	          pb.add(width);                        // The xScale
	          pb.add(height);                        // The yScale
	          pb.add(0.0F);                       // The x translation
	          pb.add(0.0F);                       // The y translation
	          pb.add(Interpolation.getInstance(Interpolation.INTERP_BICUBIC_2)); // The interpolation

	     // Create the scale operation
	       rendering = JAI.create("scale", pb, null);

			display.set(rendering);
	    }
	    	 
	    
	     
	

   public void adjustContrast()
   {
	   ParameterBlock pb1 = new ParameterBlock();
	     pb1.addSource(source);   // The source image
	     pb1.add(null);        // The region of the image to scan
	     pb1.add(1);         // The horizontal sampling rate
	     pb1.add(1);         // The vertical sampling rate

	     // Perform the extrema operation on the source image
	     RenderedOp op = JAI.create("extrema", pb1);

//	     // Retrieve both the maximum and minimum pixel value
	     double[] min = (double[]) op.getProperty("minimum");
	     double[] max = (double[]) op.getProperty("maximum");

	   
	     ParameterBlock pb = null;
	         int bands = source.getSampleModel().getNumBands();
	        double slope;
	        double y_int;

	     
	            

	            byte lut[][] = new byte[bands][256];

	            for ( int i = 0; i < 256; i++ ) {
	                for ( int j = 0; j < bands; j++ ) {
	                    double high = max[j];
	                    double low = min[j];
	                	if ( high != low ) {
	    	                slope = 256.0 / (high - low);
	    	                y_int = 256.0 - slope*high;
	    	            } else {
	    	                slope = 0.0;
	    	                y_int = 0.0;
	    	            }
	                   int value = (int)(slope*i + y_int);

	                   if ( value < (int)low ) {
	                       value = 0;
	                   } else if ( value > (int)high ) {
	                       value = 255;
	                   } else {
	                       value &= 0xFF;
	                   }

	                   lut[j][i] = (byte) value;
	                }
	            }

	            LookupTableJAI lookup = new LookupTableJAI(lut);

	            pb = new ParameterBlock();
	            pb.addSource(source);
	            pb.add(lookup);
	            source = JAI.create("lookup", pb, null);
	            display();
	  
   }
   public boolean hasContent()
   {
	   return source!=null;
   }



public PlanarImage getSource() {
	// TODO Auto-generated method stub
	return source;
}



@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
	if(e.getButton() == MouseEvent.BUTTON1)
		scaleFactor += 0.2;
	if(e.getButton() == MouseEvent.BUTTON3)
		scaleFactor -=0.2;
	scale(scaleFactor,scaleFactor);

}



@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
	
	

}
