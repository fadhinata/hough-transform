package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;

public class Sobel extends Operation {

	@Override
	public void processCommand() {
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
			pb.addSource(initialState);
			pb.add(SOBEL_H);
			pb.add(SOBEL_V);
			
			workImage.display(JAI.create("GradientMagnitude", pb));
		
	}

}
