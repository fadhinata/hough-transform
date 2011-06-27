package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;

public class AdjustContrast extends Operation {

	private double min,max;
	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	
	public AdjustContrast(double min, double max){
		this();
		this.min=min;
		this.max=max;
	}

	public AdjustContrast() {
	}

	@Override
	public void processCommand() {
		ParameterBlock pb = null;
		int bands = initialState.getSampleModel().getNumBands();
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
		pb.addSource(initialState);
		pb.add(lookup);
		workImage.display(JAI.create("lookup", pb, null));

	}

}
