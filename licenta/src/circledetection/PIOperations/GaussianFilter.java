package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;

public class GaussianFilter extends Operation {

	@Override
	public void processCommand() {
		float[] gaussianData = { 1.0f, 2.0f, 1.0f, 2.0f, 4.0f, 2.0f, 1.0f,
				2.0f, 1.0f };
		float weight = 1 / 16f;
		for (int i = 0; i < gaussianData.length; i++) {
			gaussianData[i] = gaussianData[i] * weight;
		}

		KernelJAI gaussianKernel = new KernelJAI(3, 3, gaussianData);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(initialState);
		pb.add(gaussianKernel);
		workImage.display(JAI.create("convolve", pb));
	}

}
