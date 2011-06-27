package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;

public class Threshold extends Operation {

	private double thresholdValue;

	public Threshold(double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	@Override
	public void processCommand() {
				
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(initialState);
		pb.add(thresholdValue);
		workImage.display(JAI.create("binarize", pb));

	}

}
