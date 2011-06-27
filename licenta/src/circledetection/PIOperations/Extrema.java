package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class Extrema extends Operation {

	private RenderedOp op;

	@Override
	public void processCommand() {
		ParameterBlock pb1 = new ParameterBlock();
		pb1.addSource(initialState); // The source image
		pb1.add(null); // The region of the image to scan
		pb1.add(1); // The horizontal sampling rate
		pb1.add(1); // The vertical sampling rate

		// Perform the extrema operation on the source image
		op = JAI.create("extrema", pb1);
	
	}

	public double[] getMax() {
		return (double[]) op.getProperty("maximum");
	}
	public double[] getMin() {
		return (double[]) op.getProperty("minimum");
	}
	

}
