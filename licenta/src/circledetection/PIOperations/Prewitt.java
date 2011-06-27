package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;

public class Prewitt extends Operation {

	@Override
	public void processCommand() {
		float[] prewittVData = {
				-1.0F, -1.0F, -1.0F,
				 0.0F, 0.0F, 0.0F,
				 1.0F, 1.0F, 1.0F
				};
				
			float[] PREWITT_H_DATA = { 
				 1.0F, 0.0F, -1.0F,
				 1.0F, 0.0F, -1.0F,
				 1.0F, 0.0F, -1.0F
				};
				
			KernelJAI prewittKernelV 	= new KernelJAI(3,3,prewittVData);
			KernelJAI prewittKernelH 	= new KernelJAI(3,3,PREWITT_H_DATA);

			
			ParameterBlock pb = new ParameterBlock();
			pb.addSource(initialState);
			pb.add(prewittKernelH);
			pb.add(prewittKernelV);
			workImage.display(JAI.create("GradientMagnitude", pb));

	}

}
