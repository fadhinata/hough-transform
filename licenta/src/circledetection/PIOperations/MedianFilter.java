package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;

public class MedianFilter extends Operation {

	private MedianFilterShape mask;

	public MedianFilter()
	{
		super();
		mask = MedianFilterDescriptor.MEDIAN_MASK_X;
	}
	public MedianFilter(MedianFilterShape mask){
		super();
		this.mask = mask;
	}

	@Override
	public void processCommand() {
		ParameterBlock pb = new ParameterBlock();
		pb = new ParameterBlock();
		pb.addSource(initialState);
		pb.add(mask);
		pb.add(3);
		workImage.display(JAI.create("MedianFilter", pb));
		
	}

}
